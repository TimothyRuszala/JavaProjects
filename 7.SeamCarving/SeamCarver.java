/******************************************************************************
  *  Name:     Tim Ruszala
  *  NetID:    truszala
  *  Precept:  P04A
  *
  *  Partner Name:       N/A
  *  Partner NetID:      N/A
  *  Partner Precept:    N/A
  *
  *  Description: Content-aware image processing tool which can shorten the
  *  width or length of an image without distorting the most interesting
  *  aspects of the image (to a certain extent).
  *
  *****************************************************************************/

import java.awt.Color;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class SeamCarver {
    
    // defensive copy of input Picture
    private Picture pic;
    // width
    private int w;
    // height
    private int h;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        if (picture == null) throw new IllegalArgumentException();
        pic = new Picture(picture);  
        w = picture.width();
        h = picture.height();
    }
    
    // returns an energy array
    private double[] initializeEnergy()
    {
        double[] energy = new double[h * w];
        // initializes energy
        for (int i = 0; i < h; i++)
        {
            for (int j = 0; j < w; j++)
            {
                energy[(i * w) + j] = initializeEnergyArray(j, i);
            }
        }  
        return energy;
    }
    
    // helper method to calculate energy
    private double initializeEnergyArray(int col, int row)
    {
        long x = squareGradient(row, (col + w - 1) % w, row, (col + 1) % w);
        long y = squareGradient((row + h - 1) % h, col, (row + 1) % h, col);
        
        return Math.sqrt(x + y);
    }
    
    // helper method to calculate energy
    private int squareGradient(int rowTL, int colTL, int rowBR, int colBR)
    {
        Color colorTL = pic.get(colTL, rowTL);
        Color colorBR = pic.get(colBR, rowBR);
        
        int delR = colorTL.getRed() - colorBR.getRed();
        int delB = colorTL.getBlue() - colorBR.getBlue();
        int delG = colorTL.getGreen() - colorBR.getGreen();
        
        return delR*delR + delB*delB + delG*delG;
    }
    
    // current picture
    public Picture picture()     
    {
        Picture output = new Picture(pic);
        return output;
    }
    // width of current picture
    public     int width()    
    {
        return w;
    }
    
    // height of current picture
    public     int height()    
    {
        return h;
    }
    
    // energy of pixel at column x and row y
    public  double energy(int col, int row)               
    {
        if (col < 0 || col >= w) throw new IllegalArgumentException();
        if (row < 0 || row >= h) throw new IllegalArgumentException();
        return initializeEnergyArray(col, row);
    }
    
    // sequence of indices for horizontal seam
    public   int[] findHorizontalSeam()      
    {
        Picture picCopy = pic;
        Picture rotated = new Picture(h, w);
        for (int row = 0; row < h; row++)
        {
            for (int col = 0; col < w; col++)
            {
                int rgb = pic.getRGB(col, row);
                rotated.setRGB(row, col, rgb);
            }
        }
        pic = rotated; 
        
        int temp = w;
        w = h;
        h = temp;
        
        int[] seam = findVerticalSeam();
               
        pic = picCopy;
        h = w;
        w = temp;
             
        return seam;
    }
    
    
    // sequence of indices for vertical seam. 
    public   int[] findVerticalSeam()           
    {
        
        double[] energy = initializeEnergy();
        // holds distance from top row
        double[] distTo = new double[h * w];
        // holds column of vertex in the row above
        int[] vertTo = new int[h * w];
        for (int i = 0; i < distTo.length; i++)
        {
            distTo[i] = Double.POSITIVE_INFINITY;
            vertTo[i] = i;
        }       
        
        IndexMinPQ<Double> pq = new IndexMinPQ<Double>(w * h);    
        // insert top row into pq
        for (int x = 0; x < w; x++)
        {
            pq.insert(x, energy[x]);
            distTo[x] = energy[x];
        }
        int current = pq.minIndex();
        int currentRow = 0;
        int currentCol = indexToCol(current); 
        
        while (!pq.isEmpty())
        {
            current = pq.delMin();
            currentRow = indexToRow(current);
            if (currentRow == h - 1) break;
            currentCol = indexToCol(current);
            relax(current, pq, currentCol, distTo, vertTo, energy);
        }
        
        Stack<Integer> stack = new Stack<Integer>();
        while (vertTo[current] != current)
        { 
            stack.push(indexToCol(current));
            current = vertTo[current];
         }
        stack.push(indexToCol(current));
        
        int[] indices = new int[stack.size()];
        for (int i = 0; i < indices.length; i++)
            indices[i] = stack.pop();

        return indices;
        
    }
    
    // helper method for removeVerticalSeam
    private void relax(int pixIndex, IndexMinPQ<Double> pq, int col,
                       double[] distTo, int[] vertTo, double[] energy)
    {       
        int mid = pixIndex + w;
        if (distTo[pixIndex] + energy[mid] <
            distTo[mid])
        {
            distTo[mid] = 
                distTo[pixIndex] + energy[mid];
            vertTo[mid] = pixIndex;
            if (!pq.contains(mid))
                pq.insert(mid, distTo[mid]);
            else pq.decreaseKey(mid, distTo[mid]);
        }
        
        if (col > 0)
        {
            int left = pixIndex + w - 1;
            if (distTo[pixIndex] + energy[left] <
                distTo[left])
            {
                distTo[left] = 
                    distTo[pixIndex] + energy[left];
                vertTo[left] = pixIndex;
                if (!pq.contains(left))
                    pq.insert(left, distTo[left]);
                else pq.decreaseKey(left, distTo[left]);
            }
        }
        
        if (col < w - 1)
        {
            int right = pixIndex + w + 1;
            if (distTo[pixIndex] + energy[right] <
                distTo[right])
            {
                distTo[right] = 
                    distTo[pixIndex] + energy[right];
                vertTo[right] = pixIndex;
                if (!pq.contains(right))
                    pq.insert(right, distTo[right]);
                else pq.decreaseKey(right, distTo[right]);
            }
        }
    }
    
    // takes a pixel index and returns the corresponding row
    private int indexToRow(int index)
    {
        int row = index / w;
        return row;
    }
    
    // takes a pixel index and returns the corresponding col
    private int indexToCol(int index)
    {
        int col = index % w;
        return col;
    }
    
    
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)   
    {
        if (seam == null) throw new IllegalArgumentException();
        
        if (seam.length != w) throw new IllegalArgumentException();
        for (int i = 0; i < seam.length - 1; i++)
        {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) 
                throw new IllegalArgumentException();
            if (seam[i + 1] < 0 || seam[i + 1] > h) 
                throw new IllegalArgumentException();
        }
        if (seam[0] < 0 || seam[0] > h) 
                throw new IllegalArgumentException();
        if (h == 1) throw new IllegalArgumentException();
        
        Picture newPic = new Picture(w, h - 1);
        for (int i = 0; i < w; i++)
        {
            int skip = seam[i];
            for (int j = 0; j < h; j++)
            {
                int rgb = pic.getRGB(i, j);
                if (j < skip)
                {
                    newPic.setRGB(i, j, rgb);
                }                
                
                else if (j > skip)
                {
                    newPic.setRGB(i, j - 1, rgb);                  
                }
            }
        }
        pic = newPic;
        h = h - 1;
        
    }
    
    // remove vertical seam from current picture
    public    void removeVerticalSeam(int[] seam)     
    {
        if (seam == null) throw new IllegalArgumentException();
        if (seam.length != h) throw new IllegalArgumentException();
        for (int i = 0; i < seam.length - 1; i++)
        {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) 
                throw new IllegalArgumentException();
            if (seam[i + 1] < 0 || seam[i + 1] > w) 
                throw new IllegalArgumentException();
        }
        if (seam[0] < 0 || seam[0] > w) 
                throw new IllegalArgumentException();
        if (w == 1) throw new IllegalArgumentException();
        
        w = w - 1;
        Picture newPic = new Picture(w, h);
        for (int row = 0; row < h; row++)
        {         
            int skip = seam[row];
            for (int col = 0; col < w + 1; col++) 
            {
                int rgb = pic.getRGB(col, row);
                if (col < skip)
                {
                    newPic.setRGB(col, row, rgb);
                }
                
                else if (col > skip)
                {
                    newPic.setRGB(col - 1, row, rgb);                  
                }
            }
        }
        pic = newPic; 
    }
    
    //  unit testing (required)
    public static void main(String[] args)  
    {       
        Picture picture = new Picture(args[0]);
        SeamCarver sc = new SeamCarver(picture);
        StdOut.println("width = " + sc.width());
        StdOut.println("height = " + sc.height());
        sc.picture().show();
        
        
        StdOut.println("VERTICAL");
        for (int row = 0; row < sc.height(); row++)
        {
            for (int col = 0; col < sc.width(); col++)
            {
                StdOut.printf("%9.0f ", sc.energy(col, row));
            }
            StdOut.println();
        }
        
        int[] horSeam = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(horSeam);
        StdOut.println("height = " + sc.height());
        horSeam = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(horSeam);
        StdOut.println("height = " + sc.height());
        int[] vertSeam = sc.findVerticalSeam();
        StdOut.println("height = " + vertSeam.length);
        sc.removeVerticalSeam(vertSeam);    
    }
}
