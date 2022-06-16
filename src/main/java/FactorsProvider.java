import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.inversion.MatrixInverter;
import org.la4j.matrix.dense.Basic2DMatrix;
import java.util.ArrayList;
import java.util.List;

public class FactorsProvider {

    protected final Tools tools;

    public FactorsProvider() {
        tools = new Tools();
    }

    protected List<Double> finiteDifference(int windowSize, int derivativeOrder, List<Double> points) {

        List<Double> output = new ArrayList<>();

        Matrix matrix = new Basic2DMatrix(windowSize, windowSize);

        for (int i = 0; i < points.size(); ++i) {
            for (int j = 0; j < windowSize; ++j) {

                double value = Math.pow(points.get(i), j);

                matrix.set(j, i, value);
            }
        }

        MatrixInverter inverter = matrix.withInverter(LinearAlgebra.NO_PIVOT_GAUSS);

        matrix = inverter.inverse();

        Matrix factorialMatrix = new Basic2DMatrix(new double[windowSize][1]);

        for (int i = 0; i < windowSize; ++i) {

            factorialMatrix.set(i, 0, 0.0);

            if (i == derivativeOrder) factorialMatrix.set(i, 0, tools.calculateFactorial(derivativeOrder));
        }

        Matrix finalMatrix = matrix.multiply(factorialMatrix);

        for (int i = 0; i < windowSize; ++i) output.add(finalMatrix.get(i, 0));

        return output;
    }
}