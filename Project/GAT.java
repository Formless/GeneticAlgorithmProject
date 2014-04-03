	//Peter Le
	//Period 2
	//Computer Systems Research
	//Genetic Algorithm Test
	
		
   import java.util.*;
   import java.io.*;
   
    public class GAT
   {	  
   	
       public static void main(String[] args) throws IOException
      { 
      
         Double[][] d = new Double[2][8];
         for(int x = 0;x<8;x++)
         {
            d[0][x] = 40.0*Math.random();
            d[0][x] = 20-d[0][x];
            d[0][x] = function(d[0][x]);
            d[1][x] = 1.0*x;
         }
      	
         Arrays.sort(d[0]);
         Double[] d2 = new Double[8]; 
         
         for(int bleh = 0;bleh<8;bleh++)
         {
            double k = d[1][bleh];
            for(int meh = 0;meh<7;meh++)
               if(function(k)==d[0][meh])
               {
                  d2[meh]=k;
                  break;
               }
         }
         
         for(int guh = 0;guh<8;guh++)
            d[1][guh] = d2[guh];
         for(int blargh = 0; blargh<10;blargh++)
         {
            generation(d);
            generation(d);
            generation(d);
         }
      }
   
       public static double function(double x)
      {
         double y = Math.abs(1.0*x) + Math.cos(1.0*x); 
         return y;
      }
      
       public static void generation(Double[][] d)
      {
         Arrays.sort(d[0]);
         Double[][] d2 = new Double[2][8];
         int count = 0;
         for(int x = 4;x>0;x--)
         {
            d2[0][count] = d[0][x];
            d2[1][count] = d[1][x];
            count++;
         }
         //shift d[bleh][x] over //nvm
      	//code..
         d = d2;
         makeBabies(d, 4);
         makeBabies(d, 6);
         System.out.println(d[0][0]);
      	
      }
   
       public static void makeBabies(Double[][] d, int index)
      {
         double rand1 = Math.random();
         double rand2 = Math.random();
         
         
         while(eval(rand1)!=eval(rand2))
            rand2 = Math.random();
         System.out.println(eval(rand1) + " " + eval(rand2));	
      
         double p1, p2, c1, c2;
         p1 = d[0][eval(rand1)];
         p2 = d[0][eval(rand2)];
      	
         c1 = blend(p1, p2);
         mutate(c1);
         d[0][index] = c1; 
      	
         c2 = blend(p1, p2);
         mutate(c2);
         d[0][index+1] = c2;
      	
      	
      }
   	
       public static int eval(double d)
      {
      
         if(d<=0.4)
            return 0;
         if(d<=0.7)
            return 1;
         if(d<=0.9)
            return 2;
         else
            return 3;
      }
      
       public static double blend(double x1, double x2)
      {
         Double[][] meh = new Double[2][3];
         
         int x3 = (int)(0.5*x1+0.5*x2);
         meh[0][0] = function(x3);
         meh[1][0] = 1.0*x3;
         int x4 = (int)(1.5*x1-0.5*x2);
         meh[0][1] = function(x4);
         meh[1][1] = 1.0*x4;
         int x5 = (int)(-0.5*x1+1.5*x2); 
         meh[0][2] = function(x5);
         meh[1][2] = 1.0*x5;
         System.out.println(x3 + " " + x4 + " " + x5);
         Arrays.sort(meh[0]);
         
         return meh[0][1];
      }
   
       public static void mutate(double x)
      {
         x = x - Math.random();
         x = x + Math.random();
      
      }
   		
   	
   }