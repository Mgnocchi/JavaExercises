package Executables;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import MyDataToBio.SQLSantaCruz.UCSCInterrogation;
import MyDataToBio.Sequences.DnaSequence;

/*Scrivere un metodo che legga degli identificativi di mRNA da un file e che estragga le
sequenze corrispondenti a tali identificativi (non in formato FASTA) .*/
public class C2_es2 {
	
	public static void main(String[] args) {
		String fileName; //l'argomento può essere inserito da linea di comando, se no viene chiesto finchè non inseriamo un nome valido
			if(args.length == 0 ) {
				fileName = "";
			}else {
				fileName = args[0];
			}
		String seqName;
		UCSCInterrogation interrogator = new UCSCInterrogation();
		
		//Apro un lettore consolle fuori dal ciclo per evitare si chiuda 
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		
		while(!fileName.equals(":q")) {	//ripeto la richiesta del file finchè è necessario o viene 
			try(BufferedReader lettoreFile = new BufferedReader(new FileReader(fileName))){
				while((seqName = lettoreFile.readLine())!= null) {	//supponiamo che ogni riga del file contenga solo l'identificatore univoco di una sequenza
					String tmpSqlStatement = "select * from knownGeneMrna where name like '" + seqName +"'";
					ArrayList<DnaSequence> trascritti = interrogator.AskMRNA(tmpSqlStatement);
					if(!trascritti.isEmpty()) {
						System.out.println( trascritti.get(0).getSequence() ); //gli identificatori sono univoci, questa è al più l'unica sequenza
					}else {
						System.out.println("La sequenza " + seqName + " non è stata trovata");
					}
				}
				fileName = ":q"; //se sono qui ho letto correttamente un file e sono arrivato alla fine
			}catch(FileNotFoundException e) { //se non trovo il file ne chiedo di nuovo il nome
				System.out.print("Nessun nome file valido inserito, riprova (:q per uscire)\n> ");
				try {
					fileName = consoleReader.readLine();
				} catch (IOException e1) {	//se non riesco a leggere da consolle ho un problema
					e1.printStackTrace();
					break;
				}
			} catch (IOException e1) { //qui ho aperto il file ma non riesco a leggerlo
				e1.printStackTrace();
				break;
			}
		}//fine while

	}
	
	
	
	
}

