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

    static final int MAXITERATION = 1000; // Nombre d'itération maximun
    static final int NBTABOU = 5; // Taille de la liste tabou
        
    @Override
    public Plateau getSolution(Plateau x0) {
        
        Plateau xMin = x0; // Plateau de la meilleure solution
        int fMin = xMin.getValeurSolution(); // Valeur de la meilleure solution
        int i = 0; // Compteur
        
        Plateau xActuel = xMin; // Plateau actuel
        int fActuelle = fMin; // Valeur actuelle
        
        ArrayList<Plateau> C = new ArrayList<>();
        ArrayList<Plateau> listeTabou = new ArrayList<>(); // Stocke des plateaux mais seule la permutation nous interesse
        
        int pct = 0; // POUR TEST
        
        do{
            // On récupère tous les voisins
            C = xActuel.getVoisin();
            
            // On enlève les permutations tabous
            if(!listeTabou.isEmpty()){
                //for(Plateau voisin : C){
                for(int v = 0; v < C.size(); v++){
                    int[] pV = C.get(v).getPermutation();
                    for(int j = 0; j < listeTabou.size(); j++){
                        int[] pT = listeTabou.get(j).getPermutation();
                        if((pV[0] == pT[0] && pV[1] == pT[1]) || (pV[0] == pT[1] && pV[1] == pT[0])){
                            j = listeTabou.size() +1;
                            C.remove(v);
                        }
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
                        minSolution = valeurVoisin;
                    } else if(valeurVoisin < minSolution){
                        meilleurVoisin = voisin;
                        minSolution = valeurVoisin;
                    }
                }
                
                // Calcul de deltaF
                deltaF = minSolution - fActuelle;
                
                // Si aucun voisin meilleur que la solution actuelle
                if(deltaF >= 0){
                    if(listeTabou.size() >= NBTABOU){
                        listeTabou.remove(0);
                        listeTabou.add(meilleurVoisin);
                    } else{
                        listeTabou.add(meilleurVoisin);
                    }
                }
                
                // Si un voisin est meilleur que la solution générale
                if(minSolution < fMin){
                    fMin = minSolution;
                    xMin = meilleurVoisin;
                }
                
                xActuel = meilleurVoisin;
                fActuelle = minSolution;
            }
            // On incrémente le compteur
            i++;
            
            if((i*100/MAXITERATION) != pct){
                pct = (i*100/MAXITERATION);
                System.out.println(pct + "%");
            }
        } while((i <= MAXITERATION) && (C.size() != 0) && (fActuelle != 0));
        
        return xMin;
    }
    
}
