## log4j ��־û�����

��ΪһЩ*���淶�Ķ������ڸ�����Ĭ�� log4j.properties ����*, �� emayclient-cooperation-4.2.15.jar, Ϊ�˱���Ӧ��������ص�����ȷ�� log4j ����, ali-tomcat Ĭ�Ͻ�ֹ log4j �Զ�����Ĭ�� log4j ����, ���� web.xml ����� Log4jConfigListener �ֶ�ָ�� log4j.xml ����.

�� web.xml �ļ�����ǰ����������������� (�뽫 WEB-INF/log4j.xml �滻ΪӦ��ʵ��ʹ�õ� log4j �����ļ�·��):

    <listener>
        <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
    </listener>
    <context-param>
        <param-name>log4jConfigLocation</param-name>
        <param-value>WEB-INF/log4j.xml</param-value>
    </context-param>

