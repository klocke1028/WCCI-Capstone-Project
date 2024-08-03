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
        if (!gameRepository.existsById(id)) return false;

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
		URL searchGames = new URL("https://api.isthereanydeal.com/games/search/v1?title=" + searchTerm + "&results=" + resultsLimiter + "&key=" + itadApiKey);
        HttpsURLConnection itadConnection = (HttpsURLConnection) searchGames.openConnection();
        itadConnection.setRequestMethod("GET");

        int responseCode = itadConnection.getResponseCode();

		if (responseCode == HttpsURLConnection.HTTP_OK) {
			BufferedReader  searchBufferedReader = new BufferedReader(new InputStreamReader(itadConnection.getInputStream()));
			String searchInputLine = searchBufferedReader.readLine();
            StringBuilder searchResponse = new StringBuilder();

			searchResponse.append(searchInputLine);

			ObjectMapper searchObjectMapper = new ObjectMapper();
            JsonNode searchResultsNode = searchObjectMapper.readTree(searchResponse.toString());

			if (searchResultsNode.isArray()) {
                for (JsonNode gameNode : searchResultsNode) {
					String title = gameNode.path("title").asText();
					String itadId = gameNode.path("id").asText();

                    URL gameInfo = new URL("https://api.isthereanydeal.com/games/info/v2?id=" + itadId + "&key=" + itadApiKey);
                    HttpsURLConnection gameInfoConnection = (HttpsURLConnection) gameInfo.openConnection();
                    gameInfoConnection.setRequestMethod("GET");
                    int gameInfoResponseCode = gameInfoConnection.getResponseCode();

                    if (gameInfoResponseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader  gameBufferedReader = new BufferedReader(new InputStreamReader(gameInfoConnection.getInputStream()));
                        String gameInputLine = gameBufferedReader.readLine();
                        StringBuilder gameResponse = new StringBuilder();

                        gameResponse.append(gameInputLine);

                        ObjectMapper gameObjectMapper = new ObjectMapper();
                        JsonNode gameInfoNode = gameObjectMapper.readTree(gameResponse.toString());
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
                        
                        Game game = new Game(title, itadId, boxArtUrl);
					    searchResults.add(game);
                    } else {
                        System.out.println("Error in getting game info. Error code: " + gameInfoResponseCode);
                    }
                }
            }
		} else {
			System.out.println("Error in getting search results. Error code: " + responseCode);
		}

		return searchResults;
    }
}
