package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.client.RestTemplate;


public class Main {

    public static void main (String[] args) {
        String json = new RestTemplate().getForObject(
                "https://official-joke-api.appspot.com/random_ten", String.class);

        JsonArray response = JsonParser.parseString(json).getAsJsonArray();
        JsonObject fifthJoke = response.get(4).getAsJsonObject();
        String setup = fifthJoke.get("setup").getAsString();
        String punchline = fifthJoke.get("punchline").getAsString();


        System.out.println(setup + " - " + punchline);
    }

}