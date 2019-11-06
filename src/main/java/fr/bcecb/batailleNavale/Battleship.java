package fr.bcecb.batailleNavale;

import java.util.ArrayList;
import java.util.List;

public class Battleship { //Gère tous les aspects d'une partie, création de la grille, changer l'orientation d'un bateau, le placer, touché/coulé, win condition
    public void initBoard(List<String> board) { //Initialisation de la (liste) grille avec des "o"
        for (int i = 0; i < 100; i++) {
            board.add("o");
        }
    }

    /*
     * @param i
     *
     * Place la premiere lettre du bateau a l'index i dans la liste du bateau en fonction de l'orientation du bateau
     * Exemple pour un bateau A de 3 cases verticales avec un i = 2 :
     *   y
     * x O O A O O O O     - Le premier A est en (2;0)
     *   O O A O O O O
     *   O O A O O O O
     *   O O O O O O O
     *   O O O O O O O
     *
     * Exemple pour un boat B de 3 cases horizontales avec un i = 15 :
     *   y
     * x O O O O O O O
     *   O O O O O O O
     *   O B B B O O O     - Le premier B est en (1;2)
     *   O O O O O O O
     *   O O O O O O O
     *
     * i varie de 0 à 99
     */

    public void putBoat(Boat boat, List<String> board, List<Boat> listBoat, int i) { //Place les bateaux
        int j = i, cpt = 0; //Compte le nombre de case du bateau déjà posé
        if (boat.isOrientation()) { //Si le bateau est horizontal
            if (verification(j, board, boat)) { //Vérification du placement
                while (j < boat.getSizeBoat() + i) {
                    board.set(j, boat.getName()); //On change la lettre dans la liste
                    j++;
                }
                listBoat.add(boat); //Une fois placé, on ajoute le bateau à la liste des bateaux
            }
        } else { //Si le bateau est vertical
            if (verification(j, board, boat)) {
                while (cpt < boat.getSizeBoat()) {
                    board.set(j, boat.getName());
                    cpt++;
                    j += 10;
                }
                listBoat.add(boat);
            }
        }
    }

    public boolean verification(int j, List<String>board, Boat boat) {
        if (board.get(j).equals("o")) { //Si il y a de " l'eau "
            if (boat.isOrientation() && (boat.getSizeBoat() <= tooDeepRight(j)) || (!boat.isOrientation() && boat.getSizeBoat() <= ((90 - j) / 10)+2)) {
                //Bateau Horizontal et ne dépasse pas à droite || Bateau Vertical et ne dépasse pas en bas
                return true;
            }
        }
        return false;
    }

    public int tooDeepRight(int val) {  //Pour ne pas avoir un bateau qui dépasse de la grille à droite
        double temp = (Math.ceil((double) val / 10) * 10);
        return (int) temp;
    }

    public void swapOrientation(Boat boat) { //Change l'orientation du bateau passé en adresse
        if (boat.isOrientation()) {
            boat.setOrientation(false);
        } else {
            boat.setOrientation(true);
        }
    }

    public boolean isTouch(int i, List<String> board, List<Boat> listBoat) { //On réduit la taille du bateau de 1 quand il est touché
        if(board.get(i) != "o"){ //Touché
            touch(board.get(i), listBoat);
            board.set(i,"x");
            return true;
        }else{ //Loupé
            board.set(i,"z");
            return false;
        }
    }

    public void touch(String boatTouch, List<Boat> listBoat){
        for (Boat boat: listBoat) {
            if (boat.getName()==boatTouch){
                boat.setSizeBoat(-1);
                sink(boat, listBoat); //On check si il est coulé
            }
        }
    }

    public boolean sink(Boat boat, List<Boat> listBoat) { //Si la taille du bateau est nulle, il est retiré de la liste puis on check si c'est win
        if (boat.getSizeBoat() == 0) { //Bateau coulé
            boat.setAlive(false);
            for (Boat boat2: listBoat) {
                if (boat2.isAlive()){
                    return false;
                }
            }
            dead();
            return true;
        }
        return false;
    }

    /////////////////////////////////////

    public void affichage(List<String> board) { //A retirer
        for (int i = 0; i < board.size(); i++) {
            if (i % 10 == 0) System.out.println();
            System.out.print(board.get(i));
        }
    }

    public void dead() { //A retirer
        Boat.stream() // Affiche tous les bateaux coulés
                .filter(d -> d.isAlive() == false)
                .forEach(System.out::println);
        System.out.println("gg");
    }
}
