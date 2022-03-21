package kotitalous;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.PrintStream;

/**
 * Käyttäjät-luokka, vastuualueet:
 * Pitää yllä varsinaista käyttäjärekisteriä, eli osaa lisätä ja poistaa käyttäjän
 * Lukee ja kirjoittaa käyttäjien tiedostoon
 * Osaa etsiä ja lajitella
 * @author Anniina
 * @version 21.2.2022
 *
 */
public class Kayttajat {
    
    private static final int MAX_JASENIA = 5;
    
    private int             lkm;
    private Kayttaja[]      alkiot;

    /**
     * Luodaan alustava taulukko
     */
    public Kayttajat() {
        this.alkiot = new Kayttaja[MAX_JASENIA];
    }
    
    
    /**
     * Lisätään käyttäjä käyttäjistöön
     * @param kayttaja lisättävä käyttäjä
     * @example
     * <pre name="test">
     *  Kayttajat kayttajat = new Kayttajat();
     *  Kayttaja aada = new Kayttaja(), ben = new Kayttaja();
     *  kayttajat.getLkm() === 0;
     *  kayttajat.lisaa(aada); kayttajat.getLkm() === 1;
     *  kayttajat.lisaa(ben); kayttajat.getLkm() === 2;
     *  kayttajat.lisaa(aada); kayttajat.getLkm() === 3;
     *  kayttajat.anna(0) === aada;
     *  kayttajat.anna(1) === ben;
     *  kayttajat.anna(2) === aada;
     *  kayttajat.anna(1) == aada === false;
     *  kayttajat.anna(1) == ben === true;
     *  kayttajat.anna(3) === aada; #THROWS IndexOutOfBoundsException
     *  kayttajat.lisaa(aada); kayttajat.getLkm() === 4;
     *  kayttajat.lisaa(aada); kayttajat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Kayttaja kayttaja)  {
        if (lkm >= this.alkiot.length) {
            this.alkiot = Arrays.copyOf(this.alkiot, lkm+10);
        }
        this.alkiot[lkm] = kayttaja;
        this.lkm++;
    }
    
    
    /**
     * @param kayttajaId etsitty käyttäjä-id
     * @return etsitty käyttäjä
     * @throws SailoException jos ei löydy käyttäjää annetulla id:llä
     */
    public Kayttaja etsi(int kayttajaId) throws SailoException {
        for (Kayttaja k : this.alkiot) {
            if (k.getKid() == kayttajaId) return k;
        }
        throw new SailoException("Ei löydy annetulla käyttäjäindeksillä " + kayttajaId + ".");
    }
    
    
    /**
     * @param i käyttäjän indeksi
     * @return käyttäjä kyseisellä indeksillä
     * @throws IndexOutOfBoundsException kun mennään yli käyttäjälukumäärän
     */
    public Kayttaja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return this.alkiot[i];
    }
    
    
    /**
     * Palauttaa tämänhetkisen lukumäärän
     * @return lukumäärä
     */
    public int getLkm() {
        return this.lkm;
    }
    
    
    /**
     * Lukee jäsenistön tiedostosta, kesken. // TODO
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/nimet.dat";
        File ftied = new File(nimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if ( s == null || s.equals("") || s.charAt(0) == ';') continue;
                Kayttaja kayttaja = new Kayttaja();
                kayttaja.parse(s);                  // kertoisi onnistumista ? esim poikkeus, palauttaisi true false
                lisaa(kayttaja);
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
        }
    }
    
    
//    /**
//     * Tallentaa käyttäjistön tiedostoon
//     * Tiedoston muoto:
//     * <pre>
//     * 2|Aada|35
//     * 3|Ben|34
//     * </pre>
//     * @param hakemisto tallennettavan tiedoston hakemisto
//     * @throws SailoException jos talletus epäonnistuu
//     */
//    public void tallenna(String hakemisto) throws SailoException {
//        File ftied = new File(hakemisto + "/nimet.dat"); // TODO tee tiedosto, jos sitä ei ole
//        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, true))) { //tarkista tuleeko false vai true
//            for (int i = 0; i < this.getLkm(); i++) {
//                Kayttaja kayttaja = this.anna(i);
//                fo.println(kayttaja.toString());
//            }
//        } catch (FileNotFoundException ex) {
//            throw new SailoException("Tiedosto " + ftied.getAbsolutePath());
//        }
//    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kayttajat kayttajat = new Kayttajat();
        
        // tähän lukeminen parse:n kanssa...
        
        Kayttaja aada = new Kayttaja();         // saa syntyä millaisena haluaa graafisessa ympäristössä
        Kayttaja ben = new Kayttaja();
        
        aada.rekisteroi();                      // olio saa itse tehdä
        ben.rekisteroi();                       // tekee eri rekisterinumeron...
        
        aada.taytaAadaTiedoilla();            // täyttää itse itsensä esimerkki-Aadan tiedoilla
        ben.taytaAadaTiedoilla();
        
        try {
            kayttajat.lisaa(aada);
            kayttajat.lisaa(ben);
            kayttajat.lisaa(aada);
            kayttajat.lisaa(ben);
            kayttajat.lisaa(aada);
            kayttajat.lisaa(ben);
            kayttajat.anna(34);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(" ====================== Käyttäjät testi ======================= ");
        for (int i= 0; i < kayttajat.getLkm(); i++) {
            Kayttaja kayttaja = kayttajat.anna(i);
            System.out.println("Jäsen indeksi: " + i);
            kayttaja.tulosta(System.out);
        }
        
//        try {
//            kayttajat.tallenna("kotitalous");             // pitäisi toimia jo, ei testattu
//        } catch (SailoException e) {
//            System.out.println(e.getMessage());
//        }
        
        
    }
}
