import java.util.ArrayList;

/**
 * Created by kzlou on 4/25/2017.
 */
public class Cycler {

    private ArrayList<ICycle> cycles = new ArrayList<>();

    public void senseAndCycle() {
        for(ICycle cycle : cycles) {
            cycle.sense();
        }
        for(ICycle cycle : cycles) {
            cycle.cycle();
        }
    }

    public void add(ICycle cycle) {
        cycles.add(cycle);
    }
}
