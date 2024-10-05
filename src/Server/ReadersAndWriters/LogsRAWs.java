package Server.ReadersAndWriters;

import Server.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

public class LogsRAWs {

    public static void writeTimeIn(String username, String timeIn){
        File file = new File("main/resources/UserLogs.json");

        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray logs = fileObject.get("logs").getAsJsonArray();
            if(!checkExistence(username)){
                JsonObject mainUserObject = new JsonObject();
                JsonArray newUserLogsArray = new JsonArray();
                mainUserObject.addProperty("username",username);
                mainUserObject.add("userLogs",newUserLogsArray);
                logs.add(mainUserObject);
            }
            for(int i = 0; i < logs.size(); i++){
                JsonObject user = logs.get(i).getAsJsonObject();
                if(user.get("username").getAsString().equals(username)){
                    JsonArray userLogs = user.getAsJsonArray("userLogs");
                    JsonObject newUserLog = new JsonObject();
                    newUserLog.addProperty("timeIn",timeIn);
                    userLogs.add(newUserLog);
                    break;
                }
            }
            FileWriter writer = new FileWriter("main/resources/UserLogs.json");
            writer.write(fileObject.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeTimeOut(String username, String timeOut) {
        File file = new File("main/resources/UserLogs.json");

        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray logs = fileObject.get("logs").getAsJsonArray();
            for(int i = 0; i < logs.size(); i++) {
                JsonObject user = logs.get(i).getAsJsonObject();
                if (user.get("username").getAsString().equals(username)) {
                    JsonArray userLogs = user.getAsJsonArray("userLogs");
                    JsonObject log = userLogs.get(userLogs.size()-1).getAsJsonObject();
                    log.addProperty("timeOut", timeOut);
                }
            }

            FileWriter writer = new FileWriter("main/resources/UserLogs.json");
            writer.write(fileObject.toString());
            writer.close();

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static boolean checkExistence(String key){
        File file = new File("main/resources/UserLogs.json");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray logsList = fileObject.get("logs").getAsJsonArray();
            for (JsonElement user : logsList) {
                JsonObject userObject = user.getAsJsonObject();
                if (Objects.equals(userObject.get("username").getAsString(), key)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public static ArrayList<ArrayList<String>> readLogs(String username){
        File file = new File("main/resources/UserLogs.json");
        ArrayList<ArrayList<String>> logData = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        SimpleDateFormat getDate = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat getTime = new SimpleDateFormat("HH:mm:ss");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray logsList = fileObject.get("logs").getAsJsonArray();
            for(JsonElement user: logsList){
                JsonObject userObject = user.getAsJsonObject();
                if(Objects.equals(userObject.get("username").getAsString(), username)){
                    JsonArray userArray = userObject.get("userLogs").getAsJsonArray();
                    for(JsonElement logs : userArray){
                        ArrayList<String> temp = new ArrayList<>();

                        JsonObject logsObject = logs.getAsJsonObject();

                        String inDate = logsObject.get("timeIn").getAsString();
                        Date timeInDate = formatter.parse(inDate);

                        temp.add(getDate.format(timeInDate));
                        temp.add(getTime.format(timeInDate));

                        String outDate;
                        if(logsObject.get("timeOut") != null){
                            outDate = logsObject.get("timeOut").getAsString();
                            Date timeOutDate = formatter.parse(outDate);
                            temp.add(getTime.format(timeOutDate));
                        }
                        else{
                            temp.add(null);
                        }
                        logData.add(temp);
                    }
                }
            }
            System.out.println(logData);
        } catch (FileNotFoundException | ParseException e) {
            throw new RuntimeException(e);
        }
        return logData;
    }

    public static HashMap<String, String> checkLastTimeOut() {
        HashMap<String, String> status = new HashMap<>();
        File file = new File("main/resources/UserLogs.json");
        try{
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();
            JsonArray logs = fileObject.getAsJsonArray("logs");

            for (int i = 0; i < logs.size(); i++) {
                JsonObject obj = logs.get(i).getAsJsonObject();
                    JsonArray userLogs = obj.getAsJsonArray("userLogs");
                    if (userLogs.size() > 0) {
                        JsonObject lastLog = userLogs.get(userLogs.size()-1).getAsJsonObject();
                        if (lastLog.has("timeOut")) {
                            status.put(obj.get("username").getAsString(),"On Break");
                        }
                        else{
                            status.put(obj.get("username").getAsString(),"Working");
                        }
                    }
            }
            return status;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<ArrayList<String>> getLogsInRange(String startTime, String endTime) throws Exception {

        ArrayList<ArrayList<String>> logsInRange = new ArrayList<>();

        File file = new File("main/resources/UserLogs.json");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();
            JsonArray logs = fileObject.get("logs").getAsJsonArray();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

            Date startDate = dateFormat.parse(startTime);
            Date endDate = dateFormat.parse(endTime);

            for (int i = 0; i < logs.size(); i++) {
                JsonObject userObj =  logs.get(i).getAsJsonObject();
                JsonArray userLogs = userObj.get("userLogs").getAsJsonArray();
                String username = userObj.get("username").getAsString();

                for (int j = 0; j < userLogs.size(); j++) {

                    JsonObject logObj = userLogs.get(j).getAsJsonObject();

                    JsonElement timeInElement = logObj.get("timeIn");
                    JsonElement timeOutElement = logObj.get("timeOut");

                    if (timeInElement != null && timeOutElement != null) {
                        String timeInStr = timeInElement.getAsString();
                        String timeOutStr = timeOutElement.getAsString();

                        Date timeIn = dateFormat.parse(timeInStr);
                        Date timeOut = dateFormat.parse(timeOutStr);

                        if (timeIn.compareTo(startDate) >= 0 && timeOut.compareTo(endDate) <= 0) {
                            ArrayList<String> log = new ArrayList<>();
                            log.add(username);
                            log.add(timeInStr);
                            log.add(timeOutStr);
                            logsInRange.add(log);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
            return logsInRange;
    }

    public static HashMap<String, Double> calculateHoursRendered(ArrayList<ArrayList<String>> logsInRange) {
        HashMap<String, Double> hoursRendered = new HashMap<>();
        DecimalFormat formatter = new DecimalFormat("0.00");

        for (List<String> log : logsInRange) {
            String username = log.get(0);
            String timeInStr = log.get(1);
            String timeOutStr = log.get(2);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

            try {
                Date timeIn = dateFormat.parse(timeInStr);
                Date timeOut = dateFormat.parse(timeOutStr);

                double MilliSec = timeOut.getTime() - timeIn.getTime();
                double hours = Double.parseDouble(formatter.format(MilliSec / (60.0 * 60.0 * 1000.0)));


                if (hoursRendered.containsKey(username)) {
                    double totalHours = hoursRendered.get(username) + hours;
                    hoursRendered.put(username, totalHours);
                } else {
                    hoursRendered.put(username, hours);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return hoursRendered;
    }

    public static void main(String[] args) {
//        readLogs("Rohit");
//        writeTimeIn("Melody","23-12-12 12:00:00");
        try {
            System.out.println(getLogsInRange("23-04-03 00:00:00","23-05-03 20:02:30"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
