package kotitalous.test;
// Generated by ComTest BEGIN
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import kotitalous.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.03.06 16:30:24 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class SovitutTehtavatTest {



  // Generated by ComTest BEGIN
  /** testAnnaSovitutKayttajat34 */
  @Test
  public void testAnnaSovitutKayttajat34() {    // SovitutTehtavat: 34
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
    List<SovittuTehtava> loytyneet = steet.annaSovitutKayttajat(t1.getTid()); 
    assertEquals("From: SovitutTehtavat line: 73", 1, loytyneet.size()); 
    assertEquals("From: SovitutTehtavat line: 74", true, loytyneet.get(0) == st1); 
  } // Generated by ComTest END
}