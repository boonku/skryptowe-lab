#!/bin/bash
re='^[0-9]+$'
if ! [[ $2 =~ $re ]]; then
    echo "$2 is not a number"
    exit 1
fi
# get all files, select the ones with given extension and sort them by size, then store in temp file
# temp file will be used to calculate average with awk
java Paths.java -R -s | sed "s/^\t*//g" | java Process.java --select=$1 | sort -t$'\t' -nrk2 > tmp
echo "$2 biggest files with '$1' extension:"
java Head.java --lines=$2 < tmp
if [ ! $? -eq 0 ]; then
    rm -f tmp
    exit 1
fi

echo -e "\nAverage size of files with '$1' extension in bytes: \c"
# calculate average, add up second column and in the end print sum divised by number of rows
awk '{ total += $2 } END { print total/NR }' tmp

# delete temp file
rm -f tmp