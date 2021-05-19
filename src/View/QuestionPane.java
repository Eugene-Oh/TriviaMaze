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
//import  TriviaMaze.src.Model.QuestionAnswer;

    public class QuestionPane extends JPanel {

        public QuestionPane(src.Model.QuestionAnswer question) {
            setLayout(new GridBagLayout());

            final JPopupMenu popup = new JPopupMenu();
            DefaultListModel<String> model = new DefaultListModel<>();
//            JLabel questiontext = new JLabel("   TEXT FOR QUESTION NUMBER 1 ");
//            add(questiontext);

            System.out.println(question.getAnswers());
            for( String answer: question.getAnswers()){
                model.addElement(answer);
            }
            JList list = new JList(model);
            popup.setLayout(new BorderLayout());
            popup.add(new JScrollPane(list));

            final JButton button = new JButton("You need to answer the question!");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Dimension size = popup.getPreferredSize();
                    int x = (button.getWidth() - size.width) / 2;
                    int y = button.getHeight();
                    popup.show(button, x, y);
                }
            });

            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (question.isRightAnswer(list.getSelectedIndex())){
                        System.out.println("right answer!");
                        popup.setVisible(false);
                    }
                }
            });

            add(button);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.dispose();
        }
    }
