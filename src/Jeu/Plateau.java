/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Epulapp
 */
public class Plateau {
    
    private int nbDame;
    private int taille;
    private int[] posDame;
    private int valeur = -1;
    private static Random rand = new Random();
    private int[] permutation;
    
    public Plateau(int nbDame){
        this.nbDame = nbDame;
        this.taille = nbDame; // Car la taille du plateau est égale à nXn
        this.posDame = new int[this.nbDame]; // Initialise le tableau des positons
        
        // Initialise le tableau des positions des dames en diagonale (haut gauche vers bas droit)
        for(int i = 0; i < this.nbDame; i++){
            this.posDame[i] = i;
        }
        
        // Ajout Xavier
//        this.printPosDame();
//        System.out.println("Nombre de voisins : " + this.getVoisin().size());
    }
    
    public Plateau(int nbDame, int[] posDame){
        this.nbDame = nbDame;
        this.taille = nbDame; // Car la taille du plateau est égale à nXn
        this.posDame = new int[posDame.length]; // Initialise le tableau des positons
        
        // Copie la position des dames passée en paramètre
        for(int i = 0; i < this.nbDame; i++){
            this.posDame[i] = posDame[i];
        }
        
        
    }
    
    public ArrayList<Plateau> getVoisin(){
        ArrayList<Plateau> voisins = new ArrayList<>();
        
        for(int i = 0; i < this.nbDame; i++){
            for(int n = i+1; n < this.nbDame; n++){
                int[] posSolution/* = new int[this.nbDame]*/;
                
                posSolution = this.getPosDame().clone();
                posSolution[i] = this.getPosDame()[n];
                posSolution[n] = this.getPosDame()[i];
                
                Plateau plateauVoisin = new Plateau(this.nbDame, posSolution);
                plateauVoisin.permutation = new int[]{i, n};
                //plateauVoisin.printPosDame();
                voisins.add(plateauVoisin);
            }
        }
        
        return voisins;
    }
    
    public Plateau getRandomVoisin()
    {
        int x1 = rand.nextInt(taille);
        int x2 = rand.nextInt(taille);
        while (x1 == x2) {
            x2 = rand.nextInt(taille);
        }
        int[] buffer = getPosDame().clone();
        buffer[x1] = getPosDame()[x2];
        buffer[x2] = getPosDame()[x1];
        return new Plateau(taille, buffer);
    }

//    public ArrayList<Plateau> getVoisin(ArrayList<Plateau> listeTabou){
//        ArrayList<Plateau> voisins = new ArrayList<>();
//        
//        for(int i = 0; i < this.nbDame; i++){
//            for(int n = i+1; n < this.nbDame; n++){
//                if(!listeTabou.isEmpty()){
//                    for(int j = 0; j < listeTabou.size(); j++){
//                        int[] pTabou = listeTabou.get(j).getPermutation();
//                        if((pTabou[0] == i && pTabou[1] == n) || (pTabou[0] == n && pTabou[1] == i)){
//                        } else{
//                            
//                            int[] posSolution = new int[this.nbDame];
//
//                            posSolution = this.posDame.clone();
//                            posSolution[i] = this.posDame[n];
//                            posSolution[n] = this.posDame[i];
//
//                            Plateau plateauVoisin = new Plateau(this.nbDame, posSolution);
//                            plateauVoisin.permutation = new int[]{i, n};
//                            //plateauVoisin.printPosDame();
//                            voisins.add(plateauVoisin);
//                        }
//                    }
//                } else{
//                    int[] posSolution = new int[this.nbDame];
//
//                    posSolution = this.posDame.clone();
//                    posSolution[i] = this.posDame[n];
//                    posSolution[n] = this.posDame[i];
//
//                    Plateau plateauVoisin = new Plateau(this.nbDame, posSolution);
//                    plateauVoisin.permutation = new int[]{i, n};
//                    //plateauVoisin.printPosDame();
//                    voisins.add(plateauVoisin);
//                }   
//            }
//        }
//        
//        return voisins;
//    }

    
    public void printPosDame(){
        String tab = "[";
        for(int i = 0; i < this.nbDame; i++){
            tab += this.getPosDame()[i];
            if(i < this.nbDame-1){
                tab += ",";
            }
        }
        tab += "]";
        System.out.println(tab);
    }
    
    public int getValeurSolution()
    {
        if (valeur < 0) {
            int nbConflits = 0;
            for(int i = 0; i<taille; i++)
            {
                int nbDamesSens1 = 0;
                int nbDamesSens2 = 0;
                int nbDamesSens3 = 0;
                int nbDamesSens4 = 0;
                for (int n = 0; n<=i; n++)
                {
                    if (getPosDame()[i-n] == n) {
                        nbDamesSens1 ++;
                    }
                    if (i < taille-1 && getPosDame()[taille-1-i+n] == n) {
                        nbDamesSens2 ++;
                    }
                    if (getPosDame()[i-n] == taille-1-n) {
                        nbDamesSens3 ++;
                    }
                    if (i < taille-1 && getPosDame()[taille-1-i+n] == taille-1-n) {
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
            valeur = nbConflits;
        }
        
        return valeur;
    }

    /**
     * @return the posDame
     */
    public int[] getPosDame() {
        return posDame;
    }

    /**
     * @param posDame the posDame to set
     */
    public void setPosDame(int[] posDame) {
        this.posDame = posDame;
    }
    /**
     * @return the permutation
     */
    public int[] getPermutation() {
        return permutation;
    }
    
    
}
