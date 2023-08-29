package com.example.soonsul.manager;

import com.example.soonsul.liquor.entity.*;
import com.example.soonsul.liquor.exception.CodeNotExist;
import com.example.soonsul.liquor.repository.*;
import com.example.soonsul.manager.dto.LocationRes;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.oauth.jwt.JwtTokenProvider;
import com.example.soonsul.user.repository.UserRepository;
import com.example.soonsul.util.LiquorUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoogleSheetsService {

    private final Sheets sheetsService;
    private final LiquorRepository liquorRepository;
    private final LocationRepository locationRepository;
    private final LocationInfoRepository locationInfoRepository;
    private final SalePlaceRepository salePlaceRepository;
    private final SalePlaceInfoRepository salePlaceInfoRepository;
    private final PrizeRepository prizeRepository;
    private final PrizeInfoRepository prizeInfoRepository;
    private final LiquorUtil liquorUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Value("${map.kakao.apiKey}")
    private String apiKey;

    @Value("${map.kakao.apiUrl}")
    private String apiUrl;

    private final RestTemplate restTemplate;


    public void postLiquor(String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();

        final List<List<Object>> list= response.getValues();
        for(List<Object> row: list){
            final String liquorId= (checkEmpty(row.get(0))==null) ? null : row.get(0).toString();
            System.out.println(liquorId);
            final String name= (checkEmpty(row.get(1))==null) ? null : row.get(1).toString();
            final Double alcohol= (checkEmpty(row.get(2))==null) ? null : Double.valueOf(row.get(2).toString());
            final String ingredient= (checkEmpty(row.get(3))==null) ? null : row.get(3).toString();
            final String brewery= (checkEmpty(row.get(4))==null) ? null : row.get(4).toString();
            final String locationName= (checkEmpty(row.get(5))==null) ? null : row.get(5).toString();
            final Integer capacity= (checkEmpty(row.get(6))==null) ? null : Integer.valueOf(row.get(6).toString());
            final Long lowestPrice= (checkEmpty(row.get(7))==null) ? null : Long.valueOf(row.get(7).toString());
            final String salePlaceName= row.get(8).toString();

            final Liquor liquor= Liquor.builder()
                    .liquorId(liquorId)
                    .name(name)
                    .ingredient(ingredient)
                    .averageRating(0.0)
                    .lowestPrice(lowestPrice)
                    .alcohol(alcohol)
                    .capacity(capacity)
                    .viewCount(0L)
                    .region(liquorUtil.getCodeId(parse(locationName, " ").get(0)))
                    .imageUrl(null)
                    .liquorCategory(liquorId.substring(0, 4))
                    .build();
            liquorRepository.save(liquor);

            for(LocationInfo info: locationInfoRepository.findByBrewery(brewery)){
                final Location location= Location.builder()
                        .locationInfoId(info.getLocationInfoId())
                        .liquor(liquor)
                        .build();
                locationRepository.save(location);
            }

            for(String sName: parse(salePlaceName,", ")){
                final Optional<SalePlaceInfo> info= salePlaceInfoRepository.findByName(sName);

                if(info.isEmpty()) continue;
                final SalePlace salePlace = SalePlace.builder()
                        .salePlaceInfoId(info.get().getSalePlaceInfoId())
                        .liquor(liquor)
                        .build();
                salePlaceRepository.save(salePlace);
            }
        }
    }


    @Transactional(readOnly = true)
    public List<String> getLocationCheck(String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
        final List<List<Object>> list= response.getValues();

        final List<String> errorList= new ArrayList<>();
        for(List<Object> row: list){
            final String locationName= row.get(5).toString();
            if(locationName.equals("-")) continue;

            final String url = apiUrl + "?query=" + locationName;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", apiKey);

            try{
                final HttpEntity<String> request = new HttpEntity<>(headers);
                LocationRes locationRes= restTemplate.exchange(url, HttpMethod.GET, request, LocationRes.class).getBody();
            } catch(ArrayIndexOutOfBoundsException e){
                errorList.add(row.get(0).toString());
            }
        }
        return errorList;
    }


    @Transactional(readOnly = true)
    public List<String> getRegionCodeCheck(String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
        final List<List<Object>> list= response.getValues();

        final List<String> errorList= new ArrayList<>();
        for(List<Object> row: list){
            final String locationName= row.get(5).toString();
            if(locationName.equals("-")) continue;

            try{
                String codeId= liquorUtil.getCodeId(parse(locationName, " ").get(0));
            } catch(CodeNotExist e){
                errorList.add(row.get(0).toString());
            }
        }
        return errorList;
    }


    @Transactional
    public void postLocationInfo(String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
        final List<List<Object>> list= response.getValues();

        for(List<Object> row: list){
            final String locationInfoId= row.get(0).toString();
            final String brewery= row.get(1).toString();
            final String name= row.get(2).toString();

            final String url = apiUrl + "?query=" + name;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", apiKey);
            final HttpEntity<String> request = new HttpEntity<>(headers);
            LocationRes locationRes= restTemplate.exchange(url, HttpMethod.GET, request, LocationRes.class).getBody();

            final LocationInfo info= LocationInfo.builder()
                    .locationInfoId(locationInfoId)
                    .name(name)
                    .brewery(brewery)
                    .latitude(locationRes.documents[0].latitude)
                    .longitude(locationRes.documents[0].longitude)
                    .build();
            locationInfoRepository.save(info);
        }
    }


    @Transactional
    public void postPrizeInfo(String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
        final List<List<Object>> list= response.getValues();

        for(List<Object> row: list){
            final String prizeInfoId= row.get(0).toString();
            final String name= row.get(1).toString();

            final PrizeInfo info= PrizeInfo.builder()
                    .prizeInfoId(prizeInfoId)
                    .name(name)
                    .build();
            prizeInfoRepository.save(info);
        }
    }


    @Transactional
    public void postPrize(String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
        final List<List<Object>> list= response.getValues();

        for(List<Object> row: list){
            final String prizeInfoId= row.get(1).toString();
            final String liquorId= row.get(2).toString();

            final Prize prize= Prize.builder()
                    .prizeInfoId(prizeInfoId)
                    .liquor(liquorUtil.getLiquor(liquorId))
                    .build();
            prizeRepository.save(prize);
        }
    }

    @Transactional(readOnly = true)
    public Set<String> getSalePlaceAll(String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
        final List<List<Object>> list= response.getValues();

        final HashMap<String, Integer> map= new HashMap<>();
        for(List<Object> row: list){
            final String locationName= row.get(8).toString();
            for(String s: parse(locationName, ", ")) {
                map.put(s, 1);
            }
        }

        return map.keySet();
    }


    @Transactional
    public void postSalePlaceInfo(String spreadsheetId, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
        final List<List<Object>> list= response.getValues();
        salePlaceInfoRepository.deleteAll();
        for(List<Object> row: list){
            final String salePlaceInfoId= (checkEmpty(row.get(0))==null) ? null : row.get(0).toString();
            final String name= (checkEmpty(row.get(1))==null) ? null : row.get(1).toString();
            final String siteUrl= (checkEmpty(row.get(2))==null) ? null : row.get(2).toString();
            final String phoneNumber= (checkEmpty(row.get(3))==null) ? null : row.get(3).toString();

            final SalePlaceInfo info= SalePlaceInfo.builder()
                    .salePlaceInfoId(salePlaceInfoId)
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .siteUrl(siteUrl)
                    .build();
            salePlaceInfoRepository.save(info);
        }
    }


    @Transactional(readOnly = true)
    public String getToken(String userId) {
        final User user= userRepository.findById(userId).get();
        return jwtTokenProvider.generateJwtToken(user);
    }

    private Object checkEmpty(Object obj){
        if(obj.equals("-") || obj.equals("")) return null;
        return obj;
    }

    private List<String> parse(String input, String split) {
        String[] lines = input.split(split);
        return new ArrayList<>(Arrays.asList(lines));
    }
}