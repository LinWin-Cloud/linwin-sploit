import main as main
import os

def mainUI(jre: str , runPath: str):

    while True:
        try:
            print("[Enter 'exit' to exit]")
            start_port:int = input("Enter a port you want to start: ")
        
            if start_port == 'exit':
                break   
    
            proxy_url: str = input("Proxy Url: ")

            if proxy_url == 'exit':
                break

            os.popen(jre+" -jar "+runPath+"/../Module/post/proxy/server/release/ProxyService.jar "+str(proxy_url)+" "+str(start_port),"r")

            print(" [INFO] Run `ProxyService.jar` Successful!")
            print(" [INFO] You can connect or visit: http://localhost:"+str(start_port))
            break
        
        except:
            print(' [ERROR] Your input content was error!')

    main.jre = jre
    main.runPath = runPath
    main.main()