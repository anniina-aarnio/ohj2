package kotitalous;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sovitut tehtävät -luokka:
 * - pitää y llä sovittujen tehtävien rekisteriä, eli osaa lisätä ja poistaa yhteyden
 *   tehtävän ja käyttäjän välillä
 * - lukee ja kirjoittaa sovitut tehtävät -tiedostoon
 * - osaa etsiä ja lajitella
 * @author Anniina
 * @version 6.3.2022
 */
public class SovitutTehtavat {
    
    private final Collection<SovittuTehtava> alkiot = new ArrayList<SovittuTehtava>();
    
    
    /**
     * @param sovittutehtava annettu tehtävä
     */
    public void lisaa(SovittuTehtava sovittutehtava) {
        this.alkiot.add(sovittutehtava);
    }
    
    
    /**
     * Etsii käyttäjät tehtävä-id:n perusteella
     * @param tehtavaId tehtävä-id, jonka perusteella etsitään
     * @return kaikki käyttäjät, joilla annettu tehtävä on
     * @example
     * <pre name="test">
     * #import java.util.*;
     *   SovitutTehtavat steet = new SovitutTehtavat();
     *   SovittuTehtava st1 = new SovittuTehtava();
     *   SovittuTehtava st2 = new SovittuTehtava();
     *   SovittuTehtava st3 = new SovittuTehtava();
     *   
     *   Kayttajat koot = new Kayttajat();
     *   Kayttaja k1 = new Kayttaja();
     *   Kayttaja k2 = new Kayttaja();
     *   k1.rekisteroi();
     *   k2.rekisteroi();
     *   k1.taytaAadaTiedoilla();
     *   k2.taytaAadaTiedoilla();
     *   koot.lisaa(k1);
     *   koot.lisaa(k2);
     *   
     *   Tehtavat teet = new Tehtavat();
     *   Tehtava t1 = new Tehtava();
     *   Tehtava t2 = new Tehtava();
     *   t1.rekisteroi();
     *   t2.rekisteroi();
     *   t1.taytaImurointiTiedoilla();
     *   t2.taytaImurointiTiedoilla();
     *   teet.lisaa(t1);
     *   teet.lisaa(t2);
     *   
     *   st1.setKayttaja(k1.getKid());
     *   st2.setKayttaja(k2.getKid());
     *   st3.setKayttaja(k1.getKid());
     *   st1.setTehtava(t1.getTid());
     *   st2.setTehtava(t2.getTid());
     *   st3.setTehtava(t2.getTid());
     *   
     *   steet.lisaa(st1);
     *   steet.lisaa(st2);
     *   steet.lisaa(st3);
     *   
     *   List<SovittuTehtava> loytyneet = steet.annaSovitutKayttajat(t1.getTid());
     *   loytyneet.size() === 1;
     *   loytyneet.get(0) == st1 === true;
     *   
     *   loytyneet = steet.annaSovitutTehtavat(k2.getKid());
     *   loytyneet.size() === 1;
     *   loytyneet.get(0) == st2 === true;
     *   
     * </pre>
     */
    public List<SovittuTehtava> annaSovitutKayttajat(int tehtavaId) {
        List<SovittuTehtava> loydetyt = new ArrayList<SovittuTehtava>();
        for (SovittuTehtava st : this.alkiot) {
            if (st.getTid() == tehtavaId) loydetyt.add(st);
        }
        return loydetyt;
    }
    
    
    /**
     * Etsii tehtävät käyttäjä-id:n perusteella
     * @param kayttajaId käyttäjä-id, jonka perusteella etsitään
     * @return kaikki tehtävät, joilla annettu käyttäjä on
     */
    public List<SovittuTehtava> annaSovitutTehtavat(int kayttajaId) {
        List<SovittuTehtava> loydetyt = new ArrayList<SovittuTehtava>();
        for (SovittuTehtava st : this.alkiot) {
            if (st.getKid() == kayttajaId) loydetyt.add(st);
        }
        return loydetyt;
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        SovitutTehtavat steet = new SovitutTehtavat();
        SovittuTehtava st1 = new SovittuTehtava();
        SovittuTehtava st2 = new SovittuTehtava();
        SovittuTehtava st3 = new SovittuTehtava();
        
        Kayttajat koot = new Kayttajat();
        Kayttaja k1 = new Kayttaja();
        Kayttaja k2 = new Kayttaja();
        k1.rekisteroi();
        k2.rekisteroi();
        k1.taytaAadaTiedoilla();
        k2.taytaAadaTiedoilla();
        koot.lisaa(k1);
        koot.lisaa(k2);
        
        Tehtavat teet = new Tehtavat();
        Tehtava t1 = new Tehtava();
        Tehtava t2 = new Tehtava();
        t1.rekisteroi();
        t2.rekisteroi();
        t1.taytaImurointiTiedoilla();
        t2.taytaImurointiTiedoilla();
        teet.lisaa(t1);
        teet.lisaa(t2);
        
        st1.setKayttaja(k1.getKid());
        st2.setKayttaja(k2.getKid());
        st3.setKayttaja(k1.getKid());
        st1.setTehtava(t1.getTid());
        st2.setTehtava(t2.getTid());
        st3.setTehtava(t2.getTid());
                
        steet.lisaa(st1);
        steet.lisaa(st2);
        steet.lisaa(st3);
        
        List<SovittuTehtava> sovitut = steet.annaSovitutKayttajat(t2.getTid());
        System.out.println("Tehtävät käyttäjä-id:n perusteella:");
        for (SovittuTehtava st : sovitut) {
            try {
                System.out.println(koot.etsi(st.getKid()).getNimi() + " " + teet.anna(st.getTid()));
            } catch (SailoException e) {
                System.out.println(e.getMessage());
            }
        }
        
        sovitut = steet.annaSovitutTehtavat(k1.getKid());
        System.out.println("\nKäyttäjät tehtävä-id:n perusteella:");
        for (SovittuTehtava st : sovitut) {
            try {
                System.out.println(koot.etsi(st.getKid()).getNimi() + " " + teet.anna(st.getTid()));
            } catch (SailoException e) {
                System.out.println(e.getMessage());
            }
        }
        
    }
}
