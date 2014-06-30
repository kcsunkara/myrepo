#!/bin/sh

FILELIST=$1
OUTPUT=$2
TMPDIR=$3
STORAGE=$4

echo "PREPARING JSON FOR THE FOLLOWING ASSETS..."
IFS=$'\n'
cat $FILELIST | awk -F";" '{print $1}' | while read LINE 
do
     ls -l $LINE
done
echo "-----------------------------------------------------------------------------"

#cat $FILELIST | tr -s "\t" ";" > $TMPDIR/assetdetails.txt
cat $FILELIST > $TMPDIR/assetdetails.txt

IFS=$'\n'

for i in `cut -d";" -f1 $TMPDIR/assetdetails.txt`
do
	FILE_NAME=$(basename "$i")
        echo "$FILE_NAME" >> $TMPDIR/assetname.txt
done

paste $TMPDIR/assetname.txt $TMPDIR/assetdetails.txt | tr -s "\t" ";" > $TMPDIR/assetmeta.txt
cat $TMPDIR/assetmeta.txt | awk -F";" '{print $2}' > $TMPDIR/assetpath.txt
cat $TMPDIR/assetpath.txt | while read lines
do
	#stat -f "%Sm" $lines  | awk '{print $2, $1, $4, $3}' >> $TMPDIR/assettime.txt
	#ls -l $lines | awk '{print $6, $7, $8}' >> $TMPDIR/assettime.txt
        find $lines -maxdepth 0 -printf "%Td %Tb %TY %TH:%TM:%TS\n" >> $TMPDIR/assettime.txt
done
paste $TMPDIR/assetmeta.txt $TMPDIR/assettime.txt | tr -s "\t" ";" > $TMPDIR/assetmetadata.txt

cat << FILE > $TMPDIR/body.txt
BEGIN{
FS=";"
print "{'assets':"
print "["
}

{
print "{"
print "'name':'"\$1"' , 'filesize':'"\$3"' , 'created_date':'"\$4"',"
print "'fs_path': '"\$2"',"
print "'policy_id':'126',"
print "'assetInstances': ["
print "{'filename': '"\$1"',"
print "'storageLocId': 'machine'"
#print "'path': '"\$2"'"
print "}]},"
}

END{
print "]}"
}
FILE

awk -f $TMPDIR/body.txt $TMPDIR/assetmetadata.txt > $TMPDIR/jsonfile.txt

sed "s/'/\"/g" $TMPDIR/jsonfile.txt > $TMPDIR/asset.json

m=`cat $TMPDIR/asset.json | sed '/^$/d' | wc -l`

n=`expr $m - 2`

eval $(echo "cat $TMPDIR/asset.json | sed -n '1,$n p' | sed 's/ p/p/'") > $TMPDIR/wsrequest.json

cat $TMPDIR/asset.json | tail -2 | sed 's/,//' >> $TMPDIR/wsrequest.json
x=`uname`
a="sed 's/machine/$STORAGE/'"
eval $(echo "cat $TMPDIR/wsrequest.json | $a") > $OUTPUT
echo "-----------------------------------------------------------------------------"
echo "JSON request file created: $OUTPUT"
echo "-----------------------------------------------------------------------------"



