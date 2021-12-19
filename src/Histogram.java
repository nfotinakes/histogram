import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Title: Histogram
 * Abstract: This program sorts and counts character frequency from a file
 * and then displays the results as a histogram graph.
 * Author: Nicholas Fotinakes
 * Date: 11/1/2021
 * Note: Referenced the book Starting Out With Java by Gaddis & Muganda for certain
 * things such as BubbleSort algorithm.
 */

public class Histogram {
    public static void main(String[] args) {
        // Populate the 'letter' array with all possible characters
        char[] letter = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'};

        // Creates an int array of length 11 to correspond with letters
        // for counting purposes. ex: letterCount[0] will represent how many
        // 'A' characters are counted
        int[] letterCount = new int[11];
        String filename;    // To hold filename

        // Call method to get filename from user
        filename = getFileName();

        // Call read method to read characters from file and populate arrays
        read(letter, letterCount, filename);

        // Call read method to sort arrays by ascending frequencies
        sort(letter, letterCount);

        // Call display method to print results
        display(letter,letterCount);
    }

    /**
     * The getFileName method creates a Scanner object to get keyboard input
     * from user for a filename
     * @return filename
     */
    public static String getFileName() {
        Scanner keyboard = new Scanner(System.in);
        String filename;
        System.out.print("Input filename: ");
        filename = keyboard.nextLine();
        return filename;
    }

    /**
     * The read method reads characters from a file and populates the character
     * letter array and its corresponding letterCount array
     * @param letter The array containing all possible letters
     * @param letterCount The array to count character frequency
     * @param filename The filename to read from
     */
    public static void read(char[] letter, int[] letterCount, String filename) {
        // Create new File and Scanner object to read file
        // if file not found use try/catch to throw exception
        File file = new File(filename);
        Scanner inputFile;

        // StringBuilder object to append each letter to from file
        StringBuilder str = new StringBuilder();

        // Try to create scanner from file to read the characters to StringBuilder
        try {
            inputFile = new Scanner(file);
            // Read each file line and add the character to str
            while (inputFile.hasNext()) {
                String input = inputFile.nextLine();
                str.append(input);
            }
            inputFile.close(); // close file
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file " + e);
        }

        String lettersAsString = str.toString(); // Convert to normal string

        // Iterate through each character from file now saved as a string
        for(int i = 0; i < lettersAsString.length(); i++) {
            // When character matches one in letter array
            // then add 1 to the corresponding value in letterCount array
            for(int j = 0; j < letter.length; j++) {
                if(lettersAsString.charAt(i) == letter[j]){
                    letterCount[j]++;
                }
            }
        }
    }

    /**
     * The sort method uses bubble sort algorithm to sort the letter and letterCount
     * arrays into ascending order
     * @param letter the array with possible characters
     * @param letterCount the array now populated with character frequency
     */
    public static void sort(char[] letter, int[] letterCount) {
        // Use bubble sort to sort both arrays in ascending order
        // Sort by frequency but also swap the letter array with corresponding
        // character to keep everything aligned
        int tempInt;
        char tempChar;
        for (int i = 0; i < 11; i++) {
            for(int j = 1; j < (11-i); j++) {
                if(letterCount[j-1] > letterCount[j]){
                    tempInt = letterCount[j-1];
                    tempChar = letter[j-1];
                    letterCount[j-1] = letterCount[j];
                    letter[j-1] = letter[j];
                    letterCount[j] = tempInt;
                    letter[j] = tempChar;
                }
            }
        }
    }

    /**
     * The display method will print character frequencies as well as
     * the matching histogram chart
     * @param letter the character array now sorted
     * @param letterCount the frequency of characters now sorted
     */
    public static void display(char[] letter, int[] letterCount) {

        // Top block of display output
        // Iterate through sorted letterCount and print all non-zero
        // values with corresponding character
        System.out.println("Char Occurrences");
        for(int i = 0; i <letter.length; i++){
            if(letterCount[i] > 0){
                System.out.println(letter[i] + " " + letterCount[i]);
            }
        }
        System.out.println();

        // Find and store the maximum integer value of character frequency
        // Used in Histogram graph output
        int max = 0;
        for(int i = 0; i < 11; i++){
            if (letterCount[i] > max){
                max = letterCount[i];
            }
        }

        // Create an array populated from 1 to the max frequency that occurs
        // This will be used to print the correct spaces in Histogram Graph
        // after sorting in next section
        int arrayCounter = 1;
        int[] spaceArray = new int[max];
        for(int i = 0; i < spaceArray.length; i ++){
            spaceArray[i] = arrayCounter;
            arrayCounter++;
        }

        // Iterate through the array and replace each element with how many times
        // that frequency appears ex: if 5 characters each appear 1 time, then
        // spaceArray[0] will be replaced by a 5. Now ready for printing
        int spaceCount = 0;
        for(int k = 0; k < spaceArray.length; k++){
            for(int m = 0; m < letterCount.length; m++){
                if(letterCount[m] == spaceArray[k]){
                    spaceCount++;
                }
            }
            spaceArray[k] = spaceCount;
            spaceCount = 0;
        }

        //Histogram Graph block of display output
        System.out.println("=============================");
        // Initialize integers for calculating spacing output in graph
        int spaceSize = spaceArray.length - 1;
        int totalSpace = 22;

        // Iterate through the spaceArray to display Histogram Graph with frequencies
        for (int p = spaceSize; p >= 0; p--) {
            //Calculate how many blank spaces to print for loop
            totalSpace = totalSpace - (spaceArray[p] * 2);
            // Iterate through letterCount array to print corresponding frequency
            for (int i = 10; i >= 10; i--) {
                // Print left side of Graph with frequency number
                if (letterCount[i] > 0 && letterCount[i] >= max && max >= 1) {
                    System.out.printf("|%5s|", max);
                    //Print the blank spaces
                    for (int t = 1; t <= totalSpace; t++) {
                        System.out.print(" ");
                    }
                    // Print the characters for corresponding frequency
                    for (int j = 0; j <= 10; j++) {
                        if (letterCount[j] >= max) {
                            System.out.print(letter[j] + " ");
                        }
                    }
                }
            }
            System.out.println();
            max--;
        }

        //Bottom block of display output
        System.out.println("-----------------------------");
        System.out.print("       ");
        // Print character values from sorted character array
        for(char c : letter) {
            System.out.print(c + " ");
        }
    }

}
