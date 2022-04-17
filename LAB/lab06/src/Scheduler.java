import java.util.List;

public class Scheduler<T extends Worker> {
    private static final int waitms = 400;
    private List<T> workers;

    public Scheduler(List<T> workers) {
        this.workers = workers;
    }

    T schedule() {
        return workers.get(0);
    }

    T schedule(int index) {
        // TODO: problem3.1 - Add scheduling logic
        if(workers.size() <= index) {
            return workers.get(0);
        } else {
            return workers.get(index);
        }
    }

    T scheduleRandom() {
        // TODO: problem3.1 - Add scheduling logic
        int ran = (int)(Math.random()*workers.size());
        return workers.get(ran);
    }

    T scheduleFair() {
        // TODO: problem3.2 - Add scheduling logic
        int max=0;
        int maxWorkerId =0;
        for (Worker worker : workers) {
            if (max < worker.delay) {
                max = worker.delay;
                maxWorkerId = worker.id;
            }
        }
        for (Worker worker : workers) {
            if (worker.id!=maxWorkerId) {
                worker.delay++;
            }
        }
        Worker maxWorker = workers.get(maxWorkerId);
        maxWorker.delay=0;
        return workers.get(maxWorkerId);
    }

    static void delay() {
        try {
            Thread.sleep(waitms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
