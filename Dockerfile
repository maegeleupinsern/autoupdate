FROM naturecloud.io/library/tomcat:v7.0.55
RUN mkdir -p /tomcat/webapps/update
ADD WebContent/ /tomcat/webapps/update
RUN mkdir -p /tomcat/webapps/update/WEB-INF/classes
ADD src/ /tomcat/webapps/update/WEB-INF/classes
RUN javac -cp /tomcat/webapps/update/WEB-INF/lib/commons-beanutils-1.7.0.jar:/tomcat/webapps/update/WEB-INF/lib/commons-collections-3.2.1.jar:/tomcat/webapps/update/WEB-INF/lib/commons-lang-2.3.jar:/tomcat/webapps/update/WEB-INF/lib/commons-lang-2.3.jar:/tomcat/webapps/update/WEB-INF/lib/ezmorph-1.0.3.jar:/tomcat/webapps/update/WEB-INF/lib/json-lib-2.2.3-jdk15.jar:/tomcat/webapps/update/WEB-INF/lib/log4j-1.2.17.jar:/tomcat/webapps/update/WEB-INF/lib/mongo-java-driver-2.13.2.jar:/tomcat/webapps/update/WEB-INF/lib/javax.servlet-api-3.1.0.jar /tomcat/webapps/update/WEB-INF/classes/com/naturecloud/util/*.java 
