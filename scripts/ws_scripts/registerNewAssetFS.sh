#!/bin/bash

# This script is to watch a file system, looking for new files
# It starts with a find command for all ctimes in the last x hours
# then it goes through that list puts a file size on each, and
# starts checking their growth.  When they stop growing (as observed
# by filesize not changing in a check interval), then it will
# notice and make WS calls.

# cmin interval in minutes
echo "-----------------------------------------------------------------------------"
if [ $# -lt 3 ]; then
        echo "Error... Please follow the usage."
        #echo "USAGE: sh registerNewAssetFS.sh <STORAGE_ID> <Absolute path of the folder to watch> <LOGS FOLDER> <Interval in Minutes>"
		echo "USAGE: sh registerNewAssetFS.sh <Absolute path of the folder to watch> <LOGS FOLDER> <Interval in Minutes> [STORAGE_ID]"
        echo "-----------------------------------------------------------------------------"
        exit 1
fi
if [ $# -ge 4 ]; then
	STORAGE_ID=$4
fi

INTERVAL=$3
echo "THIS SCRIPT IS RUNNING ON STORAGE: $STORAGE_ID"
echo "SCANNING THE LOCATION: $1"
echo "for Assets not older than $INTERVAL minutes."
echo "-----------------------------------------------------------------------------"

START_TIME=$(date +"%Y-%m-%d-%T").$$
WATCH=$1
TMPDIR="$2/run_$START_TIME"
OUTDIR="$2/json"
mkdir -p $TMPDIR
mkdir -p $OUTDIR

# Temp file for results
TMPFILE="$TMPDIR/findresults.$$"

# comparison files for snapshots
SNAP0="$TMPDIR/filesizes.0"
SNAP1="$TMPDIR/filesizes.1"

# List of files ready for ingest
READY="$TMPDIR/ingest_ready.txt"
REQUEST="$OUTDIR/request_$START_TIME.json"
OUTPUT="$OUTDIR/output_$START_TIME.json"

# sleep time
SLEEPTIME=10
# Hard stop to prevent forever watching.
MAXLOOPCOUNT=2

#find "$WATCH" \! \( -path "$WATCH"/json -prune -o -path "$WATCH"/logs -prune \) -type f -cmin -$INTERVAL -print > $TMPFILE
find "$WATCH" -type f -cmin -$INTERVAL -print > $TMPFILE
RETVAL=$?
if [ $RETVAL -ne 0 ]; then
  echo "Find failed - $(date +"%Y-%m-%d-%T")"
  exit 10
fi

TRACKING=`wc -l $TMPFILE | awk '{print $1}'`
echo "Find Completed:  $TRACKING new files as of  - $(date +"%Y-%m-%d-%T")"
if [ "$TRACKING" -eq 0 ]; then
  rm -r $TMPDIR
  exit 0
fi

rm -f $SNAP1
rm -f $SNAP0

#echo "Running Perl one liners to get file sizes"
perl -n -e'chomp;printf "%s\;%s\n", $_, (stat)[7]' $TMPFILE > $SNAP0

#rm -f $TMPFILE

echo "Tracking files for changes:------------------------"
DIFFERENT=1
COUNTER=0
while [ $DIFFERENT -eq 1 -a $COUNTER -lt $MAXLOOPCOUNT ]; do
  echo "LoopCounter:---------- $COUNTER - $(date +"%Y-%m-%d-%T")"
  echo "Sleeping for:--------- $SLEEPTIME secs"
  sleep $SLEEPTIME

  #copy Snap0 contents to snap1 for files still in flux.,
  # if snap0 is same as snap1, then everything has stopped.

  #Ingest files that went static, then keep watching
  # non-static files for another loop.  That would accelerate ingest
  # of static files and not make them wait for all files to stop growing.

  #I'd do that with two one-liners.  One for static sizes that appends to
  # a "ready to ingest" file, second to SNAP1.
  # then, after diff, insetead of just looping, put the web service
  # processing inside the loop for each of the entries in
  # ready to ingest
  # Perl One-liner for all.  File size from STAT matches the file size in SNAP0
  
  perl -n -e'chomp;chomp;;s/\;.*$//;printf "%s\;%s\n",$_,(stat)[7]' $SNAP0 > $SNAP1

  cat $SNAP0 | grep -v '^$' | while read line
  do
    cat $SNAP1 | grep "$line"
  done > $READY
  
  if [ `wc -l $SNAP0 | awk '{print $1}'` -eq `wc -l $READY | awk '{print $1}'` ]; then
    DIFFERENT=0
  else 
    echo "Snapshots Not stable yet..."
  fi

  # Making Copy of SNAP1 as SNAP0
  rm -f $SNAP0
  mv  $SNAP1 $SNAP0

  let "COUNTER=COUNTER+1"
done

echo "SCANNING COMPLETED."
if [ "$DIFFERENT" -ne 0 ]; then
  echo "Some  of the files didn't stop growing before I ran out of loops - $(date +"%Y-%m-%d-%T")"
fi
echo "-----------------------------------------------------------------------------"
echo "LIST OF STABLE ASSETS:"
cat $READY
echo "-----------------------------------------------------------------------------"

if [ `wc -l $READY | awk '{print $1}'` -eq 0 ]; then
  echo "You have no stable files to ingest."
  rm -r $TMPDIR
  exit 0
fi

# Files to ingest are in $MATCH
echo "You are ready to process $MATCH for generating JSON, to pass to WS - $(date +"%Y-%m-%d-%T")"
echo "-----------------------------------------------------------------------------"

sh $5/createJSON.sh $READY $REQUEST $TMPDIR $STORAGE_ID

curl -X POST -H "Content-Type: application/json" --data @$REQUEST http://linuxdrupal-1.vpc.ctsdamlabs.com/replicawsprovider/replicaws/fileasset > $OUTPUT
#rm -r $TMPDIR
echo "-----------------------------------------------------------------------------"

exit 0



