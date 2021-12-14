package com.grantwiswell.batch_demo;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class MoveToArchive implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        System.out.println("\nMoveToArchive Start\n");


        File documentsDirectory = new File("./documents");
        File archiveDirectory = null;

        if (!documentsDirectory.isDirectory()) {
            System.out.println("No documents directory");
            return RepeatStatus.FINISHED;
        }

        archiveDirectory = new File(documentsDirectory.getAbsolutePath()+"\\archive");

        if (!archiveDirectory.exists()) {
           archiveDirectory.mkdir();
        }

        File[] fileList = documentsDirectory.listFiles();

        for (File file : fileList) {
            if(!file.isFile())
                continue;

            String message = String.format("Moving %s to %s", file.getName(), archiveDirectory.getAbsolutePath());
            System.out.println(message);

            String newFilePath = String.format("%s\\%s", archiveDirectory.getAbsolutePath(), file.getName());
            file.renameTo(new File(newFilePath));
        }

        System.out.println("\nMoveToArchive Complete");
        return RepeatStatus.FINISHED;
    }
}
