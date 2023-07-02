package com.example.soonsul.main;

import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.repository.ClickRepository;
import com.example.soonsul.liquor.repository.LiquorRepository;
import com.example.soonsul.main.dto.WeekLiquorDto;
import com.example.soonsul.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class MainService {
    private final ClickRepository clickRepository;
    private final UserUtil userUtil;
    private final LiquorRepository liquorRepository;


    @Transactional(readOnly = true)
    public List<WeekLiquorDto> getWeekLiquor(){
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        final List<Liquor> liquorList= liquorRepository.findAll();
        for(Liquor l: liquorList){
            pq.add(new Pair(clickRepository.findAllByLiquorId(l.getLiquorId()).size(), l.getLiquorId()));
        }

        final List<String> tenLiquor= new ArrayList<>();
        int ten= 10;
        while(ten>0){
            tenLiquor.add(pq.peek().second);
            pq.remove();
            ten--;
        }

        final List<WeekLiquorDto> result= new ArrayList<>();
        for(String s: tenLiquor){
            final WeekLiquorDto dto= WeekLiquorDto.builder()
                    .liquorId(s)
                    .imageUrl(liquorRepository.findById(s).get().getImageUrl())
                    .build();
            result.add(dto);
        }

        return result;
    }


}


class Pair implements Comparable<Pair> {
    int first;
    String second;

    Pair(int f, String s) {
        this.first = f;
        this.second = s;
    }

    public int compareTo(Pair p) {
        if(this.first > p.first) return -1;
        return 1;
    }
}

