# LinwinSploit渗透测试软件
# 作者: LinwinCloud
# https://gitee.com/LinWin-Cloud/linwin-sploit/

import os
import WebControl as webControl
import ConfigConsole as ConfigConsole


def get_file_content(path: str, exception: bool) -> str:
    # get_file_content() 方法: 获取文件的内容
    # path: 要获取的文件路径
    # exception: 出现意外是否退出程序, True为退出
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
    # 启动Logo画面
    print(get_file_content(runPath + "/../resource/Logo.txt", True))
    print("  [ Enter 'help' to get help ]")
    print("  [ Project: https://gitee.com/LinWin-Cloud/linwin-sploit ]")
    print("  [ CopyRight By LinwinSoft ]")
    print("  [ Version : " + version + " ]")
    print()
    main()
