package com.github.chen0040.moea.components;


import com.github.chen0040.moea.utils.CompareUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 16/6/2017.
 */
@Getter
@Setter
public class NondominatedPopulation extends Population {
   public static final double Epsilon = 1e-10;

   public static int compare(Solution s1, Solution s2) {
      int flag = CompareUtils.ConstraintCompare(s1, s2);

      if (flag == 0)
      {
         flag = CompareUtils.ParetoObjectiveCompare(s1, s2);
      }
      return flag;
   }

   @Override
   public boolean add(Solution solution_to_add)
   {
      List<Solution> solutions_to_remove = new ArrayList<>();

      // solutions must be sorted at this point
      boolean should_add = true;
      for (Solution solution : solutions)
      {
         int flag = compare(solution_to_add, solution);

         if (flag < 0)
         {
            solutions_to_remove.add(solution);
         }
         else if (flag > 0)
         {
            should_add = false;
            break;
         }
         else if (getDistance(solution_to_add, solution) < Epsilon)
         {
            should_add = false;
            break;
         }
      }

      solutions.removeAll(solutions_to_remove);

      if (should_add)
      {
         return super.add(solution_to_add);
      }
      return should_add;
   }

   protected double getDistance(Solution s1, Solution s2)
   {
      double distance = 0.0;

      int objective_count = mediator.getObjectiveCount();
      for (int i = 0; i < objective_count; i++)
      {
         distance += Math.pow(s1.getCost(i) - s2.getCost(i), 2.0);
      }

      return Math.sqrt(distance);
   }

   public void truncate(int size)
   {
      truncate(size, NondominatedPopulation::compare);
   }
}
