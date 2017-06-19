package com.github.chen0040.moea.components;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 19/6/2017.
 * differential evolution crossover
 */
public class DE {


   public static List<Solution> apply(Mediator mediator, Solution[] parents) {
      List<Solution> results = new ArrayList<>();

      int dimension_count = mediator.getDimension();
      double crossover_rate = mediator.getCrossoverRate();

      double mF = mediator.getInterpolation4DE();
      int ccount=parents.length;
      Solution child1, parent1, parent2, parent3;
      for (int i = 0; i < ccount; i+=4)
      {
         int child1_index = i;
         int parent1_index = (i + 1) % ccount;
         int parent2_index = (i + 2) % ccount;
         int parent3_index = (i + 3) % ccount;

         child1 = parents[child1_index].makeCopy();
         parent1 = parents[parent1_index];
         parent2 = parents[parent2_index];
         parent3 = parents[parent3_index];

         int jrand = mediator.nextInt(dimension_count);

         for (int j = 0; j < dimension_count; j++)
         {
            if ((mediator.nextDouble() <= crossover_rate) || (j == jrand))
            {
               double v1 = parent1.get(j);
               double v2 = parent2.get(j);
               double v3 = parent3.get(j);

               double y = mediator.applyBound(v3 + mF * (v1 - v2), j);



               child1.set(j, y);
            }
         }
         results.add(child1);
      }

      return results;
   }
}
