
import java.io.IOException;

/**
 * <h1>Main class of the program</h1>
 * @author Xiao Yunxuan Beijingjiaotong University
 * @version 1.0
 */
public class UTMmain {

    /**
     * <h2>the entry function of the program</h2>
     * @param args environmental arguments
     * @throws IOException handle IOExceptions
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            new BaseUTM();
        } else {
            String input;
            String desPath = args[0];
            System.out.println(desPath);
            boolean animation = false;
            input = args[1];
            if (args[2].equals("--animation")) {
                animation = true;
            }
            System.out.println(args[0]);
            new BaseUTM(animation, input, desPath);
        }

    }
}