//Scott Wedge
//2020

//This program file guides the user through building aa words search puzzle

import java.util.*;

public class WordSearchMain {

    public static void main(String[] args) {
        WordSearch search = new WordSearch();
        printIntro(search); //calls user menu and user options method
    }

    //User menu that will provide instructions and guide the user through the
    // use of the word search puzzle program.
    public static void printIntro(WordSearch search) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the puzzle Wizard.");
        System.out.println("This program will allow you to generate your own" +
            "word search puzzle.");
        System.out.println("Please select an option:");
        System.out.println("Generate a new word search (g).");
        System.out.println("Print out your word search (p).");
        System.out.println("Show the solution to your word search (s).");
        System.out.println("Quit the program (q).");
        System.out.print(">>>");
        String userChoice = input.next();
        while (!userChoice.equals('q')) {
            switch (userChoice) { //switch/case to handle possible user responses
                case "g": //generate word search puzzle
//                generate(search);
                    Scanner console = new Scanner(System.in);
                    int wordAmount = 1;
                    int track = 0; //tracks entered words by user
                    System.out.println("How many words would you like to add to the word search?");
                    wordAmount = console.nextInt();
                    //wordAmount--; //makes it like count starts at 1 instead of 0
                    System.out.println("input the amount of words you specified, " +
                        "followed by hitting 'enter' after each word.");
                    String userWords = input.next();
                    track++;
                    ArrayList<String> makeWords = new ArrayList<>();
                    do {
                        makeWords.add(userWords); //adds words to ArrayList
                        userWords = input.next();
                        track++;
                    }
                    while (track < wordAmount);
                    makeWords.add(userWords);
                    String[] words = new String[makeWords.size()];
                    makeWords.toArray(words);
                    search.generate(words);
                    printIntro(search);
                    break;
                case "p": //prints word search
                    try {
                        print(search);
                        printIntro(search);
                    } catch (Exception NullPointerException) {
                        System.out.println();
                        System.out.println("***You must first create a puzzle to use this option***");
                        System.out.println();
                        printIntro(search);
                    }
                    break;
                case "s": //shows word search solution
                    try {
                        showSolution(search);
                        printIntro(search);
                    } catch (Exception NullPointerException) {
                        System.out.println();
                        System.out.println("***You must first create a puzzle to use this option***");
                        System.out.println();
                        printIntro(search);
                    }
                    break;
                case "q": //quits program
                    System.exit(0);
                    break;
                default: //ensures user plays by menu options
                    System.out.println("Invalid entry, please try again.");
                    printIntro(search);
            }
        }
    }


//    // Generates a new word search puzzle
//    private static void generate(WordSearch search) {
//        Scanner console = new Scanner(System.in);
//        Scanner input = new Scanner(System.in);
//        int wordAmount = 0;
//        System.out.println("How many words would you like to add to the word search?");
//        while (wordAmount < 1 || wordAmount > 100) {
//            wordAmount = console.nextInt();
//            if (wordAmount < 1 || wordAmount > 100) {
//                System.out.println(wordAmount + "is out of the allowed range.");
//                console.nextInt();
//            }
//        }
//        System.out.println("input the amount of words you specified, " +
//            "followed by hitting 'enter' after each word.");
//        String userWords = input.next();
//        ArrayList<String> makeWords = new ArrayList<>();
//        while (makeWords.size() < wordAmount) {
//            makeWords.add(userWords); //adds words to ArrayList
//            userWords = input.next();
//            String[] words = new String[makeWords.size()];
//            makeWords.toArray(words);
//            //search.generate(words);
//
//            for (int i = 0; i < words.length; i++) {
//                words[i] = words[i].toLowerCase();
//            }
//            //userWords = words;
//            char[][] wordChars = search.gridSize();
//            for (int i = 0; i < wordChars.length; i++) {
//                search.placeWord(wordChars, i);
//            }
//            search.fillGrid();
//        }
//    }

        //prints out the word search puzzle with random characters
        public static void print(WordSearch printSearch) {
            System.out.println(printSearch);
        }

        //prints out the word search solution with '_' characters
        public static void showSolution(WordSearch ws) {
            System.out.println(ws.toSolution());
        }
    }
