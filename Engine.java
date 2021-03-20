import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner; 

public class Engine {
	
	//Creates a new AVLTree
    public static AVLTree tree = new AVLTree();
	
	public static void main (String args[]) {
	      
		  //Parses words from the files
		  System.out.println("Parsing file1.txt");
		  readFile("file1.txt");
		  
		  System.out.println("Parsing file2.txt");
		  readFile("file2.txt");
		  
		  System.out.println("Parsing file3.txt");
		  readFile("file3.txt"); 
		  
		  System.out.println("Parsing file4.txt");
		  readFile("file4.txt");
		  
		  String options="\n\t 1. Find a word. Example: 1 earth" 
				  		+"\n\t 2. Delete a word from the tree. Example: 2 earth"
				  		+"\n\t q  Type q or quit to exit program";
		  
		  System.out.println("Hello what would you like me to do?" 
				  		     +options);
		  
		  String userInput="";
		  Scanner scanner = new Scanner(System.in);
	      
		  while(!userInput.equals("q")) {
				
				userInput=scanner.nextLine();
				userInput.trim();
				
				
				//Quits the program
				if(userInput.equals("q")) {break;}
				
				
				//Splits user input 
				
				
				try{
					String[] splitUserInput  = userInput.split(" ");
					
					//Converts the 1st string to an int
					int optionNum =Integer.parseInt(splitUserInput[0].trim());
					
					//Converted string tells program the option
					switch(optionNum) {
					
						//Case 1: Find the queried word
						case 1:{
							
							//find(splitUserInput[1].trim());
							tree.root = tree.find(tree.root, wordToNumber(splitUserInput[1].trim()));
							break;
						}
					
						//Case 2: Delete the queried word
						case 2:{
							
							tree.root = tree.deleteNode(tree.root, wordToNumber(splitUserInput[1]));
							break;
							
						}
						
						//default that the user input is not recognized
						default:{
							
							System.out.println("I can only do options 1 or 2! :( ");
							break;
							
						}
					
					}
					
					System.out.println("\nWhat else would you like me to do?"
						    +options);
					
				}
				catch(ArrayIndexOutOfBoundsException ex) {

					System.out.println("Invalid input, please give me an option number and a word." + options);
					
				}
				
				catch(NumberFormatException ex) {
					
					System.out.println("Invalid input, please give me the option number first or make sure there's no space before the number." + options);
					
				}

		  }	
		  
		  //Closes scanner
		  scanner.close();
		  System.out.println("\nBye!");
	 }
	
	//Converts a given word into a number. For example: wordToNumber(the) would return -> 200805 because 
	public static double wordToNumber(String word) {
		
		word.toLowerCase();
		
		word.trim();
		
		double encoded=0;
		
		//for each letter in the word convert it into a number, 
		for(int i = 0 ; i < word.length() ; i++) {
			
			encoded = letterToNumber(word.charAt(i), word.length() - (i+1)) + encoded;
			
		}
			
		return (double)Math.round(encoded * 1d) /1d ;
	}
	
	//Converts a given letter to a number, such as 10
	//For example t is 20 and is in the third letter so the program will out put 200000
	//More specifically 20*100^2, 2 obtained from,  (the length of the word) - (location of letter in the word + 1)
	public static double letterToNumber(char c, double power) {
		
		Character[] alphabet= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		
		double value=0;
		
		//Go through alphabet
		for(int i = 0 ; i < alphabet.length ; i++) {
			
			//If the letter of the alphabet equals to the given letter
			if(alphabet[i].equals(c)) { 
				
				//set the value to, (index + 1)*100^(given power)
				value=(i+1)*Math.pow(100, power);
				
				//Round to whole numbers
				return (double)Math.round(value * 1d) /1d;
			}
			
		}
		
		return 0;
	}
	
	public static void readFile(String fileName) {
		
		String line = null;
		
		try {
			
            FileReader fileReader = new FileReader(fileName);
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);
			
            while((line = bufferedReader.readLine()) != null) {
            	
            	//Insert into the tree a node with the following attributes: key, word, filename
            	tree.root = tree.insert(tree.root, wordToNumber(line.trim()), line.trim(), fileName.trim());
            	
            }
            bufferedReader.close();
		}
		catch(FileNotFoundException ex) {System.out.println("Unable to open file '" + fileName + "'");}
        catch(IOException ex) {System.out.println("Error reading file '" + fileName + "'");} 
		
		}
}
