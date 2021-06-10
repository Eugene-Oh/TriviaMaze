package src.View;

import src.Model.QuestionAnswer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
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
    private JLabel myQuestionText;

    /**
     * JLabel that contains the correct answer.
     */
    private JLabel myAnswerCorrect;

    /**
     * JLabel that contains the wrong answer.
     */
    private JLabel myAnswerWrong;

    /**
     * JLabel that contains the answer 1.
     */
    private JRadioButton myAnswer1;

    /**
     * JLabel that contains the answer 2.
     */
    private JRadioButton myAnswer2;

    /**
     * JLabel that contains the answer 3.
     */
    private JRadioButton myAnswer3;

    /**
     * JLabel that contains the answer 4.
     */
    private JRadioButton myAnswer4;

    /**
     * JButton for our button.
     */
    private JButton myButton;

    /**
     * ButtonGroup for each answer.
     */
    private ButtonGroup myButtonGroup;

    /**
     * Box for the answers.
     */
    private Box myBox;

    /**
     * Getter method for if the player answered the question right.
     */
    public Boolean getMyIsAnsweredCorrect() {
        return myIsAnsweredCorrect;
    }

    /**
     * Boolean value for whether the question is answered correctly or not.
     */
    private Boolean myIsAnsweredCorrect;

    /**
     * Constructor for QuestionPane.
     *
     * @param question question.
     */
    public QuestionPane(QuestionAnswer question) {
        setLayout(new GridBagLayout());
        myQuestionText = new JLabel("<html>" + question.getMyQuestion() + "</html>");
        myQuestionText.setPreferredSize(new Dimension(156, 120));
        myAnswerCorrect = new JLabel("Correct Answer!");
        myAnswerWrong = new JLabel(String.format("<html>Wrong Answer! <br> Correct answer was: %s <br></html>", question.getCorrectAnswer()));
        myAnswerWrong.setPreferredSize(new Dimension(156, 120));
        myAnswerCorrect.setVisible(false);
        myAnswerWrong.setVisible(false);
        myIsAnsweredCorrect = false;
        myAnswer1 = new JRadioButton(question.getAnswers()[0]);
        myAnswer1.setActionCommand(question.getAnswers()[0]);
        myAnswer2 = new JRadioButton(question.getAnswers()[1]);
        myAnswer2.setActionCommand(question.getAnswers()[1]);
        myAnswer3 = new JRadioButton(question.getAnswers()[2]);
        myAnswer3.setActionCommand(question.getAnswers()[2]);
        myAnswer4 = new JRadioButton(question.getAnswers()[3]);
        myAnswer4.setActionCommand(question.getAnswers()[3]);
        myButtonGroup = new ButtonGroup();
        myButtonGroup.add(myAnswer1);
        myButtonGroup.add(myAnswer2);
        myButtonGroup.add(myAnswer3);
        myButtonGroup.add(myAnswer4);

        myBox = Box.createVerticalBox();
        myBox.add(myQuestionText);
        myBox.add(myAnswer1);
        myBox.add(myAnswer2);
        myBox.add(myAnswer3);
        myBox.add(myAnswer4);
        myBox.add(myAnswerCorrect);
        myBox.add(myAnswerWrong);

        myButton = new JButton("Submit!");
        myButton.addActionListener(createActionListener(question));
        myBox.add(myButton);
        add(myBox);
    }

    /**
     * Updates the questions.
     *
     * @param question question.
     * @param wasWrong Boolean for whether the question was wrong or not.
     */
    public void updateQuestion(QuestionAnswer question, Boolean wasWrong) {
        myQuestionText = new JLabel("<html>" + question.getMyQuestion() + "</html>");
        myQuestionText.setPreferredSize(new Dimension(156, 120));
        JLabel oldAnswer = myAnswerWrong;
        myAnswerWrong = new JLabel(String.format("<html>Wrong Answer! <br> Correct answer was: %s </html>", question.getCorrectAnswer()));
        myAnswerWrong.setPreferredSize(new Dimension(156, 120));
        myAnswer1 = new JRadioButton(question.getAnswers()[0]);
        myAnswer1.setActionCommand(question.getAnswers()[0]);
        myAnswer2 = new JRadioButton(question.getAnswers()[1]);
        myAnswer2.setActionCommand(question.getAnswers()[1]);
        myAnswer3 = new JRadioButton(question.getAnswers()[2]);
        myAnswer3.setActionCommand(question.getAnswers()[2]);
        myAnswer4 = new JRadioButton(question.getAnswers()[3]);
        myAnswer4.setActionCommand(question.getAnswers()[3]);
        myAnswer1.setBackground(BACKGROUND_COLOR);
        myAnswer2.setBackground(BACKGROUND_COLOR);
        myAnswer3.setBackground(BACKGROUND_COLOR);
        myAnswer4.setBackground(BACKGROUND_COLOR);
        repaint();
        myButtonGroup = new ButtonGroup();
        myButtonGroup.add(myAnswer1);
        myButtonGroup.add(myAnswer2);
        myButtonGroup.add(myAnswer3);
        myButtonGroup.add(myAnswer4);
        myBox.removeAll();
        myBox.add(oldAnswer);
        myBox.add(new JLabel("\n"));
        myBox.add(myQuestionText);
        myBox.add(myAnswer1);
        myBox.add(myAnswer2);
        myBox.add(myAnswer3);
        myBox.add(myAnswer4);
        myBox.add(myAnswerCorrect);
        myBox.add(myAnswerWrong);
        oldAnswer.setVisible(wasWrong);
        myAnswerCorrect.setVisible(false);
        myAnswerWrong.setVisible(false);
        for (ActionListener al : myButton.getActionListeners()) {
            myButton.removeActionListener(al);
        }
        myButton.addActionListener(createActionListener(question));
        myButton.setEnabled(true);
        myBox.add(myButton);
        add(myBox);
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
        return e -> {
            if (question.isCorrectAnswer(myButtonGroup.getSelection().getActionCommand())) {
                myAnswerCorrect.setVisible(true);
                myIsAnsweredCorrect = true;
                question.setIsAnswered(true);
            } else {
                myAnswerWrong.setVisible(true);
                myIsAnsweredCorrect = false;
            }
            for (JRadioButton button : new JRadioButton[]{myAnswer1, myAnswer2, myAnswer3, myAnswer4}) {
                button.setEnabled(false);
            }
            myButton.setEnabled(false);
            fireChangeListeners();
        };
    }

    /**
     * Sets the size of the question panel.
     */
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

    /**
     * Default override blank method.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
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
