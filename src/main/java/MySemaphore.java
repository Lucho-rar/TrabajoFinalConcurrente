import java.util.*;
import java.util.concurrent.*;
public class MySemaphore extends Semaphore {
    public MySemaphore(int permits) {
        super(permits);
    }

    public Collection<Thread> getMyQueuedThreads() {
        return getQueuedThreads();
    }
}
