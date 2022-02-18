package MyDataToBio.Sequences;

import java.util.HashMap;
import java.util.Set;

public abstract class AbstractSequence {
//Fields
	protected String sequence;
	protected String header;

//methods
	//constructors
	public AbstractSequence(String header, String sequence){
		this.header = header;
		this.sequence = sequence;
	}
	
	//GET and SET-------------------------------
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
	//-------------------------------------------
	public abstract Set<Character> getAlphabet();
	
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
	
	//Since each character in the alphabet is a character any non-single-character string will do fine for non-alphabet items
	//this can be used for substrings and whatnot. the default use case is demanded to the non static call.
	public static HashMap<String,Integer> getComposition(String sequence, AbstractSequence abSeq){
		Set<Character> alphabet = abSeq.getAlphabet();
		int notAlphabet = 0;
		//we first initialize the hashmap
		HashMap<String,Integer> output = new HashMap<>();
		for (char letter : alphabet) {
			output.put(String.valueOf(letter), 0); //letter is char not Character
		}
		//we then count and modify it as needed
		for (char item : sequence.toCharArray()) {
			if (alphabet.contains(item)) {//item contains only one character; if it's part of the alphabet
				output.put(String.valueOf(item),(output.get(String.valueOf(item)) + 1));
			}else {
				notAlphabet++;
			}
		}
		if(notAlphabet > 0) {
			output.put("NA", notAlphabet);
		}
		return output;
	}
	//non static polimorphic method
	public HashMap<String,Integer> getComposition(){
		return getComposition(this.sequence,this);
	}
	
	
	//print
	public void printFasta(int tokensLength){
		System.out.println(">"+header);
		// spezza stringa in sottostringhe di tokenslength caratteri e le salva in String array
		String[] seqlines = this.sequence.split(String.format("(?<=\\G.{%1$d})", tokensLength));
		for(String s: seqlines) {
			System.out.println(s);        // stampa fullsequence in righe di tokenslength caratteri
		}
	}
	
	public void printFasta(){
		this.printFasta(60); //standard FASTA lenght
	}

	

}
