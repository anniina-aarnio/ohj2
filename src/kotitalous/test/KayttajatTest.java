package kotitalous.test;
// Generated by ComTest BEGIN
import java.io.File;
import java.util.Iterator;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import kotitalous.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.04.23 14:13:27 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class KayttajatTest {



  // Generated by ComTest BEGIN
  /** testLisaa43 */
  @Test
  public void testLisaa43() {    // Kayttajat: 43
    Kayttajat kayttajat = new Kayttajat(); 
    Kayttaja aada = new Kayttaja(), ben = new Kayttaja(); 
    assertEquals("From: Kayttajat line: 46", 0, kayttajat.getLkm()); 
    kayttajat.lisaa(aada); assertEquals("From: Kayttajat line: 47", 1, kayttajat.getLkm()); 
    kayttajat.lisaa(ben); assertEquals("From: Kayttajat line: 48", 2, kayttajat.getLkm()); 
    kayttajat.lisaa(aada); assertEquals("From: Kayttajat line: 49", 3, kayttajat.getLkm()); 
    assertEquals("From: Kayttajat line: 50", aada, kayttajat.anna(0)); 
    assertEquals("From: Kayttajat line: 51", ben, kayttajat.anna(1)); 
    assertEquals("From: Kayttajat line: 52", aada, kayttajat.anna(2)); 
    assertEquals("From: Kayttajat line: 53", false, kayttajat.anna(1) == aada); 
    assertEquals("From: Kayttajat line: 54", true, kayttajat.anna(1) == ben); 
    try {
    assertEquals("From: Kayttajat line: 55", aada, kayttajat.anna(3)); 
    fail("Kayttajat: 55 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    kayttajat.lisaa(aada); assertEquals("From: Kayttajat line: 56", 4, kayttajat.getLkm()); 
    kayttajat.lisaa(aada); assertEquals("From: Kayttajat line: 57", 5, kayttajat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testKorvaaTaiLisaa76 */
  @Test
  public void testKorvaaTaiLisaa76() {    // Kayttajat: 76
    Kayttajat kayttajat = new Kayttajat(); 
    Kayttaja k = new Kayttaja(), k2 = new Kayttaja(); 
    k.rekisteroi(); k2.rekisteroi(); 
    assertEquals("From: Kayttajat line: 80", 0, kayttajat.getLkm()); 
    kayttajat.korvaaTaiLisaa(k); assertEquals("From: Kayttajat line: 81", 1, kayttajat.getLkm()); 
    kayttajat.korvaaTaiLisaa(k2); assertEquals("From: Kayttajat line: 82", 2, kayttajat.getLkm()); 
    Kayttaja k3 = new Kayttaja(); 
    try {
    k3 = k.clone(); 
    } catch (CloneNotSupportedException e) {
    System.out.println(e.getMessage()); 
    }
    k3.aseta(1, "Aada"); 
    Iterator<Kayttaja> it = kayttajat.iterator(); 
    assertEquals("From: Kayttajat line: 91", true, it.next() == k); 
    kayttajat.korvaaTaiLisaa(k3); assertEquals("From: Kayttajat line: 92", 2, kayttajat.getLkm()); 
    it = kayttajat.iterator(); 
    Kayttaja k0 = it.next(); 
    assertEquals("From: Kayttajat line: 95", k3, k0); 
    assertEquals("From: Kayttajat line: 96", false, k0 == k); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPoista115 */
  @Test
  public void testPoista115() {    // Kayttajat: 115
    Kayttajat kt = new Kayttajat(); 
    assertEquals("From: Kayttajat line: 117", 0, kt.getLkm()); 
    Kayttaja k = new Kayttaja(); 
    k.rekisteroi(); 
    kt.lisaa(k); 
    assertEquals("From: Kayttajat line: 121", 1, kt.getLkm()); 
    kt.poista(k); 
    assertEquals("From: Kayttajat line: 123", 0, kt.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi156 
   * @throws SailoException when error
   */
  @Test
  public void testEtsi156() throws SailoException {    // Kayttajat: 156
    Kayttajat koot = new Kayttajat(); 
    Kayttaja k1 = new Kayttaja(); k1.rekisteroi(); 
    Kayttaja k2 = new Kayttaja(); k2.rekisteroi(); 
    koot.lisaa(k1); koot.lisaa(k2); 
    assertEquals("From: Kayttajat line: 163", k1.toString(), koot.etsi(k1.getKid()).toString()); 
    assertEquals("From: Kayttajat line: 164", k2.toString(), koot.etsi(k2.getKid()).toString()); 
    try {
    koot.etsi(-10); 
    fail("Kayttajat: 165 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta201 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta201() throws SailoException {    // Kayttajat: 201
    Kayttajat kayttajat = new Kayttajat(); 
    Kayttaja aada1 = new Kayttaja(); aada1.rekisteroi(); aada1.taytaAadaTiedoilla(); 
    Kayttaja aada2 = new Kayttaja(); aada2.rekisteroi(); aada2.taytaAadaTiedoilla(); 
    Kayttaja aada3 = new Kayttaja(); aada3.rekisteroi(); aada3.taytaAadaTiedoilla(); 
    String tiedNimi = "testiKotitalous"; 
    File ftied = new File(tiedNimi + "/kayttajat.dat"); 
    ftied.delete(); 
    try {
    kayttajat.lueTiedostosta(tiedNimi); 
    fail("Kayttajat: 212 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    kayttajat.lisaa(aada1); 
    kayttajat.lisaa(aada2); 
    kayttajat.lisaa(aada3); 
    kayttajat.tallenna(tiedNimi); 
    kayttajat = new Kayttajat(); 
    kayttajat.lueTiedostosta(tiedNimi); 
    Iterator<Kayttaja> i = kayttajat.iterator(); 
    assertEquals("From: Kayttajat line: 220", aada1.toString(), i.next().toString()); 
    assertEquals("From: Kayttajat line: 221", aada2.toString(), i.next().toString()); 
    assertEquals("From: Kayttajat line: 222", aada3.toString(), i.next().toString()); 
    assertEquals("From: Kayttajat line: 223", false, i.hasNext()); 
    kayttajat.poista(aada1); 
    assertEquals("From: Kayttajat line: 225", 2, kayttajat.getLkm()); 
    kayttajat.tallenna(tiedNimi); 
    assertEquals("From: Kayttajat line: 227", true, ftied.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testIterator278 
   * @throws SailoException when error
   */
  @Test
  public void testIterator278() throws SailoException {    // Kayttajat: 278
    Kayttajat kayttajat = new Kayttajat(); 
    Kayttaja aada1 = new Kayttaja(), aada2 = new Kayttaja(); 
    aada1.rekisteroi(); aada2.rekisteroi(); 
    kayttajat.lisaa(aada1); 
    kayttajat.lisaa(aada2); 
    kayttajat.lisaa(aada1); 
    StringBuffer ids = new StringBuffer(30); 
    for (Kayttaja kayttaja : kayttajat)
    ids.append(" " + kayttaja.getKid()); 
    String tulos = " " + aada1.getKid() + " " + aada2.getKid() + " " + aada1.getKid(); 
    assertEquals("From: Kayttajat line: 295", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Kayttaja> i = kayttajat.iterator(); i.hasNext(); ) {
    Kayttaja k = i.next(); 
    ids.append(" " + k.getKid()); 
    }
    assertEquals("From: Kayttajat line: 303", tulos, ids.toString()); 
    Iterator<Kayttaja> i = kayttajat.iterator(); 
    assertEquals("From: Kayttajat line: 306", true, i.next() == aada1); 
    assertEquals("From: Kayttajat line: 307", true, i.next() == aada2); 
    assertEquals("From: Kayttajat line: 308", true, i.next() == aada1); 
    try {
    i.next(); 
    fail("Kayttajat: 310 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END
}