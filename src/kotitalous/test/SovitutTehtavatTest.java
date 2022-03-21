package kotitalous.test;
// Generated by ComTest BEGIN
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import kotitalous.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.03.16 09:51:11 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class SovitutTehtavatTest {



  // Generated by ComTest BEGIN
  /** testLisaa25 */
  @Test
  public void testLisaa25() {    // SovitutTehtavat: 25
    SovitutTehtavat sovitut = new SovitutTehtavat(); 
    SovittuTehtava st = new SovittuTehtava(); 
    SovittuTehtava st2 = new SovittuTehtava(); 
    Kayttaja aada = new Kayttaja(); 
    Tehtava imu = new Tehtava(); 
    Tehtava imu2 = new Tehtava(); 
    aada.taytaAadaTiedoilla(); 
    imu.taytaImurointiTiedoilla(); 
    imu2.taytaImurointiTiedoilla(); 
    aada.rekisteroi(); 
    imu.rekisteroi(); 
    imu2.rekisteroi(); 
    st.setKayttaja(aada); 
    st.setTehtava(imu); 
    st2.setKayttaja(aada); 
    st2.setTehtava(imu2); 
    sovitut.lisaa(st); 
    sovitut.lisaa(st2); 
    List<SovittuTehtava> aadan = sovitut.annaSovitutTehtavat(aada); 
    assertEquals("From: SovitutTehtavat line: 46", 2, aadan.size()); 
    List<SovittuTehtava> imuroinnit = sovitut.annaSovitutKayttajat(imu); 
    assertEquals("From: SovitutTehtavat line: 48", 1, imuroinnit.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaSovitutKayttajat83 */
  @Test
  public void testAnnaSovitutKayttajat83() {    // SovitutTehtavat: 83
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
    steet.lisaa(st1); 
    steet.lisaa(st2); 
    steet.lisaa(st3); 
    steet.setKayttaja(st1, k1); 
    steet.setKayttaja(st2, k2); 
    steet.setKayttaja(st3, k1); 
    steet.setTehtava(st1, t1); 
    steet.setTehtava(st2, t2); 
    steet.setTehtava(st3, t2); 
    List<SovittuTehtava> loytyneet = steet.annaSovitutKayttajat(t1); 
    assertEquals("From: SovitutTehtavat line: 122", 1, loytyneet.size()); 
    assertEquals("From: SovitutTehtavat line: 123", true, loytyneet.get(0) == st1); 
    loytyneet = steet.annaSovitutTehtavat(k2); 
    assertEquals("From: SovitutTehtavat line: 126", 1, loytyneet.size()); 
    assertEquals("From: SovitutTehtavat line: 127", true, loytyneet.get(0) == st2); 
  } // Generated by ComTest END
}