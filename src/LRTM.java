import java.io.IOException;

/**
 * Class for Left-Reset Turing Machine inherit form BaseUtm Class
 * @author Xiao Yunxuan Beijingjiaotong University
 * @version 1.0
 */
public class LRTM extends BaseUTM {

    /**
     * <h2>constructor of LRTM<h2/>
     * @param animation choose whether to use the animation
     * @param input the bit string of TM
     * @throws IOException handle IOExeptions
     */
    LRTM(boolean animation, String input) throws IOException {
        super(animation, input, "leftreset.desc");
    }
}
