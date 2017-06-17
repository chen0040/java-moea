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
public class NondominatedSortingPopulation extends Population {
   public static final double Epsilon = 1e-10;
   private static final long serialVersionUID = 5499819471836071184L;

   @Override
   public Population makeCopy(){
      NondominatedSortingPopulation clone = new NondominatedSortingPopulation();
      clone.copy(this);
      return clone;
   }

   public static int compare(Solution s1, Solution s2){
      return - invertedCompare(s1, s2);
   }

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

      // solutions must be sorted descendingly at this point such that solutions[0] is the best solution
      assert SortUtils.isSortedDesc(solutions, NondominatedSortingPopulation::compare);

      boolean should_add = true;
      int size = solutions.size();
      for (int i=size-1; i >= 0; --i)
      {
         Solution solution = solutions.get(i);
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


   public void sortDescAndTruncate(int size)
   {
      // solutions must be sorted descendingly such that solutions[0] is the best solution
      sortAndTruncate(size, NondominatedSortingPopulation::invertedCompare);
   }

   public static boolean better(Solution s1, Solution s2) {
      return invertedCompare(s1, s2) < 0;
   }

   public boolean replace(Solution solution_to_remove, Solution solution_to_add)
   {
      // solutions must be sorted descendingly at this point such that solutions[0] is the best solution
      assert SortUtils.isSortedDesc(solutions, NondominatedSortingPopulation::compare);

      int index = solutions.indexOf(solution_to_remove);
      if(index == -1){
         return false;
      }

      int lt = index - 1;
      int gt = index + 1;

      boolean added = false;
      for(int i=0; i < solutions.size(); ++i) {
         if (!better(solutions.get(i), solution_to_add)) {
            solutions.add(i, solution_to_add);
            added = true;
            break;
         }
      }
      if(!added) {
         solutions.add(solution_to_add);
      }

      return false;
   }
}
