package src.sql;

import org.sqlite.SQLiteDataSource;
import src.Model.QuestionAnswer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * A class used to connect to a question database.
 *
 * @author Eugene Oh, Yavuzalp Turkoglu, Jonathan Cho
 * @version Spring 2021
 */

public class SQLHelper {

    public static SQLiteDataSource connectToDB() {
        // Establish connection (creates db file if it does not exist :-)
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:questions.db");
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    /**
     * Gets a question and answer from the database.
     *
     * @return The question and answer.
     */
    public static QuestionAnswer getQuestionAnswer() {
        return getQuestionAnswer(connectToDB(), true);
    }

    /**
     * Gets a random question and answer from the database.
     *
     * @return The question and answer.
     */
    public static QuestionAnswer getQuestionAnswer(SQLiteDataSource ds, Boolean isRandom) {
        // Now query the database table for all its contents and display the results
        // System.out.println( "Selecting all rows from test table" );
        // String query = "SELECT * FROM questions";
        int id = 5;
        if (isRandom) {
            Random random = new Random();
            id = random.nextInt(61) + 1;
        }
        String query = String.format("SELECT QUESTION, ANSWER, IS_CORRECT\n" +
                "FROM questions\n" +
                "INNER JOIN answers on answers.QUESTION_ID = questions.QUESTION_ID\n" +
                "WHERE questions.QUESTION_ID = %d;", id);

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(query);

            // Walk through each 'row' of results, grab data by column/field name
            // and print it.
            String[] answers = new String[4];
            int i = 0;
            String question = "";
            String correctAnswer = "";
            while (rs.next()) {
                question = rs.getString("QUESTION");
                String answer = rs.getString("ANSWER");
                Integer is_correct = rs.getInt("IS_CORRECT");
                answers[i++] = answer;
                if (is_correct == 1) {
                    correctAnswer = answer;
                }
            }
            QuestionAnswer questionToReturn = new QuestionAnswer(question, answers, correctAnswer);
            return questionToReturn;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}