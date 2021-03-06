package com.github.chen0040.moea.problems;


import com.github.chen0040.moea.components.Solution;

import java.io.Serializable;
import java.util.List;


/**
 * Created by xschen on 17/6/2017.
 */
public interface ProblemInstance extends Serializable {
   double getCost(Solution x, int objective_index);


   int getObjectiveCount();

   int getDimension();

   List<Double> getLowerBounds();

   List<Double> getUpperBounds();
}
