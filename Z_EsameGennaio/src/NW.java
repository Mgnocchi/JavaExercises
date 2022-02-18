public class NW {
	public Alignment NWAlignProtein(AbstractSequence Aseq1, AbstractSequence Aseq2, alignScoringSystemPROTEIN asp){

		String seq1 = "0"+Aseq1.getSequence();
		String seq2 = "0"+Aseq2.getSequence();

		// score and pointers matrices
		double[][] scorematrix = new double[seq2.length()][seq1.length()];
		String[][] pointermatrix = new String[seq2.length()][seq1.length()];
		
	// YOUR CODE HERE ------------------------------------------------------------------------------------------------------
		// matrices initialization
		// we create the padding line and column outside the normal filling loop because this approach uses
		// less operations very quickly not needing checks to understand if we should put a 0 (padding)
		// a) TOP-LEFT cells initializazion 
		scorematrix[0][0]=0.0;
		pointermatrix[0][0]="*";
		// b) FIRST ROW and COLUMN initialization
		//Since the alignment must be global we compute starting offsets as gaps
		// - first row
		for(int l = 1; l<seq1.length();l++) { 													
			scorematrix[0][l]=asp.gap*l;
			pointermatrix[0][l]="*";
			}
		// - first column
		for(int l = 1; l<seq2.length();l++) { 													
			scorematrix[l][0]=asp.gap*l;
			pointermatrix[l][0]="*";
		}
		// ###################################################################################
		
		// le inizializiamo una volta sola, fuori dal ciclo, non serve assegnarle tutte le volte
		double diagonal_score=0.0;
		double left_score=0.0;
		double up_score=0.0;
				
		for(int i=1;i<seq2.length();i++) {	// cycle rows (seq2)			
			for(int j=1;j<seq1.length();j++) {	// cycle columns (seq1)
				// current symbol in sequence 1
				char letter1 = seq1.charAt(j); 	
				// current symbol in sequence 2
				char letter2 = seq2.charAt(i);
				
				// I ) ------------- compute scores ------------------
				// A) compute scores for match/mismatch using a Protein scoring system object (asp)
				diagonal_score = scorematrix[i-1][j-1] + asp.compare.get(Character.toString(letter1)+Character.toString(letter2));
				// B) compute gap scores (unweighted linear mode)
				up_score = scorematrix[i-1][j] + asp.gap;
				left_score = scorematrix[i][j-1] + asp.gap;
				// --------------------------------------------------
				
				// II) ------------- best score choice --------------
				// We do not have a limitation over negative values
				// select the largest score
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
				
			}	 // end columns cycle (j)
		}	// end rows cycle (i)

		// IV ) ---- build the alignment: DP TRACEBACK  --------------------------
		String align1="";
		String align2="";
		String descstring="";
		// if the matrices are seq2.length()xseq1.length(), the indexes go from 0 to those quantities -1
		int j= seq1.length() - 1;
		int i= seq2.length() - 1;
		// walk the pointers matrix until we find a "*" pointer. When this happens exit the cycle
		
		while(true) { // infinite cycle (use with caution)
			// escape condition:
			if(j<=0 && i<=0) {break;}
			// Rispetto al SW questo allineamento non può terminare sul bordo, ma deve proseguire stampando anche i
			// caratteri che producono un offset tra le sequenze. Questo è gestito dai primi due controlli.
			// alignment progressive construction
			if(j<=0){
				//se sono qui i>0, e devo lasciare il gap appropriato lungo la seq1
				align1 = "-" + align1;
				align2 = seq2.substring(i,i+1) + align2;
				descstring = " " + descstring;
				i--;
			}else if(i<=0){
				//se sono qui j>0, e devo lasciare il gap appropriato lungo la seq1
				align1 = seq1.substring(j,j+1) + align1;
				align2 = "-" + align2;
				descstring = " " + descstring;
				j--;
			}else if(pointermatrix[i][j].equals("d")) {
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
			Alignment aln = new Alignment(Aseq1, Aseq2, align1, align2, descstring, false, scorematrix, pointermatrix, scorematrix[seq2.length()-1][seq1.length()-1]);
	return aln;
	//----------------------------------------------------------------------------------------------------------------------	
	}
}

