#!/bin/bash
if [ $# -lt 3 ]; then
    echo "Too few arguments given"
    exit 1
fi

re='^[0-9]+$'
if ! [[ $2 =~ $re ]]; then
    echo "$2 is not a number"
    exit 1
fi

if ! [[ $3 =~ $re ]]; then
    echo "$3 is not a number"
    exit 1
fi

cases=$(tail -n+2 covid.tsv | java Process.java --select=$1 --select-column=11 --project=1,5 | java Process.java --select=.$2.$3 --select-column=1 | java Aggregate.java --func=sum --column=2)
echo "Sum of cases in $1 in $2.$3: $cases"