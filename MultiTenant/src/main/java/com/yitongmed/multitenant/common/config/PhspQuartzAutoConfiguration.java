package com.yitongmed.multitenant.common.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties(QuartzProperties.class)
public class PhspQuartzAutoConfiguration {
    @Bean(name = "masterScheduler")
    public SchedulerFactoryBean quartzScheduler(QuartzProperties properties,
                                                ObjectProvider<JobDetail> jobDetails,
                                                ObjectProvider<Trigger> triggers,
                                                ApplicationContext applicationContext,
                                                @Qualifier("primaryDataSource") DataSource master,
                                                ObjectProvider<PlatformTransactionManager> transactionManager) {
        return getSchedulerFactoryBean(properties, jobDetails, triggers, applicationContext, master);
    }

    @Bean(name = "tenant1Scheduler")
    public SchedulerFactoryBean tenant1Scheduler(QuartzProperties properties,
                                                 ObjectProvider<JobDetail> jobDetails,
                                                 ObjectProvider<Trigger> triggers,
                                                 ApplicationContext applicationContext,
                                                 @Qualifier("tenant1DataSource") DataSource master,
                                                 ObjectProvider<PlatformTransactionManager> transactionManager) {
        return getSchedulerFactoryBean(properties, jobDetails, triggers, applicationContext, master);
    }

    private SchedulerFactoryBean getSchedulerFactoryBean(QuartzProperties properties,
                                                         ObjectProvider<JobDetail> jobDetails,
                                                         ObjectProvider<Trigger> triggers,
                                                         ApplicationContext applicationContext,
                                                         DataSource master) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory(jobFactory);
        if (properties.getSchedulerName() != null) {
            schedulerFactoryBean.setSchedulerName(properties.getSchedulerName());
        }
        schedulerFactoryBean.setAutoStartup(properties.isAutoStartup());
        schedulerFactoryBean.setStartupDelay((int) properties.getStartupDelay().getSeconds());
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(properties.isWaitForJobsToCompleteOnShutdown());
        schedulerFactoryBean.setOverwriteExistingJobs(properties.isOverwriteExistingJobs());
        if (!properties.getProperties().isEmpty()) {
            schedulerFactoryBean.setQuartzProperties(asProperties(properties.getProperties()));
        }
        schedulerFactoryBean.setJobDetails(jobDetails.orderedStream().toArray(JobDetail[]::new));
        schedulerFactoryBean.setTriggers(triggers.orderedStream().toArray(Trigger[]::new));
        schedulerFactoryBean.setDataSource(master);
        return schedulerFactoryBean;
    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }


}
