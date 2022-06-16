import java.util.ArrayList;
import java.util.List;

public class IndexesProvider {

    protected List<Double> listProvider(int windowSize, int edgeDistance) {

        List<Double> list = new ArrayList<>();

        for (int i = -edgeDistance; i < windowSize - edgeDistance; ++i) list.add((double) i);

        return list;
    }
}