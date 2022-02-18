import java.io.IOException;

public class JFPtest {

	public static void main(String[] args) throws InterruptedException, IOException{
		// costruzione oggetto istanza di classe JavaFileParser
		String strinput = "Homo_sapiens.GRCh38.cds.all.fa";
		String stroutput = "prova1.txt";
		JavaFileParser jfp = new JavaFileParser(strinput,stroutput);
		String currentinputFile = jfp.getInputFilePath();
		String currentoutputFile = jfp.getOutputFilePath();
		boolean serialize = jfp.getSerializeFlag();
		
		// stampa informazioni su oggetto jfp
		System.out.println("target input file:\t"+currentinputFile);
		System.out.println("target output file:\t"+currentoutputFile);
		System.out.println("serialize flag status: "+serialize);

		// pausa di 4 secondi
		Thread.sleep(4000);
		
		// parsing file multi FASTA
		jfp.parse();
	}

}
