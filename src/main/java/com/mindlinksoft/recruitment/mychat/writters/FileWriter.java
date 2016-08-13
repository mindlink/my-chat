package main.java.com.mindlinksoft.recruitment.mychat.writters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.com.mindlinksoft.recruitment.mychat.exceptions.FileAlreadyExistsExc;

public class FileWriter implements Writer {
	private static final Logger LOGGER = Logger.getLogger(FileWriter.class.getName() );
	
	private final String filePath;
	
	public FileWriter(String filePath) throws FileAlreadyExistsExc{
		File f = new File(filePath);
		if (f.exists()){
			String err = String.format(
					"Cannot create file %s. File already exists.", filePath);
			throw new FileAlreadyExistsExc(err);
		}

		this.filePath = filePath;
	}
	
	public boolean write(String content) {
		try (OutputStream os = new FileOutputStream(this.filePath, true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
			
			bw.write(content);
			return true;
		} catch (FileNotFoundException e) {
			String error = String.format(
					"Failed to write conversation to path '%s'. File was not found.", this.filePath);
			LOGGER.log(Level.SEVERE, error);
			return false;
		} catch (IOException e) {
			String error = String.format("Failed to write conversation to file '%s'.", this.filePath);
			LOGGER.log(Level.SEVERE, error);
			return false;
		}
	}
}
