package src.Model;

import java.util.Arrays;

/**
 * A class representing questions and answers.
 *
 * @author Eugene Oh, Jonathan Cho, Yavuzalp Turkoglu
 * @version Spring 2021
 */

public class QuestionAnswer {

    /**
     * String for the question.
     */
    private String myQuestion;

    /**
     * String for the correct answer.
     */
    private String myCorrectAnswer;

    /**
     * String Array for the answers.
     */
    private String[] myAnswers;

    /**
     * Boolean which represents whether the question has been answered or not.
     */
    private Boolean isAnswered;

    /**
     * Constructor for setting the questions and answers.
     *
     * @param theQuestion      The questions string.
     * @param theAnswers       A list of answer options.
     * @param theCorrectAnswer The correct answer.
     */
    public QuestionAnswer(String theQuestion, String[] theAnswers, String theCorrectAnswer) {
        myQuestion = theQuestion;
        myAnswers = theAnswers;
        myCorrectAnswer = theCorrectAnswer;
        isAnswered = false;
    }

    /**
     * Checks if the answer is correct.
     *
     * @param theAnswer The inputted answer.
     * @return Boolean value depending if the answer was correct or not.
     */
    public Boolean isCorrectAnswer(String theAnswer) {
        return myCorrectAnswer.equals(theAnswer);
    }

    /**
     * Getter for question.
     *
     * @return String representing the question.
     */
    public String getMyQuestion() {
        return myQuestion;
    }

    /**
     * Getter for answers.
     *
     * @return String Array of answers.
     */
    public String[] getAnswers() {
        return myAnswers;
    }

    /**
     * Getter for question.
     *
     * @return String representing the correct answer.
     */
    public String getCorrectAnswer() {
        return myCorrectAnswer;
    }

    /**
     * Getter for isAnswered.
     *
     * @return Boolean which represents whether the question has been answered or not.
     */
    public Boolean getIsAnswered() {
        return isAnswered;
    }

    /**
     * Sets is Answered.
     *
     * @param answered Boolean which represents whether the question has been answered or not.
     */
    public void setIsAnswered(Boolean answered) {
        isAnswered = answered;
    }

    /**
     * toString method.
     *
     * @return Formatted String.
     */
    public String toString() {
        return "Question: " + myQuestion + "\nAnswers: " + Arrays.toString(myAnswers) + "\nCorrect Answer: " + myCorrectAnswer;
    }
}
