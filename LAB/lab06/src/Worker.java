import java.util.Queue;

public abstract class Worker {
    Queue<Work> workQueue;

    public abstract void run();

    static int num=0;
    int id;
    int delay=0;

    Worker(Queue<Work> workQueue) {
        this.workQueue = workQueue;
        // TODO: problem1
        id=num++;
    }

    void report(String msg){
        // TODO: problem2
        System.out.println(msg);
    }

}

class Producer extends Worker {
    Producer(Queue<Work> workQueue) {
        super(workQueue);
    }

    @Override
    public void run() {
        // TODO: problem2
        if(!(workQueue.size() ==20)){
            Work work = new Work();
            workQueue.offer(work);
            String msg = "worker " + id + " produced work " + work.id;
            report(msg);
        } else {
            String msg = "worker " + id + " failed to produce work (workQueue is full)";
            report(msg);
        }
    }
}

class Consumer extends Worker {
    Consumer(Queue<Work> workQueue) {
        super(workQueue);
    }

    @Override
    public void run() {
        // TODO: problem2
        int prob = (int)(Math.random()*2); // 0, 1
        if(prob==1 && !workQueue.isEmpty()){
            Work work = workQueue.poll();
            String msg = "worker " + id + " consumes work " + work.id;
            report(msg);
        }
        if(prob==0) {
            String msg = "worker " + id + " failed to consume work";
            report(msg);
        }
    }
}
