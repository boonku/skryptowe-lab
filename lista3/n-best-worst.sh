#!/bin/bash
if [ $# -eq 0 ]; then
    echo "No arguments given"
    exit 1
fi

re='^[0-9]+$'
if ! [[ $2 =~ $re ]]; then
    echo "$2 is not a number"
    exit 1
fi

tail -n+2 covid.tsv | java Process.java --select=$1 --project=1,6 > tmp
if [ ! -s tmp ]; then
    echo "No records for that country"
    exit 1
fi
for i in {2019..2020}
do
    if [ $i -eq 2019 ]; then
        range=$(seq -w 12 12)
    else
        range=$(seq -w 01 12)
    fi
    for j in $range
    do
        echo -ne "$j\t$i\t" >> average
        s=$(java Process.java --select=.$j.$i < tmp | java Aggregate.java --func=avg --column=2)
        if [ ! -z $s ]; then
            LC_NUMERIC="en_US.UTF-8" printf "%.2f" $s >> average
        fi
        echo >> average
    done
done

rm -f tmp
awk -F'\t' '$3 != ""' average > result
rm -f average
sort -nk3 result > tmp && mv tmp result
len=$(java Aggregate.java --func=count --column=3 < result)

if [ $2 -gt $len ]; then
    echo "Too few records to display"
    exit 1
fi

echo "$2 best months:"
echo -e "month\tyear\taverage deaths"
java Head.java --lines=$2 < result
echo -e "\n$2 worst months:"
echo -e "month\tyear\taverage deaths"
java Tail.java --lines=$2 < result
rm -f result