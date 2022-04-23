package kotitalous;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.HetuTarkistus;
import kanta.Tietue;

/**
 * Tehtävä-luokka:
 * - tietää tehtävän kentät (nimi, kesto, ikä) 
 * - osaa tarkistaa tietyn kentän oikeellisuuden
 * - osaa muuttaa 1|Imurointi|30|10 -merkkijonon tehtävän tiedoiksi
 * - osaa antaa merkkijonona i:n kentän tiedot
 * - osaa laittaa merkkijonon i:nneksi kentäksi
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 21.4.2022
 */
public class Tehtava implements Cloneable, Tietue {
    
    private int         tid;                // tehtävä id
    private String      nimi    = "";
    private int         kesto;              // minuuteissa
    private int         ika;                // tehtävän tekijän ikä vähintään
    
    private static int seuraavaNro = 1;     // luokkamuuttuja, joka on olemassa ilman yhtään olemassaolevaa tehtävää

    /**
     * Asettaa tehtävän id-numeron ja samalla varmistaa,
     * että seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTid(int nr) {
        this.tid = nr;
        if (this.tid >= seuraavaNro) seuraavaNro = this.tid + 1;
    }
    
    
    /**
     * Palauttaa tehtävän nimen
     * @return tehtävän nimi
     */
    public String getNimi() {
        return this.nimi;
    }
    
    
    /**
     * Antaa tehtävälle seuraavan rekisterinumeron
     * @return tehtävän uusi id-numero
     * @example
     * <pre name="test">
     *  Tehtava t1 = new Tehtava();
     *  t1.getTid() === 0;
     *  t1.rekisteroi();
     *  Tehtava t2 = new Tehtava();
     *  t2.rekisteroi();
     *  int n1 = t1.getTid();
     *  int n2 = t2.getTid();
     *  n1 === n2-1;
     */
    public int rekisteroi() {
        this.tid = seuraavaNro;
        seuraavaNro++;
        return this.tid;
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot Tehtävälle
     */
    public void taytaImurointiTiedoilla() {
        this.nimi = "Imurointi " + HetuTarkistus.arvoHetu();
        this.kesto = HetuTarkistus.rand(0, 100);
        this.ika = HetuTarkistus.rand(5, 18);
    }
    
    
    /**
     * @return tehtävä-id
     */
    public int getTid() {
        return this.tid;
    }
    
    
    /**
     * @return tehtävän kenttien lukumäärä
     */
    @Override
    public int getKenttia() {
        return 4;
    }
    
    
    /**
     * @return ensimmäinen syötettävän kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 1;
    }
    
    
    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     */
    @Override
    public String getKysymys(int k) {
        switch(k) {
        case 0: return "Id";
        case 1: return "Nimi";
        case 2: return "Kesto";
        case 3: return "Ikä";
        default: return "???";
        }
    }
    
    
    /**
     * @param k minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *  Tehtava t = new Tehtava();
     *  t.parse("3 |   Imurointi   |  40   |   10   ");
     *  t.anna(0) === "3";
     *  t.anna(1) === "Imurointi";
     *  t.anna(2) === "40";
     *  t.anna(3) === "10";
     * </pre>
     */
    @Override
    public String anna(int k) {
        switch (k) {
        case 0: return "" + this.tid;
        case 1: return "" + this.nimi;
        case 2: return "" + this.kesto;
        case 3: return "" + this.ika;
        default: return "???";
        }
    }
    
    
    /**
     * Asetetaan valitun kentän sisältö.
     * Mikäli asettaminen onnistuu, palautetaan null,
     * muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *  Tehtava t = new Tehtava();
     *  t.aseta(1, "") === "Nimi ei saa olla tyhjä";
     *  t.aseta(1, "Imurointi |") === "Merkki | ei ole sallittu";
     *  t.aseta(2, "kissa") === "Anna kokonaisluku, minuutteja";
     *  t.aseta(2, "30") === null;
     *  t.aseta(3, "100") === "Suurin sallittu luku 18";
     *  t.aseta(3, "-1") === "Anna kokonaisluku";
     *  t.aseta(3, "3") === null;
     * </pre>
     */
    @Override
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuilder sb = new StringBuilder(st);
        switch (k) {
        case 0:
            setTid(Mjonot.erota(sb, '§', getTid()));
            return null;
        case 1:
            if (st == null || st.isEmpty()) return "Nimi ei saa olla tyhjä";
            if (st.contains("|")) return "Merkki | ei ole sallittu";
            this.nimi = st;
            return null;
        case 2:
            if (st == null || st.isEmpty()) return "Keston tulee olla vähintään 0 minuuttia";
            if (!st.matches("[0]|[1-9][0-9]*")) return "Anna kokonaisluku, minuutteja";
            this.kesto = Integer.parseInt(st);
            return null;
        case 3:
            if (st == null || st.isEmpty()) return "Iän tulee olla vähintään 0";
            if (st.matches("[1-9][0-9][0-9]+")) return "Suurin sallittu luku 18";
            if (!st.matches("[0]|[1-9][0-9]?")) return "Anna kokonaisluku";
            this.ika = Integer.parseInt(st);
            return null;
        default: return "Väärä kentän indeksi";         
        }
    }
    
    
    /**
     * Selvittää tehtävän tiedot | erotellusta merkkijonosta.
     * Pitää huolen, että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta tehtävän tiedot otetaan
     * @example
     * <pre name="test">
     *  Tehtava tehtava = new Tehtava();
     *  tehtava.parse("   2   |  Imurointi     | 30 |  10    ");
     *  tehtava.getTid() === 2;
     *  tehtava.toString() === "2|Imurointi|30|10";
     *  
     *  tehtava.rekisteroi();
     *  int n = tehtava.getTid();
     *  tehtava.parse(""+(n+20));
     *  tehtava.rekisteroi();
     *  tehtava.getTid() === n+20+1;
     *  tehtava.toString() === "" + (n+20+1) + "|Imurointi|30|10";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTid(Mjonot.erota(sb,  '|', getTid()));
        this.nimi = Mjonot.erota(sb, '|', this.nimi);
        this.kesto = Mjonot.erota(sb, '|', this.kesto);
        this.ika = Mjonot.erota(sb, '|', this.ika);
    }
    
    
    /**
     * Tehdään identtinen klooni tehtävästä
     * @return Object kloonattu tehtävä
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     *  Tehtava t = new Tehtava();
     *  t.parse("3 |   Imurointi   |  40   |   10   ");
     *  Tehtava kopio = t.clone();
     *  kopio.toString() === t.toString();
     *  t.parse("  4 |  Moppaus |  20 | 10   ");
     *  kopio.toString().equals(t.toString()) === false;
     */
    @Override
    public Tehtava clone() throws CloneNotSupportedException {
        Tehtava t = (Tehtava) super.clone();
        return t;
    }
    
    
    /**
     * Tulostetaan käyttäjän tiedot
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.toString());
    }
    
    
    @Override
    public String toString() {
        return this.tid + "|" + this.nimi + "|" + this.kesto + "|" + this.ika;
    }

    
    /**
     * Palauttaa tiedon, onko tämä ja verrattava samat
     * @param tehtava verrattava tehtävä
     * @return true jos samat, false jos eri
     * @example
     * <pre name="test">
     * Tehtava t1 = new Tehtava();
     * t1.parse(" 2  |   Imurointi | 23 | 11 ");
     * Tehtava t2 = new Tehtava();
     * t2.parse(" 3  | Roskien vienti     | 2 | 2");
     * Tehtava t3 = new Tehtava();
     * t3.parse("  3 |  Roskien vienti |2|2");
     * 
     * t1.equals(t2) === false;
     * t2.equals(t1) === false;
     * t1.equals(t3) === false;
     * t2.equals(t3) === true;
     * t3.equals(t3) === true;
     * </pre>
     */
    public boolean equals(Tehtava tehtava) {
        // tämä kannattaisi tehdä käymällä kentät läpi, toistaiseksi tämä riittää
        return this.toString().equals(tehtava.toString()); 
    }
    
    
    @Override
    public boolean equals(Object tehtava) {
        if (tehtava == null) return false;
        if (tehtava instanceof Tehtava) return equals((Tehtava)tehtava);
        return false;
    }
    
    @Override
    public int hashCode() {
        return getTid();
    }


    /**
     * Testaa Tehtävä-luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tehtava t1 = new Tehtava();
        Tehtava t2 = new Tehtava();
        
        t1.rekisteroi();
        t2.rekisteroi();
        
        t1.tulosta(System.out);
        t2.tulosta(System.out);
        
        t1.taytaImurointiTiedoilla();
        t2.taytaImurointiTiedoilla();


        t1.tulosta(System.out);
        t2.tulosta(System.out);
    }
}
