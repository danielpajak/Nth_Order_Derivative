import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
public class Nth_Order_Derivative implements PlugInFilter {

    protected ImageStack stack;

    protected DialogBox dialogBox;

    protected PixelsCalculator pixelsCalculator;

    protected String title;

    @Override
    public void run(ImageProcessor imageProcessor) {

        if ((dialogBox.getWindowSize() > dialogBox.getDerivativeOrder())
                && dialogBox.getWindowSize() > 0
                && dialogBox.getDerivativeOrder() >= 0
                && (dialogBox.getWindowSize() % 2 == 1)
                && ((dialogBox.getAxis() == Tools.Axis.Z_AXIS && dialogBox.getWindowSize() < stack.getSize())
                || (dialogBox.getAxis() == Tools.Axis.X_AXIS && dialogBox.getWindowSize() < stack.getWidth())
                || (dialogBox.getAxis() == Tools.Axis.Y_AXIS && dialogBox.getWindowSize()  < stack.getHeight()))
        ) {
            pixelsCalculator = new PixelsCalculator(stack, title, dialogBox.getWindowSize(), dialogBox.getDerivativeOrder());

            pixelsCalculator.calculateNewPixels(dialogBox.getAxis());

        } else {
            IJ.showMessage("Invalid data. Plugin is closed.");
        }
    }

    @Override
    public int setup(String s, ImagePlus imagePlus) {

        stack = imagePlus.getStack();

        dialogBox = new DialogBox();

        title = imagePlus.getShortTitle();

        return NO_CHANGES + DOES_ALL;
    }

    public static void main(String[] args) {
    }
}