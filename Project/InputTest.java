   //Peter Le
	//Period 2
	//Computer Systems Research
	//Data input handler
	
   import java.util.*;
   import java.io.*;
   
    public class InputTest
   {	  
      static int citz = 1000;
      static int poolz = 1;
      static int govtz = 20;
   	
       public static void main(String[] args) throws IOException
      {          
         PrintStream cw = new PrintStream(new FileOutputStream("citizen.txt"));
         PrintStream gw = new PrintStream(new FileOutputStream("govt.txt"));
         cw.println(citz); //# citizens
      	
      	//happy, workrate, wealth, spending, wealth assessment
         double h, wr, w, sp, wa;
         h = 75.0; /*wr = 0.05;*/ wr = 25; w = 500.0; sp = 0.10; wa = 50.0;
         for(int x = 0;x<citz;x++)
         {
            double bleh = Math.random()+0.75;
            h = 75.0; /*wr = 0.05;*/ wr = 25; w = 500.0; sp = 0.10; wa = 50.0;
            h*= bleh;
            wr*= bleh;
            w*= bleh;
            sp*= bleh;
            wa*= bleh;
            cw.println(h+" "+wr+" "+w+" "+sp + " " + wa); //Citizen traits, for now arbitrary, variation?
         }
      	
         gw.println(govtz); //# govts
      	//taxrate, responsiveness, welfare, wealth, happy, approval,wealth assessment,oldApproval, salesTax
         double tr, r, pw, w2, h2, ap, wa2, oa, st;
         tr = .050; r = 0.9; pw = 0.0; w2 = 300000.0; h2 = 70.0; ap = 50.0; wa2 = 50.0; oa = ap; st = 0.045;
         for(int z = 0;z<govtz;z++)
         {
            double bleh = 5.0*Math.random()+0.75;
            tr = .050; r = 0.9; pw = 5.0; w2 = 300000.0; h2 = 70.0; ap = 50.0; wa2 = 50.0; oa = ap; st = 0.045;
            tr*= bleh;
            r*= bleh;
            pw*= bleh;
            /*w2*= bleh;
            h2*= bleh;
            ap*= bleh;
            wa2*= bleh;
            oa*= bleh;*/
            st*= bleh;
            gw.println(tr+" "+r+" "+pw+" "+w2+" "+h2+" "+ap+" "+wa2 + " " + oa + " " + st);
         }
      	
      	
      }
   	
   	
   }