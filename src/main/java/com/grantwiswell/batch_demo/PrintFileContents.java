package com.grantwiswell.batch_demo;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PrintFileContents implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("NotifyTask Start");

        try (Stream<Path> paths = Files.walk(Paths.get("C:/"))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(System.out::println);
        }

        System.out.println("NotifyTask Complete");
        return RepeatStatus.FINISHED;
    }
}
