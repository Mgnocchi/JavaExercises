package robacciaTest;

import java.util.ArrayList;


import prog.io.*;

public class listaGenericaCapireTipo {
	
	public static boolean readYesNo(ConsoleInputManager in, ConsoleOutputManager out) {
		char choice;
		do{
			choice = Character.toLowerCase(in.readChar());
			if (choice != 'y' && choice != 'n') {
				out.println("Carattere non valido, scegliere (Y/n): ");
				continue;
			}
			else if(choice == 'y') {
				return true;
			}
			else if(choice == 'n') {
				return false;
			}
		}while(true);
	}
	
	private static boolean readTwo(ConsoleInputManager in, ConsoleOutputManager out, char op1, char op2) {
		char choice;
		op1 = Character.toLowerCase(op1);
		op2 = Character.toLowerCase(op2);
		
		do{
			choice = Character.toLowerCase(in.readChar());
			if (choice != op1 && choice != op2) {
				out.println("Carattere non valido, scegliere " + op1 + " o " + op2);
				continue;
			}
			else if(choice == op1) {
				return true;
			}
			else if(choice == op2) {
				return false;
			}
		}while(true);
	}
	
	private static ArrayList<Integer> leggiListaInteri(ConsoleInputManager in, ConsoleOutputManager out){
		ArrayList<Integer> lista = new ArrayList<Integer>();
		
		while(true) {
			out.println("Vuoi inserire un intero? (Y/n)");
			if(readYesNo(in, out)) {
				out.println("prego: ");
				lista.add(in.readInt());
			}
			else break;
		}
		return lista;
		
	}
	
	private static ArrayList<Double> leggiListaDouble(ConsoleInputManager in, ConsoleOutputManager out){
		ArrayList<Double> lista = new ArrayList<Double>();
		
		while(true) {
			out.println("Vuoi inserire un double? (Y/n)");
			if(readYesNo(in, out)) {
				out.println("prego: ");
				lista.add(in.readDouble());
			}
			else break;
		}
		return lista;
		
	}


	public static void main(String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		ArrayList<?> lista = null;
		
		out.println("Vuoi una sequenza di interi o di double? (I/d)");
		if (readTwo(in, out, 'i', 'd')) {
			lista = leggiListaInteri(in,out);
		}
		else { 
			lista = leggiListaDouble(in,out);
		}
		
	//lista instanceof ArrayList<Integer> è un'espressione illegale: come facciamo?
	//l'idea è peek	
		if (!lista.isEmpty()) {
			if(lista.get(0) instanceof Integer) {	//non ho effettivamente peek perchè non è uno stack, qui l'accesso è libero
				out.println("La lista contiene gli interi ");
				for(Object o:lista) {
					out.println(o.toString());
				}
			}
			else if(lista.get(0) instanceof Double) {	//non ho effettivamente peek perchè non è uno stack, qui l'accesso è libero
				out.println("La lista contiene i Double ");
				for(Object o:lista) {
					out.println(o.toString());
				}
			}
			else  out.println("che cazzo? la lista è di tipo " + lista.get(0).getClass());
		}
		else out.println("La lista è vuota ");
		

	}

}
