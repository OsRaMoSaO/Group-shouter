package com.ylf.gshout;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class httpChecker {

    String groupID = shouterBot.config.get("GROUP_ID");

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String prevStatus = null;

    public String[] checkCondition() {

        String startedWord = shouterBot.config.get("KEYWORD_STARTED").toLowerCase();
        String endedWord = shouterBot.config.get("KEYWORD_ENDED").toLowerCase();

        Request request = new Request.Builder()
                .url("https://groups.roblox.com/v1/groups/" + groupID)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();

            boolean n = parseShoutBodyFromResponse(responseBody);
            if (!n){
                return null;
            }

            String username = getData(responseBody, "username");
            String content = getData(responseBody, "body");

            if (content.toLowerCase().contains(startedWord)) {
                content = "started";
            }
            if (content.toLowerCase().contains(endedWord)) {
                content = "ended";
            }

            String[] data = {content, username};
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{"error"};
        }
    }

    private Boolean parseShoutBodyFromResponse(String responseBody) {
        try {
            String startedWord = shouterBot.config.get("KEYWORD_STARTED").toLowerCase();
            String endedWord = shouterBot.config.get("KEYWORD_ENDED").toLowerCase();

            // Parse the JSON response
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            // Navigate to shout.body
            JsonNode shoutNode = jsonNode.path("shout");
            if (shoutNode.isMissingNode()) {
                return false; // shout node is not present
            }

            String content = getData(responseBody, "body");
            if (content.toLowerCase().contains(startedWord)) {
                if (prevStatus == null)
                {
                    prevStatus = startedWord;
                    return false;
                }
                else if (prevStatus.equals(endedWord))
                {
                    prevStatus = startedWord;
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (content.toLowerCase().contains(endedWord)) {
                if (prevStatus == null)
                {
                    prevStatus = endedWord;
                    return false;
                }
                else if (prevStatus.equals(startedWord))
                {
                    prevStatus = endedWord;
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }

            ///Old checking method
            //String newDate = shoutNode.path("updated").asText();
           // if (newDate.equals(lastDate)) {
            //    return false;
            //} else {
           //     lastDate = newDate;
            //    return true;
            ///}
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getData(String responseBody, String field) {
        try {
            // Parse the JSON response
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            // Navigate to shout
            JsonNode shoutNode = jsonNode.path("shout");
            if (shoutNode.isMissingNode()) {
                return null; // shout node is not present
            }
            if (field.equals("username")) {
                String text = shoutNode.path("poster").path("username").asText();
                return text;
            } else {
                return shoutNode.path(field).asText();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
