package com.example.product.Thread;

public class MyThread extends Thread {
    private String threadName;

    // 构造函数，传入线程名称
    public MyThread(String threadName) {
        this.threadName = threadName;
    }

    // 重写run方法
    @Override
    public void run() {
        for (int i = 0; i <= 5 ; i++) {
            System.out.println(threadName + " 正在运行，第 " + i + " 次");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(threadName + "被中断");
            }
        }
        System.out.println(threadName + "执行完成");
    }
}
