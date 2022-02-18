import prog.io.*;
import prog.utili.*;

//accetta diversi quadrati e rettangoli e restituisce l'oggetto con la maggiore area (identificando se è quadrato o rettangolo)

public class Lezione_8_1 {

	private static Rettangolo ChiediFigura() {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		Double b = 0.0,h = 0.0;
		
		out.println("Inserisci la Base della Figura: ");
		while ((b = in.readDouble() ) <= 0 ) {
			out.println("Ma che ooohh??? Non puoi inserire valori negativi o nulli! Riscrivi la base: ");
		}
		out.println("Inserisci l'Altezza della Figura: ");
		while ((h = in.readDouble()) <= 0 ) {
			out.println("Ma che ooohh??? Non puoi inserire valori negativi o nulli! Riscrivi l'altezza: ");
		}
		
		if ( Double.compare( b , h ) == 0) {
			return new Quadrato(b);
		}
		else {
			return new Rettangolo(b,h);
		}
		
	}
	
	//---------------------------------------------------------------------------------------
	
	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		char Choice = 'y';
		Rettangolo Letto, Massimo = null;
		
		while( Character.toLowerCase(Choice) == 'y' ){
			out.println("Vuoi inserire un rettangolo? (Y/n) ");
			Choice = in.readChar();
			if (Character.toLowerCase(Choice) == 'y') {
				Letto = ChiediFigura();
				if (Massimo == null || Letto.getArea() > Massimo.getArea()) {
					Massimo = Letto;
				}
			}
			else if (Character.toLowerCase(Choice) != 'n') {
				out.print("Non ho capito... ");
				Choice = 'y';
			}
		}
		
		//qui dovrei avere Massimo == null se non ho mai letto nulla o Massimo che punta a quadrato o rettangolo se ho letto
		
		if(Massimo == null) {
			out.println("Nessun Rettangolo inserito, io chiudo ");
		}
		else if (Massimo instanceof Quadrato) {
			out.println("Il rettangolo di area maggiore è un quadrato di lato " + Massimo.getBase());
		}
		else {
			out.println("Il rettangolo di area maggiore ha base " + Massimo.getBase() + " e altezza " + Massimo.getAltezza());
		}
		
		
	}

}
