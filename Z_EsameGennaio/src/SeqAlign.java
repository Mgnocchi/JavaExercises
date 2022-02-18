import java.io.*;
import java.util.*;

public class SeqAlign{
	// members:
	private Combination comb = new Combination();
	private alignScoringSystemDNA DnaScore = new alignScoringSystemDNA();
	private alignScoringSystemPROTEIN ProteinScore = null;
	private SW SWalign = new SW();
	private NW NWalign = new NW();

	// Constructor: handle exceptions here via try catch blocks in case of need
	SeqAlign(){
		try{
			this.ProteinScore = new alignScoringSystemPROTEIN("BLOSUM50.txt");
		}catch(FileNotFoundException e){
			System.out.println("File BLOSUM50.txt not found");
		}
	}

	// methods:
	public Alignment singlePairAlignSW(AbstractSequence seq1, AbstractSequence seq2){
		Alignment tmpAln = this.SWalign.SWAlignDna(seq1, seq2, DnaScore);
		return tmpAln;
	}

	// This method aligns a pair of biosequences using the NW alignment algorithm instead of the SW algorithm
	// this alignment is a global alignment (while SW is a local alignment algorithm)
	public Alignment singlePairAlignNW(AbstractSequence seq1, AbstractSequence seq2){
		// YOUR CODE HERE -------------------------------------------------------------------------------
		Alignment tmpAln = this.NWalign.NWAlignProtein(seq1, seq2, ProteinScore);
		return tmpAln;
		// ----------------------------------------------------------------------------------------------
	}

	public List<Alignment> collectionAlignSNW(List<? extends AbstractSequence> collseq, boolean isDnaSeqList){
		// empty list of Alignment objects
		List<Alignment> listaln = new ArrayList<Alignment>();
		// indexes combinations (without repetitions) construction
		Integer[] indexlist = new Integer[collseq.size()];
		int numsequences = collseq.size();
		for(int i=0; i<numsequences; i++){
			indexlist[i]=i;
		}
		// Compute combinations
		comb.combinations(indexlist, 2, 0, new Integer[2]);
		// now the comb object contains a List<Integer[]> where each element is a pair of indexes of sequences
		// we can use each pair to extract two AbstractSequences and perform a Smith-Waterman alignment or a
		// Needleman-Wunsch exactly as we did in the singlePairAlignSW method. Each resulting Alignment object 
		// must be added to listaln but now you must decide the type of alignment according to the value of isDnaSeqList...
		//
		// YOUR CODE HERE ---------------------------------------------------------------------------------------
		List<Integer[]> indexCombinations = comb.getCombos();
		if (isDnaSeqList) { //we could put this check later to cut duplicate code but this is more efficent
			for ( Integer[] idxCouple : indexCombinations) {
				listaln.add( singlePairAlignSW( collseq.get(idxCouple[0]),collseq.get(idxCouple[1])) );
			}
		} else {
			for ( Integer[] idxCouple : indexCombinations) {
				listaln.add( singlePairAlignNW( collseq.get(idxCouple[0]),collseq.get(idxCouple[1])) );
			}
		}
		return listaln;
		// ------------------------------------------------------------------------------------------------------
	}
}
