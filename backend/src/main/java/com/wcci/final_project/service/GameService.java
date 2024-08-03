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
		URL urlObj = new URL("https://api.isthereanydeal.com/games/search/v1?title=" + searchTerm + "&results=" + resultsLimiter + "&key=" + itadApiKey);
        HttpsURLConnection itadConnection = (HttpsURLConnection) urlObj.openConnection();
        itadConnection.setRequestMethod("GET");

        int responseCode = itadConnection.getResponseCode();

		if (responseCode == HttpsURLConnection.HTTP_OK) {
			BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(itadConnection.getInputStream()));
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
     * Store titles and IDs that contain the search term in their title into an array 
     * Return the array
     */
}
