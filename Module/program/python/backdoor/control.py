import socket
import requests
import sys

start_port: int

def wait_for_client():
    try:
        get_connect = socket.socket()
        get_connect.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR , 1)
        get_connect.bind("127.0.0.1",)
    except:
        print("error")
        exit(0)

if __name__ == '__main__':


    print("\n Backdoor Virus Control Consoele.\n")
    command = input("LinwinSploit> ")