import java.util.ArrayList;

/**
 * Created by kzlou on 4/25/2017.
 */
public class Cycler {

    /** Collection of cycles */
    private ArrayList<ICycle> cycles = new ArrayList<>();

    /**
     * Sense all precomputedOutput
     */
    public void sense() {
        for(ICycle cycle : cycles) {
            cycle.sense();
        }
    }

    /**
     * Cycle all registers.
     */
    public void cycle() {
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
