
//Imports IO, Util , and Math 
import java.io.*;
import java.util.*;
import java.lang.Math.*;

public class SRPN {

	public Stack<String> inputStack = new Stack<String>(); //Creates new Stack where numbers are stored during the operation of the code as strings
	
	public char[] operators = new  char[9]; //Array to hold all the operators of the SRPN
	
	public boolean foundOperator; // Boolean that will check if an Operator has been found so it they aren't added to the Stack
	
	public Queue<Integer> randomNumbers = new LinkedList<Integer>(); // Queue that stores all the "random" numbers of the SRPN program
	
	public SRPN(){
	
		//Adds the Operators of the SRPN program to the operators Array 
		operators[0] = '+';
		operators[1] = '-';
		operators[2] = '*';
		operators[3] = '/';
		operators[4] = '%';
		operators[5] = '^';
		operators[6] = 'r';
		operators[7] = 'd';
		operators[8] = '=';
	
	}
	
	public static void main(String[] args){
		
		SRPN srpn = new SRPN(); // Creates new srpn Object from the SRPN class
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // New Buffered Reader to Read the Input of the user 
		
		try{
			while(true){
	
				String input = reader.readLine(); // Makes a string of the Users Input for each line they enter
			
				if(input == null){
					System.exit(0); // Exits the the Program gracefully if the input is null 
				}
				else{
					srpn.processInput(input); // Calls the processInput Method on the input so we can manipulate it 
				}
			
				
			}
		}
		catch(IOException e) {
         //Exits the Program if an Error Occurs 
		 System.err.println(e.getMessage());
          System.exit(1);
		}
	}

	public void processInput(String input){
		
		//Checks if the input String is not Empty 
		if(!input.isEmpty()){	
			// Checks if the Entered input has any spaces in it,
			//If it doesn't it Runs the processInputSingle Method, if it does it runs the processInputLine method which is needed to split the entered input up  into seperate strings 
			if(input.indexOf(" ") == -1){
				processInputSingle(input);
			}
			else{
				processInputLine(input);
			}
		}
	}
	
	public void operators(char operator){
	
	// Method Containing all off the Operators and What to do if one of them is located in the input 
	
	int z ; //Result of Calculation to be parsed back onto the Stack
	long result ,x ,y ; //Values do work with when an operator is called
	
	//For most of the Operations we run through the same Algorithm
	
		/*	Checks if the Stack has more than 1 element in it. When True 
			Creates Long Variable x of the Top most element in the stack
			Creates Another Long Variable y of the Top most -1  element in the stack
			Creates A Long Result of the operation from x and y 
			Creates new Integer variable z from the Saturation Test, Where it Checks if it is more or less than the int max / Mni Values 
			Adds the z back on top of the Stack
			Otherwise , Print Stack underflow Error Message to the screen
			
			*/
	
	if(operator == '='){
	
	//Checks if the Stack contains more than or Equal to 1 Element 
	// If it does Peek at the top Entry of the Stack 
	//Otherwise print an Error message stating that the Stack if Empty therefore can peek at the element 
		if(inputStack.size() >= 1){
			System.out.println(inputStack.peek());
		}
		else if(inputStack.isEmpty()){
			System.err.println("Stack empty.");
		}

	}
	else if(operator == '+'){
	
		if(inputStack.size()>=2){
			x = Long.parseLong((inputStack.pop()).toString());
			y = Long.parseLong((inputStack.pop()).toString()); 
			result = y+x; 
			z = saturationTest(result); 
			inputStack.push(Integer.toString(z)); 
		}
		else{
			System.err.println("Stack underflow."); 
		}
	}
	else if(operator == '-'){
			if(inputStack.size()>=2){
			x = Long.parseLong((inputStack.pop()).toString());
			y = Long.parseLong((inputStack.pop()).toString());
			result = y-x;
			z = saturationTest(result);
			inputStack.push(Integer.toString(z));
		}
		else{
			System.err.println("Stack underflow.");
		}
	}
	else if(operator == '*'){
		if(inputStack.size()>=2){
			x = Long.parseLong((inputStack.pop()).toString());
			y = Long.parseLong((inputStack.pop()).toString());
			result = y*x;
			z = saturationTest(result);
			inputStack.push(Integer.toString(z));
		}
		else{
			System.err.println("Stack underflow.");
		}	
	}
	else if(operator == '/'){
		if(inputStack.size()>=2){
			x = Long.parseLong((inputStack.pop()).toString());
			if( x == 0){
				//Checks if The First Value is = to Zero 
				System.err.println("Divide by 0."); // Prints Error message That the user will be dividing by zero, since this is a bad thing to do 
				inputStack.push(Long.toString(x)); // Adds x back to the stack 
			}
			else{
				y = Long.parseLong((inputStack.pop()).toString());
				result = y/x; 
				z = saturationTest(result);
				inputStack.push(Integer.toString(z));
			}
			
		}
		else{
			System.err.println("Stack underflow.");
		}
	}
	else if(operator == '%'){
		if(inputStack.size()>=2){
			x = Long.parseLong((inputStack.pop()).toString());
			y = Long.parseLong((inputStack.pop()).toString());
			result = y%x;
			z = saturationTest(result);
			inputStack.push(Integer.toString(z));
		}
		else{
			System.err.println("Stack underflow.");
		}
	}
	else if(operator == '^'){
		if(inputStack.size()>=2){
			 x = Long.parseLong((inputStack.pop()).toString());
			 y = Long.parseLong((inputStack.pop()).toString());
			 result= (long) Math.pow(y,x);
			 z = saturationTest(result);
			 inputStack.push(Integer.toString(z));
		}
		else{
			System.err.println("Stack underflow.");
		}
	}
	else if(operator == 'r'){
		//Checks if the RandomNumber Queue is Empty 
		if(randomNumbers.isEmpty()){
			randomGenerator();	// Runs the randomGenerator Methods which adds all the random numbers to the Queue
		}
		
		//Checks if the Stack has less than 23 element in it 
		if(inputStack.size()<23){
			z = (Integer) randomNumbers.remove(); // Makes the Integer = to the the First Element of the Queue
			inputStack.push(Integer.toString(z)); // Adds Element to the Stack 
			randomNumbers.add(z); //Puts the Elemement back into the Queue
		}
		else{
			System.err.println("Stack overflow.");
		}
			
	
		
	}
	else if(operator == 'd'){
		// Prints the Elements ofhe Stack 
		for(String element : inputStack ){
			System.out.println(element);
		}
	}
	else{
		foundOperator = false;
	}
	
}
	
	public void processInputLine(String input){
			
			input = commentCatcher(input); // Deals with Comments by running the Comment Catcher Method 
			
			String[] inputLine = input.trim().split("\\s+"); // Splits the input at any multiple spaces into an array of strings
			
			//Compares the Array of the Input to the Array of Operators if any of them Match it will Run the Operators Method on the Operator it found 
			for(int i = 0;i<inputLine.length;i++){
				for(int j = 0;j<operators.length;j++){
					if(inputLine[i].charAt(inputLine[i].length()-1) == operators[j]){
						operators(operators[j]);
						foundOperator = true; // Sets the foundOperator to true 
					}
				}
				// Checks if the the the foundOperator  is false 
				if(!foundOperator){
					//Check if the Element that wasnt an Operator is can be converted to an Integer, if it cant then it must be an non Operator 
					if(!isInteger(inputLine[i])){
						System.err.println("Unrecognised operator or operand \"" + inputLine[i] + "\"."); // Prints Error Stating that the input found was a non operator/Integer 
					}
					//Checks if the Stack has less than 23 Elements 
					else if(inputStack.size()<23){
						inputStack.push(inputLine[i]); // Adds the Input to the Stack 
					}	
					else{
						System.err.println("Stack overflow."); //Prints an Error Message stating that the Stack is too full so it cant any more numbers to it 
					}
				}
				foundOperator = false ; // Resets the foundOperator boolean
			}
	}
	
	public void processInputSingle(String input){
		
		//Checks if the input has a comment 
		if(input.indexOf("#")!= -1){
					input = " "; // if it has found a comment change string into a whitespace 
				}
				//Compares the Input to the Operators 
				for(int i =0; i<operators.length;i++){
					if(input.charAt(input.length()-1) == operators[i]){
						operators(operators[i]);
						foundOperator = true;
					}
				}
			
				
			// Checks if the the the foundOperator  is false 
			if(!foundOperator){
				//Check if the Element that wasnt an Operator is can be converted to an Integer, if it cant then it must be an non Operator 
				if(!isInteger(input)){
						System.err.println("Unrecognised operator or operand \"" + input + "\".");// Prints Error Stating that the input found was a non operator/Integer 
				
				}
				//Checks if the Stack has less than 23 Elements 
				else if(inputStack.size()<23){
					inputStack.push(input); // Adds the Input String to the Stack 
				}
				else{
					System.err.println("Stack overflow.");//Prints an Error Message stating that the Stack is too full so it cant any more numbers to it 
				}
			}
			
		foundOperator = false ;// Resets the foundOperator boolean
			
	}
	
	public String commentCatcher(String input){
		 //Checks if the input has any #
		if(input.indexOf("#")!= -1){
			// check if the # is the first Element of the string 
			if(input.indexOf("#") == 0){
				input = " ";  // Makes the Input String = to a whiteSpace
			}
			else{
				input = input.substring(0,input.indexOf("#")); //Removes Comments from the input so the calculation ignores # and beyond 
			}
		}
		return input; //Returns the Input back the the  processInputLine Method 
	
	
	}
	
	public static int saturationTest(long result){
	
		int z ; //Result to be return once test is complete 
	
		//Checks if the Result Long is more than the Max Value of the Int 
		if(result >= 2147483647){
			z = 2147483647 ; // if it is Make the Int Value of it equal to the max Value 
			}
		//Checks if the Result Long is less than the Min Value of the Int 
		else if(result <= -2147483648){
			z = -2147483648; // if it is Make the Int Value of it equal to the min Value 
			}
		else{
			z = (int) result; //OtherWise make it equal the the value it was already
			}
			return z ; //Return the Int back the the Operator Method 
	
	}
	
	public static boolean isInteger(String input){
	
	//Checks for each character in the input, it check if it is outside the uniCode values of "/" and ":"  
	//If it is the Input is not an Integer and therefore cant be converted to one 
		for(int i =1 ;i<input.length();i++){
			char c = input.charAt(i);
			if(c <= '/' || c>= ':'){
				return false; // Returns false 
			}
		
		}
		return true; // Returns true 
	
	}

	public void randomGenerator(){
	
		//Add all the "Random" Numbers to the randomNumbers Queue 
	
		randomNumbers.add(1562469902);
		randomNumbers.add(1039845534);	
		randomNumbers.add(2025653534);	
		randomNumbers.add(739593874);	
		randomNumbers.add(994290584);	
		randomNumbers.add(1198075102);	
		randomNumbers.add(605335584);	
		randomNumbers.add(563009619);	
		randomNumbers.add(1076425455);	
		randomNumbers.add(1979353639);	
		randomNumbers.add(1481705266);	
		randomNumbers.add(416282717);	
		randomNumbers.add(1502074844);	
		randomNumbers.add(339011283);	
		randomNumbers.add(1656724019);	
		randomNumbers.add(75412011);	
		randomNumbers.add(296441807);	
		randomNumbers.add(1150973001);	
		randomNumbers.add(1935872936);	
		randomNumbers.add(378814183);	
		randomNumbers.add(1318686473);	
		randomNumbers.add(2034028701);	
		randomNumbers.add(1310947874);	
		randomNumbers.add(2095686658);	
		randomNumbers.add(1548890542);	
		randomNumbers.add(987301502);	
		randomNumbers.add(543737933);	
		randomNumbers.add(1987654433);	
		randomNumbers.add(111092794);	
		randomNumbers.add(1359561819);	
	
	
	}
	


	
	
}