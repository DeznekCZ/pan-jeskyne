#!/bin/sh
mvn clean package
scp ./target/pan-jeskyne.war root@89.221.217.45:/var/lib/tomcat8/stage
ssh root@89.221.217.45 /var/lib/tomcat8/stage/deploy.sh
ssh root@89.221.217.45 tail -f /var/lib/tomcat8/logs/catalina.out
