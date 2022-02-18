import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class es1 {
	static String path = "/home/gnowwho/Documents/ProgRisorse&Consegne/CONSEGNE/2020-11-18/covid.csv";
	
	public static void lettoreDati(ArrayList<Integer> listaCasi,ArrayList<Integer> listaMorti, ArrayList<String> listaPaesi) throws NullPointerException{
		String line;
		String[] campi;
		Integer casi = 0, morti = 0;
		int index;
		
		if (listaCasi == null || listaMorti == null|| listaPaesi == null) {
			throw new NullPointerException("Arguments of lettoreDati cannot be null pointers");
		}
		else {
			try {
				BufferedReader lettore = new BufferedReader(new FileReader(path));
				lettore.readLine(); //Butto la prima riga
				while( (line = lettore.readLine()) != null) { //finchè non leggo EOF
					campi = line.split(",");					//splitto le righe in campi
					casi = Integer.valueOf(campi[4]);
					morti = Integer.valueOf(campi[5]);
					
					if ( (index = listaPaesi.indexOf(campi[6])) <0){	//se non ho mai letto il paese
						listaPaesi.add(campi[6]);
						listaCasi.add(casi);
						listaMorti.add(morti);
					}
					else { //il paese è già in elenco in listaPaesi: devo aggiungere valTemp al giusto campo in listaCasi
						listaCasi.set(index, listaCasi.get(index) + casi);
						listaMorti.set(index, listaMorti.get(index) + morti);
					}
				}
				lettore.close();
			}
			catch(FileNotFoundException e) {
				System.out.println("File non trovato. Il programma usa un path predefinito, editare nel codice sorgente");
				System.exit(0);
			}
			catch(IOException e) {
				System.out.println("File trovato, ma la lettura non è possibile");
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	//Il CFR è positivo per definizione; uso un valore negativo per segnalare il problema
	public static double CFR(int casi, int morti) {
		if(morti > 0 && casi > 0) {
			return ((double) morti)/casi;
		}
		else {
			return -1.0;
		}
		
	}
	
	
	
	public static void main(String[] args) {
		ArrayList<Integer> listaCasi = new ArrayList<Integer>();
		ArrayList<Integer> listaMorti = new ArrayList<Integer>();
		ArrayList<String> listaPaesi = new ArrayList<String>();
		int indiceCFR = -1, maxCasi = -1, indiceCasi = -1;
		double lastCFR = -1.0, thisCFR = 0.0;
		//CFR case fatality ratio
		
		lettoreDati(listaCasi, listaMorti, listaPaesi);
		
		for(int i=0; i<listaPaesi.size(); i++) {				//per ogni paese
			if (listaCasi.get(i) > maxCasi) {					//idem per i morti (guarda CFR, ho invertito
				indiceCasi = i;
				maxCasi=listaCasi.get(i);
			}
			if (listaMorti.get(i).equals(0)) {
				continue;		//se un Paese ha 0 morti il suo CFR non è definito, ma posso sempre confrontarlo con i morti totali
								//quindi skippo il giro solo a quest'altezza
			}
			thisCFR = CFR(listaCasi.get(i),listaMorti.get(i));	//calcolo il CFR
			if(thisCFR > lastCFR) {								//se è maggiore dell'ultimo registrato
				indiceCFR = i;									//salvo l'indice e aggiorno l'ultimo CFR
				lastCFR = thisCFR;									//al termine indiceCFR è l'indice del paese con CFR maggiore
			}
			
		}
		
		System.out.println("Il paese con CFR maggiore è: " + listaPaesi.get(indiceCFR));
		System.out.println("con un CFR di: " + lastCFR);
		System.out.println();
		System.out.println("Il paese con il numero maggiore di morti è: " + listaPaesi.get(indiceCasi) );
		System.out.println("con " + maxCasi + " morti");
	}

}
