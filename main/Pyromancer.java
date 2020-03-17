package main;

public class Pyromancer extends Hero {

    // constante
    private final int viataCnst = 500;  // constanta de viata
    private final int hpCnst = 50;  // pt cresterea hp la fiecare nivel
    private final int lvlUpConst2 = 50;  // pentru metoda levelup
    private final int lvlUpConst1 = 250;  // pentru metoda levelup
    private final int baseDmgFire = 350;  // base damage la fireblast
    private final int lvlCnstFire = 50;  // coord. cresterea base dmg la fireblast in fct. de level
    private final int baseDmgIg = 150;  // base damage la ignite
    private final int lvlDmgIg = 20;  // coord. cresterea base dmg la ignite in fct. de level
    private final float landMod = 1.25f;  // land modifier (VOLCANIC)
    private final float rogMod = 0.8f;  // rogue race modifier
    private final float knightMod = 1.2f;  // knight race modifier
    private final float pyroMod = 0.9f;  // pyromancer race modifier
    private final float wizMod = 1.05f;  // wizard race modifier
    private final int dotLvlConst = 30;  // coord. cresterea dot-ului in functie de lvl atacatorului

    public Pyromancer(final int pozitieL, final int pozitieC) {
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
        return "P";
    }


    /**
     * levelUP pentru incrementarea daca este nevoie a level.
     */
    public void levelUP() {
        if (this.isLevelUp()) {
            int contor = this.level;
            int oneXP = lvlUpConst1 + contor * lvlUpConst2;
            while (oneXP <= this.xp) {
                contor++;
                oneXP = lvlUpConst1 + contor * lvlUpConst2;
                }
            this.level = contor;
            this.hp = viataCnst + this.level * hpCnst;
            this.hpMaxim = viataCnst + this.level * hpCnst;
            }
        }

    /**
     * override.
     * metoda necesara pentru Deflectul lui Wizard
     * @param hero - pentru calcularea damage fara race modifiers
     * @param joc - jocul curent
     * @return
     */
    public int totalDamage(final Hero hero, final Joc joc) {
        // Fireblast
        float damage1 = baseDmgFire + lvlCnstFire * this.level;
        // Ignite
        float damage2 = baseDmgIg + lvlDmgIg * this.level;

        // calculam damage total cu rotunjire
        // daca se afla pe volcanic
        int damagetot;
        // daca this se afla pe VOLCANIC(primeste 25% bonus damage)
        if (this.ceTeren(joc).equals("V")) {
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
    public void attack(final Hero hero, final Joc joc) { //returnam damage
        float damage1 = 0;
        // Fireblast
        // base damage pentru fireblast
        damage1 = baseDmgFire + lvlCnstFire * this.level;

        // Ignite
        // base damage pentru ignite
        float damage2 = baseDmgIg + lvlDmgIg * this.level;

        // pt overtime damage
        // base DOT
        hero.dOT = lvlCnstFire + dotLvlConst * this.level;
        // nr de runde in care se aplica DOT
            hero.nrRundeDot = 2;
        // in functie de victima, aplicam race modifier
        if (hero.toString().equals("R")) {
            hero.dOT = Math.round(hero.dOT * rogMod);
            damage1 = Math.round(damage1 * rogMod);
            damage2 = Math.round(damage2 * rogMod);
            } else if (hero.toString().equals("K")) {
                damage1 = Math.round(damage1 * knightMod);
                damage2 = Math.round(damage2 * knightMod);
                hero.dOT = Math.round(hero.dOT * knightMod);
                } else if (hero.toString().equals("P")) {
                    damage1 = Math.round(damage1 * pyroMod);
                    damage2 = Math.round(damage2 * pyroMod);
                    hero.dOT = Math.round(hero.dOT * pyroMod);
                    } else if (hero.toString().equals("W")) {
                        damage1 = Math.round(damage1 * wizMod);
                        damage2 = Math.round(damage2 * wizMod);
                        hero.dOT = Math.round(hero.dOT * wizMod);
                        }
        // calculam damage total cu rotunjire
        // daca se afla pe VOLCANIC(primeste 25% bonus damage)
        int damagetot;
        if (this.ceTeren(joc).equals("V")) {
            //damage = (damage1 + damage2) * landMod;
            damage1 = Math.round(damage1 * landMod);
            damage2 = Math.round(damage2 * landMod);
            hero.dOT = Math.round(hero.dOT * landMod);
            damagetot = Math.round(damage1 + damage2);
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
