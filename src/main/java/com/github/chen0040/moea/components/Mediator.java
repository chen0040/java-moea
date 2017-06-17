package com.github.chen0040.moea.components;


import com.github.chen0040.moea.enums.CrossoverType;
import com.github.chen0040.moea.enums.MutationType;
import com.github.chen0040.moea.enums.ReplacementType;
import com.github.chen0040.moea.problems.ProblemInstance;
import com.github.chen0040.moea.utils.CostFunction;
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
   private int maxGenerations = 1000;
   private List<Double> lowerBounds = new ArrayList<>();
   private List<Double> upperBounds = new ArrayList<>();
   private RandomGenerator randomGenerator = new RandomGeneratorImpl();
   private double mutationRate = 0.1;
   private CostFunction costFunction;
   private int populationSize = 1000;
   private MutationType mutationType = MutationType.Uniform;
   private CrossoverType crossoverType = CrossoverType.OnePoint;
   private ReplacementType replacementType = ReplacementType.Generational;
   private int maxArchive = 50;

   public void read(ProblemInstance problemInstance) {
      objectiveCount = problemInstance.getObjectiveCount();
      dimension = problemInstance.getDimension();

      lowerBounds.clear();
      lowerBounds.addAll(problemInstance.getLowerBounds());

      upperBounds.clear();
      upperBounds.addAll(problemInstance.getUpperBounds());

      costFunction = (CostFunction) (s, objective_index, lowerBounds, upperBounds) -> problemInstance.getCost(s, objective_index);
   }


   public double randomWithinBounds(int index){
      double lowerBound = lowerBounds.get(index);
      double upperBound = upperBounds.get(index);
      return lowerBound + randomGenerator.nextDouble() * (upperBound - lowerBound);
   }


   public double evaluate(Solution solution, int objective_index) {
      return costFunction.evaluate(solution, objective_index, lowerBounds, upperBounds);
   }


   public double nextDouble() {
      return randomGenerator.nextDouble();
   }


   public int nextInt(int upper) {
      return randomGenerator.nextInt(upper);
   }
}
