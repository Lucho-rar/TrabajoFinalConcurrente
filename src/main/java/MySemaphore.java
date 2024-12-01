import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class MySemaphore {
	private final Semaphore semaphore = new Semaphore(1);
    private final Set<Thread> myQueuedThreads = new HashSet<>();
    
    public MySemaphore() { }
    
    public void acquire() throws InterruptedException {
        synchronized (myQueuedThreads) {
            myQueuedThreads.add(Thread.currentThread());
        }
        semaphore.acquire();
        synchronized (myQueuedThreads) {
            myQueuedThreads.remove(Thread.currentThread());
        }
    }
    
    public void release() {
        semaphore.release();
    }

    public Set<Thread> getMyQueuedThreads() {
        synchronized (myQueuedThreads) {
            return new HashSet<>(myQueuedThreads);
        }
    }
    
    public int hasQueuedThreads() {
        synchronized (myQueuedThreads) {
            return myQueuedThreads.size();
        }
    }
}
