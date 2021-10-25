package com.nerotomato.thread.join;

/**
 * Created by nero on 2021/4/8.
 */
public class JoinThread {
    public static void main(String[] args) {
        Object obj = new Object();
        MyThread myThread = new MyThread("ChildrenThread");
        myThread.setObj(obj);
        myThread.start();


        synchronized (obj) {
            for (int i = 0; i < 200; i++) {
                if (i == 20) {
                    try {

                        /**
                         * 当前线程调用对象的 wait() 方法，当前线程释放 obj 对象锁，进入等待队列。依靠 notify()/notifyAll()
                         * 唤醒或者 wait(long timeout) timeout 时间到自动唤醒。唤醒会，线程恢复到 wait 时的状态。
                         * */
                        //0相当于未设置超时时间
                        obj.wait(0);
                        //obj.wait(10);
                        /**
                         * 调用其他线程的join方法，当前线程不会释放对象锁obj
                         * 当前线程会进入WAITING或TIMED_WAITING状态，等待其他线程执行完毕
                         */
                        //myThread.join();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "--" + i);
            }
        }
    }
}

class MyThread extends Thread {
    private String name;
    private Object obj;

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        // 这里用obj或this，效果不同
        synchronized (obj) {
            for (int i = 0; i < 100; i++) {
                System.out.println(name + i);
            }
            //obj.notify() 唤醒在此对象监视器上等待的单个线程，选择是任意性的。
            // notifyAll() 唤醒在此对象监视器上等待的所有线程
            obj.notify();
        }
    }
}
