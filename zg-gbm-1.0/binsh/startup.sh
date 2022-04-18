APP_ROOT=$HOME/service/aov

nohup java -Xms512m -Xmx2048m -Dhccp.platform.container.admin.port=10999 -Dspring.profiles.active="etcd,ops-module-statistics-enabled" -classpath $APP_ROOT:$APP_ROOT/*:$APP_ROOT/lib/*:$APP_ROOT/lib/tp/*:$APP_ROOT/lib/app/*:$APP_ROOT/run:$APP_ROOT/run/linux org.noc.hccp.platform.service.Run 1>/dev/null 2>$APP_ROOT/log/stderr.out &
echo "SERVICE START"
