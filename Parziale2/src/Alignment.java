import java.io.PrintStream;
import java.io.PrintWriter;


public class Alignment {
	// members (state) :
	public AbstractSequence Aseq1 = null;
	public AbstractSequence Aseq2 = null;
	//public String alignment = "";
	public String Aseq1AlnStr = "";
	public String Aseq2AlnStr = "";
	public String AlnDecoStr = "";
	public boolean isDNA;
	public double[][] scoreMatrix = null;	// score matrix
	public String[][] pointerMatrix = null; // pointer matrix
	public double maxScore = 0.0d;

	// methods (behavior) :
	// Constructor
	Alignment(AbstractSequence as1, AbstractSequence as2, String S1AlnString, String S2AlnString, String AlnDecoString, boolean isDna, double[][] scoreM, String[][] pointerM, double maxScr ){
		this.Aseq1 = as1;
		this.Aseq2 = as2;
		//this.alignment = a;
		this.Aseq1AlnStr = S1AlnString;
		this.Aseq2AlnStr = S2AlnString;
		this.AlnDecoStr = AlnDecoString;
		this.isDNA = isDna;
		this.scoreMatrix = scoreM;
		this.pointerMatrix = pointerM;
		this.maxScore = maxScr;
	}
	// print alignment methods on stdout
	void printAln() {
		this.printAln(null, true);
	}
	// print alignment methods on scriba
	void printAln(PrintWriter scriba){ 
		this.printAln(scriba,false);
	}
	// Polimorphic metod to reduce spaghetti code
	private void printAln(PrintWriter scriba, boolean stdout){

		String s1 = this.Aseq1AlnStr;
		String s2 = this.Aseq2AlnStr;
		String sd = this.AlnDecoStr;

		int tokenslength = 60;    // 60 characters (FASTA standard)
		// spezza stringa in sottostringhe di tokenslength caratteri e le salva in String array
		String[] s1lines = s1.split(String.format("(?<=\\G.{%1$d})", tokenslength));
		String[] s2lines = s2.split(String.format("(?<=\\G.{%1$d})", tokenslength));
		String[] sdlines = sd.split(String.format("(?<=\\G.{%1$d})", tokenslength));

		int currow = 0;
		if (stdout) {
			System.out.println("seq1: "+ this.Aseq1.getHeader());
			System.out.println("seq2: "+ this.Aseq2.getHeader());
			System.out.println("Score: " + String.format("%.3f", this.maxScore) + "\n");

			for(String s : s1lines){
				System.out.println(s);
				System.out.println(sdlines[currow]);
				System.out.println(s2lines[currow] + "\n");
				currow++;
			}
		}
		else {
			scriba.println("seq1: "+ this.Aseq1.getHeader());
			scriba.println("seq2: "+ this.Aseq2.getHeader());
			scriba.println("Score: " + String.format("%.3f", this.maxScore) + "\n");

			for(String s : s1lines){
				scriba.println(s);
				scriba.println(sdlines[currow]);
				scriba.println(s2lines[currow] + "\n");
				currow++;
			}
		}
	}
		
		//stampa la score matrix
		void printScoreMatrix(PrintStream scriba){
			for (int j=1; j<this.scoreMatrix.length-1; j++) { //righe
				for (int i=1; i<this.scoreMatrix[0].length-1; i++) { //colonne
					scriba.print(String.format("%.2f",this.scoreMatrix[i][j]) + " ");
				}
				scriba.println(); //a fine riga vado a capo
			}
			scriba.println();
		}
		
		void printScoreMatrix(){
			this.printScoreMatrix(System.out);
		}
		
		//stampa la pointer matrix
		void printPointerMatrix(PrintStream scriba){
			for (int j=1; j< this.pointerMatrix.length-1; j++) { //righe
				for (int i=1; i<this.pointerMatrix[0].length-1; i++) { //colonne
						scriba.print(this.pointerMatrix[i][j] + " ");
				}
				scriba.println(); //a fine riga vado a capo
			}
			scriba.println();
		}
		
		void printPointerMatrix(){
			this.printPointerMatrix(System.out);
		}
}
