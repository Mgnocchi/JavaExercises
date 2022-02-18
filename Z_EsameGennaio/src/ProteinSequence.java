import java.util.Collections;
//import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.Set;

// protein alphabet:
// A, R, N, D, C, Q, E, G, H, I, L, K, M, F, P, S, T, W, Y, V, *  
// QUESTION 1: Why 0 is not present in the above set of symbols?
	/*Because it is a character with no biological significance, and it is only useful to manage the NW algorithm 
	 * corner cases.*/
// QUESTION 2: Why * is present in the above alphabet?
	/*Because it covers both the teorethically expected case of stop codons during translation, both the possible 
	 * uncertain reads that could be produced by analysis. It would be better to have two different characters, but
	 * there might be some limit I'm unaware of that make this impractical.*/

public class ProteinSequence extends AbstractSequence implements IProteinBioSeq {
	// YOUR CODE HERE -----------------------------------------------------------------
	
	//IProteinBioSeq Implementation ---------------------------------------------------
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
	// --------------------------------------------------------------------------------
	
	// AbstractSequence implementation ------------------------------------------------
		public Set<Character> getAlphabet(){	
			Set<Character> set = new LinkedHashSet<Character>();
			set.add('A');
			set.add('R');
			set.add('N');
			set.add('D');
			set.add('C');
			set.add('Q');
			set.add('E');
			set.add('G');
			set.add('H');
			set.add('I');
			set.add('L');
			set.add('K');
			set.add('M');
			set.add('F');
			set.add('P');
			set.add('S');
			set.add('T');
			set.add('W');
			set.add('Y');
			set.add('V');
			set.add('*');
			return Collections.unmodifiableSet(set);
		}

	// --------------------------------------------------------------------------------
	
	// Constructor
		public ProteinSequence(String header, String sequence){
			super(header, sequence);
		}
		
	// --------------------------------------------------------------------------------
	
}
