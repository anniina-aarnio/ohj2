package kanta;

/**
 * Rajapinta tietueelle, johon voi taulukon avulla rakentaa
 * "attribuutit"
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 13.4.2022
 *
 */
public interface Tietue {

    /**
     * @return tietueen kenttien lukumäärä
     * @example
     * <pre name="test">
     *  #import kotitalous.Kayttaja;
     *  Kayttaja k = new Kayttaja();
     *  k.getKenttia() === 3;
     * </pre>
     */
    public abstract int getKenttia();


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     * @example
     * <pre name="test">
     *  Kayttaja k = new Kayttaja();
     *  k.ekaKentta() === 1;
     * </pre>
     */
    public abstract int ekaKentta();
    
    
    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     * @example
     * <pre name="test">
     * Kayttaja k = new Kayttaja();
     * k.getKysymys(1) === "Nimi";
     * </pre>
     */
    public abstract String getKysymys(int k);
    
        
    /**
     * @param k minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *  Kayttaja k = new Kayttaja();
     *  k.parse("  3 |   Aada    | 35   ");
     *  k.anna(0) === "3";
     *  k.anna(1) === "Aada";
     *  k.anna(2) === "35";
     * </pre>
     */
    public abstract String anna(int k);

    
    /**
     * Asetetaan valitun kentän sisältö.
     * Mikäli asettaminen onnistuu, palautetaan null,
     * muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null, jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     * Kayttaja k = new Kayttaja();
     * k.aseta(2, "kissa") === "Anna positiivinen kokonaisluku";
     * k.aseta(2, "13") === null;
     * </pre>
     */
    public abstract String aseta(int k, String s);
    
    
    /**
     * Tehdään tietueesta identtinen klooni
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     * Kayttaja k = new Kayttaja();
     * k.parse("  3 |   Aada    | 35   ");
     * Object kopio = k.clone();
     * kopio.toString() === k.toString();
     * k.parse("   1 |  Ben   |  35  ");
     * kopio.toString().equals(k.toString()) === false;
     * kopio instanceof Kayttaja === true;
     * </pre>
     */
    public abstract Tietue clone() throws CloneNotSupportedException;
    
    
//    /**
//     * Vastaa, onko annetussa tietueessa virheitä
//     * @return true, jos on virheitä, false jos kaikki kunnossa
//     * @example
//     * <pre name="test">
//     * Kayttaja k = new Kayttaja();
//     * k.parse("   3 |     Aada | 35   ");
//     * k.onkoVirheita() === false;
//     * k.parse("   3  |  |    20");
//     * k.onkoVirheita() === true;
//     * k.parse("3 | Aada | -1");
//     * k.onkoVirheita() === true;
//     * </pre>
//     */
//    public abstract boolean onkoVirheita();       TODO tarvitaanko?

    
    /**
     * Palauttaa tietueen merkkijonona jonka voi tallentaa tiedostoon
     * @return tietue tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     * Kayttaja k = new Kayttaja();
     * k.parse("  3 |   Aada    | 35   ");
     * k.toString() === "3|Aada|35";        // esimerkissä oli =R= ja tolppia ennen aina \\|
     * </pre>
     */
    @Override
    public abstract String toString();

}
