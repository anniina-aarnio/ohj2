package kotitalous;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Tehtävät-luokka:
 * - pitää yllä varsinaista tehtävärekisteriä, eli osaa lisätä ja poistaa tehtävän
 * - lukee ja kirjoittaa tehtävät tiedostoon
 * - osaa etsiä ja lajitella
 * @author Anniina
 * @version 6.3.2022
 */
public class Tehtavat {
    
    private final Collection<Tehtava> alkiot = new ArrayList<Tehtava>();
    
    /**
     * Lisää tehtävän
     * @param tehtava lisättävä tehtävä
     */
    public void lisaa(Tehtava tehtava) {
        this.alkiot.add(tehtava);
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
     * teet.anna(t1.getTid()) === t1;
     * teet.anna(200); #THROWS SailoException
     * </pre>
     */
    public Tehtava anna(int tehtavaId) throws SailoException {
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
     * Testaa tehtävät-luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tehtavat teet = new Tehtavat();
        Tehtava imurointi1 = new Tehtava();
        Tehtava imurointi2 = new Tehtava();
        imurointi1.rekisteroi();
        imurointi2.rekisteroi();
        imurointi1.taytaImurointiTiedoilla();
        imurointi2.taytaImurointiTiedoilla();
        
        teet.lisaa(imurointi1);
        teet.lisaa(imurointi2);
        
        System.out.println("=================Tehtävä-testi===============");
        try {
            teet.anna(1).tulosta(System.out);
            teet.anna(2).tulosta(System.out);
            teet.anna(3).tulosta(System.out);

        } catch (SailoException e) {
            System.out.println(e.getMessage());
        }

    }

}
