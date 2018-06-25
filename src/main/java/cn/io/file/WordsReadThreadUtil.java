package cn.io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/*
 * 20150426
 * 文件数少时和内容少时，效果不明显，甚至线程池读取时间更多
 * 单线程：9105	多线程：5093
 * */
public class WordsReadThreadUtil {

	public static Map<String, Integer> resultMap=new HashMap<String, Integer>();//结果集，key-value分别对应“单词-出现次数”
	
	public static void main(String[] args) {
		String path="./Files";//路径
		int size=3;//线程数
		try {
			long begin=System.currentTimeMillis();
			readWordsFromFile(path,size);
			long end=System.currentTimeMillis();
			System.out.println("共花费时间："+(end-begin)+"ms");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param path 文件夹路径参数
	 * @param size 开辟线程数
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void readWordsFromFile(String path,int size) throws InterruptedException, ExecutionException{
		
		File root=new File(path);
		if(!root.exists()){
			System.out.println("该目录不存在！");
			return;
		}
		File []fs=root.listFiles();//得到参数path的该目录中所有文件
		//如果线程数大于文件数或输入有误，则以文件数作为线程数
		if(size>fs.length){
			size=fs.length;
		}
		int len=0;
		if(fs.length%size==0){
			len=fs.length/size;
		}else{
			len=fs.length/size+1;
		}
		System.out.println(size+"个线程");
		//开辟线程，每个线程读取若干个文件
		List<Thread> list=new ArrayList<Thread>();
		for(int i=0;i<size;i++){
			//System.arraycopy(fs, i*len, arg2, arg3, arg4)
			List<File> splitFiles=new ArrayList<File>();
			int limit=fs.length>(i+1)*len?(i+1)*len:fs.length;
			for(int k=i*len;k<limit;k++){
				splitFiles.add(fs[k]);
			}
			Thread t=new Thread(new MyRunnable(splitFiles));
			t.start();
			list.add(t);
		}
		for(Thread tmp:list){
			tmp.join();
		}
		getTop10(resultMap);
	}
	/**
	 * 组合每个线程的结果集合
	 */
	public static synchronized void combineMap(Map<String, Integer> map){
		for(String key:map.keySet()){
			if(resultMap.get(key)!=null){
				resultMap.put(key, resultMap.get(key)+map.get(key));
			}
			else{
				resultMap.put(key, map.get(key));
			}
		}
	}
	/**
	 * Map对象排序，获取Top10单词并输出
	 */
	public static Map<String, Integer> getTop10(Map<String, Integer> map){
		List<Map.Entry<String, Integer>> sortList=new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Map<String, Integer> result=new HashMap<String, Integer>();
		Collections.sort(sortList, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> arg0,
					Entry<String, Integer> arg1) {
				return arg0.getValue()>arg1.getValue()?0:1;
			}
		});
		int len=sortList.size()>10?10:sortList.size();
		//输入Top10单词以及数量
		for(int i=0;i<len;i++){
			result.put(sortList.get(i).getKey(), sortList.get(i).getValue());
			System.out.println(sortList.get(i).getKey()+":共"+sortList.get(i).getValue()+"次");
		}
		return result;
	}
	
	
}

class MyRunnable implements Runnable{
	private List<File> file=null;
	
	public MyRunnable(List<File> file) {
		super();
		this.file = file;
	}
	Map<String, Integer> map=new HashMap<String, Integer>();
	@Override
	public void run() {
		for(int i=0;i<file.size();i++){
			if(file.get(i).isFile()){
				BufferedReader bf=null;
				try {
					//读取文件
					bf=new BufferedReader(new FileReader(file.get(i)));
					String word=null;
					while((word=bf.readLine())!=null){
						if(!"".equals(word.trim())){
							if(map.get(word.trim())!=null){
								map.put(word.trim(), map.get(word.trim())+1);
							}else{
								map.put(word.trim(), 1);
							}
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						bf.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		WordsReadThreadUtil.combineMap(map);
	}
	
}
