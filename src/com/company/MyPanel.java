package com.company;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
    int ballx=160;
    int bally=218;
    int batx=160;
    int baty=245;

    Rectangle Ball = new Rectangle(ballx, bally , 10 ,10);
    Rectangle Bat = new Rectangle(batx, baty , 100 ,30);
    Rectangle[]Brick=new Rectangle[60];

    int movex=-1;
    int movey=-1;
    MyPanel(){
        init();
        addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent e)
            {
                int keycode=e.getKeyCode();
                switch(keycode)
                {
                    case KeyEvent.VK_RIGHT: Bat.x+=5; break;
                    case KeyEvent.VK_LEFT:  Bat.x-=5; break;
                }
                repaint();
            }

            @Override
            public void keyTyped(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }
        });
        this.requestFocus();
        setFocusable(true);
    }
    public void init()
    {
        this.ballx=160;
        this.bally=218;
        this.movex=-1;
        this.movey=-1;
        int brickx=10;
        int bricky=10;
        int brickBreadth=30;
        int brickHeight=20;
        // 벽돌 쌓기
        for(int i=0; i<Brick.length;i++)
        {
            if (i<20)
            {
                Brick[i]=new Rectangle(brickx, bricky, brickBreadth, brickHeight);
                brickx+=(brickBreadth+1);
            }
            if(i>=20 && i<40)
            {
                if(i==20)
                    brickx=10;
                Brick[i]=new Rectangle(brickx, bricky+21, brickBreadth, brickHeight);
                brickx+=(brickBreadth+1);
            }

            if(i>=40 && i<60)
            {
                if(i==40)
                    brickx=10;
                Brick[i]=new Rectangle(brickx, bricky+42, brickBreadth, brickHeight);
                brickx+=(brickBreadth+1);
            }

        }
    }
    public static void main(String[] args){
        JFrame frame=new JFrame();
        MyPanel game=new MyPanel();
        frame.setSize(1000,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setResizable(false);
        frame.setVisible(true);
        game.setFocusable(true);
        game.run();
    }
    public void paint(Graphics g)
    {
        g.setColor(Color.white);
        g.fillRect(0, 0, 650, 280);
        g.setColor(Color.blue);
        g.fillRect(Ball.x, Ball.y , Ball.width , Ball.height);
        g.setColor(Color.black);
        g.fill3DRect(Bat.x, Bat.y, Bat.width , Bat.height, true);
        g.setColor(Color.red);
        for(int i=0;i<Brick.length;i++){
            if(Brick[i]!=null){
                g.fill3DRect(Brick[i].x, Brick[i].y, Brick[i].width , Brick[i].height, true);
            }
        }

    }
    public void gameover()
    {
        int result=0;
        result=JOptionPane.showConfirmDialog(null, "게임을 다시 하시겠습니까?","re?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(result==0)
        {
            init();
        }
        else
            System.exit(0);
    }
    boolean Final =false;
    public void run(){
        while(true){
            Final =true;
            boolean done=false;
            for(int i=0;i<Brick.length;i++){
                if(Brick[i]!=null){
                    Final = false;
                    if(Brick[i].intersects(Ball)){
                        Brick[i]=null;
                        movey=-movey;
                        done=true;
                    }
                }
            }
            repaint();
            Ball.x+=movex;
            Ball.y+=movey;

            if(Ball.intersects(Bat))
                movey=-movey;
            if(Ball.x<=0 || Ball.x+Ball.height>=643)
                movex=-movex;
            if(Ball.y<=0)
                movey=-movey;
            if(Ball.y>getHeight()-10)
                gameover();
            if(Final == true)
                gameover();
            try{
                Thread.sleep(10);
            }
            catch(Exception ex){}

        }
    }
}

