package cn.io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JunFeng20160325HandleData {

	public static void main(String[] args) {
		String path="C:/12306Bypass_1.10.63/data";//路径
		//readWordsFromFile(path);
		String s = "fgdkls	fdio";
		String[] split = s.split("\t");
		System.out.println(split[0]);
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
					int n =0;
					while(n<50&&(word=bf.readLine())!=null){
						if(word!=null){
							String[] split = word.split("\t");
							
						}
						n++;
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
	}
	
	//2010/5/27	圆白菜	承德市蔬菜果品批发市场	1.2	1.2	1.2	元/公斤 
	class Data{
		String date;
		String name;
		String address;
		String lowPrice;
		String highPrice;
		String averagePrice;
		String unit;
		public Data(String date, String name, String address, String lowPrice, String highPrice, String averagePrice, String unit) {
			super();
			this.date = date;
			this.name = name;
			this.address = address;
			this.lowPrice = lowPrice;
			this.highPrice = highPrice;
			this.averagePrice = averagePrice;
			this.unit = unit;
		}
		
		
	}
}
