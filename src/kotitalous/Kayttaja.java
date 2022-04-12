package kotitalous;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
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
    
    /**
     * Asettaa käyttäjä-id:n ja samalla varmistaa,
     * että seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava käyttäjä-id
     */
    private void setKid(int nr) {
        this.kid = nr;
        if (this.kid >= seuraavaNro) seuraavaNro = this.kid + 1;
    }
  
    /**
     * Antaa jäsenelle seuraavan rekisterinumeron.
     * @return käyttäjän uusi id-numero
     * @example
     * <pre name="test">
     *  Kayttaja aku1 = new Kayttaja();
     *  aku1.getKid() === 0;
     *  aku1.rekisteroi();
     *  Kayttaja aku2 = new Kayttaja();
     *  aku2.rekisteroi();
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
     * Asettaa käyttäjän nimen
     * @param nimi muutettava nimi
     * @return null jos kaikki meni hyvin, muuten virheteksti
     * @example
     * <pre name="test">
     * Kayttaja k = new Kayttaja();
     * k.setNimi("Minna") === null;
     * k.setNimi("") === "Nimi ei saa olla tyhjä";
     * k.getNimi() === "Minna";
     * k.setNimi(null) === "Nimi ei saa olla tyhjä";
     * k.setNimi("Minna | Mikkonen") === "Merkki | ei ole sallittu";
     * </pre>
     */
    public String setNimi(String nimi) {
        if (nimi == null || nimi.isEmpty()) return "Nimi ei saa olla tyhjä";
        if (nimi.contains("|")) return "Merkki | ei ole sallittu";
        this.nimi = nimi;
        return null;
    }
    
    
    /**
     * Palauttaa käyttäjän iän
     * @return käyttäjän ikä: vuodet kokonaislukuna
     */
    public int getIka() {
        return this.ika;
    }
    
    
    /**
     * Asettaa käyttäjän iän
     * @param nro uusi ikä
     */
    public void setIka(int nro) {
        this.ika = nro;
    }
    
    
    /**
     * Asettaa käyttäjän iän merkkijonosta
     * @param s merkkijono, josta ikä otetaan
     * @return null, jos kaikki ok, muuten virheteksti
     * @example
     * <pre name="test">
     * Kayttaja k = new Kayttaja();
     * k.setIka("14") === null;
     * k.setIka("") === "Iän tulee olla vähintään 0";
     * k.getIka() === 14;
     * k.setIka("a") === "Vääriä merkkejä, käytä vain kokonaislukuja";
     * k.setIka("01") === "Vääriä merkkejä, käytä vain kokonaislukuja";
     * k.setIka("100") === "Yli 100-vuotiaan ei tarvitse tehdä kotitöitä";
     * </pre>
     */
    public String setIka(String s) {
        if (s == null || s.isEmpty()) return "Iän tulee olla vähintään 0";
        if (s.matches("[1-9][0-9][0-9]+")) return "Yli 100-vuotias saa levätä";
        if (!s.matches("[0]|[1-9][0-9]?")) return "Käytä kokonaislukuja";
        this.ika = Integer.parseInt(s);
        return null;
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle.
     * Nimestä tulee "Aada " ja leikkihenkilötunnus.
     * Ikä arvotaan välillä 1-99.
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
        out.println(String.format("%03d: %s %d", this.kid, this.nimi, this.ika));  
    }
    
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));           // printstream saa hoitaa yhteyden outputstreamille
    }
    
    
    /**
     * Selvittää käyttäjän tiedot | erotellusta merkkijonosta
     * Pitää huolen, että seuraavaNro on suurempi kuin tuleva tunnusNro
     * @param rivi josta käyttäjän tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *  Kayttaja kayttaja = new Kayttaja();
     *  kayttaja.parse("   3   | Aada     | 35   ");
     *  kayttaja.getKid() === 3;
     *  kayttaja.toString().startsWith("3|Aada|35") === true;
     *  
     *  kayttaja.rekisteroi();
     *  int n = kayttaja.getKid();
     *  kayttaja.parse(""+(n+20)); // Otetaan merkkijonosta vain tunnusnumero
     *  kayttaja.rekisteroi();     // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *  kayttaja.getKid() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        setKid(Mjonot.erota(sb, '|', getKid()));
        this.nimi = Mjonot.erota(sb, '|', this.nimi);
        this.ika = Mjonot.erota(sb, '|', this.ika);
    }
    
    
    /**
     * Tulostaa käyttäjän tiedot muodossa:
     * KäyttäjäID|Nimi|Ikä
     * @example
     * <pre name="test">
     * Kayttaja aada = new Kayttaja();
     * aada.toString() === "0||0";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + 
                this.getKid() + "|" +
                this.nimi + "|" + 
                this.ika;
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
        
        aada.taytaAadaTiedoilla();              // täyttää itse itsensä esimerkki-Aadan tiedoilla
        ben.taytaAadaTiedoilla();
        
        aada.tulosta(System.out);               // malliksi tulostus
        ben.tulosta(System.out);                // malliksi tulostus
        
    }
}
