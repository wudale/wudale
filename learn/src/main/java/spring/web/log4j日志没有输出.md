## log4j 日志没有输出

因为一些*不规范的二方包内附带了默认 log4j.properties 配置*, 如 emayclient-cooperation-4.2.15.jar, 为了避免应用意外加载到不正确的 log4j 配置, ali-tomcat 默认禁止 log4j 自动加载默认 log4j 配置, 请在 web.xml 中添加 Log4jConfigListener 手动指定 log4j.xml 配置.

在 web.xml 文件的最前面添加类似如下配置 (请将 WEB-INF/log4j.xml 替换为应用实际使用的 log4j 配置文件路径):

    <listener>
        <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
    </listener>
    <context-param>
        <param-name>log4jConfigLocation</param-name>
        <param-value>WEB-INF/log4j.xml</param-value>
    </context-param>

