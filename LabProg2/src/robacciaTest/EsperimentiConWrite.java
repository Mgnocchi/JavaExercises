package robacciaTest;

import java.io.*;
import java.util.ArrayList;

public class EsperimentiConWrite {

	public static void scrivi(String nomeFile, ArrayList<String> parole) throws IOException {
		PrintStream scrivitoreDiCavalieri = new PrintStream (new BufferedOutputStream(new FileOutputStream(nomeFile, false)));
		for(String parola:parole) {
			scriviParola(scrivitoreDiCavalieri, parola);
			//scrivitoreDiCavalieri.write(parola);
		}
		scrivitoreDiCavalieri.close();
	}
	
	public static void scriviParola(String parola) {
		System.out.println(parola);
	}
	
	public static void scriviParola(PrintStream scrivitore, String parola) throws IOException {
		PrintStream backup = System.out;
		System.setOut(scrivitore);
		scriviParola(parola);
		System.setOut(backup);
	}
	
	
	public static void main(String[] args) throws IOException{
		ArrayList<String> parole = new ArrayList<>();
		parole.add("terrazzo");
		parole.add("sgabello");
		parole.add("formaggio");
		parole.add("borraccia blu");
		parole.add("cazzo di gomma");
		parole.add("cannuccia");
		
		scrivi("FrocioChiLegge", parole);
		
		System.out.println("Ho finito vostro odore");

	}

}
