//Scott Wedge
//2020

//This program will print a self-innovative design consiting of shapes and colors

import java.awt.*;

public class Doodle {

   public static void main(String[] args) {
   
      DrawingPanel panel = new DrawingPanel(650, 400);
      panel.setBackground(Color.CYAN);
      Graphics g = panel.getGraphics();
      
      // prints left side corner lines      
      corner(113, 0, 0, 112, 50, g);
      
      // prints right side corner lines
      corner(480, 0, 650, 150, 40, g);
      
      // prints bottom squares
      square(81, 320, 80, 80, 15, g);
      square(100, 333, 50, 50, 10, g);
      square(113, 339, 30, 30, 5, g);
      
      //prints centered circles
      nestedCircle(g);
      
      
   }
   
   // creates many lines on the left and right side of canvas
   public static void corner(int x, int y, int x2, int y2, int repeat, Graphics g) {
      for(int line = 0; line < repeat; line++) {  
         g.setColor(Color.YELLOW); 
         g.drawLine(x + line * 6, y, x2, y2 + line);
         g.setColor(Color.GREEN);
         g.drawLine(x + line, y, x2, y2 + line * 10);
         
      }
   }
   
   public static void square(int x, int y, int width, int height, int repeat,  Graphics g) {
      for(int cube = 0; cube < repeat; cube++) {
         g.setColor(Color.BLACK);
         g.drawRect(x + cube, y - cube, width, height);
      }
   }
   
   // creates a large oval with many nested ovals inside of it
   public static void nestedCircle(Graphics g) {
      g.setColor(Color.BLACK);
      g.drawOval(115, 50, 450, 300);
      g.drawOval(120, 55, 440, 290);
      g.drawOval(125, 60, 430, 280);
      g.drawOval(130, 65, 420, 270);
      g.drawOval(135, 70, 410, 260);
      g.drawOval(140, 75, 400, 250);
      g.setColor(Color.YELLOW);
      g.drawOval(145, 80, 390, 240);
      g.drawOval(150, 85, 380, 230);
      g.drawOval(155, 90, 370, 220);
      g.drawOval(160, 95, 360, 210);
      g.drawOval(165, 100, 350, 200);
      g.setColor(Color.MAGENTA);
      g.drawOval(170, 105, 340, 190);
      g.drawOval(175, 110, 330, 180);
      g.drawOval(180, 115, 320, 170);
      g.drawOval(185, 120, 310, 160);
      g.drawOval(190, 125, 300, 150);
      g.drawOval(195, 130, 290, 140);
      g.drawOval(200, 135, 280, 130);
      g.drawOval(205, 140, 270, 120);
      g.setColor(Color.BLACK);
      g.drawOval(210, 145, 260, 110);
      g.drawOval(215, 150, 250, 100);
      g.drawOval(220, 155, 240, 90);
      g.drawOval(225, 160, 230, 80);
      g.drawOval(230, 165, 220, 70);
      g.drawOval(235, 170, 210, 60);
      g.drawOval(240, 175, 200, 50);
      g.drawOval(245, 180, 190, 40);
   
   }
   
}

