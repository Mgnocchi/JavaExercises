package MyDataToBio.Sequences;

import java.io.*;
import java.util.*;


public class FastaFileReader {

	//Reads fasta as DnaSequence.
	public static List<DnaSequence> readFasta(String inputfile) throws FileNotFoundException {
		List<DnaSequence> DnaSequenceList = new ArrayList<DnaSequence>();
		
		boolean first = true;
		String tmpSeq = "";
		String tmpHeader = "";
		DnaSequence dnaseq;
		
		try (Scanner sc = new Scanner(new File(inputfile))) { //non ho resource leak perchè il blocco try è condizionato allo scanner. quando il blocco finisce lo scanner è chiuso e tutto è deallocato dalla jvm e garbage collector
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.charAt(0) == '>') {
					if (first){
						first = false;
						tmpHeader = line.substring(1);
					}else{
						dnaseq = new DnaSequence(tmpHeader, tmpSeq);
						DnaSequenceList.add(dnaseq);
						tmpHeader = line.substring(1);
						tmpSeq = "";
					}
				} else {
					tmpSeq = tmpSeq + line;
				}
			}
			dnaseq = new DnaSequence(tmpHeader, tmpSeq);
			DnaSequenceList.add(dnaseq);
		}
		return DnaSequenceList;
	}
	
	//Reads Fasta as RnaSequence. I know, it's weird
	public static List<RnaSequence> readFastaRNA(String inputfile) throws FileNotFoundException {
		List<RnaSequence> RnaSequenceList = new ArrayList<RnaSequence>();
		
		boolean first = true;
		String tmpSeq = "";
		String tmpHeader = "";
		RnaSequence rnaseq;
		
		try (Scanner sc = new Scanner(new File(inputfile))) { //non ho resource leak perchè il blocco try è condizionato allo scanner. quando il blocco finisce lo scanner è chiuso e tutto è deallocato dalla jvm e garbage collector
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.charAt(0) == '>') {
					if (first){
						first = false;
						tmpHeader = line.substring(1);
					}else{
						rnaseq = new RnaSequence(tmpHeader, tmpSeq);
						RnaSequenceList.add(rnaseq);
						tmpHeader = line.substring(1);
						tmpSeq = "";
					}
				} else {
					tmpSeq = tmpSeq + line;
				}
			}
			rnaseq = new RnaSequence(tmpHeader, tmpSeq);
			RnaSequenceList.add(rnaseq);
		}
		return RnaSequenceList;
	}

}
