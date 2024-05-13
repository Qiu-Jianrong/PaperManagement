package com.qiu.paper_management.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreadLocalUtilTest {

//    展现其线程安全性
    @Test
    void get() {
        ThreadLocal tl = new ThreadLocal<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                tl.set("tl在线程1");
                System.out.println(Thread.currentThread().getName() + ": " + tl.get());
                System.out.println(Thread.currentThread().getName() + ": " + tl.get());
                System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            }
        }, "线程1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                tl.set("tl在线程2");
                System.out.println(Thread.currentThread().getName() + ": " + tl.get());
                System.out.println(Thread.currentThread().getName() + ": " + tl.get());
                System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            }
        }, "线程2").start();
    }
}