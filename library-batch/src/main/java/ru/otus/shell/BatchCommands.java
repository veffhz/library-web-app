package ru.otus.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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

    @Autowired
    public BatchCommands(JobLauncher jobLauncher, Job csvFileToDatabaseJob) {
        this.jobLauncher = jobLauncher;
        this.csvFileToDatabaseJob = csvFileToDatabaseJob;
    }

    @ShellMethod(value = "Run import csv.", key = "import-csv")
    public String importCsv() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

       // @ShellOption(defaultValue="/raw_data-20160416T1130.csv") String csvFile,
       // @ShellOption(defaultValue=";") String delimiter

       // JobParametersBuilder jobBuilder= new JobParametersBuilder();
       // jobBuilder.addString("csvFile", csvFile);
       // jobBuilder.addString("delimiter", delimiter);
       // JobParameters jobParameters =jobBuilder.toJobParameters();

        jobLauncher.run(csvFileToDatabaseJob, new JobParameters());
        return "Done!";
    }

    @ShellMethod(value = "Run migrate jpa.", key = "migrate-jpa")
    public String migrateJpa() {
        StringBuilder sb = new StringBuilder();
        sb.append("TEST");
        sb.append("\n");
        return sb.toString();
    }

}
