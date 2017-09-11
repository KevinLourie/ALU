import java.util.ArrayList;

/**
 * Created by kzlou on 4/25/2017.
 */
public class Cycler {

    /** Collection of cycles */
    private ArrayList<ICycle> cycles = new ArrayList<>();

    /**
     * Sense all precomputedOutput. Cycle all registers
     */
    public void senseAndCycle() {
        for(ICycle cycle : cycles) {
            cycle.sense();
        }
        for(ICycle cycle : cycles) {
            cycle.cycle();
        }
    }

    /**
     * Add cycle to collection
     * @param cycle cycle to add
     */
    public void add(ICycle cycle) {
        cycles.add(cycle);
    }
}
