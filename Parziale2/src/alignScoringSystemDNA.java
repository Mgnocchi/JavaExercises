import java.util.*;

public class alignScoringSystemDNA{
	public Double match = 1.0d;
	public Double mismatch = - 1.0d/3.0;
	public Double gap = -4.0d/3.0;
	public Map<String,Double> compare = new HashMap<String,Double>();

	//costruttore senza identificatore di visibilità?
	//The data members, class or methods which are not declared using any access modifiers i.e. 
	//having default access modifier are accessible only within the same package.
	alignScoringSystemDNA(){
		String[] nts = {"A","T","C","G"};
		for(String s1 : nts){
			for(String s2 : nts){
				if(s1.equals(s2)){
					this.compare.put(s1+s2, this.match);
				}else{
					this.compare.put(s1+s2, this.mismatch);
				}
			}
		}
		// handle special character 0 derived corner cases (for SW implementation) impossibile nei confronti di
		// Questo è supefluo se si corregge SW. L'unica utilità è quella di non avere una chiamata
		// match e mismatch in SW a causa del carattere 0 aggiunto nelle stringhe.
		// Questo è meglio gestibile, sia semplicemente anticipando il controllo degli zeri,
		// ma soprattutto evitando che possa avvenire tale confronto, sistemando gli indici 
//		this.compare.put("00", -100.0d);
//		this.compare.put("0A", -100.0d);
//		this.compare.put("0T", -100.0d);
//		this.compare.put("0C", -100.0d);
//		this.compare.put("0G", -100.0d);
//		this.compare.put("A0", -100.0d);
//		this.compare.put("T0", -100.0d);
//		this.compare.put("C0", -100.0d);
//		this.compare.put("G0", -100.0d);
		
	}

	/* Questi metodi cambiano gli scoring ma non la matrice associata. 
	 * Sarebbe bello avere un metodo comune al costruttore e questi setter, ma in effetti sarebbe un po' meno efficente.
	 * l'ideale ritengo sarebbe non avere una hash table da settare ma un metodo che prende due stringhe di lunghezza uno 
	 * e confronta i caratteri, in modo da avere una complessità operazionale costante. é vero che conoscendo a priori
	 * la dimensione della hash table anche l'approccio corrente ha, tecnicamente, complessità costante
	public void setMatchScore(double m){
		this.match = m;
	}

	public void setMismatchScore(double m){
		this.mismatch = m;
	}

	public void setGapScore(double g){
		this.gap = g;
	}
	*/
	
	public void setMatchScore(double m){
		this.match = m;
		
		String[] nts = {"A","T","C","G"};
		for(String s1 : nts){
			this.compare.put(s1+s1, this.match); // non serve aggiornare i punteggi di mismatch, che non ho modificato
		}											//basta un indice per scorrere la diagonale
	}

	public void setMismatchScore(double m){
		this.mismatch = m;
		
		String[] nts = {"A","T","C","G"};
		for(String s1 : nts){
			for(String s2 : nts){
				if(!s1.equals(s2)){
					this.compare.put(s1+s2, this.mismatch);
				}
			}
		}
	}

	public void setGapScore(double g){
		this.gap = g;
	}
}
