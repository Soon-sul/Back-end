package com.example.soonsul.scan;

import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.exception.LiquorNotExist;
import com.example.soonsul.liquor.repository.LiquorRepository;
import com.example.soonsul.response.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScanService {
    private final ScanRepository scanRepository;
    private final LiquorRepository liquorRepository;


    @Transactional(readOnly = true)
    public String getLiquor(String name){
        final Liquor liquor= liquorRepository.findByName(name)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        return liquor.getLiquorId();
    }
}
