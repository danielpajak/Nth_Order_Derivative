import ij.IJ;

public class Tools{

    public enum Axis {
        X_AXIS,
        Y_AXIS,
        Z_AXIS
    }

    public double calculateFactorial(int n) {

        if (n <= 2) return n;

        return n * calculateFactorial(n - 1);
    }

    public synchronized void showProgress(int i1, int i2){
        IJ.showProgress((double)i1 / (double)i2);
    }
}