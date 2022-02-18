package MyDataToBio.Sequences;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class PepSequence extends AbstractSequence{
	public Set<Character> getAlphabet(){	
		Set<Character> set = new LinkedHashSet<Character>();
		set.add('A');
		set.add('R');
		set.add('N');
		set.add('D');
		set.add('C');
		set.add('E');
		set.add('Q');
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
		return Collections.unmodifiableSet(set);
	}
	
	public PepSequence(String header, String sequence){
		super(header, sequence);
	}
}
