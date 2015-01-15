package jeumot;

import javax.swing.JOptionPane;

/**
 *
 * @author como89
 */
public class JeuMot {

     public static final int BONNE_LETTRE = 0;
     public static final int MAUVAISE_LETTRE = 1;
     
     public static final int JOUER = 0;
     public static final int RENOMMER = 1;
     public static final int QUITTER = 2;
     public static final int CROIX_ROUGE = -1;
     
    public static void main(String[] args) {
       String[] motOriginaux = new String[]{"chat","chien","maison"};
       String[] indices = new String[]{"Animal","Animal","Logis"};
       String nomJoueur = "Joueur";
       int boutonPresse = 0;
       while(boutonPresse != CROIX_ROUGE && boutonPresse != QUITTER){
           boutonPresse = afficherMenu(nomJoueur);
           switch(boutonPresse){
               case JOUER :
                   jouer(motOriginaux,indices);
                   break;
               case RENOMMER :
                   String nom = renommer();
                   if(nom != null){
                       nomJoueur = nom;
                   }
           }
       }
    }
    
    private static String renommer(){
        String nouveauNom = "";
        boolean cancel = false;
        do{
            nouveauNom = JOptionPane.showInputDialog(null, "Veuillez écrire votre nom : ", "Renommer", JOptionPane.QUESTION_MESSAGE);
            
            if(nouveauNom == null){
              cancel = true;
              nouveauNom = "c";
            }
            
            if(nouveauNom.isEmpty()){
                JOptionPane.showMessageDialog(null, "Vous devez écrire quelque chose, un nom vide n'est pas accepté.", "Nom vide", JOptionPane.WARNING_MESSAGE);
            }
            
        }while(!cancel && nouveauNom.isEmpty());
        return cancel?null:nouveauNom;
    }
    
     private static int afficherMenu(String nomJoueur) {
        return JOptionPane.showOptionDialog(null, "Bienvenue sur le menu de JeuMot! \nVotre nom est : " + nomJoueur, "JeuMot" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Jouer","Renommer","Quitter"}, 0);
    }
    
    private static void jouer(String[] motOriginaux, String[] indices){
        int[] compteurLettres = new int[2];
       int chances = 3;
       boolean perdu = false;
       int i = 0;
       while(!perdu && i < motOriginaux.length){
           boolean motTrouve = false;
           int nombreLettres = motOriginaux[i].length();
           while(!motTrouve && !perdu){
               String ligne = afficherMot(indices[i],nombreLettres);
               if(ligne == null){
                   perdu = true;
               } else {
                int nombreLettresReponse = ligne.length();
                if(nombreLettresReponse != nombreLettres){
                    JOptionPane.showMessageDialog(null, "Le mot n'est pas de la même longueur.\n" + "Vous avez perdu une chance." , "Mots de différente longueur", JOptionPane.WARNING_MESSAGE);
                    chances--;
                }
                else {
                    if(verifierReponse(ligne,motOriginaux[i],compteurLettres)){
                        JOptionPane.showMessageDialog(null, "Bravo, vous avez trouvé le mot!", "Bravo", JOptionPane.INFORMATION_MESSAGE);
                        motTrouve = true;
                        chances = 3;
                        i++;
                    } else{
                        JOptionPane.showMessageDialog(null, "Le mot " + ligne + " n'est pas le bon mot. Vous avez pourtant marqué " + compteurLettres[BONNE_LETTRE] + (compteurLettres[BONNE_LETTRE] > 2?" bonnes letrres":" bonne lettre")
                         + " et " + compteurLettres[MAUVAISE_LETTRE] + (compteurLettres[MAUVAISE_LETTRE] > 2?" mauvaises lettres.":" mauvaise lettre.") + "\nVous avez perdu une chance.", "Mauvaise réponse", JOptionPane.WARNING_MESSAGE);
                        chances--;
                    }
                }

                if(chances == 0){
                    perdu = true;
                    JOptionPane.showMessageDialog(null,"Vous avez perdu la partie! Le bon mot était " + motOriginaux[i] + ".","Partie perdu",JOptionPane.ERROR_MESSAGE);
                }

                if(!motTrouve && !perdu){
                    JOptionPane.showMessageDialog(null,"Il vous reste " + chances + (chances > 2? " chances.":" chance."),"Nombre de chances restantes",JOptionPane.WARNING_MESSAGE);
                }
                reinitialisation(compteurLettres);
            }
           }
       }
    }

    private static void reinitialisation(int[] compteurLettres) {
        compteurLettres[BONNE_LETTRE] = 0;
        compteurLettres[MAUVAISE_LETTRE] = 0;
    }

    private static String afficherMot(String indice, int nombreLettres) {
        return JOptionPane.showInputDialog(null, "Vous devez chercher un mot de " + nombreLettres + (nombreLettres > 2? " lettres.":" lettre.") + "\nINDICE : " + indice, "Cherchez un mot", JOptionPane.QUESTION_MESSAGE);
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
