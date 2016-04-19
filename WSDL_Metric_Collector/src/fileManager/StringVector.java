package fileManager;

public class StringVector {
	
	private String data[];
	private int count;
	
	public StringVector(){
		count = 0;
		data = null;
	}
	
	public void addString(String newString){		
		String auxiliarData[] = new String[count+1];

		for(int i=0; i<count; i++){

			auxiliarData[i] = data[i];

		}

		auxiliarData[count] = newString;

		data = auxiliarData;

		count++;
	}
	
	public int size(){
		return count;
	}
	
	public String[] getData(){
		return data;
	}
}
