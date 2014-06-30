#!/bin/sh

#Shared script for running MD5 jobs.

MYNAME=`uname -n`
SCRIPT_NAME="createMD5WSClient.sh"
MYLOGFILE="`echo ~`/kcsunkara/data/logs/md5_job_execution_shared.log"
EXECTIME=`date +"%Y-%m-%d_%H-%M-%S"`

echo "Processing MD5 Shared Script for $EXECTIME from $MYNAME" >> $MYLOGFILE

# -----------------------------------------------------------------------
# For all nodes in "linuxdrupal-1.vpc.ctsdamlabs.com" with shared storage: /home/ec2-user/kcsunkara/data/..
# -----------------------------------------------------------------------
if [ "$MYNAME" = "linuxdrupal-1.vpc.ctsdamlabs.com" ]; then
  echo "Running md5 scripts for $MYNAME"
     SCRIPT_ROOT="/home/ec2-user/kcsunkara/scripts/ws_scripts"
     LOGFILE="/home/ec2-user/kcsunkara/data/logs/md5logs"

     export STORAGE_ID=11
     nohup "$SCRIPT_ROOT/$SCRIPT_NAME" "$LOGFILE" $STORAGE_ID >> "$LOGFILE/$MYNAME.$STORAGE_ID.$EXECTIME.$$.out" 2>&1 &
     echo "Executed MD5 Shared Script for $EXECTIME from $MYNAME:$STORAGE_ID" >> $MYLOGFILE

	sleep 10	 
	 export STORAGE_ID=21
     nohup "$SCRIPT_ROOT/$SCRIPT_NAME" "$LOGFILE" $STORAGE_ID >> "$LOGFILE/$MYNAME.$STORAGE_ID.$EXECTIME.$$.out" 2>&1 &
     echo "Executed MD5 Shared Script for $EXECTIME from $MYNAME:$STORAGE_ID" >> $MYLOGFILE

	sleep 10
	 export STORAGE_ID=31
     nohup "$SCRIPT_ROOT/$SCRIPT_NAME" "$LOGFILE" $STORAGE_ID >> "$LOGFILE/$MYNAME.$STORAGE_ID.$EXECTIME.$$.out" 2>&1 &
     echo "Executed MD5 Shared Script for $EXECTIME from $MYNAME:$STORAGE_ID" >> $MYLOGFILE
	 
	sleep 10
	 export STORAGE_ID=41
     nohup "$SCRIPT_ROOT/$SCRIPT_NAME" "$LOGFILE" $STORAGE_ID >> "$LOGFILE/$MYNAME.$STORAGE_ID.$EXECTIME.$$.out" 2>&1 &
     echo "Executed MD5 Shared Script for $EXECTIME from $MYNAME:$STORAGE_ID" >> $MYLOGFILE
	 
	sleep 10
	 export STORAGE_ID=42
     nohup "$SCRIPT_ROOT/$SCRIPT_NAME" "$LOGFILE" $STORAGE_ID >> "$LOGFILE/$MYNAME.$STORAGE_ID.$EXECTIME.$$.out" 2>&1 &
     echo "Executed MD5 Shared Script for $EXECTIME from $MYNAME:$STORAGE_ID" >> $MYLOGFILE

        sleep 10
         export STORAGE_ID=51
     nohup "$SCRIPT_ROOT/$SCRIPT_NAME" "$LOGFILE" $STORAGE_ID >> "$LOGFILE/$MYNAME.$STORAGE_ID.$EXECTIME.$$.out" 2>&1 &
     echo "Executed MD5 Shared Script for $EXECTIME from $MYNAME:$STORAGE_ID" >> $MYLOGFILE

  exit
fi

echo "HOST NOT FOUND $EXECTIME from $MYNAME" >> $MYLOGFILE

