import main as main


class attack:
    connect: str = ""
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


def web_control_console(payload: str):
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
    1. set host [Host IP]           Set your trojan's connect host.
    2. set port [Port]              Set your trojan's connect port.
    3. run                          listen on a port and accept the message.
    
    For example:
    1. set host 127.0.0.1
    2. set port 8888
                """)
            if command.startswith("set host "):
                host: str = command[len("set host "):len(command)]
                attack.connect = host
                continue

            if command.startswith("set port "):
                port: int = int(command[len("set port "):len(command)])
                attack.port = port

            if command == 'exit':
                break

            if command == 'run':


        except:
            continue

    main.main()
