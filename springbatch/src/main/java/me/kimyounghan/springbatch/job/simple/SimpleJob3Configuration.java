package me.kimyounghan.springbatch.job.simple;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 실패를 해보는 실행 Job (BATCH_JOB_EXECUTION 학습)
 *
 * @author 김영한
 */
@Slf4j
@RequiredArgsConstructor
@Configuration // Spring Batch의 모든 Job은 @Configuration으로 등록해서 사용
public class SimpleJob3Configuration {
    private static final String SIMPLE_JOB_3 = "simpleJob3";
    private static final String SIMPLE_STEP_3 = "simpleStep3";
    private static final String SIMPLE_STEP_4 = "simpleStep4";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob3() {
        return jobBuilderFactory.get(SIMPLE_JOB_3)
            .start(simpleStep3(null))
            .next(simpleStep4(null))
            .build();
    }

    @Bean
    @JobScope
    public Step simpleStep3(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get(SIMPLE_STEP_3)
            .tasklet(((contribution, chunkContext) -> {
//                throw new IllegalArgumentException(SIMPLE_STEP_3 + " is failed."); FAILED 테스트
                log.info(">>>>> This is {}", SIMPLE_STEP_3);
                log.info(">>>>> requestDate = {}", requestDate);
                return RepeatStatus.FINISHED;
            })).build();
    }

    @Bean
    @JobScope
    public Step simpleStep4(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get(SIMPLE_STEP_4)
            .tasklet(((contribution, chunkContext) -> {
                log.info(">>>>> This is {}", SIMPLE_STEP_4);
                log.info(">>>>> requestDate = {}", requestDate);
                return RepeatStatus.FINISHED;
            })).build();
    }
}
