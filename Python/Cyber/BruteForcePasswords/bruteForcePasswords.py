
import sys
from getopt import *

def Program_Help():
    print("""
    Usage/Help page for the program : CMD-inputs.py

    OPTIONS:
    -h or --help                    : Optional, Shows options and how to use them.

    -f or --file                    : Required, The password file you wish to use!
                                      passwordBruteForcer -f "file.name"
                                      passwordBruteForcer --file "file.name"
    """)

#Gets arguments
def get_arguments():

    try:
        #option map
        options = getopt(sys.argv[1:],
                         shortopts="f:h",
                         longopts=["file=", "help"])
    except GetoptError as e:
        print ("[-] ERROR: ", e)
        sys.exit(Program_Help())

    file = ""
    if options:
        for (opt, args) in options[0]:

            #Help options
            if opt in ("-h", "--help"):
                sys.exit(Program_Help())
            #File Name option
            if opt in ("-f", "--file"):
                file = args
    
    return file

def getPasswords(passwordFile):
    try:
        with open(passwordFile, "r") as file:
            p = [line.strip() for line in file]
        return p
    except FileNotFoundError as e:
        sys.exit("[-] ERROR:\tPossible that no File was specified or not found.\n\t\tUse -h or --help for help!\n")

filename = get_arguments()
passwords = getPasswords(filename)
for password in passwords: print(password)