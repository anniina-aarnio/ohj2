package kanta.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import kotitalous.Kayttaja;
import kanta.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.04.21 11:43:41 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class TietueTest {



  // Generated by ComTest BEGIN
  /** testInt15 */
  @Test
  public void testInt15() {    // Tietue: 15
    Kayttaja k = new Kayttaja(); 
    assertEquals("From: Tietue line: 18", 3, k.getKenttia()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testInt27 */
  @Test
  public void testInt27() {    // Tietue: 27
    Kayttaja k = new Kayttaja(); 
    assertEquals("From: Tietue line: 29", 1, k.ekaKentta()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testString39 */
  @Test
  public void testString39() {    // Tietue: 39
    Kayttaja k = new Kayttaja(); 
    assertEquals("From: Tietue line: 41", "Nimi", k.getKysymys(1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testString51 */
  @Test
  public void testString51() {    // Tietue: 51
    Kayttaja k = new Kayttaja(); 
    k.parse("  3 |   Aada    | 35   "); 
    assertEquals("From: Tietue line: 54", "3", k.anna(0)); 
    assertEquals("From: Tietue line: 55", "Aada", k.anna(1)); 
    assertEquals("From: Tietue line: 56", "35", k.anna(2)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testString70 */
  @Test
  public void testString70() {    // Tietue: 70
    Kayttaja k = new Kayttaja(); 
    assertEquals("From: Tietue line: 72", "Anna positiivinen kokonaisluku", k.aseta(2, "kissa")); 
    assertEquals("From: Tietue line: 73", null, k.aseta(2, "13")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testTietue84 
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testTietue84() throws CloneNotSupportedException {    // Tietue: 84
    Kayttaja k = new Kayttaja(); 
    k.parse("  3 |   Aada    | 35   "); 
    Object kopio = k.clone(); 
    assertEquals("From: Tietue line: 89", k.toString(), kopio.toString()); 
    k.parse("   1 |  Ben   |  35  "); 
    assertEquals("From: Tietue line: 91", false, kopio.toString().equals(k.toString())); 
    assertEquals("From: Tietue line: 92", true, kopio instanceof Kayttaja); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testString119 */
  @Test
  public void testString119() {    // Tietue: 119
    Kayttaja k = new Kayttaja(); 
    k.parse("  3 |   Aada    | 35   "); 
    assertEquals("From: Tietue line: 122", "3|Aada|35", k.toString());  // esimerkissä oli =R= ja tolppia ennen aina \\|
  } // Generated by ComTest END
}