import java.util.*;

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
		this.Aseq1AlnStr = S1AlnString;
		this.Aseq2AlnStr = S2AlnString;
		this.AlnDecoStr = AlnDecoString;
		this.isDNA = isDna;
		this.scoreMatrix = scoreM;
		this.pointerMatrix = pointerM;
		this.maxScore = maxScr;
	}
	// print alignment methods
	void printAln(){
		String s1 = this.Aseq1AlnStr;
		String s2 = this.Aseq2AlnStr;
		String sd = this.AlnDecoStr;

		int tokenslength = 60;    // 60 characters (FASTA standard)
		// spezza stringa in sottostringhe di tokenslength caratteri e le salva in String array
		String[] s1lines = this.Aseq1AlnStr.split(String.format("(?<=\\G.{%1$d})", tokenslength));
		String[] s2lines = this.Aseq2AlnStr.split(String.format("(?<=\\G.{%1$d})", tokenslength));
		String[] sdlines = this.AlnDecoStr.split(String.format("(?<=\\G.{%1$d})", tokenslength));

		int currow = 0;
		System.out.println("seq1: "+ this.Aseq1.getHeader());
		System.out.println("seq2: "+ this.Aseq2.getHeader());
		System.out.println("Score: " + String.format("%.3f", this.maxScore) + "\n");

		System.out.println("\nAlignment:");
		for(String s : s1lines){
			System.out.println(s);
			System.out.println(sdlines[currow]);
			System.out.println(s2lines[currow] + "\n");
			currow++;
		}
	}
}
