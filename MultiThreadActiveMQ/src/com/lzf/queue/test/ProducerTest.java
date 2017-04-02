package com.lzf.queue.test;

import com.lzf.queue.producer.INDProducer;
import com.lzf.queue.producer.NDProducerImp;

/**
 * Created by Administrator on 2017/4/2.
 */
public class ProducerTest{

    class MyThread implements Runnable {
       public INDProducer indProducer;

       public MyThread(INDProducer indProducer){
           this.indProducer = indProducer;
       }
       @Override
       public void run() {
           while (true){
               indProducer.senMessage("DN-LI-20");
           }
       }
   }

    public static void main(String[] args) {
        INDProducer indProducer = new NDProducerImp();
        indProducer.init();
        ProducerTest producerTest = new ProducerTest();
        //创建五个线程
        new Thread(producerTest.new MyThread(indProducer)).start();
        new Thread(producerTest.new MyThread(indProducer)).start();
        new Thread(producerTest.new MyThread(indProducer)).start();
        new Thread(producerTest.new MyThread(indProducer)).start();
        new Thread(producerTest.new MyThread(indProducer)).start();
    }
}
