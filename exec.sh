
#!/bin/bash

project=sirwhitemonkey
appName=blerter-master
tag=test

if [ "$1" != "" ] && [ "$2" != "" ]; then

 case "$2" in
         build)
        if [ "$3" != "" ]; then
        	   tag=$3
        	   
        	   docker build --no-cache -t "${appName}" .
           docker tag "${appName}" "${project}/${appName}:${tag}"
           docker push "${project}/${appName}:${tag}"
        
        else
           docker build --no-cache -t "${appName}" .
           docker tag "${appName}" "${project}/${appName}"
           docker push "${project}/${appName}"
           
        fi

        ;;
        start)
        
        export BLERTER_DB_HOST=blerter-db.$1.svc.cluster.local
        
        if [ "$3" != "" ]; then
        	  tag=$3
        	  imageTag="${project}/${appName}:${tag}"
           sed -i.bak "s#${project}/${appName}#${imageTag}#" config.yaml
           kubectl create -f config.yaml --namespace=$1
           imageTag="${project}/${appName}"
           sed -i.bak "s#${project}/${appName}:${tag}#${imageTag}#" config.yaml
           rm config.yaml.bak
        
        else
          kubectl create -f config.yaml --namespace=$1 
        fi
        
          kubectl patch deployment -n $1 blerter-master -p \
                         '{"spec": {"template": {"spec":{"containers":[{"name": "blerter-master", "env":[{"name": "BLERTER_DB_HOST", "value": '\"$BLERTER_DB_HOST\"'}]}]}}}}'
        ;;
        stop)
        kubectl delete -f config.yaml --namespace=$1 --grace-period=0

        ;;
        *)
        echo "Usage: {start|stop|build}"
        ;;
    esac

else
    echo "Error: syntax <namespace> <build|start|stop> <tag>"
fi
