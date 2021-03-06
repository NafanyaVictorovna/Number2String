package digit.parser;

public class TransformNumberRu extends AbstractTransformNumber{
	
	private String[][] forms;
	
	final String[][] units = {
			{"","один","два","три","четыре","пять","шесть","семь","восемь","девять"},
			{"","одна","две","три","четыре","пять","шесть","семь","восемь","девять"}
	};
	
	final String[] hundreds = {"", "сто","двести","триста","четыреста","пятьсот",
								"шестьсот","семьсот","восемьсот","девятьсот"};
	
	final String[] decades_11_19 = {"","одиннадцать","двенадцать","тринадцать",
									"четырнадцать","пятнадцать","шестнадцать",
									"семнадцать","восемнадцать","девятнадцать"};
	
	final String[] decades = {"","десять","двадцать","тридцать","сорок","пятьдесят",
								"шестьдесят","семьдесят","восемьдесят","девяносто"};
		
	public void setForms(String[][] array){
		forms = array;
	}
	
	public String[][] getForms(){
		return forms;
	}
	
	protected String getUnits(int n, int gender) {
		return units[gender][n];
	}

	protected String getDecades(int n) {
		return decades[n];
	}

	protected String get11_19(int n) {
		return decades_11_19[n];
	}

	protected String getHundreds(int n) {
		return hundreds[n];
	}

	protected String getUnitTitle(int index, double count) {
		if(count%100 > 4 && count%100 < 21){
			return forms[index][2];
		}
		switch((int)(count%10)){
		case 1: return forms[index][0];
		case 2: case 3: case 4: return forms[index][1];
		default: return forms[index][2];
		}
	}

	protected int getGender(int index) {
		return Integer.parseInt(forms[index][3]);
	}
}