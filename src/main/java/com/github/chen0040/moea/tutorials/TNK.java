package com.github.chen0040.moea.tutorials;


import com.github.chen0040.moea.components.Solution;

import java.util.Arrays;
import java.util.List;


/**
 * Created by xschen on 17/6/2017.
 */
public class TNK implements Tutorial {

   public static final double M = 888888.0;

   @Override
   public double getCost(Solution x, int objective_index)
   {
      double h=0 , f=0;
      switch(objective_index)
      {
         case 0:
         {
            h = getConstraint(x.get(0), x.get(1));
            f = x.get(0) + M * h;
            break;
         }
         case 1:
         {
            h = getConstraint(x.get(0), x.get(1));
            f = x.get(1) + M * h;
            break;
         }
      }
      return f;
   }

   //  use the design parameters to compute the constraint equation to get the value
   private double getConstraint(double x1, double x2)
   {
      double c1,c2,h;
      c1 = -x1*x1-x2*x2+1+0.1*Math.cos(16*Math.atan(x1/x2));
      c2 = (x1-0.5)*(x1-0.5)+(x2-0.5)*(x2-0.5)-0.5;
      if(c1>c2)
         h = (c1>0)?c1:0;
      else
         h = (c2>0)?c2:0;
      return h;
   }

   @Override
   public int getObjectiveCount()
   {
      return 2;
   }

   @Override
   public int getDimension()
   {
      return 2;
   }

   @Override
   public List<Double> getLowerBounds()
   {
      return Arrays.asList(0.0, 0.0);
   }

   @Override
   public List<Double> getUpperBounds()
   {
      return Arrays.asList(Math.PI, Math.PI);
   }
}
