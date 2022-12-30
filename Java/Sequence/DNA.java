//Scott Wedge
//2020

//This program processes files with raw DNA data, and prints to a new file with the 
//data nicely organized for reading

import java.util.*;
import java.io.*;

public class DNA {

   public static final int CODON = 5;     //min # codons for protein
   public static final int PERCENT = 30;  //mass% of C & G for protein
   public static final int NUCLEO = 4;    //unique nucleotides (A,C,G,T)
   public static final int NUCCODE = 3;   //nucleotides per codon
   
   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
          
      System.out.println("This program reports information about DNA");
      System.out.println("nucleotide sequences that may encode proteins.");
      System.out.println();
      System.out.print("Input file name? ");
      String inFile = console.nextLine(); //gets read file name
      File read = new File(inFile);
       
      System.out.print("Output file name? ");
      String outFile = console.nextLine(); //gets write file name
      File write = new File(outFile);
      PrintStream writeOut = new PrintStream(write);
      
      Scanner input = new Scanner(read);
      while(input.hasNextLine()) {
         String region = input.nextLine(); //grabs name of sequence
         String sequence = input.nextLine().toUpperCase(); //grabs necleotide sequence
         int[] nCount = acgtCount(sequence); //counts nucleotide types
         int dash = junkDash(sequence); //counts junk characters
         double[] massPortion = new double[NUCLEO]; //array for percent mass per nucleotide type
         double allOfMass = toteMass(nCount, dash, massPortion); //total mass and percent mass
         sequence = sequence.replace("-", ""); //remove junk from sequence
         String[] codonLst = codonInNuc(sequence); //distinguishes codons in sequence
         String gainz = proteinOrNo(sequence, massPortion, codonLst); //tests for protein
         printOut(region, sequence, nCount, massPortion, allOfMass, codonLst, gainz, writeOut);
                //name, nuc sequence, nuc types, percent, total mass, codon #, protein, file
      }
          
   }
   
   //determines if sequence is a protein or not. Requires sequence, nucleotide mass array, and
   //codon list array to determine starting/ending codon, # of codons, & percent of C+G
   public static String proteinOrNo(String seq, double[] massPortion, String[] codonLst) {
      if (seq.startsWith("ATG") && seq.endsWith("TAA") || seq.endsWith("TAG") ||
          seq.endsWith("TGA") && codonLst.length >= CODON) { //CODON == 5, PERCENT == 30
          if (massPortion[1] + massPortion[2] >= PERCENT) { //[1] = (C)ytosine, [2] = (G)uanine
            return "YES"; //yes, it is a protein
          }
      }
      return "NO"; //no, it is NOT a protein
   }
   
   //calculates the total mass per sequence 
   public static double toteMass(int[] nCount, int dash, double[] massPortion) {
      double[] massVals = {135.128, 111.103, 151.128, 125.107}; //A,C,G,T
      dash *= 100.000; // junk character '-'
      double noDashMass = 0; 
      for(int i = 0; i < nCount.length; i++) { //calculates nuc type occurrence * type mass
         noDashMass += nCount[i] * massVals[i];
      }
      double total = noDashMass + dash; //includes junk characters for total mass amount
      for(int j = 0; j < massPortion.length; j++) { //cals/rounds nuc mass in sequence
         massPortion[j] = Math.round((nCount[j] * massVals[j] / total * 100) * 10) / 10.0;
      }
      total = Math.round(total * 10) /10.0; //rounds grand total mass to tenth place
      return total; //returns total mass for sequence to allOfMass
   }
   
   //separates codons that exist within sequence
   public static String[] codonInNuc(String seq) {
      String[] codons = new String[seq.length() / NUCCODE]; //breaks sequence into groups of 3
      for(int i = 0; i < codons.length; i++) { //runs through array
         codons[i] = seq.substring(i * NUCCODE, i * NUCCODE + NUCCODE);
      }
      return codons; //returns codons array string index values
   }
      
   //determines the occurrence of each nucleotide type in sequence
   public static int[] acgtCount(String seq) {
      int[] countNucs = new int[NUCLEO]; //array length is class constant value
      for(int i = 0; i < seq.length(); i++) { //runs through sequence string
         if (seq.charAt(i) == 'A') { //changes index[0]
            countNucs[0]++;
         } else if (seq.charAt(i) == 'C') { //changes index[1]
            countNucs[1]++;
         } else if (seq.charAt(i) == 'G') { //changes index[2]
            countNucs[2]++;
         } else if (seq.charAt(i) == 'T') { //changes index[3]
            countNucs[3]++;
         }
      }
      return countNucs; //returns the nucleotide type occurrece value for sequence
   }
   
   //determines the occurrence of a '-' character in each sequence
   public static int junkDash(String seq) {
      int dashCount = 0;
      for(int i = 0; i < seq.length(); i++) {
         if (seq.charAt(i) == '-') {
            dashCount++; //tracks junk characters
         }
      }
      return dashCount; //returns the # of '-' characters in sequence
   }
   
   //Takes all data and prints to output file
   public static void printOut(String region, String seq, int[] nCount, double[] massPortion,
         double allOfMass, String[] codonLst, String gainz, PrintStream writeOut)
         throws FileNotFoundException {
      
      writeOut.println("Region Name: " + region);
      writeOut.println("Nucleotides: " + seq);
      writeOut.println("Nuc. Counts: " + Arrays.toString(nCount));
      writeOut.println("Total Mass%: " + Arrays.toString(massPortion) + " of " + allOfMass);
      writeOut.println("Codons List: " + Arrays.toString(codonLst));
      writeOut.println("Is Protein?: " + gainz);
      writeOut.println();
   }
   
}
