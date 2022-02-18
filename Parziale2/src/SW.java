public class SW {

	public Alignment SWAlignDna(AbstractSequence Aseq1, AbstractSequence Aseq2, alignScoringSystemDNA asd){

		String seq1 = "0"+Aseq1.getSequence();
		String seq2 = "0"+Aseq2.getSequence();

		// score and pointers matrices
		double[][] scorematrix = new double[seq2.length()][seq1.length()]; 
		String[][] pointermatrix = new String[seq2.length()][seq1.length()];
		// matrices initialization
		// we create the padding line and column outside the normal filling loop because this approach uses
		// less operations very quickly not needing checks to understand if we should put a 0 (padding)
		// a) TOP-LEFT cells initializazion 
		scorematrix[0][0]=0.0;
		pointermatrix[0][0]="*";
		// b) FIRST ROW and COLUMN initialization
		// - first row
		for(int l = 1; l<seq1.length();l++) { 													
			scorematrix[0][l]=0.0;
			pointermatrix[0][l]="*";
			}
		// - first column
		for(int l = 1; l<seq2.length();l++) { 													
			scorematrix[l][0]=0.0;
			pointermatrix[l][0]="*";
		}
// ###################################################################################
		int max_i=0;	// row of max_score
		int max_j=0;	// column of max_score
		double max_score=0.0d;	// initial value of max_score (lower bound is 0.0)

		for(int i=1;i<seq2.length();i++) {	// cycle rows (seq2)			
			for(int j=1;j<seq1.length();j++) {	// cycle columns (seq1)
			// current cell variables declaration
				double diagonal_score=0.0;
				double left_score=0.0;
				double up_score=0.0;
				// current symbol in sequence 1
				char letter1 = seq1.charAt(j); 	
				// current symbol in sequence 2
				char letter2 = seq2.charAt(i);
				// I ) ------------- compute scores ------------------
				// A) compute scores for match/mismatch using a DNA scoring system object (asd)
				diagonal_score = scorematrix[i-1][j-1] + asd.compare.get(Character.toString(letter1)+Character.toString(letter2));
				// B) compute gap scores (unweighted linear mode)
				up_score = scorematrix[i-1][j] + asd.gap;
				left_score = scorematrix[i][j-1] + asd.gap;
				// --------------------------------------------------
				// II) ------------- best score choice --------------
				// case 1) all scores are <= 0
				if(diagonal_score <= 0 & up_score <= 0 & left_score <= 0) {
					scorematrix[i][j]=0;
					pointermatrix[i][j]="*";
					continue;
				}else {
				// case 2) select the largest score
					if(diagonal_score>=up_score) {
						if(diagonal_score>=left_score) {
							scorematrix[i][j]=diagonal_score;
							pointermatrix[i][j]="d";
						}else {
							scorematrix[i][j]=left_score;
							pointermatrix[i][j]="l";
						}
					}else{
						if(up_score>=left_score) {
							scorematrix[i][j]=up_score;
							pointermatrix[i][j]="u";
						}else{
							scorematrix[i][j]=left_score;
							pointermatrix[i][j]="l";
						}
					}
				}
				// III ) ---- store max score coordinates (w.r.t. the matrices)  ----------------
				if(scorematrix[i][j]>max_score) {
					max_i = i;
					max_j = j;
					max_score = scorematrix[i][j];
				}
			}	 // end columns cycle (j)
		}	// end rows cycle (i)

		// IV ) ---- build the alignment: DP TRACEBACK  --------------------------
		String align1="";
		String align2="";
		String descstring="";
		int j=max_j;
		int i=max_i;
		// walk the pointers matrix until we find a "*" pointer. When this happens exit the cycle
		while(true) { // infinite cycle (use with caution)
			// escape condition:
			if(pointermatrix[i][j].equals("*")) {break;}
			// alignment progressive construction
			if(pointermatrix[i][j].equals("d")) {
				align1 = seq1.substring(j,j+1) + align1;
				align2 = seq2.substring(i,i+1) + align2;
				// the added character is always the first of the string; we need to check if we have a match here
				// or not. It's not implied by this being the best direction.
				if (align1.charAt(0)==align2.charAt(0)) {
					descstring = "|" + descstring;
				}
				else {
					descstring = "." + descstring;
				}
				i--;
				j--;
			}else if(pointermatrix[i][j].equals("l")) {
				align1 = seq1.substring(j,j+1) + align1;
				align2 = "-" + align2;
				descstring = " " + descstring;
				j--;
			}else if(pointermatrix[i][j].equals("u")) {
				align1 = "-" + align1;
				align2 = seq2.substring(i,i+1) + align2;
				descstring = " " + descstring;
				i--;
			}
		} // traceback end
		// V ) ------------- OUTPUT OBJECT CONSTRUCTION ----------------------
		// V.b: alignment object construction -------------------------------
		//String tmps = "";
		//tmps = tmps + "SEQ1:|" + Aseq1.getHeader() + "| - SEQ2:| " + Aseq2.getHeader() + "|\n";  // add sequence headers
		//tmps = tmps + "Max score:" + "\n" + String.format("%.3f", max_score) + "\n\n"; // add Max score
		// add alignment
		//tmps = tmps + align1 + "\n";
		//tmps = tmps + descstring + "\n";
		//tmps = tmps + align2 + "\n";

		Alignment aln = new Alignment(Aseq1, Aseq2, align1, align2, descstring, true, scorematrix, pointermatrix, max_score);
		return aln;
// ###################################################################################
	}



}
