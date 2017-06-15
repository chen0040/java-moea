package com.github.chen0040.moea.components;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 15/6/2017.
 */
@Getter
@Setter
public class Mediator implements Serializable {

   private int objectiveCount;
   private int dimension;
   private List<Double> lowerBounds = new ArrayList<>();
   private List<Double> upperBounds = new ArrayList<>();
   private RandomGenerator randomGenerator = new RandomGeneratorImpl();
   private double mutationRate = 0.1;

   public double randomWithinBounds(int index){
      double lowerBound = lowerBounds.get(index);
      double upperBound = upperBounds.get(index);
      return lowerBound + randomGenerator.nextDouble() * (upperBound - lowerBound);
   }


   public double evaluate(Solution solution, int objective_index) {
      return 0;
   }


   public double nextDouble() {
      return randomGenerator.nextDouble();
   }


   public int nextInt(int upper) {
      return randomGenerator.nextInt(upper);
   }
}
