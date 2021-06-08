package src.View;

import src.Model.QuestionAnswer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A class that handles the QuestionPane.
 *
 * @author Eugene Oh, Jonathan Cho, Yavuzalp Turkoglu
 * @version 4.0
 */
public class QuestionPane extends JPanel implements PropertyChangeListener {

    /**
     * RGB Value for background color.
     */
    private static final Color BACKGROUND_COLOR = new Color(142, 120, 120);

    /**
     * JLabel that contains the question.
     */
    JLabel questiontext;
    /**
     * JLabel that contains the correct answer.
     */
    JLabel answerCorrect;
    /**
     * JLabel that contains the wrong answer.
     */
    JLabel answerWrong;
    /**
     * JLabel that contains the answer 1.
     */
    JRadioButton answer1;
    /**
     * JLabel that contains the answer 2.
     */
    JRadioButton answer2;
    /**
     * JLabel that contains the answer 3.
     */
    JRadioButton answer3;
    /**
     * JLabel that contains the answer 4.
     */
    JRadioButton answer4;
    /**
     * JButton for our button.
     */
    JButton button;
    /**
     * ButtonGroup.
     */
    ButtonGroup bg;
    /**
     * Box.
     */
    Box box;
    /**
     * Boolean value for whether the question is answered correctly or not.
     */
    Boolean isAnsweredCorrect = false;

    /**
     * Constructor for QuestionPane.
     *
     * @param question question.
     */
    public QuestionPane(QuestionAnswer question) {
        setLayout(new GridBagLayout());

        questiontext = new JLabel("<html>" + question.getMyQuestion() + "</html>");
        questiontext.setPreferredSize(new Dimension(156, 120));
        answerCorrect = new JLabel("Correct Answer!");
        answerWrong = new JLabel(String.format("<html>Wrong Answer! <br> Correct answer was: %s <br></html>", question.getCorrectAnswer()));
        answerWrong.setPreferredSize(new Dimension(156, 120));

        answerCorrect.setVisible(false);
        answerWrong.setVisible(false);

        answer1 = new JRadioButton(question.getAnswers()[0]);
        answer1.setActionCommand(question.getAnswers()[0]);
        answer2 = new JRadioButton(question.getAnswers()[1]);
        answer2.setActionCommand(question.getAnswers()[1]);
        answer3 = new JRadioButton(question.getAnswers()[2]);
        answer3.setActionCommand(question.getAnswers()[2]);
        answer4 = new JRadioButton(question.getAnswers()[3]);
        answer4.setActionCommand(question.getAnswers()[3]);
        bg = new ButtonGroup();
        bg.add(answer1);
        bg.add(answer2);
        bg.add(answer3);
        bg.add(answer4);

        box = Box.createVerticalBox();
        box.add(questiontext);
        box.add(answer1);
        box.add(answer2);
        box.add(answer3);
        box.add(answer4);
        box.add(answerCorrect);
        box.add(answerWrong);

        button = new JButton("Submit!");
        button.addActionListener(createActionListener(question));
        box.add(button);
        add(box);
    }

    /**
     * Updates the questions
     *
     * @param question question.
     * @param wasWrong Boolean for whether the question was wrong or not.
     */
    public void updateQuestion(QuestionAnswer question, Boolean wasWrong) {
        questiontext = new JLabel("<html>" + question.getMyQuestion() + "</html>");
        questiontext.setPreferredSize(new Dimension(156, 120));
        JLabel oldAnswer = answerWrong;
        answerWrong = new JLabel(String.format("<html>Wrong Answer! <br> Correct answer was: %s </html>", question.getCorrectAnswer()));
        answerWrong.setPreferredSize(new Dimension(156, 120));
        answer1 = new JRadioButton(question.getAnswers()[0]);
        answer1.setActionCommand(question.getAnswers()[0]);
        answer2 = new JRadioButton(question.getAnswers()[1]);
        answer2.setActionCommand(question.getAnswers()[1]);
        answer3 = new JRadioButton(question.getAnswers()[2]);
        answer3.setActionCommand(question.getAnswers()[2]);
        answer4 = new JRadioButton(question.getAnswers()[3]);
        answer4.setActionCommand(question.getAnswers()[3]);
        answer1.setBackground(BACKGROUND_COLOR);
        answer2.setBackground(BACKGROUND_COLOR);
        answer3.setBackground(BACKGROUND_COLOR);
        answer4.setBackground(BACKGROUND_COLOR);
        repaint();
        bg = new ButtonGroup();
        bg.add(answer1);
        bg.add(answer2);
        bg.add(answer3);
        bg.add(answer4);
        box.removeAll();
        box.add(oldAnswer);
        box.add(new JLabel("\n"));
        box.add(questiontext);
        box.add(answer1);
        box.add(answer2);
        box.add(answer3);
        box.add(answer4);
        box.add(answerCorrect);
        box.add(answerWrong);
        oldAnswer.setVisible(wasWrong);
        answerCorrect.setVisible(false);
        answerWrong.setVisible(false);
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
        button.addActionListener(createActionListener(question));
        button.setEnabled(true);
        box.add(button);
        add(box);
        revalidate();
        repaint();
    }

    /**
     * Creates a Action Listener.
     *
     * @param question question.
     * @return question.
     */
    public ActionListener createActionListener(QuestionAnswer question) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (question.isCorrectAnswer(bg.getSelection().getActionCommand())) {
                    answerCorrect.setVisible(true);
                    isAnsweredCorrect = true;
                    question.setIsAnswered(true);
                } else {
                    answerWrong.setVisible(true);
                    isAnsweredCorrect = false;
                }
                for (JRadioButton button : new JRadioButton[]{answer1, answer2, answer3, answer4}) {
                    button.setEnabled(false);
                }
                button.setEnabled(false);
                fireChangeListeners();
            }
        };
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(240, 250);
    }

    /**
     * Paint Component.
     *
     * @param g Graphics.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.dispose();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//            System.out.println("event: " + evt);
    }

    /**
     * Adds a change listener.
     *
     * @param listener the listener you want to change to.
     */
    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    /**
     * Removes a change listener.
     *
     * @param listener the listener you want to change to.
     */
    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    /**
     * Getter for ChangeListeners.
     *
     * @return ChangeListener.
     */
    public ChangeListener[] getChangeListeners() {
        return listenerList.getListeners(ChangeListener.class);
    }

    /**
     * Fires the change listeners.
     */
    protected void fireChangeListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : getChangeListeners()) {
            listener.stateChanged(event);
        }
    }
}
