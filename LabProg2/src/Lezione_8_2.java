import prog.io.*;
import prog.utili.*;

//accetta diversi quadrati, rettangoli e cerchi e restituisce l'oggetto con la maggiore area (identificando la figura)
public class Lezione_8_2 {
	
	private static Figura ChiediFigura() {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		char Choice;
		
		do {
			out.println("Vuoi inserire un Rettangolo o un Cerchio? (R/C) ");
			Choice = in.readChar();
			
			if (Character.toLowerCase(Choice) == 'c') {
				return ChiediCerchio();
			}
			else if (Character.toLowerCase(Choice) == 'r') {
				return ChiediRettangolo();
			}
			out.println("Scelta non riconosciuta!");
		} while ( true );
		
	}
	
	private static Cerchio ChiediCerchio () {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		double r;
		
		out.println("Inserisci il raggio della Figura: ");
		while ((r = in.readDouble() ) <= 0 ) {
			out.println("Ma che ooohh??? Non puoi inserire valori negativi o nulli! Riscrivi la base: ");
		}
		
		return new Cerchio(r);
	}
	
	private static Rettangolo ChiediRettangolo() {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		double b = 0,h = 0;
		
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
		Figura Letto, massimo = null;
		
		while( Character.toLowerCase(Choice) == 'y' ){
			out.println("Vuoi inserire una figura? (Y/n) ");
			Choice = in.readChar();
			if (Character.toLowerCase(Choice) == 'y') {
				Letto = ChiediFigura();
				if (massimo == null || Letto.haAreaMaggiore(massimo)) {
					massimo = Letto;
				}
			}
			else if (Character.toLowerCase(Choice) != 'n') {
				out.print("Non ho capito... ");
				Choice = 'y';
			}
		}
		
		//qui dovrei avere massimo == null se non ho mai letto nulla o massimo che punta a figura se ho letto
		
		if(massimo == null) {
			out.println("Nessuna figura inserita, io chiudo ");
		}
		else if(massimo instanceof Rettangolo){
			if (massimo instanceof Quadrato) {
				//faccio la cosa burina: le dimensioni non sono recuperabili dal tipo figura perchè non implementate astrattamente. Riesco a fare un casting nel subtype?
				Quadrato massimoQuadrato = (Quadrato) massimo;
				out.println("La figura di area maggiore è un quadrato di lato " + massimoQuadrato.getBase());
			}
			else{
				Rettangolo massimoRettangolo = (Rettangolo) massimo; //madonna che sporcizia
				out.println("La figura di area maggiore è un rettangolo di base " + massimoRettangolo.getBase() + " e altezza " + massimoRettangolo.getAltezza());
			}
		}
		else {
			Cerchio massimoCerchio = (Cerchio) massimo; //però sticazzi, funziona
			out.println("La figura di area maggiore è un Cerchio di raggio "+ massimoCerchio.getRaggio());
		}
		
	}

}
