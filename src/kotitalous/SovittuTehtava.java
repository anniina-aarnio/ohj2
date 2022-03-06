package kotitalous;

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
    public int getKayttaja() {
        return this.kid;
    }
    
    /**
     * @return tehtävän id
     */
    public int getTehtava() {
        return this.tid;
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        SovittuTehtava st1 = new SovittuTehtava();
        SovittuTehtava st2 = new SovittuTehtava();
        
        Kayttaja k1 = new Kayttaja();
        Kayttaja k2 = new Kayttaja();
        k1.rekisteroi();
        k2.rekisteroi();
        k1.taytaAadaTiedoilla();
        k2.taytaAadaTiedoilla();
        
        Tehtava t1 = new Tehtava();
        Tehtava t2 = new Tehtava();
        t1.rekisteroi();
        t2.rekisteroi();
        t1.taytaImurointiTiedoilla();
        t2.taytaImurointiTiedoilla();
        
        st1.setKayttaja(1);
        st2.setKayttaja(2);
        st1.setTehtava(1);
        st1.setTehtava(2);
    }
}
