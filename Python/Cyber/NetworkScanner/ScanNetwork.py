#!usr/bin/env python

import scapy.all as scapy
from getopt import *
import sys

# Function that allows for a colored output
def color(r, g, b, text):
    return "\033[38;2;{};{};{}m{} \033[38;2;255;255;255m".format(r, g, b, text)

# Help Message
def Program_Help():
    print(color(255,255,255,"""
    Usage/Help page for the program : network_scanner.py
    OPTIONS:
    -h or --help                    : Optional, Shows options and how to use them.
    -t or --target                  : Required, Target IP Address you want to scan
                                      network_scanner.py -t 192.168.1.1/24
    """))

#Gets the Arguments and options of the CMD input and applies them
def get_arguments():

    try:
        #option map
        options = getopt(sys.argv[1:],
                         shortopts="t:h",
                         longopts=["target=", "help"])
    except GetoptError as e:
        print (color(255,0,0,"ERROR: Wrong option used --> "), e)
        sys.exit(Program_Help())
    
    
    #Checks if the options where provided
    if options:
        for (opt, args) in options[0]:
            try:
                #Help options
                if opt in ("-h", "--help"):
                    sys.exit(Program_Help())
                #Target options
                if opt in ("-t", "--target"):
                    target = args
                    
            except ValueError as e:
                sys.exit(Program_Help())
    
    return target

def scan(ip):
    # Generates the ARP Request
    arp_request = scapy.ARP(pdst=ip) 
    # sets the destination MAC to the broadcast MAC dst = mac destination
    broadcast = scapy.Ether(dst="ff:ff:ff:ff:ff:ff") 
    # contains the info of both the request and the broadcast
    arp_request_broadcast = broadcast/arp_request 
    # sends out the packet, and waits for a response
    response_list = scapy.srp(arp_request_broadcast, timeout=1, verbose=False)[0]

    # List of all targets found
    targets = []
    for nodes in response_list:
        target_dict = {"ip": nodes[1].psrc, "mac": nodes[1].hwsrc}
        targets.append(target_dict)
    return targets

# Print Function
def print_result(result_list):
    print(color(0,255,0,"\n |IP Address\t|\t\tMAC Address\t\t|\tHostname|\n")
          + "|--------------|---------------------------------------|---------------|")
    for client in result_list:
        print(" |" + client["ip"] + "\t|\t\t" + client["mac"] + "\t|\t" + client["ip"] + "|")

#Try catch to see if options where provided or not
try:
    # Gets Target IP from argument
    target = get_arguments()
except UnboundLocalError:
    print(color(255,0,0,"\nINVALID USE OF PROGRAM! Use -h or --help for info."))
    sys.exit(Program_Help())

# Scan result from scanning network
try:
    scan_result = scan(target)
except OSError as e:
    print(color(255,0,0,"ERROR: Did you run the command as a sudoer?"), e)
    sys.exit(Program_Help())

# Prints the results captured from above
print_result(scan_result)
