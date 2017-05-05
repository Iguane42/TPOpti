/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristique;

import Jeu.Plateau;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Epulapp
 */
public class RecuitSimule implements IAlgorithme{
    static final float T0 = 4.5f;
    static final float MICRO = 0.6f;
    static final int MAX_ITERATIONS = 10;
    static final int MAX_TEMP_DECREASE = 10;
    
    static Random rand = new Random();
    
    @Override
    public Plateau getSolution(Plateau x0) {
        Plateau xMin = x0;
        Plateau xi = x0;
        float t = T0;
        int fMin = xMin.getValeurSolution();
        for (int k=0; k<MAX_TEMP_DECREASE; k++) {
            for (int i=1; i <= MAX_ITERATIONS; i++) {
                List<Plateau> voisins = xi.getVoisin();
                Plateau y = voisins.get(rand.nextInt(voisins.size()));
                int delta = y.getValeurSolution() - xi.getValeurSolution();
                if (delta <= 0) {
                    xi = y;
                    if (xi.getValeurSolution() < fMin)
                    {
                        fMin = xi.getValeurSolution();
                        xMin = xi;
                    }
                } else {
                    float p = rand.nextFloat();
                    if (p<=Math.exp(-delta/t)) {
                        xi = y;
                    }
                }
            }
            t = MICRO * t;
        }
        return xMin;
    }
    
}
