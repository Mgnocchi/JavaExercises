import java.io.*;
import java.util.*;

public class myTest {
	// test class for BioSeqCollection objects

	public static void main(String[]args) throws FileNotFoundException{

		// I) construction of a BioSeqCollection object ---------------------------------------------------------
		// I.a) Load a collection of biological sequences (DNA) from a multifasta file
		BioSeqCollection bsc = new BioSeqCollection("sequences2.fasta");
		// I.b) Load a collection of biological sequences (proteins) from a multifasta file (sequences3.fasta) 
		// YOUR CODE HERE ---------------------------------------------------------------------------------------
		BioSeqCollection bscProt = new BioSeqCollection("sequences3.fasta",false);
		// ------------------------------------------------------------------------------------------------------

		// I.c) Dna sequences collection test
		// print information about sequence 0 :
		bsc.setActiveSequenceIndex(0);
		System.out.println("Active sequence index: "+ bsc.getActiveSequenceIndex());
		System.out.println("\nInformation about active sequence:");
		bsc.activeSeqInfo();
		// print information about sequence 1 :
		bsc.setActiveSequenceIndex(1);
		System.out.println("Active sequence index: "+ bsc.getActiveSequenceIndex());
		System.out.println("\nInformation about active sequence:");
		bsc.activeSeqInfo();

		// I.d) Protein sequences collection test
		// YOUR CODE HERE ---------------------------------------------------------------------------------------
		bscProt.setActiveSequenceIndex(0);
		System.out.println("Active sequence index: "+ bscProt.getActiveSequenceIndex());
		System.out.println("\nInformation about active sequence:");
		bscProt.activeSeqInfo();
		// print information about sequence 1 :
		bscProt.setActiveSequenceIndex(1);
		System.out.println("Active sequence index: "+ bscProt.getActiveSequenceIndex());
		System.out.println("\nInformation about active sequence:");
		bscProt.activeSeqInfo();
		// ------------------------------------------------------------------------------------------------------

		
		// II) Alignment of a pair of sequences -----------------------------------------------------------------
		// II.a) align a PAIR of sequences (test sequences from LAB a, sequences 0 and 1):
		Alignment aln = bsc.alignPair(0,1);
		aln.printAln();

		// II.b) align a PAIR of sequences (protein sequences test1 and test2 from NW_info.pdf, sequences 0 and 1):
		// YOUR CODE HERE ---------------------------------------------------------------------------------------
		Alignment alnProt = bscProt.alignPair(0, 1);
		alnProt.printAln();
		// ------------------------------------------------------------------------------------------------------


		// III) align FULL COLLECTION test: ---------------------------------------------------------------------
		// III.a) align a collection of DNA sequences
		List<Alignment> lsaln = bsc.alignCollection();
		int totaln = lsaln.size();
		System.out.println("Computed " + totaln + " alignments.");
		int lastindex = totaln-1;
		int secondindex = 1;
		if(secondindex<=lastindex){
			Alignment a = lsaln.get(secondindex);
			System.out.println("\nAlignment " + secondindex + " in List of Alignments:");
			a.printAln();
		}
		
		// III.b) align a collection of protein sequences
		// YOUR CODE HERE ---------------------------------------------------------------------------------------
		List<Alignment> lsalnProt = bscProt.alignCollection();
		totaln = lsalnProt.size();
		System.out.println("Computed " + totaln + " alignments.");
		lastindex = totaln-1;
		secondindex = 14;
		if(secondindex<=lastindex){
			Alignment a = lsalnProt.get(secondindex);
			System.out.println("\nAlignment " + secondindex + " in List of Alignments:");
			a.printAln();
		}
		
		// ------------------------------------------------------------------------------------------------------
	}
}
