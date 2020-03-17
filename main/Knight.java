package main;

public class Knight extends Hero { // clasa copil a clasei Hero
    // constante necesare
    private final int viataConst = 900;  // constanta de viata
    private final int lvlUpConst1 = 250;  // pentru metoda levelup
    private final int lvlUpConst2 = 50;  // pentru metoda levelup
    private final int hpNouConst = 80;  // pt cresterea hp la fiecare nivel
    private final float limLifeEx = 0.2f;  // pt verificarea atingerii limitei de viata la execute
    private final float levelLifeEx = 0.01f; //const. ce indica cresterea lim. de viata la execute
    private final float maxLimEx = 0.4f;  // maximul limitei de viata ce poate fi
    private final int baseDmgEx = 200;  // base damage la execute
    private final int levelDmgEx = 30;  // coord. cresterea base dmg la execute in fnctie de lvl
    private final int levelDmgSl = 40;  // coord. cresterea base dmg la slam in functie de level
    private final float landMod = 1.15f;  // land modifier (LAND)
    private final float rogueCnstEx = 1.15f;  // rogue race modifier pentru execute
    private final float pyroModEx = 1.1f;  // pyromancer race modifier pentru execute
    private final float wizModEx = 0.8f; // wizard race modifier pentru execute
    private final float rogCnstSl = 0.8f;  // rogue race modifier pentru slam
    private final float knModSl = 1.2f;  // knight race modifier pentru slam
    private final int baseDmgSl = 100;  // base damage la slam
    private final float pyroModSl = 0.9f;  // pyromancer race modifier pentru slam
    private final float wizModSl = 1.05f;    // wizard race modifier pentru slam




    public Knight(final int pozitieL, final int pozitieC) {
        // apelam constructorul clasei parinte
        super(pozitieL, pozitieC);
        // initializam hp si hpMaxim cu viataConst corespunzatoare nivelului 0
        this.hp = viataConst;
        this.hpMaxim = viataConst;
    }

    /**
     * metoda folosita pentru afisare.
     * override
     */
    public String toString() {
        return "K";
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
            this.hp = viataConst + this.level * hpNouConst;
            this.hpMaxim = viataConst + this.level * hpNouConst;
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
        // Execute
        float damage1 = 0;
        // daca nu a ajuns maximul de 40% a limitei de viataConst
        if (limLifeEx + levelLifeEx * this.level <= maxLimEx) {
            // daca hp eroului e mai mic decat limita de viataConst admisibila
            if (hero.hp < (limLifeEx + levelLifeEx * this.level) * hero.hpMaxim) {
                // damage-ul va fi egal cu hp-ul adversarului
                damage1 = hero.hp;
            }
        } else {
            // daca a ajuns maximul de 40% a limitei de viataConst
            if (limLifeEx + levelLifeEx * this.level > maxLimEx) {
                // daca hp eroului e mai mic decat limita de viataConst admisibila
                if (hero.hp < maxLimEx * hero.hpMaxim) {
                    // damage-ul va fi egal cu hp-ul adversarului
                    damage1 = hero.hp;
                }
            }
        }
        // daca nu a fost ucis in cele 2 if-uri de mai sus
        if (damage1 == 0) {
            // folosim base damage
            damage1 = baseDmgEx + levelDmgEx * this.level;
        }

        // Slam
        float damage2 = 0;
        // folosim base damage
        damage2 = baseDmgSl + levelDmgSl * this.level;


        // calculam damage total cu rotunjire
        int damagetot;
        // daca this se afla pe teren de tip LAND(primeste bonus de 15%)
        if (this.ceTeren(joc).equals("L")) {
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
        // Execute
        float damage1 = 0;
        // daca nu a ajuns maximul de 40% a limitei de viataConst
        if (limLifeEx + levelLifeEx * this.level <= maxLimEx) {
            // daca hp eroului e mai mic decat limita de viataConst admisibila
            if (hero.hp < (limLifeEx + levelLifeEx * this.level) * hero.hpMaxim) {
                // damage-ul va fi egal cu hp-ul adversarului
                damage1 = hero.hp;
                }
            } else {
            // daca a ajuns maximul de 40% a limitei de viataConst
                if (limLifeEx + levelLifeEx * this.level > maxLimEx) {
                // daca hp eroului e mai mic decat limita de viata admisibila
                    if (hero.hp < maxLimEx * hero.hpMaxim) {
                    // damage-ul va fi egal cu hp-ul adversarului
                        damage1 = hero.hp;
                        }
                    }
                }

        // daca nu a fost ucis in cele 2 if-uri de mai sus
        if (damage1 == 0) {
            // folosim base damage
            damage1 = baseDmgEx + levelDmgEx * this.level;
            // in functie de victima, ii aplicam dmg-ului race modifier
            if (hero.toString().equals("R")) {
                damage1 = Math.round(damage1 * rogueCnstEx);
                } else if (hero.toString().equals("P")) {
                    damage1 = Math.round(damage1 * pyroModEx);
                    } else if (hero.toString().equals("W")) {
                        damage1 = Math.round(damage1 * wizModEx);
                        }
            }

        // Slam
        float damage2 = 0;
        // setam imposibilitate miscare pentru urmatoarea runda
        hero.impMisc = true;
        hero.nrRundeImpMisc = 1;
        // folosim base damage
        damage2 = baseDmgSl + levelDmgSl * this.level;
        // in functie de victima, ii aplicam dmg-ului race modifier
        if (hero.toString().equals("R")) {
            damage2 = Math.round(damage2 * rogCnstSl);
            } else if (hero.toString().equals("K")) {
                damage2 = Math.round(damage2 * knModSl);
                } else if (hero.toString().equals("P")) {
                    damage2 = Math.round(damage2 * pyroModSl);
                    } else if (hero.toString().equals("W")) {
                        damage2 = Math.round(damage2 * wizModSl);
                        }

        // calculam damage total cu rotunjire
        // daca se afla pe land
        int damagetot;
        // daca this se afla pe teren de tip LAND(primeste bonus de 15%)
        if (this.ceTeren(joc).equals("L")) {
            damage1 = Math.round(damage1 * landMod);
            damage2 = Math.round(damage2 * landMod);
            damagetot = Math.round(damage1 + damage2);
            } else {
                damagetot = Math.round(damage1 + damage2);
                }

        // ii adaugam lui hero damage-ul total primit in urma luptei
        hero.dmgPrim = damagetot;
        }
}
