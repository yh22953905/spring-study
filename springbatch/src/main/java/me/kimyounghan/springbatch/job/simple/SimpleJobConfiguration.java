package me.kimyounghan.springbatch.job.simple;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 단순히 실행해보는 Job
 *
 * @author 김영한
 */
@Slf4j
@RequiredArgsConstructor
@Configuration // Spring Batch의 모든 Job은 @Configuration으로 등록해서 사용
public class SimpleJobConfiguration {
    private static final String SIMPLE_JOB_1 = "simpleJob";
    private static final String SIMPLE_STEP_1 = "simpleStep1";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get(SIMPLE_JOB_1) // simpleJob이란 이름의 Batch Job을 생성, job의 이름은 별도로 지정하지 않고, 이렇게 Builder를 통해 지정
            .start(simpleStep1())
            .build();
    }

    @Bean
    @JobScope
    public Step simpleStep1() {
        return stepBuilderFactory.get(SIMPLE_STEP_1) // simpleStep 이란 이름의 Batch Step 을 생성, step의 이름은 별도로 지정하지 않고, 이렇게 Builder를 통해 지정
            .tasklet(((contribution, chunkContext) -> { // Step 안에서 수행될 기능들 명시, asklet은 Step안에서 단일로 수행될 커스텀한 기능들을 선언
                log.info(">>>>> This is {}", SIMPLE_STEP_1);
                return RepeatStatus.FINISHED;
            })).build();
    }
}