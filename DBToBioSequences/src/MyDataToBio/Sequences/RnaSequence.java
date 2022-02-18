package MyDataToBio.Sequences;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class RnaSequence extends AbstractSequence {
	public Set<Character> getAlphabet(){	
		Set<Character> set = new LinkedHashSet<Character>();
		set.add('A');
		set.add('C');
		set.add('G');
		set.add('U');
		return Collections.unmodifiableSet(set);
	}

	public RnaSequence(String header, String sequence){
		super(header, sequence);
	}
	
	public DnaSequence toDna() {
		String[] basi = this.sequence.split("");
		String seqParziale = "";
		for(String base:basi) {
			if(base.equals("U")){
				seqParziale = seqParziale + "T";
			}else{
				seqParziale = seqParziale + base;
			}
		}
		return new DnaSequence(this.header,seqParziale);
	}
	
}
