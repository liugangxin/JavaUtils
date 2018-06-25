package cn.guava.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class BuildCache {

	public static void main(String[] args) throws ExecutionException {
		
		LoadingCache<Integer, String> build = CacheBuilder.newBuilder()
        //.maximumSize(1000)//1 基于容量回收，这个和设置的maximunSize有关
       //.expireAfterWrite(10, TimeUnit.MINUTES)//2 定时回收，这个和设置的expireAfterAccess，expiredAfterWrite有关
        .weakValues()//3用的少;基于引用回收，这个和设置的weakKeys，weakValues和softValues有关。
        .removalListener(removalListener)// 在Entry项被移除的时候调用
        .build(
            new CacheLoader<Integer, String>() {
                public String load(Integer empId) throws Exception {
                    return "hello";
                }
        });
		
		String a = build.get(1);
		build.put(1, "yes");
		String b = build.get(1);
		System.out.println(a);
		System.out.println(b);
	}
	
	public static final RemovalListener<Integer, String> removalListener = new RemovalListener<Integer, String>() {
		public void onRemoval(RemovalNotification<Integer, String> removal) {
			String v = removal.getValue();
			//do something,if this is a db connection, you can close it.
		}
	};
}
