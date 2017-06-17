package com.github.chen0040.moea.utils;


import com.github.chen0040.moea.components.Solution;

import java.io.Serializable;
import java.util.List;


/**
 * Created by xschen on 17/6/2017.
 */
public interface CostFunction extends Serializable {
   double evaluate(Solution s, int objective_index, List<Double> lowerBounds, List<Double> upperBounds);
}
