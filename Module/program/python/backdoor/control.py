import socket
import requests
import sys

start_port: int

def wait_for_client():
    try:
        get_connect = socket.socket()
        get_connect.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR , 1)
        get_connect.bind(("127.0.0.1",start_port))
        get_connect.listen()

        while True:
            conn, addr = get_connect.accept()
            try:
                
            except:
                conn.send(bytes("error".encode()))

            conn.close()
    except:
        print("error")
        exit(0)

if __name__ == '__main__':
    #print(sys.argv[1])
    start_port = int(sys.argv[1])

    print("\n Backdoor Virus Control Consoele.\n")
    command = input("LinwinSploit> ")