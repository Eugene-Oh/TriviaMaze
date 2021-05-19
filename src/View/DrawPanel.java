package src.View;

import src.Logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DrawPanel extends JPanel implements ActionListener, KeyListener {

    Timer t = new Timer(10, this);
    Player p = new Player(10,10,10,10,0,0);

    public DrawPanel(){
        addKeyListener(this);
        setFocusable(true);
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        p.tick();
        repaint();
    }

    public void paint(Graphics g){
        g.clearRect(0,0,getWidth(),getHeight());
        p.draw(g);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_D:
                p.setDx(1);
                break;
            case KeyEvent.VK_S:
                p.setDy(1);
                break;
            case KeyEvent.VK_A:
                p.setDx(-1);
                break;
            case KeyEvent.VK_W:
                p.setDy(-1);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
