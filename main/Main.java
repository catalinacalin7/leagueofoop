package main;
import java.io.IOException;

import fileio.FileSystem;

public abstract class Main {

    public static void main(final String[] args) {
        // TODO Auto-generated method stub
        String input = args[0], output = args[1];
        try {
            // fileSystem care ne permite si scrierea si citirea in acelasi moment
            FileSystem fileSystem = new FileSystem(input, output);

            // instantiez un joc nou
            Joc j = new Joc();
            j.setN(fileSystem.nextInt());  // citim nr de linii a terenului
            j.setM(fileSystem.nextInt());  // citim nr de coloane a terenului
            j.creareTeren();  // cream terenul de joc

            for (int i1 = 0; i1 < j.getN(); i1++) {
                String linieT;  // linie teren
                linieT = fileSystem.nextWord(); // citim o linie
                for (int i2 = 0; i2 < j.getM(); i2++) {
                    // adaugam in terenul de joc pe fiecare pozitie corespunzatoare -tipul de teren
                    j.setTeren(i1, i2,
                    Character.toString(linieT.charAt(i2)));  // folosim Character pentru a salva
                    // tipurile de teren ca stringuri
                    // charAt pentru a accesa cate o litera din linia citita
                    }
                }

            j.setP(fileSystem.nextInt());  // citim nr de personaje

            String litera;
            int x;
            int y;
            for (int i3 = 0; i3 < j.getP(); i3++) {
                // citim informatia despre juc
                litera = fileSystem.nextWord();
                x = fileSystem.nextInt();
                y = fileSystem.nextInt();
                // ii introducem in lista de juc a jocului curent
                if (litera.equals("W")) {
                    j.addPlayer(new Wizard(x, y));
                    } else if (litera.equals("R")) {
                        j.addPlayer(new Rogue(x, y));
                        } else if (litera.equals("P")) {
                            j.addPlayer(new Pyromancer(x, y));
                            } else {
                                j.addPlayer(new Knight(x, y));
                                }
                }


            j.setR(fileSystem.nextInt());  // citim nr de runde
            // de aici se incepe jocul
            //
            // pentru fiecare runda
            for (int a = 0; a < j.getR(); a++) {
                // in fiecare runda citim linia ce corespunde miscarilor jucatorilor
                String runda = fileSystem.nextWord();
                for (int k = 0; k < j.getJuc().size(); k++) {
                    // la incp. de fiecare runda scadem DOT la fiecare jucator(daca este nevoie)
                    j.getJuc().get(k).doTMethod();
                    // facem miscarea fiecarui jucator din fiecare runda;
                    // cazul in care nu se poate misca in runda curenta
                    if (j.getJuc().get(k).impMisc) {  // daca are indicator de imp. miscare
                        j.getJuc().get(k).nrRundeImpMisc--; // decrem. nr de runde de imp. miscare
                        j.getJuc().get(k).move("_");
                        if (j.getJuc().get(k).nrRundeImpMisc == 0) {
                            //daca in urma decrementarii nr de runde devine 0, atunci
                            // si indicatorul de imp miscare devine false, ca sa se poata
                            // misca in runda urmatoare
                            j.getJuc().get(k).impMisc = false;
                            }

                        } else {    // daca se poate misca in runda curenta, folosim metoda move
                            j.getJuc().get(k).move(Character.toString(runda.charAt(k)));
                            }
                    }

                // cautam 2 jucatori ce se afla pe acceasi pozitie in teren si facem atacurile
                for (int t = 0; t < j.getJuc().size() - 1; t++) {
                    for (int h = t + 1; h < j.getJuc().size(); h++) {
                        // parcurgem vectorul si cautam jucatori ce se afla pe aceeasi linie
                        // pe aceeasi coloana si sunt in viata
                        if (j.getJuc().get(t).pozL == j.getJuc().get(h).pozL
                        && j.getJuc().get(t).pozC == j.getJuc().get(h).pozC
                        && !j.getJuc().get(t).isDead && !j.getJuc().get(h).isDead) {
                            // pentru lovitura backstab am nevoie ca wizard sa atace primul
                            if (j.getJuc().get(t).toString().equals("W")) {
                                j.getJuc().get(t).attack(j.getJuc().get(h), j);
                                j.getJuc().get(h).attack(j.getJuc().get(t), j);
                                } else {
                                    j.getJuc().get(h).attack(j.getJuc().get(t), j);
                                    j.getJuc().get(t).attack(j.getJuc().get(h), j);
                                    }
                            // la sfarsit de lupta scadem din hp damage-ul primit in urma luptei
                            j.getJuc().get(t).hp = j.getJuc().get(t).hp - j.getJuc().get(t).dmgPrim;
                            j.getJuc().get(h).hp = j.getJuc().get(h).hp - j.getJuc().get(h).dmgPrim;
                            // daca unul din jucatori a decedat, celuilalt i se adauga puncte XP
                            // si setam flagul isDead celui decedat cu true
                            if (j.getJuc().get(t).hp <= 0) {
                                j.getJuc().get(h).adaugaXP(j.getJuc().get(t));
                                j.getJuc().get(t).isDead = true;
                                }
                            if (j.getJuc().get(h).hp <= 0) {
                                j.getJuc().get(t).adaugaXP(j.getJuc().get(h));
                                j.getJuc().get(h).isDead = true;
                                }
                            // facem level up daca este cazul
                            j.getJuc().get(t).levelUP();
                            j.getJuc().get(h).levelUP();
                            }
                        }
                    }
                }

            // scrierea in fisier
            // parcurgem vectorul de jucatori
            for (int i = 0; i < j.getJuc().size(); i++) {
                // daca jucatorul este decedat
                if (j.getJuc().get(i).isDead) {
                    fileSystem.writeWord(j.getJuc().get(i).toString());
                    fileSystem.writeWord(" dead");
                    fileSystem.writeNewLine();
                    } else {
                        // daca jucatorul este in viata ii afisam toate proprietatile
                        fileSystem.writeWord(j.getJuc().get(i).toString());
                        fileSystem.writeWord(" ");
                        fileSystem.writeInt(j.getJuc().get(i).level);
                        fileSystem.writeWord(" ");
                        fileSystem.writeInt(j.getJuc().get(i).xp);
                        fileSystem.writeWord(" ");
                        fileSystem.writeInt(j.getJuc().get(i).hp);
                        fileSystem.writeWord(" ");
                        fileSystem.writeInt(j.getJuc().get(i).pozL);
                        fileSystem.writeWord(" ");
                        fileSystem.writeInt(j.getJuc().get(i).pozC);
                        fileSystem.writeNewLine();
                        }
                }
            // inchidem fisierul de citire/scriere
            fileSystem.close();
            } catch (IOException e) { // inchidem try
                e.printStackTrace();
                }
        }
}
