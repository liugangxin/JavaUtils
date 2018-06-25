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
//20150426
public class WordsReadUtil {

	public static void main(String[] args) {
		String path="./Files";//路径
		long begin=System.currentTimeMillis();
		readWordsFromFile(path);
		long end=System.currentTimeMillis();
		System.out.println(end-begin);
		/*Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("aa", 4);
		map.put("dsaa", 2);
		map.put("adsa", 3);
		map.put("asea", 6);
		map.put("ara", 4);
		getTop10(map);*/
	}
	/**
	 * @param path 文件夹路径参数
	 */
	public static void readWordsFromFile(String path){
		Map<String, Integer> map=new HashMap<String, Integer>();//结果集，key-value分别对应“单词-出现次数”
		File root=new File(path);
		if(!root.exists()){
			System.out.println("该目录不存在！");
			return;
		}
		File []fs=root.listFiles();//得到参数path的该目录中所有文件
		int len=fs.length;
		for(int i=0;i<len;i++){
			//读取文件
			if(fs[i].isFile()){
				BufferedReader bf=null;
				try {
					bf=new BufferedReader(new FileReader(fs[i]));
					String word=null;
					while((word=bf.readLine())!=null){
						if(!"".equals(word.trim())){
							if(map.get(word.trim())!=null){
								map.put(word.trim(), map.get(word.trim())+1);
							}else{
								map.put(word.trim(), 1);
							}
						}
						//System.out.println(word);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						bf.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		getTop10(map);
	}
	/**
	 * 排序，获取Top10单词并输出
	 */
	public static void getTop10(Map<String, Integer> map){
		List<Map.Entry<String, Integer>> sortList=new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(sortList, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> arg0,
					Entry<String, Integer> arg1) {
				return arg0.getValue()>arg1.getValue()?0:1;
			}
		});
		int len=sortList.size()>10?10:sortList.size();
		for(int i=0;i<len;i++){
			System.out.println(sortList.get(i).getKey()+":共"+sortList.get(i).getValue()+"次");
		}
	}
	
}
