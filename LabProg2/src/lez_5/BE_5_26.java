package lez_5;

import prog.io.ConsoleInputManager;
import prog.io.ConsoleOutputManager;
import prog.utili.Frazione;
import java.util.ArrayList;
import java.util.Collections;

//Leggi una sequenza di frazioni, calcola media, moda e mediana.

public class BE_5_26 {

	//MEDIA----------------------------------------------------------------------
	private static Frazione media(ArrayList<Frazione> lista) {
		Frazione somma = new Frazione(0);
		
		for(Frazione f:lista) {
			somma = somma.piu(f);
		}
		
		return(somma.diviso(new Frazione (lista.size() ) ) );
	}
	
	//MEDIANA---------------------------------------------------------------------
	private static Frazione mediana(ArrayList<Frazione> lista) {
		
		if( lista.size()%2 == 0 ) { //se la lista ha un numero pari di elementi
			ArrayList<Frazione> dueCentro = new ArrayList<Frazione>();
			dueCentro.add(lista.get( lista.size()/2 - 1));
			dueCentro.add(lista.get( lista.size()/2 ));
			
			return media(dueCentro);
		}
		else return lista.get(lista.size()/2); //prende l'elemento centrale (ricorda che si arrotonda per difetto e gli array partono da 0)
	}
	

	
	//MODA-------------------------------------------------------------------------
		private static Frazione moda(ArrayList<Frazione> lista) {
			//posso supporre gli elementi siano ordinati e ce ne sia almeno uno
			Frazione frequente = null, corrente = null;
			int freqFrequente = 0, freqCorrente = 1;
			
			for(int i = 0; i<lista.size(); i++) {
				corrente = lista.get(i);
				if ( i<((lista.size() - 1)) && corrente.equals(lista.get( i+1 )) ){
					freqCorrente++;
					continue;
				}
				else if (freqCorrente > freqFrequente){
					freqFrequente = freqCorrente;
					frequente = corrente;
				}
				freqCorrente = 1;
			}
			return frequente;
		}

	
	
	
	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		char choice;
		int num, den;
		ArrayList<Frazione> listaFrazioni = new ArrayList<Frazione>();
		
		
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
			//deve ordinare le frazioni
				Collections.sort(listaFrazioni); //qui serve che si passi una lista o array di tipi che supportano l'interfaccia "Comparable", che penso sia per lo più avere il metodo compareTo
			//poi calcolare media, moda, mediana
				out.println("La media delle frazioni inserite è " + media(listaFrazioni));
				out.println("La mediana delle frazioni inserite è " + mediana(listaFrazioni));
				out.println("La moda delle frazioni inserite è " + moda(listaFrazioni));
		} else out.println("Non sono state inserite Frazioni");
	
	}

}
