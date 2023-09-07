package edu.auburn.cpsc4970.m3;

/**
 * Simple Java Application
 *
 */
public class HelloSonarQube
{
  // We need to updated the constants value_prefix and length_prefix to adhere to statndard java coding practices.
   public static final String VALUE_PREFIX = "Value = ";
   public static final String LENGTH_PREFIX= "Length = ";

   public static void main( String[] args )
   {
      System.out.println( "Hello World!" );
   
      HelloSonarQube helloSonarQube = new HelloSonarQube();
      helloSonarQube.printValue(null);
   }


   private void printValue(String value) {
      // Check if the value is null to avoid the NullpointerException
      if (value == null) {
         System.out.println(VALUE_PREFIX + "No value");
      }
      else {
      // Instead of using System.out we could consider using a logger as SonarQube suggests..
      // The severity of System.out is low in this instance and implementing the logger would make this application more complex then need be.
      // Considering adding the logger would be unnecessary I will mark the smell as "Won't Fix" as the instructions suggest.
   
      // Update contants to new contant names... VALUE_PREFIX and LENGTH_PREFIX...
      System.out.println(VALUE_PREFIX + value + LENGTH_PREFIX + value.length());
      }
   
   }
}
