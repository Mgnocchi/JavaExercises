package lesson_Lab4;
import prog.io.*;
import java.util.*;

public class Es_ArrayList {

	public static void main(String[] args) {
		ConsoleInputManager in =new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		ArrayList<String> puppagallo = new ArrayList<String>();
		String letto = new String();
		boolean vuota = true;
		
		do {
			out.println("Inserisci una parola, premere INVIO senza inserire nulla per terminare l'inserimento");
			letto = in.readLine();
			if ( !(vuota = letto.equals("")) ) {
				puppagallo.add(letto);
			}
			
		}while(!vuota);
		
		out.println("Inserimento terminato, ecco le tue parole al contrario: ");
		
		while ( !(puppagallo.isEmpty()) ) {
			out.println( puppagallo.remove( puppagallo.size()-1 ) );
		}
		
		
	}

}
