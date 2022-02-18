package lez_5;

import prog.io.ConsoleInputManager;
import prog.io.ConsoleOutputManager;
import prog.utili.Frazione;
import java.util.ArrayList;



public class BE_5_29 {
	
	//MEDIA----------------------------------------------------------------------
		private static Frazione media(ArrayList<Frazione> lista) {
			Frazione somma = new Frazione(0);
			
			for(Frazione f:lista) {
				somma = somma.piu(f);
			}
			
			return(somma.diviso(new Frazione (lista.size() ) ) );
		}
	//DELTA----------------------------------------------------------------------
		private static Frazione delta(Frazione a,Frazione b ) {
			if(a.compareTo(b)<0) {
				return b.meno(a);
			} else return a.meno(b);
		}
		
	//MINIMO_DELTA--------------------------------------------------------------	//restituisce la frazione in lista con minor delta da frac
		private static Frazione minimoDelta(ArrayList<Frazione> lista, Frazione frac) {
			Frazione deltaVecchio = null, soluzione = null, deltaNuovo = null;
			//ConsoleOutputManager out = new ConsoleOutputManager();
			
			for(Frazione f:lista) {
				deltaNuovo = delta(f,frac);
				if ( deltaVecchio == null || (deltaNuovo.isMinore(deltaVecchio)) ) {
					soluzione = f;
					deltaVecchio = deltaNuovo;
				}
			}
			
			return soluzione;
			
		}
		
		
		
		public static void main(String[] args) {
			ConsoleInputManager in = new ConsoleInputManager();
			ConsoleOutputManager out = new ConsoleOutputManager();
			char choice;
			int num, den;
			ArrayList<Frazione> listaFrazioni = new ArrayList<Frazione>();
			Frazione frac = null;
			
			while(true) {
				out.println("Vuoi inserire una frazione? (Y/n) ");
				if( (choice = Character.toLowerCase(in.readChar())) == 'y') {
					out.println("Inserisci il numeratore: ");
					num = in.readInt();
					out.println("Inserisci il denominatore: ");
					den = in.readInt();
					listaFrazioni.add(new Frazione(num,den));
					continue;
				}
				else if (choice != 'n') {
					out.println("Carattere non valido ");
					continue;
				}
				break;
			}
			
			//se non sono state inserite frazioni esci
			if (!listaFrazioni.isEmpty()) {
				//poi calcolare media
				out.println("La media delle frazioni inserite è " + ( frac = media(listaFrazioni) ));
				//qui deve trovare e stampare la frazione più vicina alla media
				out.println("Tra quelle inserite, la frazione più vicina alla media è " + minimoDelta(listaFrazioni,frac));
			} else out.println("Non sono state inserite Frazioni");
		
		}
	
}
