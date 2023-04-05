javac -d ../out/trojan_virus/ *.java
mv resource ../out/trojan_virus
cd ../out/trojan_virus/
jar -cvfm app.jar ../../release/Trojan_virus.MF *
java -jar app.jar