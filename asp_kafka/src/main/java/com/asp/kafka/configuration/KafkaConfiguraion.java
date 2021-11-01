package com.asp.kafka.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.asp.common.AppStartupConstants;
import com.asp.message.KafkaMessageDetails;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@PropertySource("classpath:KafkaConfiguration.properties")
public class KafkaConfiguraion {
	@Autowired
	Environment env;

	@Bean
	public ConsumerFactory<String, KafkaMessageDetails> kafkaJobDetailsConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		String bootstrap_server = env.getProperty(AppStartupConstants.BOOTSTRAP_SERVER_URL);
		String group_id_config = env.getProperty(AppStartupConstants.GROUP_ID);
		String max_poll_records = env.getProperty(AppStartupConstants.MAX_POLL_RECORDS);
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_server);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, group_id_config);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, max_poll_records);

		ErrorHandlingDeserializer2<KafkaMessageDetails> errorHandlingDeserializer = new ErrorHandlingDeserializer2<>(
				new JsonDeserializer<>(KafkaMessageDetails.class, objectMapper()));

		configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "9000");

		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), errorHandlingDeserializer);

	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, KafkaMessageDetails> KafkaListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, KafkaMessageDetails> factory = new ConcurrentKafkaListenerContainerFactory<>();
		int timeout = Integer.parseInt(env.getProperty(AppStartupConstants.POLL_TIMEOUT));
		factory.setConsumerFactory(kafkaJobDetailsConsumerFactory());
		// factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(timeout);
		factory.setBatchListener(true);
		return factory;
	}

	private ObjectMapper objectMapper() {
		return Jackson2ObjectMapperBuilder.json().visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
				.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).build();
	}

}
