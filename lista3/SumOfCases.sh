#!/bin/bash

tail -n+2 covid.tsv | java Process.java --select=$1 | java Process.java --select=$2 --select-column=4 | java Process.java --select=$3 --select-column=3 | java Aggregate.java --func=sum --column=5
