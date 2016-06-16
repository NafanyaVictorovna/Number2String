package view;

import javax.swing.*;

public class Window extends JFrame{

	private JTextArea testResults;
	private JPanel commonPanel;
	
	public Window(){
		initComponents();
	}
	
	private void initComponents(){
		
		commonPanel = new JPanel();
		testResults = new JTextArea("здесь будут отображены результаты тестирвания...");
		testResults.setColumns(50);
		testResults.setRows(5);
		testResults.setLineWrap(true);
		
		commonPanel.add(testResults);

		setContentPane(commonPanel);
	}
	
	public void setText(String text){
		testResults.append(text);
	}
	
	public void clearTextArea(){
		testResults.setText("");
	}
}
