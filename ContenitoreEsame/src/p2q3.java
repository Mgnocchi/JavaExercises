// ---------- PROGRAMMAZIONE II ---------------------
//              AA 2020-2021
//
// Sistema d'esame - PARZIALE 1
// Domanda 3:             Interpretazione
//
// Questo  programma java  realizza un  algoritmo  di
// confronto tra una stringa passata  come  argomento 
// sulla riga di comando ed una o piu' stringhe conte-
// nute  in  un  file.  Le  stringhe   possono  conte-
// nere solo i caratteri A,C,G,T  (case sensitive).
//
// Per testarne il funzionamento potete (in  Eclipse)
// impostarne i parametri tramite
// Run as -> Run configurations... -> (tab) Arguments:
// inserendo in Program arguments:  AATGCCATTGACGG input.txt
// dove  input.txt  e'  il  nome  di un file di testo
// contenente una o piu' stringhe (una per riga) sen-
// za spazi.
// In questo file potreste (per testare il programma) 
// inserire una sola stringa: CAGCCTCGCTTAG.
//
// La linea di comando corrispondente sarebbe:
//  java -cp "." p2q3 CAGCCTCGCTTAG input.txt
//
// Per compilare, sempre da riga di comando:
// javac -cp "." p2q3.java
//
// --------------- WARNING: -------------------------
//
// - NON cambiate il nome della classe
// - NON aggiungete costruttori
// - Non potete modificare nulla se non per inserire
//   commenti
// 
//
// -------------- CONSEGNA --------------------------
//
// Il seguente programma valuta stringhe confrontandole.
// La logica con cui lo fa non e' riportata  sottoforma
// di commento e il programma stampa poche informazioni.
// Il vostro obiettivo in questo esercizio e' cercare di
// capire come vengono confrontate le stringhe valutate.
//
// Potete commentare liberamente questo sorgente (dovre-
// te sottometterlo) ma non e' consentita la sua modifi-
// ca.
// Oltre ai commenti del codice dovrete sottomettere un
// file di testo avente nome  nummatricola.txt (in  cui
// nummatricola e' il vostro numero di  matricola).  In
// potrete parlare liberamente del programma.
//
// Suggerimenti per la compilazione del file di testo:
// La lunghezza del contenuto di nummatricola non garan-
// tisce un punteggio piu' elevato. Meglio schematizzare:
// Input, output, descrizone  sintetica  elaborazione...
// Dovreste essere inoltre in grado di fornire  una ipo-
// tesi personale riguardante lo scopo del  programma (
// in  questa parte, se lo ritenete utile, potete incol-
// lare parti (*BREVI*) di codice che, secondo voi, sup-
// portano le vostre interpretazioni.
//
// WARNING: lo scopo dell'esercizio NON  E'  quello  di
// identificare l'algoritmo (anche se  e' famoso...) ma
// verificare la vostra capacita' di  estrarre informa-
// zioni da codice eseguibile ma non commentato.
// Quindi ... informazioni tratte da siti web  o  altre
// fonti di informazioni non prenderanno molti punti.
// Al contrario l'impegno nell'interpretare il  codice
// in modo autonomo e nel descriverlo con parole vostre
// prenderanno piu' punti. 
//
// L'algoritmo e' di complessita' medio-alta (per questo
// ne e' vietata la modifica... c'e' il rischio  che non
// compili piu' facendovi perdere tempo). Se  proprio vo-
// lete testare qualche modifica (ma fate attenzione  al
// tempo!) consiglio di provare valori diversi delle cos-
// tanti/variabili alle righe : 84, 93 e 94
 
import java.io.*;
import java.lang.Math;

class p2q3 {
public static final int PENALTY = -4;
public static int[][] simscores = null;
public static int[][] seqcomp = null;
public static int slen_1, slen_2;
public static int bestScore, max_x, max_y;
public static int f_score;
public static String f1, f2;
public static void makeSimM(){	//inizializza simscores
         simscores = new int[ 4 ][ 4 ];
         int eqscore = 3;
         int diffscore = -1;
         simscores[ 0 ][ 0 ] = eqscore;
         simscores[ 1 ][ 0 ] = diffscore;
         simscores[ 2 ][ 0 ] = diffscore;
         simscores[ 3 ][ 0 ] = diffscore;
         simscores[ 0 ][ 1 ] = diffscore;
         simscores[ 1 ][ 1 ] = eqscore;
         simscores[ 2 ][ 1 ] = diffscore;
         simscores[ 3 ][ 1 ] = diffscore;
         simscores[ 0 ][ 2 ] = diffscore;
         simscores[ 1 ][ 2 ] = diffscore;
         simscores[ 2 ][ 2 ] = eqscore;
         simscores[ 3 ][ 2 ] = diffscore;
         simscores[ 0 ][ 3 ] = diffscore;
         simscores[ 1 ][ 3 ] = diffscore;
         simscores[ 2 ][ 3 ] = diffscore;
         simscores[ 3 ][ 3 ] = eqscore;
}

//legge matrice, se true stampa la prima sequenza separando i dati con un tab, altrimenti stampa solo la matrice con eventuali offset degli inidici dipendenti dal flag
public static void printMatrix( int[][] matrix, String seq1, String seq2, int max_x, int max_y, boolean seqcomp ){
         if(seqcomp)
         {
                 for(int x = 0; x < slen_1; x++) //scorre la prima sequenza e stampa i suoi caratteri separati da tab
                         System.out.print( "\t" + seq1.charAt(x) );
                 System.out.println(" ");	//va a capo
         }

         for(int y = (seqcomp ? 1 : 0); y < max_y; y++)
         {
                 System.out.print( seq2.charAt(y - (seqcomp ? 1 : 0) )  + "\t");
                 for(int x = (seqcomp ? 1 : 0); x < max_x; x++)
                 {
                         System.out.print( matrix[x][y] + "\t" );
                 }
                 System.out.println("");
         }
}

public static void calculateMatrix( String seq1, String seq2 ){
         int choices[] = new int[3];
         seqcomp = new int[ slen_1 + 1 ][ slen_2 + 1 ];	//matrice comparativa, di uno più lunga della stringa per l'intestazione
         for(int x = 0; x < slen_1; x++)
                 seqcomp[x][0] = PENALTY * x;	//la prima riga e colonna sono inizializzate ad un valore negativo
         for(int y = 0; y < slen_2; y++)
                 seqcomp[0][y] = PENALTY * y;
         for(int x = 1; x <= slen_1; x++)
                 for(int y = 1; y <= slen_2; y++)
                 {
                         choices[0] = seqcomp[ x - 1 ][ y - 1 ] + simscores[ getScore( seq1, x-1) ][ getScore( seq2, y-1) ]; //prende il valore di "base" del numero in direzione -1/-1 e agiunge il punteggio di uguaglianza o differenza dei caratteri in tale posizione
                         choices[1] = seqcomp[x - 1][y] + PENALTY;	//sono i valori adiacenti (sopra e a sinistra) con penalità
                         choices[2] = seqcomp[x][y - 1] + PENALTY;
                         seqcomp[x][y] = getMax( choices );	//scelgo il massimo
                         if ( seqcomp[x][y] > bestScore )	//se è il migliore aggiorno il punteggio migliore
                         {
                                 bestScore = seqcomp[x][y];
                                 max_x = x;
                                 max_y = y;
                         }
                 }
}
//il punteggio aumenta quanto più continuativamente uguali sono le stringhe. cala per differenze brevi. 

public static int getMax( int[] in ){
         return Math.max( Math.max( in[0], in[1]), Math.max( in[2], 0 ) );
}

public static int getScore(String seq, int index){	//assegna una coordinata ad ogni lettera
         switch (seq.charAt(index))
         {
                         case 'A': return 0;
                         case 'G': return 1;
                         case 'C': return 2;
                         case 'T': return 3;
         }
         return -1;
}

public static void showPath(String s1, String s2, boolean recordBest){ //stampa la stringa che genera il cammino di maggior valore, spostandosi sulla matrice di comparazione
         String new_s1 = "", new_s2 = "";
         int i = slen_1, j = slen_2;
         int score = 0, score_diag = 0, score_up = 0, score_left = 0;        

         while( i > 0 && j > 0 ){
                 score = seqcomp[i][j];
                 score_diag = seqcomp[i - 1][j - 1];
                 score_up = seqcomp[i][j - 1];
                 score_left = seqcomp[i - 1][j]; //guardo gli score nelle tre direzioni "legali"

                 if(score == 0) //se vedo zero mi fermo
                         break;
                 if( score == score_diag + simscores[ getScore( s1, i-1) ][ getScore( s2, j-1) ] ){
                         new_s1 = s1.charAt(i-1) + new_s1;
                         new_s2 = s2.charAt(j-1) + new_s2;
                         i--;
                         j--;
                 }
                 else if( score == score_left + PENALTY ){
                         new_s1 = s1.charAt(i-1) + new_s1;
                         new_s2 = "-" + new_s2;
                         i--;
                 }else if( score == score_up + PENALTY ){
                         new_s1 = "-" + new_s1;
                         new_s2 = s2.charAt(j-1) + new_s2;
                         j--;
                 }
         }
         if(recordBest){
                 f1 = new_s1;
                 f2 = new_s2;
         }
         System.out.println( new_s1 );
         System.out.println( new_s2 );
}

public static void main(String[] args) throws IOException{
         f1 = "";
         f2 = "";
         f_score = 0;

         if( args.length < 2 ){
                 System.out.println( "Usage: java progname string multistringfile." );
                 return;
         }

         BufferedReader linereader;
         try{
                 linereader = new BufferedReader(new FileReader(args[1]));
         }catch(FileNotFoundException e){
                 System.out.println("Errore! File [" + args[1] + "] non trovato!");
                 return;
         }

         String seq1 = "";
         String seq2 = args[0];
         slen_2 = seq2.length();
        
         makeSimM();  //inizializza la matrice: 3 diagonale, -1 altrove      
         System.out.println("\nComparison scores table : ");
         printMatrix(simscores, "    ", "    ", 4, 4, false); //stampa la matrice appena inizializzata

         while( (seq1 = linereader.readLine()) != null){
                 slen_1 = seq1.length();	//salvo la lunghezza della riga e inizializzo i contatori
                 bestScore = 0;
                 max_x = 0;
                 max_y = 0;
        
                 System.out.println("\nStrings evaluation matrix : ");
                 calculateMatrix( seq1, seq2 );
                 printMatrix( seqcomp, seq1, seq2, slen_1 + 1, slen_2 + 1 , true); //stampa la matrice di valutazione con intestazione

                 if( bestScore > f_score ){	//se le stringhe sono abbastanza simili da generare un punteggio positivo 
                         f_score = bestScore;	//stampo il puteggio e la posizione del picco
                         System.out.println("\nScore: " + bestScore + " [" + max_x +"][" + max_y + "]\n");
                         showPath( seq1, seq2, true);
                 } else {
                         System.out.println("\nScore: " + bestScore + " [" + max_x +"][" + max_y + "]\n");
                         showPath( seq1, seq2, false); //altrimenti stampo 0
                 }
         }
         linereader.close();
         System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . .");
         System.out.println("Best Score: " + f_score);
         System.out.println(" " + f1 + "\n " + f2);        
}
}
