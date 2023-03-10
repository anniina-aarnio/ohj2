package kotitalous;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Tehtävät-luokka:
 * - pitää yllä varsinaista tehtävärekisteriä, eli osaa lisätä ja poistaa tehtävän
 * - lukee ja kirjoittaa tehtävät tiedostoon
 * - osaa etsiä ja lajitella
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 21.4.2022
 */
public class Tehtavat implements Iterable<Tehtava> {
    
    private final List<Tehtava> alkiot = new ArrayList<Tehtava>();
    private boolean muutettu = false;
    
    /**
     * Lisää tehtävän
     * @param tehtava lisättävä tehtävä
     */
    public void lisaa(Tehtava tehtava) {
        this.alkiot.add(tehtava);
        this.muutettu = true;
    }
    
    
    /**
     * Korvaa käyttäjän tietorakenteessa. Ottaa käyttäjän omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva käyttäjä.
     * Jos ei löydy, niin lisätään uutena käyttäjänä.
     * @param tehtava korvattava tai lisättävä käyttäjä
     * @example
     * #THROWS CloneNotSupportedException
     * <pre name="test">
     * Tehtavat tehtavat = new Tehtavat();
     * Tehtava t = new Tehtava(), t2 = new Tehtava();
     * t.rekisteroi(); t2.rekisteroi();
     * tehtavat.getLkm() === 0;
     * tehtavat.korvaaTaiLisaa(t); tehtavat.getLkm() === 1;
     * tehtavat.korvaaTaiLisaa(t2); tehtavat.getLkm() === 2;
     * Tehtava t3 = new Tehtava();
     * try {
     *  t3 = t.clone();
     * } catch (CloneNotSupportedException e) {
     *  System.out.println(e.getMessage());
     * }
     * t3.aseta(1, "Imurointi");
     * Iterator<Tehtava> it = tehtavat.iterator();
     * it.next() == t === true;
     * tehtavat.korvaaTaiLisaa(t3); tehtavat.getLkm() === 2;
     * it = tehtavat.iterator();
     * Tehtava t0 = it.next();
     * t0 === t3;
     * t0 == t === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Tehtava tehtava) {
        int id = tehtava.getTid();

        for (int i = 0; i < getLkm(); i++) {
            if (this.alkiot.get(i).getTid() == id) {
                this.alkiot.set(i, tehtava);
                muutettu = true;
                return;
            }
        }
        lisaa(tehtava);
    }
    
    
    /**
     * Poistaa tehtävän tehtävistä
     * @param tehtava tehtävä joka poistetaan
     * @return true jos poistettiin, false jos ei
     */
    public boolean poista(Tehtava tehtava) {
        boolean poistettu = this.alkiot.remove(tehtava);
        if (poistettu) this.muutettu = true;
        return poistettu;
    }
    
       
    /**
     * Etsii ja palauttaa tehtävä-id:n perusteella tehtävän
     * @param tehtavaId etsittävän tehtävän 
     * @return etsitty tehtävä
     * @throws SailoException virheilmoitus, jos ei löytynyt
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Tehtavat teet = new Tehtavat();
     * Tehtava t1 = new Tehtava();
     * t1.rekisteroi();
     * teet.lisaa(t1);
     * teet.etsi(t1.getTid()) === t1;
     * teet.etsi(200); #THROWS SailoException
     * </pre>
     */
    public Tehtava etsi(int tehtavaId) throws SailoException {
        for (Tehtava t : this.alkiot) {
                if (t.getTid() == tehtavaId) return t;
        }
        throw new SailoException("Etsittyä tehtävä-id:tä " + tehtavaId + " ei löytynyt.");
    }
    
    /**
     * Palauttaa tehtävien lukumäärän
     * @return tehtävien lukumäärä
     * @example
     * <pre name="test">
     *  Tehtavat teet = new Tehtavat();
     *  teet.getLkm() === 0;
     *  teet.lisaa(new Tehtava());
     *  teet.getLkm() === 1;
     * </pre>
     */
    public int getLkm() {
        return this.alkiot.size();
    }

    
    /**
     * Luo ja palauttaa listan, jossa on kaikki tämän hetken tehtävät
     * @return tehtävät listana
     */
    public List<Tehtava> annaKaikki() {
        List<Tehtava> kaikki = new ArrayList<Tehtava>();
        for (Tehtava t : this.alkiot) {
            kaikki.add(t);
        }
        return kaikki;
    }
    
    /**
     * Palauttaa listan tehtävistä, joihin sopii hakuehto
     * @param hakuehto merkkijono, joka tulee löytyä nimestä
     * @return lista sopivista tehtävistä
     * @example
     * <pre name="test">
     * #import java.util.List;
     * Tehtavat teet = new Tehtavat();
     *  Tehtava imu = new Tehtava(); imu.rekisteroi(); imu.parse("2  | Imurointi | 20 |15");
     *  Tehtava wc = new Tehtava(); wc.rekisteroi(); wc.parse("3  | WC:n pesu | 10 |18");
     *  Tehtava astiat = new Tehtava(); astiat.rekisteroi(); astiat.parse("2  | Astioiden vienti | 20 |1");
     *  teet.lisaa(imu); teet.lisaa(wc); teet.lisaa(astiat);
     *  List<Tehtava> iilliset = teet.annaHakuehdolla("i");
     *  iilliset.size() === 2;
     *  iilliset.get(0) === imu;
     *  iilliset.get(1) === astiat;
     *  iilliset = teet.annaHakuehdolla("wc");
     *  iilliset.size() === 1;
     *  iilliset.get(0) === wc;
     * </pre>
     */
    public List<Tehtava> annaHakuehdolla(String hakuehto) {
        if (hakuehto == null || hakuehto.isEmpty()) return annaKaikki();
        List<Tehtava> haetut = new ArrayList<Tehtava>();
        for (Tehtava t : this.alkiot) {
            if (t.ehto(hakuehto)) haetut.add(t);
        }
        return haetut;
    }
    
    
    /**
     * Lukee tehtävät tiedostosta.
     * @param hakemisto tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * #import java.util.Iterator;
     *  Tehtavat teet = new Tehtavat();
     *  Tehtava imu12 = new Tehtava(); imu12.rekisteroi(); imu12.taytaImurointiTiedoilla();
     *  Tehtava imu23 = new Tehtava(); imu23.rekisteroi(); imu23.taytaImurointiTiedoilla();
     *  Tehtava imu34 = new Tehtava(); imu34.rekisteroi(); imu34.taytaImurointiTiedoilla();
     *  String tiedNimi = "testiKotitalous";
     *  File ftied = new File(tiedNimi + "/tehtavat.dat");
     *  ftied.delete();
     *  teet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  teet.lisaa(imu12);
     *  teet.lisaa(imu23);
     *  teet.lisaa(imu34);
     *  teet.tallenna(tiedNimi);
     *  teet = new Tehtavat();
     *  teet.lueTiedostosta(tiedNimi);
     *  Iterator<Tehtava> i = teet.iterator();
     *  i.next().toString() === imu12.toString();
     *  i.next().toString() === imu23.toString();
     *  i.next().toString() === imu34.toString();
     *  i.hasNext() === false;
     *  teet.lisaa(imu12);
     *  teet.tallenna(tiedNimi);
     *  ftied.delete() === true; 
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/tehtavat.dat";
        File ftied = new File(nimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = fi.nextLine().trim();
                if ("".equals(s) || s.charAt(0) == ';') continue;
                Tehtava teht = new Tehtava();
                teht.parse(s);                      // voisi kaivata virhekäsittelyä..
                lisaa(teht);
            }
        }catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
        }
    }
    
    
    /**
     * Tallentaa tehtävät tiedostoon.
     * Tiedoston muoto:
     * <pre>
     * 1|Imurointi|10|30
     * 3|Roskien vienti|3|7
     * </pre>
     * @param hakemisto tallennettavan tiedoston hakemisto
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        if (this.muutettu == false) return;
        File ftied = new File(hakemisto + "/tehtavat.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (var teht : this.alkiot) {
                fo.println(teht.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea.");
        }
    }
    
    
    @Override
    public Iterator<Tehtava> iterator() {
        return this.alkiot.iterator();
    }
    
    /**
     * Testaa tehtävät-luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tehtavat teet = new Tehtavat();
        
        try {
            teet.lueTiedostosta("testiKotitalous");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
        
        Tehtava imurointi1 = new Tehtava();
        Tehtava imurointi2 = new Tehtava();
        imurointi1.rekisteroi();
        imurointi2.rekisteroi();
        imurointi1.taytaImurointiTiedoilla();
        imurointi2.taytaImurointiTiedoilla();
        
        teet.lisaa(imurointi1);
        teet.lisaa(imurointi2);
        
        System.out.println("=================Tehtävä-testi===============");

        for (Tehtava t : teet.annaKaikki()) {
            t.tulosta(System.out);
        }
        
        try {
            teet.tallenna("testiKotitalous");
        } catch (SailoException e) {
            e.printStackTrace();
        }
        
        
    }
}
