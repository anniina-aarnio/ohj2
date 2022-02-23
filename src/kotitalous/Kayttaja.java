package kotitalous;

import java.io.OutputStream;
import java.io.PrintStream;

import kanta.HetuTarkistus;

/**
 * Käyttäjä-luokka, vastuualueet:
 * Tietää käyttäjän kentät (id, nimi, ikä)
 * Osaa tarkistaa tietyn kentän oikeellisuuden
 * Osaa muuttaa 1|Aada|35 -merkkijonon käyttäjän tiedoiksi
 * Osaa antaa merkkijonona i:nnen kentän tiedot
 * Osaa laittaa merkkijonon i:nneksi kentäksi
 * @author Anniina
 * @version 21.2.2022
 */
public class Kayttaja {
    
    private int         kid;                            // käyttäjä id
    private String      nimi         = "";              // etunimi riittää
    private int         ika;                            // alkuun riittää itse annettu ikä, voisi olla syntymävuoden mukaan
    
    private static int  seuraavaNro  = 1;               // luokkamuuttuja, joka on olemassa ilman yhtään olemassaolevaa kayttajaa
    

    // jos luokassa on parametriton JA jokin toinen muodostaja niin parametritonkin on pakko tehdä
    // pelkkään parametrittomaan riittää tämä alustukseksi
    
    /**
     * Antaa jäsenelle seuraavan rekisterinumeron.
     * @return käyttäjän uusi id-numero
     * @example
     * <pre name="test">
     *  Kayttaja aku1 = new Kayttaja();
     *  aku1.getKid();
     *  aku1.rekisteroi();
     *  Kayttaja aku2 = new Kayttaja();
     *  aku2. rekisteroi();
     *  int n1 = aku1.getKid();
     *  int n2 = aku2.getKid();
     *  n1 === n2-1;
     *  n2 === n1+1;
     * </pre>
     */
    public int rekisteroi() {
        this.kid = seuraavaNro;
        seuraavaNro++;
        return this.kid;
    }
    
    
    /**
     * @return käyttäjän id
     * @example
     * <pre name="test">
     *  Kayttaja aku1 = new Kayttaja();
     *  aku1.getKid() === 0;
     * </pre>
     */
    public int getKid() {
        return this.kid;
    }
    
    
    /**
     * Palauttaa käyttäjän nimen
     * @return käyttäjän nimi
     */
    public String getNimi() {
        return this.nimi;
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle
     */
    public void taytaAadaTiedoilla() {
        this.nimi = "Aada " + HetuTarkistus.arvoHetu();
        this.ika = HetuTarkistus.rand(1, 99);
//        rekisteroi();
    }
    
    
    /**
     * Tulostetaan käyttäjän tiedot
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", this.kid) + ": " + this.nimi + " " + this.ika);        // %03 kolmen numeron ja loppu täytetään nollilla, esim 001, 002 ... 
    }
    
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));           // printstream saa hoitaa yhteyden outputstreamille
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kayttaja aada = new Kayttaja();         // saa syntyä millaisena haluaa graafisessa ympäristössä
        Kayttaja ben = new Kayttaja();
        
        aada.rekisteroi();                      // olio saa itse tehdä
        ben.rekisteroi();                       // tekee eri rekisterinumeron...
        
        aada.tulosta(System.out);               // parametrina tietovirta, jotta voi tulostaa muuallekin kuin näyttöön
        ben.tulosta(System.out);
        
        aada.taytaAadaTiedoilla();            // täyttää itse itsensä esimerkki-Aadan tiedoilla
        ben.taytaAadaTiedoilla();
        
        aada.tulosta(System.out);               // malliksi tulostus
        ben.tulosta(System.out);                // malliksi tulostus
        
    }
}
