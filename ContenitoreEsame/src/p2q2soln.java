// ---------- PROGRAMMAZIONE II ---------------------
//              AA 2020-2021
//
// Sistema d'esame - PARZIALE 1
// Domanda 2:             Correzione di eccezione
//
// Questo  e'  il  template  di  una  classe java con-
// tenente del codice nel metodo main. In esso e' pre-
// sente un errore dovuto ad  impossibilita' di effet-
// tuare in modo automatico una conversione di tipo.
//
// La soluzione dell'esercizio comporta:
// - lettura di documentazione delle classi importate
// - identificazione di  un  metodo che  puo'  essere
//   utilizzato per effettuare la conversione
// - utilizzo di tale metodo per riuscire a compilare
//   l'istruzione di assegnamento senza sollevare una
//   eccezione di tipo ClassCastException.
// - NON E' CONSENTITO CAMBIARE IL TPO DELLA VARIABILE
//   stringArray.
//
// --------------- WARNING: -------------------------
//
// - NON cambiate il nome della classe
// - NON aggiungete costruttori
//
// -------------- CONSEGNA --------------------------
//
// In questo programma sono coinvolte due classi.
//
// - La classe F e' generica
// - essa contiene un membro privato (myArray, array di Object)
// - essa contiene un metodo (getArray) che restituisce myArray
//
// - La classe p2q2soln e' la classe di test per domanda 2
// - Il suo metodo main crea una istanza di F con il tipo concreto String
// - Il suo metodo main crea un array di Stringhe e cerca di assegnare ad
//   esso l'array restituito dal metodo getArray dall'istanza di f creata
//   in main
// - Questa operazione solleva una eccezione di tipo ClassCastException
//  
//
// - L'obiettivo dell'esercizio e' quello di  riuscire a  salvare l'ar-
//   ray restituito dalla chiamata f.getArray() in stringArray.
//
// - SUGGERIMENTO:
//   - java.utils.Arrays NON E' importato per caso!!!
//   - l'operazione e' possibile mediante una operazione di COPIA
//   - leggete il contenuto della documentazione di Arrays
//



import java.util.Arrays;

class p2q2soln {

@SuppressWarnings("unused")
public static void main(String args[]) {
         F<String> f = new F<String>();	//qui chiamo la classe generica F assegnando String come parametro

         // Wrong way:   (comment the following line before submission)
         //String[] stringArray =(String[]) f.getArray();

         // Correct way:
         ///////////// YOUR CODE HERE /////////////////////////////////
         Object[] host = f.getArray();												//Soluzione
         String[] stringArray = Arrays.copyOf(host, host.length, String[].class); 
         //////////////////////////////////////////////////////////////
}
                        
}

class F<E> {								//la classe F ha solo il costruttore vuoto, un campo che è inizializzato come un array di Object non inizializzato e un solo metodo che lo restituisce
@SuppressWarnings("unchecked")
private E[] myArray = (E[])new Object[10]; //L'array di E contiene Objects castati forzatamente a E
public E[] getArray() {
         return myArray;				//il riferimento ha tipo E[], ma le istanze tipo Object[]
}
}

/*Perchè la soluzione funziona: 
Poichè passo un riferimento con tipo String[] ma con dentro Object il compilatore non si lamenta, perchè il 
riferimento ha il tipo giusto, ma in esecuzione conosco solo il tipo degli oggetti, non quelli del riferimento, 
e la JVM si trova a dover castare Object in String, e non riesce.
Quindi lo mettiamo in un array di Object, e il compilatore non si lamenta perchè String è subclass di Object
poi usiamo copyOf specificando di voler produrre una copia del tipo String[].class che alla peggio gestisce la cosa 
al runtime con un throw ArrayStoreException

*/
