import java.util.*; 

public class HmmVit {
	
	@SuppressWarnings({ "serial", "unused" }) //questo non ho capito cosa sia ma è legato alle hashmap che non hanno un "serial UID"
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
		v.put("F", new ArrayList<Double>(Arrays.asList(0.0d)));
		v.put("L", new ArrayList<Double>(Arrays.asList(-0.51d))); //queste sono le probabilità del carattere iniziale, servirebbe un check per stabilirle: fatte a mano dipende troppo dalla sequenza

		// pointers matrix path : HashMap String to List<String>
		Map<String, List<String>> path = new HashMap<String, List<String>>();
		path.put("F", new ArrayList<String>(Arrays.asList("*")));
		path.put("L", new ArrayList<String>(Arrays.asList("*")));

		// Viterbi/pointers variables
		String max_state;
		Double max_state_v = 0.0d;
		String[] states = {"F", "L"};
		Double max_v;
		String max_prev_state;
		String max_path;

		// Sequence variables
		int idx = 1;		// position tracker
		String obsidx = "";	// current symbol (at position idx)

		// INITIALIZATION
		for (String tmp_obs: obs_list){
			// Get current dice roll value ( or END)
			if(idx>obs_list.length-1){obsidx="END";}else{obsidx=obs_list[idx];}

			max_state="";
			max_state_v=0.0d;

			for(String state2: states){ //state2 è lo stato di arrivo
				// outer cycle variables:
				max_v = -1.0d; //questo non dovrebbe esistere: setta una treshold senza senso ai valori che posso avere come probabilità nell'if, dove dovrei scegliere SEMPRE qualcosa
				max_prev_state = "X";
				max_path = "XX";

				for(String state1: states){ 	//per ogni stato di partenza voglio sapere quanto è probabile finire in quello d'arrivo
					String tmp_trans = state1+state2; //un valore valido nelle chiavi di P_transition
					// VITERBI ITERATION																		//   |-- questo get è di lista, non di hashmap
					Double tmp_v = P_emission.get(state2).get(tmp_obs) + P_transition.get(tmp_trans) + v.get(state1).get(idx-1);   // la probabilità di emettere il valore corrente nello stato2 (il max viene selezionato dall'if sotto)
					// testo da dove arriva il valore state2 											// nota che il conto sopra è una prob condizionata grazie a ipotesi markoviana (prodotto) e scala log (prod è somma)
					if(tmp_v > max_v){ //questa cosa butta le transizioni brutte e tiene solo le due migliori per lo stato F e L
						max_v = tmp_v;
						max_prev_state = state1;
						max_path = state1+state2;
					}
				}
				// store variables for next step
				path.get(state2).add(idx,max_prev_state);
				v.get(state2).add(idx,max_v);
			}		
			idx=idx+1;
		}
		// Final state :
		String last_state = "X";
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

		//v matrix
		// YOUR CODE HERE (OPT)
		
		// path matrix (pointers)
		// YOUR CODE HERE (OPT)

	}// end of main method
}