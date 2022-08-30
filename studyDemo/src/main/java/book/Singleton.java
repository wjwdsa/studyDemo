package book;

/******************************
 * @Description 尽可能高效、安全的单例模式
 * @author Administrator
 * @date 2022-08-30 21:22
 ******************************/
class Singleton {
    //生成唯一的实例
    private static Singleton singleton;

    //用private修饰构造方法, 让外部无法对其实例化
    private Singleton() {
    }

    //使用synchronized修饰符修饰方法对其上锁, 使其线程安全
    private static synchronized Singleton getSingleton() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
}