package com.github.chen0040.moea.components;


import com.github.chen0040.moea.utils.FastNondominatedSort;
import com.github.chen0040.moea.utils.InvertedCompareUtils;
import com.github.chen0040.moea.utils.QuickSort;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 16/6/2017.
 */
@Getter
@Setter
public class NondominatedSortingPopulation extends Population {
   public static final double Epsilon = 1e-10;
   private static final long serialVersionUID = 5499819471836071184L;
   private boolean rankedByCostsAndConstraints = false;

   @Override
   public Population makeCopy(){
      NondominatedSortingPopulation clone = new NondominatedSortingPopulation();
      clone.copy(this);
      return clone;
   }

   // compare by rank and crowding distance
   // return -1 if s2 is better
   // return 1 is s1 is better
   public static int compare(Solution s1, Solution s2){
      return - invertedCompare(s1, s2);
   }

   // compare by rank and crowding distance but invert the sign
   // return 1 if s1 is better
   // return -1 if s2 is better
   public static int invertedCompare(Solution s1, Solution s2) {
      int flag = InvertedCompareUtils.RankCompare(s1, s2);

      if (flag == 0)
      {
         flag = InvertedCompareUtils.CrowdingDistanceCompare(s1, s2);
      }
      return flag;
   }

   @Override
   public boolean add(Solution solution_to_add)
   {
      solutions.add(solution_to_add);
      rankedByCostsAndConstraints = false;
      return true;
   }

   public Solution get(int index){
      ensureRankedByObjectiveAndConstraint();
      return solutions.get(index);
   }

   private void ensureRankedByObjectiveAndConstraint(){
      if(!rankedByCostsAndConstraints) {
         FastNondominatedSort.rank(this);
         rankedByCostsAndConstraints = true;
      }
   }


   public void sort(){
      ensureRankedByObjectiveAndConstraint();
      sort(NondominatedSortingPopulation::invertedCompare);
   }

   // sort by rank and crowding distance and truncate
   public void truncate(int size)
   {
      ensureRankedByObjectiveAndConstraint();

      // solutions must be sorted descendingly such that solutions[0] is the best solution
      sortAndTruncate(size, NondominatedSortingPopulation::invertedCompare);
   }

   // better in the sense of rank and crowding distance
   public static boolean better(Solution s1, Solution s2) {
      return invertedCompare(s1, s2) < 0;
   }

   public boolean replace(Solution solution_to_remove, Solution solution_to_add)
   {
      int index = solutions.indexOf(solution_to_remove);
      if(index == -1){
         return false;
      }
      solutions.set(index, solution_to_add);

      rankedByCostsAndConstraints = false;
      return true;
   }

   public void remove(int index){
      solutions.remove(index);
      rankedByCostsAndConstraints = false;
   }

   public void remove(Solution s) {
      solutions.remove(s);
      rankedByCostsAndConstraints = false;
   }

   public void clear(){
      solutions.clear();
      rankedByCostsAndConstraints = false;
   }

   public void prune(int size){

      ensureRankedByObjectiveAndConstraint();

      QuickSort.sort(solutions, InvertedCompareUtils::RankCompare);

      int maxRank = solutions.get(size - 1).getRank();

      Population front = new Population();

      for (int i = solutions.size() - 1; i >= 0; i--)
      {
         Solution solution = solutions.get(i);
         int rank = solution.getRank();

         if (rank >= maxRank)
         {
            solutions.remove(i);

            if (rank == maxRank)
            {
               front.add(solution);
            }
         }
      }

      while (solutions.size() + front.size() > size)
      {
         FastNondominatedSort.updateCrowdingDistance(front);
         front.sortAndTruncate(front.size() - 1,
            InvertedCompareUtils::CrowdingDistanceCompare);
      }

      for (Solution s : front)
      {
         solutions.add(s);
      }
   }
}
