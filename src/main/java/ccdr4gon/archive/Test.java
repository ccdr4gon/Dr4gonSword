package ccdr4gon.archive;

import ccdr4gon.Load;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Exception{


        byte[] load= new ByteBuddy()
                .redefine(Load.class)
                .name("asd")
                .field(ElementMatchers.named("pass")).value("asdsadasdsad")
                .make()
                .getBytes();

        Method defineClass = Class.forName("java.lang.ClassLoader").getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        defineClass.setAccessible(true);

        Class test =(Class) defineClass.invoke(Thread.currentThread().getContextClassLoader(), load, 0, load.length);
        Field f=test.getDeclaredField("pass");
        System.out.println(f.get(null));

        }
}
