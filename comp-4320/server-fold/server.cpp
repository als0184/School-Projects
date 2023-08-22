/* 
 * server.cpp
 * COMP-4320 Project 1
 * als0184 - Austin Smith
 * Due Date: 7/31/23
 * 
 * Compile Instructions: (This is also included in the README file in more detail)
 * g++ server.cpp -o server
 * ./server
 * Please note that each program will need to be compiled and ran in seperate terminal windows.
 *
 * Implementation:
 * This is a simple file transfer protocol (FTP) server program designed using c++.
 * The program uses (UDP) for communication between server and client over the socket 8080 and 
 * loopback IP address (127.0.0.1).
 * The server is responsible for listening for a GET command sent from the client, reading in the 
 * requested file, dividing the file into 512-byte packets, and sending these packets to the client. 
 * I also included a custom header which includes sequence number.
 * The server will then print to the console the sequence number and the first 64 bytes of data
 * of each packet it sends. When the end of the file is reached the server closes the file.
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
#define TOTAL_SIZE MAX_BUFFER_SIZE + HEADER_SIZE // 448 + 64 = 512

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
  struct sockaddr_in server_info, client_add;
  int s, clientAddrSize = sizeof(client_add);
  Packet packet;

  if ((s = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) == -1) {
    perror("socket");
    exit(1);
  }
  //Reference 2 was referenced for the below section of code...
  server_info.sin_family = AF_INET;
  server_info.sin_port = htons(8080); //server socket 8080 client.cpp will be using the same socket
  server_info.sin_addr.s_addr = htonl(INADDR_ANY);
  //Reference 3 was referenced for the below section of code... 
  if (bind(s, (struct sockaddr * ) & server_info, sizeof(server_info)) == -1) {
    perror("bind");
    exit(1);
  }
  //Reference 1: Output was modeled from the TA's example on piazza.
  std::cout << "Server Application started\n";
  std::cout << "Listening for commands...\n";

  if (recvfrom(s, & packet, sizeof(packet), 0, (struct sockaddr * ) & client_add, (socklen_t * ) & clientAddrSize) ==
  -1) {
    perror("recvfrom()");
  }
  std::cout << "\"GET\" command received!\n";
  std::string fileName = packet.filename;
  //Reference 5: file handling...
  std::ifstream file(fileName, std::ios::in | std::ios::binary);
  //Check to see if file name exists in directroy if not close program...
  if (!file) {
    std::cerr << "File does not exist in directory: " << fileName << "... Send error to client.\n";
    //create error packet to send to client 
    packet.fileNotFoundError = true;
    strcpy(packet.data, "File does not exist in directory.");
    packet.dataSize = strlen(packet.data) + 1;
    packet.endOfFile = true;
    //Send error packet to client
    if (sendto(s, & packet, sizeof(packet), 0, (struct sockaddr * ) & client_add, clientAddrSize) == -1) {
      perror("sendto()");
      exit(EXIT_FAILURE);
    }
    return 1;
  }
  //If file does exist then we print success message and start the process of sending packets...
  std::cout << fileName << " successfully opened!\n";
  //set seqNum to 0 and begin while loop...
  packet.seqNum = 0;
  while (!file.eof()) {
    file.read(packet.data, MAX_BUFFER_SIZE);
    packet.dataSize = file.gcount();
    packet.endOfFile = file.eof();
    //Reference 1: Output was modeled from the TA's example on piazza.
    std::cout << "Sending Packet " << packet.seqNum << " to Client\n";
    std::cout << "First 64 Bytes of Packet " << packet.seqNum << ":\n";
    for (int i = 0; i < 64 && i < packet.dataSize; i++) {
      std::cout << packet.data[i]; //prints first 64 bytes of the packets data to console
    }
    std::cout << "\n";
    //Send the packet...
    if (sendto(s, & packet, sizeof(packet), 0, (struct sockaddr * ) & client_add, clientAddrSize) == -1) {
      perror("sendto()");
      exit(EXIT_FAILURE);
    }
    //Increment the seqNum for the next packet to be sent...
    packet.seqNum++;
  }
  //Send an empty packet with the endOfFile flag set to true and a null character in data...
  packet.endOfFile = true;
  packet.data[0] = '\0'; //Null character
  packet.dataSize = 1;

  if (sendto(s, & packet, sizeof(packet), 0, (struct sockaddr * ) & client_add, clientAddrSize) == -1) {
    perror("sendto()");
    exit(EXIT_FAILURE);
  }

  file.close(); //close file...
  close(s); //close socket s...

  return 0;
}
