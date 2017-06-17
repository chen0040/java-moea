package com.github.chen0040.moea.problems;


import com.github.chen0040.moea.components.Solution;

import java.util.Arrays;
import java.util.List;


/**
 * Created by xschen on 18/6/2017.
 */
public class NGPD implements ProblemInstance {

   private final static double M = 888888.0;

   @Override public double getCost(Solution x, int objective_index) {
      if(objective_index==0)
      {
         double h = getNGPDConstraints(x.get(0), x.get(1), x.get(2), x.get(3));
         double f = 1.10471 * x.get(0) * x.get(0) * x.get(2) + 0.04811 * x.get(3) * x.get(1) * (14.0 + x.get(2)) - 5 - M * h;
         return f;
      }
      else
      {
         double h = getNGPDConstraints(x.get(0), x.get(1), x.get(2), x.get(3));
         double f = 2.1952 / (x.get(3) * x.get(3) * x.get(3) * x.get(1)) - 0.0001 - M * h;
         return f;
      }
   }

   public double getNGPDConstraints(double h, double b, double l, double t)
   {
      double c1, c2, c3, c4, r0, r1, r2, temp;
      r1 = 6000 / (Math.sqrt(2.0) * h * l);
      r2 = (1500 * (14 + 0.5 * l) * Math.sqrt(l * l + (h + t) * (h + t))) / (0.707 * h * l * ((l * l) / 12.0 + 0.25 * (h + t) * (h + t)));
      r0 = Math.sqrt(r1 * r1 + r2 * r2 + (2 * l * r1 * r2) / (Math.sqrt(l * l + (h + t) * (h + t))));
      c1 = 13600 - r0;
      c2 = 30000 - 504000 / (t * t * b);
      c3 = b - h;
      c4 = 64746.022 * (1 - 0.0282346 * t) * t * b * b * b - 6000;
      if (c1 < c2)
      {
         if (c3 < c4)
            temp = (c3 < c1) ? c3 : c1;
         else
            temp = (c4 < c1) ? c4 : c1;
      }
      else
      {
         if (c3 < c4)
            temp = (c3 < c2) ? c3 : c2;
         else
            temp = (c4 < c2) ? c4 : c2;
      }
      return (temp < 0) ? temp : 0;
   }



   @Override public int getObjectiveCount() {
      return 2;
   }


   @Override public int getDimension() {
      return 4;
   }


   @Override public List<Double> getLowerBounds() {
      return Arrays.asList(0.125, 0.125, 0.1, 0.1);
   }


   @Override public List<Double> getUpperBounds() {
      return Arrays.asList(5.0, 5.0, 10.0, 10.0);
   }
}
