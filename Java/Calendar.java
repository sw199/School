//Scott Wedge
//2020

/////////////////////////////////////////////////////////////////////////////
//       The user will have the option to input various commands that      // 
//             execute different behavoirs for the calendar.               //
/////////////////////////////////////////////////////////////////////////////

import java.util.*;
import java.io.*;

public class MyCalendar {

   public static final int SIZE = 10;  

   public static void main(String[] args) throws FileNotFoundException {
   
      String[][] globalE = globalBuild(); //globalE = global calendar events array
      File read = new File("calendarEvents.txt");
      if (read.exists()) {
         commonEvents(globalE, read); //puts events in globalE array
      }
      userMenu(); //user options for navigating calendar
      Scanner input = new Scanner(System.in);
      String command = input.next();
      boolean isCalendarPresent = false;
      int carryMonth = 0; //assumes user's month # or current month # for 'n' & 'p'
      int carryDay = 0; //assumes user's month# or current month # for 'n' & 'p'
      
      //takes user input and descides how or which calendar to print
      while (!command.equals("q")) {
      
         if (command.equals("e")) {
            isCalendarPresent = true;
            String date = getMmDd(input);
            int fromMonth = monthFromDate(date);  //'mm' from mm/dd 
            int fromDay = dayFromDate(date);      //'dd' from mm/dd 
            mountScapeHwy();  //ascii art
            drawMonth(fromMonth, fromDay, globalE);   //draws calendar for user input
            displayDate(fromMonth, fromDay); //prints Month:mm & Day:dd under user input calendar
            carryMonth = fromMonth; //passes user's month # to commands 'n' & 'p' 
            carryDay = fromDay;  //passes user's day # to commands 'n' & 'p'
            userMenu();
            command = input.next();
                     
         } else if (command.equals("t")) {
            isCalendarPresent = true;
            System.out.println("The current month is:");
            String date = currentDate();
            int fromMonth = monthFromDate(date);
            int fromDay = dayFromDate(date);
            mountScapeHwy();  //ascii art
            drawMonth(fromMonth, fromDay, globalE); //draws calendar for current date
            displayDate(fromMonth, fromDay); //prints Month:mm & Day:dd under calendar
            carryMonth = fromMonth;  //passes current month # to commands 'n' & 'p'
            carryDay = fromDay;  //passes current day # to commands 'n' & 'p'           
            userMenu();
            command = input.next();
         
         } else if (command.equals("n") && isCalendarPresent == true) {
            System.out.println("The next month is:");
            carryMonth++; //if 'n' is entered +1 to month #
            if (carryMonth > 12) { 
               carryMonth -= 12; //wraps month to January(1) if > December(12)
            } 
            mountScapeHwy();  //ascii art          
            drawMonth(carryMonth, carryDay, globalE); //draws next calendar
            displayDate(carryMonth, carryDay); //prints Month:mm & Day:dd under calendar
            userMenu();
            command = input.next();       
         
         } else if (command.equals("p") && isCalendarPresent == true) {
            System.out.println("The previous month is:");
            carryMonth--; //if 'p' is entered -1 to month #
            if (carryMonth < 1) { 
               carryMonth += 12; //wraps month to December(12) if < January(1)
            }
            mountScapeHwy();  //ascii art
            drawMonth(carryMonth, carryDay, globalE); //draws previous calendar
            displayDate(carryMonth, carryDay); //prints Month:mm & Day:dd under calendar
            userMenu();
            command = input.next();
            
         } else if (command.equals("ev")) {            
            evCommand(globalE); //adds users event to global events array
            System.out.println("Calendar event has been created!");
            userMenu();
            command = input.next();   
                     
         } else if (command.equals("fp")) { 
            PrintStream fileOut = System.out;
            String date = getMmDd(input); //prompts user for mm/dd
            System.out.print("Please select a name for your output file: ");
            String writeFile = input.next(); //gets users name for output file
            fpCommand(globalE, input, fileOut, date, writeFile); 
            System.out.println();
            System.out.println("Your calendar month has been saved to the file: " + "\"" + 
               writeFile + "\"");      
            userMenu();
            command = input.next();
            
         } else if (command.equals("p") || command.equals("n") && isCalendarPresent == false) {
            System.out.println("You must display a calendar first (\"e\" or \"t\")."); 
            command = input.next(); //checks the user for 'e' or 't'      
         
         } else {   //makes user respect valid command options
            System.out.println("Please enter a valid command.");
            userMenu();
            command = input.next();
         }
      }
                                               
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //                         user menu for various commands                         //
   ////////////////////////////////////////////////////////////////////////////////////
   public static void userMenu() {
      System.out.println();
      System.out.println("Please type a command");      
      System.out.println("\t \"e\" to enter a date and display the corresponding calendar");
      System.out.println("\t \"t\" to get todays date and display the today's calendar");
      System.out.println("\t \"n\" to display the next month");
      System.out.println("\t \"p\" to display the previous month");
      System.out.println("\t \"ev\" to create a new event on the calendar");
      System.out.println("\t \"fp\" to save the calendar month to a file");
      System.out.println("\t \"q\" to quit the program");
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //          formats the global events array for a length of 12  for each          //
   //            month, and contains month length in days for each month             //
   ////////////////////////////////////////////////////////////////////////////////////
   public static String[][] globalBuild() {
      String noNull = "";
      for(int blank = 0; blank < SIZE; blank++) {
         noNull += " "; //makes string noNull w/ SIZE amount of " "
      }
      String[][] formatArray = new String[12][]; //creates jagged array w/ 12 rows (0 - 11)
      for(int month = 1; month <= 12; month++) { //each array row represents a month (0 == JAN)
         formatArray[month - 1] = new String[daysMonth(month)]; //creates array column length
         for(int clear = 0; clear < formatArray[month - 1].length; clear++) {
            formatArray[month - 1][clear] = noNull; //replaces "null" with "blank spaces"
         }
      }
      return formatArray; //return array w/ formatted rows/columns            
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //        grabs events from file and adds them to the global event array          //
   ////////////////////////////////////////////////////////////////////////////////////
   public static void commonEvents(String[][] globalE, File read) throws FileNotFoundException {
      Scanner reading = new Scanner(new File("calendarEvents.txt"));
      while (reading.hasNextLine()) { //runs until end of events in events file
         String commonEvent = reading.nextLine();
         String eventDate = commonEvent.substring(0, 5); //gets MM/DD from each line in file
         int fromMonth = monthFromDate(eventDate);  //'mm' from mm/dd 
         int fromDay = dayFromDate(eventDate);      //'dd' from mm/dd 
         String eventName = commonEvent.substring(6).replace("_", " ");
         globalE[fromMonth - 1][fromDay - 1] = eventName; //adds events to global array
      }
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //       prompts user for name & date of their new event for the calendar         //
   ////////////////////////////////////////////////////////////////////////////////////
   public static void evCommand(String[][] globalE) {
      Scanner console = new Scanner(System.in);
      System.out.print("What is the name and date of your event? (\"MM/DD event_title\"): ");
      String userEvent = console.nextLine();
      String eventDate = userEvent.substring(0, 5); //gets MM/DD from String userEvent
      int fromMonth = monthFromDate(eventDate);  //'mm' from mm/dd 
      int fromDay = dayFromDate(eventDate);      //'dd' from mm/dd
      String eventName = userEvent.substring(6).replace("_"," ");
      globalE[fromMonth - 1][fromDay - 1] = eventName; //adds user event to global array
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //       Gets global events array, Scanner, output PrintStream, users mm/dd,      //
   //          and the users output filename to build the calendar month and         //
   //                      print/save it to an external file.                        //
   ////////////////////////////////////////////////////////////////////////////////////
   public static void fpCommand(String[][] globalE, Scanner input, PrintStream fileOut,
         String date, String writeFile) throws FileNotFoundException {
         
      PrintStream writer = new PrintStream(writeFile);            
      int fromMonth = monthFromDate(date);  //'mm' from mm/dd 
      int fromDay = dayFromDate(date);      //'dd' from mm/dd 
      System.setOut(writer); //sets the caledar month to be saved to file
      mountScapeHwy();  //ascii art
      drawMonth(fromMonth, fromDay, globalE); //draws calendar for user input
      displayDate(fromMonth, fromDay); //prints Month:mm & Day:dd under user calendar
      System.setOut(fileOut); //ends PrintStream 
      writer.close(); //closes file
   }
   ////////////////////////////////////////////////////////////////////////////////////
   //                    gets current date from calendar object                      //
   ////////////////////////////////////////////////////////////////////////////////////
   public static String currentDate() {
      String dateToday = "";
      Calendar dates = Calendar.getInstance();   //Calendar object for current date
      dateToday += (dates.get(Calendar.MONTH) + 1);  
      dateToday += "/" + dates.get(Calendar.DATE);      
      return dateToday;
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //      gets user input for command "e" & "ev", uses Scanner as a parameter,      //
   //                validates user input is proper format (mm/dd)                   //
   //                      returns mm/dd of user choice                              //
   ////////////////////////////////////////////////////////////////////////////////////
   public static String getMmDd(Scanner input) { //Scanner for user input
      String userDate = "";
      while (true) { //forces user to enter proper date format without crashing program
         System.out.print("What date would you like to look at? ");
         userDate = input.next();
         if (userDate.length() == 5 && userDate.charAt(2) == '/' && //restricts format to mm/dd
              Integer.parseInt(userDate.substring(0,2)) >= 1 &&  //prevents month entry < 1
              Integer.parseInt(userDate.substring(0,2)) <= 12 && //prevents month entry > 12
              Integer.parseInt(userDate.substring(3,5)) >= 1 &&  //prevents day entry < 1
              Integer.parseInt(userDate.substring(3,5)) <= 31) { //prevents day entry > 31                 
         return userDate;
         } else {
            System.out.println("Please enter a valid date (mm/dd) to proceed.");
         }
      }
   }
      
   ////////////////////////////////////////////////////////////////////////////////////
   //      returns the amount of days in given month (29,30, or 31) (LeapYear)       //
   ////////////////////////////////////////////////////////////////////////////////////
   public static int daysMonth(int monthNum) { //month by # value
      int dayNumMonth = 0;
      if (monthNum == 4 || monthNum == 6 || monthNum == 9 || monthNum == 11) {
         dayNumMonth = 30; //April, June, September, November
      } else if (monthNum == 2) {
         dayNumMonth = 29; //February on leap year
      } else {
         dayNumMonth = 31; //remaining months
      }
      return dayNumMonth; //returns the day amount for each month           
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //         returns the day that each month starts on when given the month         //
   ////////////////////////////////////////////////////////////////////////////////////
   public static int firstDays(int monthNum) { //month by # value
      int startDay = 0;
      if (monthNum == 1 || monthNum == 4 || monthNum == 7) {
         startDay = 3; //1st day of month is Wednesday (Jan, Apr, Jul)
      } else if (monthNum == 2 || monthNum == 8) {
         startDay = 6; //1st day of month is Saturday (Feb, Aug)
      } else if (monthNum == 3 || monthNum == 11) {
         startDay = 0; //1st day of month is Sunday (Mar, Nov)
      } else if (monthNum == 5) {
         startDay = 5; //1st day of month is Friday (May)
      } else if (monthNum == 6) {
         startDay = 1; //1st day of month is Monday (Jun)
      } else if (monthNum == 9 || monthNum == 12) {
         startDay = 2; //1st day of month is Tuesday (Sep, Dec)
      } else if (monthNum == 10) {
         startDay = 4; //1st day of month is Thursday (Oct)
      }
      return startDay; //returns the start day for each month
   }
    
   //////////////////////////////////////////////////////////////////////////////////// 
   //         Displays the month and a graphical ascii image of the calendar         //
   ////////////////////////////////////////////////////////////////////////////////////
   public static void drawMonth(int month, int day, String[][] globalE) { //month & current day #
      for(int i = 1; i <= SIZE * 3.4; i++) {  //spacing for month # above each calendar
         System.out.print(" ");                   
      }
      System.out.println(month); //month # above each calendar 
      
      for(int spce = 1; spce <= SIZE / 2 - 1; spce++) { //calculates spacing for "SUN"
         System.out.print(" ");
      }
      System.out.print("SUN");  
      headerDays("MON");       
      headerDays("TUE");
      headerDays("WED");   //The rest of the calendar header days
      headerDays("THU");
      headerDays("FRI");
      headerDays("SAT");
      System.out.println();
      
      int startDay = firstDays(month);  //gets the day that each month starts on
      int daysInMonth = daysMonth(month); //gets the amount of days within a given month
      if (month == 5 || month == 8) {  //April(5) & August(8)require sixth row to be proper
         for(int row = 1; row <= 6; row++) {
            drawRow(row, month, startDay, daysInMonth, day, globalE);
         }
      } else {    
         for(int row = 1; row <= 5; row++) { //calls drawRow to create 5 rows.
            drawRow(row, month, startDay, daysInMonth, day, globalE);
         }
      } 
      drawEqLine();  //draws calendar bottom line   
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //     draws 5 or 6 ascii rows that form a calendar image with #'s in each box    //
   ////////////////////////////////////////////////////////////////////////////////////
   //row = ascii calendar rows, month = month #, startDay = day month begins on,
   //daysInMonth = amount of days in month, day = specific day #
   public static void drawRow(int row, int month, int startDay, int daysInMonth,
                              int day, String[][] globalE) { 
                              
      drawEqLine();  //draws calendar top line            
      int morrow = 0;
      String offset = "";
      for(int box = 1; box <= 7; box++) { //spacing for #'s in each calendar box
         morrow = ((row * 7) - 7 + box - startDay);
         if (morrow > daysInMonth || morrow < 1) { //for: [#>31 && #<1] replaces # with " "          
            System.out.print("| " + offset); //'offset' replaces # value that 'morrow' would place
            int intOffset = offset.length(); //controls spacing for empty cells
            for(int space = 1; space <= (SIZE - intOffset - 2); space++) {
               System.out.print(" ");
            } 
                   
         } else { //print remainder of ascii calendar (same as calendar part 1)            
            int moLength = String.valueOf(morrow).length();            
            if (morrow == day) { //prints '**' next to the # in the current/selected day box
               System.out.print("| " + morrow + "**"); 
               for(int spacey = 1; spacey <= (SIZE - 4 - moLength); spacey++) {
                  System.out.print(" ");
               }
                           
            } else {            
               System.out.print("| " + morrow);  //prints | and one space and day number            
               for(int spacey = 1; spacey <= (SIZE - 2 - moLength); spacey++) { 
                  System.out.print(" "); //makes 4 spaces after number
               }
            } 
         }
      } 
      System.out.println("|");                            
      for(int pip = 0; pip <= (SIZE / 2) - 3; pip++) { //runs 3 times if SIZE = 10
         for(int pipe = 0; pipe <= 6; pipe++) {
            System.out.print("|"); //prints 7 pipes
            for(int space = 0; space <= SIZE - 2; space++) { //prints 5 spaces
               System.out.print(" ");
            }
         }
         System.out.println("|"); //prints 8th/final pipe for row
      }
      System.out.print("|");
      for(int box = 1; box <= 7; box++) { //same loop as the above for spacing #'s in calendar
         morrow = ((row * 7) - 7 + box - startDay);
         String event = ""; //assumes event value from array
         String holdE = ""; //stores event title for specific date
         if (morrow > daysInMonth || morrow < 1) {   
            event = "";   
         } else {
            event = globalE[month - 1][morrow - 1];
            for(int spell = 0; spell < SIZE - 1; spell++) {
               holdE += event.charAt(spell); //grabs event string letter by letter
            }
            event = holdE;
         }
         System.out.print(event); //prints event or several spaces
         for(int space = 0; space <= SIZE - event.length() - 2; space++) {
            System.out.print(" ");
         }
         System.out.print("|");
      }
      System.out.println(); 
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //                 draws equal sign line between each calendar row                // 
   ////////////////////////////////////////////////////////////////////////////////////
   public static void drawEqLine() {
      for(int eq = 0; eq <= (SIZE * 7); eq++) {    //calendar top/bottom line
         System.out.print("=");
      }      
      System.out.println(); 
   }
   
   //////////////////////////////////////////////////////////////////////////////////////
   //  gets string value for day of week and calculates proper spacing above calendar  //
   //////////////////////////////////////////////////////////////////////////////////////
   public static void headerDays(String days) {  //days = abbreviated week day         
      for(int spce = 1; spce <= SIZE - 3; spce++) {
         System.out.print(" ");
      }
      System.out.print(days);
   }
   
   ////////////////////////////////////////////////////////////////////////////////////   
   //                Displays Month:mm & Day:dd under each calendar                  // 
   ////////////////////////////////////////////////////////////////////////////////////
   public static void displayDate(int month, int day) {  //month # value & day # value 
      System.out.println("Month: " + month);
      System.out.println("Day: " + day);      
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //                    Returns month value for each calendar                       //
   ////////////////////////////////////////////////////////////////////////////////////
   public static int monthFromDate(String date) { //date = users input of mm/dd
      int monthVal = 0;
      if (date.length() == 5) {
         monthVal = Integer.parseInt(date.substring(0, 2)); //returns mm from mm/dd as an int
      } else if (date.length() == 4) {
         monthVal = Integer.parseInt(date.substring(0, 1));
      }
      return monthVal; //returns month number
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //                     Returns day value for each calendar                        // 
   ////////////////////////////////////////////////////////////////////////////////////
   public static int dayFromDate(String date) { //date = users input of mm/dd
      int dayVal = 0;
      if (date.length() == 5) {
         dayVal = Integer.parseInt(date.substring(3, 5)); //returns dd from mm/ddd as an int
      } else if (date.length() == 4) {
         dayVal = Integer.parseInt(date.substring(2,4));
      }
      return dayVal; //returns day number 
   }
   
   ////////////////////////////////////////////////////////////////////////////////////
   //          Prints ascii art of a mountain range beyond a highway                 //
   ////////////////////////////////////////////////////////////////////////////////////
   public static void mountScapeHwy() {
      System.out.println ("                   .");
      System.out.println ("                  ,'");
      System.out.println ("                 .`");
      System.out.println ("               ,'                       /\\");
      System.out.println ("             ,'                        /  \\");
      System.out.println ("       /\\,.'`  /\\             /\\      /    \\");
      System.out.println (".,,,,./  \\    /  \\      /\\   /\\/\\    /\\/\\/\\/\\");
      System.out.println ("     /\\/\\/\\  /\\/\\/\\    /  \\ /    \\  /        \\");
      System.out.println ("    /      \\/      \\  /\\/\\/\\      \\/          \\");
      System.out.println ("   /        \\       \\/      \\     /            \\");
      System.out.println ("  /          \\      /        \\   /              \\");
      System.out.println (" /            \\    /          \\ /                \\");
      System.out.println ("/              \\  /            /                  \\");
      for(int curb = 1; curb <= 53; curb++) {
         System.out.print ("_");
      }
      System.out.println ("\n");
      for(int lane = 1; lane <= 27; lane++) {  //3 bottom loops print highway under mountains
         System.out.print ("- ");
      }
      System.out.println ();
      for(int curb1 = 1; curb1 <= 53; curb1++) {
         System.out.print ("_");
      }
      System.out.println("\n");
   }
   
}
