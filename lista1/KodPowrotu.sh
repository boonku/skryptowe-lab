#!bin/bash
# check if any arguments were passed
if [ $# -eq 0 ]; then
    echo "No arguments were passed"
    exit 22
fi

# ensure first arguemnt is a file
if [ ! -f $1 ]; then
    echo "No file was passed as 1st argument or it doesn't exist in currenct directory"
    exit 2
fi

file=$1
# get all arguments (keywords) without the file name
shift
arguments=$@

KOD_POWROTU="KodPowrotu.java"
# run KodPowrotu with arguments
java $KOD_POWROTU $arguments < $file

return_code=$?

if [ $return_code -gt 0 ]; then
    let "keyword_number = $return_code + 1"
    echo "Most common keyword in the specified text is: '${!keyword_number}'"
else
    echo "No such keywords were found in the specified file"
fi

exit 0
