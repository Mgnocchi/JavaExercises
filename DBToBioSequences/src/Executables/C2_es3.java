package Executables;


import MyDataToBio.SQLSantaCruz.UCSCInterrogation;

/*Scrivere un metodo in grado di contare il numero dei geni contenuti in ogni cromosoma
(considerate solo I cromosomi il cui nome NON contiene il carattere _*/
public class C2_es3 {

	public static void main(String[] args) {
		UCSCInterrogation interrogator = new UCSCInterrogation();
		String tmpSqlStatement = "select chrom, count(chrom) from knownGene where chrom NOT LIKE '%\\_%' group by chrom;";
		System.out.println("Number of genes for cromosome");
		System.out.println("-------------------------------------------------");
		interrogator.RemoteConnUCSCgenericQuery(tmpSqlStatement,false);
	}

}
