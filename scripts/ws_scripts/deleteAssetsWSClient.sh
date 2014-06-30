#!/bin/sh
# THIS SCRIPT WILL CALL 'findDeleteAssets' WEBSERVICE FOR ANY ASSETS MARKED FOR DELETION.
# PERFORMS THE DELETE OPERATION AND LOGS THE STATUS.
# WE'RE READING THE STORAGE_ID FROM THE EVN VARIABLES.

if [ $# -lt 1 ]; then
        echo "-----------------------------------------------------------------------------"
        echo "Error... Please follow the usage."
        echo "USAGE: sh deleteAssetsWSClient.sh <LOGS_FOLDER_LOCATION> [STORAGE_ID]"
        echo "-----------------------------------------------------------------------------"
        exit 1
fi

if [ $# -ge 2 ]; then
        STORAGE_ID=$2
fi

CURRENT_TIME=$(date +"%Y-%m-%d-%T").$$

WORKER="DELETE_ASSETS"
MACHINE=$(uname -n)
WORKERS_DIR=/home/developer/sunkk001/workers
mkdir -p $WORKERS_DIR
NAME=$MACHINE.$WORKER.$CURRENT_TIME.lock

finish(){
        END_TIME=$(date +"%Y-%m-%d-%T")
        if [ $@ -eq 18 ]; then
                echo "DELETE_ASSETS WORKER INTRUPTED ON $END_TIME"
                echo "UN-REGISTERING DELETE_ASSETS WORKER: $WORKERS_DIR/$NAME"
                echo "PROCESSING END-TIME: $END_TIME"
		rm "$WORKERS_DIR/$NAME"
                exit 1
        fi
        echo "UN-REGISTERING DELETE_ASSETS WORKER: $WORKERS_DIR/$NAME"
        rm "$WORKERS_DIR/$NAME"
        echo "PROCESSING END TIME: $END_TIME"
        echo "-----------------------------------------------------------------------------"
        exit 0
}

# WE NEED TO REGISTER THAT THIS WORKER IS RUNNING SO WHEN THE CRONTAB TRIES TO START IT AGAIN,
# IT CAN TELL CRONTAB TO SKIP THE SCHEDULE, IF IT'S RUNNING ALREADY.
if [ -f "$WORKERS_DIR/$NAME" ]; then
        echo "THE WORKER 'deleteAssetsWSClient' IS IN PROGRESS: $NAME"
        echo "SKIPPING THIS SCHEDULE: $CURRENT_TIME"
        exit 0
else
        echo "-----------------------------------------------------------------------------"
        echo "REGISTERING 'deleteAssetsWSClient' WORKER: $NAME"
        touch  "$WORKERS_DIR/$NAME"
        echo "PROCESSING START-TIME: $CURRENT_TIME"
fi

# TRAPING THE PROCESS SIGNALS AND UNREGISTERING THE WORKER.
trap "finish 18" SIGHUP SIGINT SIGQUIT SIGTERM SIGSTOP SIGTSTP

deleteAssets() {

        OUTDIR=$@
        if [ ! -d "$OUTDIR" ]; then
                mkdir -p $OUTDIR
        fi

        echo "CALLING 'DeleteAssetsRequest' WEBSERVICE, WITH STORAGE_ID: $STORAGE_ID"

        #REQUEST_URL=`echo "http://localhost:8080/replicawsprovider/replicaws/deleteAssetRequest/$STORAGE_ID"`
        REQUEST_URL=`echo "http://linuxdrupal-1.vpc.ctsdamlabs.com/replicawsprovider/replicaws/deleteAssetRequest/$STORAGE_ID"`
        curl --request GET $REQUEST_URL > $OUTDIR/DeleteAssetsRequestOutput_$CURRENT_TIME.json
        # cat sampleDelete.json > $OUTDIR/DeleteAssetsRequestOutput_$CURRENT_TIME.json

        if [ $? -eq 0 ]; then
                echo "SUCCESSFULLY INVOKED 'findDeleteAssets' WEBSERVICE"
        else
                echo "FAILED TO INVOKE 'findDeleteAssets' WEBSERVICE"
                finish 1
        fi

        # POPULATING KEY-VALUE PAIRS
        CODE=`cat $OUTDIR/DeleteAssetsRequestOutput_$CURRENT_TIME.json | json code`
        if [ $CODE -eq 0 ]; then
                echo "CODE: $CODE."
                echo "NO ASSETS ARE MARKED FOR DELETE AS OF: $CURRENT_TIME"
                finish 0
        fi
        MSG=`cat $OUTDIR/DeleteAssetsRequestOutput_$CURRENT_TIME.json | json message`
        DESC=`cat $OUTDIR/DeleteAssetsRequestOutput_$CURRENT_TIME.json | json description`
        DELETE_LIST_COUNT=`cat $OUTDIR/DeleteAssetsRequestOutput_$CURRENT_TIME.json | json dataList.length`

        echo "-----------------------------------------------------------------------------"
        echo "RESPONSE FROM 'findDeleteAssets' WEBSERVICE:"
        echo "-----------------------------------------------------------------------------"
        echo "CODE: $CODE"
        echo "MESSAGE: $MSG"
        echo "DESCRIPTION: $DESC"
        # echo "ASSETS MARKED FOR DELETE: "
        # cat $OUTDIR/DeleteAssetsRequestOutput_$CURRENT_TIME.json | json dataList | json -a path 
	echo "TOTAL ASSETS MARKED FOR DELETE: $DELETE_LIST_COUNT"
	
        echo "-----------------------------------------------------------------------------"
        echo "DELETING THE FILES FROM STORAGE WITH STORAGE_ID: $STORAGE_ID"
        echo "-----------------------------------------------------------------------------"
        RESP_JSON_FILE=$OUTDIR/DeleteAssetsResponseInput_$CURRENT_TIME.json

        echo "[" > $RESP_JSON_FILE
        for ((i=0;i<$DELETE_LIST_COUNT;i++))
        do
                INSTANCE_ID=`cat $OUTDIR/DeleteAssetsRequestOutput_$CURRENT_TIME.json | json dataList[$i].instanceId`
                FILE_PATH=`cat $OUTDIR/DeleteAssetsRequestOutput_$CURRENT_TIME.json | json dataList[$i].path`

                D_TIME=$(date +"%Y-%m-%d-%T")
                if [ ! -e $FILE_PATH ]; then
                        echo "STATUS: FILE NOT FOUND, MARKING AS DELETED;  FILE: $FILE_PATH;  TIME: $D_TIME"
                        STATUS="DELETED"
                else
                        rm "$FILE_PATH"
                        if [ $? -eq 0 ]; then
                                echo "STATUS: DELETED;  FILE: $FILE_PATH;  TIME: $D_TIME"
                                STATUS="DELETED"
                        else
                                echo "STATUS: FAILED TO DELETE;  FILE: $FILE_PATH;  TIME:  $D_TIME"
                                STATUS="FAILED"
                        fi
                fi
                RECORD=`echo "{\"instanceId\":\"$INSTANCE_ID\", \"status\":\"$STATUS\"}"`
	        echo $RECORD >> $RESP_JSON_FILE
                j=`expr $i + 1`
                if [ $j -lt $DELETE_LIST_COUNT ]; then
                        echo "," >> $RESP_JSON_FILE
                fi
        done
        echo "]" >> $RESP_JSON_FILE

        echo "-----------------------------------------------------------------------------"
        echo "CALLING 'DeleteAssetsResponse' WEBSERVICE:"
	# echo "WITH THE FOLLOWING JSON INPUT:"
        # cat $RESP_JSON_FILE | json
        echo "-----------------------------------------------------------------------------"

	#curl -X POST -H "Content-Type: application/json" --data @$RESP_JSON_FILE http://localhost:8080/replicawsprovider/replicaws/deleteAssetResponse 
	curl -X POST -H "Content-Type: application/json" --data @$RESP_JSON_FILE http://linuxdrupal-1.vpc.ctsdamlabs.com/replicawsprovider/replicaws/deleteAssetResponse
	echo ""
	if [ $? -eq 0 ]; then
		echo "SUCCESSFULLY INVOKED 'DeleteAssetsResponse' WEBSERVICE."
	else
		echo "ERROR WHILE CALLING 'DeleteAssetsResponse' WEBSERVICE."
	fi
        echo "DELETE ASSTES PROCESSING COMPLETED."
}

deleteAssets $1
finish 0

