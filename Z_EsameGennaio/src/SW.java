/* allo stesso modo di come era in laboratorio sono presenti le doppie inizializzazioni e le dimensioni delle 
 * matrici sono troppo ridotte di uno: questo provoca un ignorare gli ultimi due elementi delle stringhe da
 * allineare
 * 
 * Sarebbe possibile adattare l'algoritmo all'allineamento di AbstractSequence generiche in diversi modi:
 * uno di questi può essere di creare un algoritmo identico, con prototipo polimorfico, che semplicemente usi 
 * un alignScoringSystemPROTEIN in argomento per differenziarsi da questo, e restituisca in output un oggetto 
 * sostanzialmente identico a quello del codice qui fornito, con la premura di modificare il flag "isDna", chiamando
 * il costruttore con "false" in tale campo
 * 
 * Un altro approccio più laborioso, ma più generale, passa dall'implementazione di una superclasse astratta 
 * alignScoringSystem di cui sia alignScoringSystemDNA che alignScoringSystemPROTEIN siano estensioni, che possieda il
 * campo compare. Il passo successivo sarebbe quello di implementare un metodo privato che prenda in argomento oggetti 
 * che estendono tale superclasse qualunque, e due stringhe qualunque e costruisca le matrici e stringhe di allineamento
 * conservando il dato sulla cella di maggior punteggio.
 * Questo metodo sarebbe poi invocato da tanti wrapper quanti sono le tipologie specifiche di output che desideriamo:
 * almeno uno che prenda sequenze astratte e lo chiami con le stringhe associate al loro campo sequenza, ad esempio, 
 * e/o che confezioni l'output in oggetti del tipo Alignment, o che passi addirittura oggetti diversi, eventualmente
 * in varietà troppo grande per essere distinti con un solo flag booleano (magari implemetabili come nonBioAlignment,
 * anche questa classe astratta, che si rapporti più direttamente con le stringhe e matrici che compongono il
 * raw data dell'allineamento, se necessario)*/

public class SW {

	public Alignment SWAlignDna(AbstractSequence Aseq1, AbstractSequence Aseq2, alignScoringSystemDNA asd){

		String seq1 = "0"+Aseq1.getSequence();
		String seq2 = "0"+Aseq2.getSequence();

		// score and pointers matrices
		double[][] scorematrix = new double[seq2.length()-1][seq1.length()-1];
		String[][] pointermatrix = new String[seq2.length()-1][seq1.length()-1];
		// matrices initialization
		// a) TOP-LEFT cells initializazion 
		scorematrix[0][0]=0.0;
		pointermatrix[0][0]="*";
		// b) FIRST ROW and COLUMN initialization
		// - first row
		for(int l = 1; l<seq1.length()-1;l++) { 													scorematrix[0][l]=0.0;
			pointermatrix[0][l]="*";													}
		// - first column
		for(int l = 1; l<seq2.length()-1;l++) { 													scorematrix[l][0]=0.0;
			pointermatrix[l][0]="*";
		}
// ###################################################################################
		int max_i=0;	// row of max_score
		int max_j=0;	// column of max_score
		double max_score=0.0d;	// initial value of max_score (lower bound is 0.0)

		for(int i=1;i<seq2.length()-1;i++) {	// cycle rows (seq2)			
			for(int j=1;j<seq1.length()-1;j++) {	// cycle columns (seq1)
			// current cell variables declaration
				double diagonal_score=0.0;
				double left_score=0.0;
				double up_score=0.0; 															// current symbol in sequence 1
				char letter1 = seq1.charAt(j-1);
				// current symbol in sequence 2
				char letter2 = seq2.charAt(i-1);
				// I ) ------------- compute scores ------------------
				// A) compute scores for match/mismatch using a DNA scoring system object (asd)
				diagonal_score = scorematrix[i-1][j-1] + asd.compare.get(Character.toString(letter1)+Character.toString(letter2));
				// B) compute gap scores (unweighted linear mode)
				up_score = scorematrix[i-1][j] + asd.gap;
				left_score = scorematrix[i][j-1] + asd.gap;
				// --------------------------------------------------
				// II) ------------- best score choice --------------
				// case 0) letter1 equals "0" || letter1 equals "0"  (DP matrix borders)
				if(Character.toString(letter1).equals("0")||Character.toString(letter2).equals("0")) {
					scorematrix[i][j]=0;
					pointermatrix[i][j]="*";
					continue;
				}
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
				align1 = align1 + seq1.substring(j-1,j);
				align2 = align2 + seq2.substring(i-1,i);
				if(seq1.substring(j-1,j).equals(seq2.substring(i-1,i))) {descstring+="|";}
				if(!seq1.substring(j-1,j).equals(seq2.substring(i-1,i))) {descstring+=".";}
				i--;
				j--;
			}else if(pointermatrix[i][j].equals("l")) {
				align1 = align1 + seq1.substring(j-1,j);
				align2 = align2 + "-";
				descstring+=" ";
				j--;
			}else if(pointermatrix[i][j].equals("u")) {
				align1 = align1 + "-";
				align2 = align2 + seq2.substring(i-1, i);
				descstring+=" ";
				i--;
			}
		} // traceback end
		// V ) ------------- OUTPUT OBJECT CONSTRUCTION ----------------------
		// V.a: reverse the alignment strings --------------------------------
		StringBuffer s = new StringBuffer(align1);
		align1 = s.reverse().toString();
		s = new StringBuffer(align2);
		align2 = s.reverse().toString();
		s = new StringBuffer(descstring);
		descstring = s.reverse().toString();
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
