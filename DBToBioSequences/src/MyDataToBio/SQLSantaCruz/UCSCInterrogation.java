package MyDataToBio.SQLSantaCruz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import MyDataToBio.Sequences.*;


public class UCSCInterrogation {
	
//|||||||||||||||||||  CAMPI  |||||||||||||||||||||||||||||||||||||||||
	protected String Hostname = null;
	protected String Username = null;
	protected String DBname = null;
//|||||||||||||||||| COSTRUTTORI ||||||||||||||||||||||||||||||||||||||	
	public UCSCInterrogation(String hostname, String username, String dbname) {
		this.Hostname = hostname;
		this.Username = username;
		this.DBname = dbname;
	}
	public UCSCInterrogation(){
		this("genome-euro-mysql.soe.ucsc.edu","genome","hg38");
	}
//|||||||||||||||||||| METODI |||||||||||||||||||||||||||||||||||||||||	
	
	// Esegue una query SQL data la stringa che la contiene e una HashMap contenente i parametri di connessione
	// Li passa in una lista di DnaSequence; null se non ha letto
	//NB: prevede la conoscenza delle colonne nel set di risultati
	public ArrayList<DnaSequence> AskMRNA(String myStatmnt){
		ArrayList<DnaSequence> seqOut = null;
		
		try(
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://"+ this.Hostname  +":3306/"+ this.DBname,
				this.Username,"");
			Statement st = conn.createStatement();	// empty statement
			){
			ResultSet rset = st.executeQuery(myStatmnt);	
			seqOut = new ArrayList<>();
			while(rset.next()){
				String header = rset.getString("name");
				String seq = rset.getString("seq");
				seqOut.add(new DnaSequence(header, seq));
			}
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
			return seqOut;
	}
	
	
	// Esegue una query SQL data la stringa che la contiene e na HashMap contenente i parametri di connessione
	// Non fa assunzioni sulla natura del set di risultati ottenuto e utilizza una logica di stampa generica
	// (non adattata al formato FASTA come nel metodo precedente).
	public void RemoteConnUCSCgenericQuery(String myStatmnt, boolean printMeta){
		try(
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://"+ this.Hostname  +":3306/"+ this.DBname,
					this.Username,"");
			Statement st = conn.createStatement();
			){
				ResultSet rset = st.executeQuery(myStatmnt);
				ResultSetMetaData rsmd = rset.getMetaData();
				int nCols = rsmd.getColumnCount();
				
				if(printMeta) {
					System.out.println("ResultSet MetaData:");
					System.out.println("---------------------------------");

					for(int c=1; c<=nCols; c++){
						System.out.println(rsmd.getColumnName(c)+"\t"+rsmd.getColumnTypeName(c));
					}
					System.out.println("---------------------------------\n");
				}

				while(rset.next()){
					for(int i=1; i<=nCols;i++){
						if(i>1) System.out.print(" | ");
						String columnVal = rset.getString(i);
						System.out.print(columnVal);
					}
					System.out.println("");
				}
			
			}catch(SQLException ex){
				ex.printStackTrace();
			}
	}


	public ArrayList<PepSequence> AskPep(String myStatmnt){
			ArrayList<PepSequence> pepList = null;
		try(
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://"+ this.Hostname  +":3306/"+ this.DBname,
						this.Username,"");
				Statement st = conn.createStatement();	// empty statement
			){
				ResultSet rset = st.executeQuery(myStatmnt);
				pepList = new ArrayList<>();
				while(rset.next()){
					String header = rset.getString("name");
					String seq = rset.getString("seq");
					pepList.add(new PepSequence(header, seq));
		//If Longblob are incorrectly interpreted
//					// longblob test ----------------------------------------------------
//					Blob nativedata = rset.getBlob("seq");
//					byte[] bytes = nativedata.getBytes(1, (int)nativedata.length());
//					String blobString = new String(bytes);
//					if(seq.equals(blobString)){
//						System.out.println("Equals strings...");
//					}
//					// end longblob test ------------------------------------------------
				}
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		return pepList;
	}
	

	
	
	//SET-----------------------------------------	
		public void setHostname (String host) {
			this.Hostname = host;
		}
		
		public void setUser (String usr) {
			this.Username = usr;
		}
		
		public void setDatabase (String dbname) {
			this.DBname = dbname;
		}
	//GET-------------------------------------------	
		public String getHostname() {
			return this.Hostname;
		}
		
		public String getUser() {
			return this.Username;
		}
		
		public String getDatabase() {
			return this.DBname;
		}	
	
	
}
