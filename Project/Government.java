 	//Peter Le
	//Period 2
	//Computer Systems Research
	//Government class file
	
   import java.util.*;

    public class Government
   {
      private double taxrate;
      private double salesTax;
      private double responsiveness;
      private double publicWelfare;
      private double wealth;
      private double happiness;
      private double approval; //measure of gov't succes
      private double wealthAssess;
      private double oldapproval;
       public Government(double tr, double r, double pw, double w, double h, double ap, double wa, double oa, double st)
      {
         responsiveness = r;
         taxrate = tr;
         publicWelfare = pw;
         wealth = w;
         happiness = h;
         approval = ap;
         wealthAssess = wa;
         oldapproval = oa;
         salesTax = st;
      }
   	
       public Government(Government g)
      {
         responsiveness = g.responsiveness;
         taxrate = g.taxrate;
         publicWelfare = g.publicWelfare;
         wealth = g.wealth;
         happiness = g.happiness;
         approval = g.approval;
         wealthAssess = g.wealthAssess;
         oldapproval = g.oldapproval;
         salesTax = g.salesTax;
      
      }
       public double oldApproval()
      {
         return oldapproval;
      }
       public double judgeWealth()
      {
         return wealthAssess;
      }
       public void rateW(double x)
      {
         wealthAssess = x;
      }
       public double response()
      {
         return responsiveness;
      }
       public double taxRate()
      {
         return taxrate;
      }
       public double welfare()
      {
         return publicWelfare;
      }
       public double getWealth()
      {
         return wealth;
      }
       public double getHappiness()
      {
         return happiness;
      }
       public double approvalRating()
      {
         return approval;
      }
   
       public void changeTRate(double r)
      {
         taxrate = r;
      }
       public void changeSTRate(double s)
      {
         salesTax = s;
      }
       public void adjWelfare(double w)
      {
         publicWelfare = w;
      }
       public void wealth(double x)
      {
         wealth += x;
      }
       public void happy(double x)
      {
         happiness = x;
      }
       public void changeApproval(double x)
      {
         oldapproval = approval;
         approval = x;
      }
       public void adjResponse(double x)
      {
         responsiveness+=x;
      }
       public double getSalesTax()
      {
         return salesTax;
      }
       public void changeWealth(double x)
      {
         wealth = x;
      }
   }