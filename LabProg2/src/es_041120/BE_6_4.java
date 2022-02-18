package es_041120;

import prog.io.ConsoleInputManager;
import prog.io.ConsoleOutputManager;
import prog.utili.Cerchio;
import prog.utili.Figura;
import prog.utili.Quadrato;
import prog.utili.Rettangolo;
import java.util.ArrayList;

//accetta in input un elenco di figure: circonferenze, rettangoli e quadrati
//elencali, indicando per ognuno le dimensioni che lo identificano (lato, base/altezza, raggio, ecc..), area e perimetro
//comunica poi la media delle basi dei rettangoli e le altezze dei rettangoli (contando i quadrati come rettangoli), dei lati dei quadrati e raggi delle circonferenze


public class BE_6_4 {
	
//------------------------------------------------------------------ chiediFigura()
	private static Figura chiediFigura() {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		char Choice;
		
		do {
			out.println("Vuoi inserire un Rettangolo o un Cerchio? (R/C) ");
			Choice = in.readChar();
			if (Character.toLowerCase(Choice) == 'c') {
				return chiediCerchio();
			}
			else if (Character.toLowerCase(Choice) == 'r') {
				return ChiediRettangolo();
			}
			out.println("Scelta non riconosciuta!");
		} while ( true );
	}
//-------------------------------------------------------------------- chiediCherchio()
	private static Cerchio chiediCerchio () {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		double r;
		
		out.println("Inserisci il raggio della Figura: ");
		while ((r = in.readDouble() ) <= 0 ) {
			out.println("Ma che ooohh??? Non puoi inserire valori negativi o nulli! Riscrivi la base: ");
		}
		
		return new Cerchio(r);
	}
//------------------------------------------------------------------- chiediRettangolo()
	private static Rettangolo ChiediRettangolo() {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		double b = 0,h = 0;
		
		out.println("Inserisci la Base della Figura: ");
		while ((b = in.readDouble() ) <= 0 ) {
			out.println("Ma che ooohh??? Non puoi inserire valori negativi o nulli! Riscrivi la base: ");
		}
		out.println("Inserisci l'Altezza della Figura: ");
		while ((h = in.readDouble()) <= 0 ) {
			out.println("Ma che ooohh??? Non puoi inserire valori negativi o nulli! Riscrivi l'altezza: ");
		}
		if ( Double.compare( b , h ) == 0) {
			return new Quadrato(b);
		}
		else {
			return new Rettangolo(b,h);
		}
	}
	
//------------------------------------------------------------------- descriviFigura();
	private static void descriviFigura(Figura robo) {
		ConsoleOutputManager out = new ConsoleOutputManager();
		
		if(robo instanceof Rettangolo){
			if (robo instanceof Quadrato) {
				Quadrato roboQuadrato = (Quadrato) robo;
				out.println("Quadrato di lato " + roboQuadrato.getLato());
				out.println("Ha area " + roboQuadrato.getArea() + " e perimetro " +roboQuadrato.getPerimetro()); 
			}
			else{
				Rettangolo roboRettangolo = (Rettangolo) robo; 
				out.println("Rettangolo di base " + roboRettangolo.getBase() + " e altezza " + roboRettangolo.getAltezza());
				out.println("Ha area " + roboRettangolo.getArea() + " e perimetro " +roboRettangolo.getPerimetro());			}
		}
		else {
			Cerchio roboCerchio = (Cerchio) robo; 
			out.println("Quadrato di lato " + roboCerchio.getRaggio());
			out.println("Ha area " + roboCerchio.getArea() + " e perimetro " +roboCerchio.getPerimetro());
		}
	}


//----------MAIN.
	
	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		char Choice = 'y';
		ArrayList<Figura> listaFigure = new ArrayList<Figura>();
		Figura corrente;
		
		
		while( Character.toLowerCase(Choice) == 'y' ){
			out.println("Vuoi inserire una figura? (Y/n) ");
			Choice = in.readChar();
			if (Character.toLowerCase(Choice) == 'y') {
				corrente = chiediFigura();
				descriviFigura(corrente);
				listaFigure.add(corrente);				
			}
			else if (Character.toLowerCase(Choice) != 'n') {
				out.print("Non ho capito... ");
				Choice = 'y';
			}
		}
		
		if(listaFigure.isEmpty()) {
			out.println("Nessuna figura inserita, io chiudo ");
		}
		else {
			int contaQuadrati = 0, contaCerchi  = 0, contaRettangoli = 0;
			double sommaLati = 0.0, sommaBasi = 0.0, sommaAltezze = 0.0, sommaRaggi = 0.0;
			
			//se ho quadrati fai media lati
			for(Figura f:listaFigure) {
				if(f instanceof Cerchio) {
					contaCerchi++;
					sommaRaggi += ((Cerchio) f).getRaggio();
				}
				else if(f instanceof Quadrato) {
					contaQuadrati++;
					sommaLati += ((Quadrato) f).getLato();
				}
				else if(f instanceof Rettangolo) {
					contaRettangoli++;
					sommaBasi += ((Rettangolo) f).getBase();
					sommaAltezze += ((Rettangolo) f).getAltezza();
				}
			}
			
			if (contaQuadrati != 0) {
				out.println("La media dei lati dei quadrati è " + sommaLati/contaQuadrati);
			} else out.println("Non sono stati inseriti quadrati ");
			
			if (contaRettangoli != 0) {
				out.println("La media delle basi dei rettangoli è " + sommaBasi/contaRettangoli);
				out.println("La media delle altezze dei rettangoli è " + sommaAltezze/contaRettangoli);
			} else out.println("Non sono stati inseriti rettangoli ");
			
			if (contaCerchi != 0) {
				out.println("La media dei raggi dei cerchi è " + sommaRaggi/contaCerchi);
			} else out.println("Non sono stati inseriti cerchi");
			
			
			
		}
		
			//se ho cerchi fai media raggi
			//se ho rettangoli fai media basi e media 
	
	}

}
