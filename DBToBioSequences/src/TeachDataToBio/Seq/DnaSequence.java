package TeachDataToBio.Seq;
//Implementazione lezione

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.Set;

public class DnaSequence extends AbstractSequence implements IBioSeq {
	// private members
	private final int CODONLEN = 3;
	private Map<String,String[]> Frames = new HashMap<String,String[]>();
	private Map<String,String> Translations = new HashMap<String,String>();
	private Map<String, String> readingFrames = new HashMap<String, String>();

	// IBioSeq implementation
	// 1) reverseComplement -----------------------------------------------
	public String reverseComplement(String sequence){
		String resString = "";
		String[] nucleotides = sequence.split("");
		for(String nt: nucleotides){
			switch(nt){
				case "A":
					resString = "T" + resString;
					break;
				case "T":
					resString = "A" + resString;
					break;
				case "C":
					resString = "G" + resString;
					break;
				case "G":
					resString = "C" + resString;
					break;
			}
		}
		return resString;
	}
	// 2) computeReadingFrames --------------------------------------------
	public Map<String, String[]> computeReadingFrames(String sequence){
		String[] CurStringArray;
		String superseq = super.sequence;
		String f1 = "";
		String f2 = "";
		String f3 = "";
		String r1 = "";
		String r2 = "";
		String r3 = "";
		int mod3len = 0;

		// Reading Frame +1 (f1)
		f1 = superseq;
		mod3len = f1.length() % CODONLEN;
		f1 = f1.substring(0, f1.length() - mod3len); // now length of f1 is multiple of 3
		CurStringArray  = f1.split(String.format("(?<=\\G.{%1$d})", CODONLEN));
		Frames.put("f1",CurStringArray);
		readingFrames.put("f1", f1);
		// Reading Frame +2 (f2)
		f2 = f1.substring(1);
		mod3len = f2.length() % CODONLEN;
		f2 = f2.substring(0, f2.length() -mod3len); // now length of f2 is multiple of 3
		CurStringArray  = f2.split(String.format("(?<=\\G.{%1$d})", CODONLEN));
		Frames.put("f2",CurStringArray);
		readingFrames.put("f2", f2);
		// Reading Frame +3 (f3)
		f3 = f1.substring(2);
		mod3len = f3.length() % CODONLEN;
		f3 = f3.substring(0, f3.length() -mod3len); // now length of f3 is multiple of 3
		CurStringArray  = f3.split(String.format("(?<=\\G.{%1$d})", CODONLEN));
		Frames.put("f3",CurStringArray);
		readingFrames.put("f3", f3);
		// Reading Frame -1 (r1)
		r1 = reverseComplement(f1);	// length of r1 is already multiple of 3
		CurStringArray  = r1.split(String.format("(?<=\\G.{%1$d})", CODONLEN));
		Frames.put("r1", CurStringArray);
		readingFrames.put("r1", r1);
		// Reading Frame -2 (r2)
		r2 = r1.substring(1);
		mod3len = r2.length() % CODONLEN;
		r2 = r2.substring(0, r2.length() -mod3len); // now length of r2 is multiple of 3
		CurStringArray  = r2.split(String.format("(?<=\\G.{%1$d})", CODONLEN));
		Frames.put("r2", CurStringArray);
		readingFrames.put("r2",r2);
		// Reading frame -3 (r3)
		r3 = r1.substring(2);
		mod3len = r3.length() % CODONLEN;
		r3 = r3.substring(0,r3.length() -mod3len); // now length of r3 is multiple of 3
		CurStringArray  = r3.split(String.format("(?<=\\G.{%1$d})", CODONLEN));
		Frames.put("r3", CurStringArray);
		readingFrames.put("r3",r3);

		return this.Frames;
	}
	// 3) translateFrames --------------------------------------------------------------
	public Map<String,String> translateFrames(Map<String,String[]> frames){
		for(String readingFrame : this.Frames.keySet()){
			String curTranslation = "";
			for(String codon : this.Frames.get(readingFrame)){
				curTranslation = curTranslation + gencode.get(codon);
			}
			Translations.put(readingFrame,curTranslation);
		}
		return this.Translations;
	}

	// 4) getComposition --------------------------------------------------------------
	public Map<String,Integer> getComposition(String sequence, AbstractSequence as){
		Set<Character> sc = as.getAlphabet();
		Map<String,Integer> tmpRes = new HashMap<String,Integer>();
		for(Character c: sc){
			tmpRes.put(c.toString(),0);
		}
		for(String symbol : sequence.split("")){
			tmpRes.put(symbol, (tmpRes.get(symbol) + 1) );
		}
		return tmpRes;
	}

	// End of IBioSeq implementation --------------------------------------------------
	
	// AbstractSequence implementation ------------------------------------------------
	public Set<Character> getAlphabet(){	
		Set<Character> set = new LinkedHashSet<Character>();
		set.add('A');
		set.add('C');
		set.add('G');
		set.add('T');
		return Collections.unmodifiableSet(set);
	}

	// --------------------------------------------------------------------------------

	// this method prints all the computed translations
	public void printTranslations(){
		for(String readingFrame : this.Translations.keySet()){
			String curheader = super.header + ":" + readingFrame;
			String curSequence = Translations.get(readingFrame);
			// Print in FASTA format
			System.out.println(">"+curheader);
			int tokenslength = 60;
			String[] seqlines = curSequence.split(String.format("(?<=\\G.{%1$d})", tokenslength));
			for(String s: seqlines) {
				System.out.println(s);
			}
		}
	}

	// this method prints all the codons in the computed reading frames
	public void printFramesCodons(){
		for(String readingFrame : this.Frames.keySet()){
			System.out.print(readingFrame+"\t");
			int i = 0;
			String[] strarray = this.Frames.get(readingFrame);
			for(String c : strarray){
				if(i>0){System.out.print(".");}
				System.out.print(strarray[i]);
				i++;	
			}
			System.out.println();
		}
	} 

	// Constructor
	public DnaSequence(String header, String sequence){
		super(header, sequence);
		Map<String, String[]> rf = computeReadingFrames(sequence);
		Map<String,String> tr = translateFrames(rf);
	}	
}
