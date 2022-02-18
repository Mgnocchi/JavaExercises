import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//Il programma deve leggere i codici a inizio riga, poi stampare per ognuno il numero di occorrenze di ogni lettera dell'alfabeto. il resto della stringa non serve
//OCCHIO CAZZO: tocca salvare tutto perchè potrei avere ripetizioni!

public class lab_6_Parser {
	
	//TODO secondo me questo è meglio che passi l'errore al main, così gestisco solo lì problemi dello standard input
	//legge il nome file tramite il lettore comunicando s sullo standard output, e lo restituisce
	public static File AskFile(String s, BufferedReader lettore){
		System.out.println(s);
		try {
			return new File(lettore.readLine());
		}
		catch(IOException e){
			System.out.println("Stream Standard non disponibile");
			e.printStackTrace();
			return null;
		}
	}
	
	//parser per ogni riga: rimuove i caratteri fino al primo spazio e li stampa, poi invoca contaLettere su ogni lettera dell'alfabeto e stampa il risultato
	public static void parsaRiga(String s, boolean stampaZeri) {
		String[] sSplit = s.split(" ", 2); //la coda è un po' un mischione ma poco importa date le operazioni che devo compiere
		//sSplit[0] contiene il nome della sequenza, sSplit[1] la sequenza con un paio di campi numerici che non sono n' di interesse, nè problematici
		int contaTemp = 0;
		if(sSplit.length == 2){
			System.out.print(sSplit[0] + ":: ");
			for(char c = 'A'; c<='Z';c++) {
				if ( (contaTemp=contaLettere(sSplit[1],c)) > 0 || stampaZeri )
				System.out.print(c + ":" + contaTemp + " ");
			}
			System.out.println();
		}
		
	}
	
	public static void parsaRigaBeta(String s, ArrayList<VirusRecord> virusList) {
		
		if(virusList == null) {							//per sicurezza mi assicuro che virusList esista
			virusList = new ArrayList<VirusRecord>();
		}
		
		String[] sSplit = s.split(" ", 2); //la coda è un po' un mischione ma poco importa date le operazioni che devo compiere
		//sSplit[0] contiene il nome della sequenza, sSplit[1] la sequenza con un paio di campi numerici che non sono n' di interesse, nè problematici
		int where = 0;
		
		if(sSplit.length == 2){
			if(!virusList.isEmpty()) { //se fosse vuota il for-each non va
				for(VirusRecord rec:virusList) {
					if( (rec.getNome()).equals(sSplit[0])) {
						break;
					}
					else {
						where++;
					}
				}
			}
			
			if ( where >= virusList.size() || virusList.isEmpty()) { //debbo aggiungerlo
				virusList.add(new VirusRecord(sSplit[0])); //In questo momento comunque where ha dentro l'indice della posizione in cui sta questo elemento appena aggiunto
			}
			//else where ha esattamente il valore della posizione in cui l'ho trovato (se anche ho skippato il primo blocco, è ancora 0)
			//ora in ogni caso VirusList[where] va editato aggiornando i campi di frequenza delle lettere
			// virusList.set(where, element) dove element è preso da virusList.get(where) aggiornato 
			
			virusList.set(where, 
					AggiornaOccorrenze( virusList.get(where), sSplit[1]) );
			
		}//l'else è "ho una riga vuota"
	}
	
	public static VirusRecord AggiornaOccorrenze(VirusRecord vrs, String s) {
		int contaTemp = 0;
		
		for(char c = 'A'; c<='Z';c++) {
			if ( (contaTemp=contaLettere(s,c)) > 0) {
				vrs.addNumero(vrs, c, contaTemp);
			}
		}
		return vrs;
	}
	
	//serve un servizio che stampi tutto
	
	//conta le occorrenze del carattere c nella stringa s
	public static int contaLettere(String s, char c) {
		int occorrenze = 0;
		
		for (int i=0; i<s.length(); i++) {
			if( s.charAt(i) == c ) {
				occorrenze++;
			}
		}
		return occorrenze;
	}
	
	//Legge (y/n)
	public static boolean readYesNo(BufferedReader in) throws IOException {
		char choice;
		String temp = null;
		do{
			temp = in.readLine();
			if(temp == null || temp.length()>1) {
				System.out.println("ERROR: L'input deve essere un singolo carattere, inserire (Y/n):");
				continue;
			}
			else {
				choice = Character.toLowerCase( temp.charAt(0) ); 
				if (choice != 'y' && choice != 'n') {
					System.out.println("Carattere non valido, scegliere (Y/n): ");
					continue;
				}
				else if(choice == 'y') {
					return true;
				}
				else if(choice == 'n') {
					return false;
				}
			}
			
		}while(true);
	}
	
	
	public static void stampaVirus(ArrayList<VirusRecord> virusList, boolean stampaZeri) {
		for(VirusRecord vrs:virusList) {
			System.out.print( vrs.getNome() + ":: ");
			char letter = 'A';
			for(int num:vrs.getOccorrenze()) {
				if(stampaZeri) {
					System.out.print(Character.toString(letter) + ":" + num + " ");
				}
				else if (num>0) {
					System.out.print(Character.toString(letter) + ":" + num + " ");
				}
			letter++;
			}
			System.out.println();	
		}
	}
	
	public static void main(String[] args) {
		File inFile = null;		//	/home/gnowwho/eclipse-workspace/VirusParser/R1AB_SARS2_msa.txt
		BufferedReader lettoreConsole = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader lettoreFile = null;
		boolean nulle = false;
		String sBuffer = null;
		ArrayList<VirusRecord> listaVirus = new ArrayList<VirusRecord>();
		
		// ------ Legge il nome del file assicurandosi esista
		while(inFile == null){
			inFile = AskFile("Inserire indirizzo assoluto del file da leggere", lettoreConsole);
			if (!inFile.exists()) {
				System.out.println("Il file indicato non esiste, verificare l'indirizzo");
				inFile = null;
			}
		}
		
		System.out.println("Vuoi stampare anche le occorrenze nulle?(Y/n)");
		try {
			nulle=readYesNo(lettoreConsole);
		}
		catch(IOException e) {
			System.out.println("C'è un problema nel canale d input di default: piangere e riavviare");
			e.printStackTrace();
		}
		
		try {
			lettoreFile = new BufferedReader(new FileReader(inFile));
			while( (sBuffer = lettoreFile.readLine()) != null ) {
				if(sBuffer!="") {
					parsaRigaBeta(sBuffer,listaVirus);
				}
			}
			lettoreFile.close();
		}
		catch(FileNotFoundException e){
			System.out.println("ERROR: Collegamento col file interrotto: Weird!");
			e.printStackTrace();
		}
		catch(IOException e) {
			System.out.println("ERROR: Operazioni di lettura fallite");
			e.printStackTrace();
		}
		
	//TODO stampare	
		stampaVirus(listaVirus, nulle);
		
	//-------Chiude lettore	
	try {
		lettoreConsole.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
