package mindlink;



import com.google.gson.*;
import org.junit.Test;
import java.io.File;  
import java.io.IOException; 
import java.nio.file.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args_to_pass = {"C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\chat.txt", "C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\chat.json"};
        exporter.exportConversation(args_to_pass);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        JsonConverter jc = new JsonConverter();

        //Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(args_to_pass[1])), Conversation.class);
        Conversation c = jc.jsonToConv(args_to_pass[1]);

        assertEquals("My Conversation", c.name);

        assertEquals(8, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(ms[1].content, "General Kenobi, you are a bold one");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
        File f = new File(args_to_pass[1]);
        if(f.delete())                      //returns Boolean value  
        {  
        System.out.println(f.getName() + " deleted");   //getting and printing the file name  
        }  
        else  
        {  
        System.out.println("failed");  
        }
        try
        { 
            Files.deleteIfExists(Paths.get(args_to_pass[1])); 
        } 
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists"); 
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            System.out.println("Directory is not empty."); 
        } 
        catch(IOException e) 
        { 
            System.out.println("Invalid permissions."); 
        } 
          
        System.out.println("Deletion successful."); 
     
    }

    @Test(expected=IllegalArgumentException.class)
    public void testExportingConversationExportsConversationerror() throws IllegalArgumentException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        String[] args_to_pass = {"C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\", "C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\"};
        exporter.exportConversation(args_to_pass);
        JsonConverter jc = new JsonConverter();

        Conversation c = jc.jsonToConv(args_to_pass[1]);

        assertEquals("My Conversation", c.name);

        assertEquals(8, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(ms[1].content, "General Kenobi, you are a bold one");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
    }
    @Test
    public void userTest() throws IOException{
        String[] args_user = {"C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\chat.txt", "C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\usertest.json", "-u:bob"};
    
        //normally, if you already have the file, you'd have it twice, so we should delete it first, THEN put it right back
        
        try
        { 
            Files.deleteIfExists(Paths.get(args_user[1])); 
        } 
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists"); 
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            System.out.println("Directory is not empty."); 
        } 
        catch(IOException e) 
        { 
            System.out.println("Invalid permissions."); 
        } 
          
        System.out.println("Deletion successful.");
        //after deletion, we export again
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation(args_user);
        JsonConverter jc = new JsonConverter();

        Conversation c = jc.jsonToConv(args_user[1]);
        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
        
        
        // so the for loop goes through all messages and checks whether the name is bob, as we thought it should be
        for(int i = 0; i<ms.length-1;i++){
            System.out.println(ms[i].getSenderId());
            System.out.println("i is "+ i);
            
                assertTrue(ms[i].getSenderId().equals("bob"));
                
        }
        
        
    }
        


    @Test
    public void keyAndBlacklistTest() throws IOException{
        
        // we'll run for the most used word: pie
        String[] args_key = {"C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\chat.txt", "C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\keytest.json", "-k:pie", "-b:some,like,society,head"};
    
        //normally, if you already have the file, you'd have it twice, so we should delete it first, THEN put it right back
        try
        { 
            Files.deleteIfExists(Paths.get(args_key[1])); 
        } 
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists"); 
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            System.out.println("Directory is not empty."); 
        } 
        catch(IOException e) 
        { 
            System.out.println("Invalid permissions."); 
        } 
          
        System.out.println("Deletion successful.");
        //after deletion, we export again
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation(args_key);
        JsonConverter jc = new JsonConverter();

        Conversation c = jc.jsonToConv(args_key[1]);
        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
        
        
        // so the for loop goes through all messages and checks whether the keyword pie is there, as we thought it should be
        for(int i = 0; i<ms.length-1;i++){
            assertTrue(ms[i].getContent().contains("pie"));

            System.out.println(ms[i].getContent());
            System.out.println("i is "+ i);
            
                
        }
        //these 2 are sentences where redacted should appear
        assertEquals(ms[0].getContent(), "do you *redacted pie?");
        assertEquals(ms[1].getContent(),"Hell yes! Are we buying *redacted pie?");

        
    }    
    @Test
    public void testObfuscateAndHide() throws IOException{
    // we'll run for the most used word: pie
        String[] args_oh = {"C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\chat.txt", "C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\obfuscateandhide.json", "-o", "-h"};
    
        //normally, if you already have the file, you'd have it twice, so we should delete it first, THEN put it right back
        try
        { 
            Files.deleteIfExists(Paths.get(args_oh[1])); 
        } 
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists"); 
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            System.out.println("Directory is not empty."); 
        } 
        catch(IOException e) 
        { 
            System.out.println("Invalid permissions."); 
        } 
          
        System.out.println("Deletion successful.");
        //after deletion, we export again
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation(args_oh);
        JsonConverter jc = new JsonConverter();

        Conversation c = jc.jsonToConv(args_oh[1]);
        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
        
        
        // so the for loop goes through all messages and checks whether the name is bob, as we thought it should be
        for(int i = 0; i<ms.length-1;i++){
            assertTrue(isNumeric(ms[i].getSenderId()));

            System.out.println(ms[i].getContent());
            System.out.println("i is "+ i);
            
                
        }
        assertTrue(ms[7].getContent().equals("my phone number is *redacted and credit card number is *redacted"));

    
    }
    public static boolean isNumeric(String str) {
  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
}
    
    class InstantDeserializer implements JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonPrimitive()) {
                throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
            }

            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

            if (!jsonPrimitive.isNumber()) {
                throw new JsonParseException("Expected instant represented as JSON number, but different primitive found.");
            }

            return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
        }
    }
}
