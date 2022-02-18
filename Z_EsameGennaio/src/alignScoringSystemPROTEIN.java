import java.io.*;
import java.util.*;

public class alignScoringSystemPROTEIN{
	public Double gap = -8.0d/1.0;
	public Map<String,Double> compare = new HashMap<String,Double>();

	// This constructors works exactly as the one that you can see in the alignScoringSystemDNA class
	// but here the scores are read from a file (BLOSUM50.txt). Pay attention to the alignScoringSystemDNA
	// constructor: this one must provide the same scoring features by mean of the compare HashMap but it
	// will compare pair of amino acid (AA) symbols. Also the special symbols:
	// - "0"	(alignments algorithms)
	// - "*"	(undefined AA or STOP codon) 
	// must be considered.
	// Be sure to manage also the pair "00" in compare (in order to be able to handle upper-left cell in the score matrix of the NW alignment method.
	// It is *strongly* advised to read carefully the alignScoringSystemDNA.java and SW.java files before to 
	// start to implement this class. 
	//
	alignScoringSystemPROTEIN(String matrixfilename) throws FileNotFoundException{
		// YOUR CODE HERE -----------------------------------------------------------------------
		// the comparison with 0 will be managed in the algorithm, thus is not included here
		
		boolean first = true;
		String[] proteins = null;
		String[] scores = null; //index 0 will contain the current protein to match
		try (Scanner sc = new Scanner(new File(matrixfilename))) {
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.charAt(0) == '#') {
					//skip line to ignore comments
				}else {
					if(first) {
						proteins = line.split("\\s+"); //regex to cut a variable amount of spaces
						first = false;
					}else {
						scores = line.split("\\s+");
						for (int i=1; i<scores.length; i++) { 
							//i=0 contains a protein character, while proteins is offset by one because of this
							this.compare.put(scores[0] + proteins[i-1], Double.valueOf(scores[i]) );
						}
					}
				}
					
			}
		}
		//---------------------------------------------------------------------------------------
	}

	public void setGapScore(double g){
		this.gap = g;
	}

}
