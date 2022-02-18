import java.io.*;
import java.util.*;

public class myTest {
	// test class for BioSeqCollection objects

	public static void main(String[]args) throws IOException{
		// TEST 0:
		// Read biological sequences collection from multifasta file sequences2.fasta
		BioSeqCollection bsc = new BioSeqCollection("sequences2.fasta");
		// print information about a given sequence (the current active sequence):
		System.out.println("Active sequence index: "+ bsc.getActiveSequenceIndex());
		System.out.println("Information about active sequence:\n");
		bsc.activeSeqInfo();

		// TEST 1:
		// align a pair of sequences test (test sequences obtained from LAB a)
		// and then print some information about the obtained alignment.
		// The pair of sequences used in the alignment are sequences 0 and 1.
		// The aforementioned tasks must be realized using AT MOST 2 LINES OF CODE.
		//

		Alignment firstTest = bsc.alignPair(0, 1);
		firstTest.printAln();

		// TEST 2:
		// align full collection test. Before to write this test unit you must complete
		// some methods. Refer to the README.pdf file for this exam session
		//
		// In this unit test you ar expected to:
		// - perform a all-vs-all alignment for all the sequences in the collection 
		// - save the results into a object named	lsaln
		// - print the statement: "Computed x alignments" where x is the number of the computed alignments
		// - print some information about the LAST alignment in lsaln
		//
		// The aforementioned tasks must be realized using AT MOST 6 LINES OF CODE.
		//

		// - perform a all-vs-all alignment for all the sequences in the collection 
		// - save the results into a object named	lsaln
//		List<Alignment> lsaln = bsc.alignCollection();
//		
//		// - print the statement: "Computed x alignments" where x is the number of the computed alignments
//		System.out.println("Computed " + lsaln.size() + " alignments");
//		// - print some information about the LAST alignment in lsaln
//		lsaln.get(lsaln.size()-1).printAln();
//		
// MORE TESTS		
//		Alignment best = null;
//		for (Alignment current: lsaln) {
//			if (best == null || current.maxScore > best.maxScore) {
//				best = current;
//			}
//		}
//		
//		System.out.println("The best alignment is: ");
//		best.printAln();
//		
//		System.out.println("Printing all allignments to file ...");
//		BioSeqCollection.writeCollection(lsaln, "testPrint.txt");
//		System.out.println("Done");
		
//TODO Test for Global alignment		
		firstTest.printAln();
		Alignment globalTest = bsc.alignPairGlobal(0, 1);
		globalTest.printAln();
		
		System.out.println(bsc.getSingleSeq(0).getSequence().toString());
		System.out.println(bsc.getSingleSeq(1).getSequence().toString());
	}
}
