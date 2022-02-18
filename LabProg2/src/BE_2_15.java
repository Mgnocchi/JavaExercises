import prog.io.*;
import prog.utili.Importo;

public class BE_2_15 {

	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out =new ConsoleOutputManager();
		
		int parteIntera = in.readInt("Parte intera: ");
		int parteDecimale = in.readInt("Parte decimale: ");
		Importo importo1 = new Importo(parteIntera, parteDecimale);
		
		parteIntera = in.readInt("Parte intera: ");
		parteDecimale = in.readInt("Parte decimale: ");
		Importo importo2 = new Importo(parteIntera, parteDecimale);
		
			//il casting .toString dovrebbe essere fatto automaticamente: il nome del metodo è di default e viene cercato in compilazione
		out.println("somma degli importi: " + (importo1.piu(importo2)).toString() + "€");
			//il nesting delle chiamate ai metodi non è necessario sia esplicitato con le parentesi: la valutazione viene fatta
			//risolvendo le chiamate in ordine
		out.println("somma degli importi in lire: " +  importo1.piu(importo2).toLire() + "£");
		//qui ovviamente la cosa sensata sarebbe salvare la somma e usare il .toLire su essa, così da risparmiare lavoro al garbage collector
	}

}
