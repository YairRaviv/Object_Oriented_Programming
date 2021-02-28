package gameClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Frame extends JFrame
{

    public Panel panel;

    /**
     * constructor to the enter frame of the game
     * @param height
     * @param width
     * @throws IOException
     */
    Frame(int height,int width) throws IOException
    {
        this.setSize(width,height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.panel= new Panel(this.getWidth(), this.getHeight());
        this.add(panel);
        this.setVisible(false);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
    }


    public static class Panel extends JPanel implements MouseListener
    {
        public int num;
        public int id;
        public TextField t,t2;
        public Button b;
        public boolean flag=false;
        public static int pai=0;
        public BufferedImage image;


        /**
         * constructor to the panel of the frame,
         * this method sets the text fields for the level and the id, the "enter" button
         * and set the background(pikachu image)
         * @param width
         * @param height
         * @throws IOException
         */
        public Panel(int width,int height) throws IOException {
            this.image= ImageIO.read(new File("doc/background.png"));
            this.num=-1000;
            this.id=0;

            this.setSize(width,height);
            this.setVisible(false);

            this.t=new TextField();
            t.setText("please enter level num");


            this.t2=new TextField();
            t2.setText("please enter id");


            this.b=new Button("enter");
            b.addMouseListener(this);

            this.add(t2);
            this.add(b);
            this.add(this.t);

        }

        /**
         * this method set the location of the graphics object and paint them
         * @param g
         */
        @Override
        public void paint(Graphics g)
        {
            super.paint(g);
            g.drawImage(image,0,0,getWidth(),getHeight(),null);
            t.setLocation((int)(getWidth()/2.5),(int)(getHeight()/1.9));
            t2.setLocation((int)(getWidth()/2.5),t.getY()+20);
            b.setLocation((int)(getWidth()/2.5),(int)(getHeight()/1.72));

            g.setFont(new Font("welcome",1,40));
            g.setColor(Color.BLACK);
            g.drawString("welcome to pokemons game!",(int)(getWidth()/4),(int)(getHeight()/5.2));

        }


        @Override
        public void mouseClicked(MouseEvent e)
        {

        }

        /**
         * this two next methods response to listen to the enter button and react to mouse opperations
         * according the current texts of the text fields
         * @param e
         */
        @Override
        public void mousePressed(MouseEvent e)
        {

            String s1=t.getText();
            int numb=Integer.parseInt(s1);
            String s2=t2.getText();
            this.num=numb;
            this.id=Integer.parseInt(s2);
            this.flag=true;

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            String s1=t.getText();
            int numb=Integer.parseInt(s1);
            String s2=t2.getText();
            this.num=numb;
            this.id=Integer.parseInt(s2);
            this.flag=true;

        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }

        public boolean getFlag()
        {
            return this.flag;
        }

    }
}
