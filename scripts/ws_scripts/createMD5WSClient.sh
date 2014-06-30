#!/bin/sh
# THIS SCRIPT WILL CALL requestMD5 WEBSERVICE FOR ANY MD5 JOBS THAT IT HAS TO DO.
# PERFORMS THE MD5 GIVEN ASSETS, CALLS responseMD5 WEBSERVICE WITH JOB_ID THAT IT HAS RECEIVED EARLIER AND STATUS.
# WE'RE READING THE STORAGE_ID FROM THE EVN VARIABLES.
echo "-----------------------------------------------------------------------------"

if [ $# -lt 1 ]; then
        echo "Error... Please follow the usage."
        echo "USAGE: sh createMD5WSClient.sh <LOGS_FOLDER_LOCATION> [STORAGE_ID]"
        echo "-----------------------------------------------------------------------------"
        exit 1
fi

if [ $# -ge 2 ]; then
        STORAGE_ID=$2
fi

MYHOSTNAME=`uname -n`

CURRENT_TIME=$(date +"%Y-%m-%d-%T").$$
create_md5() {
        OUTDIR=$@
        mkdir -p $OUTDIR

        echo "CALLING requestMD5 WEBSERVICE, WITH STORAGE_ID: $STORAGE_ID"
        echo "CURRENT TIME: $CURRENT_TIME"
        echo "-----------------------------------------------------------------------------"
		
		curl -X POST -H "Content-Type: application/json" --data '{"storageId":"'"$STORAGE_ID"'","hostName":"'"$MYHOSTNAME"'"}' http://linuxdrupal-1.vpc.ctsdamlabs.com/replicawsprovider/replicaws/requestmd5 > $OUTDIR/requestMD5Output_$CURRENT_TIME.json
#       /usr/local/bin/curl -X POST -H "Content-Type: application/json" --data '{"storageId":"'"$STORAGE_ID"'","hostName":"'"$MYHOSTNAME"'"}' http://linuxdrupal-1.vpc.ctsdamlabs.com/replicawsprovider/replicaws/requestmd5 > $OUTDIR/requestMD5Output_$CURRENT_TIME.json
#        curl -X POST -H "Content-Type: application/json" --data '{"storageId":"'"$STORAGE_ID"'","hostName":"'"$MYHOSTNAME"'"}' http://localhost:8080/replicawsprovider/replicaws/requestmd5 > $OUTDIR/requestMD5Output_$CURRENT_TIME.json

        if [ $? -eq 0 ]; then
           echo "SUCCESSFULLY INVOKED 'requestMD5' WEBSERVICE"
        else
           echo "FAILED TO INVOKE 'requestMD5' WEBSERVICE"
           exit 1
        fi

        # Populating the key-value pairs
	cat $OUTDIR/requestMD5Output_$CURRENT_TIME.json | sed -e 's/{//' -e 's/}//' -e 's/"//g' -e 's/,/:/g' > $OUTDIR/requestMD5OutputTemp_$CURRENT_TIME.json

	code=`cat $OUTDIR/requestMD5OutputTemp_$CURRENT_TIME.json | awk -F":" '{print $2}'`
	message=`cat $OUTDIR/requestMD5OutputTemp_$CURRENT_TIME.json | awk -F":" '{print $4}'`
	description=`cat $OUTDIR/requestMD5OutputTemp_$CURRENT_TIME.json | awk -F":" '{print $6}'`
	jobId=`cat $OUTDIR/requestMD5OutputTemp_$CURRENT_TIME.json | awk -F":" '{print $8}'`
	path=`cat $OUTDIR/requestMD5OutputTemp_$CURRENT_TIME.json | awk -F":" '{print $10}'`
	error=`cat $OUTDIR/requestMD5OutputTemp_$CURRENT_TIME.json | awk -F":" '{print $12}'`

	rm $OUTDIR/requestMD5OutputTemp_$CURRENT_TIME.json
	
	if [ "$code" != "200" ]; then
		echo "Something went wrong in REQUEST service..."
		cat $OUTDIR/requestMD5Output_$CURRENT_TIME.json
		exit 1
	fi
	if [ "$jobId" = "0" ] || [ "$jobId" = "" ]; then
                echo "JobId = 0. NO JOBS TO PERFORM. EXITING..."
                exit 0
        fi

        echo "-----------------------------------------------------------------------------"
        echo "RESPONSE FROM 'requestMD5' WEBSERVICE:"
        echo "-----------------------------------------------------------------------------"
        echo "CODE: $code"
        echo "MESSAGE: $message"
        echo "DESCRIPTION: $description"
        echo "JOBID: $jobId"
        echo "PATH: $path"
        echo "ERROR: $error"
        echo "-----------------------------------------------------------------------------"
	
        if [ ! -e $path ]; then
                        echo "File not found: $path"
                        status="File not found"
                        MD5=""
        else
                        MD5INFO=`md5sum "$path"`
                        if [ $? -eq 0 ]; then
                                        MD5=`echo "$MD5INFO" | awk '{print $1}'`
                                        echo "MD5 JOB COMPLETED"
                        else
                                        status="FAILED"
                                        echo "MD5 FAILED FOR..."
                                        echo "SRC: $path"
                                        MD5=""
                        fi
        fi

        echo "-----------------------------------------------------------------------------"
        echo "CALLING 'responseMD' WEBSERVICE WITH JOBID: $jobId , MD5: $MD5 and STATUS: $status"
        echo "-----------------------------------------------------------------------------"

        curl -X POST -H "Content-Type: application/json" --data '{"batchId":"'"$jobId"'","paramMd5":"'"$MD5"'","errorCode":"'"$status"'"}' http://linuxdrupal-1.vpc.ctsdamlabs.com/replicawsprovider/replicaws/responsemd5 > $OUTDIR/responseMD5Output_$CURRENT_TIME.json
		#/usr/local/bin/curl -X POST -H "Content-Type: application/json" --data '{"batchId":"'"$jobId"'","paramMd5":"'"$MD5"'","errorCode":"'"$status"'"}' http://linuxdrupal-1.vpc.ctsdamlabs.com/replicawsprovider/replicaws/responsemd5 > $OUTDIR/responseMD5Output_$CURRENT_TIME.json
        #curl -X POST -H "Content-Type: application/json" --data '{"batchId":"'"$jobId"'","paramMd5":"'"$MD5"'","errorCode":"'"$status"'"}' http://localhost:8080/replicawsprovider/replicaws/responsemd5 > $OUTDIR/responseMD5Output_$CURRENT_TIME.json

        if [ $? -eq 0 ]; then
                        echo "SUCCESSFULLY INVOKED 'responseMD5' WEBSERVICE"
                        echo "RESPONSE FROM 'responseMD5' WEBSERVICE:"
                        cat $OUTDIR/responseMD5Output_$CURRENT_TIME.json
        else
                        echo "FAILED TO INVOKE 'responseMD5' WEBSERVICE."
        fi
        echo ""
        echo "-----------------------------------------------------------------------------"
        END_TIME=$(date +"%Y-%m-%d-%T")
        echo "PROCESSING COMPLETED ON $END_TIME"
        echo "-----------------------------------------------------------------------------"
}
create_md5 $1

