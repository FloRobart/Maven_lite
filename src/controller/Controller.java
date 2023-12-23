package controller;

import metier.Metier;


public class Controller 
{
    private Metier metier;

    /**
     * Enregistre les arguments passés au programme
     * @param args les arguments passés au programme
     */
    public Controller(String[] args)
    {
        this.metier = new Metier(args);
    }


    public static void main(String[] args)
    {
        new Controller(args);
    }
}
