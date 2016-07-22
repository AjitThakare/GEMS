package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SaveLogPerDate {
	
	public static void main(String[] args) {
		String fileName="E:\\Totoise.git\\GEMS\\logfile.log"; // File to backup
		
		File source = new File(fileName);
		System.out.println(source.getAbsolutePath());
		File dest=new File("e:\\LOGS"); //  Here backups are stored
		String outputFileName = null;
		Boolean proceed=false;
		try {
			Scanner sc= new Scanner(source);
			if(sc.hasNext())
			{
				outputFileName =sc.next();		// Read first string 
				proceed= true;
			}
			else
			{	System.out.println("Last log has been saved successfully... No new data to be saved");
				JOptionPane.showMessageDialog(null, "Last log has been saved successfully... No new data to be saved");
			}
			if(proceed)
			{
			dest.mkdirs();
			dest= new File("e:\\LOGS\\"+outputFileName+".log");
			try {
				dest.createNewFile();
				System.out.println("Backup file created successfully...");
			} catch (IOException e1) {
				System.out.println("Error in creating file . . .");
				e1.printStackTrace();
			}
			try {
				copyFile(source, dest); System.out.println("Data copied from source to Destination");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				FileWriter fwriter= new FileWriter(source);
				fwriter.write("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		} catch (FileNotFoundException e) {
			
		} // TODO Auto-generated method stub

	}
	private static void copyFile(File source, File dest) throws IOException {
		InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
}
