import java.io.*;
import java.util.*;

public class MyTest {

	public static void main(String[] args) throws FileNotFoundException {
		DnaSequence mySeq = new DnaSequence("sequence 1", "ATTCGGATCGATAGCTGATTAATAGCTTAGAGGATCGGATATATAGCTAGTCGAAGCTAGTC");
		System.out.println(">"+mySeq.getHeader()+"\n"+mySeq.getSequence());
		System.out.println(mySeq.getRatioValid());
		System.out.println(mySeq.getAlphabet());

		// illegal operation :
		// mySeq.getAlphabet().add("Z");
		
		mySeq.printFasta();

		//FastaFileReader fr = new FastaFileReader();	//per qualche motivo produceva questo oggetto pur avendo il metodo statico e chiamandolo su tale oggetto.
														//non da problemi legati all'impossibilità di chiamare metodi statici quindi boh, probabilmente un lapsus
		List<DnaSequence> seqlist = new ArrayList<DnaSequence>();
		seqlist = FastaFileReader.readFasta("sequences.fasta");

		for(DnaSequence s: seqlist){
			s.printFasta();
		}
	
	}

}
