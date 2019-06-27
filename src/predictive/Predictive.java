
package predictive;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;
import javax.imageio.ImageIO;
import java.util.Random;

public class Predictive {

    public static int[][] readImage(String path){
		
		
		BufferedImage img;
		try {
			img = ImageIO.read(new File(path));
		
		int hieght=img.getHeight();
		int width=img.getWidth();
		
		int[][] imagePixels=new int[hieght][width];
		for(int x=0;x<width;x++){
			for(int y=0;y<hieght;y++){
				
				int pixel=img.getRGB(x, y);
				
				int red=(pixel  & 0x00ff0000) >> 16;
				int grean=(pixel  & 0x0000ff00) >> 8;
				int blue=pixel  & 0x000000ff;
				int alpha=(pixel & 0xff000000) >> 24;
				imagePixels[y][x]=red;
			}
		}
		
		return imagePixels;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}

    public static void writeImage(int[][] imagePixels,String outPath){
		
		BufferedImage image = new BufferedImage(imagePixels.length, imagePixels[0].length, BufferedImage.TYPE_INT_RGB);
	    for (int y= 0; y < imagePixels.length; y++) {
	        for (int x = 0; x < imagePixels[y].length; x++) {
	             int value =-1 << 24;
	             value= 0xff000000 | (imagePixels[y][x]<<16) | (imagePixels[y][x]<<8) | (imagePixels[y][x]);
	             image.setRGB(x, y, value); 

	        }
	    }

	    File ImageFile = new File(outPath);
	    try {
	        ImageIO.write(image, "jpg", ImageFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

		
	}

    public static void main(String[] args) {
     //int []data=new int [18];
    // int []data = new int[]{15,16,24,33,44,68};
       int[][] pixels=Predictive.readImage("C:\\Users\\Ascd\\Downloads\\Compressed\\FromTA.TawfekAhmedMohamedHZVO8C2kpq\\ReadWriteImageClass\\cameraMan.jpg");
                 
        int []data=new int [pixels.length*pixels.length];
        int cc=0;
        for(int i=0;i<pixels.length;i++)
        {
            for(int j=0;j<pixels.length;j++)
            {
                data[cc]=pixels[i][j];
                cc++;
            }
        }
         Scanner scan=new Scanner (System.in);        
        System.out.print("Enter the number of Levels : " );        
        int num_levels=scan.nextInt();
        System.out.println();
         System.out.print("Enter the number of steps : " );        
        int num_steps=scan.nextInt();
        System.out.println();
      Vector<Integer> div=new Vector<Integer>();
      div.add(data[0]);
      int x=0;
      for(int i=1;i<data.length;i++)
      {
          x=data[i]-data[i-1];
          if(x>0&&x<255)
          {
            div.add(x);  
          }
          
          x=0;
      }
      int []code=new int[100];
      int []low=new int[100];
      int []high=new int[100];
      int []q=new int [100];
      for(int t=0;t<num_levels;t++)
      {
          code[t]=t;
      }
     Object object1 = Collections.max(div);
           int pp= div.indexOf(object1);
           int v=div.get(pp);
           Object object2 = Collections.min(div);
           int y= div.indexOf(object2);
           int z=div.get(y);
           int bb=0;
      for(int i=z;i<v+1;i+=num_steps)
      {
          low[bb]=i;
          if(bb<num_levels)
          {
          bb++;
          }
          else
          {
              break;
          }
      }
      bb=0;
         for(int i=z+num_steps;i<v+8;i+=8)
      {
          high[bb]=i;
          bb++;
      }
         
         for(int i=0;i<num_levels;i++)
         {
             q[i]=((low[i]+high[i])/2)+1;
         }
        
          Vector<Integer> qunt=new Vector<Integer>();
          Vector<Integer> dequnt=new Vector<Integer>();
          Vector<Integer> decode=new Vector<Integer>();
          qunt.add(data[0]);
          for(int i=1;i<div.size();i++)
          {
              for(int j=0;j<low.length;j++)
              {
                  if(div.get(i)>=low[j]&&div.get(i)<=high[j])
                  {
                      qunt.add(code[j]);
                  }
              }
          }
          dequnt.add(data[0]);
           for(int j=1;j<qunt.size();j++)
         {
             dequnt.add(q[qunt.get(j)]);
         }
           decode.add(data[0]);
          for(int j=1;j<dequnt.size();j++)
         {
             decode.add(decode.get(j-1)+dequnt.get(j));
         }
 ///////////////////////////////////////////////////////////////    print     
           for(int j=0;j<dequnt.size();j++)
         {
            
          System.out.println(decode.get(j));
         }
            for(int j=0;j<low.length;j++)
         {
             System.out.println(code[j]+" : "+low[j]+"==>"+high[j]+" : "+q[j]);
         }
     ////////////////////////////////////////////////////////////////////     
   
      int output[][]=new int [125][125];
        Random rand = new Random();                                                                                                                                
     int []input=new int [decode.size()];
     for(int t=0;t<decode.size();t++)
     {
         input[t]=decode.get(t);
     }
   /*    for(int i=0;i<input.length;i++)
     {
         if(input[i]<0)
         {
             input[i]=input[i]*-1;
         }
         if(input[i]>256)
         {
             input[i]=rand.nextInt(256)+0;
         }
     }
        for(int j=0;j<input.length;j++)
         {
            
          System.out.println(input[j]);
         }*/
         int cc1=0;
        for (int i=0;i<125;i++){
            for (int j=0;j<125;j++){
                output[i][j]=input[cc1];
                cc1++;
            }
        }
          	Predictive.writeImage(output, "C:\\Users\\Ascd\\Downloads\\Compressed\\FromTA.TawfekAhmedMohamedHZVO8C2kpq\\cameraMan_out.jpg");

    }
    
}
