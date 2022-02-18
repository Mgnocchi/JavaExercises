package Executables;

import MyDataToBio.Sequences.*;
import MyDataToBio.SQLSantaCruz.UCSCInterrogation;

import java.util.List;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		UCSCInterrogation interrogator = new UCSCInterrogation();
		String tmpSqlStatement = "select * from knownGeneMrna limit 5";
		List<DnaSequence> trascritti = interrogator.AskMRNA(tmpSqlStatement);
		DnaSequence testItem = trascritti.get(0);
		
		
//		for (DnaSequence seq:trascritti) {
//			seq.printFasta();
//		}
		
		//test getAlphabet
		Set<Character> alphabet = testItem.getAlphabet();
		System.out.print("Alfaberto:");
		for(Character a: alphabet) {
			System.out.print(" " + a);
		}
		System.out.println(";");
		
		//test Complementary
		System.out.println("Seq and complementary (double reversed):");
		StringBuffer s = new StringBuffer(testItem.reverseComplement()); 
		String notRevCompl =  s.reverse().toString();
		String decoration = "";
		String testSequence = testItem.getSequence();
		char a,b;
		System.out.println("seq: " + testSequence.length());
		System.out.println("com: " + notRevCompl.length());
		
		for (int i=0;i<testSequence.length(); i++) {
			a = testSequence.charAt(i);
			b = notRevCompl.charAt(i);
			if ((a == 'T' && b == 'A') || (a == 'A' && b == 'T') || (a == 'C' && b == 'G') || (a == 'G' && b == 'C')) {
				decoration = decoration + "|";
			}
			else if(!alphabet.contains(a)) {
				decoration = decoration + " ";
			}
			else {
				decoration = decoration + ".";
			}
			
		}
		System.out.println(testItem.getSequence());
		System.out.println(decoration);
		System.out.println(notRevCompl);
		
		//test frames and translation
		testItem.setSequence(testSequence.substring(0, 10));
		System.out.println(testItem.getSequence());
		testItem.initFrames();
		testItem.translateFrames();
		String frameTemp;
		for(String key: testItem.getFrames().keySet()) {
			frameTemp = testItem.getFrames(key);
			System.out.println(key + ":" + frameTemp);
			System.out.println("Codoni:");
			for(String cod : testItem.getFrameCodons(key)) {
				System.out.println(cod);
			}
			System.out.println("Translation:");
			System.out.println(testItem.getTranslations(key)); 
			
		}

	}

}
