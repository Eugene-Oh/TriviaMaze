package src.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.sqlite.SQLiteDataSource;
import src.Model.QuestionAnswer;

/**
 * @author tom capaul
 * <p>
 * Simple class to demonstrate SQLite connectivity
 * 1) create connection
 * 2) add a table
 * 3) add entries to the table
 * 4) query the table for its contents
 * 5) display the results
 * <p>
 * NOTE: any interactions with a database should utilize a try/catch
 * since things can go wrong
 * @see <a href="https://shanemcd.org/2020/01/24/how-to-set-up-sqlite-with-jdbc-in-eclipse-on-windows/">
 * https://shanemcd.org/2020/01/24/how-to-set-up-sqlite-with-jdbc-in-eclipse-on-windows/</a>
 */
public class SQLHelper {

    public static void main(String[] args) {
        SQLiteDataSource ds = connectToDB();
//        createTables(ds);
//        addItemsToTable(ds);
//        getItems(ds);
//        getQuestionAnswer(ds);
//        System.out.println(getQuestionAnswer(ds));

    }

    public static SQLiteDataSource connectToDB() {
        //establish connection (creates db file if it does not exist :-)
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:questions.db");
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

//        System.out.println( "Opened database successfully" );
        return null;
    }

    public static void createTables(SQLiteDataSource ds) {
        //now create a table
        String QuestionQuery = "CREATE TABLE IF NOT EXISTS questions ( " +
                "QUESTION_ID INTEGER PRIMARY KEY AUTOINCREMENT, QUESTION TEXT NOT NULL, " +
                "IS_AUDIO INTEGER NOT NULL DEFAULT 0)";
        String AnswerQuery = "CREATE TABLE IF NOT EXISTS answers ( " +
                "ANSWER_ID INTEGER PRIMARY KEY AUTOINCREMENT, QUESTION_ID INTEGER NOT NULL, " +
                "ANSWER TEXT NOT NULL, IS_CORRECT INTEGER NOT NULL DEFAULT 0, " +
                "FOREIGN KEY (QUESTION_ID) REFERENCES questions(QUESTION_ID))";
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();) {
            int rv = stmt.executeUpdate(QuestionQuery);
            int rv2 = stmt.executeUpdate(AnswerQuery);
//            System.out.println( "executeUpdate() returned " + rv );
//            System.out.println( "executeUpdate() returned " + rv2 );
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
//        System.out.println( "Created questions table successfully" );
//        System.out.println( "Created answers table successfully" );
    }

    public static void addItemsToTable(SQLiteDataSource ds) {
        //next insert two rows of data
//        System.out.println( "Attempting to insert two rows into questions table" );
        String query1 = "INSERT INTO questions ( QUESTION ) VALUES ( 'Last name of Java creator?' )";
        String query2 = "INSERT INTO answers ( QUESTION_ID, ANSWER, IS_CORRECT ) VALUES ( 1, 'Gosling', 1 )";

        String query3 = "INSERT INTO questions ( QUESTION ) VALUES ( 'This statement is false' )";
        String query4 = "INSERT INTO answers ( QUESTION_ID, ANSWER, IS_CORRECT ) VALUES ( 2, 'paradox', 1 )";

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();) {
            int rv = stmt.executeUpdate(query1);
//            System.out.println( "1st executeUpdate() returned " + rv );
            rv = stmt.executeUpdate(query2);
            System.out.println("2st executeUpdate() returned " + rv);

            rv = stmt.executeUpdate(query3);
//            System.out.println( "3nd executeUpdate() returned " + rv );

            rv = stmt.executeUpdate(query4);
//            System.out.println( "4nd executeUpdate() returned " + rv );
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void getItems(SQLiteDataSource ds) {

        //now query the database table for all its contents and display the results
//        System.out.println( "Selecting all rows from test table" );
        String query = "SELECT * FROM questions";

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();) {

            ResultSet rs = stmt.executeQuery(query);

            //walk through each 'row' of results, grab data by column/field name
            // and print it
//            while ( rs.next() ) {
//                String question = rs.getString( "QUESTION" );
//                String answer = rs.getString( "ANSWER" );
//
//                System.out.println( "Result: Question = " + question +
//                    ", Answer = " + answer );
//            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static QuestionAnswer getQuestionAnswer() {
        return getQuestionAnswer(connectToDB(), true);
    }

    public static QuestionAnswer getQuestionAnswer(SQLiteDataSource ds, Boolean isRandom) {

        //now query the database table for all its contents and display the results
//        System.out.println( "Selecting all rows from test table" );
//        String query = "SELECT * FROM questions";
        int id = 5;
        if (isRandom) {
            Random random = new Random();
            id = random.nextInt(39) + 1;
        }
        String query = String.format("SELECT QUESTION, ANSWER, IS_CORRECT\n" +
                "FROM questions\n" +
                "INNER JOIN answers on answers.QUESTION_ID = questions.QUESTION_ID\n" +
                "WHERE questions.QUESTION_ID = %d;", id);

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();) {

            ResultSet rs = stmt.executeQuery(query);

            //walk through each 'row' of results, grab data by column/field name
            // and print it
            String[] answers = new String[4];
            int i = 0;
            String question = "";
            String correctAnswer = "";
            while (rs.next()) {
//                System.out.println(rs);
                question = rs.getString("QUESTION");
                String answer = rs.getString("ANSWER");
                Integer is_correct = rs.getInt("IS_CORRECT");

                answers[i++] = answer;
                if (is_correct == 1) {
                    correctAnswer = answer;
                }

//                System.out.println( "Result: Question = " + question +
//                    ", Answer = " + answer + ", IS_CORRECT = " + is_correct );
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