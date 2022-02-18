import prog.io.ConsoleOutputManager;
import prog.io.ConsoleInputManager;

//This excercise was never finished and it's not expected to work

class ordina{
	
	//chiede un numero intero con il messaggio s
	public int ChiediNumero(String s){				
		ConsoleOutputManager ask = new ConsoleOutputManager();
		ask.println(s);
	
		ConsoleInputManager Answer = new ConsoleInputManager();
		int numero = Answer.readInt();
	
		return numero;
	}
	
	//riempie un array lungo n con numeri inseriti dall'utente
	public int[] RiempiArrayInt(int n){
		int[] arint;
		arint= new int[n];
		
		for(int i=0; i<n; i++) {
			arint[i] = ChiediNumero("Inserire il valore " + Integer.toString(i) + "di " + Integer.toString(n));
		}
		return arint;
	}

	//prendi array e restituisci array ordinato
	
	
public static void main(String[] args){
	
}

}
