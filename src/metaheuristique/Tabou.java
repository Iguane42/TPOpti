/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristique;

import Jeu.Plateau;
import java.util.ArrayList;

/**
 *
 * @author Epulapp
 */
public class Tabou implements IAlgorithme{

    static final int MAXITERATION = 10; // Nombre d'itération maximun
    static final int NBTABOU = 1; // Taille de la liste tabou
        
    @Override
    public Plateau getSolution(Plateau x0) {
        
        Plateau xMin = x0; // Plateau de la meilleure solution
        int fMin = xMin.getValeurSolution(); // Valeur de la meilleure solution
        Plateau xActuel = xMin; // Plateau actuel
        int fActuelle = fMin; // Valeur actuelle
        int i = 0; // Compteur
        ArrayList<Plateau> C = new ArrayList<>();
        ArrayList<Plateau> listeTabou = new ArrayList<>();
        
        do{
            // On récupère tous les voisins
            C = xActuel.getVoisin();
            // On enlève les voisins tabous
            for(Plateau voisin : C){
                for(int j = 0; j < NBTABOU; j++){
                    if(voisin == listeTabou.get(j)){
                        C.remove(voisin);
                    }
                }
            }
            
            // S'il y a des voisins
            if(C != null){
                Plateau meilleurVoisin = xActuel;
                int minSolution = fActuelle;
                int deltaF = 0;
                
                // On prend le meilleur des voisins
                for(Plateau voisin : C){
                    int valeurVoisin = voisin.getValeurSolution();
                    
                    if(C.indexOf(voisin) == 0){
                        meilleurVoisin = voisin;
                        minSolution = voisin.getValeurSolution();
                    } else if(valeurVoisin < minSolution){
                        meilleurVoisin = voisin;
                        minSolution = valeurVoisin;
                    }
                }
                
                // Calcul de deltaF
                deltaF = minSolution - fActuelle;
                
                // Si aucun voisin meilleur que la solution actuelle
                if(deltaF >= 0){
                    // MAJ listetabou
                }
                
                // Si un voisin est meilleur que la solution générale
                if(deltaF < fMin){
                    fMin = minSolution;
                    xMin = meilleurVoisin;
                }
            }
            // On incrémente le compteur
            i++;
        } while((i == MAXITERATION) || C == null);
        
        return xMin;
    }
    
}
