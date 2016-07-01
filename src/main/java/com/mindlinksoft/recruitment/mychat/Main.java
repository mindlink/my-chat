package com.mindlinksoft.recruitment.mychat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import exceptionHandeling.readExcep;
import exceptionHandeling.writeExcep;
/**
 * program entry point
 * 
 * @author Asmaa
 *
 */
public class Main {

	public static void main(String[] args) {
		try{
						
			ConversationExporter convoExp = new ConversationExporter();
			convoExp.exportConversation(args);
			
		} catch (IllegalArgumentException e){
			System.out.println("ERROR: invalid arguments");
			e.printStackTrace();

		} catch (readExcep e){
			System.out.println("ERROR: problem reading file");
			e.printStackTrace();

		} catch (writeExcep e){
			System.out.println("ERROR: problem writing file");
			e.printStackTrace();
			
		} catch (Exception e){
			System.out.println("ERROR: unknown error");
			e.printStackTrace();
		}
		
		clearFile();
	
	}
	
	public static void clearFile() throws writeExcep {
		try (OutputStream outputStream = new FileOutputStream("chat.json", false)) {

			outputStream.write(0);

		} catch (IOException e) {
			throw new writeExcep("Test Error while writing to the output file ", e);			
		}
	}

}
