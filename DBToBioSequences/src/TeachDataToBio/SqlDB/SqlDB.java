package TeachDataToBio.SqlDB;
import java.sql.*;
import java.util.*;

public class SqlDB {

	// Esegue una query SQL data la stringa che la contiene e una HashMap contenente i parametri di connessione
	// Stampa a video il risultato. NB: prevede la conoscenza delle colonne nel set di risultati
	public void RemoteConnUCSC(String myStatmnt, Map<String,String> connParams){
		
		try(
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://"+ connParams.get("hostname")  +":3306/"+ connParams.get("dbname"),
				connParams.get("username"),
				"");
			Statement st = conn.createStatement();	// empty statement
			){
				ResultSet rset = st.executeQuery(myStatmnt);
				while(rset.next()){
					// ATTENZIONE: questo metodo di stampa del risultato 
					// non e' applicabile a tutte le query! Va bene solo se
					// stiamo estraendo dal database sequenze e vogliamo 
					// rappresentarle in formato FASTA.
					String header = rset.getString("name");
					String seq = rset.getString("seq");
					System.out.println(">"+header);
					int tokenslength = 60;    // 60 characters (FASTA standard)
					// spezza stringa in sottostringhe di tokenslength caratteri
					// e le salva in String array
					String[] seqlines = seq.split(String.format("(?<=\\G.{%1$d})", tokenslength));
					for(String s: seqlines) {
						System.out.println(s);
						// stampa fullsequence in righe di tokenslength caratteri
					}
				}
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
	}

	// Esegue una query SQL data la stringa che la contiene e na HashMap contenente i parametri di connessione
	// Non fa assunzioni sulla natura del set di risultati ottenuto e utilizza una logica di stampa generica
	// (non adattata al formato FASTA come nel metodo precedente).
	public void RemoteConnUCSCgenericQuery(String myStatmnt, Map<String, String> connParams){
		try(
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://"+ connParams.get("hostname")  +":3306/"+ connParams.get("dbname"),
				connParams.get("username"),
				"");
			Statement st = conn.createStatement();
			){
				ResultSet rset = st.executeQuery(myStatmnt);
				ResultSetMetaData rsmd = rset.getMetaData();
				int nCols = rsmd.getColumnCount();

				System.out.println("ResultSet MetaData:");
				System.out.println("---------------------------------");

				for(int c=1; c<=nCols; c++){
					System.out.println(rsmd.getColumnName(c)+"\t"+rsmd.getColumnTypeName(c));
				}
				System.out.println("---------------------------------\n");


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


	public void RemoteConnUCSCPep(String myStatmnt, Map<String,String> connParams){
	
		try(
				Connection conn = DriverManager.getConnection(
				"jdbc:mysql://"+ connParams.get("hostname")  +":3306/"+ connParams.get("dbname"),
				connParams.get("username"),
				"");
				Statement st = conn.createStatement();	// empty statement
				){
				ResultSet rset = st.executeQuery(myStatmnt);
				while(rset.next()){
					// ATTENZIONE: questo metodo di stampa del risultato 
					// non e' applicabile a tutte le query! Va bene solo se
					// stiamo estraendo dal database sequenze e vogliamo 
					// rappresentarle in formato FASTA.
					String header = rset.getString("name");
					String seq = rset.getString("seq");
				
//					// longblob test ----------------------------------------------------
//					Blob nativedata = rset.getBlob("seq");
//					byte[] bytes = nativedata.getBytes(1, (int)nativedata.length());
//					String blobString = new String(bytes);
//					if(seq.equals(blobString)){
//						System.out.println("Equals strings...");
//					}
//					// end longblob test ------------------------------------------------
				
					System.out.println(">"+header);
					int tokenslength = 60;    // 60 characters (FASTA standard)
					// spezza stringa in sottostringhe di tokenslength caratteri
					// e le salva in String array
					String[] seqlines = seq.split(String.format("(?<=\\G.{%1$d})", tokenslength));
					for(String s: seqlines) {
						System.out.println(s);
						// stampa fullsequence in righe di tokenslength caratteri
					}
				}
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
	}
}

