package comparators;

import javax.swing.table.DefaultTableModel;

import fileManager.StringVector;

public class Basic {
	public Basic(){
		
	}
	
	public void compare(StringVector older, StringVector newer){
		
		int deleted = 0;
		
		for(int i=0; i<older.size(); i++){
			
			boolean found = false;
			
			for(int j=0; j<newer.size() && !found; j++){
				
				String olderLine = older.getData()[i].replace(" ", "");
				String newerLine = newer.getData()[j].replace(" ", "");

				
				if( olderLine.compareTo(newerLine) == 0 ){
					found = true;
				}
			}
			
			if(!found) deleted++;
		}
		
		int added = 0;
		
		for(int i=0; i<newer.size(); i++){
			
			boolean found = false;
			
			for(int j=0; j<older.size() && !found; j++){
				
				String newerLine = newer.getData()[i].replace(" ", "");
				String olderLine = older.getData()[j].replace(" ", "");
				
				if( newerLine.compareTo(olderLine) == 0 ){
					found = true;
				}
			}
			
			if(!found) added++;
		}
	}
	
	public int count(){
		return 0;
	}
}
