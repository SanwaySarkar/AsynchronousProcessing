package com.asp.kafka.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.asp.common.AppStartupConstants;

@Configuration
@PropertySource("classpath:KafkaAsynchThreadPool.properties")
public class KafkaTasksExecutorSpringConfig {

	@Autowired
	Environment env;

	@Bean(name = "EYKafkaExecPool")
	public ThreadPoolTaskExecutor getInstance() {

		int queueCapacity = Integer.valueOf(env.getProperty(AppStartupConstants.QUEUE_CAPACITY));
		int corePoolSize = Integer.valueOf(env.getProperty(AppStartupConstants.ASYNC_CORE_POOL_SIZE));
		int maxPoolSize = Integer.valueOf(env.getProperty(AppStartupConstants.ASYNC_MAX_POOL_SIZE));
		String threadNamePrefix = env.getProperty(AppStartupConstants.THREAD_NAME_PREFIX);
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix(threadNamePrefix);
		executor.initialize();
		return executor;
	}

}
