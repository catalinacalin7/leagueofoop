package main;
import java.util.ArrayList;

public class Joc {
    private int n;  // nr linii teren
    private int m;  // nr coloane teren
    private int p;  // nr personaje
    private int r;  // nr runde
    private ArrayList<Hero> juc; // lista ce contine toti jucatorii
    private String[][] teren; // matricea ce reprezinta terenul(contine tipurile de teren)

    public Joc() {
        // initializam toti membrii jocului la crearea acestuia
        this.n = 0;
        this.m = 0;
        this.p = 0;
        this.r = 0;
        this.juc = new ArrayList<Hero>();
        }

    /**
     * metoda ce creaza de teren de dimensiune nxm primiti in urma
     * citirii din fisier.
     */
    public final void creareTeren() {
        this.teren = new String[n][m];
        }

    /**
     * getter pentru lista de jucatori.
     * @return lista de jucatori
     */
    public final ArrayList<Hero> getJuc() {
        return this.juc;
    }

    /**
     * getter pentru teren.
     * @return terenul de joc
     */
    public final String[][] getTeren() {
        return this.teren;
    }

    /**
     * getter pentru o pozitie din teren.
     * @param l - linia
     * @param c - coloana
     * @return tipul de teren ce se afla pe pozitia [l][c]
     */
    public final String getTerenP(final int l, final int c) {
        return this.teren[l][c];
    }

    /**
     * adauga un elem pe pozitia [l][c] in terenul de joc.
     * @param l
     * @param c
     * @param elem
     */
    public final void setTeren(final int l, final int c, final String elem) {
        this.teren[l][c] = elem;
        }

    /**
     * adauga in lista de jucatori un jucator nou.
     * @param hero - jucatorul ce trebuie adaugat
     */
    public final void addPlayer(final Hero hero) {
        this.juc.add(hero);
        }

    /**
     * getter pentru N.
     * @return nr de linii a terenului de joc
     */
    public final int getN() {
        return this.n;
        }

    /**
     * setter pentru N.
     * @param n - seteaza nr de linii a terenului cu n
     */
    public final void setN(final int n) {
        this.n = n;
        }

    /**
     * getter pentru M.
     * @return nr de coloane a terenului de joc
     */
    public final int getM() {
        return this.m;
        }

    /**
     * setter pentru M.
     * @param m - seteaza nr de coloane a terenului cu m
     */
    public final void setM(final int m) {
        this.m = m;
        }

    /**
     * getter pentru P.
     * @return nr de personaje a jocului curent
     */
    public final int getP() {
        return this.p;
        }

    /**
     * setter pentru P.
     * @param p - seteaza nr de personaje a jocului curent cu p
     */
    public final void setP(final int p) {
        this.p = p;
        }

    /**
     * getter pentru R.
     * @return nr de runde a jocului curent
     */
    public final int getR() {
        return this.r;
        }

    /**
     * setter pentru R.
     * @param r - seteaza nr de runde a jocului curent cu r
     */
    public final void setR(final int r) {
        this.r = r;
        }
}
