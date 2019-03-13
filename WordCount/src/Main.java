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
 
    public static void main(String[] args) { // ��HashMap���<����:��Ƶ>����һ��ӳ���ϵ
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        
        // ��������ʽ�������ַ����е����б�����
        String regex = "[������.��,����\"!--;:?\'\\]]";
        try {
            // ��ȡҪ������ļ�
        	System.out.println("�������ȡ�ļ����ļ�����");
        	String s = in.nextLine();
            @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(s));
            String value;
            while ((value = br.readLine()) != null) {
                value = value.replaceAll(regex, " ");
                // ʹ��StringTokenizer���ִ�
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
            //��ʾ�û���Ҫ��ѯ�����ɸ����ʵĴ�Ƶ
            queryWord(hashMap);
            //��ֵ����,������û�Ҫ��Ĵ�Ƶ����ǰK������
            sortMapByValues(hashMap);
            //���ݼ�����ĸ˳�������źú�ŵ�result.txt�ļ���
            sortMapByKeys(hashMap);
        
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //��ʾ�û���Ҫ��ѯ�����ɸ����ʵĴ�Ƶ
    private static void queryWord(Map<String, Integer> Map) {
    	
    	//��ʾ�û���Ҫ��ѯ�����ɸ����ʵĴ�Ƶ
        System.out.println("��������Ҫ��ѯ�ĵ���,����֮���ÿո����");
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
        System.out.println("��������ص���״ͼ���£�һ��*���������Σ���");
        int j;
        for(i=0; i<word.length; i++) {
        	for(Entry<String,Integer> entry : Map.entrySet()) { 
        		if(word[i].equals(entry.getKey()))
        		{
        			double t = (double)(entry.getValue())/sum*100;
        			BigDecimal a =new BigDecimal(t).setScale(2, BigDecimal.ROUND_HALF_UP);		//�Ը����������������봦��
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
    
    
    //��ֵ����,������û�Ҫ��Ĵ�Ƶ����ǰK������
    private static void sortMapByValues(Map<String, Integer> Map) {  
    	  
        Set<Entry<String,Integer>> mapEntries = Map.entrySet();   
        LinkedList<Entry<String, Integer>> List = new LinkedList<Entry<String,Integer>>(mapEntries);  
  
        //����ӳ���ֵ���б�����
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
  
        // ������Ҫ�鿴�Ĵ�Ƶ��ߵ�ǰK������
        System.out.println("��������Ҫ�鿴�Ĵ�Ƶ��ߵĵ��ʸ�����");
        int n = in.nextInt();
        for(Entry<String,Integer> entry : Map2.entrySet()) {  
            System.out.println(entry.getKey() + " - " + entry.getValue());  
            n--;
            if(n<=0)	break;
        } 
       
    } 
    
    //���ݼ�����ĸ˳�������źú�ŵ�result.txt�ļ���
    private static void sortMapByKeys(Map<String, Integer> Map) {  
    	  
        Set<Entry<String,Integer>> mapEntries = Map.entrySet();    
        LinkedList<Entry<String, Integer>> List = new LinkedList<Entry<String,Integer>>(mapEntries);  
  
        // ����ӳ��ļ����б�����
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
  
        //������õĵ��ʼ���Ӧ�Ĵ�Ƶ�ŵ��ļ���
        System.out.println("�������ļ��д�ţ����Ժ�...\n");
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
        	System.out.println("�Ѱ���ĸ��˳���Ž���������srcĿ¼�²鿴��");
        }catch(IOException e) {
        	e.printStackTrace();
        }
    }  

}
