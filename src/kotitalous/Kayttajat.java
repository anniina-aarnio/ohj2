package kotitalous;

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
            Kayttaja[] uusi = new Kayttaja[lkm + 10];
            for (int i = 0; i < lkm; i++) {
                uusi[i] = this.alkiot[i];
            }
            this.alkiot = uusi;
        }
        this.alkiot[lkm] = kayttaja;
        this.lkm++;
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
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kayttajat kayttajat = new Kayttajat();
        
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
    }
}
