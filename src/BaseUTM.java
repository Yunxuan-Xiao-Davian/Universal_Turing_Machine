import utm.HaltState;
import utm.MoveClassical;
import utm.TuringMachine;
import utm.UniversalTuringMachine;
import utmeditor.UTMController;
import utmeditor.UTMEditor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <h1>BaseUTM is the base class of Each type of Turing machine</h1>
 * it integrated provided code form turing machines.jar with my own class
 * it implements UTMControler to give a GUI interface
 * and decides the function to initiate and excute TM
 * @author Xiao Yunxuan Beijingjiaotong University
 * @version 1.0
 */
public class BaseUTM extends UniversalTuringMachine implements UTMController {
    /**choose whether to use animation to represents TM*/
    boolean animation = true;
    /**this argument stands for input bit string of TM*/
    String inputSting;
    /**this argument stands for the path of documents of the rules*/
    String filePath;
    /**this argument stands for the array of rules*/
    String[] rules;
    /**this argument stands for the rejected state of the TM*/
    String rejectState;
    /**this argument stands for the accepted state of the TM*/
    String acceptState;
    /**this argument stands for the initial state of the TM*/
    String initialState;
    /**this argument stands for the initial position of the pointer of the TM*/
    int initialPointer = 0;
    UniversalTuringMachine utm;

    /**
     * <h2>constructor with parameter of BaseUTM class used when used with Command line instruction</h2>
     *  @param animation decides whether the program gives the animation of TM
     *  @param filePath Load a TM from a description file.
     *  @param inputSting the given input that TM runs on
     *  @exception IOException IOException throw the exceptions when it is not able to read rules from target document
     */
    public BaseUTM(boolean animation, String inputSting, String filePath) throws IOException {
        this.animation = animation;
        this.inputSting = inputSting;
        this.filePath = filePath;
        this.readTM();
        this.initiateTM();
        this.excuteTM(this.initialPointer);
        this.utm = this;
    }

    /**
     * <h2>constructor without parameter of BaseUTM class when starting with UTM interface</h2>
     * @exception IOException handle IOException
     */
    public BaseUTM() throws IOException {
        UTMEditor UTMeditor = new UTMEditor();
        UTMeditor.setUTMController(this);
        this.utm = this;
    }

    /**
     * read config files to decide the initial state accepted state and the rejected state
     * also the rules followed by TM
     * decide the initial position of the pointer
     * @exception IOException handle the exceptions of read null file etc.
     */
    public void readTM() throws IOException {
        FileInputStream inputStream = new FileInputStream(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str;
        while((str = bufferedReader.readLine()) != null)
        {
            String[] configStr;
            configStr = str.split("=");
            if(configStr[0].equals("initialState")) {
                initialState = configStr[1];continue;
            }
            if(configStr[0].equals("acceptState")) {
                acceptState = configStr[1];continue;
            }
            if(configStr[0].equals("rejectState")) {
                rejectState = configStr[1];continue;
            }
            if(configStr[0].equals("variant") && configStr[1].equals("BUSY_BEAVER")) {
                initialPointer = 10;
            }
            if(configStr[0].equals("rules")) {
                rules = str.split("=")[1].split("<>");break;
            }
        }
        //close
        inputStream.close();
        bufferedReader.close();
    }

    /**
     * initiate the TM
     * when input is not zero
     */
    public void initiateTM() {
        utm = new UniversalTuringMachine();
        TuringMachine tm0 = new TuringMachine(rules.length, initialState, acceptState, rejectState);
        for(int i = 0; i < rules.length; i++)
        {
            String[] info = rules[i].split(",");
            if (info[4].equals("RESET")){
                tm0.addRule(info[0], info[1].charAt(0), info[2], info[3].charAt(0), MyMoveClassical.valueOf(info[4]));}
            else
                tm0.addRule(info[0], info[1].charAt(0), info[2], info[3].charAt(0), MoveClassical.valueOf(info[4]));

        }
        utm.loadTuringMachine(tm0);
        if(initialPointer!=0) inputSting = "00000000000000000000";
        utm.loadInput(inputSting);
        utm.display();
    }

    /**
     * excute TM according to provided rules
     * @param ini initial position of the pointer of TM
     */
    public void excuteTM(int ini)  {
        for(int i = 0; i < ini; i++) {
            super.moveHead(MoveClassical.RIGHT, false);
        }
        boolean isContine = true;
        while (isContine) {
            for (String[] rule : utm.getTuringMachine().getRules()) {

                if (utm.getTuringMachine().getHead().getCurrentState().equals(rule[0])
                        && utm.getTuringMachine().getTape().get(utm.getTuringMachine().getHead().getCurrentCell())
                        .toString().equals(rule[1])) {
                    utm.writeOnCurrentCell(rule[3].charAt(0));
                    if (rule[4].equals("RESET")) {
                        for(int i = utm.getTuringMachine().getHead().getCurrentCell(); i >0; i--) {
                            utm.moveHead(MoveClassical.LEFT, false);
                        }
                    } else {
                        utm.moveHead(MoveClassical.valueOf(rule[4]), animation);
                    }
                    utm.updateHeadState(rule[2]);
                }
                if (utm.getTuringMachine().getHead().getCurrentState()
                        .equals(utm.getTuringMachine().getAcceptState())) {
                    utm.displayHaltState(HaltState.ACCEPTED);
                    isContine = false;
                    break;
                }
                if (utm.getTuringMachine().getHead().getCurrentState()
                        .equals(utm.getTuringMachine().getRejectState())) {
                    utm.displayHaltState(HaltState.REJECTED);
                    isContine = false;
                    break;

                }
            }
        }
    }

    /**
     * load the rules of TM from rules file
     * @param filePath the file that stands for the rules of TM
     */
    public void loadTuringMachineFrom(String filePath) {
        this.filePath = filePath;

    }

    /**
     * starts running TM according to the given rules
     * @param inputSting the input bit string of TM
     */
    public void runUTM(String inputSting) {
        this.inputSting = inputSting;
        try {
            this.readTM();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        this.initiateTM();
        this.excuteTM(this.initialPointer);
    }
}
