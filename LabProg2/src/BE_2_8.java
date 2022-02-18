import prog.io.*;
import prog.utili.Frazione;


public class BE_2_8 {

	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		
		int num = in.readInt("Numeratore della prima frazione: ");
		int den = in.readInt("Denominatore della prima frazione: ");
		Frazione f1 = new Frazione(num, den);

		num = in.readInt("Numeratore della prima frazione: ");
		den = in.readInt("Denominatore della prima frazione: ");
		Frazione f2 = new Frazione(num, den);

		out.println("La somma di " + f1.toString() + " e " + f2.toString() + " è: " + (f1.piu(f2)).toString() );
		out.println("La differenza di " + f1.toString() + " e " + f2.toString() + " è: " + (f1.meno(f2)).toString() );
		out.println("Il prodotto di " + f1.toString() + " e " + f2.toString() + " è: " + (f1.per(f2)).toString() );
		out.println("Il quoziente " + f1.toString() + " e " + f2.toString() + " è: " + (f1.diviso(f2)).toString() );
		
	}

}
