import sys
from getopt import *
from unittest.mock import DEFAULT


def Program_Help():
    print("""
    Usage/Help page for the program : CMD-inputs.py

    OPTIONS:
    -h or --help                    : Optional, Shows options and how to use them.

    -t or --text                    : Optional, Text you would like to be displayed. Default: 'Hello World!'
                                      CMD-inputs.py -t "your text"
                                      CMD-inputs.py --text "your text"

    -p or --prints                  : Optional, Amount of times your text will be printed. Default: 1
                                      CMD-inputs.py -p #
                                      CMD-inputs.py --prints #
    
    """)

#Gets arguments
def get_arguments():

    try:
        #option map
        options = getopt(sys.argv[1:],
                         shortopts="t:p:h",
                         longopts=["text=", "prints=", "help"])
    except GetoptError as e:
        print ("ERROR: Wrong option used --> ", e)
        sys.exit(Program_Help)

    text = DEFAULT_TEXT
    prints = DEFAULT_PRINTS
    if options:
        for (opt, args) in options[0]:

            #Help options
            if opt in ("-h", "--help"):
                sys.exit(Program_Help())
            #Text option
            if opt in ("-t", "--text"):
                text = args
            #Print options
            try:
                
                if opt in ("-p", "--prints"):
                    prints = int(args)

            except ValueError as e:
                print("ERROR: wrong value used. MUST BE AN INT --> ", e)
                sys.exit(Program_Help())
    
    return text, prints


DEFAULT_TEXT = "Hello World!"
DEFAULT_PRINTS = 1
custom_text, custom_prints = get_arguments()
for i in range(custom_prints):
    print(custom_text)
