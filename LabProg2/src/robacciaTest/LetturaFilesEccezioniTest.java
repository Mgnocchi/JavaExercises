package robacciaTest;

import java.io.FileReader;
import java.io.IOException;

//import prog.io.ConsoleOutputManager;

public class LetturaFilesEccezioniTest {

	public static void main(String[] args) {
		//ConsoleOutputManager out = new ConsoleOutputManager();
		try {
			FileReader frd = new FileReader(args[0]);
			int i;
			while((i = frd.read()) != -1) {
				System.out.print((char) i);
			}
			frd.close();
		}
		catch(IOException ecc) {
			System.out.println("Nome file non valido");
		}
	}

}
