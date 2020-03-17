package main;

public class Rogue extends Hero {
    private int nrBackstab;

    // constante necesare
    private final int viataCnst = 600;  // hp initiala a lui rogue
    private final int hpConst = 40;  // pt cresterea hp la fiecare nivel
    private final int lvlConst2 = 50;  // pentru metoda leveup
    private final int lvlConst1 = 250;  // pentru metoda leveup
    private final int lvlDmgPA = 10;  // coord. cresterea base dmg la paralysis in fct. de level
    private final int lvlDmgBS = 20;  // coord. cresterea base dmg la backstab in fct. de level
    private final int baseDmgBS = 200;  // base damage la Backstab
    private final float landMod = 1.15f;  // land modifier (WOODS)
    private final float extraDmgBS = 1.5f;  // prima lovitura backstab pe teren Woods
    private final float rogModBS = 1.2f;  // rogue race modifier pentru backstab
    private final float knModBS = 0.9f;  // knight race modifier pentru backstab
    private final float wpModBS = 1.25f;  // pyromancer/wizard race modifier pentru backstab
    private final float knModPar = 0.8f;  // knight race modifier pentru paralysis
    private final float pyroModPar = 1.2f;  // pyromancer race modifier pentru paralysis
    private final float wizModPar = 1.25f;  // wizard race modifier pentru paralysis
    private final float rogModPar = 0.9f; // rogue race modifier pentru paralysis
    private final int ultLovBS = 3;  // ultima lovitura inaintea reluarii ciclului(Backstab)
    private final int extraCond = 6;  // conditiile primei lovituri pe terenul Woods
    private final int simpleCond = 3;  // conditiile oricarei lovituri simple
    private final int lvlDOT = 10;  // coordoneaza cresterea dot-ului in functie de lvl atacatorului
    private final int baseDmgPar = 40; // base damage la Paralysis


    public Rogue(final int pozitieL, final int pozitieC) {
        // apelam constructorul clasei parinte
        super(pozitieL, pozitieC);
        // initializam hp,hpMaxim cu viata corespunzatoare nivelului 0
        // si nr loviturii de tip Backstab la 1
        this.nrBackstab = 1;
        this.hp = viataCnst;
        this.hpMaxim = viataCnst;
        }

    /**
     * metode folosita pentru afisare.
     * override
     */
    public String toString() {
        return "R";
    }

    /**.
     * metoda ce urmeaza a fi suprascrisa in clasele copii
     */
    public final void levelUP() {
        if (this.isLevelUp()) {
          // contor - va contoriza nr de niveluri pe care li-a atins eroul curent
            int contor = this.level;
            int oneXP = lvlConst1 + contor * lvlConst2;
            while (oneXP <= this.xp) {
                contor++;
                oneXP = lvlConst1 + contor * lvlConst2;
                }
            this.level = contor;
         // reactualizam hp si hpMaxim pentru nivelul nou
            this.hp = viataCnst + this.level * hpConst;
            this.hpMaxim = viataCnst + this.level * hpConst;
            }
        }

        /**
     * override.
     * metoda necesara pentru Deflectul lui Wizard
     * @param hero - pentru calcularea damage fara race modifiers
     * @param joc - jocul curent
     * @return
     */
  public final int totalDamage(final Hero hero, final Joc joc) {
        // Backstab
        // base damage la backstab
      float damage1 = baseDmgBS + lvlDmgBS * this.level;
        // daca este prima lovitura backstab aplicata
       if (this.nrBackstab == 1) {
            // daca se afla pe teren WOODS se inmulteste dmg cu 1.5
            if (this.ceTeren(joc).equals("W")) {
                damage1 = Math.round(extraDmgBS * damage1);
                }
            }

        // Paralysis
        //base damage
        float damage2 = baseDmgPar + lvlDmgPA * this.level;

        // calculam damage total cu rotunjire
        // daca se afla pe wood
        int damagetot;
        // daca se afla pe terenul WOODS = bonus 15% la dmg
        if (this.ceTeren(joc).equals("W")) {
            damage1 = Math.round(damage1 * landMod);
            damage2 = Math.round(damage2 * landMod);
            damagetot = Math.round(damage1 + damage2);
            } else {
                damage1 = Math.round(damage1);
                damage2 = Math.round(damage2);
                damagetot = Math.round(damage1 + damage2);
                }
        return damagetot;
        }


    /**
     * override.
     * apelata cand are loc lupta dintre this si hero
     * @param hero - victima atacului
     * @param joc - jocul curent
     */
    public final void attack(final Hero hero, final Joc joc) {
        // Backstab
        // base damage la backstab
        float damage1 = baseDmgBS + lvlDmgBS * this.level;
        // in functie de victima se aplica race modifier
        if (hero.toString().equals("R")) {
            damage1 = Math.round(damage1 * rogModBS);
            } else if (hero.toString().equals("K")) {
                damage1 = Math.round(damage1 * knModBS);
                } else if (hero.toString().equals("P")) {
                    damage1 = Math.round(damage1 * wpModBS);
                    } else if (hero.toString().equals("W")) {
                        damage1 = Math.round(damage1 * wpModBS);
                        }

        // odata la trei lovituri sau x1.5
        // sau reluam ciclul
        if (this.nrBackstab == 1) {
            // daca se afla pe WOODS
            if (this.ceTeren(joc).equals("W")) {
                damage1 = Math.round(damage1 * extraDmgBS);
                this.nrBackstab++;
                } else {
                    this.nrBackstab++;
                    }
            // daca a ajuns la a treia lovitura reluam ciclul
            } else if (this.nrBackstab == ultLovBS) {
                this.nrBackstab = 1;
                } else if (this.nrBackstab == 2) {
                    this.nrBackstab++;
                    }

        // Paralysis
        // base damage
        float damage2 = baseDmgPar + lvlDmgPA * this.level;
        // base DOT
        hero.dOT = baseDmgPar + lvlDOT * this.level;
        // daca se afla pe WOOODS
        hero.impMisc = true;

            if (this.ceTeren(joc).equals("W")) {
                // nr de runde in care se aplica DOT si nr de runde de impos. miscare este 6
                hero.nrRundeDot = extraCond;
                hero.nrRundeImpMisc = extraCond;
                } else { // daca nu se afla pe WOODS
                    // nr de runde in care se aplica DOT si nr de runde de impos. miscare este 3
                    hero.nrRundeDot = simpleCond;
                    hero.nrRundeImpMisc = simpleCond;
                    }
            // in functie de victima se aplica race modifier
        if (hero.toString().equals("R")) {
            damage2 = Math.round(damage2 * rogModPar);
            hero.dOT = Math.round(hero.dOT * rogModPar);
            } else if (hero.toString().equals("K")) {
                damage2 = Math.round(damage2 * knModPar);
                hero.dOT = Math.round(hero.dOT * knModPar);
                } else if (hero.toString().equals("P")) {
                    damage2 = Math.round(damage2 * pyroModPar);
                    hero.dOT = Math.round(hero.dOT * pyroModPar);
                    } else if (hero.toString().equals("W")) {
                        damage2 = Math.round(damage2 * wizModPar);
                        hero.dOT = Math.round(hero.dOT * wizModPar);
                        }

        // calculam damage total cu rotunjire
        // daca se afla pe wood
        int damagetot;
        // daca se afla pe WOODS (se adauga la dmg si dot 15% bonus)
        if (this.ceTeren(joc).equals("W")) {
            damage1 = Math.round(damage1 * landMod);
            damage2 = Math.round(damage2 * landMod);
            damagetot = Math.round(damage1 + damage2);
            hero.dOT = Math.round(hero.dOT * landMod);
            } else {
                damage1 = Math.round(damage1);
                damage2 = Math.round(damage2);
                hero.dOT = Math.round(hero.dOT);
                damagetot = Math.round(damage1 + damage2);
                }
                // ii adaugam lui hero damage-ul total primit in urma luptei
                hero.dmgPrim = damagetot;
        }


}
