//caricare i dati da waste.csv in /home/gnowwho/Documents/ProgRisorse&Consegne/LAB7/

//Scrivere un programma che legge il file 'waste.csv' e analizzi i dati del 2016
//relativi all'Italia.  In particolare, calcolare la percentuale di rifiuti
//prodotti da ciascun settore nell'anno 2016 in Italia. Stampare l'elenco dei
//settori ordinati per quantità di rifiuti prodotti nel 2016 in Italia e la
//percentuale associata a quel settore.
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Lab7_es2 {
	static String path ="/home/gnowwho/Documents/datiEsercizi/waste.csv";
	
	public static void lettoreDati(ArrayList<IndexableElement<Double>> listaValori, ArrayList<String> listaSettori) {
		String line;
		String[] campi;
		IndexableElement<Double> eleTemp = null;
		int ind = 0;
		Double valTemp = 0.0;
		
		try {
			BufferedReader lettore = new BufferedReader(new FileReader(path));
			lettore.readLine(); //Butto la prima riga
			while( (line = lettore.readLine()) != null) {
				line = line.replaceAll("\"","");
				campi = line.split("\\|");
				if ( campi[1].equals("Italy") && campi[5].equals("2016")){
					valTemp = Double.valueOf(campi[12]);
					eleTemp = new IndexableElement<Double>(valTemp, ind);
					listaValori.add(eleTemp);
					listaSettori.add(campi[3]);	//sto supponendo occorrenze uniche
					ind++;
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

	public static void main(String[] args) {
		ArrayList<IndexableElement<Double>> listaValori = new ArrayList<IndexableElement<Double>>();
		ArrayList<String> listaSettori = new ArrayList<String>();
		
		lettoreDati(listaValori, listaSettori);
		
		listaValori.sort(null);	//sort col comparatore naturale: IndexableElement sfrutta quello degli elementi per stabilire un ordine
		for (int i=0; i<listaValori.size(); i++) {
			System.out.println(listaValori.get(i) +":\t"+ listaSettori.get(listaValori.get(i).getIndex()) );	//L'indice salvato nel campo apposito di IndexableElement ricorda
		}
		
		
	}

}
