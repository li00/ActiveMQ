package com.lzf.qeue2.consumer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**消息队列监控端口是http://localhost:8161/密码admin/用户名admin
 * Created by Administrator on 2017/4/2.
 */
public class DNCusumerImp implements IDNConsumer {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;

    //初始化ActiveMQ
    @Override
    public void init() {
        try {
            //创建连接
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);

            connection = connectionFactory.createConnection();
            connection.start();
            //创建事务,消费消息是没有事物的，所以值为false,且未自动应答
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMessage(String disName) {
        try {
            Queue queue = session.createQueue(disName);
            MessageConsumer messageConsumer = session.createConsumer(queue);

            while (true){
                //创建接收内容对像
                TextMessage textMessage = (TextMessage) messageConsumer.receive();
                Thread.sleep(10);
                //消费消息之后要给生产者一个应答
                textMessage.acknowledge();
                if(textMessage != null){
                    System.out.println("Consumer:接收内容是："+textMessage.getText());
                }else{
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
