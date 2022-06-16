import ij.Prefs;
import ij.gui.GenericDialog;

public class DialogBox {

    protected int windowSize;

    protected int derivativeOrder;

    protected Tools.Axis axis;

    public DialogBox() {
        createDialogBox();
    }

    public void createDialogBox() {

        Prefs prefs = new Prefs();

        double initWindowSize = prefs.getDouble(".nthorderderivative.windowsize",5.0);

        double initDerivativeOrder = prefs.getDouble(".nthorderderivative.derivativeorder",2.0);

        String initAxis = prefs.getString(".nthorderderivative.axis", "Z");

        GenericDialog genericDialog = new GenericDialog("Configuration");

        genericDialog.addMessage("Derivative");

        String[] axes = new String[3];

        axes[0] = "Z";

        axes[1] = "X";

        axes[2] = "Y";

        genericDialog.addChoice("Select axis", axes, initAxis);

        genericDialog.addNumericField("Enter window size:", initWindowSize, windowSize);

        genericDialog.addNumericField("Enter derivative order: ", initDerivativeOrder, derivativeOrder);

        genericDialog.addCheckbox("Save settings", false);

        genericDialog.showDialog();

        if(genericDialog.wasOKed()){

            String choice = genericDialog.getNextChoice();

            windowSize = (int) genericDialog.getNextNumber();

            derivativeOrder = (int) genericDialog.getNextNumber();

            if(genericDialog.getNextBoolean()){
                prefs.set("nthorderderivative.windowsize", "" + windowSize);
                prefs.set("nthorderderivative.derivativeorder", "" + derivativeOrder);
                prefs.set("nthorderderivative.axis", "" + choice);
                prefs.savePreferences();
            }

            switch (choice) {
                case "X":
                    axis = Tools.Axis.X_AXIS;
                    break;
                case "Y":
                    axis = Tools.Axis.Y_AXIS;
                    break;
                case "Z":
                    axis = Tools.Axis.Z_AXIS;
                    break;
            }
        }
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getDerivativeOrder() {
        return derivativeOrder;
    }

    public Tools.Axis getAxis() {
        return axis;
    }
}