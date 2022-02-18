
public class smithWaterman {

	protected static int[][] matchingPoints = {{1, -1, -1, -1}, {-1, 1, -1, -1},{-1, -1, 1, -1},{-1, -1, -1, 1}};
	protected static int jumpPenalty = -3;
	protected static int rigaMax = -1, colonnaMax = -1;
	
	
	//associa ogni lettera ad un indice su matcchingPoints, così posso incrociare se sono uguali o no
	// da -1 se la stringa non è sana
	protected static int letterIndex (char c) {
		switch (c) {
			case 'A': return 0;
			case 'T': return 1;
			case 'C': return 2;
			case 'G': return 3;
			default: return -1;
		}
	}
	
	//si assicura che la stringa sia di soli A,T,C,G
	//0 == false, 1 == true, -1 la striga è null o meno di un carattere
	protected static int checkString(String s) {
		if (s==null || s.length() == 0) {
			return -1;
		}
		else {
			boolean flag = true;
			for (int i=0; i< s.length(); i++) {
				if ( s.charAt(i) !='A' || s.charAt(i) !='T' || s.charAt(i) !='C' || s.charAt(i) !='G'){
					flag = false;
					break;
				}
			}
			if (flag) return 1;
			else return 0;
		}
	}
	
	//la matrice sarebbe meglio globale e serve una matrice per le direzioni di arrivo: non è vero che arrivo
	//sempre facendo i salti puù piccoli. credo.
	protected static int [][] compilaMatriceValutazione(String s1, String s2){
		int righe = s1.length()+1, colonne = s2.length()+1;
		int[][] matVal = new int[righe][colonne];
		int valDaSu = 0, valDaSn = 0, valDiag = 0;
		int preceMax = -1;
// L'inizializzazione la faccio a parte perchè risparmio sui controlli di i o j nulle per matrici rettangolari almeno
// 2x2, cosa su cui mi aspetto di lavorare nella stragrande maggioranza dei casi
		//inizializzazione
		for (int i=0; i<righe; i++) {
			matVal[i][0] = 0;
		}
		for (int j=1; j<colonne; j++) { //la prima l'ho già settata prima
			matVal[0][j] = 0;
		}
		//riempimento
		for (int i = 1; i < righe; i++) {	//parto da 1 perchè la prima riga e colonna sono zeri
			for (int j = 1; j < colonne; j++) {
				//la casella i,j è relativa ai caratteri i-1 della s1 e j-1 della s2
				//se la raggiungo da sopra o da sinistra ho fatto un salto
				valDaSu = matVal[i-1][j] + jumpPenalty;
				valDaSn = matVal[i][j-1] + jumpPenalty;
				//se la raggiungo in diagonale devo capire se ho un match o meno
				valDiag = matVal[i-1][j-1] + matchingPoints[letterIndex(s1.charAt(i-1))][letterIndex(s2.charAt(j-1))];
				
				//devo scegliere la direzione di avvicinamento migliore (occhio ho dimenticato che se il massimo è zero devo metterlo)
				if (valDaSu < valDaSn) { //per determinare il massimo faccio così, solo per separare la ricerca nel codice: tanto ho gli stessi confronti ma con meno assegnamenti
					if (valDaSn < valDiag) {		//valDiag è massimo
						matVal[i][j] = valDiag;
					}
					else matVal[i][j] = valDaSn;	//valDaSn è massimo
				}
				else {
					if (valDaSu < valDiag) {		//valDiag è massimo
						matVal[i][j] = valDiag;
					}
					else matVal[i][j] = valDaSu;	//valDaSu è massimo
				}
				// prima di cambiare indici devo salvare la posizione se questo è il massimo valore di sempre
				if(preceMax < matVal[i][j]) {
					preceMax = matVal[i][j];
					rigaMax = i;
					colonnaMax = j;
				}
			}
		}
		
		return matVal;
	}
	
	protected static void traceback (int [][] matriceSW, int rigaInizio, int colonnaInizio) {
		if (matriceSW == null) {
			System.out.println("la matrice chiamata è null!"); //queste verifiche dovrebbero essere inutili
			return;
		}
		if (matriceSW.length == 0  || matriceSW[0].length == 0) {
			System.out.println("la matrice chiamata non ha elementi");
			return;
		}
		//se l'input non è almeno (1,1) mi fermo subito quindi dovrò gestirlo
		//se l'input non è nella matrice idem
		//matrice.length da le righe (ricorda che 2 righe ho indici 0 1 e basta)
		//matrice[i].length da le colonne della riga i-esima, se sono tutte uguali da le colonne.
		if (rigaInizio < 1 || colonnaInizio < 1 || rigaInizio >= matriceSW.length || colonnaInizio >= matriceSW[0].length) {
			System.out.println("Attenzione, indici out of bound ");
			return;
		}
		//TUTTE queste verifiche dovrebbero essere impossibili da soddisfare
		
		//TODO aggiungi il vero corpo del metodo
		/*idealmente voglio confrontare le celle, dal punto di partenza, con quelle in alto, a sinistra
		 * e in diagonale e 
		 * 1- se trovo zero in diagonale stoppo
		 * 2- se no vado nella casella dal punteggio più alto aggiungendo il carattere corrispondente (uso - se salto)
		 * 	(tra una riga e l'altra metto | se caratteri uguali, . se diversi e nulla se ho un - sopra o sotto)
		 * quasi qusi gestirei con un array di char che è più facile da rovesciare quando stampo la sequenza
		 * */
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
