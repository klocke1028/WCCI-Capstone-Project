package com.wcci.final_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcci.final_project.entity.Game;
import com.wcci.final_project.repository.GameRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public boolean deleteGame(Long id) {
        if (!gameRepository.existsById(id))
            return false;

        gameRepository.deleteById(id);
        return true;
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    public void updateGame(Game updatedGame) {
        gameRepository.save(updatedGame);
    }

    public List<Game> searchForGamesByTitle(String searchTerm) throws IOException {
        List<Game> searchResults = new ArrayList<>();
        int resultsLimiter = 20;
        String itadApiKey = "7f002b2417b6c356251e81434b37c25a3a28402d";
        URL urlObj = new URL("https://api.isthereanydeal.com/games/search/v1?title=" + searchTerm + "&results="
                + resultsLimiter + "&key=" + itadApiKey);
        HttpsURLConnection itadConnection = (HttpsURLConnection) urlObj.openConnection();
        itadConnection.setRequestMethod("GET");

        int responseCode = itadConnection.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(itadConnection.getInputStream()));
            String inputLine = bufferedReader.readLine();
            StringBuilder response = new StringBuilder();

            response.append(inputLine);

            bufferedReader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode searchResultsNode = objectMapper.readTree(response.toString());

            if (searchResultsNode.isArray()) {
                for (JsonNode gameNode : searchResultsNode) {
                    String title = gameNode.path("title").asText();
                    String itadId = gameNode.path("id").asText();
                    JsonNode assetsNode = gameNode.path("assets");
                    String boxArtLink = "";
                    Game game = new Game(title, itadId, boxArtLink);
                    searchResults.add(game);
                }
            }
        } else {
            System.out.println("Error in getting game search results. Error code: " + responseCode);
        }

        return searchResults;
    }

    // Modify this to do this:
    /*
     * use Steam API
     * First do a get all apps fetch to get IDs and titles
     * We want to loop through the games
     * Store titles and IDs that contain the search term in their title into an
     * array
     * Return the array
     */

    public List<Game> getPopularGames() throws IOException {
        List<Game> waitlistedGames = new ArrayList<>();
        int resultLimiter = 20;
        String itadApiKey = "7f002b2417b6c356251e81434b37c25a3a28402d";
        URL urlObj = new URL("https://api.isthereanydeal.com/stats/most-waitlisted/v1?offset=0&limit=" + resultLimiter
                + "&key=" + itadApiKey);
        HttpsURLConnection itadConnection = (HttpsURLConnection) urlObj.openConnection();
        itadConnection.setRequestMethod("GET");

        int responseCode = itadConnection.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(itadConnection.getInputStream()));
            String inputLine = bufferedReader.readLine();
            StringBuilder response = new StringBuilder();

            response.append(inputLine);

            bufferedReader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode waitlistedResultsNode = objectMapper.readTree(response.toString());

            if (waitlistedResultsNode.isArray()) {
                for (JsonNode gameNode : waitlistedResultsNode) {
                    String title = gameNode.path("title").asText();
                    String itadId = gameNode.path("id").asText();
                    String boxArtLink = getBoxArtLink(itadId, itadApiKey);

                    Game game = new Game();
                    game.setTitle(title);
                    game.setItadId(itadId);
                    game.setBoxArtLink(boxArtLink);

                    waitlistedGames.add(game);
                }
            }
        } else {
            System.out.println("Error in getting most waitlisted games. Error code: " + responseCode);
        }

        return waitlistedGames;
    }

    private String getBoxArtLink(String itadId, String apiKey) throws IOException {
        URL urlObj = new URL("https://api.isthereanydeal.com/games/info/v2?id=" + itadId + "&key=" + apiKey);
        HttpsURLConnection itadConnection = (HttpsURLConnection) urlObj.openConnection();
        itadConnection.setRequestMethod("GET");

        int responseCode = itadConnection.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(itadConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }

            bufferedReader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode gameInfoNode = objectMapper.readTree(response.toString());

            JsonNode assetsNode = gameInfoNode.path("assets");
            if (assetsNode.isObject()) {
                String boxArt = assetsNode.path("boxArt").asText(null);
                String banner600 = assetsNode.path("banner600").asText(null);
                String banner400 = assetsNode.path("banner400").asText(null);
                String banner300 = assetsNode.path("banner300").asText(null);
                String banner145 = assetsNode.path("banner145").asText(null);

                if (boxArt != null && !boxArt.isEmpty()) {
                    return boxArt;
                } else if (banner600 != null && !banner600.isEmpty()) {
                    return banner600;
                } else if (banner400 != null && !banner400.isEmpty()) {
                    return banner400;
                } else if (banner300 != null && !banner300.isEmpty()) {
                    return banner300;
                } else if (banner145 != null && !banner145.isEmpty()) {
                    return banner145;
                }
            }
        } else {
            System.out.println("Error in getting box art link. Error code: " + responseCode);
        }

        return "Image Unavailable";
    }
}
