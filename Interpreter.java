import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Interpreter {

	static ArrayList<String> integers;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			FortranZToJava("fortranz.prog");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public static void FortranZToJava(String fileName) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		PrintWriter file = new PrintWriter(new File("output.java"));

		integers = new ArrayList<String>();

		String line; 


		while ((line = reader.readLine()) != null) {

			StringTokenizer token = new StringTokenizer(line); // a String Tokenizer that tokenizes the input line for operators

			while (token.hasMoreTokens()) {


				String begin = token.nextToken(); // a string that takens in the next token


				if (begin.equals("END") || begin.equals("STOP") || begin.equals("ENDIF")){
					while (token.hasMoreTokens()) {
						token.nextToken();
					}
					file.println("\t\n}"); // declares the ending brackets

				}

				if(begin.equals("PROGRAM")){
					while (token.hasMoreTokens()) {
						String next = token.nextToken();

						file.println("public class "+next+"\n{\n\tpublic static void main(String [] args)\n\t{"); // declares the starting of the output program
					}
				}

				if (begin.equals("INTEGER")) // Declare INTEGER variables
				{

					while (token.hasMoreTokens()) {

						String next = token.nextToken();


						file.print("\t\tint " + next.charAt(0)+ "= 0;\n");


					}

				} 

				if (begin.startsWith("WRITE(*,")) // interprets the lines for WRITE call in FORTRAN
				{
					String firstarg = begin.substring(begin.indexOf("(")+1, begin.indexOf(","));
					String secondarg = begin.substring(begin.indexOf("*,")+2, begin.indexOf(")"));

					ArrayList<String> operands = new ArrayList<String>();
					ArrayList<String> format = new ArrayList<String>();


					while (token.hasMoreTokens()) {

						String next = token.nextToken();


						if(next.charAt(next.length()-1)==','){
							next =next.substring(0,next.length()-1);
						}

						operands.add(next);				    	
					}

					if(secondarg.equals("*")){
						String output=""; 

						for (int i = 0; i < operands.size(); i ++) {
							output += operands.get(i); 

							if(i<operands.size()-1) {
								output+= "+";
							}
						}
						file.write("\t\tSystem.out.println("+output+");\n");
					}
					else if (secondarg.matches("[-+]?\\d*\\.?\\d+")){ // Parse format and return write
						String output=""; 

						line = reader.readLine();
						StringTokenizer linetoken = new StringTokenizer(line); 
						String next = linetoken.nextToken();

						// Parsing format 
						while(linetoken.hasMoreTokens()){
							next = linetoken.nextToken();
							if(next.startsWith("FORMAT(")){
								next = next.substring(7,next.length());

								if(next.charAt(next.length()-1)==','){
									next =next.substring(0,next.length()-1);
								}

								format.add(next);

								while (linetoken.hasMoreTokens()) {
									next = linetoken.nextToken();

									if(next.charAt(next.length()-1)==','){
										next =next.substring(0,next.length()-1);
									}

									if(next.charAt(next.length()-1)==')'){

										next =next.substring(0,next.length()-1);
									}


									format.add(next);	

								} 

							}
						}

						int operatorCount=0;
						output ="";

						for (int i = 0; i < format.size(); i++) {							   
							int numberOfSkips; 

							if(format.get(i).endsWith("X")){
								numberOfSkips=Integer.parseInt(format.get(i).substring(0, format.get(i).length()-1));

								output += "\"";

								for(int j=0; j<numberOfSkips; j++){
									output+=" ";
								}

								output += "\"+";


							}
							else {
								output+=operands.get(operatorCount) ;
								if(operatorCount<operands.size()-1) {
									output+= "+";
								}	 

								operatorCount++;
							}


						}

						file.write("\t\tSystem.out.println("+output+");\n");					
					}


				} // END OF WRITE


				if (begin.equals("DO")) // interprets the lines for DO call in function
				{
					while (token.hasMoreTokens()) {

						String next = token.nextToken();
						String equals = token.nextToken();
						String num = token.nextToken();
						num = num.replace(",", ""); // removes the commas
						file.println("\t\tfor (" + next + equals + num + "; " + next + " < " +
								token.nextToken() + "; " + next + "++)\n\t\t{");
					}

				} // END OF DO

				if(begin.equals("I") || begin.equals("J") || begin.equals("K") || begin.equals("L") || begin.equals("M") || begin.equals("N")) {	
					String output=""; 
					String equals="";
					String equation="";

					equals = token.nextToken();

					output="\t\t\t\t"+begin+" "+equals+" "; 
					equation = token.nextToken();




					if(equation.startsWith("(RAND")){
						equation = equation.substring(8, equation.length()); 
						output= output + "(int) (Math.random()" + equation + ";";
					}
					else if (equation.startsWith("MOD")){

						String firstarg = equation.substring(4,5);
						String secondarg = equation.substring(6,7);

						output= output + firstarg+"%"+secondarg+";"; 

					}
					else{
						output= output + equation;

						while (token.hasMoreTokens()) {
							String next = token.nextToken();
							output= output + next;
						}
						output+=";";
					}

					file.println(output);
				}

				if(begin.equals("IF")){
					String firstarg="";
					String secondarg="";
					String compare="";

					while (token.hasMoreTokens()) {
						String next = token.nextToken();
						if(next.startsWith("(")){
							firstarg=next.substring(1,next.length());
						}
						else if(next.endsWith(")")){
							secondarg=next.substring(0,next.length()-1);
						}
						else if(next.startsWith(".") && next.endsWith(".")){
							compare=next.substring(1,next.length()-1);
						}
					}

					String compareop =""; 

					switch(compare){
					case "EQ":compareop="=="; break;
					case "NE":compareop="!="; break;
					case "LE":compareop="<"; break;
					case "GE":compareop=">"; break;
					case "GT":compareop=">="; break;
					case "LT":compareop="<="; break;
					case "NOT":compareop="!"; break;
					case "OR":compareop=" || "; break;
					case "AND":compareop=" && "; break;


					}

					file.println("if("+firstarg+compareop+secondarg+"){");
				}

				if(begin.equals("ELSE")){
					file.println("}\n\t\t\telse{");
				}

			}

		}

		file.println("\t\n}"); // declares the ending brackets
		reader.close();
		file.close();	



	}

}