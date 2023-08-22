Project 1 COMP - 4320
als0184 - Austin Smith
Implementation of a Simple File Transfer Service over the UDP Transport Service
Due Date: 7/31/23

This project is composed of two programs written in C++ (server.cpp and client.cpp) to establish a simple file
transfer protocol (FTP) using User Datagram Protocol (UDP). Both programs communicate over socket 8080 and
loopback IP address (127.0.0.1).

server.cpp:
 - The server.cpp program acts as a server that listens for a GET command sent from the client. On receiving
   the command, it reads the requested file, error handling if required, divides the file into 512-byte packets, and sends these packets to
   the client. The server also includes a custom header with a sequence number inside each packet. The server then prints to the console 
   the sequence number and the first 64 bytes of data of each packet it sends to the client. If the end of the file is reached, a null character packet 
   is sent to the client to notify it that is the end of the file and that is the last packet it will receive and the server closes the file.

client.cpp:
 - The client.cpp program acts as a client that sends a GET 'filename' command to the server. If the file name was valid, it then receives
   a number of 512-byte packets from the server, each packet containing a custom header which includes the seqNum. The client writes the
   received data to a local file and prints the sequence number and the first 64 bytes of data from each packet
   to the console. When the client receives a packet with an end-of-file indicator (null character), it knows that it has
   received the last packet and closes the file it is writing too.

Compilation and Execution:
 - Both programs need to be compiled and ran in separate terminal windows.

- server.cpp:
   Step 1: Open a terminal window.
   Step 2: Navigate to the directory where server.cpp is located. Example: "cd comp-4320/server-fold"
   Step 3: Compile the program using the command:
	    - "g++ server.cpp -o server"
      	   Execute the program with the following command:
	    - "./server"

- client.cpp:
   Step 4: Open a separate terminal window.
   Step 5: Navigate to the directory where client.cpp is located. Example: "cd comp-4320"
   Step 3: Compile the program using the command:
	    - "g++ client.cpp -o client"
      	   Execute the program with the following command:
	    - "./client"
Uses Cases:
(Use Case 1: Successful File Tansfer)
 - Ensure that both server and client are running simultaneously for the file transfer to take place
   correctly. Make sure to run the server before the client, as the client sends the GET request as soon as it
   starts. After you follow the above steps the client will prompt you to enter a file name... "Please enter a
   file name to transfer: " you will then enter the name of a file that exist in the server-fold directory.
   For example the file "TestFile" exist in the server-fold directory so once entered the client will send the
   file name to the server and the server will begin the process of sending 512-byte sized packets. This will
   beindicated in both the server and client console... and once complete the newly assembled file will be
   saved to the project directory "comp-4320" under the name 'filename'_assembled.
   
   example server output of a successful transfer... 
   		Server Application started!
	        Listening for commands...
	        "GET" command received!
	        TestFile successfully opened!
     		Sending Packet 0 to Client
	        First 64 Bytes of Packet 0:
	        <"The first 64 Bytes of Data that will be sent to the Client in this packet">

	        Sending Packet 1 to Client
	        First 64 Bytes of Packet 1:
	        <"The first 64 Bytes of Data that will be sent to the Client in this packet">

	        <"Repeats until the end of file is reached">
	       
    example client output of a successful transfer... 
	        Please enter a file name to transfer: TestFile
	        Recieved Packet 0 from Server
	        First 64 Bytes of Packet 0:
	        <"The first 64 Bytes of Data that will be sent to the Client in this packet">

	        Recieved Packet 1 from Server
	        First 64 Bytes of Packet 1:
	        <"The first 64 Bytes of Data that will be sent to the Client in this packet">

	        <"Repeats until the end of file is transfered">
	       
(Use Case 2: Error Messages in File Tansfer/ Incorrect Input)
 - Ensure that both server and client are running simultaneously to set the stage for the file transfer. 
   Make sure to run the server before the client, as the client sends the GET request as soon as it
   starts. After you follow the above steps the client will prompt you to enter a file name... "Please enter a
   file name to transfer: " if you enter the name of a file that DOES NOT exist in the server-fold directory.
   For example the file "NotinDirec" does not exist in the server-fold directory so once entered the client
   will send the file name to the server and the server will notify the client that this is not a valid file
   by sending an error packet to the client this proccess is indicated in both the server and client
   console... 
   
   example server output of an invalid file transfer... 
   		Server Application started!
	        Listening for commands...
	        "GET" command received!
	        File does not exist in directory: NotinDirec... Send error to client.
     		
	        <"Closes Program">
	       
    example client output of an invalid file transfer... 
	        Please enter a file name to transfer: NotinDirec
	        Error received from server: File does not exist in directory.

	        <"Closes Program">
	       
*** Both use cases will be shown with real output from server and client in my script files included in my submission ***

Scripts:
 - TestFile-Server-Script:
 	This script is the servers point of view/ output when we are transfering a file that is a multiple of 512.
 	This file is exactly 80.384 kB which is 80,384 bytes... 512 x 157 = 80,384.
 	This script follows the creation of the newly transferred file 'TestFile_assembled' in the projects root directory.
 
 - TestFile-Client-Script:
  	This script is the clients point of view/ output when we are transfering a file that is a multiple of 512.
 	This file is exactly 80.384 kB which is 80,384 bytes... 512 x 157 = 80,384.
 	This script follows the creation of the newly transferred file 'TestFile_assembled' in the projects root directory.
 
 - TestFile2-Server-Script:
  	This script is the servers point of view/ output when we are transfering a file that is not a multiple of 512.
 	This file size is 93.7 kB which which is not a multiple of 512.
 	This script follows the creation of the newly transferred file 'TestFile2_assembled' in the projects root directory.
 
 - TestFile2-Client-Script:
   	This script is the clients point of view/ output when we are transfering a file that is not a multiple of 512.
 	This file size is 93.7 kB which which is not a multiple of 512.
 	This script follows the creation of the newly transferred file 'TestFile2_assembled' in the projects root directory.
 
 - Error-Server-Script:
   	This script is the servers point of view/ output when we receive an error for invalid file (File does not exist in servers directory).
 
 - Error-Client-Script:
   	This script is the clients point of view/ output when we receive an error for invalid file (File does not exist in servers directory).
   	
 
Thank you for taking the time to read all of the above, the majority of this readme will be reflected in each of the individual programs (server.cpp and client.cpp)
Along with a list of references and more of a general overview of what each program is responsible for. I spent upwards of 20 hours on this assignment mainly because 
I have never done this before and it was all very new to me, that being said if you have any questions at all please let me know!






