import java.util.*;

// BioSeqCollection interface definition:
//
// Interface describing a collections of generic biological sequences (two or more IBioSeq)
// The classes implementing this interface must be able to:
// - load a collection of sequences from a local file. The file can contain:
// 	- a collection of FASTA formatted sequences (multi FASTA file)
// 	- a collection of database identifiers 
// - keep track of the CURRENT (active) sequence in the collection
// - print some information about the active sequence (length, composition in terms of single AAs or NTs or generic k-mers)
// - print the active sequence in FASTA format
// - perform a pairwise (all-vs-all) local alignment (Smith-Waterman) between all the sequences in the collection avoiding
//   the alignment of already aligned pairs and print the top scoring N alignments (N is a parameter of the method) 
// - Write the collection of sequences into a multi FASTA file
//
// State (members):
// none
//
// Behavior (methods):
// - constructor (?)
// - setActiveSequenceIndex
// - getActiveSequenceIndex
// - alignCollection
// - writeCollection 
// - collectionLength
//
public interface IBioSeqCollection {

	void setActiveSequenceIndex(int curIndex);
		
	int getActiveSequenceIndex();
	
	//this cannot be static without being implemented in the interface, because it would be invoked as interface.method
	// and not as classImplementingInterface.method
	void writeCollection(); 
		
	List<Alignment> alignCollection();
		
	int collectionLength();
		
}
