import java.util.*;

public class SeqAlign{
	// members:
	private Combination comb = new Combination();
	private alignScoringSystemDNA DnaScore = new alignScoringSystemDNA();
	private SW SWalign = new SW();

	// methods:
	public Alignment singlePairAlignSW(AbstractSequence seq1, AbstractSequence seq2){
		Alignment tmpAln = this.SWalign.SWAlignDna(seq1, seq2, DnaScore);
		return tmpAln;
	}
	
	public List<Alignment> collectionAlignSW(List<? extends AbstractSequence> collseq){
		// empty list of Alignment objects
		List<Alignment> listaln = new ArrayList<Alignment>();
		// indexes combinations (without repetitions) construction
		Integer[] indexlist = new Integer[collseq.size()];
		int numsequences = collseq.size();
		for(int i=0; i<numsequences; i++){
			indexlist[i]=i;
		}
		// Compute combinations
		comb.combinations(indexlist, 2);
		// now the comb object contains a List<Integer[]> where each element is a pair of indexes of sequences
		// we can use each pair to extract two AbstractSequences and perform a SmithWaterman alignment
		// exactly as we did in the singlePairAlignSW method. Each resulting Alignment object must be added
		// to listaln.
		//
		// Read carefully the content of README.pdf before implementing this part of the collectionAlignSW method
		// YOUR CODE HERE
		List<Integer[]> indexCombinations = comb.getCombos();
		for ( Integer[] idxCouple : indexCombinations) {
			listaln.add( singlePairAlignSW( collseq.get(idxCouple[0]),collseq.get(idxCouple[1])) );
		}
		return listaln;

	}
	
//TODO Personal extentions
	
	public Alignment singlePairAlignNW(AbstractSequence seq1, AbstractSequence seq2){
		NW NWalign = new NW();
		Alignment tmpAln = NWalign.NWAlignDna(seq1, seq2, DnaScore);
		return tmpAln;
	}
}
