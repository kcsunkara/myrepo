#!/bin/sh
# THIS SCRIPT WILL CALL 'VerifyPolicies' WEBSERVICE.

if [ $# -lt 1 ]; then
        echo "-----------------------------------------------------------------------------"
        echo "Error... Please follow the usage."
        echo "USAGE: sh verifyPoliciesWSClient.sh <Logs_Folder_Location> [STORAGE_ID]" 
        echo "-----------------------------------------------------------------------------"
        exit 1
fi

if [ $# -ge 2 ]; then
        STORAGE_ID=$2
fi

CURRENT_TIME=$(date +"%Y-%m-%d-%T").$$

OUTDIR=$1
if [ ! -d "$OUTDIR" ]; then
    mkdir -p $OUTDIR
fi

echo "CALLING 'verifyPolicies' WEBSERVICE: "

#curl --request GET http://localhost:8080/replicawsprovider/replicaws/verifyPolicies > $OUTDIR/verifyPoliciesWSOutput_$CURRENT_TIME.json
curl --request GET http://localhost/replicawsprovider/replicaws/verifyPolicies > $OUTDIR/verifyPoliciesWSOutput_$CURRENT_TIME.json

if [ $? -eq 0 ]; then
	echo "SUCCESSFULLY INVOKED 'verifyPolicies' WEBSERVICE"
else
	echo "FAILED TO INVOKE 'verifyPolicies' WEBSERVICE"
	exit 1
fi

# POPULATING KEY-VALUE PAIRS
COUNT=`cat $OUTDIR/verifyPoliciesWSOutput_$CURRENT_TIME.json | json length`
# POPULATING KEY-VALUE PAIRS
echo "-----------------------------------------------------------------------------"
echo "RESPONSE FROM 'verifyPolicies' WEBSERVICE:"
echo "-----------------------------------------------------------------------------"
if [ $COUNT > 0 ]; then
	for ((i=0;i<$COUNT;i++))
	do
		echo "CODE: `cat $OUTDIR/verifyPoliciesWSOutput_$CURRENT_TIME.json | json $i.code`"
		echo "MESSAGE: `cat $OUTDIR/verifyPoliciesWSOutput_$CURRENT_TIME.json | json $i.message`"
		echo "DESCRIPTION: `cat $OUTDIR/verifyPoliciesWSOutput_$CURRENT_TIME.json | json $i.description`"
		echo "-----------------------------------------------------------------------------"
	done
else
	echo "CODE: `cat $OUTDIR/verifyPoliciesWSOutput_$CURRENT_TIME.json | json code`"
	echo "MESSAGE: `cat $OUTDIR/verifyPoliciesWSOutput_$CURRENT_TIME.json | json message`"
	echo "DESCRIPTION: `cat $OUTDIR/verifyPoliciesWSOutput_$CURRENT_TIME.json | json description`"
	echo "-----------------------------------------------------------------------------"
fi


