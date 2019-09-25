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
    	       System.out.println("开始构建Wc程序");
    	       System.out.println("基本功能列表：");
    	       System.out.println("            -c 返回文件字符数");
    	       System.out.println("            -w 返回文件词的个数");
    	       System.out.println("            -l 返回文件行数");
    	       System.out.println("---------------------------------");
    	       System.out.println("扩展功能列表：");
    	       System.out.println("            -s 递归处理目录下符合条件的文件");
    	       System.out.println("            -a 返回代码行数,空行，注释行");
    	       System.out.println("---------------------------------");
    	       System.out.println("高级功能列表：");
    	       System.out.println("            -x 弹出图形界面，选取文件");
    	       System.out.print("请输入指令：");
    	       
    	       /**
    	        * 1.选取指令 并输入文件地址
    	        * 2.执行指令里面的方法
    	        */
    	       
    	       
    	       /*
    	        *1.选取指令 
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
       
 //  递归处理目录下符合条件的文件    
private static void FileCounter()throws IOException {
	/**
	 * 1.输入路径
	 * 2.遍历文件夹
	 * 并逐个输出
	 */
	System.out.print("请输入文件路径：");
	Scanner filePath=new Scanner(System.in);
	String[] fileName=filePath.nextLine().split("(\\*\\.)|(\\?\\.)");
	for(String string:fileName) {
		System.out.println(string);
	}
		
	File src=new File(fileName[0]);	
	CountFile(src,fileName[1]);   //读取目录子文件的信息
		
	}

//
public static void CountFile(File src,String st) throws IOException {
	  
	  
	  if(null==src||!src.exists()) {
	    	return;
	    }else if(src.isDirectory()){
	    	for(File s:src.listFiles()) {
	    		//判断所读取子文件是否是txt文件
	    		String str1=s.getPath();
	    		if(str1.endsWith(st)) {
	    			//读取目录里每个文件里面的信息并打印
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
		    			System.out.println("文件名："+s.getName());
		    			System.out.println("行数："+row);
		    			System.out.println("字符数："+chars);
		    			System.out.println("单词数："+word);
		    			CountFile(s,st);	
	    			
	    		}
	    		else {
	    			return;
	    		}
	    	}
	    }
}


//返回代码行数,空行数，注释行数
private static void  DeLinesCounter() throws IOException {
	System.out.print("请输入文件路径：");
	Scanner filePath=new Scanner(System.in);
	String[] fileName=filePath.nextLine().split("\\s");
	
	int rowCode=0;//代码行数
	int rowNull=0;//空行数
	int rowNote=0;//注释行数
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
	System.out.println("代码行数："+rowCode);
	System.out.println("空白行数："+rowNull);
	System.out.println("注释行数："+rowNote);
}
		
	//返回文件字符数
	private static void CharsCounter() throws IOException {
		System.out.print("请输入文件路径：");
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
		System.out.println("字符数："+chars);
	}

	//返回文件词数
	private static void WordsCounter()throws IOException {
		System.out.print("请输入文件路径：");
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
		System.out.println("单词数："+word);
		
	}
	
	
//返回文件行数
	private static void LinesCounter() throws IOException{
		System.out.print("请输入文件路径：");
		Scanner filePath=new Scanner(System.in);
		String[] fileName=filePath.nextLine().split("\\s");
		
		int row=0;
		BufferedReader brin=new BufferedReader(new FileReader(fileName[fileName.length-1]));
		String s;
		while ((s=brin.readLine())!=null) {
			row++;
		}
		brin.close();
		System.out.println("行数："+row);		
	}
}
