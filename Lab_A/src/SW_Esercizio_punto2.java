

public class SW_Esercizio_punto2 {		

	//fa l'allineamento delle due sequenze passate con gli score passati e li salva in un vettore di output
	//l'output ha forma 
	//out[0] = double[][]  contiene la matrice dei valori. la dimesione è nota, ed è quella delle sequenze in ingresso +1
	//out[1] = String[][]  contiene la matrice dei puntatori, dimensioni come sopra
	//out[2] = int[] 	   contiene la i e la j sulle matrici qui sopra della cella di valore massimo
	public static Object[] allignSW(String seq1, String seq2, double match, double mismatch, double gap) {
		// An offset in the sequences is being introduced to have better correspondances with the score matrix
		seq1 = "0"+seq1; 
		seq2 = "0"+seq2; 
		
		// score and pointers matrices
		//if seq1 has originally 2 characters, it has now 3, we want the matrix to be that long and tall 
		//so that it can store the padding line and column of zeroes
		double[][] scorematrix = new double[seq2.length()][seq1.length()];
		String [][] pointermatrix = new String[seq2.length()][seq1.length()];
		//now the index correspondances between the matrix and seq has no offset
		
		// matrices initialization
		// we create the padding line and column outside the normal filling loop because this approach uses
		// less operations very quickly not needing checks to understand if we should put a 0 (padding)
		// a) TOP-LEFT cells initializazion (this could be done in one of the two cicles under this but the efficency is virtually the same)
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
						
		// --------- FILL the dynamic programming matrix ---------------------------------- 
		// we initialize the max value and position at zero. This is the actual position of a cell containing zero
		// so it is consistent if called while no value higher than zero is found.
		int max_i=0;	// row of max_score
		int max_j=0;	// column of max_score
		double max_score=0.0d;	// initial value of max_score (lower bound is 0.0)
		
		
		for(int i=1;i<seq2.length();i++) {	// cycle rows (seq2: CAGCCUCGCUUAG)
			
			for(int j=1;j<seq1.length();j++) {	// cycle columns (seq1: AAUGCCAUUGACGG)
					// current cell variables declaration
				double diagonal_score=0.0;
				double left_score=0.0;
				double up_score=0.0;
				
				// There is no offset in the correspondances between the matrix and the strings
				// adding an offset of -1 would create a second padding line and loose the last column and 
				// row of matches that may possibly produce matches of interest
				// current symbol in sequence 1
				char letter1 = seq1.charAt(j);
				// current symbol in sequence 2
				char letter2 = seq2.charAt(i);
								
				// I ) ------------- compute scores ---------------------------------------
				// A) compute scores for match/mismatch
				if(Character.toString(letter1).equals(Character.toString(letter2))) {
					diagonal_score = scorematrix[i-1][j-1]+match;
				}else {
					diagonal_score = scorematrix[i-1][j-1]+mismatch;
				}
				// B) compute gap scores (unweighted linear mode)
				up_score = scorematrix[i-1][j]+gap;
				left_score = scorematrix[i][j-1]+gap;
				// --------------------------------------------------------------------
				
				// II) ------------- best score choice --------------------------------
				// case 0) letter1 equals "0" || letter1 equals "0"  (DP matrix borders) 
				// this part is removed as we already have the padding of zeroes and we never have i or j at 0:
				//if(Character.toString(letter1).equals("0")||Character.toString(letter2).equals("0")) {
				//	scorematrix[i][j]=0;
				//	pointermatrix[i][j]="*";//la prima riga e colonna sono 0, metto una barriera
				//	continue;
				//}
				// case 1) all scores are <= 0 
				// if the scores are too low we set up a block, since it's meaningless to go further in the Traceback phase
				if(diagonal_score <= 0 & up_score <= 0 & left_score <= 0) {
					scorematrix[i][j]=0;
					pointermatrix[i][j]="*";
					continue;
				}else {
				// case 2) select the largest score
				// here we need to save the highest value in the current cell and the aproaching direction used to obtain it	
					if(diagonal_score>=up_score) {
						if(diagonal_score>=left_score) { 	//diagonal_score is highest
							scorematrix[i][j]= diagonal_score;
							pointermatrix[i][j]= "d";
						}else { 							//left_score is highest
							scorematrix[i][j]= left_score;
							pointermatrix[i][j]= "l";
						}					
					}else{	
						if(up_score>=left_score) {
							scorematrix[i][j]= up_score;
							pointermatrix[i][j]= "u";		//up_score is highest
						}else{
							scorematrix[i][j]= left_score;
							pointermatrix[i][j]= "l";		//left_score is highest
						}
					}
				}
				// III ) ---- store max score coordinates (w.r.t. the matrices)  ----------------
				// we want to keep track of the position of the highest value. we keep track of the last max 
				// score for clarity but we could as well check for scorematrix[max_i][max_j] as save an assignment once in a while
				if(scorematrix[i][j]>max_score) {
					max_i = i;
					max_j = j;
					max_score = scorematrix[i][j];
				}
				
			}	 // end columns cycle (j)		
		}	// end rows cycle (i)

	//-------------fine compilazione matrice
	int[] maxPosition = new int[]{max_i, max_j};
	
	return new Object[] {scorematrix, pointermatrix, maxPosition};

	}
	
	
	//we output the strings built in an array of [allignment from first seq, a decorative line , allignment from second seq]
	public static String[] tracebackFromMax(String seq1, String seq2, double[][] scorematrix, String [][] pointermatrix, int[] maxPosition) {
		String align1="";
		String align2="";
		String descstring="";
		int j=maxPosition[1]; //maxPosition contiene (i,j)
		int i=maxPosition[0];
		
		// walk the pointers matrix until we find a "*" pointer. When this happens exit the cycle  
		while(true) { // infinite cycle (use with caution)
			// escape condition:
			if(pointermatrix[i][j].equals("*")) {break;}
			
			// alignment progressive construction
			// we build the string from the end as we are going backwards
			if(pointermatrix[i][j].equals("d")) {
				align1 = seq1.substring(j-1,j) + align1;
				align2 = seq2.substring(i-1,i) + align2;
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
				align1 = seq1.substring(j-1,j) + align1;
				align2 = "-" + align2;
				descstring = " " + descstring;
				j--;
			}else if(pointermatrix[i][j].equals("u")) {
				align1 = "-" + align1;
				align2 = seq2.substring(i-1,i) + align2;
				descstring = " " + descstring;
				i--;
			}
			
		}
		return new String[] {align1,descstring,align2};
	}
	
	public static void main(String[] args) {
			// Esercizio:
			// JAVA: 
			//		 esempio di array di array (matrici)
			//		 esempio di memory streams (stringhe + inversione di stringhe)
			//		 esempio di array di stringhe
			
			// PROGRAMMAZIONE 2:
			//		 programmazione OO
			//		 programmazione dinamica
			
			// BIOLOGIA COMPUTAZIONALE:
			//		 allineamento locale di biosequenze
			//		 allineamento pairwise
			//		 allineamento ottimale dato uno schema di scoring
			//		 
			// Implementazione local pairwise alignment (Smith Waterman)
			// 
			
			// Sequenze da allineare:
			String seq1 = "AAUGCCAUUGACGG";
			String seq2 = "CAGCCUCGCUUAG"; 
			
			// Scoring scheme definition:  		
			double match = 1.0d;
			double mismatch = -1d/3;
			double gap = -4.0d/3.0;	// fixed gap penalty
			
			// III )----- we build the two allignment matrix, and save the values in object we copy the  pointers to 
			double[][] scorematrix = null;
			String [][] pointermatrix = null;
			int [] maxPosition = null;
			
			//probably the simplest way to output multiple values with different types without using a second class
			//(that would be used only here and be a bit overkill
			Object[] alignmentData = allignSW(seq1, seq2, match, mismatch, gap);
			
			if (alignmentData[0] instanceof double[][]) { //we want our compiler happy :)
				scorematrix = (double[][]) alignmentData[0];
			}
			if (alignmentData[1] instanceof String[][]) {
				pointermatrix = (String[][]) alignmentData[1];
			}
			if (alignmentData[2] instanceof int[]) {
				maxPosition = (int[]) alignmentData[2];
			}
			
			// IV ) ----------- we build the traceback from the index of max values on the allignment matrices
			String[] alignments = tracebackFromMax(seq1, seq2, scorematrix, pointermatrix, maxPosition);
			
			// V ) ------------- OUTPUT PRINT ------------------------------
			// V.a: reverse the alignment strings -------------------------------
			//this is no longer needed, as we filled our strings backwards
			/*
			StringBuffer s = new StringBuffer(align1); 
			align1 = s.reverse().toString();
			s = new StringBuffer(align2);
			align2 = s.reverse().toString();
			s = new StringBuffer(descstring);
			descstring = s.reverse().toString();
			*/
			// V.b: print the alignment -----------------------------------------
			System.out.println("Sequence 1: " + seq1); // .substring(1)); we no longer need to omit the first character as it was only added in the method
			System.out.println("Sequence 2: " + seq2); // .substring(1)); we computed the matrices into
			System.out.println("Max score: " + String.format("%.3f", scorematrix[maxPosition[0]][maxPosition[1]]) );
			System.out.println(alignments[0]);
			System.out.println(alignments[1]);
			System.out.println(alignments[2]);	
			
			// we print the alignment matrix without paddings
			// WARNING: the cell (i,j) is calculated in regards to the characters of index i+1 and j+1 in the 
			// two seq because zero is added only in the method that computes the two matrices
			//for a nicer layout see the content of "extra.tar.gz"
			//a decorative space between elements has been kept to avoid complete unreadability
			
			for (int j=0; j<seq1.length(); j++) { //le dimensioni della matrice sono queste
				for (int i=0; i<seq2.length(); i++) {
						System.out.print(String.format("%.2f",scorematrix[i+1][j+1]) + " ");
				}
			System.out.println(); //a fine riga vado a capo
			}
			// we print the pointers matrix without paddings
			for (int j=0; j<seq1.length(); j++) { //le dimensioni della matrice sono queste
				for (int i=0; i<seq2.length(); i++) {
						System.out.print(pointermatrix[i+1][j+1] + " ");
				}
			System.out.println(); //a fine riga vado a capo
			}
			
		}

}
