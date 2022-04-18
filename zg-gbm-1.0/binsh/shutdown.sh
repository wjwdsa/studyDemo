kill -9 `ps -aef | grep org.noc.hccp.platform.service.Run | awk '{if($8!~/grep/) print $2}'`
