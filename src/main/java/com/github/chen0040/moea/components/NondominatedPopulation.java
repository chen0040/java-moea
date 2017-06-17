package com.github.chen0040.moea.components;


import com.github.chen0040.moea.utils.InvertedCompareUtils;
import com.github.chen0040.moea.utils.SortUtils;
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
   private static final long serialVersionUID = 5499819471836071184L;

   @Override
   public Population makeCopy(){
      NondominatedPopulation clone = new NondominatedPopulation();
      clone.copy(this);
      return clone;
   }

   // compare by costs and constraints
   public static int compare(Solution s1, Solution s2){
      return - invertedCompare(s1, s2);
   }

   // compare by costs and constraints but invert the sign
   public static int invertedCompare(Solution s1, Solution s2) {
      int flag = InvertedCompareUtils.ConstraintCompare(s1, s2);

      if (flag == 0)
      {
         flag = InvertedCompareUtils.ParetoObjectiveCompare(s1, s2);
      }
      return flag;
   }

   @Override
   public boolean add(Solution solution_to_add)
   {
      List<Solution> solutions_to_remove = new ArrayList<>();

      boolean should_add = true;
      for (Solution solution : solutions)
      {
         int flag = invertedCompare(solution_to_add, solution);

         if (flag < 0)
         {
            solutions_to_remove.add(solution);
         }
         else if (flag > 0) // solution is better than solution_to_add
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
         return solutions.add(solution_to_add);
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

   // sort by costs and constraints and truncate
   public void sortDescAndTruncate(int size)
   {
      // solutions must be sorted descendingly such that solutions[0] is the best solution
      sortAndTruncate(size, NondominatedPopulation::invertedCompare);
   }

   // better in the sense of costs and constraints
   public static boolean better(Solution s1, Solution s2) {
      return invertedCompare(s1, s2) < 0;
   }
}
