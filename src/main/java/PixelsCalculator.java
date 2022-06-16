import ij.ImagePlus;
import ij.ImageStack;
import java.util.ArrayList;
import java.util.List;

public class PixelsCalculator {

    final protected String stackTitle;

    final protected int windowSize;

    final protected int derivativeOrder;

    final protected ImageStack imageStack;

    final protected FactorsProvider factorsProvider;

    final protected IndexesProvider indexesProvider;

    protected List<List<Double>> indexes;

    protected List<List<Double>> factors;

    protected Tools tools;

    public PixelsCalculator(ImageStack stack, String title, int size, int order) {

        imageStack = stack;

        stackTitle = title;

        windowSize = size;

        derivativeOrder = order;

        factorsProvider = new FactorsProvider();

        indexesProvider = new IndexesProvider();

        indexes = new ArrayList<>();

        factors = new ArrayList<>();

        tools = new Tools();

        calculateFactorsAndIndexes();
    }

    protected void calculateFactorsAndIndexes() {

        for (int i = windowSize - 1; i >= 0; --i) {

            List<Double> tempList = indexesProvider.listProvider(windowSize, i);

            indexes.add(0, tempList);

            tempList = factorsProvider.finiteDifference(windowSize, derivativeOrder, indexes.get(0));

            factors.add(0, tempList);
        }

        System.out.println(factors);
    }

    public void calculateNewPixels(Tools.Axis axis) {

        int stackSize = imageStack.getSize();

        int width = imageStack.getWidth();

        int height = imageStack.getHeight();

        ImageStack newImageStack = imageStack.duplicate();

        newImageStack.setBitDepth(32);

        if (axis == Tools.Axis.Z_AXIS) {
            calculate(newImageStack, Tools.Axis.Z_AXIS, width, height, stackSize);
        } else if (axis == Tools.Axis.X_AXIS) {
            calculate(newImageStack, Tools.Axis.X_AXIS, stackSize, height, width);
        } else {
            calculate(newImageStack, Tools.Axis.Y_AXIS, stackSize, width, height);
        }

        ImagePlus imagePlus = new ImagePlus();
        imagePlus.setStack(stackTitle + "_Derivative", newImageStack);
        imagePlus.setSlice(1);
        imagePlus.show();
    }

    protected void calculate(ImageStack newImageStack, Tools.Axis axis, int size1, int size2, int size3) {

        for (int s1 = 0; s1 < size1; ++s1) {

            tools.showProgress(s1, size1 - 1);

            for (int s2 = 0; s2 < size2; ++s2) {

                double[] pixels = getPixelsByAxis(axis, s1, s2, size3);

                int ind = 0;

                for (int s3 = 0; s3 < size3; ++s3) {

                    double newPixelValue = 0.0;

                    if (s3 < windowSize / 2) {

                        for (int k = 0; k < windowSize; ++k) {
                            newPixelValue += pixels[s3 + indexes.get(ind).get(k).intValue()] * factors.get(ind).get(k);
                        }
                        ind++;

                    } else if (s3 + windowSize / 2 > size3 - 1) {

                        ind++;
                        for (int k = 0; k < windowSize; ++k) {
                            newPixelValue += pixels[s3 + indexes.get(ind).get(k).intValue()] * factors.get(ind).get(k);
                        }

                    } else {
                        for (int k = 0; k < windowSize; ++k) {
                            newPixelValue += pixels[s3 + indexes.get(windowSize / 2).get(k).intValue()] * factors.get(windowSize / 2).get(k);
                        }
                    }

                    if (axis == Tools.Axis.X_AXIS) newImageStack.setVoxel(s3,s2,s1, newPixelValue);
                    else if(axis == Tools.Axis.Z_AXIS) newImageStack.setVoxel(s1,s2,s3, newPixelValue);
                    else newImageStack.setVoxel(s2,s3,s1, newPixelValue);
                }
            }
        }

    }

    protected double[] getPixelsByAxis(Tools.Axis axis, int i1, int i2, int size) {

        double[] pixels = new double[size];

        for (int i = 0; i < size; ++i){

            if (axis == Tools.Axis.X_AXIS) pixels[i] = imageStack.getVoxel(i, i2, i1);
            else if(axis == Tools.Axis.Z_AXIS) pixels[i] = imageStack.getVoxel(i1, i2, i);
            else pixels[i] = imageStack.getVoxel(i2, i, i1);
        }

        return pixels;
    }

}