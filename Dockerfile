#java
FROM anapsix/alpine-java 

MAINTAINER blerterTeam

USER root

#create dir
RUN mkdir -p /blerter/datanucleus

#set dir
WORKDIR /blerter

#copy data datanucleus

#copy
COPY datanucleus/datanucleus-api-jdo-5.1.1.jar /opt/jdk/jre/lib/ext
COPY datanucleus/datanucleus-core-5.1.1.jar /opt/jdk/jre/lib/ext
COPY datanucleus/datanucleus-rdbms-5.1.1.jar /opt/jdk/jre/lib/ext
COPY datanucleus/HikariCP-2.6.3.jar /opt/jdk/jre/lib/ext
COPY datanucleus/javax.jdo-3.2.0-m7.jar /opt/jdk/jre/lib/ext
COPY datanucleus/log4j-1.2.17.jar /opt/jdk/jre/lib/ext
COPY datanucleus/slf4j-api-1.7.25.jar /opt/jdk/jre/lib/ext
#COPY datanucleus/slf4j-log4j12-1.7.25.jar /opt/jdk/jre/lib/ext

COPY target/blerter-master-service.jar .

#export port
EXPOSE 4010 4012

#deploy
CMD java -Xms1024m -Xmx1024m -Dorg.apache.coyote.http11.Http11Protocol.COMPRESSION=on  -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=20 -XX:ConcGCThreads=5 -XX:InitiatingHeapOccupancyPercent=70 -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -server -jar blerter-master-service.jar
