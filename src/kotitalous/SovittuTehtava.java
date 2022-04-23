package kotitalous;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Sovittu tehtävä -luokka
 * - tietää sovitun tehtävän kentät (kayttaja_id, tehtava_id)
 * - osaa tarkistaa tietyn kentän oikeellisuuden
 * - osaa muuttaa 1|1-merkkijonon sovituksi tehtäväksi
 * - osaa antaa merkkijonona i:n kentän tiedot
 * - osaa laittaa merkkijonon i:nneksi kentäksi
 * Uudessa sovitussa tehtävässä id-arvot ovat -1, mikä tarkoittaa,
 * ettei tehtävää tai käyttäjää ole sovittu
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 21.4.2022
 */
public class SovittuTehtava {
    
    private int kid;        // käyttäjä-id
    private int tid;        // tehtävä-id
    
    
    /**
     * Asettaa käyttäjän sovittuun tehtävään
     * @param kayttaja käyttäjän id
     * @example
     * <pre name="test">
     * Kayttaja aada = new Kayttaja();
     * aada.rekisteroi();
     * aada.taytaAadaTiedoilla();
     * SovittuTehtava st = new SovittuTehtava();
     * st.setKayttaja(aada);
     * st.getKid() === aada.getKid();
     * </pre>
     * 
     */
    public void setKayttaja(Kayttaja kayttaja) {
        this.kid = kayttaja.getKid();
    }
    
    
    /**
     * Asettaa tehtävän sovittuun tehtävään
     * @param tehtava asetettava tehtävä
     * @example
     * <pre name="test">
     * Tehtava t1 = new Tehtava();
     * t1.rekisteroi();
     * t1.taytaImurointiTiedoilla();
     * SovittuTehtava st = new SovittuTehtava();
     * st.setTehtava(t1);
     * st.getTid() === t1.getTid();
     * </pre>
     */
    public void setTehtava(Tehtava tehtava) {
        this.tid = tehtava.getTid();
    }
    
    
    /**
     * Palauttaa sovittuun tehtävään tallennetun käyttäjä-id:n
     * @return käyttäjän id
     * @example
     * <pre name="test">
     * SovittuTehtava st = new SovittuTehtava();
     * st.getKid() === 0;
     * Kayttaja aada = new Kayttaja();
     * aada.rekisteroi();
     * st.setKayttaja(aada);
     * st.getKid() === aada.getKid();
     * </pre>
     */
    public int getKid() {
        return this.kid;
    }
    
    
    /**
     * Palauttaa sovittuun tehtävään tallennetun tehtävä-id:n
     * @return tehtävän id
     * @example
     * <pre name="test">
     * SovittuTehtava st = new SovittuTehtava();
     * st.getTid() === 0;
     * Tehtava t1 = new Tehtava();
     * t1.rekisteroi();
     * st.setTehtava(t1);
     * st.getTid() === t1.getTid();
     * </pre>
     * 
     */
    public int getTid() {
        return this.tid;
    }
    
    
    /**
     * Tulostetaan sovitun tehtävän tiedot
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.toString());
    }
    
    
    /**
     * @return palauttaa tiedon muodossa: "KäyttäjäID|TehtäväID" esim. "1|3"
     * @example
     * <pre name="test">
     * SovittuTehtava st = new SovittuTehtava();
     * st.toString() === "0|0";
     * </pre>
     */
    @Override
    public String toString() {
        return String.format("%d|%d", this.kid, this.tid);
    }
    
    
    /**
     * Selvittää sovitun tehtävän tiedot | erotellusta merkkijonosta.
     * @param rivi josta sovitun tehtävän tiedot otetaan
     * @example
     * <pre name="test">
     *  SovittuTehtava st = new SovittuTehtava();
     *  st.parse("  1   |   2");
     *  st.getKid() === 1;
     *  st.getTid() === 2;
     *  st.toString() === "1|2";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        this.kid = Mjonot.erota(sb,  '|', this.kid);
        this.tid = Mjonot.erota(sb, '|', this.tid);
    }
    
    
    /**
     * Vertaa tätä ja annettua sovittua tehtävää toisiinsa
     * @param st verrattava sovittu tehtävä
     * @return true, jos samat, false jos eri
     */
    public boolean equals(SovittuTehtava st) {
        if (st == null) return false;
        return st.toString() == this.toString();
    }
    
    
    @Override
    public boolean equals(Object st) {
        if (st instanceof SovittuTehtava ) return equals((SovittuTehtava)st);
        return false;
    }
    
    
    @Override
    public int hashCode() {
        return 1000 * this.kid + 1000 / this.tid;
    }


    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        SovittuTehtava st1 = new SovittuTehtava();
        SovittuTehtava st2 = new SovittuTehtava();
        
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
        st1.setTehtava(t1);
        st2.setTehtava(t2);
        
        st1.tulosta(System.out);
        st2.tulosta(System.out);
        
        SovittuTehtava st4 = new SovittuTehtava();
        st4.parse("3|6");
        
        try {
            System.out.println(koot.etsi(st1.getKid()).getNimi() + " " + teet.etsi(st1.getTid()));
            System.out.println(koot.etsi(st2.getKid()).getNimi() + " " + teet.etsi(st2.getTid()));
        } catch (IndexOutOfBoundsException | SailoException e) {
            e.printStackTrace();
        }
        
        System.out.println(st4);
        
    }
}
