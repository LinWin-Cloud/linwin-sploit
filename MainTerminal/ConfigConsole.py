import main as main


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
            pass
        if options == '2':
            pass
        if options == '3':
            pass
        if options == '4':
            break
    
    main.main()