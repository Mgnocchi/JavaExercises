import java.text.DecimalFormat;

public class SW_alignment {

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
			
			// An offset is being introduced to have better correspondances with the score matrix
			seq1 = "0"+seq1; 
			seq2 = "0"+seq2; 
			
			// Scoring scheme definition:
			double match = 1.0d;
			double mismatch = -1d/3;
			double gap = -4.0d/3.0;	// fixed gap penalty
			
			// score and pointers matrices
			//if seq1 has originally 2 characters, it has now 3,
			double[][] scorematrix = new double[seq2.length()-1][seq1.length()-1];
			String[][] pointermatrix = new String[seq2.length()-1][seq1.length()-1];
			
			// matrices initialization
			// a) TOP-LEFT cells initializazion 
			scorematrix[0][0]=0.0;
			pointermatrix[0][0]="*";
			// b) FIRST ROW and COLUMN initialization
			// - first row
			for(int l = 1; l<seq1.length()-1;l++) {
				scorematrix[0][l]=0.0;
				pointermatrix[0][l]="*";
			}
			// - first column
			for(int l = 1; l<seq2.length()-1;l++) {
				scorematrix[l][0]=0.0;
				pointermatrix[l][0]="*";
			}
							
			// --------- FILL the dynamic programming matrix ---------------------------------- 
			int max_i=0;	// row of max_score
			int max_j=0;	// column of max_score
			double max_score=0.0d;	// initial value of max_score (lower bound is 0.0)
			
			
			for(int i=1;i<seq2.length()-1;i++) {	// cycle rows (seq2: CAGCCUCGCUUAG)
				
				for(int j=1;j<seq1.length()-1;j++) {	// cycle columns (seq1: AAUGCCAUUGACGG)

					// current cell variables declaration
					double diagonal_score=0.0;
					double left_score=0.0;
					double up_score=0.0;
					
					// current symbol in sequence 1
					char letter1 = seq1.charAt(j-1);
					// current symbol in sequence 2
					char letter2 = seq2.charAt(i-1);
									
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
					if(Character.toString(letter1).equals("0")||Character.toString(letter2).equals("0")) {
						scorematrix[i][j]=0;
						pointermatrix[i][j]="*";//la prima riga e colonna sono 0, metto una barriera
						continue;
					}
					// case 1) all scores are <= 0 
					if(diagonal_score <= 0 & up_score <= 0 & left_score <= 0) {
						scorematrix[i][j]=0;
						pointermatrix[i][j]="*";//se ho punteggi troppo scarsi metto una barriera e setto 0 come default
						continue;
					}else {
					// case 2) select the largest score
						if(diagonal_score>=up_score) {
							if(diagonal_score>=left_score) { //diagonal_score è il maggiore
								scorematrix[i][j]= diagonal_score;
								pointermatrix[i][j]= "d";
							}else { //left_score è massimo
								scorematrix[i][j]= left_score;
								pointermatrix[i][j]= "l";
							}					
						}else{	
							if(up_score>=left_score) {
								scorematrix[i][j]= up_score;
								pointermatrix[i][j]= "u";
							}else{
								scorematrix[i][j]= left_score;
								pointermatrix[i][j]= "l";
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
					align1 = align1 + seq1.substring(j-1,j);
					align2 = align2 + seq2.substring(i-1,i);
					i--;
					j--;
				}else if(pointermatrix[i][j].equals("l")) {
					align1 = align1 + seq1.substring(j-1,j);
					align2 = align2 + "-";
					j--;
				}else if(pointermatrix[i][j].equals("u")) {
					align1 = align1 + "-";
					align2 = align2 + seq2.substring(i-1,i);
					i--;
				}
			} // traceback end
			
			// V ) ------------- OUTPUT PRINT ------------------------------
			// V.a: reverse the alignment strings -------------------------------
			StringBuffer s = new StringBuffer(align1); 
			align1 = s.reverse().toString();
			s = new StringBuffer(align2);
			align2 = s.reverse().toString();
			s = new StringBuffer(descstring);
			descstring = s.reverse().toString();
			// V.b: print the alignment -----------------------------------------
			System.out.println("Sequence 1: " + seq1.substring(1));
			System.out.println("Sequence 2: " + seq2.substring(1));
			System.out.println("Max score: " + String.format("%.3f", max_score));
			System.out.println(align1);
			System.out.println(descstring);
			System.out.println(align2);	
			
			DecimalFormat numberFormat = new DecimalFormat("#.00");
			
			// we print the alignment matrix 
			System.out.print(" |");
			for (i=1; i<seq2.length(); i++) {
				System.out.print(seq2.charAt(i) + "\t|");
			}
			System.out.println(); //fine intestazione
			for (j=1; j<seq1.length()-1; j++) { //le dimensioni della matrice sono queste
				System.out.print(seq1.charAt(j) + "|"); //a inizio riga metto il carattere
				for (i=1; i<seq2.length()-1; i++) {
					System.out.print(numberFormat.format(scorematrix[i][j]) + "\t|");
				}
			System.out.println(); //a fine riga vado a capo
			}
			System.out.println(seq1.charAt(j));
			
			// we print the alignment matrix going willingly in overfloat and making correspondances with the
			// padding of zeroes we created
			// WARNING: the cell (i,j) is calculated in regards to the characters of index i-1 and j-1 in the 
			// two seq after zero is added
			System.out.println("-------------------------------------");
			System.out.println();
			System.out.print(" |");
			for (i=0; i<seq2.length(); i++) {
				System.out.print(seq2.charAt(i) + "\t|");
			}
			System.out.println(); //fine intestazione
			for (j=0; j<seq1.length(); j++) { //le dimensioni della matrice sono queste
				System.out.print(seq1.charAt(j) + "|"); //a inizio riga metto il carattere
				for (i=0; i<seq2.length(); i++) {
					try {
						System.out.print(numberFormat.format(scorematrix[i+1][j+1]) + "\t|");
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.print("x \t|"); //quanto è bad practice fare questa cosa?
					}
				}
			System.out.println(); //a fine riga vado a capo
			}
			//we observe that the raw and column of idex 0 are excluded from this print. A padding could be added
			//to the sequences to correct that and reindicize the sequences virtually starting from -1
		}
	}

