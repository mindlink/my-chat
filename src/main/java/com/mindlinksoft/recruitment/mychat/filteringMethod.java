package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class filteringMethod {
	String method;
	String filter;
	Collection<Message> messages = new ArrayList<Message>();


	/**
	 * Constructor of class filteringMethod
	 * 
	 * @param method
	 * @param filter
	 * @param messages
	 */
	public filteringMethod(String method,String filter, Collection<Message> messages){
		this.filter=filter;
		this.method=method;
		this.messages=messages;
	}

	/**
	 * 
	 * Distinguish the filtering method that the user has chose and calls the
	 * specific function to achieve that. It returns the new Collection of Messages,
	 * to be exported in JSON format.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Collection<Message> showMethod() throws Exception{

		if(method.equals("1")){
			messages=userFilter(filter);
		}else if(method.equals("2")){
			messages=keywordFilter(filter);
		}else if(method.equals("3")){
			messages=blacklistFilter(filter);
		}else if(method.equals("4")){
			messages=numberFilter();
		}

		return messages;
	}

	/**
	 * 
	 * It takes the userID that we want to separate and choose his/hers 
	 * messages to include in the output. We compare the senderId of all
	 * the messages that are contained into the conversation with the specific
	 * userID.
	 * 
	 * @param userID
	 * @return
	 */
	public List<Message> userFilter(String userID){
		int i=0;
		List<Message> list = getAllMessages();
		List<Message> final_list = new ArrayList<Message>();

		while(i<list.size()){
			if((list.get(i).senderId).equals(userID)){
				final_list.add(list.get(i));
			}
			i++;
		}
		return final_list;
	}

	/**
	 * 
	 * It takes the keyword that we want to filter and choose the  
	 * messages that includes it in the output. We compare the content of all
	 * the messages which may contains the keyword. If it does is included to
	 * the output.
	 * 
	 * 
	 * @param keyword
	 * @return
	 */
	public List<Message> keywordFilter(String keyword){
		int i=0;
		List<Message> list = getAllMessages();
		List<Message> final_list = new ArrayList<Message>();

		while(i<list.size()){
			if((list.get(i).content).contains(keyword)){
				final_list.add(list.get(i));
			}
			i++;
		}
		return final_list;

	}

	/**
	 * 
	 * It takes a path to a file, which contains a list with all the words
	 * that we want to hide from the conversation. It replaces all these words
	 * with *redacted*.
	 * 
	 * @param blacklistPath
	 * @return
	 * @throws Exception
	 */
	public List<Message> blacklistFilter(String blacklistPath) throws Exception{
		List<String> blacklist = new ArrayList<>();
		List<Message> final_list = new ArrayList<Message>();
		List<Message> list = getAllMessages();
		int i=0;
		int j=0;
		boolean flag=false;


		try(InputStream is = new FileInputStream(blacklistPath);
				BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

			String line;

			while ((line = r.readLine()) != null) {
				blacklist.add(line);
			}

		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The file was not found.");
		} catch (IOException e) {
			throw new Exception("Something went wrong");
		}catch (NullPointerException e){
			throw new Exception("File is emtpy");
		}

		while(i<list.size()){
			while(j<blacklist.size()){
				if((list.get(i).content).contains(blacklist.get(j))){
					flag=true;
					list.get(i).content=list.get(i).content.replaceAll(blacklist.get(j), "*redacted*");
					final_list.add(list.get(i));
				}
				j++;
			}
			if(flag==false){
				final_list.add(list.get(i));
			}
			flag=false;
			j=0;
			i++;
		}
		return final_list;

	}

	/**
	 * 
	 * We suppose that credit cards are numbers with 16 digits and phone numbers
	 * with 14 digits. It replaces these numbers with *redacted* to hide them.
	 * 
	 * @return
	 */
	public List<Message> numberFilter(){

		List<Message> final_list = new ArrayList<Message>();
		List<Message> list = getAllMessages();
		int i=0;
		int j=0;
		boolean exists=false;
		boolean existsCard=false;

		Pattern numberPattern= Pattern.compile("[0-9]{14}");
		Pattern cardPattern= Pattern.compile("[0-9]{16}");

		while(i<list.size()){
			String[] split = list.get(i).content.split(" ");
			while(j<split.length){
				exists = numberPattern.matcher(split[j]).matches();
				existsCard = cardPattern.matcher(split[j]).matches();

				if(exists){
					list.get(i).content=list.get(i).content.replaceAll(split[j], "*redacted*");
				}
				if(existsCard){
					list.get(i).content=list.get(i).content.replaceAll(split[j], "*redacted*");
				}
				j++;
				exists=false;
				existsCard=false;
			}
			final_list.add(list.get(i));
			i++;
		}
		return final_list;
	}

	/**
	 * 
	 * It creates a list, which contains all the senderID's, and the number
	 * of messages they send in the conversation. It sorts it from the most
	 * active to the least one.
	 * 
	 * @return
	 */
	public List<UserReport> activityCounter(){

		ArrayList<UserReport> final_list = new ArrayList<UserReport>();
		List<Message> list = getAllMessages();


		int i=0;
		int j=0;
		String user;
		boolean exists=false;

		while(i<list.size()){
			user=list.get(i).senderId;
			while(j<final_list.size()){
				if((final_list.get(j).userID).equals(user)){
					final_list.get(j).counter++;
					exists=true;
				}
				j++;
			}
			if(exists==false){
				UserReport r = new UserReport(user, 1);
				final_list.add(r);
			}
			exists=false;
			j=0;
			i++;
		}
		Collections.sort(final_list, (u1,u2) -> u1.counter-u2.counter);
		Collections.reverse(final_list);

		return final_list;

	}

	/**
	 * 
	 * It returns all the messages of a conversation as a List.
	 * @return
	 */
	public List<Message> getAllMessages() 
	{
		return new ArrayList<>(messages);
	}
}
