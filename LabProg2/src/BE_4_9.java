import prog.io.*;
import java.util.Random;

public class BE_4_9 {

	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		Random rng = new Random();
		double xPoint, yPoint, Chance;
		long HowMany, HMinside = 0, i;
		
		out.println("Questo programma calcola l'area di un quarto di circonferenza di raggio 1 col metodo Montecarlo");
		out.println("inserisci la dimensione del campione: ");
		HowMany = in.readLong();
		
		for (i=0; i<HowMany ; i++) {
			xPoint=rng.nextDouble();
			yPoint=rng.nextDouble();
		    // if((xPoint*xPoint + yPoint*yPoint)<1) elevare al quadrato può causare problemi di overflow a volte
			if(Math.hypot(xPoint, yPoint) < 1){ //restituisce l'ipotenusa di un triangolo rettangolo con gli argomenti come cateti, senza over e underflow intermedi
				HMinside++;						//osserva che Math è una classe: invocare hypot è possibile solo perchè è metodo statico
			}
		}
		
		Chance = (double)HMinside / HowMany;
		out.println("L'area stimata è di " + Chance);
		out.println("E quindi la stima di pi greco è " + Chance*4);
		out.println("Mentre il vero pi greco è " + Math.PI);
		
	}

}
