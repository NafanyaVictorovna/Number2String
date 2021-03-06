package digit.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractTransformNumber implements TransformNumber{

	abstract protected String getUnits(int n, int gender);
	abstract protected String getDecades(int n);
	abstract protected String get11_19(int n);
	abstract protected String getHundreds(int n);
	abstract protected String getUnitTitle(int index, double count);
	abstract protected int getGender(int index);
	private File file;
	private List<ArrayList<String>> list;
	private String[][] array;
	
	public int getRow(){
		return list.size();
	}
	
	public boolean checkString(String str){
		try{
			Double.parseDouble(str);
		} catch(Exception e){
			return false;
		}
		return true;
	}

	public String Number2String(double num) {
		String result = "";
		int index = 0;
		if(num == 0){
			result = getUnits(0, 0) + "ноль ";
		}
		if(Math.abs(num) > 0 && Math.abs(num) < 1){
			result = "не целое";
		}
		while(Math.abs(num) >= 1){
			String triad = triad2String((int)(Math.abs(num)%1000),getGender(index),index<1);
			if(num % 1000 != 0)
			result = triad + getUnitTitle(index, num % 1000) + result;
			num = num/1000;	
			index++;
		}		
		if(num < 0){
			result = "минус " + result;
		}
		return result;
	}

	public String triad2String(int n, int gender, boolean acceptZero) {
		String result = "";
		if(!acceptZero && n == 0) return "";
		if(n % 1000 > 99){
			result += getHundreds(n%1000/100) + " ";
		}
		if(n % 100 > 9){
			if(n%100>10 && n%100<20){
				result += get11_19(n % 10) + " ";
			} else 	result += getDecades(n%100/10) + " "; 
		} 
		if(n != 0 && n % 10 <= 9 )
		{
			if((n%100>10 && n%100<20) || n%10==0){ result += "";}
			else result += getUnits(n%10, gender) + " ";
		}
		return result;
	}
	
	private void fileExists(String fileName) throws FileNotFoundException{
		file = new File(fileName);
		if(!file.exists()){
			throw new FileNotFoundException(file.getName());
		}
	}

	public String[][] readFile(String fileName) throws FileNotFoundException{
		list = new ArrayList<ArrayList<String>>();
		int str_j = 0, str_i = 0;
		fileExists(fileName);
		try{
			BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			try{
				String str;
				while((str = in.readLine()) != null){
					if(!str.equals("")){
						str_i++;
						if(!str.contains(",")) str = str.concat(", ");
						list.add(new ArrayList<String>(Arrays.asList(str.split(",")))); 
					}
				}
			} finally{
				in.close();
			}
		} catch(IOException e){
			throw new RuntimeException(e);
		}
		str_j = list.get(0).size();
		array = new String[str_i][str_j];
		for(int i=0; i<str_i; i++){
			for(int j =0; j<str_j; j++)
			if(!list.get(i).get(j).isEmpty()){
			array[i][j] = list.get(i).get(j);
			} else {
				array[i][j] = "";
			}
		}
		return array;
	}
	
}

