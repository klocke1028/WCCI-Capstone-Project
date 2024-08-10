package com.wcci.final_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.wcci.final_project.entity.Game;
import com.wcci.final_project.repository.GameRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    private String itadApiKey = "7f002b2417b6c356251e81434b37c25a3a28402d";

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public boolean deleteGame(Long id) {
        if (!gameRepository.existsById(id))
            return false;
        if (!gameRepository.existsById(id))
            return false;

        gameRepository.deleteById(id);
        return true;
    }

    public Game findGameById(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    public Game updateGame(Game updatedGame) {
        return gameRepository.save(updatedGame);
    }

    public List<Game> searchGamesByTitle(String searchTerm) throws IOException {
        List<Game> searchResults = new ArrayList<>();
        int resultsLimiter = 20;
        URL searchGames = new URL("https://api.isthereanydeal.com/games/search/v1?title=" + searchTerm + "&results="
                + resultsLimiter + "&key=" + itadApiKey);
        HttpsURLConnection itadConnection = (HttpsURLConnection) searchGames.openConnection();
        itadConnection.setRequestMethod("GET");

        int responseCode = itadConnection.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader searchBufferedReader = new BufferedReader(
                    new InputStreamReader(itadConnection.getInputStream()));
            String searchInputLine = searchBufferedReader.readLine();
            StringBuilder searchResponse = new StringBuilder();

            searchResponse.append(searchInputLine);

            ObjectMapper searchObjectMapper = new ObjectMapper();
            JsonNode searchResultsNode = searchObjectMapper.readTree(searchResponse.toString());

            if (searchResultsNode.isArray()) {
                for (JsonNode gameNode : searchResultsNode) {
                    Game game = createGameSearchResult(gameNode);
                    searchResults.add(game);
                }
            }
        } else {
            System.out.println("Error in getting search results. Error code: " + responseCode);
        }

        return searchResults;
    }

    public Game createGameSearchResult(JsonNode gameNode) throws IOException {
        Game game = new Game();
        String title = gameNode.path("title").asText();
        String itadId = gameNode.path("id").asText();

        URL gameInfo = new URL("https://api.isthereanydeal.com/games/info/v2?id=" + itadId + "&key=" + itadApiKey);
        HttpsURLConnection gameInfoConnection = (HttpsURLConnection) gameInfo.openConnection();
        gameInfoConnection.setRequestMethod("GET");
        int gameInfoResponseCode = gameInfoConnection.getResponseCode();

        if (gameInfoResponseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader gameBufferedReader = new BufferedReader(
                    new InputStreamReader(gameInfoConnection.getInputStream()));
            String gameInputLine = gameBufferedReader.readLine();
            StringBuilder gameResponse = new StringBuilder();

            gameResponse.append(gameInputLine);

            ObjectMapper gameObjectMapper = new ObjectMapper();
            JsonNode gameInfoNode = gameObjectMapper.readTree(gameResponse.toString());
            String boxArtUrl = getBoxArt(gameInfoNode);

            game = new Game(title, itadId, boxArtUrl);
        } else {
            System.out.println("Error in getting game info. Error code: " + gameInfoResponseCode);
        }

        return game;
    }

    public Game searchGameOnItadByItadId(Game existingGame) throws IOException {
        Game game = new Game();
        String title = existingGame.getTitle();
        String itadId = existingGame.getItadId();

        URL gameInfo = new URL("https://api.isthereanydeal.com/games/info/v2?id=" + itadId + "&key=" + itadApiKey);
        HttpsURLConnection gameInfoConnection = (HttpsURLConnection) gameInfo.openConnection();
        gameInfoConnection.setRequestMethod("GET");
        int gameInfoResponseCode = gameInfoConnection.getResponseCode();

        if (gameInfoResponseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader gameBufferedReader = new BufferedReader(
                    new InputStreamReader(gameInfoConnection.getInputStream()));
            String gameInputLine = gameBufferedReader.readLine();
            StringBuilder gameResponse = new StringBuilder();

            gameResponse.append(gameInputLine);

            ObjectMapper gameObjectMapper = new ObjectMapper();
            JsonNode gameInfoNode = gameObjectMapper.readTree(gameResponse.toString());
            String boxArtUrl = getBoxArt(gameInfoNode);

            game = new Game(title, itadId, boxArtUrl);
        } else {
            System.out.println("Error in getting game info. Error code: " + gameInfoResponseCode);
        }

        return game;
    }

    public String getBoxArt(JsonNode gameInfoNode) {
        String boxArtUrl = gameInfoNode.path("assets").path("boxart").asText();

        if (boxArtUrl.isEmpty()) {
            String banner600 = gameInfoNode.path("assets").path("banner600").asText();
            boxArtUrl = banner600;
        }

        if (boxArtUrl.isEmpty()) {
            String banner400 = gameInfoNode.path("assets").path("banner400").asText();
            boxArtUrl = banner400;
        }

        if (boxArtUrl.isEmpty()) {
            String banner300 = gameInfoNode.path("assets").path("banner300").asText();
            boxArtUrl = banner300;
        }

        if (boxArtUrl.isEmpty()) {
            String banner145 = gameInfoNode.path("assets").path("banner145").asText();
            boxArtUrl = banner145;
        }

        return boxArtUrl;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Double getBestPrice(String shopIds, String gameItadId) throws IOException {
        Double bestPrice = 0.0;
        String itadApiKey = "7f002b2417b6c356251e81434b37c25a3a28402d";
        String requestBody = "[ \"" + gameItadId + "\" ]";
    
        URL getBestPriceUrl = new URL("https://api.isthereanydeal.com/games/prices/v2?country=US&nondeals=true&vouchers=false&shops=" + shopIds + "&key=" + itadApiKey);
        HttpsURLConnection getBestPriceConnection = (HttpsURLConnection) getBestPriceUrl.openConnection();
        
        getBestPriceConnection.setRequestMethod("POST");
        getBestPriceConnection.setDoOutput(true); 
        getBestPriceConnection.setRequestProperty("Content-Type", "application/json");

        OutputStreamWriter getBestPriceWriter = new OutputStreamWriter(getBestPriceConnection.getOutputStream(), "UTF-8");

        getBestPriceWriter.write(requestBody);
        getBestPriceWriter.close();

        int getBestPriceResponseCode = getBestPriceConnection.getResponseCode();

        if (getBestPriceResponseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader  bestPriceReader = new BufferedReader(new InputStreamReader(getBestPriceConnection.getInputStream()));
            String bestPriceInputLine = bestPriceReader.readLine();
            ObjectMapper bestPriceMapper = new ObjectMapper();
            JsonNode bestPriceNode = bestPriceMapper.readTree(bestPriceInputLine);
            System.out.println(bestPriceNode.asText());
        } else {
            System.out.println("Error in getting best price. Response Code: " + getBestPriceResponseCode);
        }

        return bestPrice;
    }

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

    public Game getGameProperties(JsonNode gameNode) throws IOException {
        String title = gameNode.path("title").asText();
        String itadId = gameNode.path("id").asText();
    
        URL gameInfoUrl = new URL("https://api.isthereanydeal.com/games/info/v2?id=" + itadId + "&key=" + itadApiKey);
        HttpsURLConnection connection = (HttpsURLConnection) gameInfoUrl.openConnection();
        connection.setRequestMethod("GET");
    
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpsURLConnection.HTTP_OK) {
            throw new IOException("Failed to get game info: HTTP code " + responseCode);
        }
    
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
    
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode gameInfoNode = objectMapper.readTree(response.toString());
    
        title = gameInfoNode.path("title").asText();
        String boxArtUrl = null;
        JsonNode assetsNode = gameInfoNode.path("assets");
        if (assetsNode.isObject()) {
            boxArtUrl = assetsNode.path("boxart").asText(null);
            if (boxArtUrl == null || boxArtUrl.isEmpty()) {
                boxArtUrl = assetsNode.path("banner600").asText(null);
            }
            if (boxArtUrl == null || boxArtUrl.isEmpty()) {
                boxArtUrl = assetsNode.path("banner400").asText(null);
            }
            if (boxArtUrl == null || boxArtUrl.isEmpty()) {
                boxArtUrl = assetsNode.path("banner300").asText(null);
            }
            if (boxArtUrl == null || boxArtUrl.isEmpty()) {
                boxArtUrl = assetsNode.path("banner145").asText(null);
            }
        }
    
        JsonNode tagsNode = gameInfoNode.path("tags");
        List<String> tags = new ArrayList<>();
        if (tagsNode.isArray()) {
            for (JsonNode tagNode : tagsNode) {
                tags.add(tagNode.asText());
            }
        }
    
        return new Game(title, itadId, boxArtUrl, tags);
    }
}
