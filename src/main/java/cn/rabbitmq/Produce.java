package cn.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

//@Slf4j
/**
 * rabbitmq安装教程，可访问：http://1181731633.iteye.com/blog/2412783
 * 
 * @author Administrator
 *
 */
public class Produce {
	private static final AtomicInteger channelNum = new AtomicInteger(1);
	private static final Logger log = LoggerFactory.getLogger(Produce.class);

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = null;
		try {
			// 获取连接通道
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			int num = 3;
			while (num > 0) {
				num--;
				new Thread(new ProduceMsg(connection)).start();
			}
			
			// 暂停当前线程，等待消息发送完成
			Thread.sleep(10000L);
		} catch (Exception e) {
			log.error("发送出错：", e);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	static class ProduceMsg implements Runnable {
		private final Connection connection;

		public ProduceMsg(Connection connection) {
			this.connection = connection;
		}

		@Override
		public void run() {
			Channel channel = null;
			try {
				// 每次建立新的通道，是因为不同线程访问，可能有线程安全。
				channel = connection.createChannel(channelNum.getAndIncrement());
				// 声明队列(非必要代码)，注意这里的配置要和已建立的交换器一致，不然会报错。durable-持久化
				// channel.queueDeclare(MQConstant.QUEUE_NAME, true, false, false, null);
				String message = "Hello World!";
				channel.basicPublish(MQConstant.EXCHANGE_NAME, MQConstant.RoutingKey, null, message.getBytes("UTF-8"));
				System.out.println("[Produce] Sent:'" + message + "'");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (channel != null) {
					try {
						channel.close();
					} catch (IOException | TimeoutException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
