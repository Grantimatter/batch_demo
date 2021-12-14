package com.grantwiswell.batch_demo;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class PrintFileContents implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("NotifyTask Start\n");

        try {
            File directoryPath = new File("./documents");

            File[] fileList = directoryPath.listFiles();
            Scanner scanner = null;
            for (File file : fileList) {
                if (!file.isFile())
                    continue;

                scanner = new Scanner(file);
                String input;
                StringBuffer stringBuffer = new StringBuffer();

                while (scanner.hasNextLine()) {
                    input = scanner.nextLine();
                    stringBuffer.append(input).append("\n");
                }

                String message = String.format("Reading %s...\n%s", file.getAbsolutePath(), stringBuffer);
                System.out.println(message);

                scanner.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("PrintFileContents failed, file not found");
        }

        System.out.println("PrintFileContents Complete");
        return RepeatStatus.FINISHED;
    }
}
