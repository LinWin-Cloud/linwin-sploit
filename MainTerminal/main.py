# LinwinSploit渗透测试软件
# 作者: LinwinCloud
# https://gitee.com/LinWin-Cloud/linwin-sploit/

import os
import WebControl as webControl
import ConfigConsole as ConfigConsole


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


runPath = os.path.abspath(os.path.dirname(__file__))
version = get_file_content(runPath + "/../resource/version.txt", True).replace("\n", "")
commandLine = "LinwinSploit-" + version + " $ "
software = os.path.abspath(os.path.join(runPath,".."))
jre: str


def run_command(command: str) -> bool:
    command = command.strip()

    try:
        if command == 'help':
            print(get_file_content(runPath + "/../resource/Help.txt", True))
            return True
        if command == 'show payload':
            print(get_file_content(runPath + "/../resource/Payload.txt", True))
            return True

        if command.startswith("use "):
            use_payload = command[5:len(command)]
            webControl.web_control_console(use_payload)
            return True
        if command == 'exit':
            exit()
        if command == 'config':
            ConfigConsole.configConsole()
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
