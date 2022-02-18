package robacciaTest;

class Sopra{
	int k = 1;
	
	public String toString() {
		return String.valueOf(k);
	}
}

class Sotto extends Sopra{
	int k = 2;
	
	public String toString() {
		return k + ", " + super.toString();
	}
}

public class adombramento {
	
	public int x = 1;
	
	public void provo() {
		System.out.println("campo x:" + x);
		int x = 3;
		System.out.println(" ora x: " + x);
		System.out.println(" ora this.x: " + this.x);
		
	}
	
	public static void main(String[] args) {
		Sotto var = new Sotto();
		adombramento q = new adombramento();
		System.out.println(var.toString());
		
		q.provo();

	}

}
