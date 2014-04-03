 	//Peter Le
	//Period 2
	//Computer Systems Research
	//Third Quarter Simulation Program (Cycle and output)
 
   import java.util.*;
   import java.io.*;
   
    public class Test
   {
      static int MONTHS = 144;	  
      static Citizen[] population;
   
       public static void main(String[] args) throws IOException
      { 
      	//read in data for different kinds of classes
         BufferedReader citizenReader = new BufferedReader(new FileReader("citizen.txt"));
         //BufferedReader resourceReader = new BufferedReader(new FileReader("resourcepool.txt"));
         BufferedReader govtReader = new BufferedReader(new FileReader("govt.txt"));
         PrintStream p = new PrintStream(new FileOutputStream("results.txt")); //include resourcepool and govt # in output
         PrintStream goutput = new PrintStream(new FileOutputStream("govtracker.txt")); //track wealth over time
         PrintStream coutput = new PrintStream(new FileOutputStream("cittracker.txt")); //ditto
      	
      	//Create population
         int n = Integer.parseInt(citizenReader.readLine());
         Citizen[] people = new Citizen[n];
         for(int i = 0; i<people.length;i++)
         {
            int x = 0;
            double[] d = new double[5];
            StringTokenizer st = new StringTokenizer(citizenReader.readLine());
            while (st.hasMoreTokens()) {
               String s = st.nextToken();
               d[x] = Double.parseDouble(s);
               x++;
            }
         
            people[i] = new Citizen(d[0], d[1], d[2], d[3], d[4]);
         }
         citizenReader.close();
      
      
      	//for now just read one Government, later use more for multiple tests
         n = Integer.parseInt(govtReader.readLine());
         Government[] govts = new Government[n];
         for(int y = 0; y<govts.length;y++)
         {
            int x = 0;
            double[] d = new double[10];
            StringTokenizer st = new StringTokenizer(govtReader.readLine());
            while (st.hasMoreTokens()) {
               String s = st.nextToken();
               d[x] = Double.parseDouble(s);
               x++;
            }
         
            govts[y] = new Government(d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7], d[8]);
         }
         govtReader.close();
      
         population = people;
         iterate(MONTHS, govts,people, p, goutput, coutput);
      		
      }
      
      
       public static void iterate(int time, Government[] govts, Citizen[] people2, PrintStream p, PrintStream goutput, PrintStream coutput) 
      {
         String[] output = new String[(govts.length)];
         String[][] finalAssess = new String[govts.length][time+1];
         
         for(int asdf = 0;asdf<govts.length;asdf++) //cycle through governments
         {
            int ccount = 0;
            int gcount = 0;
            int gv = asdf;
            double rating;
            double[] cwealth = new double[time+1];
            double[] gwealth = new double[time+1];
            
            for(int meh = 0;meh<people2.length;meh++) //initial wealth count
            {
               cwealth[ccount] += people2[meh].getWealth();
            }
         
            gwealth[gcount] = govts[gv].getWealth(); 
         
            for(int i = 0;i<time;i++) //start the cycle, no multiple govts/resources now
            {
               boolean wellfair = false;
               Government g1 = govts[gv];
               rating = 0;
               if(g1.oldApproval()<g1.approvalRating()) //very simple right now, maybe do govt assessment of "need"
               {          
                  if(Math.random()<g1.response())
                     wellfair = true;
               }      
               double oldemoney = g1.getWealth();
            
               ccount++;
            
            /*********************************************************************************/
               for(int cv = 0;cv<people2.length;cv++)//civ cycle
               {
                  Citizen p1 = people2[cv];
                  double oldwealth = p1.getWealth();
               
               //receive benefits
                  if(wellfair=true && g1.getWealth()>0.0 && p1.getApproval()<g1.approvalRating()) //MUCH too generous
                  {
                     p1.wealth(g1.welfare());
                     g1.wealth(-g1.welfare());
                  }
               //work + taxes
               //p1.wealth(p1.getWorkRate()*p1.getWealth()); //rate based
                  p1.wealth(p1.getWorkRate());
                  p1.wealth(-g1.taxRate()*p1.getWealth());
                  g1.wealth(g1.taxRate()*p1.getWealth());
               
               //spending
                  p1.wealth(-p1.getSpending()*p1.getWealth());
                  p1.wealth(-p1.getSpending()*g1.getSalesTax());
                  g1.wealth(p1.getSpending()*g1.getSalesTax());
               /*
               //consumption
                  p1.wealth(-p1.getSpending());
               */
               
                  if(p1.getHappiness()>100.0)
                     p1.happy(100.0);
               
                  double assessW;
                  assessW = assessWealth(oldwealth, p1.getWealth()); //wealth ratio
                  p1.rateW(assessW); 	
                  rating += p1.getApproval(); //messed up
               
                  cwealth[ccount] += p1.getWealth(); //p1.getApproval();
                    
               } //pay and interact then update govt standing and output + resources
            
               rating /= people2.length; //average of approvals = approval rating
               g1.changeApproval(rating);
            
            //Start government action
               double w;
               w = assessWealth(oldemoney,g1.getWealth());
               g1.rateW(w);
               g1.happy((w+g1.approvalRating())/2);
            //"[resource] [govt] [rating]"
               finalAssess[gv][i] = (i + " " + gv + " " + g1.getHappiness());//update approval each time
            
               gcount++;
               gwealth[gcount] = g1.getWealth(); //track gwealth
            	
            }
            printOutGovtCycle(goutput, gv, gwealth);
            printOutCitizenCycle(coutput, gv, cwealth);
         }
         printOut(time, finalAssess, p);
         
      }
      
       public static void printOut(int time, String[][] s, PrintStream printer)
      {
         printer.println(s.length + " " + time);
         for(int x = 0;x<s.length;x++)
            for(int y = 0;y<time;y++)
               printer.println(s[x][y]);
      }
      
       public static void printOutGovtCycle(PrintStream printer, int gnum,double[] gwealth) //print progress of wealth
      {
         for(int x = 0;x<gwealth.length;x++)
         {
            printer.println(x + " " + gnum + " " + gwealth[x]);
         }
      }
   	
       public static void printOutCitizenCycle(PrintStream printer, int gnum, double[] cwealth) //print progress of wealth stats
      {
         for(int x = 0;x<cwealth.length;x++)
         {
            printer.println(x + " " + gnum + " " + cwealth[x]);
         }
      }
   	   
       public static double assessWealth(double beforeW, double afterW) //need relative wealth measure
      {
         double measureW = 0.0;
      
         measureW = afterW/beforeW; //percentage
         if(afterW<0.0 && beforeW<0.0)
            measureW = beforeW/afterW;
         /*if(WA>1)
            measureW = measureW*(WA); 
         else
            measureW = -measureW*(WA);
         */   
         return measureW;
      }
   	
   	
   }