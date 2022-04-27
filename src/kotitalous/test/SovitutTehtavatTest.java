package kotitalous.test;
// Generated by ComTest BEGIN
import java.util.*;
import java.io.File;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.*;
import kotitalous.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.04.23 12:00:08 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class SovitutTehtavatTest {



  // Generated by ComTest BEGIN
  /** testLisaa33 */
  @Test
  public void testLisaa33() {    // SovitutTehtavat: 33
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
    assertEquals("From: SovitutTehtavat line: 54", 2, aadan.size()); 
    List<SovittuTehtava> imuroinnit = sovitut.annaSovitutKayttajat(imu); 
    assertEquals("From: SovitutTehtavat line: 56", 1, imuroinnit.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPoista71 */
  @Test
  public void testPoista71() {    // SovitutTehtavat: 71
    SovitutTehtavat steet = new SovitutTehtavat(); 
    SovittuTehtava st1 = new SovittuTehtava(); 
    SovittuTehtava st2 = new SovittuTehtava(); 
    Kayttaja k1 = new Kayttaja(); k1.rekisteroi(); 
    Kayttaja k2 = new Kayttaja(); k2.rekisteroi(); 
    Tehtava t1 = new Tehtava(); t1.rekisteroi(); 
    st1.setKayttaja(k1); st2.setKayttaja(k2); 
    st1.setTehtava(t1); st2.setTehtava(t1); 
    steet.lisaa(st1); steet.lisaa(st2); 
    List<SovittuTehtava> sovitutT1 = steet.annaSovitutKayttajat(t1); 
    assertEquals("From: SovitutTehtavat line: 83", 2, sovitutT1.size()); 
    assertEquals("From: SovitutTehtavat line: 84", st1.toString(), sovitutT1.get(0).toString()); 
    steet.poista(k1, t1); 
    sovitutT1 = steet.annaSovitutKayttajat(t1); 
    assertEquals("From: SovitutTehtavat line: 88", st2.toString(), sovitutT1.get(0).toString()); 
    steet.poista(st2); 
    sovitutT1 = steet.annaSovitutKayttajat(t1); 
    assertEquals("From: SovitutTehtavat line: 92", 0, sovitutT1.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaSovitutKayttajat148 */
  @Test
  public void testAnnaSovitutKayttajat148() {    // SovitutTehtavat: 148
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
    assertEquals("From: SovitutTehtavat line: 187", 1, loytyneet.size()); 
    assertEquals("From: SovitutTehtavat line: 188", true, loytyneet.get(0) == st1); 
    loytyneet = steet.annaSovitutTehtavat(k2); 
    assertEquals("From: SovitutTehtavat line: 191", 1, loytyneet.size()); 
    assertEquals("From: SovitutTehtavat line: 192", true, loytyneet.get(0) == st2); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta225 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta225() throws SailoException {    // SovitutTehtavat: 225
    SovitutTehtavat steet = new SovitutTehtavat(); 
    SovittuTehtava st1 = new SovittuTehtava(); 
    SovittuTehtava st2 = new SovittuTehtava(); 
    SovittuTehtava st3 = new SovittuTehtava(); 
    String tiedNimi = "testiKotitalous"; 
    File ftied = new File(tiedNimi + "/sovitut.dat"); 
    ftied.delete(); 
    try {
    steet.lueTiedostosta(tiedNimi); 
    fail("SovitutTehtavat: 237 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
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
    st1.setKayttaja(k1); st1.setTehtava(t1); 
    st2.setKayttaja(k2); st2.setTehtava(t2); 
    st3.setKayttaja(k1); st3.setTehtava(t2); 
    steet.lisaa(st1); 
    steet.lisaa(st2); 
    steet.lisaa(st3); 
    steet.tallenna(tiedNimi); 
    steet = new SovitutTehtavat(); 
    steet.lueTiedostosta(tiedNimi); 
    Iterator<SovittuTehtava> i = steet.iterator(); 
    assertEquals("From: SovitutTehtavat line: 265", st1.toString(), i.next().toString()); 
    assertEquals("From: SovitutTehtavat line: 266", st2.toString(), i.next().toString()); 
    assertEquals("From: SovitutTehtavat line: 267", st3.toString(), i.next().toString()); 
    assertEquals("From: SovitutTehtavat line: 268", false, i.hasNext()); 
    steet.lisaa(st1); 
    steet.tallenna(tiedNimi); 
    assertEquals("From: SovitutTehtavat line: 271", true, ftied.delete()); 
  } // Generated by ComTest END
}