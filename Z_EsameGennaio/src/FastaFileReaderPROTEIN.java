import java.io.*;
import java.util.*;

public class FastaFileReaderPROTEIN {
	
	public static List<ProteinSequence> readFasta(String inputfile) throws FileNotFoundException {
		List<ProteinSequence> ProteinSequenceList = new ArrayList<ProteinSequence>();
			
		boolean first = true;
		String tmpSeq = "";
		String tmpHeader = "";
		ProteinSequence proteinSeq;
			
		try (Scanner sc = new Scanner(new File(inputfile))) {
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.charAt(0) == '>') {
					if (first){
						first = false;
						tmpHeader = line.substring(1);
					}else{
						proteinSeq = new ProteinSequence(tmpHeader, tmpSeq);
						ProteinSequenceList.add(proteinSeq);
						tmpSeq = "";
						tmpHeader = line.substring(1);
					}
				} else {
					tmpSeq = tmpSeq + line;
				}
			}
		}
		proteinSeq = new ProteinSequence(tmpHeader, tmpSeq);
		ProteinSequenceList.add(proteinSeq);
		return ProteinSequenceList;
	}

}

