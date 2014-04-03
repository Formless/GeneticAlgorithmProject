	//Peter Le
	//Period 2
	//Computer Systems Research
	//Genetic Algorithm Optimization
	
		
   import java.util.*;
   import java.io.*;
   
    public class GAO
   {	  
   	
      static int MONTHS = 144;	 
      static int generations = 10; 
      static Citizen[] population;
   
       public static void main(String[] args) throws IOException
      { 
      	//read in data for different kinds of classes
         BufferedReader citizenReader = new BufferedReader(new FileReader("citizen.txt"));
         //BufferedReader resourceReader = new BufferedReader(new FileReader("resourcepool.txt"));
         BufferedReader govtReader = new BufferedReader(new FileReader("govt.txt"));
         PrintStream p = new PrintStream(new FileOutputStream("resultsGA.txt")); //include resourcepool and govt # in output
         PrintStream goutput = new PrintStream(new FileOutputStream("govtrackerGA.txt")); //track wealth over time
         PrintStream coutput = new PrintStream(new FileOutputStream("cittrackerGA.txt")); //ditto
      	
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
      
      	//Set of Governments
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
         
         Government[] sortedGs;
         double[][] results = new double[govts.length][generations];
      	
         sortedGs = iterate(MONTHS, govts,people, results, goutput, coutput, 0); //run genetic algorithm, run test again
         for(int r = 0;r<govts.length;r++)
            for(int c = 0;c<generations;c++)
            {
               p.println(r + " " + c + " " + results[r][c]);
               //System.out.println("ASDF");
            }
      	
      }
      
      
       public static Government[] iterate(int time, Government[] govts, Citizen[] people2, double[][] results, PrintStream goutput, PrintStream coutput, int gens) 
      {
      
         if(gens>=generations)
            return null;
         else
         {
            
            Government[] originalGovts = new Government[govts.length];
            for(int blah = 0;blah<govts.length;blah++)
            {
               originalGovts[blah]=new Government(govts[blah]);
            }
         	
            String[] output = new String[(govts.length)];
            Double[] finalAssess = new Double[govts.length];
         
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
                     if(wellfair=true /*&& g1.getWealth()>0.0*/ && p1.getApproval()<g1.approvalRating()) //MUCH too generous
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
                  
                     if(p1.getHappiness()>100.0)
                        p1.happy(100.0);
                  
                     double assessW;
                     assessW = assessWealth(oldwealth, p1.getWealth()); //wealth ratio
                     p1.rateW(assessW); 	
                     rating += p1.getApproval(); //iffy
                  
                     cwealth[ccount] += p1.getWealth(); //p1.getApproval();
                    
                  } //pay and interact then update govt standing and output + resources
               
                  rating /= people2.length; //average of approvals = approval rating
                  g1.changeApproval(rating);
               
               //Start government action
                  double w;
                  w = assessWealth(oldemoney,g1.getWealth());
                  
                  g1.rateW(w);
                  if(oldemoney<1000.0)
                     System.out.println(oldemoney + "  " + g1.getWealth() + "  " + w);
               	
                  g1.happy((w+g1.approvalRating())/2);
               //"[govt] [rating]"
                  finalAssess[gv] = (g1.getHappiness());//update approval each time
                  
                  gcount++;
                  gwealth[gcount] = g1.getWealth(); //track gwealth
               
               }
               printOutGovtCycle(goutput, gv, gwealth);
               printOutCitizenCycle(coutput, gv, cwealth);
               ;
               results[gv][gens] = finalAssess[gv]; //store final results
            
            
            }
         	/********************************************************         	
         	Sort governments, print and record order
         	Get rid of bottom half
         	Reference data and order to get government #, grab policy
         	Genetic algorithm sequence
         	********************************************************/
         	//govts[gv], finalAssess[gv], etc
         	//new array --> copy and sort, ref finalAssess, ref govts[gv] and make policy array
         	
            Double[] sortedAssess = new Double[govts.length];
            for(int gah = 0;gah<sortedAssess.length;gah++)	
               sortedAssess[gah] = finalAssess[gah];
            //Arrays.sort(sortedAssess);
         	
            Arrays.sort(sortedAssess, Collections.reverseOrder());
         
         		
            Government[] sortedPolicy = new Government[govts.length]; 
            int sortCount=0;
            for(int gah=0;gah<sortedAssess.length;gah++)
            {
               for(int blah = 0;blah<finalAssess.length;blah++)
                  if(sortedAssess[gah]==finalAssess[blah])
                  {
                     sortedPolicy[sortCount] = originalGovts[blah]; //must reset wealth and stuff
                     sortCount++;
                  }
            }
         
            Government[] newGovts;
            generation(sortedPolicy);
         
            newGovts = iterate(MONTHS, sortedPolicy,people2, results, goutput, coutput, gens+1);	
            return newGovts;
            
         }
      }
      
       public static void printOut(Double[] s, PrintStream printer)
      {
         printer.println(s.length + " " + MONTHS);
         for(int x = 0;x<s.length;x++)
            printer.println(s[x]);
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
      
      	//System.out.println(beforeW + " " + afterW);
      
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
      
   	
   	
   	
       public static double mutate(double x, double offset)
      {
         x = x - Math.random()*offset;
         x = x + Math.random()*offset;
         return x;
      }
   	
       public static void generation(Government[] g)
      {
       
         for(int x = g.length/2;x<g.length;x++) //get rid of bottom half
            g[x] = null;
      	
         for(int y = g.length/2;y<g.length;y+=2)
            makeBabies(g, y);
         //System.out.println(d[0][0]);
      	
      }
   
       public static void makeBabies(Government[] d, int index)
      {
         double rand1 = Math.random()*3.0;
         double rand2 = Math.random()*3.0;
         //System.out.println((rand1) + " " + (rand2));
         
         while(eval(rand1)!=eval(rand2))
            rand2 = Math.random()*3.0;
         //System.out.println(eval(rand1) + " " + eval(rand2));	
      
         Government p1, p2, p3, p4;
         p1 = d[eval(rand1)];
         p2 = d[eval(rand2)];
      	
      	/*************************
      	blend and mutate variables
         *************************/
         //first child
      	
      	//taxrate, responsiveness, welfare, wealth, happy, approval,wealth assessment,oldApproval, salesTax
        	//make wealth, happy, approval, assessment, oldapproval the same each time
         p3 = new Government(0.0,0.0,0.0,300000.,70.0,50.0,50.0,50.0,0.0);
         p3.adjResponse(mutate(blend(p1.response(), p2.response()), 1.0));
         p3.changeTRate(mutate(blend(p1.taxRate(),p2.taxRate()), 2.0));
         p3.adjWelfare(mutate(blend(p1.welfare(),p2.welfare()),1.5));
         //p3.changeWealth(mutate(blend(p1.getWealth(),p2.getWealth()), 1.5));
         //p3.happy(mutate(blend(p1.getHappiness(), p2.getHappiness()), 1.5));
         //p3.changeApproval(mutate(blend(p1.approvalRating(),p2.approvalRating()), 1.0));
         p3.changeSTRate(mutate(blend(p1.getSalesTax(), p2.getSalesTax()), 1.5));
         d[index] = p3; 
      	
      	//second child 
         p4 =  new Government(0.0,0.0,0.0,300000.,70.0,50.0,50.0,50.0,0.0);
         p4.adjResponse(mutate(blend(p1.response(), p2.response()), 1.0));
         p4.changeTRate(mutate(blend(p1.taxRate(),p2.taxRate()), 2.0));
         p4.adjWelfare(mutate(blend(p1.welfare(),p2.welfare()),1.5));
         //p4.changeWealth(mutate(blend(p1.getWealth(),p2.getWealth()), 1.5));
         //p4.happy(mutate(blend(p1.getHappiness(), p2.getHappiness()), 1.5));
         //p4.changeApproval(mutate(blend(p1.approvalRating(),p2.approvalRating()), 1.0));
         p4.changeSTRate(mutate(blend(p1.getSalesTax(), p2.getSalesTax()), 1.5));
         d[index+1] = p4;
      	
      }
   	
   	
       public static double blend(double x1, double x2)
      {
         
         int x3 = (int)(0.5*x1+0.5*x2);
         int x4 = (int)(1.5*x1-0.5*x2);
         int x5 = (int)(-0.5*x1+1.5*x2); 
      
         //System.out.println(x3 + " " + x4 + " " + x5);
         int blendArray[] = new int[3]; 
         blendArray[0] = x3;
         blendArray[1] = x4;
         blendArray[2] = x5;
         int randy = (int)(Math.random()*2);
      	
         return blendArray[randy];
      }
      
       public static int eval(double d)
      {
      
         if(d<=1.2)
            return 0;
         if(d<=1.8)
            return 1;
         if(d<=2.3)
            return 2;
         if(d<=2.6)
            return 3;
         if(d<=2.7)
            return 4;
         if(d<=2.8)
            return 5;
         if(d<=2.9)
            return 6;
         if(d<=2.96)
            return 7;
         if(d<=2.98)
            return 8;
         else
            return 9;
      }
   
   }