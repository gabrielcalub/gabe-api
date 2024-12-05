package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.client.RestTemplate;

public class Main {

    // URL to fetch data for a specific hero using their hero ID
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String API_URL = "https://www.superheroapi.com/api.php/{API_KEY}/{HERO_ID}";

    public static void main(String[] args) {
        // Define hero IDs (You can replace these with actual hero IDs)
        String heroId1 = "23";  // Example: Spider-Man
        String heroId2 = "38";  // Example: Iron Man

        // Fetch the strength and name of both heroes and compare
        Hero hero1 = getHeroDetails(heroId1);
        Hero hero2 = getHeroDetails(heroId2);

        // Print the hero details
        System.out.println("Hero 1: " + hero1.getName() + " - Strength: " + hero1.getStrength());
        System.out.println("Hero 2: " + hero2.getName() + " - Strength: " + hero2.getStrength());

        // Compare and determine the stronger hero
        if (hero1.getStrength() > hero2.getStrength()) {
            System.out.println(hero1.getName() + " is stronger.");
        } else if (hero2.getStrength() > hero1.getStrength()) {
            System.out.println(hero2.getName() + " is stronger.");
        } else {
            System.out.println("Both heroes have equal strength.");
        }
    }

    // Fetches the hero data and returns a Hero object with the hero's name and strength
    private static Hero getHeroDetails(String heroId) {
        // Make the API call using RestTemplate, pass both API_KEY and heroId as parameters
        String url = API_URL;
        String response = new RestTemplate().getForObject(url, String.class, API_KEY, heroId);

        if (response != null) {
            // Parse the JSON response using Gson
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

            // Extract hero name
            String name = jsonObject.get("name").getAsString();

            // Extract strength value
            int strength = 0;  // Default strength value
            if (jsonObject.has("powerstats")) {
                JsonObject powerstats = jsonObject.getAsJsonObject("powerstats");
                if (powerstats.has("strength")) {
                    strength = powerstats.get("strength").getAsInt();
                }
            }

            // Return a new Hero object with the name and strength
            return new Hero(name, strength);
        }

        // Return a default hero if data is not available
        return new Hero("Unknown Hero", 0);
    }
}
