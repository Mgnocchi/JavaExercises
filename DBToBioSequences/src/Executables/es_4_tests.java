package Executables;

import java.util.ArrayList;

import MyDataToBio.SQLSantaCruz.UCSCInterrogation;
import MyDataToBio.Sequences.DnaSequence;

public class es_4_tests {

	public static void main(String[] args) {
		UCSCInterrogation interrogator = new UCSCInterrogation();
		String tmpSqlStatement = "select value, knownCanonical.transcript , knownCanonical.clusterId from knownToRefSeq inner join knownIsoforms on knownToRefSeq.name=knownIsoforms.transcript join knownCanonical on knownIsoforms.clusterId=knownCanonical.clusterId where value like 'NM_002929' and chrom not like '%\\\\_%' group by transcript;";
		
		System.out.println("-------------------------------------------------");
		interrogator.RemoteConnUCSCgenericQuery(tmpSqlStatement,true);
		System.out.println("-------------------------------------------------");
		
		tmpSqlStatement = "select knownGeneMrna.name, seq from knownToRefSeq inner join knownIsoforms on knownToRefSeq.name=knownIsoforms.transcript join knownCanonical on knownIsoforms.clusterId=knownCanonical.clusterId join knownGeneMrna on knownCanonical.transcript=knownGeneMrna.name where chrom not like '%\\\\_%' and value like 'NM_002929' group by knownCanonical.transcript;";
		
		System.out.println("-------------------------------------------------");
		ArrayList<DnaSequence> gencodeAsList = interrogator.AskMRNA(tmpSqlStatement);
		
		for(DnaSequence gencode:gencodeAsList) {
			gencode.printFasta();
		}
		
	}

}
