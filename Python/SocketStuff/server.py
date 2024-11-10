import socket
import subprocess
import os
import struct

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

    # If a color name is provided, use the corresponding RGB values
    if col and col in colors:
        r, g, b = colors[col]

    # Check if RGB values are provided
    if r is not None and g is not None and b is not None and text is not None:
        return "\033[38;2;{};{};{}m{} \033[38;2;255;255;255m".format(r, g, b, text)
    else:
        return text  # Default return of plain text if inputs are missing

def createServerSocket(host='127.0.0.1', port=12345):
    # Create and bind a server socket.
    servSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    servSocket.bind((host, port))
    servSocket.listen(1)
    print(f"{color(col='green', text='Server started!')} Listening for connections...")
    return servSocket

def handleUpload(clientSocket, path):
    try:
        # Check if file exists on the server
        if not os.path.exists(path):
            clientSocket.send(b"ERROR: File not found on server.")
            return

        # Send file size to the client
        file_size = os.path.getsize(path)
        clientSocket.send(struct.pack(">Q", file_size))  # Send file size as an 8-byte integer

        # Send the file name to the client so it knows how to save it
        file_name = os.path.basename(path).encode('utf-8')
        clientSocket.send(struct.pack(">I", len(file_name)) + file_name)

        # Send the file data in chunks
        with open(path, 'rb') as f:
            print(f"Uploading file to client: {path} ({file_size} bytes)")
            while True:
                data = f.read(1024)
                if not data:
                    break  # End of file
                clientSocket.sendall(data)
        print(f"Upload complete: {path}")
    except Exception as e:
        error_message = f"Error during upload: {str(e)}"
        clientSocket.send(error_message.encode('utf-8'))

def handleDownload(clientSocket, path):
    try:
        # Send the download request with the specified file path
        clientSocket.send(f"download {path}".encode('utf-8'))

        # Step 1: Receive the file size (8 bytes)
        raw_size = clientSocket.recv(8)
        if not raw_size:
            raise Exception("Did not receive file size.")
        file_size = struct.unpack(">Q", raw_size)[0]

        # Step 2: Receive the file name length and file name
        raw_name_length = clientSocket.recv(4)
        if not raw_name_length:
            raise Exception("Did not receive file name length.")
        name_length = struct.unpack(">I", raw_name_length)[0]

        file_name = clientSocket.recv(name_length).decode('utf-8')
        if not file_name:
            raise Exception("Did not receive file name.")

        # Step 3: Receive and write file content
        with open(file_name, 'wb') as f:
            print(f"{color(col='yellow', text='Downloading:')} {file_name} ({file_size} bytes)...")
            bytes_received = 0
            while bytes_received < file_size:
                data = clientSocket.recv(1024)
                if not data:
                    break
                f.write(data)
                bytes_received += len(data)
            print(f"{color(col='green', text='Download complete:')} {file_name}")
            clientSocket.send(b"Download complete.")
    except Exception as e:
        error_message = f"Error during download: {str(e)}"
        print(f"{color(col='red', text=error_message)}")
        clientSocket.send(error_message.encode('utf-8'))

def handleClient(clientSocket, address):
    # Handle communication with a connected client.
    print(f"{color(col='green', text='Connection established from:')} {address}")

    try:
        while True:
            message = input(">> ")
            if message.lower() in ["exit", "end connection"]: raise KeyboardInterrupt
            if message.lower() in ["disconnect", "terminate"]:  
                print(f"{color(col='yellow', text='Terminating connection and Server...')}") 
                os._exit(1);
            if message.lower() in ["cls", "clear"]: subprocess.run(message)
            if message.lower().startswith("upload" ): 
                path = message[7:].strip()
                handleUpload(clientSocket, path);
            if message.lower().startswith("download"): 
                path = message[9:].strip()
                handleDownload(clientSocket, path);
            clientSocket.send(message.encode('utf-8'))
            # Attempt to receive output from the client with a timeout
            clientSocket.settimeout(1)  # Set a timeout of 1 second

            # Attempt to receive output from the client
            try:
                output = clientSocket.recv(1024).decode('utf-8')
                if output:  # If output is not empty
                    print(f"{output}")
            except socket.timeout:
                # No data received yet, just continue the loop
                continue
            except ConnectionResetError:
                print(f"Connection lost with {address}")
                break

    except KeyboardInterrupt:
        print(f"{color(col='yellow', text='Connection Closed')} \nListening for connections...")
    except ConnectionResetError as e:
        conResErrorStr = "\n-- Connection Closed. Trying again. Ctrl+C to terminate program fully"
        print(f"{color(col='red', text=str(e))} {conResErrorStr}")
    finally:
        clientSocket.close()

def main():

    servSocket = createServerSocket()

    try:
        while True:
            try:
                clientSocket, address = servSocket.accept()
                handleClient(clientSocket, address)
            except socket.error as e:
                print(f"{color(col='red', text=str(e))} \nListening for connections...")

    except KeyboardInterrupt:
        print("\nShutting down the server...")

    finally:
        servSocket.close()
        print("Server socket closed.")

if __name__ == "__main__":
    main()
