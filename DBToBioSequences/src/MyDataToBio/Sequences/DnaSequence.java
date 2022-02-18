package MyDataToBio.Sequences;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import TeachDataToBio.Seq.AbstractSequence;

public class DnaSequence extends AbstractSequence implements dnaTranslation{
//Fields------------------------------
	protected HashMap<String, String> frames = null;				//having these at null makes easy to know if initialized
	protected HashMap<String, String[]> frameCodons = null;
	protected HashMap<String, String> translatedFrames = null;
//Constructors------------------------
	public DnaSequence(String header, String sequence){
		super(header, sequence);
	}
//Methods-----------------------------
	
	public Set<Character> getAlphabet(){	
		Set<Character> set = new LinkedHashSet<Character>();
		set.add('A');
		set.add('C');
		set.add('G');
		set.add('T');
		return Collections.unmodifiableSet(set);
	}
	
	//get and set for header and sequence defined in super
//	getFrames //make polimorphic so that you may call specific frames
	public HashMap<String, String> getFrames(){
		return this.frames;
	}
	public String getFrames(String key) {
		try {
			return this.frames.get(key);
		}
		catch (Exception e) {
			return null;
		}
	}
	
//	getFrameCodons
	public HashMap<String, String[]> getFrameCodons(){
		return this.frameCodons;
	}
	public String[] getFrameCodons(String key) {
		try {
			return this.frameCodons.get(key);
		}
		catch (Exception e) {
			return null;
		}
	}
	
//	getTranslations
	public HashMap<String, String> getTranslations(){
		return this.translatedFrames;
	}
	public String getTranslations(String key) {
		try {
			return this.translatedFrames.get(key);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	//invert the transcript: no Header as there seems to not be a convention for complementary transcript names
	public static String reverseComplement(String ogSeq) {
		String[] basi = ogSeq.split("");
		String compl = "";
		for (String base:basi) {
			switch (base) { //remember that the complementary would be 3'-5', but we want a 5'-3' sequence
				case "A":
					compl = "T" + compl;
					break;
				case "T":
					compl = "A" + compl;
					break;
				case "C":
					compl = "G" + compl;
					break;
				case "G":
					compl = "C" + compl;
					break;
				default:
					compl = base + compl;
					break;
			}
		}
		return compl;
	}
	//polimorphic non-static version
	public String reverseComplement() {
		return reverseComplement(this.sequence);
	}

	
	@Override
	//since this method initializes two things we don't necessairly know which one to pass to the user
	public void initFrames() {
		if(this.frames == null) { //dato che frames e frameCodons sono protected so che solo questa funzione li inizializza, quindi posso evitare che si esegua se è già stata chiamata sulla sequenza corrente
			int codonLength = dnaTranslation.codLength, frameLength = 0;
			String frameBuffer;
			String[] codonBuffer;
			this.frames = new HashMap<>();
			this.frameCodons = new HashMap<>();
			
			//the f1 frame is not translated
			frameLength = this.sequence.length(); //we have to remove the end so that its length is multiple of the length of codons
			frameLength = frameLength - (frameLength % codonLength); // % is the module of the division, so the remainder
			frameBuffer = this.sequence.substring(0, frameLength);
			codonBuffer = frameBuffer.split(String.format("(?<=\\G.{%1$d})", codonLength));
			this.frames.put("f1", frameBuffer); //save the frame
			this.frameCodons.put("f1", codonBuffer); //save the codons
		
			//the f2 frame is translated by one base
			frameLength = this.sequence.length() - 1 ; //we don't count the first character as it will be soon removed
			frameLength = frameLength - (frameLength % codonLength); //we need this number to be multiple of codonLength
			frameBuffer = this.sequence.substring(1, frameLength + 1); 
			codonBuffer = frameBuffer.split(String.format("(?<=\\G.{%1$d})", codonLength));
			this.frames.put("f2", frameBuffer); //save the frame
			this.frameCodons.put("f2", codonBuffer); //save the codons
		
			//the f3 frame is translated by two basis
			frameLength = this.sequence.length() - 2 ; //we don't count the first two character as they will be soon removed
			frameLength = frameLength - (frameLength % codonLength); //we need this number to be multiple of codonLength
			frameBuffer = this.sequence.substring(2, frameLength + 2); 
			codonBuffer = frameBuffer.split(String.format("(?<=\\G.{%1$d})", codonLength));
			this.frames.put("f3", frameBuffer); //save the frame
			this.frameCodons.put("f3", codonBuffer); //save the codons
		
			//All the r frames refer to the reverse complementary of the transcript
			//the r1 frame is not translated
			frameLength = this.sequence.length(); //the reverse is as long as the original sequence
			frameLength = frameLength - (frameLength % codonLength); //we need this number to be multiple of codonLength
			frameBuffer = this.reverseComplement().substring(0, frameLength); //we use the reverse
			codonBuffer = frameBuffer.split(String.format("(?<=\\G.{%1$d})", codonLength));
			this.frames.put("r1", frameBuffer); //save the frame
			this.frameCodons.put("r1", codonBuffer); //save the codons
		
			//the r2 frame is translated by one base
			frameLength = this.sequence.length() - 1; //the reverse is as long as the original sequence
			frameLength = frameLength - (frameLength % codonLength); //we need this number to be multiple of codonLength
			frameBuffer = this.reverseComplement().substring(1, frameLength + 1); //we use the reverse
			codonBuffer = frameBuffer.split(String.format("(?<=\\G.{%1$d})", codonLength));
			this.frames.put("r2", frameBuffer); //save the frame
			this.frameCodons.put("r2", codonBuffer); //save the codons
		
			//the r3 frame is translated by two basis
			frameLength = this.sequence.length() - 2; //the reverse is as long as the original sequence
			frameLength = frameLength - (frameLength % codonLength); //we need this number to be multiple of codonLength
			frameBuffer = this.reverseComplement().substring(2, frameLength + 2); //we use the reverse
			codonBuffer = frameBuffer.split(String.format("(?<=\\G.{%1$d})", codonLength));
			this.frames.put("r3", frameBuffer); //save the frame
			this.frameCodons.put("r3", codonBuffer); //save the codons
		}
	}

	
	@Override
	public HashMap<String, String> translateFrames() {
		if (this.translatedFrames == null) {
			this.translatedFrames = new HashMap<>();
			for(String frameKey : this.frameCodons.keySet()){ //for each key
				String currentTranslation = "";
				for(String codon : this.frameCodons.get(frameKey)){ //for each codon in that key
					currentTranslation = currentTranslation + gencode.get(codon);
				}
				translatedFrames.put(frameKey,currentTranslation);
			}
		}
		return this.translatedFrames;
	}
	
	
	
	
		
}

