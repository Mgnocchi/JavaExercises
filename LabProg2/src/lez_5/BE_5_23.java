package lez_5;

//chiedi un numero, per ogni valore da 2 a quello elimina ogni multiplo del numero corrente e passa al numero successivo. usa la classe Insieme<E>
//della libreria standard.

import prog.io.*;
import prog.utili.Insieme;
//import java.lang.Math.*;


public class BE_5_23 {

	public static void main(String[] args) {
		int limiteMax, limiteRipetizioni, limiteMultipli, aux;
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		Insieme<Integer> primi = new Insieme<Integer>();
	
	//leggo il limite superiore
		out.println("Questo programma cerca Numeri Primi col Crivello di Eratostene, quindi sii buono per favore; Inserisci il limite superiore: ");
		limiteMax = in.readInt();
	//se troppo basso esco con un messaggio	
		if (limiteMax<2) {
			out.println("Non ci sono primi prima del 2 bruh");
		}
	//altrimenti eseguo l'algoritmo	
		else {
		
		//parto da una griglia che va da 2 al limite massimo inclusi	
			for(int i=2; i<=limiteMax; i++) {
				primi.add(i);
			}
		//devo testare solo valori a quadrato minore del limite	
			limiteRipetizioni = (int) Math.sqrt(limiteMax);		//Dovrò togliere i multipli solo dei numeri minori o uguali all'arrotondamento per difetto 
																//a intero della radice quadrata
			
			for (int i=2; i<=limiteRipetizioni; i++) {			//li passo tutti (ovviamente parto da 2)
				if(primi.contains(i)) {							//se non sono mai stati tolti
					limiteMultipli = limiteMax/i;				//fisso il massimo coefficente per cui il multiplo del numero corrente è
																//incluso nell'intervallo di ricerca (quindi arrotondo per difetto)
					
					for (int j=i; j<=limiteMultipli;j++) {		//qui vorrei un for-each elemento minore di limite multipli
																//ma va fatto manualmente perchè devo modificare l'insieme
						
						if(primi.contains(aux=i*j) ) {			//se è in primi cerco il multiplo e se lo trovo lo tolgo
							primi.remove(aux);
						}
					}
				}
			}
			
		//Al termine dovrebbero essere rimasti solo i primi
			
		//stampo i primi
			out.println("I numeri primi da 2 a " + limiteMax + " sono i seguenti: ");
			for(int num:primi)
				out.println(num);
			
		}	


	}

}
