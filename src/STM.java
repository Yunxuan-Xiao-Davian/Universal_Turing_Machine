import java.io.IOException;

/**
 * <h1>Class for STM inherit form BaseUtm Class</h1>
 * @author Xiao Yunxuan Beijingjiaotong University
 * @version 1.0
 */
public class STM extends BaseUTM {
    /**
     * <h2>constructor of STM<h2/>
     * @param animation choose whether to use the animation
     * @param input the bit string of TM
     * @throws IOException handle IOExeptions
     */
    STM(boolean animation, String input) throws IOException {
        super(animation, input, "simple-tm.desc");
    }
}
