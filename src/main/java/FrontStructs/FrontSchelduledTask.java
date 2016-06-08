package FrontStructs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 16-6-7.
 */
public class FrontSchelduledTask {

    private static FrontSchelduledTask frontSchelduledTask = new FrontSchelduledTask();

    private List<FrontSchelduledTaskUnit> taskUnits = new ArrayList<FrontSchelduledTaskUnit>();

    private FrontSchelduledTask() {

    }

    public static FrontSchelduledTask getFrontSchelduledTask() {
        return frontSchelduledTask;
    }

    public void insert(FrontSchelduledTaskUnit frontSchelduledTaskUnit) {
        taskUnits.add(frontSchelduledTaskUnit);
    }

    public List<FrontSchelduledTaskUnit> getTaksUnits() {
        return taskUnits;
    }
}
