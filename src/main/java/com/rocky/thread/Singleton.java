/**
 * Author:   shitian
 * Date:     2018/4/10 17:04
 * Description: 线程安全的单例模式
 */
package com.rocky.thread;

/**
 * 〈线程安全的单例模式〉
 *
 * @author shitian
 * @create 2018/4/10
 * @since 1.0.0
 */
public class Singleton {
    //饿汉：上来就new实例；懒汉：用的时候才new，本例是懒汉模式
    public static volatile Singleton singleton = null;

    //私有构造函数，保证不能被多次创建
    private Singleton(){}

    public static void init(){
        synchronized (Singleton.class){
            //1.再检查一次，可能已经有线程已经new了，然后释放了锁，此时第二个线程获取了锁，所以再判断一次可以避免重复创建实例
            //2.上一步成立的前提是必须singleton声明为volatile，这样别的行程才能立刻获取singleton是否为空
            if(singleton == null) {
                singleton = new Singleton();
            }
        }
    }

    public static Singleton getSingleton(){
        if(singleton == null){
                init();
        }
        return singleton;
    }
}
