package com.lzf.queue.consumer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Administrator on 2017/4/2.
 */
public class DNCondumerImp implements INDConsumer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    ThreadLocal<MessageConsumer> tl = new ThreadLocal<>();

    @Override
    public void init() {
        connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKERURL);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMessage(String disNmae) {
        try {
            Queue queue = session.createQueue(disNmae);
           // MessageConsumer messageConsumer = session.createConsumer(queue);
            MessageConsumer messageConsumer;
            if(tl.get() != null){
                messageConsumer = tl.get();
            }else {
                messageConsumer = session.createConsumer(queue);
            }

            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive();
                textMessage.acknowledge();
                Thread.sleep(10);
                if(textMessage != null){
                    System.out.println("接受到的内容是："+textMessage.getText());
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
