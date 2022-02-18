import prog.io.*;
import prog.utili.Intero;

// leggi una stringa e comunica la sua lunghezza in cifre e lettere
// poi esprimi in cifre e in lettere la lunghezza della stringa che esprime la lunghezza in lettere della stringa letta
// poi esprimi in cifre e in lettere la lunghezza della stringa che esprime la lunghezza in cifre della stringa letta



public class BE_2_12 {
	
	//data una stringa restituisce un array di due stringhe contenente la lunghezza in cifre e lettere
	static String[] lunghezza(String s){
		Intero lunga = new Intero(s.length());
		
		String[] risposte = new String[2];
		risposte[0] = lunga.toDigitString();
		risposte[1] = lunga.toString();
		
		return risposte;
	}
	
	
	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		
		//legge la stringa
		String stringa = in.readLine("Inserisci una stringa: ");
		//dichiara l'array delle lunghezze: me ne servono due
		String[] LunghezzeOG = lunghezza(stringa);
		String[] LunghezzeEL;
		
		out.println("la stringa inserita è lunga " + LunghezzeOG[0] + " anche conosciuto come " + LunghezzeOG[1]);
		
		LunghezzeEL = lunghezza(LunghezzeOG[0]);
		out.println(LunghezzeOG[0] + " è lunga " + LunghezzeEL[0] + " anche conosciuto come " + LunghezzeEL[1]);
		
		LunghezzeEL = lunghezza(LunghezzeOG[1]);
		out.println(LunghezzeOG[1] + " è lunga " + LunghezzeEL[0] + " anche conosciuto come " + LunghezzeEL[1]);
		
		
	}

}
