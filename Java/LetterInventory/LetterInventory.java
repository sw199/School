//Scott Wedge
//2020

//This program takes in strings of data and sorts through the data,
// selecting only character values. Then the characters are sorted
// alphabetically and like kinds are placed together.
// The result will print the sorted characters to the console.

public class LetterInventory {
    private final int[] letterInv;
    private int size = 0;


    //Constructs an inventory (a count) of the alphabetic letters in the given string,
    // ignoring the case of letters and ignoring any non-alphabetic characters.
    public LetterInventory(String data) {
        letterInv = new int[26]; //26 letters in alphabet
        data = data.toLowerCase();
        for(int i = 0; i < data.length(); i++) { //travel length of data
            if (Character.isLetter(data.charAt(i))) { //only letter characters allowed
                letterInv[data.charAt(i) - 97]++; //store in array & ascii 'a' == 97
                size++; //tracks array length
            }
        }
    }

    //Returns a count of how many of this letter are in the inventory.
    public int get(char letter) {
        if(!Character.isLetter(letter)) {
            throw new IllegalArgumentException("non-letter characters NOT allowed: " + letter);
        }
        return letterInv[Character.toLowerCase(letter) - 97]; //return how many of each letter
    }

    //Sets the count for the given letter to the given value.
    public void set(char letter, int value) {
        if(!Character.isLetter(letter) || value < 0) {
            throw new IllegalArgumentException("Invalid character: " + letter +
                    "or value: " + value);
        } else {
            size += value - letterInv[Character.toLowerCase(letter) - 97]; //adds to size
            letterInv[Character.toLowerCase(letter) - 97] = value; //new inventory value
        }
    }

    //Returns the sum of all of the counts in this inventory.
    public int size() {
        return size; //returns all letters in inventory
    }

    //Returns true if this inventory is empty (all counts are 0).
    public boolean isEmpty() {
        return size == 0; //if no items found is true
    }

    //Returns a String representation of the inventory with the letters all in lowercase and
    // in sorted order and surrounded by square brackets.
    public String toString() {
        String stringing = "[";
        for(int i = 0; i < 26; i++) {
            for(int array = 0; array < letterInv[i]; array++) {
                stringing += (char)(i + 97); //adds i characters to stringing
            }
        }
        return stringing + "]"; //returns formatted string
    }

    //Constructs and returns a new LetterInventory object that represents the sum of this
    // letter inventory and the other given LetterInventory.
    public LetterInventory add(LetterInventory other) {
        LetterInventory adding = new LetterInventory("");
        for(int i = 0; i < 26; i++) {
            adding.letterInv[i] = this.letterInv[i] + other.letterInv[i]; //adds together now
        }
        adding.size = this.size + other.size; //adjusts counter
        return adding; //returns inventories added together
    }

    //Constructs and returns a new LetterInventory object that represents the result of
    // subtracting the other inventory from this inventory
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory minus = new LetterInventory("");
        for(int i = 0; i < 26; i++) {
            minus.letterInv[i] = this.letterInv[i] - other.letterInv[i]; //minus from each other
            if(minus.letterInv[i] < 0) {
                return null; //no negative count allowed so return null
            }
        }
        minus.size = this.size + other.size; //adjust counter
        return minus; //return the difference between inventories
    }

}
