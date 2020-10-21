package com.chenkj.myspringboot.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author
 * @Description
 * @Date 2020-09-30 10:20
 */
public class FutureMain {
    static class DemoJob implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            Thread.sleep(10000);
            return 2;
        }
    }

    public static void main(String[] args) throws Exception{
        DemoJob demoJob = new DemoJob();
        FutureTask<Integer> futureTask = new FutureTask<>(demoJob);
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println(futureTask.get());
        System.out.println(1);



    }
}
