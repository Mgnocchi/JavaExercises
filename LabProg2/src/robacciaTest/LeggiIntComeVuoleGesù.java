package robacciaTest;

import java.io.*;

public class LeggiIntComeVuoleGesù {

	public static void main(String[] args) {
		int i;
		BufferedReader lettore = new BufferedReader( new InputStreamReader(System.in));
		
		System.out.println("Inserire un numero intero: ");
		try {
			i = Integer.parseInt( lettore.readLine() );
			System.out.println("Ho letto: " + i);
		}
		catch(NumberFormatException e) {
			System.out.println("Attenzione! La stringa inserita non può essere convertita in un intero!");
		}
		catch(IOException e) {	//Questo catch può essere eliminato se si specifica nel prototipo del main che "throws IOException"
			System.out.println("Il canale di Input standard è occupato :c ");
		}

	}

}
