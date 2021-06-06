# Dr4gon's Sword
- [Dr4gon's Sword](#dr4gon-s-sword)
  * [Shiro](#shiro)
    + [使用方法](#----)
    + [利用链](#---)
    + [内存马和回显方法](#--------)
    + [本地测试过的中间件版本](#-----------)
- [未来准备更新(可能)](#----------)

版本0.0.1,仅有Shiro,而且功能不完善,百废待兴,JAVA入门没多久，写得不好请见谅

## Shiro

### 使用方法

连接冰蝎的时候加get参数stage=s

![2](2.png)

execute command之前要先点Inject进行注入

![1](1.png)

### 利用链

Commons-CollectionsK1_1 和K1的区别基本上只是我用的是InstantiateTransformer,K1用的是InvokerTransformer(大概是CC3和CC6分别缝合TemplatesImpl以后的区别)

总结:几乎一样

### 内存马和回显方法

使用listener注入,相比于[j1anFen](https://github.com/j1anFen)师傅用的servlet来说,不会被shiro拦,只要tomcat能解析的路径就能上线内存马,不用怕shirofilter匹配*的情况

并且defineclass直接加载了pageContext类(有的时候没有依赖导致注入失败)

另外我这边同一个listener同时实现命令执行回显和内存马,命令执行回显可以GBK编码

### 本地测试过的中间件版本

Tomcat 8.5.51 √

Springboot 2.3.5 √  似乎对应 tomcat-embed-core 9.0.39

# 未来准备更新(可能)

- 把类名改了,总感觉会有0.1%的可能性会被溯源
- shiro
  - 自定义Behinder的pass(现在还只能是默认的rebeyond,太惨了)
  - aes gcm加密方式(说实话也不知道有没有用)
  - commons-collections4的利用链

- fastjson

- weblogic等等更多以后准备学习的JAVA漏洞
- okhttp库response的获取有点迷,似乎只能获取一次,目前处于能用就行的丑陋状态,之后看看怎么改改

