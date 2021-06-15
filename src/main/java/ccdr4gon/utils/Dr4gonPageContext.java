package ccdr4gon.utils;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

import javax.el.ELContext;
import javax.servlet.*;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import java.io.IOException;
import java.util.Enumeration;


//冰蝎要用的PageContext
public class Dr4gonPageContext extends PageContext {
    public Request request;
    public HttpSession session;
    public Response response;
    public Dr4gonPageContext(Request request, Response response, HttpSession session){
        this.session=session;
        this.request=request;
        this.response=response;
    }
    @Override
    public void initialize(Servlet servlet, ServletRequest servletRequest, ServletResponse servletResponse, String s, boolean b, int i, boolean b1) throws IOException, IllegalStateException, IllegalArgumentException {
    }

    @Override
    public void release() {

    }

    @Override
    public HttpSession getSession() {
        return (HttpSession) this.session;
    }

    @Override
    public Object getPage() {
        return null;
    }

    @Override
    public ServletRequest getRequest() {
        return this.request;
    }

    @Override
    public ServletResponse getResponse() {
        return this.response;
    }

    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void forward(String s) throws ServletException, IOException {

    }

    @Override
    public void include(String s) throws ServletException, IOException {

    }

    @Override
    public void include(String s, boolean b) throws ServletException, IOException {

    }

    @Override
    public void handlePageException(Exception e) throws ServletException, IOException {

    }

    @Override
    public void handlePageException(Throwable throwable) throws ServletException, IOException {

    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void setAttribute(String s, Object o, int i) {

    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Object getAttribute(String s, int i) {
        return null;
    }

    @Override
    public Object findAttribute(String s) {
        return null;
    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public void removeAttribute(String s, int i) {

    }

    @Override
    public int getAttributesScope(String s) {
        return 0;
    }

    @Override
    public Enumeration<String> getAttributeNamesInScope(int i) {
        return null;
    }

    @Override
    public JspWriter getOut() {
        return null;
    }

    @Override
    public ExpressionEvaluator getExpressionEvaluator() {
        return null;
    }

    @Override
    public VariableResolver getVariableResolver() {
        return null;
    }

    @Override
    public ELContext getELContext() {
        return null;
    }
}