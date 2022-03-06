package kotitalous;

import java.io.PrintStream;

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
    private int         kesto;
    private int         ika;
    private int         tid;                // tehtävä id
    
    private static int seuraavaNro = 1;     // luokkamuuttuja, joka on olemassa ilman yhtään olemassaolevaa tehtävää

    
    /**
     * Antaa tehtävälle seuraavan rekisterinumeron
     * @return tehtävän uusi id-numero
     * @example
     * <pre name="test">
     *  Tehtava t1 = new Tehtava();
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
        return String.format("%03d", this.tid) + " " + this.nimi + ": kesto: " + this.kesto + " min, minimi-ikä: " + this.ika;
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
