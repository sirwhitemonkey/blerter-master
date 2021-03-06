#!/bin/sh

export BLERTER_DB_HOST=localhost
export BLERTER_DB_PORT=5433
export BLERTER_DB_NAME=blerter
export BLERTER_DB_USER=blerter
export BLERTER_DB_PASSWORD=blerter
export BLERTER_DB_DRIVER=org.postgresql.Driver

case "$1" in
		start)
		
		# copy
		#echo "copying datanucleus ..."
		#sudo cp datanucleus/* "${JAVA_HOME}"/jre/lib/ext

		java -Xms1024m -Xmx1024m -XX:NewRatio=4 -Dorg.apache.coyote.http11.Http11Protocol.COMPRESSION=on -Dorg.jboss.resolver.jarning=true -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=20 -XX:ConcGCThreads=5 -XX:InitiatingHeapOccupancyPercent=70 -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000  -jar target/blerter-master-service.jar > service.log &
		;;
		stop)
		pkill -f "blerter-master-service.jar"
		;;
		log)
		tail -f service.log
		;;
		*)
		echo "Usage: {start|stop|log}"
		;;
esac
		

