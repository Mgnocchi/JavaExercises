import java.util.*;
import java.io.*;

public class BioSeqCollection implements IBioSeqCollection {

// members:
	//private List<AbstractSequence> seqCollection;
	private List<? extends AbstractSequence> seqCollection; //inizializzato nel costruttore: è sempre almeno vuoto
	private int currentSequence = -1;
	// i prossimi due campi non servono davvero, si potrebbero usare dei controlli nei metodi della classe,
	// la scelta di tenerli e inizializzarli nel costruttore, è dibattibile, ma dovrebbe essere piuttosto neutrale 
	// in termini di efficenza del codice
	private boolean moreThanZeroSeq = false;	
	private boolean moreThanOneSeq = false;
	//private boolean moreThanTwoSeq = false;  //questo non viene mai usato
	private SeqAlign sal = new SeqAlign();

	
	
// I ) IBioSeqCollection interface implementation -----------------------------------------

//	// --------- set active sequence in the collection -------------------------------- 
	public void setActiveSequenceIndex(int curIndex){ 
		if(this.seqCollection.isEmpty()){ //ora seqCollection non è mai null su un oggetto istanziato
			System.out.println("Seq collection is empty ... return."); 
			return;
		}
		if(!validIndex(curIndex)){
			System.out.println("Index out of range.");
			return;
		}else{
			this.currentSequence = curIndex;
			return;
		}
	}

//	// --------- get the index of the active sequence in the collection --------------
	public int getActiveSequenceIndex(){
		return this.currentSequence;
	}
	
	// --------- prints in a text file all the allignments in a List<Alignment>
	public static void writeCollection(List<Alignment> listAlign, String fileName) throws IOException {
		try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName,false)))){
			boolean first = true;
			for(Alignment allineamento:listAlign) {
				if(!first) {
					writer.println("------------------------------------------------------------\n");
					}
				writeAlign(allineamento, writer);
				first = false;
			}
			writer.close();
		}
	}
	
	
	// --------- prints an Alignment using the given BufferedWriter
	public static void writeAlign(Alignment align, PrintWriter scriba){
		//stampa le due sequenze da allineare in formato fasta
		//A giudicare dall'output sul pdf non lo vuole
//		align.Aseq1.printFasta(scriba);
//		align.Aseq2.printFasta(scriba);
//		
		//stampa i due allineamenti inframezzati dalla linea di decoro, se su multiple linee voglio una linea vuota tra esse
		align.printAln(scriba);
		
		//stampa isDNA
		//A giudicare dall'output sul pdf non lo vuole
//		scriba.println("is DNA:" + String.valueOf(align.isDNA) + "\n");
		
		//stampa la score matrix
		//A giudicare dall'output sul pdf non lo vuole
//		align.printScoreMatrix(scriba);
		
		//stampa la pointer matrix
		//A giudicare dall'output sul pdf non lo vuole
//		align.printPointerMatrix(scriba);
		
	}
	
	// this code would be too different to have a wrapper method
	public static void writeCollection(List<Alignment> listAlign){
		boolean first = true;
		for(Alignment allineamento:listAlign) {
			if(!first) {
				System.out.println("------------------------------------------------------------\n");
				}
			allineamento.printAln();
			first = false;
		}
	}
	
	//Implementation from IBioSeqCollection
	public void writeCollection(){
		writeCollection(this.alignCollection());
	}
	
	// --------- compute and return a list of alignments starting from the sequences collection	
	// returns null if less than two sequences
	public List<Alignment> alignCollection(){
		if (moreThanOneSeq) {
			return sal.collectionAlignSW(this.seqCollection);
		}
		else {
			// ma stampare questa cosa sul system.out in modo nativo nel metodo non è bad practice?
			System.out.println("Not enough sequences to allign them (less than two)");
			return null;
		}
	}

//	// -------- get the number of elements in the collection ------------------------
	public int collectionLength(){
		return seqCollection.size();
	}
		
// II) SeqCollection methods ----------------------------------------------------
	
//	// private routine to check index, true if valid, false if out of bounds
	// worth it?
	private boolean validIndex(int i) {
		//può essere 0	//minore stretto della lunghezza è in range, uguale no
		return ((i>=0)&&(i<this.seqCollection.size())); 
	}
	
	// -------- align a pair of sequences given their indexes. Returns an Alignment object.
	public Alignment alignPair(int seq1index, int seq2index){
		// check if we have at least 2 sequences in collection
		if(!moreThanOneSeq){//this.seqCollection.size()<2){
			System.out.println("Available sequence are less than two. Return null.");
			return null;
		}
		// index in range check
		if(!validIndex(seq1index)){
			System.out.println("seq1index out of bound. Return null.");
			return null;
		}
		if(!validIndex(seq2index)){
			System.out.println("seq2index out of bound. Return null.");
			return null;
		}
		Alignment tmpaln = sal.singlePairAlignSW(this.seqCollection.get(seq1index), this.seqCollection.get(seq2index));
		return tmpaln;
	}

	// -------- read a collection of sequences from a multi FASTA file --------------
	private List<? extends AbstractSequence> readCollection(String filename, boolean isDna) throws FileNotFoundException{
		if(isDna){
			List<? extends AbstractSequence> readSeqList = FastaFileReaderDNA.readFasta(filename);
				return readSeqList; //è almeno vuota
			}else{
				return null;		
		}
	}

	// ------- print information about the active sequence ------------------------
	public void activeSeqInfo(){
		int activeIndex = getActiveSequenceIndex();
		if (activeIndex>=0 && activeIndex<seqCollection.size()) { //not using validIndex: worth it?
			AbstractSequence activeSeq = seqCollection.get(activeIndex);
			//print fasta 
			activeSeq.printFasta();
			//sequence lenght: numero nt.
			System.out.println("Sequence lenght: " + activeSeq.sequence.length() + " nt.");
			//ratio valid: rateo 
			System.out.println("Ratio valid: " + activeSeq.getRatioValid());
		}
		
		// Read carefully the  instruction  available  in  README.pdf  before  to implement
		// this method. You might also want to see the sample output provided in README.pdf

	}

// III) Constructor 
	public BioSeqCollection(String multifastafile) throws FileNotFoundException {
		this.seqCollection = readCollection(multifastafile, true);
		System.out.println("Collection of: " + this.seqCollection.size() + " sequences.");
		if(!this.seqCollection.isEmpty()){ 
			this.moreThanZeroSeq = true;
			}
		if(this.seqCollection.size()>1){ 
			this.moreThanOneSeq = true;
			}
//		if(this.seqCollection.size()>2){	//this field was not used so it has been removed
//			this.moreThanTwoSeq = true;
//			}
		if(moreThanZeroSeq){
			this.currentSequence=0;
			}
	}

//TODO personal extentions
	
	// -------- align Globally a pair of sequences given their indexes. Returns an Alignment object.
		public Alignment alignPairGlobal(int seq1index, int seq2index){
			// check if we have at least 2 sequences in collection
			if(!moreThanOneSeq){//this.seqCollection.size()<2){
				System.out.println("Available sequence are less than two. Return null.");
				return null;
			}
			// index in range check
			if(!validIndex(seq1index)){
				System.out.println("seq1index out of bound. Return null.");
				return null;
			}
			if(!validIndex(seq2index)){
				System.out.println("seq2index out of bound. Return null.");
				return null;
			}
			Alignment tmpaln = sal.singlePairAlignNW(this.seqCollection.get(seq1index), this.seqCollection.get(seq2index));
			return tmpaln;
		}
	//pretty useless test routine
	public AbstractSequence getSingleSeq(int index) {
		if (this.validIndex(index)){
			return this.seqCollection.get(index);
		}else {
			return null;
		}
	}
	
}
