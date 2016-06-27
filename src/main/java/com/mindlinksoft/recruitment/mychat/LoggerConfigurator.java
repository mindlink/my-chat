package com.mindlinksoft.recruitment.mychat;

import java.util.logging.ErrorManager;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LoggerConfigurator {
	
	final static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");
	
	/**
	 * Configures logger for the package.
	 * */
	public static void configureLogger() {
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
	private static class CustomHandler extends Handler {
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
	private static class CustomRecordFormatter extends Formatter {
	    @Override
	    public String format(final LogRecord r) {
	        StringBuilder sb = new StringBuilder();
	        sb.append(formatMessage(r)).append(System.getProperty("line.separator"));
	        
	        return sb.toString();
	    }
	}
}
