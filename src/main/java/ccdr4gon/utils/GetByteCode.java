package ccdr4gon.utils;

import ccdr4gon.Load;


import net.bytebuddy.ByteBuddy;
import java.util.Base64;

public class GetByteCode {
    public GetByteCode() throws ClassNotFoundException, NoSuchMethodException {
    }
    public static void main(String[] args) throws Exception {
            byte[] code= new ByteBuddy()
            .redefine(ccdr4gon.utils.Dr4gonListener.class)
            .name("ccdr4gon.utils.Dr4gonListener")
            .make()
            .getBytes();
        System.out.println(Base64.getEncoder().encodeToString(code));
    }






}
