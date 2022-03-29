#!/bin/bash
java Paths.java -R -s | sed "s/^\t*//g" | java Process.java --select=$1 | sort -t$'\t' -nrk2 > tmp
awk '{ total += $2 } END { print total/NR }' tmp
java Head.java --lines=$2 < tmp
rm -f tmp