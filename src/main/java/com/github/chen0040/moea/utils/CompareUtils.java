package com.github.chen0040.moea.utils;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.moea.components.Solution;


/**
 * Created by xschen on 16/6/2017.
 */
public class CompareUtils {
   public static TupleTwo<Integer, Boolean> EpsilonObjectiveCompare(Solution solution1, Solution solution2, double[] epsilons)
   {
      boolean is_same_box=false;
      int compare = 0;

      boolean dominate1 = false;
      boolean dominate2 = false;

      int objective_count = solution1.getCosts().size();
      
      for (int i = 0; i < objective_count; i++)
      {
         double epsilon = epsilons[i < epsilons.length-1 ? i : epsilons.length-1];

         int index1 = (int)Math.floor(solution1.getCost(i) / epsilon);
         int index2 = (int)Math.floor(solution2.getCost(i) / epsilon);

         if (index1 < index2)
         {
            dominate1 = true;

            if (dominate2)
            {
               return new TupleTwo<>(0, is_same_box);
            }
         }
         else if (index1 > index2)
         {
            dominate2 = true;

            if (dominate1)
            {
               return new TupleTwo<>(0, is_same_box);
            }
         }
      }

      if (!dominate1 && !dominate2)
      {
         is_same_box=true;

         double dist1 = 0.0;
         double dist2 = 0.0;

         for (int i = 0; i < objective_count; i++)
         {
            double epsilon = epsilons[i < epsilons.length - 1 ? i : epsilons.length - 1];

            int index1 = (int)Math.floor(solution1.getCost(i)
                    / epsilon);
            int index2 = (int)Math.floor(solution2.getCost(i)
                    / epsilon);

            dist1 += Math.pow(solution1.getCost(i) - index1 * epsilon,
                    2.0);
            dist2 += Math.pow(solution2.getCost(i) - index2 * epsilon,
                    2.0);
         }

         dist1 = Math.sqrt(dist1);
         dist2 = Math.sqrt(dist2);

         if (dist1 < dist2)
         {
            return new TupleTwo<>(-1, is_same_box);
         }
         else
         {
            return new TupleTwo<>(1, is_same_box);
         }
      }
      else if (dominate1)
      {
         return new TupleTwo<>(-1, is_same_box);
      }
      else
      {
         return new TupleTwo<>(1, is_same_box);
      }
   }

   public static int ConstraintCompare(Solution solution1, Solution solution2)
   {
      double constraints1 = 0;
      int objective_count = solution1.getCosts().size();
      for (int i = 0; i < objective_count; i++)
      {
         constraints1 += Math.abs(solution1.getConstraint(i));
      }

      double constraints2 = 0;
      for (int i = 0; i < objective_count; i++)
      {
         constraints2 += Math.abs(solution2.getConstraint(i));
      }

      if ((constraints1 != 0.0) || (constraints2 != 0.0))
      {
         if (constraints1 == 0.0)
         {
            return -1;
         }
         else if (constraints2 == 0.0)
         {
            return 1;
         }
         else
         {
            return Double.compare(constraints1, constraints2);
         }
      }
      else
      {
         return 0;
      }
   }

   public static int ParetoConstraintCompare(Solution solution1, Solution solution2)
   {
      boolean dominate1 = false;
      boolean dominate2 = false;

      int objective_count = solution1.getCosts().size();
      for (int i = 0; i < objective_count; i++)
      {
         if (Math.abs(solution1.getConstraint(i)) < Math.abs(solution2.getConstraint(i)))
         {
            dominate1 = true;
            if (dominate2)
            {
               return 0;
            }
         }
         else if (Math.abs(solution1.getConstraint(i)) > Math.abs(solution2.getConstraint(i)))
         {
            dominate2 = true;
            if (dominate1)
            {
               return 0;
            }
         }
      }

      if (dominate1 == dominate2)
      {
         return 0;
      }
      else if (dominate1)
      {
         return -1;
      }
      else
      {
         return 1;
      }
   }

   public static int ParetoObjectiveCompare(Solution solution1, Solution solution2)
   {
      boolean dominate1 = false;
      boolean dominate2 = false;

      int objective_count = solution1.getCosts().size();
      for (int i = 0; i < objective_count; i++)
      {
         if (solution1.getCost(i) < solution2.getCost(i))
         {
            dominate1 = true;

            if (dominate2)
            {
               return 0;
            }
         }
         else if (solution1.getCost(i) > solution2.getCost(i))
         {
            dominate2 = true;

            if (dominate1)
            {
               return 0;
            }
         }
      }

      if (dominate1 == dominate2)
      {
         return 0;
      }
      else if (dominate1)
      {
         return -1;
      }
      else
      {
         return 1;
      }
   }



   public static int CrowdingDistanceCompare(Solution solution1, Solution solution2)
   {
      double crowding1 = solution1.getCrowdingDistance();
      double crowding2 = solution2.getCrowdingDistance();

      if (crowding1 > crowding2)
      {
         return -1;
      }
      else if (crowding1 < crowding2)
      {
         return 1;
      }
      else
      {
         return 0;
      }
   }

   public static int RankCompare(Solution solution1, Solution solution2)
   {
      int rank1 = solution1.getRank();
      int rank2 = solution2.getRank();

      if (rank1 < rank2)
      {
         return -1;
      }
      else if (rank1 > rank2)
      {
         return 1;
      }
      else
      {
         return 0;
      }
   }
}
