//Scott Wedge
//2020

//This program takes in strings of data and sorts through the data,
// selecting only character values. Then the characters are sorted
// alphabetically and like kinds are placed together.
// The result will print the sorted characters to the console.


public class Data {
    public static void main(String[] args) {
        LetterInventory inventory1 = new LetterInventory("Michael J. Jackson"); //1st String data
        LetterInventory inventory2 = new LetterInventory("Large red dog"); //2nd String data
        LetterInventory inventory3 = new LetterInventory("Aang the Air Bender"); //3rd
        LetterInventory inventory4 = new LetterInventory("whoooooshhhhh"); //4th
        LetterInventory inventory5 = new LetterInventory("wwhhooooooossshhhhh");
        LetterInventory sum = inventory1.add(inventory2); //calls add method w/ other inventory
        LetterInventory minuses = inventory5.subtract(inventory4);//calls subtract method w/ other

        System.out.println("add method: " + sum.toString()); //print whats been sent to add method
        System.out.println("subtract method: " + minuses.toString()); //print subtract method
        System.out.println("inv 1: " + inventory1.toString()); //print inventory1
        System.out.println("inv 2: " + inventory2.toString()); //print inventory2
        System.out.println("inv 3: " + inventory3.toString()); //print inventory3
        System.out.println("inv 4: " + inventory4.toString()); //print inventory4
        System.out.println("inv 5: " + inventory5.toString()); //print inventory5
        System.out.println("get the 'a': " + inventory1.get((char) 97)); //send 'a' to get method
        System.out.println("inv1 size: " + inventory1.size()); //get the size of inventory1
        System.out.println("inv2 size: " + inventory2.size()); //get the size of inventory2
        System.out.println("inv3 size: " + inventory3.size()); //get the size of inventory3
        System.out.println("inv4 size: " + inventory4.size()); //get the size of inventory4
        System.out.println("inv5 size: " + inventory5.size()); //get the size of inventory5
        System.out.println("is empty? " + inventory2.isEmpty()); //check if inventory2 is empty
        inventory2.set((char) 97, 7); //feeds character 'a' and value of 7 to set method
        System.out.println(inventory3.get((char) 97)); //returns how many 'a' in inventory3
    }
}
