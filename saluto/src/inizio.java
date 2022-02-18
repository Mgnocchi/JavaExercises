import prog.io.ConsoleOutputManager;
import prog.io.ConsoleInputManager;

class inizio{
	public static void main (String[] args) {
		ConsoleInputManager in = new ConsoleInputManager();
		ConsoleOutputManager out = new ConsoleOutputManager();
		String nome = in.readLine("Inserisci il tuo nome> ");
		out.println("Ciao " + nome + ", ti auguro una buona giornata!");
	}
}
