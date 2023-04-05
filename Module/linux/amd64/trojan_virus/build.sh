#! /bin/bash

package_name=$1
make_path=$2
port=$3
host=$4

script_path=$(cd `dirname $0`; pwd)
target_virus=$script_path/build/usr/1/program.py

rm -rf $target_virus

echo 'connect: str="' $host '"' >> $target_virus
echo 'port: int=' $port >> $target_virus

