import java.util.*;

public class myTest {
	public static void main(String[] args){
		Map<String, String> parametriconnessione = new HashMap<String,String>();
		parametriconnessione.put("hostname","genome-euro-mysql.soe.ucsc.edu");
		parametriconnessione.put("username","genome");
		parametriconnessione.put("dbname","hg38");
		
		SqlDB ucsc = new SqlDB();

		String tmpSqlStatement = "select * from knownGeneMrna limit 5";
		ucsc.RemoteConnUCSC(tmpSqlStatement, parametriconnessione);

		System.out.println();
		tmpSqlStatement = "select * from knownGene limit 5";
		ucsc.RemoteConnUCSCgenericQuery(tmpSqlStatement, parametriconnessione);
		
		System.out.println();
		tmpSqlStatement = "select * from knownGenePep limit 2";
		ucsc.RemoteConnUCSCPep(tmpSqlStatement, parametriconnessione);
		
	}

}
