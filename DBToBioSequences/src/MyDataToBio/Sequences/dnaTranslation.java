package MyDataToBio.Sequences;

import java.util.HashMap;

//My guess is that this interface exist only not to instantiate the translation table for each DnaSequence object
public interface dnaTranslation {
//fields
	final int codLength = 3;
	@SuppressWarnings("serial")
	final HashMap<String, String> gencode = new HashMap<String,String>(){{
		put("AAA","K");	// * is not a codon. It means Stop codon
		put("AAC","N"); // Start is always M
		put("AAG","K");
		put("AAT","N");
		put("ACA","T");
		put("ACC","T");
		put("ACG","T");
		put("ACT","T");
		put("AGA","R");
		put("AGC","S");
		put("AGG","R");
		put("AGT","S");
		put("ATA","I");
		put("ATC","I");
		put("ATG","M"); // Start
		put("ATT","I");
		put("CAA","Q");
		put("CAC","H");
		put("CAG","Q");
		put("CAT","H");
		put("CCA","P");
		put("CCC","P");
		put("CCG","P");
		put("CCT","P");
		put("CGA","R");
		put("CGC","R");
		put("CGG","R");
		put("CGT","R");
		put("CTA","L");
		put("CTC","L");
		put("CTG","L");
		put("CTT","L");
		put("GAA","E");
		put("GAC","D");
		put("GAG","E");
		put("GAT","D");
		put("GCA","A");
		put("GCC","A");
		put("GCG","A");
		put("GCT","A");
		put("GGA","G");
		put("GGC","G");
		put("GGG","G");
		put("GGT","G");
		put("GTA","V");
		put("GTC","V");
		put("GTG","V");
		put("GTT","V");
		put("TAA","*");
		put("TAC","Y");
		put("TAG","*");
		put("TAT","Y");
		put("TCA","S");
		put("TCC","S");
		put("TCG","S");
		put("TCT","S");
		put("TGA","*");
		put("TGC","C");
		put("TGG","W");
		put("TGT","C");
		put("TTA","L");
		put("TTC","F");
		put("TTG","L");
		put("TTT","F");
	}};	
//methods
	abstract void initFrames();
	abstract HashMap<String, String> translateFrames();
	
}
