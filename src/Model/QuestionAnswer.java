package src.Model;

public class QuestionAnswer {
    private String myQuestion;
    private String myCorrectAnswer;
    private String[] myAnswers;
    private Boolean isAnswered;

    /**
     *
     * @param theQuestion The string question
     * @param theAnswers a list of answer options
     * @param theCorrectAnswer the correct answer
     */
    public QuestionAnswer(String theQuestion, String[] theAnswers, String theCorrectAnswer){
        myQuestion = theQuestion;
        myAnswers = theAnswers;
        myCorrectAnswer = theCorrectAnswer;
        isAnswered = false;

    }

    public Boolean isRightAnswer(String theAnswer) {
        return myCorrectAnswer.equals(theAnswer);
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


    public Boolean getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(Boolean answered) {
        isAnswered = answered;
    }
}
