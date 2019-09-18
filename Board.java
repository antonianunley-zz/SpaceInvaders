import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;


public class Board  extends JPanel implements Runnable, MouseListener
{
	
boolean ingame = true;
private Dimension d;
int BOARD_WIDTH=500;
int BOARD_HEIGHT=500;
int x = 0;
BufferedImage img;
int points = 0;
int lives = 3;
int wait = 0;
 private Thread animator;
 
 Alien[] aliens = new Alien[30];
 boolean[] show = new boolean[30];

boolean moveRight = false;
boolean moveLeft = false;
boolean shootNow = false;
boolean aliensGoRight = true;
boolean aliensGoLeft = false;
boolean aliensGoDown = false;
boolean alienShootNow = true;
boolean rowshot = false;

String strpoints;
String message;
String belowShooterMessage;

int numRowShots = 2;

int animationdelay = 0;
long startTime1, elapsedTime1, startTime2, elapsedTime2, startRowShotTime, elapsedRowShot;

BufferedImage alien;
BufferedImage alienShot;
BufferedImage playerShooter;
BufferedImage alien2;
BufferedImage playerShot;

private Player player;
private Shot shot;
private AlienShot alienshot;
 
    public Board()
    {
          addKeyListener(new TAdapter());
         addMouseListener(this);
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);
       
       
                
                   
        try {
           alien = ImageIO.read(this.getClass().getResource("alien.png"));
           alienShot = ImageIO.read(this.getClass().getResource("ashot.png"));
           playerShooter = ImageIO.read(this.getClass().getResource("player.png"));
           alien2 =  ImageIO.read(this.getClass().getResource("graphics-space-invaders-characters.png"));
           playerShot = ImageIO.read(this.getClass().getResource("pshot.png"));
        } catch (IOException e) {
             System.out.println("Image could not be read");
        // System.exit(1);
        }
        if (animator == null || !ingame) {
            animator = new Thread(this);

            animator.start();
        }
            
         setDoubleBuffered(true);
             
         int alienx = 30;
         int alieny = 30;
         int count = 1;
         for (int i = 0; i < aliens.length; i++) {
         		aliens[i] = new Alien(alienx, alieny);
         		show[i] = true;
         		alienx += 35;
         		if (count%10==0) {
         			alienx = 30;
         			alieny+= 30;
         		}
         		count ++;
         } 
         
         player = new Player();
         shot = new Shot();
         alienshot = new AlienShot();
         //players[0] = new Player (BOARD_WIDTH/2);

               
  
        setDoubleBuffered(true);
    }
    
public void drawAliens(Graphics g) {
	for (int i = 0; i < aliens.length; i++) {
		if (show[i]) {
			g.drawImage(alien, aliens[i].getX(), aliens[i].getY(), 25, 25, this);
		}
	}
}

public void drawPlayer(Graphics g) {
		g.drawImage(playerShooter, player.getX(), player.getY(), 60, 30, this);
		if (player.getX() < 0) {
			player.setX(0);
		}
		
		if (player.getX() + 60 > BOARD_WIDTH) {
			player.setX(BOARD_WIDTH - 60);
		}
}

public void drawShot(Graphics g) {
	if (shot.isVisible())
		g.drawImage(playerShot, shot.getX(), shot.getY(), 5, 5, this);
}

public void drawAlienShot(Graphics g) {
	if (alienshot.isVisible())
		g.drawImage(alienShot, alienshot.getX(), alienshot.getY(), 10, 10, this);
}

public void moveAliens() {
	elapsedTime2 = System.currentTimeMillis() - startTime2;
	if(elapsedTime2 >= 300) {
		if (aliensGoRight) {
	   		for (int i = 0; i < aliens.length; i++) {
	   			aliens[i].setX(aliens[i].getX() + 20);
	   			if (aliens[29].getX() >= 450) {
	   				aliensGoDown = true;
	   				aliensGoRight = false;
	   			}
	   		}
	   	}
	   	
	   	if (aliensGoDown) {
	   		for (int i = 0; i < aliens.length; i++) {
	   			aliens[i].setY(aliens[i].getY() + 10);
	   			if (aliens[29].getX() >= 450) {
	   				aliensGoLeft = true;
	   				aliensGoDown = false;
	   			} 
	   			if (aliens[0].getX() <= 35) {
	   				aliensGoRight = true;
	   				aliensGoDown = false;
	   			}
	   		}
	   	}
	   	
	   	if (aliensGoLeft) {
	   		for (int i = aliens.length - 1; i >= 0; i--) {
	   			aliens[i].setX(aliens[i].getX() - 20);
	   			if (aliens[0].getX() <= 35) {
	   				aliensGoDown = true;
	   				aliensGoLeft = false;
	   			}
	   		}
	   	}
	startTime2 = System.currentTimeMillis();
	}
}

public void goAliens() {
	
	elapsedTime1 = System.currentTimeMillis() - startTime1;
	
	if (elapsedTime1 >= (int)(Math.random() * 500) + 500) {
		startTime1 = System.currentTimeMillis();
		alienShootNow = true;
		wait = 0;
		//elapsedTime1 = System.currentTimeMillis();
	}

	
}

public void allowRowShot() {
		numRowShots--;
}

public void checkWin() {
	boolean winner = true;
	int blanks = 0;
	for (int i = 0; i < aliens.length; i++) {
		if (!show[i]) {
			blanks++;
		} else {
			continue;
		}
	}
	
	if (blanks == 30) {
		ingame = false;
	}
}
public void paint(Graphics g)
{
super.paint(g);
Color navy = new Color(32,29,78);
g.setColor(navy);
g.fillRect(0, 0, d.width, d.height);
//g.fillOval(x,y,r,r);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        checkWin();
         strpoints = "Points: " + points;
         message = "Lives: " + lives;
        belowShooterMessage = "ROW SHOTS AVAILABLE: " + numRowShots;
        g.drawString(message, 10, d.height-60);
        g.drawString(strpoints, 400, d.height-60);
       g.drawString(belowShooterMessage, 140, d.height - 30);
     
    
    if (ingame) {
    		drawAliens(g);
    		drawPlayer(g); 
    		drawShot(g);
    		drawAlienShot(g);
    		moveAliens();
    		goAliens();
    		
    		if (moveRight) {
    			player.setX(player.getX() + 5);
    		}
    		
    		if (moveLeft) {
    			player.setX(player.getX() - 5);
    		}
    		
    		if (shootNow) {
    			shot.setVisible(true);
    			if (shot.isVisible()) {
    				int shotX = shot.getX();
    				int shotY = shot.getY();
    				
    				for (int i = 0; i < aliens.length; i++) {
    					int alienX = aliens[i].getX();
    					int alienY = aliens[i].getY();
    					if (show[i]) {
    						if (shotX >= (alienX) && shotX <= (alienX + 25) && shotY >= (alienY) && shotY <= (alienY + 25)) {
    							show[i] = false;
    							shot.setY(500);
    							shot.disappear();
    							aliens[i].disappear();
    							points += 100;
    							shootNow = false;
    						}
 
    					}
    				}
    			int shoty = shot.getY();
			shoty -= 8;
			if (shoty < 0) {
				shot.disappear();
				shootNow = false;
			}else {
				shot.setY(shot.getY() - 8);
			}
    			
    			}
    		}
    		
    		if (alienShootNow) {
    			Random gen = new Random();
    		   	for (int i = 0; i < aliens.length; i++) {
    		   		int alienshooting = gen.nextInt(30);
    		   		if (show[alienshooting]) {
    		   			if (wait == 0) {
    		   				alienshot.setX(aliens[alienshooting].getX() + 1);
    		   				alienshot.setY(aliens[alienshooting].getY() + 1);
    		   				wait = 1;
    		   			}
    		   			alienshot.setVisible(true);
    		   			alienshot.setY(alienshot.getY() + 1);
    		   			if (alienshot.getX() >= player.getX() && alienshot.getX() + 20 <= player.getX() + 60 && alienshot.getY() == player.getY()) {
    		   				lives = lives - 1;
    		   				if (lives == 0) {
    		   					ingame = false;
    		   				}
    		   			}
    		   		}
    		   	}
    		   	//alienShootNow = false;
    	
    		} 
    		
    	   if (rowshot) {
    		   shot.setVisible(true);
   			if (shot.isVisible()) {
   				int shotX = shot.getX();
   				int shotY = shot.getY();
   				
   				for (int i = 0; i < aliens.length; i++) {
   					int alienX = aliens[i].getX();
   					int alienY = aliens[i].getY();
   					if (show[i]) {
   						if (shotX >= (alienX) && shotX <= (alienX + 25) && shotY >= (alienY) && shotY <= (alienY + 25)) {
   							show[i] = false;
   							shot.disappear();
   							aliens[i].disappear();
   							points += 200;
   							break;
   						}
   					}
   				}
   			int shoty = shot.getY();
			shoty -= 8;
			if (shoty < 0) {
				shot.disappear();
				rowshot = false;
			}else {
				shot.setY(shot.getY() - 8);
			}
   			
   			}
    	   }
    	  
    	   
        
    // g.drawImage(img,0,0,200,200 ,null);
     
    
   
    }
    
Toolkit.getDefaultToolkit().sync();
g.dispose();
}
private class TAdapter extends KeyAdapter {

public void keyReleased(KeyEvent e) {
     int key = e.getKeyCode();
     if(key==39){
         moveRight = false;
    	 	
       } 
 	
 	 if(key==37) {
 		 moveLeft = false;
 	 }
 	 

}

public void keyPressed(KeyEvent e) {
//System.out.println( e.getKeyCode());
   // message = "Key Pressed: " + e.getKeyCode();
    int key = e.getKeyCode();
    
    int x = player.getX();
    int y = player.getY();
    
    if(key==39){
        moveRight = true;
   	 	
      } 
	
	 if(key==37) {
		 moveLeft = true;
	 }

	 if (ingame) {
		 if (key == 32) {
			shootNow = true;
		 }
		 
		 if (!shot.isVisible()) {
			 shot = new Shot(x, y);
		 }
	 }
	 
	 if (key == 65) {
		 if (numRowShots > 0) {
			 allowRowShot();
			 rowshot = true;
		 }
		
	 }
	 
}

}



public void mousePressed(MouseEvent e) {
    int x = e.getX();
     int y = e.getY();

}

public void mouseReleased(MouseEvent e) {

}

public void mouseEntered(MouseEvent e) {

}

public void mouseExited(MouseEvent e) {

}

public void mouseClicked(MouseEvent e) {

}

public void run() {

long beforeTime, timeDiff, sleep;

beforeTime = System.currentTimeMillis();
 animationdelay = 10;
 long time = 
            System.currentTimeMillis();
    while (true) {//infinite loop
     // spriteManager.update();
      repaint();
      try {
        time += animationdelay;
        Thread.sleep(Math.max(0,time - 
          System.currentTimeMillis()));
      }catch (InterruptedException e) {
        System.out.println(e);
      }//end catch
    }//end while loop

    


}//end of run

}//end of class

