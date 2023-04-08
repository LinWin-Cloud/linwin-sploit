
# LinwinSploit FrameWork

***运行平台***: Linux

LinwinSploit是一款渗透测试框架，采用Python、Java、Linux Shell、Javascript编写。适用于国产操作系统和Linux操作下Web木马和操作系统木马渗透工具集合，拥有快捷的操作、简单的使用、高效的入侵功能。支持Android、windows、Linux和Web端的渗透控制。设计用于黑帽子黑客渗透，也可以供个人和组织学习借鉴代码

***[注意] 这款渗透测试软件完全按照刑法标准设计, 使用不当可能导致牢狱之灾***
```
《中华人民共和国刑法》有关计算机犯罪条款
 
　　第二百八十五条违反国家规定，侵入国家事务、国防建设、尖端科学技术领域的计算机信息系统的，处3年以下有期徒刑或者拘役。
　　第二百八十六条违反国家规定，对计算机信息系统功能进行删除、修改、增加、干扰，造成计算机信息系统不能正常运行，后果严重的，处5年以下有期徒刑或者拘役；后果特别严重的，处5年以上有期徒刑。
　　违反国家规定，对计算机信息系统中存储、处理或者传输的数据和应用程序进行删除、修改、增加的操作，后果严重的，依照前款的规定处罚。
　　故意制作、传播计算机病毒等破坏性程序，影响计算机系统正常运行，后果严重的，依照第一款的规定处罚。
　　第二百八十七条利用计算机实施金融诈骗、盗窃、贪污、挪用公款、窃取国家秘密或者其他犯罪的，依照本法有关规定定罪处罚。
 
``` 

### LinwinSploit适合什么样子的人
1. 编程语言初学者(Java或者Python，其实Js也是可以的)
2. 黑帽子黑客
3. 白帽子黑客测试
4. 网络安全学习人员
5. 进行网络安全测试人员

# 安装
### 安装库

```
    # 首先安装Linux软件库
    apt install `cat ./linux_lib.txt`
```

### 安装python库

```
    pip install `cat ./py_lib.txt`
```

### 启动
```
    python3 LinwinSploit.py
```

# LinwinSploit FrameWork信息

### LinwinSploit木马渗透平台
```
	1. Web Browser	 		(浏览器)
	2. Linux   			    (64位系统)
	3. Windows 			    (x64 64位系统)

	4. Jvm				    Java虚拟机
	5. Python VM			Python虚拟机
```

## 内置模块
```
    |-LinwinSploit Attack Payload-|

    1. linux/amd64/trojan_virus         Linux Deb install package
    2. linux/amd64/crash_virus          Linux Shell Script
    3. windows/cmd/crash_virus      	Windows cmd crash virus.
    4. program/python/trojan_virus      make a .py file amd run with python runtime
    5. program/java/trojan_virus        make a jar file and run with java runtime
    6. web/attack/trojan_virus          use web page or javascript control Browser
    7. web/attack/crash_virus           crash virus in web.
    8. post/proxy/server                Hide attacks through proxy servers.
    9. web/social/web_terminal          Start a Port and run Web Terminal.
   
    10. post/http/server		Start a http port.
    调用这些模块可以快速实现渗透
```

## 使用第三方开源软件
```
    1. pyinstxtractor
    2. openLinwin-ProxyService
```

## 关于LinwinSploit

### 快速学习
文档链接: https://gitee.com/LinwinSoft/linwin-sploit/wiki/home

### 鸣谢
1. LinwinCloud              (真人)
2. zmh-program              (真人)
3. ChatGPT                  (计算机)
4. LinwinSoft团队            (群体)

# 软件使用权限，部分细节内容
## 关于传播
1. 本软件允许经过二次开发进行开源或者销售.
2. 如果需要二次开发或者是二次销售，那么必须注明软件原作者: LinwinSoft (https://gitee.com/LinWin-Cloud/)

## 关于责任
1. 本产品作者对于使用者的任何行为不做出任何的承诺和担责
2. 一切自己负责

## 本产品免费开源
相信开源的力量
