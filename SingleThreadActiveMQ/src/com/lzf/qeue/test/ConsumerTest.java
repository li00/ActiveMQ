package com.lzf.qeue.test;

import com.lzf.qeue.consumer.DNCusumerImp;
import com.lzf.qeue.consumer.IDNConsumer;

/**
 * Created by Administrator on 2017/4/2.
 */
public class ConsumerTest {
    public static void main(String[] args) {
        IDNConsumer idnConsumer = new DNCusumerImp();
        idnConsumer.init();
        //生产者与消费者的disName要一致
        idnConsumer.getMessage("DN-LI-10");
    }
}
