/* 
 * client.cpp
 * COMP-4320 Project 1
 * als0184 - Austin Smith
 * Due Date: 7/31/23
 *
 * Compile Instructions: (This is also included in the README file in more detail)
 * g++ client.cpp -o client
 * ./client
 * 
 * Implementation:
 * This is a simple file transfer protocol (FTP) client program designed using c++.
 * The program uses (UDP) for communication between server and client over the socket 8080 and 
 * loopback IP address (127.0.0.1).
 * The client is responsible for sending a GET 'filename' command to the server, 
 * reciving a number of 512-byte packets which is including a custom header, and writing the data 
 * to a file in the local repo. The client prints the sequence number and the first 64 bytes of data
 * from each packet it receives to the console. When the client receives a packet with the
 * endOfFile attached it knows that is the last packets and it closes the file.
 *
 * Resources: (Below is a list of resources I used to complete this project and a short description
 * on what the resource was used for.)
 *
 * Reference 1: Piazza was one of my first resources I used... the TA informed us of what header
 * files we should consider using and a sample output... I modeled my output to resemble his.
 *
 * Reference 2: I used geeksforgeeks to gain a general understanding on the process of initializing
 * UDP Server - Client datagram. This also provided me with details on the header files the TA
 * recommended us to use.
 *
 * Reference 3: https://stackoverflow.com/questions/16508685/understanding-inaddr-any-for-socketprogramming 
 * - I used this StackOverflow thread to help me understand the use and significance of bind, htonl,
 * and INADDR_ANY in socket programming. 
 *
 * Reference 4: https://www.geeksforgeeks.org/cpp-cheatsheet/#
 * - The above source is just c++ syntax and general how too code in c++.
 *   The last time I used c++ was in Dr.li's class 'Software Construction' which was over a year ago
 *   so I needed a quick refresher. This site also included a section about the header files which
 *   was extremly helpful. 
 *
 * Reference 5: https://cplusplus.com/doc/tutorial/files/
 * - The above link provided me with a clear understanding on file handling and details on opening
 *   reading, and writing files in c++ which was essential for handling the file transfers in this
 *   project. 
 *
 * Reference 6: Class resources such as notes/lectures and textbook... I used these resoursces to
 *   gain general knowledge on File Transfer Protocol and UPD transport services and over all the
 *   understanding of this project.
 */

#include <iostream>

#include <fstream>

#include <cstdlib>

#include <cstring>

#include <unistd.h>

#include <sys/types.h>

#include <sys/socket.h>

#include <netinet/in.h>

#include <arpa/inet.h>

#define MAX_BUFFER_SIZE 448
#define HEADER_SIZE 64
#define TOTAL_SIZE MAX_BUFFER_SIZE + HEADER_SIZE

struct Packet {
  int seqNum;
  bool endOfFile;
  int dataSize;
  char data[MAX_BUFFER_SIZE];
  char filename[64];
  bool fileNotFoundError;
};

//Reference 2 was referenced for the below section of code...
int main() {
  struct sockaddr_in server_add;
  int s, serverAddrSize = sizeof(server_add);
  Packet packet;
 //create a socket...
  if ((s = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) == -1) {
    perror("socket");
    exit(1);
  }
  //server details...
  //Reference 2 was referenced for the below section of code...
  server_add.sin_family = AF_INET;
  server_add.sin_port = htons(8080); //server socket 8080
  if (inet_aton("127.0.0.1", & server_add.sin_addr) == 0) {
    fprintf(stderr, "inet_aton() failed\n");
    exit(1);
  }
  // User input for file name for transfer...
  std::cout << "Please enter filename to transfer: ";
  std::cin.getline(packet.filename, 64);
  packet.seqNum = 0;
  strcpy(packet.data, "GET");
  //Send the file name to server...
  if (sendto(s, & packet, sizeof(packet), 0, (struct sockaddr * ) & server_add, serverAddrSize) == -1) {
    perror("sendto()");
    exit(EXIT_FAILURE);
  }
  //This is the name of the file that the recived data will be assembled into...
  // 'filename'_assembled ex: TestFile_assembled
  std::string receivedFilename = std::string(packet.filename) + "_assembled";
  std::ofstream file;
 //Start while loop to recive packets 
  while (1) {
    if (recvfrom(s, & packet, sizeof(packet), 0, (struct sockaddr * ) & server_add,
        (socklen_t * ) & serverAddrSize) ==-1) {
      perror("recvfrom()");
    }
    // If the file does not exist in the server directory throw an error and close...
    if (packet.fileNotFoundError) {
    std::cerr << "Error received from server: " << packet.data << "\n";
    return 1;
    }
    if (packet.seqNum == 0 && !file.is_open()) {
      //Reference 5: file handling...
      file.open(receivedFilename, std::ios::out | std::ios::binary);
    }
    //Reference 1: Output was modeled from the TA's example on piazza.
    std::cout << "Received Packet " << packet.seqNum << " from Server\n";
    std::cout << "First 64 Bytes of Packet " << packet.seqNum << ":\n";
    for (int i = 0; i < 64 && i < packet.dataSize; i++) {
      std::cout << packet.data[i]; //prints first 64 bytes of the packets data to console
    }
    std::cout << "\n";
    //write data into the file
    //Reference 5: file handling...
    file.write(packet.data, packet.dataSize);
    //Once we reach the end of the file break and close...
    if (packet.endOfFile) {
      break;
    }
  }
  file.close(); //close file...
  close(s); //close socket s...
  
  return 0;
}
