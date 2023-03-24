import main as main
import os


def configConsole():
    print("""
 1. Java Runtime Setting                    Enter '1'
 2. Version                                 Enter '2'
 3. About                                   Enter '3'
 4. exit                                    Enter '4'
    """)
    while True:
        options = input("LinwinSploit-"+main.version+" (Config) $ ")
        options = options.strip()

        if options == '1':
            print("For Example: "+main.software+"/jre/bin/java")
            path = input("Enter Java Application: ")
            path = path.strip()

            if path == '':
                continue
            else:
                if os.path.exists(path.replace("{Software}",main.software)) and os.path.isfile(path.replace("{Software}",main.software)):
                    with open(main.software+"/resource/Jre.txt","w") as f:
                        f.write(path)
                    f.close()
                    print("OK")
                    continue
                else:
                    print("Target Path Error: "+path)
                continue
        if options == '2':
            print("\n [ Version ] "+main.version+"\n")
            continue
        if options == '3':
            print(main.get_file_content(main.runPath+"/../resource/About.md",False))
            continue
        if options == '4':
            break
    
    main.main()