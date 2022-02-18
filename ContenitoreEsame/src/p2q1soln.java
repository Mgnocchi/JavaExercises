// ---------- PROGRAMMAZIONE II ---------------------
//              AA 2020-2021
//
// Sistema d'esame - PARZIALE 1
// Domanda 1:             Completamento
//
// Questo  e'  il  template  di una classe java vuota
// in essa dovrete  scrivere UNO  ED  UN SOLO  metodo
// che risponde alla  domanda dell'esercizio che tro-
// vate nella sezione CONSEGNA.
//
// --------------- WARNING: -------------------------
//
// - NON cambiate il nome della classe
// - NON aggiungete costruttori
// - il nome del metodo DEVE essere soln1
// - commentate main() prima di sottomettere
// - gli argomenti ed il tipo di ritorno del metodo
//   vanno scelti in conformita' al testo dell'eser-
//   cizio.
//
// -------------- CONSEGNA --------------------------
// Il seguente programma effettua operazioni su matri-
// ci. In esso troverete parti rimosse  racchiuse tra
// commenti marcati con il placeholder 'YOUR CODE HE-
// RE' . Completate le parti mancanti per rendere il
// programma funzionante. potete utilizzare il metodo
// main incluso per testare la vostra soluzione ma ri-
// cordate di commentarlo prima di sottomettere.
// Il metodo da aggiungere:
// - non riceve parametri in input
// - le matrici di input sono definite internamente
// - stampa le matrici di input (come riferimento)
// - NON stampa la matrice risultante dal calcolo
// - restituisce una *sola* stringa  rappresentante  la
//   matrice calcolata in modo che, se stampata,  abbia
//   il seguente formato (esempio):
//
//  '1 2
//   3 4
//   5 6
//   7 8'
//
// NB: il separatore tra le colonne e' \t

class p2q1soln{

//////// TEST PURPOSE ONLY MAIN METHOD ////////////
// Decomment the  main method in order to test your
// your solution but remember to comment  it before
// submission
//   public static void main(String[] args){
//            String res;
//         res = soln1();
//         System.out.println(">"+res+"<");
//   }
///////////////////////////////////////////////////


   public static String soln1(){
// stringa risultato
String result = "";
        // numero righe
int row = 3;
// numero colonne
int cols = 2;
// dichiarazione matrice risultato
int[][] mres = new int[row][cols];
// definizione matrici
int[][] matrix1 = {{5,4},{8,6},{1,3}};
int[][] matrix2 = {{9,2},{8,6},{3,0}};
// stampa prima matrice
System.out.println("Prima matrice:");
for (int i = 0; i < row; i++){
         for (int j = 0; j < cols; j++){
                 System.out.print(matrix1[i][j]+"\t");
         }
         System.out.println();
}

// stampa seconda matrice
System.out.println("Seconda matrice:");
///////////////////// YOUR CODE HERE ////////////////////////
for(int i = 0; i < row; i++) {
/////////////////////////////////////////////////////////////
         for (int j = 0; j < cols; j++){
                 System.out.print(matrix2[i][j]+"\t");
         }
         System.out.println();
}
// Calcola e stampa prodotto elemento per elemento
System.out.println("Prodotto = ");
for (int i = 0; i < row; i++){
         for (int j = 0; j < cols; j++){
                 //////////////// YOUR CODE HERE ///////////////
                 mres[i][j] = matrix1[i][j] * matrix2[i][j];
                 ///////////////////////////////////////////////
                 result += ("" + mres[i][j] + "\t");
         }
         result = result.substring(0, result.length() - 1);
         result += "\n";
}

return result;

    }


}
