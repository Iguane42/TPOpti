/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpopti;

import Jeu.Plateau;
import metaheuristique.RecuitSimule;
import metaheuristique.Genetique;
import metaheuristique.Tabou;

/**
 *
 * @author Epulapp
 */
public class TPOpti {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //Plateau p = new Plateau(500);
//        Genetique rs = new Genetique();
//        Plateau sol = rs.getSolution(10);

        Plateau p = new Plateau(400);
        Tabou rs = new Tabou();
        Plateau sol = rs.getSolution(p);
        System.out.println(p.getValeurSolution());
        sol.printPosDame();
        System.out.println(sol.getValeurSolution());
    }
    
}
