import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//La base di questo codice è il codice dei punti 2 e 3
//la creazione di P_emission è fatta nell'assunzione che le probabilità del dado F siano quelle attese (quindi in scala logaritmica ogni esito di questo dado avrà probabilità 0 e quelle del dado truccato saranno relative a queste)

public class LabB_P_emission {
	
	public static String filePath = "/home/gnowwho/Documents/eclipse-workspace/Lab_B/emission_in.txt";
	
	//assume che la prima riga faccia da "attesa"
	//poichè dobbiamo lavorare in scala logaritmica vogliamo che nelle righe successive compaia al più un sottoinsieme degli esiti della riga di teshold: non abbiamo un modo di rappresentare la probabilità 1. 
	//questo limite non è hardcoded nei check, ma rispettato nella compilazione delle frequenze ignorando gli esiti diversi.
	public static Map<String, Map<String, Double>> PEmissionBuilder(String filename){
		
		try{
			BufferedReader lettore = new BufferedReader (new FileReader(filename));
			String buffer;
			String[] buffSplit;
			String[] spanStati = null;
			Double[] frequenzaBaseStati = null;
			Map<String, Map<String, Double>> P_emission = new HashMap<>();
			int i;
			
			// I) Setto le treshold leggendo la prima riga.
			if(((buffer = lettore.readLine()) !=null) && (buffSplit = buffer.split("\t")).length == 2 ) { //non faccio nulla se non ho una riga da leggere o se splitta male
				ArrayList<String> esiti = new ArrayList<>();
				ArrayList<Integer> occorrenze = new ArrayList<>();
				
				P_emission.put(buffSplit[0], new HashMap<>()); //crea la riga relativa allo stato della prima riga
				for(String c : buffSplit[1].split("")) { //per ogni carattere dello stato letto voglio misurarne le occorrenze totali, sul totale di caratteri (che è buffSplit[1].length)
					//se il carattere è in esiti aumento il counter, altrimenti lo aggiungo e aggiungo un counter in posizione identica e lo setto a 1;
					if ((i = esiti.indexOf(c))<0) {	//se non c'è
						esiti.add(c);
						occorrenze.add(1);
					}
					else {						//se c'è
						occorrenze.set(i, occorrenze.get(i)+1);
					}
				}
				//qui ho in esiti tutti gli esiti e occorrenze il numero raw delle occorrenze
				spanStati = new String[occorrenze.size()];
				frequenzaBaseStati = new Double[occorrenze.size()];
				for (i=0; i<frequenzaBaseStati.length; i++) {
					spanStati[i] = esiti.get(i);
					frequenzaBaseStati[i] = (double)occorrenze.get(i)/buffSplit[1].length();
					P_emission.get(buffSplit[0]).put(spanStati[i], 0.0d);
				}
			}
			
			//per ogni riga diversa dalla prima le operazioni sono diverse. Separo nel codice perchè la complessità è sostanzialmente uguale e così è più leggibile
			while ((buffer = lettore.readLine()) !=null) {
				buffSplit = buffer.split("\t");
				
				if (buffSplit.length != 2) continue; //se ho righe che non splittano correttamente salto il giro
				
				P_emission.put(buffSplit[0], new HashMap<>());
				int[] buffOccorrenze = new int[spanStati.length];
				Arrays.fill(buffOccorrenze, 0); //inizializzo l'occorrenza di ogni esito possibile a 0
				
				for(String c : buffSplit[1].split("")) { //per ogni carattere dello stato letto voglio misurarne le occorrenze totali
					for(i=0; i<spanStati.length; i++) { 
						if(c.equals(spanStati[i])) {	//se lo trovo tra gli Esiti ammessi
							buffOccorrenze[i]++;		//aumento
						}
					}
				}//qui ho contato tutte le occorrenze
				//ora per ogni carattere devo calcolare la probabilità, e fare il log della probabilità relativa
				for (i=0; i<spanStati.length; i++) {
					// p_i = (double)buffOccorrenze[i]/buffSplit[1].length
					// p_i_rel = p_i / frequenzaBaseStati[i]
					// poi Math.log
					P_emission.get(buffSplit[0]).put(spanStati[i], Math.log( ( (double)buffOccorrenze[i]/buffSplit[1].length() ) / frequenzaBaseStati[i] ) ); 
				}
			}//fine lettura ultima riga
			
			lettore.close();
			return P_emission;
		}
		catch (FileNotFoundException e) {
			return null;
		}
		catch (IOException e) {
			return null;
		}
	}
	
	//funzione ausiliaria per 
	public static void PEmissionPrinter(Map<String, Map<String, Double>> P_emission) {
		String[] chiaviEsterne = {""}, chiaviInterne = {""};
		chiaviEsterne = P_emission.keySet().toArray(chiaviEsterne);
		try {
			chiaviInterne = P_emission.get(chiaviEsterne[0]).keySet().toArray(chiaviInterne);  //by design queste sono tutte uguali
		}catch(IndexOutOfBoundsException e){
			System.out.println("No line was read");
		}
		
		//intestazione
		System.out.print("\t");
		for (String key: chiaviInterne) {
			System.out.print(key + "\t");
		}
		System.out.println("");
		//chiave e probabilità
		for (String keyEx: chiaviEsterne) {
			System.out.print(keyEx + "\t");
			for (String keyIn: chiaviInterne) {
				System.out.print(String.format("%.2f", P_emission.get(keyEx).get(keyIn)) + "\t");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		// Dice rolls sequence as String
		String dice_seq = "3151166666624";
		// cast it into a String array
		String[] obs_list = dice_seq.split("");

		// ---------------- HMM DEFINITION ---------------------------

		// Emission probs
		Map<String, Map<String, Double>> P_emission = new HashMap<>();
		
		// Emission probs for F state
		P_emission = PEmissionBuilder(filePath);
		if (P_emission == null) {
			System.out.println("File non esistente o risorsa occupata, chiusura");
			System.exit(1);
		}
		
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

		// INITIALIZATION
		for (String tmp_obs: obs_list){
			for(String state2: states){ //state2 è lo stato di arrivo
				// outer cycle variables:
				max_prev_state = "X"; //non particolarmente utile perchè non ho mai check, ma inizializziamo for safety reasons
				max_v = null;
				for(String state1: states){ 	//per ogni stato di partenza voglio sapere quanto è probabile finire in quello d'arrivo
					String tmp_trans = state1+state2; //un valore valido nelle chiavi di P_transition
					// VITERBI ITERATION																		
					Double tmp_v = P_emission.get(state2).get(tmp_obs) + P_transition.get(tmp_trans) + v.get(state1).get(idx-1);   // la probabilità di emettere il valore corrente nello stato2 (il max viene selezionato dall'if sotto)
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
		
		//decommenta per stampare P_emission
		//PEmissionPrinter(P_emission);

	}// end of main method
}
