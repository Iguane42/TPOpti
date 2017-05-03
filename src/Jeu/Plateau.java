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
    
    
}
