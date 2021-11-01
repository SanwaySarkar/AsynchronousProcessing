package com.asp.kafka.listener;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.asp.common.Message;
import com.asp.common.MessageProcessorFactory;
import com.asp.message.KafkaMessageDetails;
import com.asp.message.processor.RunnableTaskProcessor;
import com.asp.message.processor.TaskProcessor;

@Service

public class KafkaConsumer {

	@Autowired
	MessageProcessorFactory messageProcessorFactory;

	@Autowired
	Environment env;
	

	@Value("${topicKafka}")
	private String topicName;

	@Autowired
	@Qualifier("EYKafkaExecPool")
	private ThreadPoolTaskExecutor execPool;

	private static final Logger log = Logger.getLogger(KafkaConsumer.class);

	@KafkaListener(id = "listMsg", topics = "asp_kafka", containerFactory = "KafkaListenerFactory")

	public void listen(List<ConsumerRecord<?, KafkaMessageDetails>> list)  {

		log.info("on each poll msg received " + list.size());
		for (ConsumerRecord<?, KafkaMessageDetails> job : list) {
			log.info("Consumed  Message: " + job);
			try {
				String jobMsg = job.value().getMessage();
				String userName = job.value().getUserName();
				String groupCode = job.value().getGroupCode();
				String messageType = job.value().getJobCategory();
				String id = getUniqueId(job.partition(), job.offset());
				log.info("generated unique id " + id);

				Message message = new Message(id, jobMsg, groupCode, userName, messageType);
				TaskProcessor processor = null;
				try {
					processor = messageProcessorFactory.getMessageTaskProcessor(message);
					execPool.execute(new RunnableTaskProcessor(processor, message));
				}

				catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				log.info("error due to invalid data in topic " + job.topic() + " partition " + job.partition()
				+ " offset " + job.offset());
				//e.printStackTrace();
			}

		}
	}
	

	private String getUniqueId(int partition, long offset) {
		String id = topicName + partition + offset;
		return id;

	}

}
