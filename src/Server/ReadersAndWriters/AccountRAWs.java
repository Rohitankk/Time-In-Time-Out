package Server.ReadersAndWriters;

import com.google.gson.*;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

public class AccountRAWs {
    public static HashMap<String,String> readUsers(){
        HashMap<String,String> credentials = new HashMap<>();

        File file = new File("main/resources/Users.json");
        try{
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray jsonCredentials = fileObject.get("users").getAsJsonArray();
            for(JsonElement credentialElement : jsonCredentials){
                JsonObject credentialObject = credentialElement.getAsJsonObject();
                credentials.put(credentialObject.get("username").getAsString(),
                        credentialObject.get("password").getAsString());
            }

            return credentials;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // ADD USER //
    public static void addUser(String username, String password){
        File file = new File("main/resources/Users.json");
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(file));
            JsonArray usersArray = jsonObject.getAsJsonArray("users");

            JsonObject newUser = new JsonObject();
            newUser.addProperty("username", username);
            newUser.addProperty("password", password);

            usersArray.add(newUser);

            FileWriter fileWriter= new FileWriter("main/resources/Users.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.close();

            System.out.println("Successfully added a new element to the file");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // DELETE USER //
    public static void deleteUser(String username){
        File file = new File("main/resources/Users.json");
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(file));
            JsonArray usersArray = jsonObject.getAsJsonArray("users");
            for (int i = 0; i < usersArray.size(); i++) {
                JsonObject request = usersArray.get(i).getAsJsonObject();
                if (request.get("username").getAsString().equals(username)) {
                    usersArray.remove(i);
                    FileWriter writer = new FileWriter("main/resources/Users.json");
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();
                    System.out.println("Deleted request " + username);
                    return;
                }
            }
            System.out.println("Request not found: " + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ACCOUNT REQUESTS READER AND WRITER//

    public static HashMap<String, String> readRequests(){
        HashMap<String, String> credentials = new HashMap<>();
        File file = new File("main/resources/Requests.json");
        try{
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray jsonCredentials = fileObject.get("requests").getAsJsonArray();
            for (JsonElement credentialElement: jsonCredentials){
                JsonObject credentialObject = credentialElement.getAsJsonObject();
                credentials.put(credentialObject.get("username").getAsString(),
                        credentialObject.get("password").getAsString());
            }

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        return credentials;
    }

    public static void addRequest(String username, String password){
        File file = new File("main/resources/Requests.json");
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(file));
            JsonArray requestsArray = jsonObject.getAsJsonArray("requests");

            JsonObject newRequest = new JsonObject();
            newRequest.addProperty("username", username);
            newRequest.addProperty("password", password);

            requestsArray.add(newRequest);

            FileWriter fileWriter= new FileWriter("main/resources/Requests.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.close();

            System.out.println("Successfully added a new element to the file");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void deleteRequest(String username){
        File file = new File("main/resources/Requests.json");
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(file));
            JsonArray requestArray = jsonObject.getAsJsonArray("requests");
            for (int i = 0; i < requestArray.size(); i++) {
                JsonObject request = requestArray.get(i).getAsJsonObject();
                if (request.get("username").getAsString().equals(username)) {
                    requestArray.remove(i);
                    FileWriter writer = new FileWriter("main/resources/Requests.json");
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();
                    System.out.println("Deleted request " + username);
                    return;
                }
            }
            System.out.println("Request not found: " + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
