package com.lzf.qeue.test;

import com.lzf.qeue2.consumer.DNCusumerImp;
import com.lzf.qeue2.consumer.IDNConsumer;

/**
 * Created by Administrator on 2017/4/2.
 */
public class ConsumerTest2 {
    public static void main(String[] args) {
        IDNConsumer idnConsumer = new DNCusumerImp();
        idnConsumer.init();
        //生产者与消费者的disName要一致
        idnConsumer.getMessage("DN-LI-10");
    }
}
