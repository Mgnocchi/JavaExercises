import java.util.*;
import java.io.*;

public class BioSeqCollection implements IBioSeqCollection {

	// members:
	private List<? extends AbstractSequence> seqCollection;
	private int currentSequence = -1;
	private boolean isDnaCollection;
//	private boolean moreThanZeroSeq = false;		//removed because always check directly by other methods
//	private boolean moreThanOneSeq = false;
//	private boolean moreThanTwoSeq = false;
	private SeqAlign sal = new SeqAlign();

// I ) IBioSeqCollection interface implementation -----------------------------------------

	// --------- set active sequence in the collection -------------------------------- 
	public void setActiveSequenceIndex(int curIndex){
		if(this.seqCollection.isEmpty()){
			System.out.println("Seq collection is empty ... return.");
		}
		if((curIndex > seqCollection.size()-1) || (curIndex < 0)){
			System.out.println("Index out of range.");
			return;
		}else{
			this.currentSequence = curIndex;
			return;
		}
	}

	// --------- get the index of the active sequence in the collection --------------
	public int getActiveSequenceIndex(){
		return this.currentSequence;
	}
		
	//void writeCollection();
		
	// --------- compute and return a list of alignments starting from the sequences collection
	public List<Alignment> alignCollection(){
		return sal.collectionAlignSNW(this.seqCollection, this.isDnaCollection);
	}

	// -------- get the number of elements in the collection ------------------------
	public int collectionLength(){
		return seqCollection.size();
	}
		
// II) SeqCollection methods ----------------------------------------------------

	// -------- align a pair of sequences given their indexes. Returns an Alignment object.
	public Alignment alignPair(int seq1index, int seq2index){
		// check if we have at least 2 sequences in collection
		if(this.seqCollection.size()<2){
			System.out.println("Available sequence are less than two. Return null.");
			return null;
		}
		// index in range check
		int lastindex = this.seqCollection.size() -1; 
		if((seq1index<0)||(seq1index>lastindex)){
			System.out.println("seq1index out of bound. Return null.");
			return null;
		}
		if((seq2index<0)||(seq2index>lastindex)){
			System.out.println("seq2index out of bound. Return null.");
			return null;
		}
		// align the pair of sequences.
		// This methods align a pair of generic biological sequences. Pay attention to check 
		// the actual value of the private member isDnaCollection in order to select the ap-
		// propriate alignment algorithm (SW/NW).
		//
		// YOUR CODE HERE --------------------------------------------------------------------------------------- 
		Alignment tmpaln = null;
		if(this.isDnaCollection) {
			tmpaln = sal.singlePairAlignSW(this.seqCollection.get(seq1index), this.seqCollection.get(seq2index));
		}else {
			tmpaln = sal.singlePairAlignNW(this.seqCollection.get(seq1index), this.seqCollection.get(seq2index));
		}
		return tmpaln;
		// ------------------------------------------------------------------------------------------------------
	}

	// -------- read a collection of sequences from a multi FASTA file --------------
	private List<? extends AbstractSequence> readCollection(String filename, boolean isDna) throws FileNotFoundException{
		// YOUR CODE HERE ---------------------------------------------------------------------------------------
		if(isDna){
			List<? extends AbstractSequence> readSeqList = FastaFileReaderDNA.readFasta(filename);
			return readSeqList; //è almeno vuota
		}else{
			List<? extends AbstractSequence> readSeqList = FastaFileReaderPROTEIN.readFasta(filename);
			return readSeqList; //è almeno vuota
		}
		// ------------------------------------------------------------------------------------------------------
	}

	// ------- print information about the active sequence ------------------------
	public void activeSeqInfo(){
		if(this.currentSequence >= 0){
			this.seqCollection.get(this.currentSequence).printFasta();
			System.out.println("Sequence length: "+ this.seqCollection.get(this.currentSequence).getSequence().length()+" nt.");
			System.out.println("Ratio valid: " + String.format("%.3f", this.seqCollection.get(this.currentSequence).getRatioValid()));
		}else{
			System.out.println("This collection is empty. No sequences here ...");
		}
	}

// III) Constructors
	public BioSeqCollection(String multifastafile) throws FileNotFoundException {
		this.seqCollection = readCollection(multifastafile, true);
		this.isDnaCollection = true;
		System.out.println("Collection of: " + this.seqCollection.size() + " sequences loaded from "+multifastafile);
//		if(this.seqCollection.size()>1){this.moreThanOneSeq = true;}
//		if(this.seqCollection.size()>2){this.moreThanTwoSeq = true;}
		if(!this.seqCollection.isEmpty()){this.currentSequence=0;}
	}
	// this second constructor takes an additional argument (boolean isDnaFile). It is the one that
	// should be used while reading a collection of protein biosequences 	
	public BioSeqCollection(String multifastafile, boolean isDnaFile) throws FileNotFoundException {
		this.seqCollection = readCollection(multifastafile, isDnaFile);
		this.isDnaCollection = isDnaFile;
		System.out.println("Collection of: " + this.seqCollection.size() + " sequences loaded from "+multifastafile);
//		if(this.seqCollection.size()>1){this.moreThanOneSeq = true;}
//		if(this.seqCollection.size()>2){this.moreThanTwoSeq = true;}
		if(!this.seqCollection.isEmpty()){this.currentSequence=0;}
	}
}
