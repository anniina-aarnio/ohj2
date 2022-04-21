package kotitalous;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.HetuTarkistus;
import kanta.Tietue;

/**
 * Käyttäjä-luokka, vastuualueet:
 * Tietää käyttäjän kentät (id, nimi, ikä)
 * Osaa tarkistaa tietyn kentän oikeellisuuden
 * Osaa muuttaa 1|Aada|35 -merkkijonon käyttäjän tiedoiksi
 * Osaa antaa merkkijonona i:nnen kentän tiedot
 * Osaa laittaa merkkijonon i:nneksi kentäksi
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 13.4.2022
 */
public class Kayttaja implements Cloneable, Tietue {
    
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
     * Palauttaa käyttäjän iän
     * @return käyttäjän ikä: vuodet kokonaislukuna
     */
    public int getIka() {
        return this.ika;
    }
    

    /**
     * Palauttaa käyttäjän kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    @Override
    public int getKenttia() {
        return 3;
    }
    
    /**
     * Ensimmäinen kenttä, joka on mielekäs kysyttäväksi
     * @return ensimmäisen kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 1;
    }
    
    
    @Override
    public String anna(int k) {
        switch (k) {
        case 0: return "" + this.kid;
        case 1: return "" + this.nimi;
        case 2: return "" + this.ika;
        default: return "???";
        }
    }
    
    
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param merkkijono jono, joka asetetaan kentän arvoksi
     * @return null, jos asettaminen onnistuu, muuten vastaava virheilmoitus
     * @example
     * <pre name="test">
     *  Kayttaja kayttaja = new Kayttaja();
     *  kayttaja.aseta(1, "") === "Nimi ei saa olla tyhjä";
     *  kayttaja.aseta(1, "Aada |") === "Merkki | ei ole sallittu";
     *  kayttaja.aseta(1, "Aada") === null;
     *  kayttaja.aseta(2, "kissa") === "Anna positiivinen kokonaisluku";
     *  kayttaja.aseta(2, "100") === "Yli 100-vuotias saa levätä";
     *  kayttaja.aseta(2, "02") === "Anna positiivinen kokonaisluku";
     *  kayttaja.aseta(2, "-1") === "Anna positiivinen kokonaisluku";
     *  kayttaja.aseta(2, "0") === "Anna positiivinen kokonaisluku";
     *  kayttaja.aseta(2, "1") === null;
     * </pre>
     */
    @Override
    public String aseta(int k, String merkkijono) {
        String tjono = merkkijono.trim();
        StringBuilder sb = new StringBuilder(tjono);
        switch (k) {
        case 0:
            setKid(Mjonot.erota(sb, '§', getKid()));    // selvitä miksi näin
            return null;
        case 1:
            if (tjono == null || tjono.isEmpty()) return "Nimi ei saa olla tyhjä";
            if (tjono.contains("|")) return "Merkki | ei ole sallittu";
            this.nimi = tjono;
            return null;
        case 2:
            if (tjono == null || tjono.isEmpty()) return "Iän tulee olla vähintään 1";
            if (tjono.matches("[1-9][0-9][0-9]+")) return "Yli 100-vuotias saa levätä";
            if (!tjono.matches("[1-9][0-9]?")) return "Anna positiivinen kokonaisluku";
            this.ika = Integer.parseInt(tjono);
            return null;
        default:
            return "???"; 
        }
    }
    
    /**
     * Palauttaa k:tta jäsenen kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:nnetta kenttää vastaava kysymys
     */
    @Override
    public String getKysymys(int k) {
        switch (k) {
        case 0: return "Käyttäjän ID";
        case 1: return "Nimi";
        case 2: return "Ikä";
        default: return "???";
        }
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
     * Tekee käyttäjästä kloonin
     */
    @Override
    public Kayttaja clone() throws CloneNotSupportedException {
        Kayttaja uusi;
        uusi = (Kayttaja) super.clone();    // toimii koska vain int- ja String-attribuutteja
        return uusi;
    }
    
//    /**
//     * Vastaa, onko annetussa tietueessa virheitä
//     * @return true, jos on virheitä, false jos kaikki kunnossa
//     * @example
//     * <pre name="test">
//     * Kayttaja k = new Kayttaja();
//     * k.parse("   3 |     Aada | 35   ");
//     * k.onkoVirheita() === false;
//     * k.parse("   3  |  |    20");
//     * k.onkoVirheita() === true;
//     * k.parse("3 | Aada | -1");
//     * k.onkoVirheita() === true;
//     * </pre>
//     */
//    @Override
//    public boolean onkoVirheita() {
//        String vastaus = null;
//        for (int i = this.ekaKentta(); i < this.getKenttia(); i++) {
//            vastaus = this.aseta(i, this.anna(i));
//            if (vastaus != null) return true;
//        }
//        return false;
//    }
    
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
