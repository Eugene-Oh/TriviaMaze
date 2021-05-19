package src.Model;

public class QuestionAnswer {
    private String myQuestion;
    private String myCorrectAnswer;
    private String[] myAnswers;

    /**
     *
     * @param theQuestion
     * @param theAnswers
     * @param theCorrectAnswer
     */
    public QuestionAnswer(String theQuestion, String[] theAnswers, String theCorrectAnswer){
        myQuestion = theQuestion;
        myAnswers = theAnswers;
        myCorrectAnswer = theCorrectAnswer;

    }

    public Boolean isRightAnswer(int theChoice) {
        return myCorrectAnswer == myAnswers[theChoice];
    }

    public String getMyQuestion() {
        return myQuestion;
    }

    public String[] getAnswers() {
        return myAnswers;
    }

    public String getCorrectAnswer() {
        return myCorrectAnswer;
    }


}
