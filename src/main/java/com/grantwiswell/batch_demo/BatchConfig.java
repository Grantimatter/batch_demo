package com.grantwiswell.batch_demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step printFileContents() {
        return stepBuilderFactory.get("printFileContents")
                .tasklet(new PrintFileContents())
                .build();
    }

    @Bean
    public Step moveFilesToArchive() {
        return stepBuilderFactory.get("moveFilesToArchive")
                .tasklet(new MoveToArchive())
                .build();
    }

    @Bean
    public Job notifyJob() {
        return jobBuilderFactory.get("notifyJob")
                .incrementer(new RunIdIncrementer())
                .start(printFileContents())
                .next(moveFilesToArchive())
                .build();
    }
}
