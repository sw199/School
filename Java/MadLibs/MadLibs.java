//Scott Wedge
//2020

//This is a Mad Libs game where the user will be promted to enter a unique word/phrase
//based on a word type (verb, noun, adjective, etc..). The users input will create
//a unique story that the user will be able to view.

import java.io.*;
import java.util.*;

public class MadLibs {

   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      boolean stopLibs = true;
      
      System.out.println("Welcome to the game of Mad Libs.");
      System.out.println("I will ask you to provide various words");
      System.out.println("and phrases to fill in a story.");
      System.out.println("The result will be written to an output file.");
      System.out.println();
      
      // boolean stopLibs = true;
      while (stopLibs) { //runs game until user selects "q/Q"
         stopLibs = userMenu(console);
      }  
   }
   
   //user menu method
   public static boolean userMenu(Scanner console) throws FileNotFoundException {
      System.out.print("(C)reate mad-lib, (V)iew mad-lib, (Q)uit? ");
      String selection = console.next(); //prompts user for option 
      if (selection.equalsIgnoreCase("c")) { //create file
         createFile(); //makes input and out put files
         return true; //keeps game going
      
      } else if (selection.equalsIgnoreCase("v")) { //view file
         String whataView = inputFile();
         System.out.println();
         viewFile(console, whataView);
         return true; //keeps game going
      
      } else if (selection.equalsIgnoreCase("q")) { //quit game
         return false; //ends game
      
      } else {
         System.out.println();
         System.out.println("Please enter valid command."); //checks user nonsense
         return true; //keeps game going
      }
   }
   
   //creates and gathers the input/output files, names them, and sends them to be processed
   public static void createFile() throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      String inTake = inputFile(); //gets read file
      System.out.print("Output file name: ");
      String outTake = console.nextLine(); //user gives name for write file
      
      File write = new File(outTake); //creates & names write file
      PrintStream out = new PrintStream(write); //writes
      
      getLines(console, inTake, outTake); //Scanner, read file name, out file name
   
   }
   
   //creates input files to read from
   public static String inputFile() throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      System.out.print("Input file name: ");
      String intake = console.nextLine(); //file name to read from
      File read = new File(intake); //new file
   
      while (!read.exists()) { //checks user nonsense then repeats above
         System.out.print("File not found. Try again: ");
         intake = console.nextLine();
         read = new File(intake);
      }
      return intake; //returns file name back to createFile()
   }
   
   //reads the contents of the file the user selected to be viewed
   public static void viewFile(Scanner console, String view) //scanner & file name user wants
         throws FileNotFoundException {
         
      Scanner input = new Scanner(new File(view)); //file w/ name to be read with by Scanner 
         
      while (input.hasNextLine()) { //read until the end of lines in file
         String words = input.nextLine();
         System.out.println(words); //prints contents of file
      }
      System.out.println();
   }
   
   //grabs the <words> to be modified by user
   public static void getLines(Scanner console, String in, String out) //scanner, i/o file names
         throws FileNotFoundException {
         
      PrintStream output = new PrintStream(new File(out)); //tracks & writes
      Scanner input = new Scanner(new File(in)); //tracks & reads
      
      while(input.hasNextLine()) { //runs to end of file
         String line = input.nextLine(); //grabs lines
         while(line.contains("<") && line.contains(">")) {
            int indexNum = line.indexOf("<"); //uses index # to grab <stuff> from each line
            int indexNumb = line.indexOf(">");
            String save = line.substring(indexNum + 1, indexNumb); //grabs <stuff>
            String userLib = adjust(save); //sends <stuff> for "a" or "an" grammar
            if (indexNum == 0) { //if "<" is 1st char of line
               line = userLib + line.substring(indexNumb + 1); //builds line w/ user input
            } else if (indexNum != 0) { //same as above but if "<" is not 1st char
               line = line.substring(0, indexNum) + userLib + line.substring(indexNumb + 1);
            } else { 
               line = line; //if line does not have any <stuff>
            }
         }
         output.println(line); //prints new lines to write file
      }
      output.close(); //closes the file once finished writing
      System.out.println("Your mad-lib has been created!");
      System.out.println(); 
   }
   
   //grabs first character of users <words> and adjusts grammar
   public static String adjust(String word) //noun, adjective, verb, etc..
         throws FileNotFoundException {
         
      Scanner console = new Scanner(System.in);
      char gram = word.charAt(0); //(n, a, v, etc..)
      String aAn = "";
      if (gram == 'a' || gram == 'e' || gram == 'i' || gram == 'o' || gram == 'u' || 
         gram == 'A' || gram == 'E' || gram == 'I' || gram == 'O' || gram == 'U') {        
         aAn = " an ";
      } else {
         aAn = " a ";
      }
      word = word.replace("<", " ").replace(">", "").replace("-", " "); //strips "< > -"
      System.out.print("Please type" + aAn + word + ": "); //"a" or "an" & "stuff: "
      String userLib = console.nextLine(); //gets user response
      return (userLib); //returns the user response without "< > -"  
   }      
}
