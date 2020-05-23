import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yqc
 * @date 2020/5/2317:11
 */
public class Test {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(()->{
            for (int i = 1; i <= 10; i++){
                shareResource.printA();
            }
        }).start();

        new Thread(()->{
            for (int i = 1; i <= 10; i++){
                shareResource.printB();
            }
        }).start();

        new Thread(()->{
            for (int i = 1; i <= 10; i++){
                shareResource.printC();
            }
        }).start();
    }
}

class ShareResource{

    private int i = 1;// A:1 B:2 C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();


    public void printA(){
        lock.lock();
        try {
            while (i != 1){
                c1.await();
            }
            System.out.println('A');
            i = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printB(){
        lock.lock();
        try {
            while (i != 2){
                c2.await();
            }
            System.out.println('B');
            i = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printC(){
        lock.lock();
        try {
            while (i != 3){
                c3.await();
            }
            System.out.println('C');
            i = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}