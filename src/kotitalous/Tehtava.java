package kotitalous;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.HetuTarkistus;

/**
 * Tehtävä-luokka:
 * - tietää tehtävän kentät (nimi, kesto, ikä) 
 * - osaa tarkistaa tietyn kentän oikeellisuuden
 * - osaa muuttaa 1|Imurointi|10|30 -merkkijonon tehtävän tiedoiksi
 * - osaa antaa merkkijonona i:n kentän tiedot
 * - osaa laittaa merkkijonon i:nneksi kentäksi
 * @author Anniina
 * @version 6.3.2022
 */
public class Tehtava {
    
    private String      nimi    = "";
    private int         kesto;              // minuuteissa
    private int         ika;                // tehtävän tekijän ikä vähintään
    private int         tid;                // tehtävä id
    
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
     * Tulostetaan käyttäjän tiedot
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.toString());
    }
    
    
    @Override
    public String toString() {
        return this.tid + "|" + this.nimi + "|" + this.ika + "|" + this.kesto;
    }
    
    
    /**
     * Selvittää tehtävän tiedot | erotellusta merkkijonosta.
     * Pitää huolen, että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta tehtävän tiedot otetaan
     * @example
     * <pre name="test">
     *  Tehtava tehtava = new Tehtava();
     *  tehtava.parse("   2   |  Imurointi     | 10 |  30    ");
     *  tehtava.getTid() === 2;
     *  tehtava.toString() === "2|Imurointi|10|30";
     *  
     *  tehtava.rekisteroi();
     *  int n = tehtava.getTid();
     *  tehtava.parse(""+(n+20));
     *  tehtava.rekisteroi();
     *  tehtava.getTid() === n+20+1;
     *  tehtava.toString() === "" + (n+20+1) + "|Imurointi|10|30";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTid(Mjonot.erota(sb,  '|', getTid()));
        this.nimi = Mjonot.erota(sb, '|', this.nimi);
        this.ika = Mjonot.erota(sb, '|', this.ika);
        this.kesto = Mjonot.erota(sb, '|', this.kesto);
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
