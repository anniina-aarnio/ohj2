package kotitalous;

/**
 * Kotitalous-luokka, vastuualueet:
 * Huolehtii Käyttäjät-, Tehtävät-, ja SovitutTehtävät-luokkien välisestä yhteistyöstä
 * Lukee ja kirjoittaa Kotitalouden tiedostoon pyytämällä apua valmistajiltaan
 * 
 * Pääosin kaikki metodit ovat vain "välittäjämetodeja" käyttäjistöön.
 * @author Anniina
 * @version 22.2.2022
 *
 */
public class Kotitalous {
    private final Kayttajat kayttajat = new Kayttajat();
    // private final Tehtavat tehtavat = new Tehtavat();
    // private final Sovitut sovitut = new Sovitut();

    /**
     * Palauttaa käyttäjien määrän
     * @return käyttäjien määrä
     */
    public int getKayttajia() {
        return this.kayttajat.getLkm();
    }
    
    
    /**
     * Lisää kerhoon uuden jäsenen
     * @param kayttaja lisättävä käyttäjä
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     *  Kotitalous kt = new Kotitalous();
     *  Kayttaja aada = new Kayttaja(), ben = new Kayttaja();
     *  aada.rekisteroi(); ben.rekisteroi();
     *  kt.getKayttajia()  === 0;
     *  kt.lisaa(aada); kt.getKayttajia() === 1;
     *  kt.lisaa(ben); kt.getKayttajia() === 2;
     *  kt.lisaa(aada); kt.getKayttajia() === 3;
     *  kt.annaKayttaja(0) === aada;
     *  kt.annaKayttaja(1) === ben;
     *  kt.annaKayttaja(2) === aada;
     *  kt.annaKayttaja(3) === aada; #THROWS IndexOutOfBoundsException
     *  kt.lisaa(aada); kt.getKayttajia() === 4;
     *  kt.lisaa(aada); kt.getKayttajia() === 5;
     *  kt.lisaa(aada);              #THROWS SailoException
     * </pre>
     */
    public void lisaa(Kayttaja kayttaja) throws SailoException {
        this.kayttajat.lisaa(kayttaja);
    }

    
    /**
     * Palauttaa i:nnen jäsenen
     * @param i monesko käyttäjä palautetaan
     * @return viite i:nteen käyttäjään
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Kayttaja annaKayttaja(int i) throws IndexOutOfBoundsException {
        return this.kayttajat.anna(i);
    }

    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kotitalous kt = new Kotitalous();
        
        try {
            // kotitalous.lueTiedostosta("kotitalous");
            Kayttaja aada = new Kayttaja(), ben = new Kayttaja();
            aada.rekisteroi();
            aada.taytaAadaTiedoilla();
            ben.rekisteroi();
            ben.taytaAadaTiedoilla();
            
            kt.lisaa(aada);
            kt.lisaa(ben);
            
            System.out.println(" ================ Kotitalouden testi ================ ");
            for (int i=0; i < kt.getKayttajia(); i++) {
                Kayttaja kayttaja = kt.annaKayttaja(i);
                System.out.println("Jäsen paikassa: " + i);
                kayttaja.tulosta(System.out);
            }
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
