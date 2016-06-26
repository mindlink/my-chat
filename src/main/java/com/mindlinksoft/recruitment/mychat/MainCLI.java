package com.mindlinksoft.recruitment.mychat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.*;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

public class MainCLI {
	
	final static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");
	
	/**
	 * The CLI application entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		configureLogger();
		
		try {	
			//create configuration instance
			ConversationExporterConfiguration config = 
					CommandLineArgumentParser.parseCommandLineArguments(args);
			//create exporter instance
			ConversationExporter exporter = new ConversationExporter(config);
			//do export
			exporter.exportConversation();
			
		} catch (IOException e) {
			LOGGER.log(Level.FINE, e.getStackTrace().toString());
			LOGGER.log(Level.SEVERE, "An error occurred while reading to or "
					+ "writing from file:" + e.getMessage());
		
		} catch(IllegalArgumentException e) {
			LOGGER.log(Level.INFO, "Usage: input_file_path output_file_path \n"
					+ "[-u userid]\n"
					+ "[-k content_keyword]\n"
					+ "[-b 'words to blacklist']\n");

			LOGGER.log(Level.FINE, e.getStackTrace().toString());
			
		} catch(InvalidConfigurationException e) {
			LOGGER.log(Level.SEVERE, "Exporter not set up properly:" +
					e.getMessage());
		}
	}
	
	/**
	 * Configures logger for the package.
	 * */
	private static void configureLogger() {
		LOGGER.setUseParentHandlers(false);
		CustomRecordFormatter formatter = new CustomRecordFormatter();
		CustomHandler handler = new CustomHandler();
		handler.setFormatter(formatter);
		handler.setLevel(Level.FINE);
		LOGGER.addHandler(handler);
	}

	/**
	 * Inner class defining customer handler for logging, taken from: 
	 * {@link http://stackoverflow.com/a/2906222}
	 * */
	static class CustomHandler extends Handler {
		@Override
        public void publish(LogRecord record)
        {
            if (getFormatter() == null)
            {
                setFormatter(new SimpleFormatter());
            }

            try {
                String message = getFormatter().format(record);
                if (record.getLevel().intValue() >= Level.SEVERE.intValue())
                {
                    System.err.write(message.getBytes());                       
                }
                else
                {
                    System.out.write(message.getBytes());
                }
            } catch (Exception exception) {
                reportError(null, exception, ErrorManager.FORMAT_FAILURE);
            }
        }
     	
        @Override
        public void close() throws SecurityException {}
        @Override
        public void flush(){}
	}
	
	/**
	 * Inner class defining a logging formatter, taken from:
	 * {@link http://stackoverflow.com/a/2951641}
	 * */
	static class CustomRecordFormatter extends Formatter {
	    @Override
	    public String format(final LogRecord r) {
	        StringBuilder sb = new StringBuilder();
	        sb.append(formatMessage(r)).append(System.getProperty("line.separator"));
	        
	        return sb.toString();
	    }
	}
	
}
