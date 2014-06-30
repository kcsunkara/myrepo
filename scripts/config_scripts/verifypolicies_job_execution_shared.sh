#!/bin/sh
#Shared script for running VERIFYPOLICIES jobs.

MYNAME=`uname -n`
SCRIPT_NAME="verifyPoliciesWSClient.sh"
MYLOGFILE="`echo ~`/kcsunkara/data/logs/verifypolicies_job_execution_shared.log"
EXECTIME=`date +"%Y-%m-%d_%H-%M-%S"`

echo "Processing VERIFYPOLICIES Shared Script for $EXECTIME from $MYNAME" >> $MYLOGFILE

# -----------------------------------------------------------------------
# For all nodes in "linuxdrupal-1.vpc.ctsdamlabs.com" with shared storage: /home/ec2-user/kcsunkara/data/..
# -----------------------------------------------------------------------
if [ "$MYNAME" == "linuxdrupal-1.vpc.ctsdamlabs.com" ]; then
  echo "Running VERIFYPOLICIES scripts for $MYNAME"
     SCRIPT_ROOT="/home/ec2-user/kcsunkara/scripts/ws_scripts"
     LOGFILE="/home/ec2-user/kcsunkara/data/logs/verifypolicylogs"

     export STORAGE_ID=11
     nohup "$SCRIPT_ROOT/$SCRIPT_NAME" "$LOGFILE" $STORAGE_ID >> "$LOGFILE/$MYNAME.$STORAGE_ID.$EXECTIME.$$.out" 2>&1 &
     echo "Executed VERIFYPOLICIES Shared Script for $EXECTIME from $MYNAME:$STORAGE_ID" >> $MYLOGFILE
  exit
fi

echo "HOST NOT FOUND $EXECTIME from $MYNAME" >> $MYLOGFILE
