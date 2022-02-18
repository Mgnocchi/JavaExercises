package Executables;

import java.util.ArrayList;

import MyDataToBio.SQLSantaCruz.UCSCInterrogation;
import MyDataToBio.Sequences.DnaSequence;


/*Estrarre la sequenza dell' RNA avente identificativo RefSeq NM_002929 . Suggerimento:
SERVONO 3 JOIN e dovete utilizzare la tabella knownToRefSeq.*/
public class C2_es4 {

	// select name,seq from knownToRefSeq inner join knownGeneMrna where value like 'NM_002929' limit 1;
	public static void main(String[] args) {
		UCSCInterrogation interrogator = new UCSCInterrogation();
		//knownToRefSeq associates a single RefSeq code (here I assume for gene classification) to multiple Ensembl identifiers of the transcripts of that gene. This seems reasonable as RefSeq codes seem to account for isoforms with a ".n" after the code, which is omitted in the table knownToRefSeq. 
		//if this interpretation is incorrect I wander why am I supposed to make one in the first place given the specificity of the matter and how little the peculiarities of gene codification and SQL are propaedeutical to Object Programming.
		String tmpSqlStatement = "select knownGeneMrna.name, seq from knownToRefSeq inner join knownIsoforms on knownToRefSeq.name=knownIsoforms.transcript join knownCanonical on knownIsoforms.clusterId=knownCanonical.clusterId join knownGeneMrna on knownCanonical.transcript=knownGeneMrna.name where chrom not like '%\\_%' and value like 'NM_002929' group by knownCanonical.transcript;";
		ArrayList<DnaSequence> sequenze = interrogator.AskMRNA(tmpSqlStatement);
		
		System.out.println("Ensembl and default transcript of NM_002929:");
		for (DnaSequence seq:sequenze) {
			seq.printFasta();
		}
		
		
	}

}
