//Scott Wedge
//2020

//This program creates an illusion concocted of many cube shaped objects of varying colors.

import java.awt.*;

public class CafeWall {

public static final int MORTAR = 2;

   public static void main(String[] args) {
   
      DrawingPanel panel = new DrawingPanel(650, 400);
      panel.setBackground(Color.GRAY);
      Graphics g = panel.getGraphics();
      
      // draws the upper most left row of cubes
      fabricateRow(4, 0, 0, 20, g); 
      
      // draws the middle left upper row
      fabricateRow(5, 50, 70, 30, g);   
      
      // draws the lower left grid pattern
      synthesizeGrid(4, 10, 150, 25, 0, g);
      
      // draws the lower middle grid pattern
      synthesizeGrid(3, 250, 200, 25, 10, g);
      
      // draws the lower right grid pattern
      synthesizeGrid(5, 425, 180, 20, 10, g);
      
      // draws the upper right grid pattern
      synthesizeGrid(2, 400, 20, 35, 35, g);     
   }
   
   //accepts number value parameters to produce a single row of cubes
   public static void fabricateRow(int pair, int x, int y, int size, Graphics g) {
      for(int cube = 0; cube < pair; cube++) { 
         //makes black cubes in row     
         g.setColor(Color.BLACK);
         g.fillRect(x + 2 * cube * size * 1, y, size, size);
         //makes white cubes in row 
         g.setColor(Color.WHITE);
         g.fillRect(x + 2 * cube * size + size, y, size, size);
         //fills black cubes with a blue X 
         g.setColor(Color.BLUE);
         g.drawLine(x + 2 * cube * size, y, x + 2 * cube * size + size, y + size); 
         g.drawLine(x + 2 * cube * size + size, y, x + 2 * cube * size, y + size);

      }
   }
   
   //accepts number values to manipulate a single row of cubes into a grid pattern
   public static void synthesizeGrid(int pair, int x, int y, int size, int off, Graphics g) {
      for(int row = 0; row < pair * 2; row++) {
         fabricateRow(pair, x + off * ((row * 5) % 2), y + row * (MORTAR + size), size, g);
      }
   }
   
}
