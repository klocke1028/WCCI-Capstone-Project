package com.wcci.final_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wcci.final_project.entity.Game;
import com.wcci.final_project.repository.GameRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    
    public void searchForGameByTitle(String searchTerm) throws IOException {
        URL urlObj = new URL("https://restcountries.com/v3.1/name/france");
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            StringBuilder response = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            ObjectMapper objectMapper = new ObjectMapper();
			Country[] country = objectMapper.readValue(String.valueOf(response), new TypeReference<Country[]>() {});
			System.out.println(country[0].toString());
			scanner.close();
        } else {
                System.out.println("Error in sending a GET request.");
        }
    }

    // Modify this to do this: 
    /*
     * searchTitles will be the method in our service that makes the request to the external API. We can still use Steam if we want.
     * The searchTitles method should do a fetch and return apps with the type "game" and titles that contain our search term.
     * Need to add steam id as an attribute to game entity
     * First do a get all apps fetch to get IDs and titles 
     * We want to loop through the games and store titles and IDs that contain the search term in their title into an array and then return that array
     */
}
