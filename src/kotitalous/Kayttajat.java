package kotitalous;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


/**
 * Käyttäjät-luokka, vastuualueet:
 * Pitää yllä varsinaista käyttäjärekisteriä, eli osaa lisätä ja poistaa käyttäjän
 * Lukee ja kirjoittaa käyttäjien tiedostoon
 * Osaa etsiä ja lajitella
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 21.4.2022
 *
 */
public class Kayttajat implements Iterable<Kayttaja> {
    
    private static final int MAX_JASENIA = 5;
    
    private int             lkm;
    private Kayttaja[]      alkiot;
    private boolean         muutettu = false;
    
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
        this.muutettu = true;
    }
    
    /**
     * Korvaa käyttäjän tietorakenteessa. Ottaa käyttäjän omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva käyttäjä.
     * Jos ei löydy, niin lisätään uutena käyttäjänä.
     * @param kayttaja korvattava tai lisättävä käyttäjä
     * @example
     * #THROWS CloneNotSupportedException
     * <pre name="test">
     * Kayttajat kayttajat = new Kayttajat();
     * Kayttaja k = new Kayttaja(), k2 = new Kayttaja();
     * k.rekisteroi(); k2.rekisteroi();
     * kayttajat.getLkm() === 0;
     * kayttajat.korvaaTaiLisaa(k); kayttajat.getLkm() === 1;
     * kayttajat.korvaaTaiLisaa(k2); kayttajat.getLkm() === 2;
     * Kayttaja k3 = new Kayttaja();
     * try {
     *  k3 = k.clone();
     * } catch (CloneNotSupportedException e) {
     *  System.out.println(e.getMessage());
     * }
     * k3.aseta(1, "Aada");
     * Iterator<Kayttaja> it = kayttajat.iterator();
     * it.next() == k === true;
     * kayttajat.korvaaTaiLisaa(k3); kayttajat.getLkm() === 2;
     * it = kayttajat.iterator();
     * Kayttaja k0 = it.next();
     * k0 === k3;
     * k0 == k === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Kayttaja kayttaja) {
        int id = kayttaja.getKid();
        for (int i = 0; i < lkm; i++) {
            if (this.alkiot[i].getKid() == id) {
                this.alkiot[i] = kayttaja;
                this.muutettu = true;
                return;
            }
        }
        lisaa(kayttaja);
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
     * Lukee jäsenistön tiedostosta.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * #import java.util.Iterator;
     *  Kayttajat kayttajat = new Kayttajat();
     *  Kayttaja aada1 = new Kayttaja(); aada1.rekisteroi(); aada1.taytaAadaTiedoilla();
     *  Kayttaja aada2 = new Kayttaja(); aada2.rekisteroi(); aada2.taytaAadaTiedoilla();
     *  Kayttaja aada3 = new Kayttaja(); aada3.rekisteroi(); aada3.taytaAadaTiedoilla();
     *  String tiedNimi = "testiKotitalous";
     *  File ftied = new File(tiedNimi + "/kayttajat.dat");
     *  ftied.delete();
     *  kayttajat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kayttajat.lisaa(aada1);
     *  kayttajat.lisaa(aada2);
     *  kayttajat.lisaa(aada3);
     *  kayttajat.tallenna(tiedNimi);
     *  kayttajat = new Kayttajat();
     *  kayttajat.lueTiedostosta(tiedNimi);
     *  Iterator<Kayttaja> i = kayttajat.iterator();
     *  i.next().toString() === aada1.toString();
     *  i.next().toString() === aada2.toString();
     *  i.next().toString() === aada3.toString();
     *  i.hasNext() === false;
     *  kayttajat.lisaa(aada1);
     *  kayttajat.tallenna(tiedNimi);
     *  ftied.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/kayttajat.dat";
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
        this.muutettu = false;
    }
    
    
    /**
     * Tallentaa käyttäjistön tiedostoon
     * Tiedoston muoto:
     * <pre>
     * 2|Aada|35
     * 3|Ben|34
     * </pre>
     * @param hakemisto tallennettavan tiedoston hakemisto
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        if (!muutettu) return;
        File ftied = new File(hakemisto + "/kayttajat.dat"); // TODO tee tiedosto, jos sitä ei ole
        
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (int i = 0; i < this.getLkm(); i++) {
                Kayttaja kayttaja = this.anna(i);
                fo.println(kayttaja.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }
    }
    
    
    /**
     * Luokka käyttäjien iteroimiseksi.
     * @excample
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*;
     * 
     *  Kayttajat kayttajat = new Kayttajat();
     *  Kayttaja aada1 = new Kayttaja(), aada2 = new Kayttaja();
     *  aada1.rekisteroi(); aada2.rekisteroi();
     *  
     *  kayttajat.lisaa(aada1);
     *  kayttajat.lisaa(aada2);
     *  kayttajat.lisaa(aada1);
     *  
     *  StringBuffer ids = new StringBuffer(30);
     *  for (Kayttaja kayttaja : kayttajat)
     *      ids.append(" " + kayttaja.getKid());
     *  String tulos = " " + aada1.getKid() + " " + aada2.getKid() + " " + aada1.getKid();
     *  
     *  ids.toString() === tulos;
     *  
     *  ids = new StringBuffer(30);
     *  for (Iterator<Kayttaja> i = kayttajat.iterator(); i.hasNext();) {
     *      Kayttaja k = i.next();
     *      ids.append(" " + k.getKid());
     *  }
     *  
     *  ids.toString() === tulos;
     *  
     *  Iterator<Kayttaja> i = kayttajat.iterator();
     *  i.next() == aada1 === true;
     *  i.next() == aada2 === true;
     *  i.next() == aada1 === true;
     *  
     *  i.next(); #THROWS NoSuchElementException
     */
    @Override
    public Iterator<Kayttaja> iterator() {
        return new KayttajatIterator();
    }
    
    /**
     * @author Anniina
     * @version 28.3.2022
     */
    public class KayttajatIterator implements Iterator<Kayttaja> {
        private int kohdalla = 0;

        /**
         * Onko olemassa vielä seuraava käyttäjä
         * @return true, jos on vielä käyttäjiä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }

        /**
         * Annetaan seuraava käyttäjä
         * @return seuraava käyttäjä
         * @throws NoSuchElementException jos seuraavaa alkiota ei enää ole
         */
        @Override
        public Kayttaja next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kayttajat kayttajat = new Kayttajat();
        
        try {
            kayttajat.lueTiedostosta("testiKotitalous");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
        
        Kayttaja aada = new Kayttaja();         // saa syntyä millaisena haluaa graafisessa ympäristössä
        Kayttaja ben = new Kayttaja();
        
        aada.rekisteroi();                      // olio saa itse tehdä
        ben.rekisteroi();                       // tekee eri rekisterinumeron...
        
        aada.taytaAadaTiedoilla();            // täyttää itse itsensä esimerkki-Aadan tiedoilla
        ben.taytaAadaTiedoilla();

        
        try {
            kayttajat.lisaa(aada);
            kayttajat.lisaa(ben);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(" ====================== Käyttäjät testi ======================= ");
        for (int i= 0; i < kayttajat.getLkm(); i++) {
            Kayttaja kayttaja = kayttajat.anna(i);
            System.out.println("Jäsen indeksi: " + i);
            kayttaja.tulosta(System.out);
        }
        
        try {
            kayttajat.tallenna("testiKotitalous"); 
        } catch (SailoException e) {
            e.printStackTrace();
        }
        
        try {
            kayttajat.lueTiedostosta("testiKotitalous");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
        
        
    }


}
