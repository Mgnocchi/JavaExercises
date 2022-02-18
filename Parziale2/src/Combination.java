import java.util.*;

public class Combination {
    // members:
	    private List<Integer[]> indexcombo = new ArrayList<Integer[]>();

	// methods
	    public void combinations(Integer[] arr, int len){
	    	this.combinations(arr, len, 0, new Integer[len]);
	    }
	    //arr contains the alphabet
	    //len is the lenght of words (parameter and needed for recursion)
	    //starting position (for recursion)
	    //result (is the partial combination stored since now)
	    // there should obviously be a poliformic public form that calls this with only 
	    // This method shouldn't be called from outside and would need unreasonable knowledge of its structure
	    private void combinations(Integer[] arr, int len, int startPosition, Integer[] result){
	    	if (len == 0){									
	    		Integer[] Intarr = result.clone();				// serve una COPIA di result, che ora contine tutta la combinazione, altrimenti mi trovo multipli riferimenti all'ultima combinazione computata
	    		this.indexcombo.add(Intarr); 					
	    		return;
	    	}
	    	for(int i = startPosition; i <= arr.length-len; i++){ // 2) qui il limite superiore serve a non avere lettere troppo "alte" e non averne abbastanza strettamente maggiori per finire la parola 
	    														// 1) per ogni carattere, lo annoto, poi voglio che sia seguito da tutte le parole più corte di uno rispetto alla lunghezza obiettivo,
	    		result[result.length - len] = arr[i];			// formate dai soli caratteri che lo seguono (dato che non conta l'odine il reciproco non lo voglio trovare)
	    		combinations(arr, len-1, i+1, result);			// quando la parola che deve seguire quella che ho archiviato ha lunghezza nulla, ho una delle parole che cerco, e cambio l'ultimo carattere
	    	}													// tra i possibili valori che può assumere, quando li ho presi tutti faccio lo stesso col penultimo e così via, finchè non ho scorso tutti i caratteri iniziali.
	    }

	    //get
	    public List<Integer[]> getCombos(){
	    	return this.indexcombo;
	    }
}

