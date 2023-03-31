
connect:str = "http://127.0.0.1:8888/?api_key"
http_port: int = 11451

import os
import socket
import requests

def send_target():
    requests.get(connect)


def http_service():
    #print(" [INFO] START HTTP SEVICE ON PORT: "+str(port))
    http_socket = socket.socket()
    http_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    http_socket.bind(("127.0.0.1",http_port))
    http_socket.listen()
    while True:
        conn, addr = http_socket.accept()
        #print(conn.getsockname)

        recv_message: str = ""
        client_message = conn.makefile().readline()
        
        try:
            requests_url = client_message[client_message.index(" ")+1:client_message.rfind("H")-1]
            requests_url = requests_url.strip()

            if requests_url.startswith("/shell "):
                pass

        except:
            conn.send(bytes("error".encode()))
            conn.close()
            continue

if __name__ == '__main__':
    send_target()
    http_service()
    