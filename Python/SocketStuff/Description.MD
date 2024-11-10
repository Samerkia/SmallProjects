# TCP Server Client Communication Protocol
This is a TCP Server Client communication software. 
The server listens for incoming client communications and can send commands to the client.
## Commands
Basic Shell commands
    - ls, dir
    - cd
    - pwd
    - grep
    - etc
Custom commands
    - exit / end connection  --> Suppose to simply end the server communication side only [Not fully working as intended yet]
    - disconnect / terminate  -> Closes the connection between server and client (NOTE: Client will need to restart unfortunately [Can be useful though])
    - upload  -----------------> Upload a file to client
    - download  ---------------> Download a file from the client
    - help  -------------------> Shows this dialogue