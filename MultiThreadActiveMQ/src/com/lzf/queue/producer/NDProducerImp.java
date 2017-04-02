package com.lzf.queue.producer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Administrator on 2017/4/2.
 */
public class NDProducerImp implements INDProducer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    ThreadLocal<MessageProducer> tl = new ThreadLocal<>();
    ThreadLocal<Integer> threadCount = new ThreadLocal<>();//当前线程的第几个消息

    @Override
    public void init() {
        connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASWORD,BROKERURL);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true,Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void senMessage(String disName) {
        try {
            Queue queue = session.createQueue(disName);
           // MessageProducer messageProducer = session.createProducer(queue);
            MessageProducer messageProducer;
            if(tl.get() != null){
                messageProducer = tl.get();
            }else {
                messageProducer = session.createProducer(queue);
            }

            Integer count = threadCount.get();

            for(int i=0;i<1000;i++){

                if (count == null){
                    count = 0;
                }
                count++;

                TextMessage textMessage = session.createTextMessage(Thread.currentThread().getName()
                        +"生产者的消息:短信内容是content"+count);
                threadCount.set(count);
                System.out.println(textMessage);
                messageProducer.send(textMessage);
                session.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
