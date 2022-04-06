# !/bin/bash
if [ $# -eq 0 ]; then
    echo "No arguments given"
    exit 1
fi

tail -n+2 covid.tsv | java Process.java --select=$1 --project=1,5,6 > tmp
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
        echo -ne "$j\t$i\t" >> sum_cases_deaths
        # get sum of cases and deaths in particular month and year
        deaths=$(java Process.java --select=.$j.$i --project=1,3 < tmp | java Aggregate.java --func=sum --column=2)
        cases=$(java Process.java --select=.$j.$i --project=1,2 < tmp | java Aggregate.java --func=sum --column=2)
        # if no cases then also no deaths
        if [ ! -z $cases ]; then
            LC_NUMERIC="en_US.UTF-8" printf "%.2f\t%.2f" $cases $deaths >> sum_cases_deaths
        fi
        echo >> sum_cases_deaths
    done
done

rm -f tmp

awk -F'\t' '$3 != ""' sum_cases_deaths > result

rm -f sum_cases_deaths

echo "Percentage of deaths of infected in following months in $1:"
echo -e "month\tyear\tpercentage"
while IFS=$'\t' read -r month year cases deaths
do
    echo -ne "$month\t$year\t"
    # bash doesnt support float points
    # using python to divide values
    if [ $cases != "0.00" ]; then
        echo "print('%.2f' % ($deaths / $cases * 100) )" | python
    else
        echo 0.00
    fi
done < result

rm -f result
