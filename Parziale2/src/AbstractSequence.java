import java.io.PrintWriter;
import java.util.*;


public abstract class AbstractSequence {
//campi------------------------------
	protected String sequence;
	protected String header;
//metodi----------------------------------------
	public String getHeader(){
		return header;	
	}

	public void setHeader(String header){
		this.header = header;
	}

	public String getSequence(){
		return sequence;
	}

	public void setSequence(String sequence){
		this.sequence = sequence;
	}

	public abstract Set<Character> getAlphabet();	// Abstract method

	public float getRatioValid(){
		Set<Character> set = getAlphabet();
		float numValid = 0;
		for(Character c : sequence.toCharArray()){
			if(set.contains(c)){
				numValid++;
			}
		}
		return numValid/sequence.length();
	}
	
	// Defaults on system.out
	public void printFasta(){
		printFasta(this.sequence, this.header, null, true, 60);
	}
	public void printFasta(int token){
		printFasta(this.sequence, this.header, null, true, token);
	}
	// static variants
	public static void printFasta(String header, String seq) {
		printFasta(seq, header, null, true, 60);
	}
	public static void printFasta(String header, String seq, int token) {
		printFasta(seq, header, null, true, token);
	}
	
	// Uses the specified PrintWriter
	public void printFasta(PrintWriter scriba) {
		printFasta(this.sequence, this.header, scriba, false, 60);
	}
	public void printFasta(PrintWriter scriba, int token){
		printFasta(this.sequence, this.header, scriba, false, token);
	}
	// static variants
	public static void printFasta(String header, String seq, PrintWriter scriba) {
		printFasta(seq, header, scriba, false, 60);
	}
	public static void printFasta(String header, String seq, PrintWriter scriba, int token) {
		printFasta(seq, header, scriba, false, token);
	}
	
	
	// metodo wrapper che stampa con lo scrittore fornito, per ridurre lo spaghetti code
	private static void printFasta(String seq, String header,PrintWriter scriba, boolean stdout, int tokenslength){
		System.out.println(">"+header);
		    // 60 characters (FASTA standard)
		// spezza stringa in sottostringhe di tokenslength caratteri e le salva in String array
		String[] seqlines = seq.split(String.format("(?<=\\G.{%1$d})", tokenslength));
		if (stdout) {
			for(String s: seqlines) {
				System.out.println(s);     // stampa fullsequence in righe di tokenslength caratteri
			}
		} else {
			for(String s: seqlines) {
				scriba.println(s);     // stampa fullsequence in righe di tokenslength caratteri
			}
		}
	}
	
//Costruttori
	public AbstractSequence(String header, String sequence){
		this.header = header;
		this.sequence = sequence;
	}
	//non ho costruttore vuoto
}
