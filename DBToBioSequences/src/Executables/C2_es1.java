package Executables;

import MyDataToBio.Sequences.*;

import java.util.ArrayList;

import MyDataToBio.SQLSantaCruz.*;

// Scrivere un metodo che permetta di estrarre delle sequenze proteiche
public class C2_es1 {
	
	public static void main(String[] args) {
		UCSCInterrogation interrogator = new UCSCInterrogation();
		String tmpSqlStatement = "select * from knownGenePep limit 5";
		ArrayList<DnaSequence> trascritti = interrogator.AskMRNA(tmpSqlStatement);
		
		for (DnaSequence seq:trascritti) {
			seq.printFasta();
		}

	}
	
}
