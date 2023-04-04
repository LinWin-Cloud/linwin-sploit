# LinwinSploit渗透测试软件
# 作者: LinwinCloud
# https://gitee.com/LinWin-Cloud/linwin-sploit/

import os
import WebControl as webControl
import ConfigConsole as ConfigConsole
import ProxyService as ProxyService
import PyControlConsole as PyControlConsole
import Linux_amd64_trojanVirus as Linux_amd64_trojanVirus

import sys
import webbrowser
import threading
import time


def get_file_content(path: str, exception: bool) -> str:
    # get_file_content() Function: get file content.
    # path: this value in to get the file path
    # exception: if can not find target file then exit.
    try:
        openfile = open(path)
        return openfile.read()
    except:
        print("CAN NOT FIND TARGET FILE: " + path)
        if exception:
            exit(1)
        else:
            return "CAN NOT FIND TARGET FILE: " + path

    finally:
        pass


def copy_file(source: str,target: str):
    o = open(source)
    r = o.read()

    with open(target,"w") as f:
        f.write(r)
    f.close()


runPath = os.path.abspath(os.path.dirname(__file__))
version = get_file_content(runPath + "/../resource/version.txt", True).replace("\n", "")
commandLine = "LinwinSploit-" + version + " > "
software = os.path.abspath(os.path.join(runPath,".."))
jre: str = ""
payload: list[str] = [
    'linux/amd64/trojan_virus',
    'linux/amd64/crash_virus',
    'android/arch64/trojan_virus',
    'program/python/trojan_virus',
    'program/java/trojan_virus',
    'web/attack/trojan_virus ',
    'web/attack/crash_virus',
    'post/proxy/server ',
    'web/social/web_terminal'
]


def run_command(command: str) -> bool:
    command = command.strip()
    try:
        #def a():
        if command == 'help':
            print(get_file_content(runPath + "/../resource/Help.txt", True))
            return True
        if command == 'show payload':
            print(get_file_content(runPath + "/../resource/Payload.txt", True))
            return True

        if command.startswith("use "):
            use_payload = command[4:len(command)]
            if use_payload == 'web/attack/trojan_virus':
                webControl.web_control_console(use_payload,jre,runPath)
            
            if use_payload == 'web/attack/crash_virus':
                print("Start Http Port on 8989: http://localhost:8989/")
                os.system("cd "+runPath+"/../Module/web/attack/crash_virus/ && python3 -m http.server 8989")
            
            if use_payload == 'linux/amd64/crash_virus':
                print("The file has been generated in "+os.environ['HOME']+"/LinuxCrashVirus.sh")
                copy_file(runPath+"/../Module/linux/amd64/crash_virus/LinuxCrashVirus.sh",os.environ['HOME']+"/LinuxCrashVirus.sh")

            if use_payload == 'program/python/trojan_virus':
                PyControlConsole.console(runPath)

            if use_payload == 'post/proxy/server':
                print(" [INFO] Proxy Server Module.")
                ProxyService.mainUI(jre , runPath)

            if use_payload == 'web/social/web_terminal':

                def start_web_browser():
                    time.sleep(0.5)
                    webbrowser.open("http://127.0.0.1:11451/")

                openWeb = threading.Thread(target=start_web_browser,name="start web browser")
                openWeb.start()
                os.system("cd "+runPath+"/../Module/web/social/web_terminal/ && "+jre+" -jar "+runPath+"/../HttpServer/release/"+"HttpServer.jar 11451")


            if use_payload == 'linux/amd64/trojan_virus':
                Linux_amd64_trojanVirus.console(runPath)

            else:
                print("CAN NOT FIND TARGET MODULE: "+use_payload)
            return True
        if command == 'exit':
            exit()
        if command == 'config':
            ConfigConsole.configConsole()
            return True
        if command.startswith("search "):
            print("\n |-Search Payload-|")
            searchPayload = command[len("search "):len(command)]
            for i in payload:
                if i.find(searchPayload) != -1:
                    print(" [FIND] "+i)
                    continue

            print()
            return True
        else:
            os.system(command)
            return True
    except:
        print("INPUT COMMAND ERROR!")
        return False


def main():
    while True:
        command = input(commandLine)
        run_command(command)


if __name__ == '__main__':
    # 获取配置的jre路径
    jre = get_file_content(runPath+"/../resource/Jre.txt",True)
    jre = jre.replace("\n","")
    jre = jre.strip()
    jre = jre.replace("{Software}",software)
    
    # 启动Logo画面
    print(get_file_content(runPath + "/../resource/Logo.txt", True))
    print("  [ Enter 'help' to get help ]")
    print("  [ Project: https://gitee.com/LinWin-Cloud/linwin-sploit ]")
    print("  [ CopyRight By LinwinSoft ]")
    print("  [ Version : " + version + " ]")
    print()
    main()
