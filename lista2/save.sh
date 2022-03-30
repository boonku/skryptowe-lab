#!bin/bash

src_dir=.
dst_dir=

if [[ $1 == *--src=* ]]; then
    src_args=($(echo $1 | tr "=" "\n"))
    src_dir=${src_args[1]}
    shift
fi

if [[ $1 == *--dst=* ]]; then
    dst_args=($(echo $1 | tr "=" "\n"))
    dst_dir=${dst_args[1]}
else
    echo "Destination directory name not given"
    exit 2
fi

# if dir doesnt exist, make one
if [ ! -d $dst ]; then
    mkdir $dst
fi

# copy all files from soruce to dst, non recursive
cp -r ./$src_dir/. ./$dst_dir

echo "Copied contens to $dst_dir/"
