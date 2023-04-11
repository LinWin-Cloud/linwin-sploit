import os


runPath = os.path.abspath(os.path.dirname(__file__))

def HaveLib():
    try:
        import wget
        import urllib
        import requests
    except:
        o = open(runPath+"/py_lib.txt")
        print(o.read())
        print(" [ERR] YOU MUST INSTALL THESE LIB.")
        exit(1)

if __name__ == '__main__':

    HaveLib()
    os.system("cd "+runPath+"/MainTerminal/ && python3 main.py")
        