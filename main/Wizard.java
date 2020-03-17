package main;

public class Wizard extends Hero {

    // constante necesare
    private final int viataCnst = 400;  // constanta de viataCnst
    private final int lvlUpConst2 = 50;  //pentru metoda levelup
    private final int lvlUpConst1 = 250;  //pentru metoda levelup
    private final int hpConst = 30;  // pt cresterea hp la fiecare nivel
    private final float procDrain = 0.2f; // constanta de procent pentru drain
    private final float lvlProcMod = 0.05f; // level procent modifier
    private final float rogModDr = 0.8f; // rogue modifier pentru drain procent
    private final float knModDr = 1.2f;  // knight modifier pentru drain procent
    private final float pyroModDr = 0.9f;  // pyromancer modifier pentru drain procent
    private final float wizModDr = 1.05f;  // wizard modifier pentru drain procent
    private final float cnstDrain = 0.3f;
    private final float minProcDefl = 0.35f;  // procentul minim de la deflect
    private final float lvlProcDefl = 0.02f;  // coord. cresterea proc. de la deflect in fct de lvl
    private final float maxProcDefl = 0.7f;  // maximul procent ce poate fi la deflect
    private final float knModDefl = 1.4f; // knight modifier pentru deflect
    private final float rogModDefl = 1.2f; // rogue modifier pentru deflect
    private final float pyroModDefl = 1.3f; // pyromancer modifier pentru deflect
    private final float landMod = 1.1f;  // land modifier (DESERT)

    public Wizard(final int pozitieL, final int pozitieC) {
        // apelam constructorul clasei parinte
        super(pozitieL, pozitieC);
        // initializam hp si hpMaxim cu viata corespunzatoare nivelului 0
        this.hp = viataCnst;
        this.hpMaxim = viataCnst;
        }

    /**
     * metode folosita pentru afisare.
     * override
     */
    public String toString() {
        return "W";
    }

    /**.
     * metoda ce urmeaza a fi suprascrisa in clasele copii
     */
    public final void levelUP() {
        if (this.isLevelUp()) {
            // contor - va contoriza nr de niveluri pe care li-a atins eroul curent
            int contor = this.level;
            int oneXP = lvlUpConst1 + contor * lvlUpConst2;
            while (oneXP <= this.xp) {
                contor++;
                oneXP = lvlUpConst1 + contor * lvlUpConst2;
                }
            this.level = contor;
            // reactualizam hp si hpMaxim pentru nivelul nou
            this.hp = viataCnst + this.level * hpConst;
            this.hpMaxim = viataCnst + this.level * hpConst;
            }
        }

    /**
     * override.
     * apelata cand are loc lupta dintre this si hero
     * @param hero - victima atacului
     * @param joc - jocul curent
     */
    public final void attack(final Hero hero, final Joc joc) {
        // Drain
        float damage1 = 0;
        // base damage ptu drain
        float procent = procDrain + lvlProcMod * this.level;
        // in functie de victima, aplicam race modifier
        if (hero.toString().equals("R")) {
            procent = procent * rogModDr;
            } else if (hero.toString().equals("K")) {
                procent = procent * knModDr;
                } else if (hero.toString().equals("P")) {
                    procent = procent * pyroModDr;
                    } else if (hero.toString().equals("W")) {
                        procent = procent * wizModDr;
                        }


        // calculam damage primit de la drain
        if (cnstDrain * hero.hpMaxim <= hero.hp) {
            damage1 = procent * cnstDrain * hero.hpMaxim;
            } else {
                damage1 = procent * hero.hp;
                }

        // Deflect

        float damage2 = 0;

        // daca hero e Wizard, nu facem deflect
        //if (!(hero instanceof Wizard)) {
             // daca nu a ajuns la maximul de 70%
        if (!(hero.toString().equals("W"))) {
            if (minProcDefl + lvlProcDefl * this.level <= maxProcDefl) {
                damage2 = (minProcDefl + (lvlProcDefl * this.level))
                    * hero.totalDamage(this, joc);
                } else { // daca a ajuns la maximul de 70%
                    damage2 = maxProcDefl * hero.totalDamage(this, joc);
                    }
            // in functie de victima, aplicam race modifiers
            if (hero.toString().equals("R")) {
                damage2 = Math.round(damage2 * rogModDefl);
                } else if (hero.toString().equals("K")) {
                    damage2 = Math.round(damage2 * knModDefl);
                    } else if (hero.toString().equals("P")) {
                        damage2 = Math.round(damage2 * pyroModDefl);
                        } else {
                            damage2 = Math.round(damage2);
                            }
            }

        // calculam damage total cu rotunjire
        // daca se afla pe desert
        int damagetot;
        // daca se afla pe DESERT(primeste 10% bonus la damage)
        if (this.ceTeren(joc).equals("D")) {
            damage1 = Math.round(damage1 * landMod);
            damage2 = Math.round(damage2 * landMod);
            damagetot = Math.round(damage1 + damage2);
            } else {
                damage1 = Math.round(damage1);
                damage2 = Math.round(damage2);
                damagetot = Math.round(damage1 + damage2);
                }
        // ii adaugam lui hero damage-ul total primit in urma luptei
        hero.dmgPrim = damagetot;
        }
}
