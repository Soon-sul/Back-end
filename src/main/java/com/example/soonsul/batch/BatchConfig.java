package com.example.soonsul.batch;

import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Scrap;
import com.example.soonsul.liquor.repository.ScrapRepository;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job liquorBatchJob() {
        return jobBuilderFactory.get("liquorBatchJob")
                .start(liquorStep())
                .build();
    }

    @Bean
    public Step liquorStep() {
        return stepBuilderFactory.get("liquorStep")
                .<Liquor,Liquor>chunk(100)
                .reader(liquorItemReader())
                .processor(liquorItemProcessor())
                .writer(liquorItemWriter(entityManagerFactory))
                .build();
    }


    @Bean
    public ItemReader<Liquor> liquorItemReader() {
        JpaPagingItemReader<Liquor> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT l FROM Liquor l");
        reader.setPageSize(100); // 페이지 크기 설정
        return reader;
    }


    @Bean
    public ItemProcessor<Liquor, Liquor> liquorItemProcessor() {
        return new ItemProcessor<Liquor, Liquor>() {
            @Override
            public Liquor process(Liquor liquor) throws Exception {
                try {
                    // liquor 테이블의 id를 통해 scrap 테이블을 조회한다.
                    List<Scrap> scraps = scrapRepository.findByLiquorLiquorId(liquor.getLiquorId());

                    // scrap 테이블에서 user_id를 추출한다.
                    List<String> userIds = scraps.stream()
                            .map(scrap -> scrap.getUser().getUserId())
                            .collect(Collectors.toList());

                    // user_id를 통해 user 테이블을 조회한다.
                    List<User> users = userRepository.findAllById(userIds);

                    users.stream()
                            .collect(Collectors.groupingBy(User::getAge, Collectors.groupingBy(User::getGender, Collectors.counting())))
                            .entrySet()
                            .stream()
                            .max(Map.Entry.comparingByValue(Comparator.comparing(Map::size)))
                            .ifPresent(entry -> {
                                // liquor 테이블의 age, gender 값을 업데이트한다.
                                liquor.setAge(entry.getKey());
                                liquor.setGender(entry.getValue().entrySet().stream()
                                        .max(Map.Entry.comparingByValue())
                                        .map(Map.Entry::getKey)
                                        .orElseGet(() -> null));
                            });
                } catch (Exception e) {
                    // 예외 처리 로직 추가
                    log.error("Error processing liquor: {}", e.getMessage());
                }

                return liquor;
            }
        };
    }

    @Bean
    public ItemWriter<Liquor> liquorItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Liquor> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

}