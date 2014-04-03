	//Peter Le
	//Period 2
	//Computer Systems Research
	//Data output handler
	
   import java.util.*;
   import java.io.*;
   
    public class OutputSorter
   {	  
   	
       public static void main(String[] args) throws IOException
      { 
         BufferedReader citizenReader = new BufferedReader(new FileReader("cittrackerGA.txt"));
         BufferedReader govtReader = new BufferedReader(new FileReader("govtrackerGA.txt"));
         BufferedReader resultReader = new BufferedReader(new FileReader("resultsGA.txt"));
      	
      	//read results, separate and print files
      	//first line is [# govts] [time length] 
         StringTokenizer st = new StringTokenizer(resultReader.readLine());
         String s = st.nextToken();
         int numGov = Integer.parseInt(s);
         s = st.nextToken();
         int time = Integer.parseInt(s);
      
         for(int x = 0;x<numGov;x++)
         {
            String s1 = "govTest" + x + ".txt";
            PrintStream rw = new PrintStream(new FileOutputStream(s1)); //write results to file
            for(int y = 0;y<time;y++)
            {
               st = new StringTokenizer(resultReader.readLine());
               for(int x0 = 0;x0<2;x0++)
               {
                  String token = st.nextToken();
               } //time, gov
               
               String blah = st.nextToken();
               double d = Double.parseDouble(blah);
               rw.println(y + " " + d);
            }
         }
         
      	//read cittracker, separate and print files
         for(int x1 = 0;x1<numGov;x1++)
         {
            String s1 = "CT" + x1 + ".txt";
            PrintStream cw = new PrintStream(new FileOutputStream(s1)); //write results to file
            for(int y1 = 0;y1<time+1;y1++)
            {
               st = new StringTokenizer(citizenReader.readLine());
               
               for(int x2 = 0;x2<2;x2++)
               {
                  String token = st.nextToken();
               } //time+1, gov
               String blah = st.nextToken();
               double d = Double.parseDouble(blah);
               cw.println(y1 + " " + d);
            }
         }
      	
      	//read govtracker, separate and print files
         for(int x3 = 0;x3<numGov;x3++)
         {
            String s1 = "GV" + x3 + ".txt";
            PrintStream gw = new PrintStream(new FileOutputStream(s1)); //write results to file
            for(int y2 = 0;y2<time+1;y2++)
            {
               st = new StringTokenizer(govtReader.readLine());
               for(int x4 = 0;x4<2;x4++)
               {
                  String token = st.nextToken();
               } //time+1, gov
               String blah = st.nextToken();
               double d = Double.parseDouble(blah);
               gw.println(y2 + " " + d);
            }
         }
      	
      	//gnuplot> plot 'filename.txt'
      	
      	
      	
      	
      	
      }
   		
   }