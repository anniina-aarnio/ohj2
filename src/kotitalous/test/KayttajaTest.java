package kotitalous.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import kotitalous.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.02.22 09:17:52 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class KayttajaTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi29 */
  @Test
  public void testRekisteroi29() {    // Kayttaja: 29
    Kayttaja aku1 = new Kayttaja(); 
    aku1.getKid(); 
    aku1.rekisteroi(); 
    Kayttaja aku2 = new Kayttaja(); 
    aku2. rekisteroi(); 
    int n1 = aku1.getKid(); 
    int n2 = aku2.getKid(); 
    assertEquals("From: Kayttaja line: 37", n2-1, n1); 
    assertEquals("From: Kayttaja line: 38", n1+1, n2); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetKid51 */
  @Test
  public void testGetKid51() {    // Kayttaja: 51
    Kayttaja aku1 = new Kayttaja(); 
    assertEquals("From: Kayttaja line: 53", 0, aku1.getKid()); 
  } // Generated by ComTest END
}