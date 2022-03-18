#!bin/bash

src=./
dst=

if [ $1 = "--src" ]; then
    shift
    $src=$1
    shift
fi

if [ $1 = "--dst" ]; then
    shift
    dst=$1
else
    exit 2
fi

echo "dst = $dst"

# if dir doesnt exist, make one
if [ ! -d $dst ]; then
    mkdir $dst
fi
# copy all files from soruce to dst, non recursive
cp $src* "$dst/"
