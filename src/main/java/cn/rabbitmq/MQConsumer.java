package cn.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class MQConsumer implements Consumer {
	private static final Logger log = LoggerFactory.getLogger(Produce.class);
	private final Channel channel;
	// 这里如果用autoAck为true，则消费完自动确认。否则一定要手动确认。
	private static boolean autoAck = true;
	// 重新回队列
	private static boolean reBackqueue = true;

	public MQConsumer(Channel channel) {
		super();
		this.channel = channel;
	}

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(MQConstant.QUEUE_NAME, true, false, false, null);
			// 自动回复队列应答，不然一个消息可以被重复消费
			String basicConsume = channel.basicConsume(MQConstant.QUEUE_NAME, autoAck, new MQConsumer(channel));
			System.out.println(basicConsume);
		} catch (Exception e) {
			log.error("发送出错：", e);
		} finally {
			// 因为需要一直监听消息，所以此处不能关闭连接和通道，关闭后就不能再消费下一条消息了
			/*
			 * if (channel != null) { channel.close(); } if (connection != null)
			 * { connection.close(); }
			 */
		}

	}

	@Override
	public void handleConsumeOk(String consumerTag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleCancelOk(String consumerTag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleCancel(String consumerTag) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] message_body)
			throws IOException {
		boolean success = true;
		String message_id = properties.getCorrelationId();
		try {
			String message = new String(message_body, "UTF-8");
			System.out.println("[Consumer] Received:'" + message + "'");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!autoAck) {
				if (success) {
					channel.basicAck(envelope.getDeliveryTag(), false);
					log.info("rabbitmq消费成功。message_id={},consumerTag={},exchangeName={},routingKey={}", message_id,
							consumerTag, envelope.getExchange(), envelope.getRoutingKey());
				} else {
					log.error(
							"rabbitmq消费失败,消息Nack[requeue={}]。message_id={},consumerTag={},exchangeName={},routingKey={}",
							reBackqueue, message_id, consumerTag, envelope.getExchange(), envelope.getRoutingKey());
					channel.basicNack(envelope.getDeliveryTag(), false, reBackqueue);
				}
			}
		}
	}

	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleRecoverOk(String consumerTag) {
		// TODO Auto-generated method stub

	}
}
