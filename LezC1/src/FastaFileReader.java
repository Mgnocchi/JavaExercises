import java.io.*;
import java.util.*;

public class FastaFileReader {

	public static List<DnaSequence> readFasta(String inputfile) throws FileNotFoundException {
		List<DnaSequence> DnaSequenceList = new ArrayList<DnaSequence>();
		
		boolean first = true;
		String tmpSeq = "";
		String tmpHeader = "";
		DnaSequence dnaseq;
		
		try (Scanner sc = new Scanner(new File(inputfile))) { //non ho memory leak perchè il blocco try è condizionato allo scanner. quando il blocco finisce lo scanner è chiuso e tutto è deallocato dalla jvm e garbage collector
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

}
