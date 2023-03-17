source /etc/profile

find ./src/. | grep .java > src.txt
javac -d out @src.txt
cp -r Module ./out/

cd out
jar -cvfm LinwinSploit.jar ../bin/MANIFEST.MF *
mv LinwinSploit.jar ../bin/
cd ../bin/
java -jar LinwinSploit.jar