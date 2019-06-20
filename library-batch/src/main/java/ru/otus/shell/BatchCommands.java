package ru.otus.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BatchCommands {

    private final JobLauncher jobLauncher;

    private final Job csvFileToDatabaseJob;
    private final Job jpaEntitiesToMongoJob;

    @Autowired
    public BatchCommands(JobLauncher jobLauncher, Job csvFileToDatabaseJob, Job jpaEntitiesToMongoJob) {
        this.jobLauncher = jobLauncher;
        this.csvFileToDatabaseJob = csvFileToDatabaseJob;
        this.jpaEntitiesToMongoJob = jpaEntitiesToMongoJob;
    }

    @ShellMethod(value = "Run import csv.", key = "import")
    public String importCsv() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        jobLauncher.run(csvFileToDatabaseJob, new JobParametersBuilder()
                .addLong("uniqueness", System.nanoTime()).toJobParameters());
        return "Done!";
    }

    @ShellMethod(value = "Run migrate jpa.", key = "migrate")
    public String migrateJpa() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        jobLauncher.run(jpaEntitiesToMongoJob, new JobParametersBuilder()
                .addLong("uniqueness", System.nanoTime()).toJobParameters());
        return "Done!";
    }

}
