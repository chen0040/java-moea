package com.github.chen0040.moea.problems;


import com.github.chen0040.moea.components.Solution;

import java.util.Arrays;
import java.util.List;


/**
 * Created by xschen on 17/6/2017.
 */
public class NDND implements ProblemInstance {
   private static final long serialVersionUID = 8627945754278982189L;


   @Override public double getCost(Solution x, int objective_index) {

      double f1 = 1 - Math.exp((-4) * x.get(0)) * Math.pow(Math.sin(5 * Math.PI * x.get(0)), 4);
      if (objective_index == 0)
      {
         return f1;
      }
      else
      {
         double f2, g, h;
         if (x.get(1) > 0 && x.get(1) < 0.4)
            g = 4 - 3 * Math.exp(-2500 * (x.get(1) - 0.2) * (x.get(1) - 0.2));
         else
            g = 4 - 3 * Math.exp(-25 * (x.get(1) - 0.7) * (x.get(1) - 0.7));
         double a = 4;
         if (f1 < g)
            h = 1 - Math.pow(f1 / g, a);
         else
            h = 0;
         f2 = g * h;
         return f2;
      }
   }

   @Override public int getObjectiveCount() {
      return 2;
   }


   @Override public int getDimension() {
      return 2;
   }


   @Override public List<Double> getLowerBounds() {
      return Arrays.asList(0.0, 0.0);
   }


   @Override public List<Double> getUpperBounds() {
      return Arrays.asList(1.0, 1.0);
   }
}
