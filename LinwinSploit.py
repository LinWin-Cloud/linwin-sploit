import os


runPath = os.path.abspath(os.path.dirname(__file__))

def haveLib():
    try:
        import sys
        import wget
        import os
        import urllib
        import requests
        import random
        import threading
        import uncompyle6
    except:
        o = open(runPath+"/py_lib.txt")
        print(o.read())
        print(" [ERR] YOU MUST INSTALL THESE LIB.")
        exit(1)

if __name__ == '__main__':
    haveLib()
    os.system("cd "+runPath+"/MainTerminal/ && python3 main.py")
        