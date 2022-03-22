package kotitalous.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Testaa kotitalouden testit
 * @author Anniina
 * @version 7.3.2022
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ KayttajaTest.class, KayttajatTest.class, KotitalousTest.class,
        SovitutTehtavatTest.class, TehtavatTest.class, SovittuTehtavaTest.class })
public class AllTests {
    //
}
