package kotitalous;

import java.io.PrintStream;

/**
 * Sovittu tehtävä -luokka
 * - tietää sovitun tehtävän kentät (kayttaja_id, tehtava_id)
 * - osaa tarkistaa tietyn kentän oikeellisuuden
 * - osaa muuttaa 1|1-merkkijonon sovituksi tehtäväksi
 * - osaa antaa merkkijonona i:n kentän tiedot
 * - osaa laittaa merkkijonon i:nneksi kentäksi
 * @author Anniina
 * @version 6.3.2022
 */
public class SovittuTehtava {
    
    private int kid;
    private int tid;
    
    /**
     * @param kayttajaId käyttäjän id
     */
    public void setKayttaja(int kayttajaId) {
        this.kid = kayttajaId;
    }
    
    /**
     * @param tehtavaId tehtävän id
     */
    public void setTehtava(int tehtavaId) {
        this.tid = tehtavaId;
    }
    
    /**
     * @return käyttäjän id
     */
    public int getKid() {
        return this.kid;
    }
    
    /**
     * @return tehtävän id
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
    
    
    @Override
    public String toString() {
        return "käyttäjä-id: " + this.kid + " ja tehtävä-id: " + this.tid;
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
        
        st1.setKayttaja(k1.getKid());
        st2.setKayttaja(k2.getKid());
        st1.setTehtava(t1.getTid());
        st2.setTehtava(t2.getTid());
        
        st1.tulosta(System.out);
        st2.tulosta(System.out);
        
        try {
            System.out.println(koot.etsi(st1.getKid()).getNimi() + " " + teet.anna(st1.getTid()));
            System.out.println(koot.etsi(st2.getKid()).getNimi() + " " + teet.anna(st2.getTid()));
        } catch (IndexOutOfBoundsException | SailoException e) {
            e.printStackTrace();
        }
        
    }
}
