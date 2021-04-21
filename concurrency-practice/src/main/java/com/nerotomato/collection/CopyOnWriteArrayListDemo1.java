package com.nerotomato.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nero on 2021/4/15.
 */
public class CopyOnWriteArrayListDemo1 {
    private static final int THREAD_POOL_MAX_NUM = 10;
    //private List<String> mList = new ArrayList<String>();  // ArrayList 无法运行
    private List<String> mList = new CopyOnWriteArrayList();

    public static void main(String[] args) {
        new CopyOnWriteArrayListDemo1().start();
    }

    private void initData() {
        for (int i = 0; i < THREAD_POOL_MAX_NUM; i++) {
            mList.add("......Line" + (i + 1) + "......");
        }
    }

    private void start() {
        initData();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_MAX_NUM);
        for (int i = 0; i < THREAD_POOL_MAX_NUM; i++) {
            executorService.execute(new ListReader(mList));
            executorService.execute(new ListWriter(mList, 1));
        }
        executorService.shutdown();
    }

    private class ListReader implements Runnable {

        private List<String> mList;

        public ListReader(List list) {
            this.mList = list;
        }

        @Override
        public void run() {
            if (this.mList != null) {
                for (String str : this.mList) {
                    System.out.println(Thread.currentThread().getName() + " : " + str);
                }
            }
        }
    }

    private class ListWriter implements Runnable {

        private List<String> mList;
        private int mIndex;

        public ListWriter(List list, int index) {
            this.mList = list;
            this.mIndex = index;
        }

        @Override
        public void run() {
            mList.add("......add " + mIndex + "......");
        }
    }
}
