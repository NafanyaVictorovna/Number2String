package digit.parser;

import java.io.FileNotFoundException;
import java.util.Map;

import javax.swing.JFrame;

import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import view.Window;


public class TransformNumberRuTest {
	private TransformNumberRu transform;
	private static String text;
	private static Window window;
	private String[][] testArray;
	
	@Before
	public void openFile(){
		transform = new TransformNumberRu();
		try {
			transform.setForms(transform.readFile("resources/Degrees.txt"));
			testArray = transform.readFile("resources/test.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@After
	public void clearAll(){
		transform = null;
	}
	
	@Test
	public void testNumber2String() {
		window.clearTextArea();
		for(int i=0; i<transform.getRow(); i++){
				String str = transform.Number2String(Float.parseFloat(testArray[i][0]));
				if(str.equals(testArray[i][1])){text = "success test: " 
							+testArray[i][1] +" equals "+ str + "\n";}
			else{text = "test failed: " + testArray[i][1] + " not equals " + str + "\n";}
			window.setText(text);
		}	
	}

    public static void main(String[] args) throws Exception {
		window = new Window();
		window.setSize(400, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		window.repaint();
        Result result = JUnitCore.runClasses(TransformNumberRuTest.class);
        System.out.println("run tests: " + result.getRunCount());
        System.out.println("failed tests: " + result.getFailureCount());
        System.out.println("ignored tests: " + result.getIgnoreCount());
        System.out.println("success: " + result.wasSuccessful());
        window.repaint();
    }
}