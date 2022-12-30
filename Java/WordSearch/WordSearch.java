//Scott Wedge
//2020

//This program file does most of the work for creating and managing the word search

import java.util.*;

public class WordSearch {
    private char[][] grid;
    private boolean[][] word;

    public WordSearch() {

    }
    //accepts a parameter of a string array and makes a new word search
    // places word in word search grid. determines direction of the word and finds
    // open place to put it
    public void generate(String[] newWords) {
        Random rand = new Random();
        for(int i = 0; i < newWords.length; i++) {
            newWords[i] = newWords[i].toLowerCase();
            boolean wordsSet = false;
            int direction;
            int search;
            int check;
            char[][] wordChars = gridSize(newWords);
            int place = 0; //abandons word if unable to place within 100 attempts
            for (char[] wordChar : wordChars) {
                direction = rand.nextInt(3);
                int[] position = new int[2];
                switch (direction) {
                    case 0: //places horizontal words
                        while (!wordsSet && place < 100) {
                            position[0] = rand.nextInt((grid.length - 1) -
                                wordChar.length);
                            position[1] = rand.nextInt((grid.length - 1) -
                                wordChar.length);
                            wordsSet = true;
                            for (search = 0; search < wordChar.length; search++) {
                                //randomly places letters were the user word is not
                                if (grid[position[0] + search][position[1]] != '\u0000' &&
                                    grid[position[0] + search][position[1]] !=
                                        wordChar[search]) {
                                    wordsSet = false;
                                    break;
                                }
                            }
                            place++;
                        }
//                        try {
                        if (wordsSet) {
                            for (check = 0; check < wordChar.length; check++) {
                                grid[position[0]][position[1]] = wordChar[check];
                                word[position[0]][position[1]] = true;
                                position[0]++;
                            }
                        }
//                        } catch (Exception OutOfBounds) {
//                            generate(newWords);
//                        }
                        break;
                    case 1: //places vertical words
                        while (!wordsSet && place < 100) {
                            position[0] = rand.nextInt((grid.length - 1) -
                                wordChar.length);
                            position[1] = rand.nextInt((grid.length - 1) -
                                wordChar.length);
                            wordsSet = true;
                            for (search = 0; search < wordChar.length; search++) {
                                if (grid[position[0]][position[1] + search] != '\u0000' &&
                                    grid[position[0]][position[1] + search] !=
                                        wordChar[search]) {
                                    wordsSet = false;
                                    break;
                                }
                            }
                            place++;
                        }
                        if (wordsSet) {
                            for (check = 0; check < wordChar.length; check++) {
                                grid[position[0]][position[1]] = wordChar[check];
                                word[position[0]][position[1]] = true;
                                position[1]++;
                            }
                        }
                        break;
                    case 2: //places diagonal words
                        while (!wordsSet && place < 100) {
                            position[0] = rand.nextInt((grid.length - 1) -
                                wordChar.length);
                            position[1] = rand.nextInt((grid.length - 1) -
                                wordChar.length);
                            wordsSet = true;
                            for (search = 0; search < wordChar.length; search++) {
                                if (grid[position[0] + search][position[1] + search] != '\u0000' &&
                                    grid[position[0] + search][position[1] + search] !=
                                        wordChar[search]) {
                                    wordsSet = false;
                                    break;
                                }
                            }
                            place++;
                        }
                        if (wordsSet) {
                            for (check = 0; check < wordChar.length; check++) {
                                grid[position[0]][position[1]] = wordChar[check];
                                word[position[0]][position[1]] = true;
                                position[1]++;
                                position[0]++;
                            }
                        }
                        break;
                }
            }
            if (!wordsSet && place < 100) {
                i--;
                place++;
            } else if (wordsSet) {
                place = 0;
            } else {
                break;
            }
        }
            fillGrid();
    }

    //print array
    public String toString() {
        String puzzle = "";
        for (char[] chars : grid) {
            for (int j = 0; j < chars.length; j++) {
                puzzle += " " + chars[j] + " ";
            }
            puzzle += "\n";
        }
        return puzzle;
    }

    //returns a string representation of the word search solution
    public String toSolution() {
        String solved = "";
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if (word[i][j]) {
                    solved += " " + grid[i][j] + " ";
                }
                else {
                    solved += " _ "; //places an "_" where the users entered words are not present
                }
            }
            solved += "\n";
        }
        return solved;
    }

    //breaks up string array into 2d char array and adjusts the size of the grid
    public char[][] gridSize(String[] newWords) {
        char[][] wordChars = new char[newWords.length][];
        int longest = 6;
        for (int i = 0; i < newWords.length; i++) {
            wordChars[i] = newWords[i].toCharArray();
            if (wordChars[i].length > longest) {
                longest = wordChars[i].length;
            }
        }
        this.grid = new char[longest + 4][longest + 4];
        this.word = new boolean[longest + 4][longest + 4];
        return wordChars;
    }

    //fills the grid with random letters where user words are not present
    public void fillGrid() {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                Random rand = new Random();
                if (grid[i][j] == '\u0000') {
                    grid[i][j] = (char)(rand.nextInt(26)+97);//lowercase alphabet letters
                }
            }
        }
    }
} 
