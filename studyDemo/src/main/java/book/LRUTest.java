package book;

import java.util.LinkedHashMap;
import java.util.Map;

/******************************
 * @Description 编程一段泛型程序实现LRU缓存
 * @author Administrator
 * @date 2022-08-30 21:17
 ******************************/
public class LRUTest<K, V> extends LinkedHashMap<K, V> {
    //缓存大小
    private int cacheSize;

    public LRUTest(int cacheSize) {
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    //重写此方法，使其LinkedHashMap的大小在大于设置的缓存大小时，删除最年长的元素
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }
}
