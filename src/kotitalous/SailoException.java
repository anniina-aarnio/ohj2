package kotitalous;

/**
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 21.2.2022
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Poikkeuksen muodostaja, jolle tuodaan poikkeuksessa käytettävä viesti
     * @param viesti poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
