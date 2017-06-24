package com.github.chen0040.moea.problems;


import com.github.chen0040.moea.components.Solution;

import java.util.Arrays;
import java.util.List;


/**
 * Created by xschen on 17/6/2017.
 */
public class OKA2 implements ProblemInstance {
   private static final long serialVersionUID = 6665004120372165596L;


   @Override public double getCost(Solution x, int objective_index) {
      double f=0;
      switch(objective_index)
      {
         case 0:
         {
            f = x.get(0);
            break;
         }

         default:
         {
            f = 1 - Math.pow((x.get(0) + Math.PI), 2) / (4 * Math.pow(Math.PI, 2)) + Math.pow(Math.abs(x.get(1) - 5 * Math.cos(x.get(0))), 1.0 / 3.0) + Math.pow(Math.abs(x.get(2) - 5 * Math.sin(x.get(0))), 1.0 / 3.0);
            break;
         }
      }
      return f;
   }


   @Override public int getObjectiveCount() {
      return 2;
   }


   @Override public int getDimension() {
      return 3;
   }


   @Override public List<Double> getLowerBounds() {
      return Arrays.asList(-Math.PI, -5.0, -5.0);
   }


   @Override public List<Double> getUpperBounds() {
      return Arrays.asList(Math.PI, 5.0, 5.0);
   }
}
