import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JavaFileParser {
	// I) CAMPI (rappresentano lo STATO dell'oggetto)
	private String inputfile;
	private String outputfile;
	private boolean serialize;	//Definisce se si vuole output su dile (true) o meno (false)
	
	// II) COSTRUTTORI (servono per inizializzare gli oggetti in modo coerente)
	public JavaFileParser(String inputfilePath, String outputfilePath, boolean serializeFlag) {
		inputfile = inputfilePath;
		outputfile = outputfilePath;
		serialize = serializeFlag;
	}
	
	public JavaFileParser(String inputfilePath, String outputfilePath) {
		this(inputfilePath, outputfilePath, false);
		//inputfile = inputfilePath;
		//outputfile = outputfilePath;
		//serialize = false;
	}
	

	// III) METODI (comportamento, servizi)
	
	// III.a : lettura campi
	public String getInputFilePath() {
		return inputfile;
	}
	
	public String getOutputFilePath() {
		return outputfile;
	}
	
	public boolean getSerializeFlag() {
		return serialize;
	}
	// III.b : scrittura campi
	public void setInputFilePath(String filename) {
		inputfile = filename;
	}
	
	public void setOutputFilePath(String filename) {
		outputfile = filename;
	}
	
	public void setSerializeFlag(boolean flag) {
		serialize = flag;
	}
	
	// III.c : multi FASTA file parser
	//public static void main(String[] args) throws IOException {
	public void parse() throws IOException {
		// Class to parse biological sequences from FASTA files
		//BufferedReader bufRead = new BufferedReader(new FileReader("Homo_sapiens.GRCh38.cds.all.fa"));
		BufferedReader bufRead = new BufferedReader(new FileReader(inputfile));
		BufferedWriter bufWrite = new BufferedWriter(new FileWriter(outputfile));

		String curline = "";	// current line from BufferedReader 
		String seqid = "";		// sequence identifier
		String seqloc = "";		// sequence location
		String seqbiotype = "";	// sequence biotype
		String sequence = "";	// nucleotide sequence
		String formattedheader = ""; // formatted header
		int seenheaders = 0;
		int parsedsequences = 0;
		
		while((curline = bufRead.readLine()) != null){
			
			// Step 1: parsing the current line:
			
			if(curline.startsWith(">")) { // header row. It must be parsed ...
				// CASE I: we have already seen one sequence. Print and reset variables
				if(seenheaders>0) {
					// print output line if controlString equals P (print)
					if(formattedheader.charAt(0) == 'P') {
						if(!serialize) {
							System.out.println(formattedheader.substring(1) + "\t" + sequence.length() + "\t" + sequence);
						}else {
							bufWrite.write(formattedheader.substring(1) + "\t" + sequence.length() + "\t" + sequence + "\n");
						}
						parsedsequences++;
					}
					// clean variables
					//curline = "";		// current line // INTERESTING ERROR 1 
					seqid = "";			// sequence identifier
					seqloc = "";		// sequence location
					seqbiotype = "";	// sequence biotype
					sequence = "";		// nucleotide sequence
					formattedheader = ""; // formatted header
				}
				// CASE II : parsing the multifasta file 
				
//				 Header format (taken from :ftp://ftp.ensembl.org/pub/release-101/fasta/homo_sapiens/cds/ README file )
//-----------------------------------------------------------------------------------------------------------------------------------
//				>ENST00000525148 cds chromosome:GRCh37:11:66188562:66193526:1 gene:ENSG00000174576 gene_biotype:protein_coding transcript_biotype:nonsense_mediated_decay
//				 ^               ^    ^     ^                                       ^                    ^                           ^       
//				 ID              |    |  LOCATION                         GENE: gene stable ID        GENE: gene biotype        TRANSCRIPT: transcript biotype
//				                 |  STATUS
//				              SEQTYPE
//-----------------------------------------------------------------------------------------------------------------------------------				
				// We are interested in three fields: ID, LOCATION and gene_biotype
				
				ArrayList<String> strarray = new ArrayList<String>(  Arrays.asList(curline.substring(1).split(" ") ));				
				
				seqid = strarray.get(0);
				seqloc = strarray.get(2);
				seqbiotype = strarray.get(3);
				String controlString = "N";
				
				
				// seqloc is composed by 6 fields (we are interested in fields 3 to 6)
				strarray = new ArrayList<String>( Arrays.asList(seqloc.split(":")) );
				seqloc = strarray.get(2)+":"+strarray.get(3)+":"+strarray.get(4)+":"+strarray.get(5);
				// chromosome name length check -> if standard chromosome the name length is < 3
				if(strarray.get(2).length()<3){
					controlString="P";
					}
				// gene biotype is composed by two fields (we are interested in the second one)
				strarray = new ArrayList<String>( Arrays.asList( seqbiotype.split(":") ));
				seqbiotype = strarray.get(1);
				
				// formatted header construction
				formattedheader = controlString+seqid+"_"+seqloc+"_"+seqbiotype;
				
				// increment headers count
				seenheaders++;
			}else{
				sequence = sequence + curline;
			}
				
		} // end of while
		
		bufRead.close();
		// Print out the last parsed sequence (no more headers able to trig this step in the input file...)
		if(formattedheader.charAt(0) == 'P') {
			if(!serialize) {
				System.out.println(formattedheader.substring(1) + "\t" + sequence.length() + "\t" + sequence);
			}else {
				bufWrite.write(formattedheader.substring(1) + "\t" + sequence.length() + "\t" + sequence+"\n");
			}
		}
		// Print session info:
		if(!serialize) {
			System.out.println("\n");
			System.out.println("Seen sequences:\t"+seenheaders);
			System.out.println("Parsed sequences:\t"+parsedsequences);
		}else{
			bufWrite.write("\n\n");
			bufWrite.write("Seen sequences:\t"+seenheaders+"\n");
			bufWrite.write("Parsed sequences:\t"+parsedsequences+"\n");
			System.out.println("\nJob done!\n");
		}
		bufWrite.close();

	}
	
	
	
	
	
}
