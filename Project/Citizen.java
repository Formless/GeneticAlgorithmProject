 	//Peter Le
	//Period 2
	//Computer Systems Research
	//Citizen class file
	  
   import java.util.*;

    public class Citizen
   {
      private double happiness;
      private double workrate;
      private double health;
      private double wealth;
      private double wealthAssess;
      private double approval;
      private double spending;
   	
       public Citizen(double h, double wr,double w, double sp, double wa)
      {
         happiness = h;
         workrate = wr;
         wealth = w;
         wealthAssess = wa;
         spending = sp;
      }
   	
       public double judgeWealth()
      {
         return wealthAssess;
      }
       public double getSpending()
      {
         return spending;
      }
   	
       public double getHappiness()
      {
         return happiness;
      }
       public double getWorkRate()
      {
         return workrate;
      }
       public double getApproval() //too simple?
      {
      	/*
         approval = happiness+wealthAssess+health;
         approval /= 2;
      	*/
         approval = wealthAssess;
         return approval;
      }
   
       public double getWealth()
      {
         return wealth;
      }
       public void happy(double x)
      {
         happiness = x;
      }
       public void workrate(double x)
      {
         workrate += x;
      }
       public void wealth(double x)
      {
         wealth += x;
      }
       public void approve(double x)
      {
         approval += x;
      }
       public void rateW(double x)
      {
         wealthAssess = x;
      }
   	
   	
   }