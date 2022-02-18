import java.util.Arrays;

public class VirusRecord {
	//Campi
	private String nome = null;
	private Integer[] occorrenze = new Integer[26];
	
	//Costruttori
	public VirusRecord(String nm) {
		nome = nm;
		Arrays.fill(occorrenze, 0);
	}
	
	//Metodi
	
	//get
	public String getNome() {
		return this.nome;
	}
	
	public Integer[] getOccorrenze() {
		return this.occorrenze;
	}
	
	//questa restituisce -1 se il carattere non è nella lista, altrimenti il suo numero di occorrenze
	public Integer getNumero(VirusRecord vrs, char c) {
		c = Character.toUpperCase(c);
		if(c<='Z' && c>='A') {
			int index = c - 'A';
			return this.occorrenze[index];
			}
		else return -1;
	}
	
	//set
	public void setNome(VirusRecord vrs, String nm) {
		this.nome = nm;
	}
	
	//le occorrenze non sono pubblicamente settabili
	
	public boolean addNumero(VirusRecord vrs, char c) {
		int index = VirusRecord.posizioneCarattere(c);
		if(index >= 0) {
			this.occorrenze[index]++;
			return true;
			}
		else return false;
	}
	
	public boolean addNumero(VirusRecord vrs, char c, int n) {
		int index = VirusRecord.posizioneCarattere(c);
		if(index >= 0) {
			this.occorrenze[index]+=n;
			return true;
			}
		else return false;
	}
	
	public static int posizioneCarattere(char c) {
		c = Character.toUpperCase(c);
		if(c<='Z' && c>='A') {
			return c - 'A';
			}
		else return -1;
	}
	
}
