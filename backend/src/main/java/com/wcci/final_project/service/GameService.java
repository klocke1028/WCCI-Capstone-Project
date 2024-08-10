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
}
