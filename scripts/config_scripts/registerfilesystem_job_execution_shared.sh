#!/bin/sh
#Shared script for running REGISTERFILESYSTEM jobs.

MYNAME=`uname -n`
SCRIPT_NAME="registerNewAssetFS.sh"
MYLOGFILE="`echo ~`/kcsunkara/data/logs/registerfilesystem_job_execution_shared.log"
EXECTIME=`date +"%Y-%m-%d_%H-%M-%S"`

echo "Processing REGISTERFILESYSTEM Shared Script for $EXECTIME from $MYNAME" >> $MYLOGFILE

# -----------------------------------------------------------------------
# For all nodes in "linuxdrupal-1.vpc.ctsdamlabs.com" with shared storage: /home/ec2-user/kcsunkara/data/..
# -----------------------------------------------------------------------
if [ "$MYNAME" == "linuxdrupal-1.vpc.ctsdamlabs.com" ]; then
  echo "Running REGISTERFILESYSTEM scripts for $MYNAME"
    SCRIPT_ROOT="/home/ec2-user/kcsunkara/scripts/ws_scripts"
    WATCHPATH="/home/ec2-user/kcsunkara/data/bulkupload/data"
    LOGFILE="/home/ec2-user/kcsunkara/data/logs/bulkuploadlogs"

     export STORAGE_ID=51
     nohup "$SCRIPT_ROOT/$SCRIPT_NAME" "$WATCHPATH" "$LOGFILE" 5 $STORAGE_ID $SCRIPT_ROOT >> "$LOGFILE/$MYNAME.$EXECTIME.out" 2>&1 &
     echo "Executed REGISTERFILESYSTEM Shared Script for $EXECTIME from $MYNAME:$STORAGE_ID" >> $MYLOGFILE
  exit
fi

echo "HOST NOT FOUND $EXECTIME from $MYNAME" >> $MYLOGFILE



