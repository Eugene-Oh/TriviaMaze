package src.View;

/**
 * This class will be the logic behind the amount of questions answered.
 *
 * @author Eugene Oh, Yavuzalp Turkoglu, Jonathan Cho
 * @version Spring 2021
 */

import javax.swing.*;
import java.awt.*;

public class QuestionsAnsweredCounter extends JPanel {

    /**
     * The amount of questions answered right by the user.
     */
    private int myQuestionsAnsweredRight;

    /**
     * The amount of questions answered wrong by the user.
     */
    private int myQuestionsAnsweredWrong;

    /**
     * The total mount of questions answered.
     */
    private int myQuestionsAnsweredTotal;

    /**
     * Default constructor.
     */
    public QuestionsAnsweredCounter() {
        myQuestionsAnsweredRight = 0;
        myQuestionsAnsweredWrong = 0;
        myQuestionsAnsweredTotal = 0;
    }

    /**
     * Displays the questions answered onto the GUI.
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Correct:", 40, 100);
        g.drawString(String.valueOf(myQuestionsAnsweredRight), 120, 100);
        g.drawString("Wrong:", 40, 150);
        g.drawString(String.valueOf(myQuestionsAnsweredWrong), 110, 150);
        g.drawString("Total:", 40, 200);
        g.drawString(String.valueOf(myQuestionsAnsweredTotal), 100, 200);
    }

    /**
     * Adds one to questions answered right counter.
     */
    public void myQuestionsAnsweredRightAdd() {
        myQuestionsAnsweredRight++;
    }

    /**
     * Adds one to questions answered wrong counter.
     */
    public void myQuestionsAnsweredWrongAdd() {
        myQuestionsAnsweredWrong++;
    }

    /**
     * Adds one to questions answered total counter.
     */
    public void myQuestionsAnsweredTotalAdd() {
        myQuestionsAnsweredTotal++;
    }
}
