import java.io.*;
import java.util.*;

public class FastaFileReader {

	public static List<DnaSequence> readFasta(String inputfile) throws FileNotFoundException {
		List<DnaSequence> DnaSequenceList = new ArrayList<DnaSequence>();
		
		boolean first = true;
		String tmpSeq = "";
		String tmpHeader = "";
		DnaSequence dnaseq;
		
		try (Scanner sc = new Scanner(new File(inputfile))) {
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.charAt(0) == '>') {
					if (first){
						first = false;
						tmpHeader = line.substring(1);
					}else{
						dnaseq = new DnaSequence(tmpHeader, tmpSeq);
						DnaSequenceList.add(dnaseq);
						tmpSeq = "";
						tmpHeader = line.substring(1);
					}
				} else {
					tmpSeq = tmpSeq + line;
				}
			}
		}
		dnaseq = new DnaSequence(tmpHeader, tmpSeq);
		DnaSequenceList.add(dnaseq);
		return DnaSequenceList;
	}

}
