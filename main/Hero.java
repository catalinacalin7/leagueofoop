package main;


public class Hero {  // clasa parinte a tuturor tipurilor de eroi
    protected int pozL;  // linia pe care se afla eroul
    protected int pozC;  // coloana pe care se afla eroul
    protected int xp;  // XP-ul eroului
    protected int hp;  // HP-ul eroului
    protected int dmgPrim;  // damage-ul primit in urma unei lupte
    protected int level;  // nivelul curent eroului
    protected int hpMaxim;  // HP maxim posibil la nivelul curent
    protected boolean isDead;  // indicator de moarte
    protected int nrRundeDot;  // decrementam la inceput de fiecare runda
    protected int dOT;  // scadem din HP la inceput de fiecare runda noua
    protected boolean impMisc;   // imposibilitate miscare pt Slam(Knight)
    protected int nrRundeImpMisc;  // daca se afla in paralizare

    // constante
    private final int lvlConst1 = 200;
    private final int lvlConst2 = 40;
    private final int lvlUpConst1 = 250;
    private final int lvlUpConst2 = 50;



    public Hero(final int pozL, final int pozC) {
        // la crearea personajului, declaram starile initiale a lui
        this.hp = 0;
        this.level = 0;
        this.impMisc = false;
        this.dOT = 0;
        this.nrRundeDot = 0;
        this.pozC = pozC;
        this.pozL = pozL;
        this.isDead = false;
        }


    /**
     *  adaugaXP.
     * @param hero - in dependenta de level-ul celui ucis
     */
    public final void adaugaXP(final Hero hero) {
        this.xp = this.xp + Math.max(0, lvlConst1 - (this.level - hero.level) * lvlConst2);
        }


    /**
     * dotMethod.
     * se foloseste la inceputul fiecarei runde
     */
    public final void doTMethod() {
        // daca avem overtime damage
        if (!this.isDead) {
            // scadem din viata dOT
            if (this.dOT > 0) {
                this.hp = this.hp - this.dOT;
                // decrementam nr de runda in care trebuie de facut dot
                nrRundeDot--;
                }
            // daca in urma decrementarii, nr de runde egal cu 0
            if (this.nrRundeDot == 0) {
                // atunci setam dOT la 0
                this.dOT = 0;
                }
            // daca a murit in urma DoT
            if (this.hp <= 0) {
                this.isDead = true;
                }
            }
        }


    /**
     * override pentru toString.
     * metoda folosita la afisare
     */
    public String toString() {
        return "";
        }


    /**
     * isLevelUp().
     * verifica daca a ajuns la limita de XP necesara pt levelUP
     * @return true daca a ajuns, false daca nu a ajuns
     */
    public final boolean isLevelUp() {
        if (this.xp >= lvlUpConst1 + this.level * lvlUpConst2) {
            return true;
            }
        return false;
        }


    /**.
     * metoda ce urmeaza a fi suprascrisa in clasele copii
     */
    public void levelUP() {
        }


    /**
     * metoda ce urmeaza a fi suprascrisa in clasele copii.
     * necesara pentru Deflectul lui Wizard
     * @param hero - pentru calcularea damage fara race modifiers
     * @param joc - jocul curent
     * @return
     */
    public int totalDamage(final Hero hero, final Joc joc) {
        return 0;
        }


    /**
     * metoda ce urmeaza a fi suprascrisa in clasele copii.
     * apelata cand are loc lupta
     * @param hero - victima atacului
     * @param joc - jocul curent
     */
    public void attack(final Hero hero, final Joc joc) {
        }


    /**.
     * metoda ce arata pe ce tip de teren se afla
     * eroul ce o apeleaza
     * @param joc -jocul curent
     * @return
     */
    public final String ceTeren(final Joc joc) {
        return joc.getTerenP(pozL, pozC);
        }

    /**.
     * metoda pentru efectuarea miscarilor in fiecare runda
     * @param litera - miscarea citita din fisier primita ca parametru
     */
    public final void move(final String litera) {
        if (litera.equals("U")) {
            this.pozL--;
            } else if (litera.equals("D")) {
                this.pozL++;
                } else if (litera.equals("L")) {
                    this.pozC--;
                    } else if (litera.equals("R")) {
                        this.pozC++;
                        }
        }

}
