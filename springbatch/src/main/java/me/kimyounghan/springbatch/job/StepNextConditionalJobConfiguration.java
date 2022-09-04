package me.kimyounghan.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Next를 학습하는 Job (정상과 오류 케이스에 따라 다른 Step)
 *
 * @author 김영한
 */
@Slf4j
@RequiredArgsConstructor
@Configuration // Spring Batch의 모든 Job은 @Configuration으로 등록해서 사용
public class StepNextConditionalJobConfiguration {
    private static final String STEP_NEXT_CONDITIONAL_JOB = "stepNextConditionalJob";
    private static final String CONDITIONAL_JOB_STEP_1 = "conditionalJobStep1";
    private static final String CONDITIONAL_JOB_STEP_2 = "conditionalJobStep2";
    private static final String CONDITIONAL_JOB_STEP_3 = "conditionalJobStep3";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepNextConditionalJob() {
        return jobBuilderFactory.get(STEP_NEXT_CONDITIONAL_JOB)
            .start(conditionalJobStep1())
                .on(ExitStatus.FAILED.getExitCode()) // FAILED 일 경우
                .to(conditionalJobStep3()) // step3으로 이동한다.
                .on("*") // step3의 결과 관계 없이
                .end() // step3으로 이동하면 Flow가 종료한다.
            .from(conditionalJobStep1()) // step1로부터
                .on("*") // FAILED 외에 모든 경우
                .to(conditionalJobStep2()) // step2로 이동한다.
                .next(conditionalJobStep3()) // step2가 정상 종료되면 step3으로 이동한다.
                .on("*") // step3의 결과 관계 없이
                .end() // step3으로 이동하면 Flow가 종료한다.
            .end() // Job 종료
            .build();
    }

    @Bean
    @JobScope
    public Step conditionalJobStep1() {
        return stepBuilderFactory.get(CONDITIONAL_JOB_STEP_1)
            .tasklet(((contribution, chunkContext) -> {
                log.info(">>>>> This is {}", CONDITIONAL_JOB_STEP_1);

                // ExitStatus를 FAILED로 지정한다. 해당 status를 보고 flow가 진행된다.
                contribution.setExitStatus(ExitStatus.FAILED);

                return RepeatStatus.FINISHED;
            })).build();
    }

    @Bean
    @JobScope
    public Step conditionalJobStep2() {
        return stepBuilderFactory.get(CONDITIONAL_JOB_STEP_2)
            .tasklet(((contribution, chunkContext) -> {
                log.info(">>>>> This is {}", CONDITIONAL_JOB_STEP_2);
                return RepeatStatus.FINISHED;
            })).build();
    }

    @Bean
    @JobScope
    public Step conditionalJobStep3() {
        return stepBuilderFactory.get(CONDITIONAL_JOB_STEP_3)
            .tasklet(((contribution, chunkContext) -> {
                log.info(">>>>> This is {}", CONDITIONAL_JOB_STEP_3);
                return RepeatStatus.FINISHED;
            })).build();
    }
}
