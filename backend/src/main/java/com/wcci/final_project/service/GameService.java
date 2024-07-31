package com.wcci.final_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wcci.final_project.entity.Game;
import com.wcci.final_project.repository.GameRepository;

import java.util.List;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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
}
