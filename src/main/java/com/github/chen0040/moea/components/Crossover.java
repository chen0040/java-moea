package com.github.chen0040.moea.components;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.moea.enums.CrossoverType;


/**
 * Created by xschen on 17/6/2017.
 */
public class Crossover {
   public static TupleTwo<Solution, Solution> apply(Mediator mediator, Solution solution1, Solution solution2) {
      Solution child1 = solution1.makeCopy();
      Solution child2 = solution2.makeCopy();

      CrossoverType crossoverType = mediator.getCrossoverType();
      if(crossoverType == CrossoverType.OnePoint) {
         child1.onePointCrossover(mediator, child2);
      } else if(crossoverType == CrossoverType.Uniform) {
         child2.uniformCrossover(mediator, child2);
      }

      return new TupleTwo<>(child1, child2);
   }
}
