package es_041120;

import prog.io.*;
import prog.utili.Frazione;
import java.util.ArrayList;
//import java.util.Collections;

//chiedi frazioni, interi o stringhe, poi stampa in ordine

public class BE_6_10 {
	
	private static ArrayList<String> lettoreStringhe(){
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		char Choice;
		ArrayList<String> list = new ArrayList<String>();
		
		do{
			out.println("Vuoi inserire una Stringa? (Y/n) ");
			Choice = in.readChar();
			if (Character.toLowerCase(Choice) == 'y') {
				out.println("Vadi: ");
				list.add(in.readLine());			
			}
			else if (Character.toLowerCase(Choice) != 'n') {
				out.print("Non ho capito... ");
				Choice = 'y';
			}
		}while( Character.toLowerCase(Choice) == 'y' );
		
		return list;
	}
	
	private static ArrayList<Integer> lettoreInteri(){
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		char Choice;
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		do{
			out.println("Vuoi inserire un'intero? (Y/n) ");
			Choice = in.readChar();
			if (Character.toLowerCase(Choice) == 'y') {
				out.println("Vadi: ");
				list.add(in.readInt());			
			}
			else if (Character.toLowerCase(Choice) != 'n') {
				out.print("Non ho capito... ");
				Choice = 'y';
			}
		}while( Character.toLowerCase(Choice) == 'y' );
		
		return list;
	}
	
	private static ArrayList<Frazione> lettoreFrazioni(){
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		char Choice;
		ArrayList<Frazione> list = new ArrayList<Frazione>();
		
		do{
			out.println("Vuoi inserire una Frazione? (Y/n) ");
			Choice = in.readChar();
			if (Character.toLowerCase(Choice) == 'y') {
				out.println("inserisci numeratore: ");
				int num = in.readInt();
				out.println("inserisci denominatore: ");
				int den = in.readInt(); //potrei farlo nel costruttore, ma così gestisce meglio le eccezioni
				list.add(new Frazione(num, den));
			}				
			else if (Character.toLowerCase(Choice) != 'n') {
				out.print("Non ho capito... ");
				Choice = 'y';
			}
		}while( Character.toLowerCase(Choice) == 'y' );
		
		return list;
	}
	
	/*
	private static <T  extends Comparable<T>> ArrayList<T> riordina(ArrayList<T> input){
		input.sort(null);
		return input;
	}
	*/
	
	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		ArrayList<? extends Comparable<?>> list = null;
		char Choice;
		
		while (list == null) {
			out.println("Scegli un tipo di dato:\n - F: frazione\n - S: stringa\n - I: intero");
			Choice = in.readChar();
			if (Character.toLowerCase(Choice) == 'f') {
				list = lettoreFrazioni();		
			}
			else if (Character.toLowerCase(Choice) == 's') {
				list = lettoreStringhe();		
			}
			else if (Character.toLowerCase(Choice) == 'i') {
				list = lettoreInteri();		
			} 
			else {
				out.print("Non ho capito... ");
			}
		}
		
		list.sort(null);
		
		out.println("Tieni, te li ho ordinati in ordine ordinato:");
		for(Comparable<?> o:list) {
			out.println(o.toString());
		}
		
	}

}
