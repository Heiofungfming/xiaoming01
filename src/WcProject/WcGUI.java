package WcProject;



import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.awt.event.ActionEvent;
import java.awt.Button;
import javax.swing.JTextArea;

public class WcGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public WcGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 528, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Button button = new Button("���ļ�");
		button.setBounds(10, 10, 87, 33);
		contentPane.add(button);
		
		JTextArea textArea = new JTextArea();
		textArea.setText(" ");
		textArea.setBounds(10, 49, 482, 226);
		contentPane.add(textArea);
		
		button.addActionListener(new ActionListener() {
			JFileChooser fileChooser=null;
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==button) {
					
				        File file=null;
				        int result;
				        fileChooser =new JFileChooser("C:\\");      
				        fileChooser.setApproveButtonToolTipText("ȷ��");
				        fileChooser.setDialogTitle("���ļ�");
				        result=fileChooser.showOpenDialog(rootPane);
				        if(result==JFileChooser.APPROVE_OPTION) {
				        	file=fileChooser.getSelectedFile();
				        	if(file.isFile()&&file.exists()) {
				        		BufferedReader reader=null;
				        		try {
				        			int chars=0;
				        			int word=0;
				        			int row=0;
				        			
				        			int rowCode=0;//��������
				        			int rowNull=0;//������
				        			int rowNote=0;//ע������
				        			boolean Note=false; 
				        			
									InputStreamReader input=new InputStreamReader(new FileInputStream(file),"UTF-8");
									reader=new BufferedReader(input);
									String s;
									while ((s=reader.readLine())!=null) {
										s=s.trim();
										row++;
										for(int index=0;index<s.length();index++) {
											if(s.charAt(index)!='\n'&&s.charAt(index)!='\r')
												chars++;
											if (s.charAt(index)==' '||s.charAt(index)=='\t'||s.charAt(index)=='\n') {
												word++;
											} 
										}
										
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
									reader.close();
									word+=row;
									textArea.append("������"+row+'\n');
									textArea.append("�ַ�����"+chars+'\n');
									textArea.append("��������"+word+'\n');
									textArea.append("����������"+rowCode+'\n');
									textArea.append("�հ�������"+rowNull+'\n');
									textArea.append("ע��������"+rowNote+'\n');
									
				        	}catch(IOException e1) {
				        		e1.printStackTrace();
				        		}
				        	}
				        }
				}
			}
		});
	
	}
}
