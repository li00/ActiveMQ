package com.lzf.qeue.producer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**消息队列监控端口是http://localhost:8161/密码admin/用户名admin
 * Created by Administrator on 2017/4/2.
 */
public class DNProduserImp implements IDNProduser {
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
            //创建事务
            session = connection.createSession(true,Session.SESSION_TRANSACTED);
        }catch (JMSException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String disName) {
        try {
            Queue queue = session.createQueue(disName);
            //实例化生产者对象
            MessageProducer messageProducer = session.createProducer(queue);
            for (int i=0; i<1000; i++){
                //创建一个消息对象
                TextMessage textMessage = session.createTextMessage("生产者的消息:短信内容是content"+i);
                System.out.println(textMessage);
                //发送消息
                messageProducer.send(textMessage);
                //消息提交
                session.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
