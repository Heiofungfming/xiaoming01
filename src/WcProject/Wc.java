package WcProject;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Wc {
       public static void main(String[] args) {
    	       System.out.println("��ʼ����Wc����");
    	       System.out.println("���������б�");
    	       System.out.println("            -c �����ļ��ַ���");
    	       System.out.println("            -w �����ļ��ʵĸ���");
    	       System.out.println("            -l �����ļ�����");
    	       System.out.println("---------------------------------");
    	       System.out.println("��չ�����б�");
    	       System.out.println("            -s �ݹ鴦��Ŀ¼�·����������ļ�");
    	       System.out.println("            -a ���ش�������,���У�ע����");
    	       System.out.println("---------------------------------");
    	       System.out.println("�߼������б�");
    	       System.out.println("            -x ����ͼ�ν��棬ѡȡ�ļ�");
    	       System.out.print("������ָ�");
    	       
    	       /**
    	        * 1.ѡȡָ�� �������ļ���ַ
    	        * 2.ִ��ָ������ķ���
    	        */
    	       
    	       
    	       /*
    	        *1.ѡȡָ�� 
    	        */
    	       Scanner command=new Scanner(System.in);
    	       String[] arr=command.nextLine().split("\\s");
    	      for(int i=0;i<=arr.length-1;i++) {
    	    	    switch(arr[i]) {
    	       case "-c":
						try {
							CharsCounter();
						} catch (IOException e) {
							e.printStackTrace();
						}
    	    	   break;
    	       case"-w":
						try {
							WordsCounter();
						} catch (IOException e) {
							e.printStackTrace();
						}
    	    	   break;
    	       case"-l":
						try {
							LinesCounter();
						} catch (IOException e) {
							e.printStackTrace();
						}
    	    	   break;
    	       case"-s":
						try {
							FileCounter();
						} catch (IOException e) {
							e.printStackTrace();
						}
    	       case"-a":
						try {
							DeLinesCounter();
						} catch (IOException e) {
							e.printStackTrace();
						}
    	       case"-x":
    	    	   EventQueue.invokeLater(new Runnable() {
    	   			public void run() {
    	   				try {
    	   					WcGUI frame = new WcGUI();
    	   					frame.setVisible(true);
    	   				} catch (Exception e) {
    	   					e.printStackTrace();
    	   				}
    	   			}
    	   		});
    	       }
    	      }
  	       
       }
       
 //  �ݹ鴦��Ŀ¼�·����������ļ�    
private static void FileCounter()throws IOException {
	/**
	 * 1.����·��
	 * 2.�����ļ���
	 * ��������
	 */
	System.out.print("�������ļ�·����");
	Scanner filePath=new Scanner(System.in);
	String[] fileName=filePath.nextLine().split("(\\*\\.)|(\\?\\.)");
	for(String string:fileName) {
		System.out.println(string);
	}
		
	File src=new File(fileName[0]);	
	CountFile(src,fileName[1]);   //��ȡĿ¼���ļ�����Ϣ
		
	}

//
public static void CountFile(File src,String st) throws IOException {
	  
	  
	  if(null==src||!src.exists()) {
	    	return;
	    }else if(src.isDirectory()){
	    	for(File s:src.listFiles()) {
	    		//�ж�����ȡ���ļ��Ƿ���txt�ļ�
	    		String str1=s.getPath();
	    		if(str1.endsWith(st)) {
	    			//��ȡĿ¼��ÿ���ļ��������Ϣ����ӡ
		    		    int chars=0;
		    			int word=0;
		    			int row=0;
		    			
		    			BufferedReader brin=new BufferedReader(new FileReader(s));
		    			String str;
		    			while ((str=brin.readLine())!=null) {
		    				row++;
		    				for(int index=0;index<str.length();index++) {
		    					if(str.charAt(index)!='\n'&&str.charAt(index)!='\r')
		    						chars++;
		    					if (str.charAt(index)==' '||str.charAt(index)=='\t'||str.charAt(index)=='\n') {
		    						word++;
		    					} 
		    				}
		    			}
		    			brin.close();
		    			word+=row;
		    			System.out.println("-------------------");
		    			System.out.println("�ļ�����"+s.getName());
		    			System.out.println("������"+row);
		    			System.out.println("�ַ�����"+chars);
		    			System.out.println("��������"+word);
		    			CountFile(s,st);	
	    			
	    		}
	    		else {
	    			return;
	    		}
	    	}
	    }
}


//���ش�������,��������ע������
private static void  DeLinesCounter() throws IOException {
	System.out.print("�������ļ�·����");
	Scanner filePath=new Scanner(System.in);
	String[] fileName=filePath.nextLine().split("\\s");
	
	int rowCode=0;//��������
	int rowNull=0;//������
	int rowNote=0;//ע������
	boolean Note=false; 
	
	BufferedReader brin=new BufferedReader(new FileReader(fileName[fileName.length-1]));
	String s;  
	while ((s=brin.readLine())!=null) {
		s=s.trim();
		if(s.matches("^[\\s&&[^\\n]]*$")||s.endsWith("}")) {
			rowNull++;
		} else if(s.startsWith("/*")&&!s.endsWith("*/")){
			rowNote++;
			Note=true;
		}else if(true==Note) {
			rowNote++;
			if(s.endsWith("*/")) {
				Note=false;
			}
		}else if(s.startsWith("//")) {
			rowNote++;
		}else {
			rowCode++;
		}
	}
	brin.close();
	System.out.println("����������"+rowCode);
	System.out.println("�հ�������"+rowNull);
	System.out.println("ע��������"+rowNote);
}
		
	//�����ļ��ַ���
	private static void CharsCounter() throws IOException {
		System.out.print("�������ļ�·����");
		Scanner filePath=new Scanner(System.in);
		String[] fileName=filePath.nextLine().split("\\s");
		int chars=0;
		BufferedReader brin=new BufferedReader(new FileReader(fileName[fileName.length-1]));
		String s;
		while ((s=brin.readLine())!=null) {
			for(int index=0;index<s.length();index++) {
				if(s.charAt(index)!='\n'&&s.charAt(index)!='\r')
					chars++;
			}
		}
		brin.close();
		System.out.println("�ַ�����"+chars);
	}

	//�����ļ�����
	private static void WordsCounter()throws IOException {
		System.out.print("�������ļ�·����");
		Scanner filePath=new Scanner(System.in);
		String[] fileName=filePath.nextLine().split("\\s");
		int word=0;
		int row=0;
		
		BufferedReader brin=new BufferedReader(new FileReader(fileName[fileName.length-1]));
		String s;
		while ((s=brin.readLine())!=null) {
			row++;
			for(int index=0;index<s.length();index++) {
				if (s.charAt(index)==' '||s.charAt(index)=='\t'||s.charAt(index)=='\n') {
					word++;
				} 
			}
		}
		brin.close();
		word+=row;
		System.out.println("��������"+word);
		
	}
	
	
//�����ļ�����
	private static void LinesCounter() throws IOException{
		System.out.print("�������ļ�·����");
		Scanner filePath=new Scanner(System.in);
		String[] fileName=filePath.nextLine().split("\\s");
		
		int row=0;
		BufferedReader brin=new BufferedReader(new FileReader(fileName[fileName.length-1]));
		String s;
		while ((s=brin.readLine())!=null) {
			row++;
		}
		brin.close();
		System.out.println("������"+row);		
	}
}
