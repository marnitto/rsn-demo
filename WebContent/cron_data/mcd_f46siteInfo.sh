#!/bin/sh
date
echo $SHELL;
export CLASSPATH=/apps/mcd/WEB-INF/lib/sqljdbc.jar:/apps/mcd/WEB-INF/lib/mysql-connector-java-5.1.5-bin.jar:/apps/mcd/WEB-INF/lib/servlet-api.jar:/apps/mcd/WEB-INF/classes;
export LANG=ko_KR.eucKR;
pid=`ps -ef | grep mcd_f46stieInfo | wc -l`;
echo $pid;
if [ $pid -lt 3 ] 
  then
     echo "MCDONALD SiteInfo Transfer process is not active!!! "
     /usr/local/j2sdk1.4.2_12/bin/java risk.demon.f46_site_info;
  else
     echo "MCDONALD SiteInfo Transfer process is Active!!! ";
fi
