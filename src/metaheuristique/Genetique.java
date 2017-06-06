/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristique;

import Jeu.Plateau;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Epulapp
 */
public class Genetique {
    static final int POP_SIZE = 10000;
    static final int PROBA_CROSSOVER = 70;
    static final int PROBA_MUTATION = 1;
    static final int NB_GENERATIONS = 10000;
    
    static Random rand = new Random();
    private ArrayList<Plateau> population;
    private int taille;
    
    public Plateau getSolution(int taille)
    {
        population = new ArrayList<Plateau>();
        this.taille = taille;
        generationInitiale();
        for (int i = 0; i < NB_GENERATIONS; i++) {
            //System.out.println("Génération "+(i+1));
            reproduction();
        }
        int min = -1;
        Plateau solution = new Plateau(taille);
        for (Plateau p: population) {
            if (min > p.getValeurSolution() || min < 0) {
                min = p.getValeurSolution();
                solution = p;
            }
        }
        return solution;
    }
    
    private void generationInitiale()
    {
        for (int i = 0; i < POP_SIZE; i++)
        {
            int[] pos = new int[taille];
            for (int c = 0;c < taille; c++) {
                pos[c] = -1;
            }
            int n = 0;
            while (n < taille) {
                int index = rand.nextInt(taille);
                if (pos[index] < 0) {
                    pos[index] = n;
                    n++;
                }
            }
            population.add(new Plateau(taille, pos));
        }
    }
    
    private void reproduction()
    {
        ArrayList<Plateau> populationTirage = new ArrayList<>();
        int maxValeur = 0;
        int total = 0;
        for (Plateau p:population) {
            total += p.getValeurSolution();
            if (maxValeur < p.getValeurSolution()) {
                maxValeur = p.getValeurSolution();
            }
        }
        //System.out.println(total/POP_SIZE);
        for (Plateau p:population) {
            for (int i = 0; i < (maxValeur - p.getValeurSolution() + 1); i++) {
                populationTirage.add(p);
            }
        }
        population = new ArrayList<Plateau>();
        int tailleTirage = populationTirage.size();
        //System.out.println(tailleTirage);
        for (int i = 0; i < POP_SIZE/2; i++)
        {
            //int index = rand.nextInt(taille);
            Plateau p1 = populationTirage.get(rand.nextInt(tailleTirage));
            Plateau p2;
            do {
                p2 = populationTirage.get(rand.nextInt(tailleTirage));
            } while (p2 == p1);
            genereFilles(p1,p2);
        }
    }
    
    private void genereFilles(Plateau p1, Plateau p2)
    {
        int random = rand.nextInt(100);
        if (random < PROBA_CROSSOVER){
            crossover(p1,p2);
        } else if (random < PROBA_CROSSOVER + PROBA_MUTATION) {
            mutation(p1,p2);
        } else {
            population.add(p1);
            population.add(p2);
        }
    }
    
    private void crossover(Plateau p1, Plateau p2)
    {
        //System.out.println("==========\nNouveau Croisement");
        int[] pos1 = p1.getPosDame();
        int[] pos2 = p2.getPosDame();
        //int taille = pos1.length;
        int rand1 = rand.nextInt(taille-1)+1;
        int rand2;
        do {
            rand2 = rand.nextInt(taille-1)+1;
        } while (rand2 == rand1);
        if (rand1+1 > rand2) {
            int buffer = rand1;
            rand1 = rand2;
            rand2 = buffer;
        }
        //System.out.println("Index 1 : "+rand1+" Index 2 : "+rand2);
        int[] buffer1 = new int[taille];
        int[] buffer2 = new int[taille];
        for (int i = 0; i < taille; i++) {
            if (i >= rand1 && i < rand2) {
                buffer1[i] = pos1[i];
                buffer2[i] = pos2[i];
            } else {
                buffer1[i] = -1;
                buffer2[i] = -1;
            }
            
        }
        //System.out.println("Index 1 : "+rand1+" Index 2 : "+rand2);
        //System.out.println("Buffers : "+tableToString(buffer1)+" "+tableToString(buffer2));
        //OX(rand2, pos1, pos2, buffer1, buffer2);
        LOX(pos1, pos2, buffer1, buffer2);
    }
    
    private void OX(int bornesup, int[] pos1, int[] pos2, int[] buffer1, int[] buffer2)
    {
        int currentIndex1 = bornesup;
        int currentIndex2 = bornesup;
        for (int i = bornesup; i < bornesup+taille; i++) {
            int index = i%taille;
            if (tableauContient(pos2[index], buffer1) == false) {
                buffer1[currentIndex1] = pos2[index];
                currentIndex1 = (currentIndex1+1)%taille;
            }
            if (tableauContient(pos1[index], buffer2) == false) {
                buffer2[currentIndex2] = pos1[index];
                currentIndex2 = (currentIndex2+1)%taille;   
            }
        }
        //System.out.println("Base : "+tableToString(pos1)+" Croisement : "+tableToString(buffer1));
        //System.out.println("Base : "+tableToString(pos2)+" Croisement : "+tableToString(buffer2));
        population.add(new Plateau(taille, buffer1));
        population.add(new Plateau(taille, buffer2));
    }
    
    private void LOX(int[] pos1, int[] pos2, int[] buffer1, int[] buffer2)
    {
        int currentIndex1 = 0;
        int currentIndex2 = 0;
        for (int i = 0; i < taille; i++) {
            int index = i;
            if (tableauContient(pos2[index], buffer1) == false) {
                while(buffer1[currentIndex1] > -1) {
                    currentIndex1 ++;
                }
                buffer1[currentIndex1] = pos2[index];
                currentIndex1 ++;
            }
            if (tableauContient(pos1[index], buffer2) == false) {
                while(buffer2[currentIndex2] > -1) {
                    currentIndex2 ++;
                }
                buffer2[currentIndex2] = pos1[index];
                currentIndex2 ++;   
            }
        }
        //System.out.println("Base : "+tableToString(pos1)+" Croisement : "+tableToString(buffer1));
        //System.out.println("Base : "+tableToString(pos2)+" Croisement : "+tableToString(buffer2));
        population.add(new Plateau(taille, buffer1));
        population.add(new Plateau(taille, buffer2));
    }
    
    private void mutation(Plateau p1, Plateau p2) {
        population.add(p1.getRandomVoisin());
        population.add(p2.getRandomVoisin());
    }
    
    private boolean tableauContient(int needle, int[] haystack)
    {
        for (int i=0; i < haystack.length; i++) {
            if (needle == haystack[i]) {
                return true;
            }
        }
        return false;
    }
    
    private String tableToString(int[] table) {
        String retour = "";
        for (int i:table) {
            retour += "["+i+"]";
        }
        return retour;
    }
    
    
}
