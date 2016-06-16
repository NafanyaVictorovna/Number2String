package digit.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public abstract class AbstractTransformNumber implements TransformNumber{

	abstract protected String getUnits(int n, int gender);
	abstract protected String getDecades(int n);
	abstract protected String get11_19(int n);
	abstract protected String getHundreds(int n);
	abstract protected String getUnitTitle(int index, float count);
	abstract protected int getGender(int index);
	private File file;
	private String[][] array;
	private int str_i =0;
	
	public int getRow(){
		return str_i;
	}

	public String Number2String(float num) {
		String result = "";
		int index = 0;
		if(num == 0){
			result = getUnits(0, 0) + " " + getUnitTitle(1, 0);
		}
		while(num >= 1){
			String triad = triad2String((int)(num%1000),getGender(index),index<1);
			result = triad + getUnitTitle(index, num % 1000) + result;
			num = num/1000;	
			index++;
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
		} if(n % 10 > 0 && n % 10 < 10)
		{
			result += getUnits(n%10, gender) + " ";
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
		StringBuilder builder = new StringBuilder();
		int str_j = 0;
		str_i = 0;
		fileExists(fileName);
		try{
			BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			try{
				String str;
				while((str = in.readLine()) != null){		
					if(str_j == 0){
						for(int i = 0; i<str.length(); i++){	
							if(str.charAt(i) == ',')
								str_j++;
						}str_j++;
					}
					for(int i =0; i< str.length(); i++){
						if(str.charAt(i) == ';')
							str_i++;
					}
					builder.append(str);
				}
			} finally{
				in.close();
			}
		} catch(IOException e){
			throw new RuntimeException(e);
		}
		String str = builder.toString();
		array = new String[str_i][str_j];
			int j = 0, i = 0;
			for(String string: Arrays.toString(str.split(";")).split(",")){
				string = string.replace("[","");
				string = string.replace("]","");
				array[i][j] = string; 
				j++;
				if(j == str_j){i++; j = 0;}
				if(i >= str_i){break;}
			}
			return array;
	}
	
}
