package me.kimyounghan.springbatch.job.decider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * JobExecutionDevider를 학습하는 Job
 *
 * @author 김영한
 */
@Slf4j
@RequiredArgsConstructor
@Configuration // Spring Batch의 모든 Job은 @Configuration으로 등록해서 사용
public class DeciderJobConfiguration {
    private static final String DECIDER_JOB = "deciderJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job deciderJob() {
        return jobBuilderFactory.get(DECIDER_JOB)
            .start(startStep())
            .next(decider()) // 홀수 | 짝수 구분
            .from(decider()) // decider의 상태가
                .on("ODD") // ODD라면
                .to(oddStep()) // oddStep로 간다.
            .from(decider()) // decider의 상태가
                .on("EVEN") // ODD라면
                .to(evenStep()) // evenStep로 간다.
            .end() // builder 종료
            .build();
    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> Start!");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> 짝수입니다.");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> 홀수입니다.");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new OddDecider();
    }

    public static class OddDecider implements JobExecutionDecider {

        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            Random random = new Random();

            int randomNumber = random.nextInt(50) + 1;
            log.info("랜덤숫자: {}", randomNumber);

            if(randomNumber % 2 == 0) {
                return new FlowExecutionStatus("EVEN");
            } else {
                return new FlowExecutionStatus("ODD");
            }
        }
    }
}
