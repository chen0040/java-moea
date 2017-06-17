package com.github.chen0040.moea.components;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 15/6/2017.
 */
@Setter
@Getter
public class Solution implements Serializable {

   private static final long serialVersionUID = -3869812161043718757L;

   @Setter(AccessLevel.NONE)
   protected final List<Double> costs = new ArrayList<>(); // the lower the cost the better the solution

   @Setter(AccessLevel.NONE)
   protected final List<Double> constraints = new ArrayList<>(); // the lower the constraint the better the solution

   @Setter(AccessLevel.NONE)
   @Getter(AccessLevel.NONE)
   protected final List<Double> data = new ArrayList<>();

   protected int rank = -1; // the lower the rank the better the solution
   protected double crowdingDistance = 0; // the higher the distance the better the solution

   public Solution()
   {

   }

   public double get(int index){
      return data.get(index);
   }

   public void set(int index, double value) {
      data.set(index, value);
   }

   public int size() {
      return data.size();
   }


   public void initialize(Mediator mediator)
   {
      int chromosome_length = mediator.getDimension();
      data.clear();
      for (int i = 0; i < chromosome_length; ++i)
      {
         data.add(mediator.randomWithinBounds(i));
      }
   }

   public void mutateUniformly(Mediator mediator)
   {
      int chromosome_length = mediator.getDimension();
      for (int i = 0; i < chromosome_length; ++i)
      {
         if(mediator.nextDouble() < mediator.getMutationRate()) {
            data.set(i, mediator.randomWithinBounds(i));
         }
      }
   }

   public void onePointCrossover(Mediator mediator, Solution rhs)
   {
      if (data.size() == 1) return;

      int cut_point = 1;
      if (data.size() > 2)
      {
         cut_point = mediator.nextInt(data.size());
      }

      double temp;
      for (int index = 0; index != cut_point; ++index)
      {
         temp = this.get(index);
         this.set(index, rhs.get(index));
         rhs.set(index, temp);

      }
   }

   public void uniformCrossover(Mediator mediator, Solution rhs)
   {
      int length = data.size();
      for (int index = 0; index != length; ++index)
      {
         double val1=this.get(index);
         double val2=rhs.get(index);
         if (mediator.nextDouble() < 0.5)
         {
            this.set(index, val2);
         }

         if (mediator.nextDouble() < 0.5)
         {
            rhs.set(index, val1);
         }
      }
   }

   public void copy(Solution rhs)
   {
      costs.clear();
      constraints.clear();
      data.clear();

      costs.addAll(rhs.costs);
      constraints.addAll(rhs.constraints);
      data.addAll(rhs.data);

      rank = rhs.rank;
      crowdingDistance = rhs.crowdingDistance;
   }

   public void evaluate(Mediator mediator)
   {
      int objective_count = mediator.getObjectiveCount();
      while (costs.size() < objective_count)
      {
         costs.add(0.0);
      }
      for (int objective_index = 0; objective_index < objective_count; ++objective_index)
      {
         costs.set(objective_index, mediator.evaluate(this, objective_index));
      }
   }

   public void evaluate(Mediator mediator, int objective_index)
   {
      int objective_count = mediator.getObjectiveCount();
      while (costs.size() < objective_count)
      {
         costs.add(0.0);
      }
      costs.set(objective_index, mediator.evaluate(this, objective_index));
   }

   public Solution makeCopy()
   {
      Solution clone = new Solution();
      clone.copy(this);

      return clone;
   }


   @Override public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Solution solution = (Solution) o;

      return data.equals(solution.data);
   }


   @Override public int hashCode() {
      return data.hashCode();
   }


   public double getCost(int i) {
      return costs.get(i);
   }

   public double getConstraint(int i) {
      return constraints.get(i);
   }


   @Override public String toString() {
      return "Solution{" + "costs=" + costs + ", data=" + data + '}';
   }
}
