import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class Main { 
	static Scanner in = new Scanner(System.in);
 
    public static void main(String[] args) { // 用HashMap存放<单词:词频>这样一个映射关系
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        
        // 用正则表达式来过滤字符串中的所有标点符号
        String regex = "[【】、.。,，。\"!--;:?\'\\]]";
        try {
            // 读取要处理的文件
        	System.out.println("请输入读取文件的文件名：");
        	String s = in.nextLine();
            @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(s));
            String value;
            while ((value = br.readLine()) != null) {
                value = value.replaceAll(regex, " ");
                // 使用StringTokenizer来分词
                StringTokenizer tokenizer = new StringTokenizer(value);
                while (tokenizer.hasMoreTokens()) {
                    String word = tokenizer.nextToken();
                    if (!hashMap.containsKey(word)) {
                        hashMap.put(word, new Integer(1));
                    } else {
                        int k = hashMap.get(word).intValue() + 1;
                        hashMap.put(word, new Integer(k));
                    }
                }
            }
            //显示用户需要查询的若干个单词的词频
            queryWord(hashMap);
            //按值排序,并输出用户要求的词频最多的前K个单词
            sortMapByValues(hashMap);
            //根据键的字母顺序排序，排好后放到result.txt文件中
            sortMapByKeys(hashMap);
        
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //显示用户需要查询的若干个单词的词频
    private static void queryWord(Map<String, Integer> Map) {
    	
    	//显示用户需要查询的若干个单词的词频
        System.out.println("请输入需要查询的单词,单词之间用空格隔开");
        String s = in.nextLine();
        String[] word= s.split(" ");
        int i, sum=0;
        for(i=0; i<word.length; i++) {
        	for(Entry<String,Integer> entry : Map.entrySet()) { 
        		if(word[i].equals(entry.getKey()))
        		{
        			sum += entry.getValue();
        			System.out.println(entry.getKey() + ":\t " + entry.getValue()); 
        			break;
        		}
                
            } 
        }
        System.out.println("各单词相关的柱状图如下（一个*代表出现五次）：");
        int j;
        for(i=0; i<word.length; i++) {
        	for(Entry<String,Integer> entry : Map.entrySet()) { 
        		if(word[i].equals(entry.getKey()))
        		{
        			double t = (double)(entry.getValue())/sum*100;
        			BigDecimal a =new BigDecimal(t).setScale(2, BigDecimal.ROUND_HALF_UP);		//对浮点数进行四舍五入处理
        			Integer result = a.intValue();
        			String re = "";
        			for(j=0; j<result; j++) {
        				re += "*";
        			}
        			System.out.println(word[i] + ":\t"  + "|" + re);
        			break;
        		}
                
            } 
        }
        
    }
    
    
    //按值排序,并输出用户要求的词频最多的前K个单词
    private static void sortMapByValues(Map<String, Integer> Map) {  
    	  
        Set<Entry<String,Integer>> mapEntries = Map.entrySet();   
        LinkedList<Entry<String, Integer>> List = new LinkedList<Entry<String,Integer>>(mapEntries);  
  
        //根据映射的值对列表排序
        Collections.sort(List, new Comparator<Entry<String,Integer>>() {  
            @Override  
            public int compare(Entry<String, Integer> ele1,  Entry<String, Integer> ele2) {  
  
                return ele2.getValue().compareTo(ele1.getValue());  
            }  
        });  
  
        Map<String,Integer> Map2 = new LinkedHashMap<String, Integer>();  
        for(Entry<String,Integer> entry: List) {  
            Map2.put(entry.getKey(), entry.getValue());  
        }  
  
        // 输入需要查看的词频最高的前K个单词
        System.out.println("请输入需要查看的词频最高的单词个数：");
        int n = in.nextInt();
        for(Entry<String,Integer> entry : Map2.entrySet()) {  
            System.out.println(entry.getKey() + " - " + entry.getValue());  
            n--;
            if(n<=0)	break;
        } 
       
    } 
    
    //根据键的字母顺序排序，排好后放到result.txt文件中
    private static void sortMapByKeys(Map<String, Integer> Map) {  
    	  
        Set<Entry<String,Integer>> mapEntries = Map.entrySet();    
        LinkedList<Entry<String, Integer>> List = new LinkedList<Entry<String,Integer>>(mapEntries);  
  
        // 根据映射的键对列表排序
        Collections.sort(List, new Comparator<Entry<String,Integer>>() {  
            @Override  
            public int compare(Entry<String, Integer> ele1,  Entry<String, Integer> ele2) {  
  
                return ele1.getKey().compareTo(ele2.getKey());  
            }  
        });  
  
        Map<String,Integer> Map2 = new LinkedHashMap<String, Integer>();  
        for(Entry<String,Integer> entry: List) {  
            Map2.put(entry.getKey(), entry.getValue());  
        }  
  
        //将排序好的单词及对应的词频放到文件中
        System.out.println("正在向文件中存放，请稍后...\n");
        File file = new File("result.txt");
        try {
        	if(file.exists()) {
        		file.createNewFile();
        	}
        	FileWriter fop = new FileWriter(file.getAbsoluteFile());
        	for(Entry<String,Integer> entry : Map2.entrySet()) {
        		fop.write(entry.getKey()+":\t"+entry.getValue()+"\n");
        	}
        	fop.close();
        	System.out.println("已按字母表顺序存放结束，请在src目录下查看！");
        }catch(IOException e) {
        	e.printStackTrace();
        }
    }  

}
