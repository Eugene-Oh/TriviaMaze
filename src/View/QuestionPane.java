package src.View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import  src.Model.QuestionAnswer;

    public class QuestionPane extends JPanel {
//        public Boolean isAnswered = false;
//        public Boolean isCorrectAnswer = false;
        public QuestionPane(QuestionAnswer question) {
            setLayout(new GridBagLayout());

            JLabel questiontext = new JLabel(question.getMyQuestion());
            JLabel answerCorrect = new JLabel("Correct Answer!");
            JLabel answerWrong = new JLabel(String.format("<html>Wrong Answer! <br> Correct answer was: %s </html>", question.getCorrectAnswer()));

            answerCorrect.setVisible(false);
            answerWrong.setVisible(false);

            JRadioButton item1 = new JRadioButton(question.getAnswers()[0]);
            item1.setActionCommand(question.getAnswers()[0]);
            JRadioButton item2 = new JRadioButton(question.getAnswers()[1]);
            item2.setActionCommand(question.getAnswers()[1]);
            JRadioButton item3 = new JRadioButton(question.getAnswers()[2]);
            item3.setActionCommand(question.getAnswers()[2]);
            JRadioButton item4 = new JRadioButton(question.getAnswers()[3]);
            item4.setActionCommand(question.getAnswers()[3]);
            ButtonGroup bg=new ButtonGroup();
            bg.add(item1);bg.add(item2);bg.add(item3);bg.add(item4);

            Box box1 = Box.createVerticalBox();
            box1.add(questiontext);
            box1.add(item1);
            box1.add(item2);
            box1.add(item3);
            box1.add(item4);
            box1.add(answerCorrect);
            box1.add(answerWrong);


            final JButton button = new JButton("Submit!");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(bg.getSelection().getActionCommand() == question.getCorrectAnswer()){
                        System.out.println("Selected:" + e.getActionCommand());
                        System.out.println("Selected:" + bg.getSelection().getActionCommand());
                        answerCorrect.setVisible(true);
//                        box1.setVisible(false);
                        question.setIsAnswered(true);
                    }else{
                        answerWrong.setVisible(true);
                    }
                    for(JRadioButton button: new JRadioButton[]{item1,item2,item3,item4}){
                        button.setEnabled(false);
                    }
                    button.setEnabled(false);
                }
            });
            box1.add(button);
            add(box1);


        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(250, 250);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.dispose();
        }
    }
