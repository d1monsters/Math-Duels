package org.example.web.dto.response;

public class CheckAnswerResponse {

    private boolean correct;

    public CheckAnswerResponse() {
    }

    public CheckAnswerResponse(boolean correct) {
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
