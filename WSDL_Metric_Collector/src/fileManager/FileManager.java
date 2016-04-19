package fileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class FileManager {
	
	public FileManager(){}
	
	public StringVector readFile(String filePath){
		File file = new File(filePath);
		return readFile(file);
	}
	
	public StringVector readFile(File file){
		
		String text = null;
		StringVector data = new StringVector();
		BufferedReader reader = null;

		try {
		    reader = new BufferedReader(new FileReader(file));
		    
		    while ((text = reader.readLine()) != null){
		    	if(!text.trim().equals(""))
		    		data.addString(text);
		    }
		}
		catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
		finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}
		return data;
	}
	
	public Vector<StringVector> readMetrics(String filePath){
		File file = new File(filePath);
		return readMetrics(file);
	}

	public Vector<StringVector> readMetrics(File file){
		Vector<StringVector> metrics = new Vector<StringVector>();
		BufferedReader reader = null;
		
		try {
			InputStream myInputStream = new FileInputStream(file);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			WSDLHandler wsdlHandler = new WSDLHandler();
			
			saxParser.parse(myInputStream, wsdlHandler);
			
			/*
			data.addElement(wsdlHandler.getSimpleTypes());
			data.addElement(wsdlHandler.getComplexTypes());
			data.addElement(wsdlHandler.getElements());
			*/
			
			metrics.addElement(wsdlHandler.getMessages());
			metrics.addElement(wsdlHandler.getPortTypes());
			metrics.addElement(wsdlHandler.getOperations());
		}
		catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
		catch (IOException e) {
		    e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } 
		    catch (IOException e) {
		    }
		}
		return metrics;
	}
	
	public void writeFile(String filePath, String fileContent){		
        BufferedWriter output = null;
        
        try {
            File file = new File(filePath);
            output = new BufferedWriter(new FileWriter(file));
            output.write(fileContent);
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        finally {
            if ( output != null ){
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
	}

}
