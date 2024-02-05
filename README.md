download the zipkin server 3.0.5 or some lates version
run the zipkin server jar using below command
java -jar zipkin-server-3.0.0-exec.jar

add in our microservices application.yml file 
  management:
   tracing:
    sampling:
      probability: 1.0
