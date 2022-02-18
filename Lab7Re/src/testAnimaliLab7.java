import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

abstract class Animal {

	public abstract void makeNoise();
	
	public void eat() {
		System.out.println( this.getClass() + " says: Nom Nom Nom");
	}
	
	public void sleep(){
		System.out.println("zzzzz");
	}
	
	public abstract void roam();
	
}

abstract class Feline extends Animal{
	
	@Override
	public void roam(){
		System.out.println("passo felino!");
	}
	
	public abstract void makeNoise();
	
}

abstract class Canine extends Animal{
	
	@Override
	public void roam(){
		System.out.println("passo canino!(?)");
	}
	
	public abstract void makeNoise();
	
}


class Lion extends Feline {

	public void makeNoise() {
		System.out.println("Roar!");
	}
	
	public void eat() {
		System.out.println(this.getClass() + " says: Nom Nom Nom le zebre!"); 
	}
}


class Tiger extends Feline {

	public void makeNoise() {
		System.out.println("Verso della Tigre!");
	}
	
	public void eat() {
		System.out.println(this.getClass() + " says: Nom Nom Nom quello che mangiano le Tigri!"); 
	}

}

class Cat extends Feline {

	public void makeNoise() {
		System.out.println("Miao!");
	}
	
	public void eat() {
		System.out.println(this.getClass() + " says: Nom Nom Nom i piccoli uccelli e roditori!"); 
	}
	
}


class Wolf extends Canine {

	public void makeNoise() {
		System.out.println("Awoo!");
	}
	
	public void eat() {
		System.out.println(this.getClass() + " says: Nom Nom Nom i cervi ed il bestiame di piccola taglia!"); 
	}
	
}


class Dog extends Canine {

	public void makeNoise() {
		System.out.println("Bau!");
	}
	
	public void eat() {
		System.out.println(this.getClass() + " says: Nom Nom Nom i biscottini!"); 
	}
	
}

class testLab7 {

	public static void main(String[] args) {
		Animal animalo = null;
		String choice = "";
		BufferedReader lettore = new BufferedReader(new InputStreamReader(System.in));
		
		
		while (animalo == null) {
			System.out.println("Scegli un animale:");
			System.out.println("1) Leone");
			System.out.println("2) Tigre");
			System.out.println("3) Gatto");
			System.out.println("4) Lupo");
			System.out.println("5) Cane");
			try {
				choice = lettore.readLine(); //leggo da tastiera
				if (choice.equals("1")) {
					animalo = new Lion();
				}
				else if (choice.equals("2")) {
					animalo = new Tiger();
				}
				else if (choice.equals("3")) {
					animalo = new Cat();
				}
				else if (choice.equals("4")) {
					animalo = new Wolf();
				}
				else if (choice.equals("5")) {
					animalo = new Dog();
				}
				else{
					System.out.println("Scegliere un solo carattere tra 1, 2, 3, 4 o 5");
					break;
				}
			}//try
			catch (IOException e) {
				System.out.println("Non riesco a leggere la linea di comado :( ");
				e.printStackTrace();
			}
		}//while animalo == null
		
		System.out.println("animalo is a " + animalo.getClass());
		animalo.eat();
		animalo.makeNoise();
		animalo.roam();
		animalo.sleep();
		
	}
}