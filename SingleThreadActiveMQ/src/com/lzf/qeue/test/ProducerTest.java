package com.lzf.qeue.test;

import com.lzf.qeue.producer.DNProduserImp;
import com.lzf.qeue.producer.IDNProduser;

/**
 * Created by Administrator on 2017/4/2.
 */
public class ProducerTest {
    public static void main(String[] args) {
        IDNProduser idnProduser = new DNProduserImp();
        idnProduser.init();
        //生产者与消费者的内容要一致
        idnProduser.sendMessage("DN-LI-10");
    }
}
