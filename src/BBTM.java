import java.io.IOException;

/**
 * Class for Base Beaver Turing Machine inherit form BaseUtm Class
 * @author Xiao Yunxuan Beijingjiaotong University
 * @version 1.0
 */
public class BBTM extends BaseUTM {

    /**
     * <h2>constructor of BBTM</h2>
     * @param animation choose whether to use the animation
     * @param input the bit string of TM
     * @throws IOException handle IOExeptions
     */
    BBTM(boolean animation, String input) throws IOException {
        super(animation, "00000000000000000000", "busybeaver.desc");
    }
}
