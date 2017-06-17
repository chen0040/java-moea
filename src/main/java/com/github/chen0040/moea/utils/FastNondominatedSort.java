package com.github.chen0040.moea.utils;


import com.github.chen0040.moea.components.NondominatedPopulation;
import com.github.chen0040.moea.components.Population;
import com.github.chen0040.moea.components.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Created by xschen on 17/6/2017.
 */
public class FastNondominatedSort {
   public static void rank(Population population)
   {
      List<Solution> remaining = new ArrayList<>();

      for (Solution solution : population)
      {
         remaining.add(solution);
      }

      int rank = 0;

      while (remaining.size() > 0)
      {
         NondominatedPopulation front = new NondominatedPopulation();
         front.setMediator(population.getMediator());

         for (Solution solution : remaining)
         {
            front.add(solution);
         }

         for (Solution solution : front)
         {
            remaining.remove(solution);
            solution.setRank(rank);
         }

         updateCrowdingDistance(front);

         rank++;
      }

   }

   public static void updateCrowdingDistance(Population front)
   {
      int n = front.size();

      if (n < 3)
      {
         for (Solution solution : front)
         {
            solution.setCrowdingDistance(Double.POSITIVE_INFINITY);
         }
      }
      else
      {
         int numberOfObjectives = front.get(0).getCosts().size();

         for (Solution solution : front)
         {
            solution.setCrowdingDistance(0.0);
         }

         for (int i = 0; i < numberOfObjectives; i++)
         {
            final int k = i;
            // Xianshun:
            // Sort the pareto front to have solution with minimum i-th objective at front[0]
            // and solution with maximum i-th objective at front[n-1]
            front.sort(Comparator.comparingDouble(s -> s.getCost(k)));

            double minObjective = front.get(0).getCost(i);
            double maxObjective = front.get(n - 1).getCost(i);

            front.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
            front.get(n - 1).setCrowdingDistance(Double.POSITIVE_INFINITY);

            for (int j = 1; j < n - 1; j++)
            {
               double distance = front.get(j).getCrowdingDistance();
               distance += (front.get(j + 1).getCost(i) - front.get(j - 1).getCost(i)) / (maxObjective - minObjective);
               front.get(j).setCrowdingDistance(distance);
            }
         }
      }
   }
}
