#!/bin/sh
# THIS SCRIPT WILL CALL requestCopyTier WEBSERVICE FOR ANY COPY JOBS THAT IT HAS TO DO.
# PERFORMS THE COPY USING ASPERA ascp, CALLS responseCopyTier WITH JOB_ID THAT IT HAS RECEIVED EARLIER AND STATUS.
# WE'RE READING THE STORAGE_ID FROM THE EVN VARIABLES.
echo "-----------------------------------------------------------------------------"
if [ $# -lt 1 ]; then
        echo "Error... Please follow the usage."
        echo "USAGE: sh copyTier1WSClient.sh <Logs_Folder_Location> [STORAGE_ID]"
        echo "-----------------------------------------------------------------------------"
        exit 1
fi
if [ $# -ge 2 ]; then
	STORAGE_ID=$2
fi

CURRENT_TIME=$(date +"%Y-%m-%d-%T").$$
MYHOSTNAME=`uname -n`

copyTier1() {
        OUTDIR=$@
        if [ ! -d "$OUTDIR" ]; then
          mkdir -p $OUTDIR
        fi

        echo "CALLING requestCopyTier WEBSERVICE, WITH STORAGE_ID: $STORAGE_ID"
        echo "CURRENT TIME: $CURRENT_TIME"
        echo "-----------------------------------------------------------------------------"

        REQUEST_URL=`echo "http://linuxdrupal-1.vpc.ctsdamlabs.com/replicawsprovider/replicaws/requestCopyTier/$MYHOSTNAME/$STORAGE_ID"`
        curl --request GET $REQUEST_URL > $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json
	
        if [ $? -eq 0 ]; then
        	echo "SUCCESSFULLY INVOKED 'requestCopyTier' WEBSERVICE"
        else
                echo "FAILED TO INVOKE 'requestCopyTier' WEBSERVICE"
                exit 0
        fi

        # Populating the values of code,message,description,host,jobId,sourcePath,destinationPath
		IS_EXIT=""
		IS_TIER1=""
		COUNT=`cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json length`
		echo "-----------------------------------------------------------------------------"
		echo "RESPONSE FROM 'requestCopyTier1' WEBSERVICE:"
		echo "-----------------------------------------------------------------------------"
		if [ $COUNT > 0 ]; then
			for ((i=0;i<$COUNT;i++))
			do
				code=`cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json $i.code`
				echo "CODE: $code"
				echo "MESSAGE: `cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json $i.message`"
				echo "DESCRIPTION: `cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json $i.description`"
				if [ $code -eq 404 ]; then
					IS_EXIT="true"
					echo "-----------------------------------------------------------------------------"
                  		     	continue 
                		fi

				TIER_INFO=`cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json $i.copyTierName`
				
				if [ "$TIER_INFO" == "tier1" ]; then
					IS_TIER1="true"
					host=`cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json $i.host`
					jobId=`cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json $i.jobId`
					sourcePath=`cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json $i.sourcePath`
					destinationPath=`cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json $i.destinationPath`
					echo "HOST: $host"
					echo "JOB_ID: $jobId"
					echo "SOURCEPATH: $sourcePath"
					echo "DESTINATIONPATH: $destinationPath"
				else if [ "$TIER_INFO" == "tier2" ]; then
					echo "REQUEST_ID: `cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json $i.requestNumber`"
					echo "REQUEST_STATE: `cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json $i.requestState`"
				fi
				fi
				echo "-----------------------------------------------------------------------------"
			done
		else 
	                echo "CODE: `cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json code`"
        	        echo "MESSAGE: `cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json message`"
                	echo "DESCRIPTION: `cat $OUTDIR/requestCopyTierOutput_$CURRENT_TIME.json | json description`"
		fi
		               
		if [ "$IS_EXIT" == "true" ] || [ "$IS_TIER1" != "true" ]; then
			exit 0
		fi

		echo "SCP COMMAND: "
		echo "scp ec2-user@$host:\"$sourcePath\" \"$destinationPath\""
		# scp developer@$host:"$sourcePath" "$destinationPath"
		cp "$sourcePath" "$destinationPath"
		# ascp -d developer@$host:"$sourcePath" "$destinationPath"
		# ascp -QTd -l 2000m developer@$host:"$sourcePath" "$destinationPath"
		if [ $? -eq 0 ]; then
			status="success"
			echo "COPY JOB COMPLETED"
		else
			status="FAILED"
			echo "COPY FAILED FOR..."
			echo "SRC: $host:$sourcePath"
			echo "DEST: $destinationPath"
		fi
        
        echo "-----------------------------------------------------------------------------"
        echo "CALLING responseCopyTier WEBSERVICE WITH JOBID: $jobId and STATUS: $status"
        echo "-----------------------------------------------------------------------------"

        RESPONSE_URL=`echo "http://linuxdrupal-1.vpc.ctsdamlabs.com/replicawsprovider/replicaws/responseCopyTier/$jobId/$status"`
        curl --request GET $RESPONSE_URL > $OUTDIR/responseCopyTierOutput_$CURRENT_TIME.json

        if [ $? -eq 0 ]; then
       		 echo "SUCCESSFULLY INVOKED 'responseCopyTier' WEBSERVICE"
                 echo "RESPONSE FROM responseCopyTier WEBSERVICE:"
                 cat $OUTDIR/responseCopyTierOutput_$CURRENT_TIME.json
        else
                 echo "FAILED TO INVOKE responseCopyTier WEBSERVICE."
        fi
        echo ""
        echo "-----------------------------------------------------------------------------"
        END_TIME=$(date +"%Y-%m-%d-%T")
        echo "PROCESSING COMPLETED ON $END_TIME"
        echo "-----------------------------------------------------------------------------"
}
copyTier1 $1


