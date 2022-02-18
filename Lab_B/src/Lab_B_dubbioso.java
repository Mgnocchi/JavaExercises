import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

public class Lab_B_dubbioso {
	@SuppressWarnings("serial")//servirebbe per controllare che gli oggetti serializzati e deserializzati siano della stessa "versione" e non siano stati cambiati in struttura nel frattempo
	public static void main(String[] args){
		// Dice rolls sequence as String
		String dice_seq = "3151166666624";
		// cast it into a String array
		String[] obs_list = dice_seq.split("");

		// ---------------- HMM DEFINITION ---------------------------

		// Emission probs
		Map<String, Map<String, Double>> P_emission = new HashMap<>();
		// Emission probs for F state
		P_emission.put("F", new HashMap<>(){{
			put("1",0.0d);						//qui il warning deriva dal fatto che questi put non sono
			put("2",0.0d);						//agganciati ad un oggetto di tipo noto in fase di compilazione
			put("3",0.0d);
			put("4",0.0d);
			put("5",0.0d);
			put("6",0.0d);
		}});
		// Emission probs for L state
		P_emission.put("L", new HashMap<>(){{
			put("1",-0.51d);
			put("2",-0.51d);
			put("3",-0.51d);
			put("4",-0.51d);
			put("5",-0.51d);
			put("6",1.10d);
		}});
		// Transition probs
		Map<String, Double> P_transition = new HashMap<>();
		P_transition.put("FF",0.64d);
		P_transition.put("FL",-2.30d);
		P_transition.put("LL",0.59d);
		P_transition.put("LF",-1.61d);

		// -----------------------------------------------------------


		// Viterbi matrix v :  HashMap String to List<Double>
		Map<String, List<Double>> v = new HashMap<String, List<Double>>(); 	//bastano due chiavi perchè mi basta ricordare la posizione di probabilità più alta in uno stato
		v.put("F", new ArrayList<Double>(Arrays.asList(P_emission.get("F").get(obs_list[0]))));
		v.put("L", new ArrayList<Double>(Arrays.asList(P_emission.get("L").get(obs_list[0])))); //queste sono le probabilità del carattere iniziale: le vado a prendere leggendo il primo carattere e guardando nel modello salvato

		// pointers matrix path : HashMap String to List<String>
		Map<String, List<String>> path = new HashMap<String, List<String>>();
		path.put("F", new ArrayList<String>(Arrays.asList("*")));
		path.put("L", new ArrayList<String>(Arrays.asList("*")));

		// Viterbi/pointers variables
		String[] states = {"F", "L"};
		Double max_v;
		String max_prev_state;

		// Sequence variables
		int idx = 1;		// position tracker
		Double tmp_v = null;
		// INITIALIZATION
		for (String tmp_obs: obs_list){
			for(String state2: states){ //state2 è lo stato di arrivo
				// outer cycle variables:
				max_prev_state = "X"; //non particolarmente utile perchè non ho mai check, ma inizializziamo for safety reasons
				max_v = null;
				for(String state1: states){ 	//per ogni stato di partenza voglio sapere quanto è probabile finire in quello d'arrivo
					// VITERBI ITERATION
					if (idx==1) { //al primo giro non ho transizioni su cui condizionare
						tmp_v = P_emission.get(state2).get(tmp_obs) + v.get(state1).get(idx-1); //questo addendo, qui, è la probabilità di partire nello stato dato, su quello si condiziona
					}else { //dopo si
						String tmp_trans = state1+state2; //un valore valido nelle chiavi di P_transition
						tmp_v = P_emission.get(state2).get(tmp_obs) + P_transition.get(tmp_trans) + v.get(state1).get(idx-1);   // la probabilità di emettere il valore corrente nello stato2 (il max viene selezionato dall'if sotto)
					}
					// testo da dove arriva il valore state2 											// nota che il conto sopra è una prob condizionata grazie a ipotesi markoviana (prodotto) e scala log (prod è somma)
					if( max_v == null || tmp_v > max_v){ //questa cosa butta le transizioni brutte e tiene solo le due migliori per lo stato F e L
						max_v = tmp_v;
						max_prev_state = state1;
					}
				}
				// store variables for next step
				path.get(state2).add(idx,max_prev_state);
				v.get(state2).add(idx,max_v);
			}		
			idx=idx+1;
		}
		// Final state :
		String last_state = "X"; //non particolarmente utile perchè non ho mai check, ma inizializziamo for safety reasons
		//quando faccio l'ultimo giro idx è 1 in più dell'ultimo indice dell'array
		if(v.get("F").get(idx-1) > v.get("L").get(idx-1)){ last_state="F"; }else{ last_state="L"; }
		//Print final state :
		System.out.println("\nFinal state: " + last_state + " ( v = " + v.get(last_state).get(idx-1)  + " )");

		// TRACEBACK: 
		// Abbiamo il valore massimo dell'ultima colonna. Alla prima iterazione usa il valore dell'ultima colonna,
		// prende lo stato precedente e lo aggiunge al vettore degli stati. Poi fa un update cosi' che alla pros-
		// sima iterazione parte dal valore precedente. L'ordine degli  stati emessi dal traceback e' da inverti-
		// re (o, in alternativa, e' possibile costruire la serie di stati direttamente in ordine inverso). 

		// traceback variables:
		List<String> state_seq = new ArrayList<String>();
		String prev_state="";
		
		for(int i = idx-1; i>0; i--){
			prev_state = path.get(last_state).get(i);
			state_seq.add(prev_state);
			last_state = prev_state;
		}

		// Stampa la sequenza di stati che ha prodotto la sequenza di osservazioni in input con la massima
		// probabilita' dato il modello (prob. emissione stato specifici + prob. transizione).
		System.out.println("\n[Output]");
		System.out.println(String.join("", obs_list));
		Collections.reverse(state_seq);
		System.out.println(String.join("", state_seq));


		// EXTENDED OUTPUT:
		System.out.println();
	//v matrix
		//intestazione
		System.out.println("\nViterbi Matrix:");
		System.out.print("Obs.\tB");
		for(String obs:obs_list) System.out.print("\t" + obs);
		System.out.println();
		//valori per F
		System.out.print("F");
		for(Double prob:v.get("F")) {
			System.out.print("\t" + String.format("%.2f", prob));
		}
		System.out.println();
		//valori per L
		System.out.print("L");
		for(Double prob:v.get("L")) {
			System.out.print("\t" + String.format("%.2f", prob));
		}
		System.out.println();
		
	// path matrix (pointers)
		//formato mat viterbi
		//intestazione
				System.out.println("\nPointer Matrix:");
				System.out.print("Obs.\tB");
				for(String obs:obs_list) System.out.print("\t" + obs);
				System.out.println();
				//valori per F
				System.out.print("F");
				for(String arrivo:path.get("F")) {
					System.out.print("\t" + arrivo);
				}
				System.out.println();
				//valori per L
				System.out.print("L");
				for(String arrivo:path.get("L")) {
					System.out.print("\t" + arrivo);
				}
				System.out.println();
				
		//formato specifico puntatori
		System.out.println();
		for (int i=1; i<obs_list.length+1 ; i++){ //arrivo a length + 1 perchè sono in offset di 1 per facilitare la stampa e l'accesso alla hashmap
			System.out.println("Step " + i + "\tEmission: " + obs_list[i-1]);
			for(String state2: states){ //state2 è lo stato di arrivo
				System.out.print("    Max path to " + state2 +":"+ path.get(state2).get(i));
				if (state2==path.get(state2).get(i)) {
					System.out.println();
				}else System.out.println("  *");
			}		
			System.out.println("-----------------------------------------------");
		}

	}// end of main method
}