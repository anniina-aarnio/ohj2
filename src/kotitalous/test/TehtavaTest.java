package kotitalous.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import kotitalous.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.04.25 09:25:31 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class TehtavaTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi52 */
  @Test
  public void testRekisteroi52() {    // Tehtava: 52
    Tehtava t1 = new Tehtava(); 
    assertEquals("From: Tehtava line: 54", 0, t1.getTid()); 
    t1.rekisteroi(); 
    Tehtava t2 = new Tehtava(); 
    t2.rekisteroi(); 
    int n1 = t1.getTid(); 
    int n2 = t2.getTid(); 
    assertEquals("From: Tehtava line: 60", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnna124 */
  @Test
  public void testAnna124() {    // Tehtava: 124
    Tehtava t = new Tehtava(); 
    t.parse("3 |   Imurointi   |  40   |   10   "); 
    assertEquals("From: Tehtava line: 127", "3", t.anna(0)); 
    assertEquals("From: Tehtava line: 128", "Imurointi", t.anna(1)); 
    assertEquals("From: Tehtava line: 129", "40", t.anna(2)); 
    assertEquals("From: Tehtava line: 130", "10", t.anna(3)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAseta153 */
  @Test
  public void testAseta153() {    // Tehtava: 153
    Tehtava t = new Tehtava(); 
    assertEquals("From: Tehtava line: 155", "Nimi ei saa olla tyhjä", t.aseta(1, "")); 
    assertEquals("From: Tehtava line: 156", "Merkki | ei ole sallittu", t.aseta(1, "Imurointi |")); 
    assertEquals("From: Tehtava line: 157", "Anna kokonaisluku, minuutteja", t.aseta(2, "kissa")); 
    assertEquals("From: Tehtava line: 158", null, t.aseta(2, "30")); 
    assertEquals("From: Tehtava line: 159", "Suurin sallittu luku 18", t.aseta(3, "100")); 
    assertEquals("From: Tehtava line: 160", "Anna kokonaisluku", t.aseta(3, "-1")); 
    assertEquals("From: Tehtava line: 161", null, t.aseta(3, "3")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEhto199 */
  @Test
  public void testEhto199() {    // Tehtava: 199
    Tehtava t1 = new Tehtava(); 
    t1.parse("1 |  Imurointi | 23 | 10"); 
    assertEquals("From: Tehtava line: 202", true, t1.ehto("Imurointi")); 
    assertEquals("From: Tehtava line: 203", true, t1.ehto("imurointi")); 
    assertEquals("From: Tehtava line: 204", true, t1.ehto("mur")); 
    assertEquals("From: Tehtava line: 205", true, t1.ehto("")); 
    assertEquals("From: Tehtava line: 206", false, t1.ehto("kissa")); 
    assertEquals("From: Tehtava line: 207", false, t1.ehto(null)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse220 */
  @Test
  public void testParse220() {    // Tehtava: 220
    Tehtava tehtava = new Tehtava(); 
    tehtava.parse("   2   |  Imurointi     | 30 |  10    "); 
    assertEquals("From: Tehtava line: 223", 2, tehtava.getTid()); 
    assertEquals("From: Tehtava line: 224", "2|Imurointi|30|10", tehtava.toString()); 
    tehtava.rekisteroi(); 
    int n = tehtava.getTid(); 
    tehtava.parse(""+(n+20)); 
    tehtava.rekisteroi(); 
    assertEquals("From: Tehtava line: 230", n+20+1, tehtava.getTid()); 
    assertEquals("From: Tehtava line: 231", "" + (n+20+1) + "|Imurointi|30|10", tehtava.toString()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testClone247 
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testClone247() throws CloneNotSupportedException {    // Tehtava: 247
    Tehtava t = new Tehtava(); 
    t.parse("3 |   Imurointi   |  40   |   10   "); 
    Tehtava kopio = t.clone(); 
    assertEquals("From: Tehtava line: 252", t.toString(), kopio.toString()); 
    t.parse("  4 |  Moppaus |  20 | 10   "); 
    assertEquals("From: Tehtava line: 254", false, kopio.toString().equals(t.toString())); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEquals283 */
  @Test
  public void testEquals283() {    // Tehtava: 283
    Tehtava t1 = new Tehtava(); 
    t1.parse(" 2  |   Imurointi | 23 | 11 "); 
    Tehtava t2 = new Tehtava(); 
    t2.parse(" 3  | Roskien vienti     | 2 | 2"); 
    Tehtava t3 = new Tehtava(); 
    t3.parse("  3 |  Roskien vienti |2|2"); 
    assertEquals("From: Tehtava line: 291", false, t1.equals(t2)); 
    assertEquals("From: Tehtava line: 292", false, t2.equals(t1)); 
    assertEquals("From: Tehtava line: 293", false, t1.equals(t3)); 
    assertEquals("From: Tehtava line: 294", true, t2.equals(t3)); 
    assertEquals("From: Tehtava line: 295", true, t3.equals(t3)); 
  } // Generated by ComTest END
}