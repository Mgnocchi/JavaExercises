import prog.io.*;



public class BE_2_5 {
	
	//un metodo statico non appartiene ad un oggetto (istanza di una classe) ma alla classe stessa
	
	static void stampaIncorniciata(String s) {
		ConsoleOutputManager out = new ConsoleOutputManager();
		
		int lunghezza = s.length();
		
		String asterischi = "**";
		String vuoto = "";
		
		for (int i=0; i<lunghezza+4; i++) {
			asterischi+="*";
			vuoto+=" ";
		}
		
		out.println(asterischi);
		out.println("*" + vuoto + "*");
		out.println("*  " + s + "  *");
		out.println("*" + vuoto + "*");
		out.println(asterischi);
	}

	
	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		
		
		String nome = in.readLine("Inserire nome: ");
		String cognome = in.readLine("Inserire cognome: ");
		
		String nomeAndCognome = (nome.toUpperCase() + " " + cognome.toUpperCase());

		stampaIncorniciata(nomeAndCognome);
		
	}

}
