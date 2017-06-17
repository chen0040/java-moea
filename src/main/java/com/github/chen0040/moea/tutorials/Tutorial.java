package com.github.chen0040.moea.tutorials;


import com.github.chen0040.moea.components.Solution;

import java.util.List;


/**
 * Created by xschen on 17/6/2017.
 */
public interface Tutorial {
   double getCost(Solution x, int objective_index);

   //  use the design parameters to compute the constraint equation to Get the value
   double getConstraint(double x1, double x2);

   int getObjectiveCount();

   int getDimension();

   List<Double> getLowerBounds();

   List<Double> getUpperBounds();
}
