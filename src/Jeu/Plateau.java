/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

/**
 *
 * @author Epulapp
 */
public class Plateau {
    
    private int nbDame;
    private int taille;
    private int[] posDame;
    
    public Plateau(int nbDame){
        this.nbDame = nbDame;
        this.taille = nbDame; // Car la taille du plateau est égale à nXn
        this.posDame = new int[this.nbDame]; // Initialise le tableau des positons
        
        // Initialise le tableau des positions des dames en diagonale (haut gauche vers bas droit)
        for(int i = 0; i < this.nbDame; i++){
            this.posDame[i] = i;
        }
    }
    
    
    public int getValeurSolution()
    {
        int nbConflits = 0;
        for(int i = 0; i<taille; i++)
        {
            int nbDamesSens1 = 0;
            int nbDamesSens2 = 0;
            int nbDamesSens3 = 0;
            int nbDamesSens4 = 0;
            for (int n = 0; n<=i; n++)
            {
                if (posDame[i-n] == n) {
                    nbDamesSens1 ++;
                }
                if (i < taille-1 && posDame[taille-1-i+n] == n) {
                    nbDamesSens2 ++;
                }
                if (posDame[i-n] == taille-1-n) {
                    nbDamesSens3 ++;
                }
                if (i < taille-1 && posDame[taille-1-i+n] == taille-1-n) {
                    nbDamesSens4 ++;
                }
            }
            if (nbDamesSens1 > 1) {
                nbConflits += (nbDamesSens1 - 1);
            }
             if (nbDamesSens2 > 1) {
                nbConflits += (nbDamesSens2 - 1);
            }
              if (nbDamesSens3 > 1) {
                nbConflits += (nbDamesSens3 - 1);
            }
               if (nbDamesSens4 > 1) {
                nbConflits += (nbDamesSens4 - 1);
            }
        }
        return nbConflits;
    }
    
    
}
