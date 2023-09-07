/*
 *@author Austin Smith (als0184@auburn.edu)
 *@author Lyna Capuano (lrc0038@auburn.edu)
 *COMP 3270 - Programming Assignment
 *Professor - Yilmaz
 *Date - 11/1/22, Due Date - 11/18/22
 *Launguage - Java, IDE - JGRASP
 *
 *References - Are listed throughout the program where applicable.
 *Additional References:
 *https://www.geeksforgeeks.org/arraylist-array-conversion-java-toarray-methods/
 *https://stackoverflow.com/questions/5204051/how-to-calculate-the-running-time-of-my-program
 *https://codegym.cc/groups/posts/matrix-in-java-2d-arrays
 */
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Random;
       
public class COMP3270_Programming_Assignment {
//Step 1: Read from a file named "phw_input.txt", create an array, 
//run each of the algorithms on that input array, and print out the answer produced by each on the console.
   
//References: https://www.geeksforgeeks.org/read-file-into-an-array-in-java/
//https://www.geeksforgeeks.org/split-string-java-examples/
   public static void main(String[] args) 
      throws IOException {
     // list that holds integers of a file
      ArrayList<Integer> input = new ArrayList<>();
      //Reading from file labled "phw_input.txt"
      BufferedReader reads = new BufferedReader(new FileReader("phw_input.txt"));
      //iterates through list and splits when it finds "," symbol indicating new number. 
      //Resources: https://www.geeksforgeeks.org/split-string-java-examples/
      String str = null;
      while ((str = reads.readLine()) != null) { 
         String[] arrstr = str.split(",");         
         for (String a : arrstr) {
            int ele = Integer.parseInt(a);
            input.add(ele);
         }     
      }
      reads.close();
      
      //converts ArrayList to array[]
      Integer[] inputArr = new Integer[input.size()];
      inputArr = input.toArray(inputArr);
      
      //Displays MSCS obtained by each algorithm.
      System.out.println("Array containing 10 integers from input file: \n" + input);
      System.out.println("\nAfter running each of the algorithms on the input array we find the MSCS determined by each of the algorithms: ");
      System.out.println("Algorithm-1:\t" + algorithm1(inputArr));
      System.out.println("Algorithm-2:\t" + algorithm2(inputArr));
      System.out.println("Algorithm-3:\t" + maxSum(inputArr, 0, 9));
      System.out.println("Algorithm-4:\t" + algorithm1(inputArr));
   
   //Next, create 19 integer sequences of length 10,15,20,25,......90,95,100, containing randomly generated 
   //negative, zero and positive integers, and store these in 19 arrays of size 10,15,...,95,100: A1-A19.
        
      
      //measures time of algorithms
      int[][] timeMatrix = new int[19][8];
      ArrayList<Integer[]> randArray = randomArray();
      
      //Time for Algorithm 1
      for (int g = 0; g < 1000; g++){  
         for (int h = 0; h < 19; h++) {
            long startTime = System.nanoTime();
            algorithm1(randArray.get(h));
            long endTime = System.nanoTime();
            long lengthOfTime =  endTime - startTime;
            int timeOfAlg = (int) (lengthOfTime); 
            timeMatrix[h][0] = timeMatrix[h][0] + timeOfAlg;
         }
      }
      for (int h = 0; h < 19; h++){
         timeMatrix[h][0] = ((timeMatrix[h][0]) / 1000);
      }
      
      //Time for Algorithm 2
      for (int g = 0; g < 1000; g++){
         for (int h = 0; h < 19; h++) {
            long startTime = System.nanoTime();
            algorithm2(randArray.get(h));
            long endTime = System.nanoTime();
            long lengthOfTime =  endTime - startTime;
            int timeOfAlg = (int) (lengthOfTime); 
            timeMatrix[h][1] = timeMatrix[h][1] + timeOfAlg;
         }
      }
      for (int h = 0; h < 19; h++){
         timeMatrix[h][1] = ((timeMatrix[h][1]) / 1000);
      }
      
      //Time for Algorithm 3
      for (int g = 0; g < 1000; g++){
         for (int h = 0; h < 19; h++) {
            long startTime = System.nanoTime();
            maxSum(randArray.get(h), 0, ((randArray.get(h)).length - 1));
            long endTime = System.nanoTime();
            long lengthOfTime =  endTime - startTime;
            int timeOfAlg = (int) (lengthOfTime); 
            timeMatrix[h][2] = timeMatrix[h][2] + timeOfAlg;
         }
      }
      for (int h = 0; h < 19; h++){
         timeMatrix[h][2] = ((timeMatrix[h][2]) / 1000);
      }
      
      //Time for Algorithm 4
      for (int g = 0; g < 1000; g++){
         for (int h = 0; h < 19; h++) {
            long startTime = System.nanoTime();
            algorithm4(randArray.get(h));
            long endTime = System.nanoTime();
            long lengthOfTime =  endTime - startTime;
            int timeOfAlg = (int) (lengthOfTime); 
            timeMatrix[h][3] = timeMatrix[h][3] + timeOfAlg;
         }
      }
      for (int h = 0; h < 19; h++){
         timeMatrix[h][3] = ((timeMatrix[h][3]) / 1000);
      }
    
      //Calculates ceiling(T(n))
      int n = 10; 
      while (n < 101) {
         for (int h = 0; h < 19; h++) {
         //Algorithm 1 ceiling(T(n))
            int T1 = (int)((((7*(Math.pow(n,3)))+(30*(Math.pow(n,2)))+(23*n))/6)+n+7);
            timeMatrix[h][4] = T1;
            n = n + 5;
         }
      }
      
      n = 10; 
      while (n < 101) {
         for (int h = 0; h < 19; h++) {
         //Algorithm 2 ceiling(T(n))
            int T2 = (int)((7*(Math.pow(n,2)))+(9*n)+5);
            timeMatrix[h][5] = T2;
            n = n + 5;
         }
      }
      
      n = 10; 
      while (n < 101) {
         for (int h = 0; h < 19; h++) {
         //Algorithm 3 ceiling(T(n))
            int T3 = (int)(((13*(Math.pow(n,2)))+(31*n)-32));
            timeMatrix[h][6] = T3;
            n = n + 5;
         }
      }
      
      n = 10; 
      while (n < 101) {
         for (int h = 0; h < 19; h++) {
         //Algorithm 4 ceiling(T(n))
            int T4 = (int)((15*n)+5);
            timeMatrix[h][7] = T4;
            n = n + 5;
         }
      }
   
      //Prints matrix
      /**for (int h = 0; h < 19; h++) {
         System.out.println("\n");
         for (int g = 0; g < 8; g++) {
            System.out.print(timeMatrix[h][g]);
            if (h < 19 && g < 7) {
               System.out.print(",");
            }
         }
      }*/
   //Finally, Your main program should write one text line of algorithm and complexity order titles separated by 
   //commas (e.g., "algorithm-1,algorithm-2,algorithm-3,algorithm-4,T1(n),T2(n),T3(n), T4(n)"), followed by the 
   //above matrix also in comma-delimited format (19 lines of 8 integers separated by commas) to a file called 
   //"yourname_phw_output.txt".
      String line = "algorithm-1,algorithm-2,algorithm-3,algorithm-4,T1(n),T2(n),T3(n),T4(n)";
      BufferedWriter writer = new BufferedWriter(new FileWriter("AustinSmith_phw_output.txt"));      
      writer.write(line);
      writer.newLine();
      for (int h = 0; h < 19; h++) {
         for (int g = 0; g < 8; g++) {
            writer.write(timeMatrix[h][g] + ",");
         }
         writer.newLine();
      }
      writer.close();
   }
   
   
 //creates an arrayList of arrays of random integers 
   public static ArrayList<Integer[]> randomArray(){
      ArrayList<Integer[]> rArray = new ArrayList<Integer[]> ();
      Random rand = new Random();
      int j = 10;
      while (j < 101) {
         ArrayList<Integer> arr = new ArrayList<Integer>(); 
         for (int k = 0; k < j; k++) {  
            //range of random numbers -100 to 100                                      
            int randomNum = rand.nextInt(201) - 100;
            arr.add(randomNum);
         }
         Integer[] arrToAdd = new Integer[arr.size()];
         arrToAdd = arr.toArray(arrToAdd);
         rArray.add(arrToAdd);
         j = j + 5;
      } 
      return rArray;
   }


//Decided to get rid of the max method which is imbedded in java. 
//Since we were unable to calculate cost of each execution we implemented our own max method. 
   public static int max(int a, int b) { 
      if (a >= b)
         return a;
      else {
         return b;
      }
   }
   
   public static int max(int a, int b, int c) { 
      if (a >= b)
         if (a >= c) {
            return a;
         }
         else {
            return c;
         }
      else {
         if (b >= c) {
            return b;
         }
         else {
            return c;
         }
      }
   }
   
    //Given Algorithms
    //Algorithm-1(X : array[P..Q] of integer)
   public static int algorithm1(Integer[] X) {
      //P = beginning index of array
      int P = 0;
      //Q = end index of array 
      int Q = X.length;
     
      int maxSoFar = 0;
      for(int L = P; L < Q; L++) {
         for(int U = L; U < Q; U++) {
            int sum = 0;
            for(int I = L; I <= U; I++)   
               sum = sum + X[I];     
            maxSoFar = max(maxSoFar, sum);
         }
      }
      return maxSoFar;
   }
   
  //Algorithm-2(X : array[P..Q] of integer)
   public static int algorithm2(Integer[] X) {
      //P = beginning index of array
      int P = 0;
      //Q = end index of array 
      int Q = X.length;
      
      int maxSoFar = 0;
      for(int L = P; L < Q; L++) {
         int sum = 0;
         for(int U = L; U < Q; U++) {
            sum = sum + X[U];        
            maxSoFar = max(maxSoFar, sum);
         }
      }
      return maxSoFar;
   }
   
   //Algorithm-3 recursive function MaxSum(X[L..U]: array of integer, L, U: integer)
   public static int maxSum(Integer[] X, int L, int U) {
      if (L > U)
         return 0;                        
      if (L == U)
         return max(0, X[L]);                  
      
      int M = (L + U) / 2;                
      
      //Find max crossing to left
      int sum = 0;
      int maxToLeft = 0;
      for(int I = M; I >= L; I--) {
         sum = sum + X[I];
         maxToLeft = max(maxToLeft, sum);
      }
      
      //Find max crossing to right
      sum = 0;
      int maxToRight = 0;
      for(int I = M + 1; I <= U; I++) {
         sum = sum + X[I];
         maxToRight = max(maxToRight, sum);
      }
      
      int maxCrossing = maxToLeft + maxToRight;
      
      int maxInA = maxSum(X, L, M);
      int maxInB = maxSum(X, (M + 1), U);
      
      //Can not use the max method since we are comparing more then two numbers. 
      //Need to create a method for finding the max of three numbers...
      return max(maxCrossing, maxInA, maxInB);       
   }
   
   //Algorithm-4(X : array[P..Q] of integer)
   public static int algorithm4(Integer[] X) {
      //P = beginning index of array
      int P = 0;
      //Q = end index of array 
      int Q = X.length;
      
      int maxSoFar = 0;
      int maxEndingHere = 0;
      for (int I = P; I < Q; I++) {
         maxEndingHere = max(0, maxEndingHere + X[I]);
         maxSoFar = max(maxSoFar, maxEndingHere);
      }
           
      return maxSoFar;
   }
}