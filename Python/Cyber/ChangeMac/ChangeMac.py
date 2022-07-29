#!/usr/bin/env python

import subprocess
from getopt import *
import sys
import re

#Function that allows for a colored output
def color(r, g, b, text):
    return "\033[38;2;{};{};{}m{} \033[38;2;255;255;255m".format(r, g, b, text)

#Function to display the help message/usage of the program
def Program_Help():
    print(color(255,255,255,"""
    Usage/Help page for the program : ChangeMac.py

    OPTIONS:
    -h or --help                    : Optional, Shows options and how to use them.

    -m or --mac                     : Required, MAC Address you want to set on the Interface you set
    -i or --interface                 ChangeMac.py -m "MAC Address" -i "interface"
                                      ChangeMac.py --mac "MAC Address" --interface "interface"
    
    """))

#Gets the Arguments and options of the CMD input and applies them
def get_arguments():

    try:
        #option map
        options = getopt(sys.argv[1:],
                         shortopts="m:i:h",
                         longopts=["mac=", "interface=", "help"])
    except GetoptError as e:
        print ("ERROR: Wrong option used --> ", e)
        sys.exit(Program_Help())
    
    #Checks if the options where provided
    if options:
        for (opt, args) in options[0]:
            try:
                #Help options
                if opt in ("-h", "--help"):
                    sys.exit(Program_Help())
                #MAC options
                if opt in ("-m", "--mac"):
                    mac = args
                #Interface options
                if opt in ("-i", "--interface"):
                    interface = args
                    
            except ValueError as e:
                sys.exit(Program_Help())
    
    return mac, interface

#Changes the MAC
def change_mac(interface, mac_addr):
    print(color(0,0,255,"[+] Changing MAC Address for") + interface + color(0,0,255," to") + mac_addr)
    #Uses the subprocess command to turn the chosen interface down, sets the MAC, then turns it back on
    subprocess.call(["ifconfig", interface, "down"])
    subprocess.call(["ifconfig", interface, "hw", "ether", mac_addr])
    subprocess.call(["ifconfig", interface, "up"])

#Find the current MAC 
def get_current_mac(interface):
    ifconfig_result = subprocess.check_output(["ifconfig", interface])
    mac_search_result = re.search(r"\w\w:\w\w:\w\w:\w\w:\w\w:\w\w", str(ifconfig_result))
    if mac_search_result:
        return mac_search_result.group(0)
    else:
        print(color(255,0,0,"[-] No MAC Address found"))

#Try catch to see if options where provided or not
try:
    mac_addr, interface = get_arguments()
except UnboundLocalError:
    print(color(255,0,0,"\nINVALID USE OF PROGRAM! Use -h or --help for info.\n"))
    sys.exit(Program_Help())

current_mac = get_current_mac(interface)
print(color(0,255,0,"Current MAC ->") + str(current_mac))

change_mac(interface, mac_addr)
current_mac = get_current_mac(interface)

if current_mac == mac_addr:
    print(color(0,255,0,"[+] MAC Address successfully changed to:") + mac_addr)
else:
    print(color(255,0,0,"[-] MAC Address did not change"))
    
