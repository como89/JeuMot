package jeumot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author como89
 */
public class JeuMot {

     public static final int BONNE_LETTRE = 0;
     public static final int MAUVAISE_LETTRE = 1;
     
    public static void main(String[] args) {
       String[] motOriginaux = new String[]{"chat","chien","maison"};
       String[] indices = new String[]{"Animal","Animal","Logis"};
       int[] compteurLettres = new int[2];
       int chances = 3;
       boolean perdu = false;
       int i = 0;
       while(chances != 0 && i < motOriginaux.length){
           boolean motTrouve = false;
           int nombreLettres = motOriginaux[i].length();
           afficherMot(indices[i],nombreLettres);
           while(!motTrouve && !perdu){
               String ligne = lireReponse();
               int nombreLettresReponse = ligne.length();
               if(nombreLettresReponse != nombreLettres){
                   System.out.println("Le mot n'est pas de la même longueur.");
                   System.out.println("Vous avez perdu une chance.");
                   chances--;
               }
               else {
                   if(verifierReponse(ligne,motOriginaux[i],compteurLettres)){
                       System.out.println("Bravo, vous avez trouver le mot!");
                       motTrouve = true;
                       chances = 3;
                       i++;
                   } else{
                       System.out.println("Le mot " + ligne + " n'est pas le bon mot. Vous avez pourtant marqué " + compteurLettres[BONNE_LETTRE] + (compteurLettres[BONNE_LETTRE] > 2?" bonnes letrres":" bonne lettre")
                        + " et " + compteurLettres[MAUVAISE_LETTRE] + (compteurLettres[MAUVAISE_LETTRE] > 2?" mauvaises lettres.":" mauvaise lettre."));
                       System.out.println("Vous avez perdu une chance.");
                       chances--;
                   }
               }
               
               if(chances == 0){
                   perdu = true;
                   System.out.println("Vous avez perdu la partie! Le bon mot était " + motOriginaux[i] + ".");
               }
               
               if(!motTrouve && !perdu){
                   System.out.println("Il vous reste " + chances + (chances > 2? " chances.":" chance."));
                   System.out.print("Réessayer : ");
               }
               reinitialisation(compteurLettres);
           }
       }
    }

    private static String lireReponse() {
         try {
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             String ligne = br.readLine();
             return ligne.toLowerCase();
         } catch (IOException ex) {
             Logger.getLogger(JeuMot.class.getName()).log(Level.SEVERE, null, ex);
         }
        return "";
    }

    private static void reinitialisation(int[] compteurLettres) {
        compteurLettres[BONNE_LETTRE] = 0;
        compteurLettres[MAUVAISE_LETTRE] = 0;
    }

    private static void afficherMot(String indice, int nombreLettres) {
        System.out.println("==> Vous devez chercher un mot de " + nombreLettres + (nombreLettres > 2? " lettres.":" lettre."));
        System.out.println("INDICE : " + indice);
    }

    private static boolean verifierReponse(String ligne, String motOriginaux, int[] compteurLettres) {
        char[] tabReponse = ligne.toCharArray();
        char[] tabMot = motOriginaux.toCharArray();
        for(int i = 0; i < tabReponse.length; i++){
            if(tabReponse[i] == tabMot[i]){
                compteurLettres[BONNE_LETTRE]++;
            } else {
                compteurLettres[MAUVAISE_LETTRE]++;
            }
        }
        return motOriginaux.length() == compteurLettres[BONNE_LETTRE];
    }
    
}
