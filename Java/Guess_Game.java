//Scott Wedge
//2020

//This is a user driven guessing game. The program generates a random number that
//the user attempts to correctly guess.

import java.util.*;

public class Guess {

   public static final int SPAN = 100;
   
   public static void main(String[] args) {
      Scanner console = new Scanner(System.in);
      int totalGame = 0;
      int totalGuess = 0;
      int bestGame = 9999;
      int roundGuess = 0;
      boolean maybeEndGame = true;
           
      printInstuctions();
      while (maybeEndGame) { //runs until maybeEndGame returns 'false'
         Random value = new Random();
         int num = value.nextInt(SPAN) + 1; //random # generated between 1 and SPAN
         roundGuess = gameRound(num, console);
         totalGuess += roundGuess; //gets # of guesses from each round & adds it to grand total
         totalGame++;   //keeps track of how many rounds played by user
         maybeEndGame = playAgain(num, console); //asks user to play again & returns T/F
         if(roundGuess < bestGame) { //if true make variables equal
            bestGame = roundGuess;   
         }
      }
      yourResults(totalGame, totalGuess, bestGame); //user end-game results
   
   } 
   
   //prints guessing game instructions
   public static void printInstuctions() {      
      System.out.println("This program allows you to play a guessing game.");
      System.out.println("I will think of a number between 1 and");
      System.out.println("100 and will allow you to guess until");
      System.out.println("you get it.  For each guess, I will tell you");
      System.out.println("whether the right answer is higher or lower");
      System.out.println("than your guess.");
      System.out.println();
   }
   
   //plays one round of the Guessing Game and returns value of user guess attempts
   public static int gameRound(int randomNum, Scanner console) {      
      System.out.println("I'm thinking of a number between 1 and " + SPAN + "...");      
      int guessCount = 0;
      int attempt = 0;
      while (attempt != randomNum) {  //runs until user's guess == random # for game round
         System.out.print("Your guess? ");
         attempt = console.nextInt();
         guessCount++;            //keeps track of guess attempts per round
         if (attempt < randomNum) {             //offers the user some advice
            System.out.println("It's higher.");
         } if (attempt > randomNum) {
            System.out.println("It's lower.");
         }      
      }     
      if (guessCount == 1) {
         System.out.println("You got it right in " + guessCount + " guess");
      } else {
         System.out.println("You got it right in " + guessCount + " guesses"); 
      }
      return guessCount;  //returns the user's guess attempt amount for each round
   
   }
   
   //returns overall results for games played by user 
   public static void yourResults(int games, int overallGuess, int bestGame) {      
      double avgGuess = (double)overallGuess / games; //calculates avg guesses for whole game
      System.out.println("Overall results:");      
      System.out.println("\t total games   = " + games);
      System.out.println("\t total guesses = " + overallGuess);
      System.out.print("\t guesses/game  = ");
      System.out.printf("%.1f\n", avgGuess);  //restricts the decimal places to the 10th place 0.0
      System.out.println("\t best game     = " + bestGame); 
       
   }
      
   //asks the user if they want to play again. if 'y' then another round is played
   public static boolean playAgain(int randomNum, Scanner console) {
      boolean moGameOrNoGame = true;
      System.out.print("Do you want to play again? ");
      String choice = console.next();
      String yOrN = "" + choice.charAt(0); //gets the 1st character that user enters
      if (yOrN.equalsIgnoreCase("y")) {
         moGameOrNoGame = true;
      } else if (yOrN.equalsIgnoreCase("n")) {
         moGameOrNoGame = false;
      } 
      System.out.println();
      return moGameOrNoGame; //goes to main to play another round or exits program
   
   }
}
