package robacciaTest;

import java.io.BufferedReader;
import java.io.IOException;

import prog.io.ConsoleInputManager;
import prog.io.ConsoleOutputManager;

public class util {
	
	//Campi
	
	//Costruttori
	
	//Metodi
	
	public static boolean readYesNoLibro(ConsoleInputManager in, ConsoleOutputManager out) {
		char choice;
		do{
			choice = Character.toLowerCase(in.readChar());
			if (choice != 'y' && choice != 'n') {
				out.println("Carattere non valido, scegliere (Y/n): ");
				continue;
			}
			else if(choice == 'y') {
				return true;
			}
			else if(choice == 'n') {
				return false;
			}
		}while(true);
	}
	
	public static boolean readYesNo(BufferedReader in) throws IOException {
		char choice;
		String temp = null;
		do{
			temp = in.readLine();
			if(temp == null || temp.length()>1) {
				System.out.println("ERROR: L'input deve essere un singolo carattere");
				continue;
			}
			else {
				choice=temp.charAt(0);
				if (choice != 'y' && choice != 'n') {
					System.out.println("Carattere non valido, scegliere (Y/n): ");
					continue;
				}
				else if(choice == 'y') {
					return true;
				}
				else if(choice == 'n') {
					return false;
				}
			}
			
		}while(true);
	}

}
