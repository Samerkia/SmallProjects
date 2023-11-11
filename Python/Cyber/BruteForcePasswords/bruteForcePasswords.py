
import sys
from getopt import *

#Program help
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

def crackPassword(password):
    rp = "special"
    if password == rp:
        print(f"{password:<{20}} is a match. Password found!")
        exit()
    else:
        print(f"{password:<{20}} was NOT a match!")

def printResults():
    print(f"{'Password':<{20}} Status")
    print("--------------------------------------")
    for password in passwords: crackPassword(password)

filename = get_arguments()
passwords = getPasswords(filename)
printResults()
