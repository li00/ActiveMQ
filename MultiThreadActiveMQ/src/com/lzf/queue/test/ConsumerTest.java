package com.lzf.queue.test;

import com.lzf.queue.consumer.DNCondumerImp;
import com.lzf.queue.consumer.INDConsumer;

/**
 * Created by Administrator on 2017/4/2.
 */
public class ConsumerTest{
    public static void main(String[] args) {
        INDConsumer indConsumer = new DNCondumerImp();
        indConsumer.init();
        ConsumerTest consumerTest = new ConsumerTest();
        //创建五个线程
        new Thread(consumerTest.new MyThread(indConsumer)).start();
        new Thread(consumerTest.new MyThread(indConsumer)).start();
        new Thread(consumerTest.new MyThread(indConsumer)).start();
        new Thread(consumerTest.new MyThread(indConsumer)).start();
        new Thread(consumerTest.new MyThread(indConsumer)).start();
    }


    class MyThread implements Runnable{
        private INDConsumer indConsumer;

        public MyThread(INDConsumer indConsumer){
            this.indConsumer=indConsumer;
        }

        @Override
        public void run() {
            while (true){
                indConsumer.getMessage("DN-LI-20");
            }
        }

    }
}
