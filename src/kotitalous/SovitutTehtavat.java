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
    
    private final Collection<SovittuTehtava> alkiot = new ArrayList<SovittuTehtava>();  // pitäisikö olla hashmap??
    
    
    /**
     * Lisää sovitun tehtävän listaan
     * @param sovittutehtava annettu tehtävä
     * @example
     * <pre name="test">
     * SovitutTehtavat sovitut = new SovitutTehtavat();
     * SovittuTehtava st = new SovittuTehtava();
     * SovittuTehtava st2 = new SovittuTehtava();
     * Kayttaja aada = new Kayttaja();
     * Tehtava imu = new Tehtava();
     * Tehtava imu2 = new Tehtava();
     * aada.taytaAadaTiedoilla();
     * imu.taytaImurointiTiedoilla();
     * imu2.taytaImurointiTiedoilla();
     * aada.rekisteroi();
     * imu.rekisteroi();
     * imu2.rekisteroi();
     * 
     * st.setKayttaja(aada);
     * st.setTehtava(imu);
     * st2.setKayttaja(aada);
     * st2.setTehtava(imu2);
     * sovitut.lisaa(st);
     * sovitut.lisaa(st2);
     * List<SovittuTehtava> aadan = sovitut.annaSovitutTehtavat(aada);
     * aadan.size() === 2;
     * List<SovittuTehtava> imuroinnit = sovitut.annaSovitutKayttajat(imu);
     * imuroinnit.size() === 1;
     * </pre>
     */
    public void lisaa(SovittuTehtava sovittutehtava) {
        this.alkiot.add(sovittutehtava);
    }
    
    

    /**
     * Lisää sovittuun tehtävään käyttäjän (id:n).
     * @param st sovittu tehtävä, jota muokataan
     * @param kayttaja käyttäjä, jonka id halutaan lisätä
     */
    public void setKayttaja(SovittuTehtava st, Kayttaja kayttaja) {
        st.setKayttaja(kayttaja);
    }
    
    
    
    /**
     * Lisää sovittuun tehtävään tehtävän(id:n).
     * @param st sovittu tehtävä, jota muokataan
     * @param tehtava tehtävä, jonka id halutaan lisätä
     */
    public void setTehtava(SovittuTehtava st, Tehtava tehtava) {
        st.setTehtava(tehtava);
    }
    
    
    /**
     * Etsii käyttäjät tehtävä-id:n perusteella
     * @param tehtava tehtävä-id, jonka perusteella etsitään
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
     *   steet.lisaa(st1);
     *   steet.lisaa(st2);
     *   steet.lisaa(st3);
     *   
     *   steet.setKayttaja(st1, k1);
     *   steet.setKayttaja(st2, k2);
     *   steet.setKayttaja(st3, k1);
     *   steet.setTehtava(st1, t1);
     *   steet.setTehtava(st2, t2);
     *   steet.setTehtava(st3, t2);
     *   
     *   List<SovittuTehtava> loytyneet = steet.annaSovitutKayttajat(t1);
     *   loytyneet.size() === 1;
     *   loytyneet.get(0) == st1 === true;
     *   
     *   loytyneet = steet.annaSovitutTehtavat(k2);
     *   loytyneet.size() === 1;
     *   loytyneet.get(0) == st2 === true;
     *   
     * </pre>
     */
    public List<SovittuTehtava> annaSovitutKayttajat(Tehtava tehtava) {
        List<SovittuTehtava> loydetyt = new ArrayList<SovittuTehtava>();
        for (SovittuTehtava st : this.alkiot) {
            if (st.getTid() == tehtava.getTid()) loydetyt.add(st);
        }
        return loydetyt;
    }
    
    
    /**
     * Etsii tehtävät käyttäjän perusteella
     * @param kayttaja käyttäjä, jonka perusteella etsitään
     * @return kaikki tehtävät, joilla annettu käyttäjä on
     * (testattu "annaSovitutKayttajat"-metodin testeissä)
     */
    public List<SovittuTehtava> annaSovitutTehtavat(Kayttaja kayttaja) {
        List<SovittuTehtava> loydetyt = new ArrayList<SovittuTehtava>();
        for (SovittuTehtava st : this.alkiot) {
            if (st.getKid() == kayttaja.getKid()) loydetyt.add(st);
        }
        return loydetyt;
    }
    
    
    /**
     * Testataan Sovitut tehtävät -luokkaa
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
        
        st1.setKayttaja(k1);
        st2.setKayttaja(k2);
        st3.setKayttaja(k1);
        st1.setTehtava(t1);
        st2.setTehtava(t2);
        st3.setTehtava(t2);
                
        steet.lisaa(st1);
        steet.lisaa(st2);
        steet.lisaa(st3);
        
        List<SovittuTehtava> sovitut = steet.annaSovitutKayttajat(t2);
        System.out.println("Käyttäjät tehtävän perusteella:");
        for (SovittuTehtava st : sovitut) {
            try {
                System.out.println(koot.etsi(st.getKid()).getNimi() + " " + teet.etsi(st.getTid()));
            } catch (SailoException e) {
                System.out.println(e.getMessage());
            }
        }
        
        sovitut = steet.annaSovitutTehtavat(k1);
        System.out.println("\nTehtävät käyttäjän perusteella:");
        for (SovittuTehtava st : sovitut) {
            try {
                System.out.println(koot.etsi(st.getKid()).getNimi() + " " + teet.etsi(st.getTid()));
            } catch (SailoException e) {
                System.out.println(e.getMessage());
            }
        }
        
        for (SovittuTehtava st : sovitut) {
            System.out.println(st);
        }
        
    }


}
