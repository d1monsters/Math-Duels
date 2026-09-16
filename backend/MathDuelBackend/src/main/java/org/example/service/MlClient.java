package org.example.service;

import java.util.Map;

public interface MlClient {

    Map validateAnswer(String question, String correctAnswer, String userAnswer);
}
