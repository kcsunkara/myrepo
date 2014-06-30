#!/bin/sh

#Shared script for cleaning the logs that are older than 7 days / X minutes.
MYNAME=`uname -n`
MYLOGFILE="`echo ~`/kcsunkara/data/logs/cleanlogs_job_execution_shared.log"
EXECTIME=`date +"%Y-%m-%d_%H-%M-%S"`

echo "Processing CLEANLOGS Shared Script for $EXECTIME from $MYNAME" >> $MYLOGFILE

# -----------------------------------------------------------------------
# For all nodes in "linuxdrupal-1.vpc.ctsdamlabs.com" with shared storage: /home/ec2-user/kcsunkara/data/legacy
# -----------------------------------------------------------------------
if [ "$MYNAME" == "linuxdrupal-1.vpc.ctsdamlabs.com" ]; then
  echo "Running CLEANLOGS scripts for $MYNAME"
     LOGS="/home/ec2-user/kcsunkara/data/logs"
     #nohup find "$LOGS" -mtime +"$1" -name "*.*" -exec rm -f {} \; 2>/dev/null &
     nohup find "$LOGS" -mmin +"$1" -name "*.*" -exec rm -f {} \; 2>/dev/null &
     echo "Executed CLEANLOGS Shared Script for $EXECTIME from $MYNAME" >> $MYLOGFILE
  exit
fi

echo "HOST NOT FOUND $EXECTIME from $MYNAME" >> $MYLOGFILE

