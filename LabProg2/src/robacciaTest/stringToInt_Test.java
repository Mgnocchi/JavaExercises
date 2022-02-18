package robacciaTest;

import prog.io.*;


public class stringToInt_Test {

	public static void main(String[] args) {
		String s = "23 12";
		String[] list = s.split(" ");
		ConsoleOutputManager out = new ConsoleOutputManager();
		for(String f:list) {
			Integer i = Integer.parseInt(f);
			out.println(i.toString());
			}
		
	}

}
