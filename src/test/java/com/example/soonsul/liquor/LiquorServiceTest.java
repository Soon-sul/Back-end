//package com.example.soonsul.liquor;
//
//import com.example.soonsul.liquor.entity.FilteringClick;
//import com.example.soonsul.liquor.entity.LiquorFiltering;
//import com.example.soonsul.liquor.repository.FilteringClickRepository;
//import com.example.soonsul.liquor.repository.LiquorFilteringRepository;
//import com.example.soonsul.liquor.service.LiquorService;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class LiquorServiceTest {
//
//    @InjectMocks
//    private LiquorService liquorService;
//
//    @Mock
//    private FilteringClickRepository filteringClickRepository;
//
//    @Mock
//    private LiquorFilteringRepository filteringRepository;
//
//
//    @Test
//    void 전통주_정보_필터링기능(){
//        //given
//        final List<Integer> age= Arrays.asList(20, 30, 40, 50, 60);
//        final List<String> gender= Arrays.asList("f", "g");
//        for(Integer a: age){
//            for(String g: gender){
//                if(a==20 && g.equals("f")) doReturn(clickList1()).when(filteringClickRepository).findAllByAgeAndGender(a,g);
//                else if(a==30 && g.equals("f")) doReturn(clickList2()).when(filteringClickRepository).findAllByAgeAndGender(a,g);
//                else if(a==20 && g.equals("g")) doReturn(clickList3()).when(filteringClickRepository).findAllByAgeAndGender(a,g);
//                else if(a==40 && g.equals("g")) doReturn(clickList4()).when(filteringClickRepository).findAllByAgeAndGender(a,g);
//                else doReturn(emptyClickList()).when(filteringClickRepository).findAllByAgeAndGender(a,g);
//            }
//        }
//
//        //when
//        liquorService.updateFiltering();
//
//        //then
//        verify(filteringRepository, times(4)).save(any(LiquorFiltering.class));
//    }
//
//
//    @Nested
//    class 가장_인기있는_전통주_조회{
//        @Test
//        void 나이와성별에_해당하는_클릭이_하나이상(){
//            //given
//            doReturn(clickList3()).when(filteringClickRepository).findAllByAgeAndGender(20,"f");
//
//            //when
//            final String result= liquorService.getPopularLiquor(20, "f");
//
//            //then
//            assertEquals(result, "L0010001");
//        }
//
//        @Test
//        void 하나도_없을경우(){
//            //given
//
//            //when
//            final String result= liquorService.getPopularLiquor(20, "f");
//
//            //then
//            assertEquals(result, "continue");
//        }
//    }
//
//
//    private List<FilteringClick> clickList1(){
//        final List<FilteringClick> clickList= new ArrayList<>();
//        clickList.add(click(20,"f","L0010001"));
//        clickList.add(click(20,"f","L0010003"));
//        clickList.add(click(20,"f","L0010002"));
//        clickList.add(click(20,"f","L0010002"));
//        clickList.add(click(20,"f","L0010003"));
//        clickList.add(click(20,"f","L0010002"));
//        return clickList;
//    }
//
//    private List<FilteringClick> clickList2(){
//        final List<FilteringClick> clickList= new ArrayList<>();
//        clickList.add(click(30,"f","L0010002"));
//        clickList.add(click(30,"f","L0010002"));
//        clickList.add(click(30,"f","L0010001"));
//        return clickList;
//    }
//
//    private List<FilteringClick> clickList3(){
//        final List<FilteringClick> clickList= new ArrayList<>();
//        clickList.add(click(20,"g","L0010003"));
//        clickList.add(click(20,"g","L0010001"));
//        return clickList;
//    }
//
//    private List<FilteringClick> clickList4(){
//        final List<FilteringClick> clickList= new ArrayList<>();
//        clickList.add(click(40,"g","L0010001"));
//        return clickList;
//    }
//
//    private List<FilteringClick> emptyClickList(){
//        return new ArrayList<>();
//    }
//
//    private FilteringClick click(Integer age, String gender, String liquorId){
//        return FilteringClick.builder()
//                .age(age)
//                .gender(gender)
//                .liquorId(liquorId)
//                .build();
//    }
//
//}
