# Usage of Script

This is a python script that scans the given network range and outputs the IP's of connected devices.
It uses ARP (Action Resolution Protocol) to ping all of the connected device on the given network.

```
    Usage/Help page for the program : ScanNetwork.py
    OPTIONS:
    -h or --help                    : Optional, Shows options and how to use them.
    -t or --target                  : Required, Target IP Address you want to scan
                                      network_scanner.py -t 192.168.1.1/24
    
    """
```
## Prerequisite 
You may need to install some packages such as Scapy first before you can run this script.
## Created by Nick Raffel