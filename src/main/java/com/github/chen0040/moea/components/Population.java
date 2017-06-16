package com.github.chen0040.moea.components;


import com.github.chen0040.moea.utils.QuickSort;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Created by xschen on 15/6/2017.
 */
@Getter
@Setter
public class Population implements Serializable {

   private static final long serialVersionUID = -7818958192871058630L;

   @Setter(AccessLevel.NONE)
   protected final List<Solution> solutions = new ArrayList<>();
   protected Mediator mediator = new Mediator();
   protected int generation = 0;
   protected int populationSize = 1000;

   public void initialize(){

      solutions.clear();
      for(int i=0; i < populationSize; ++i){
         Solution s = new Solution();
         s.initialize(mediator);
         solutions.add(s);
      }
   }

   public void add(Population population){
      solutions.addAll(population.solutions);
   }

   public void truncate(int size, Comparator<Solution> comparator) {

      int current_size = solutions.size();
      if(current_size > size) {
         QuickSort.sort(solutions, comparator);
         for(int i= current_size - 1; i >= size; --i){
            solutions.remove(i);
         }
      }

   }

   public boolean add(Solution s) {
      solutions.add(s);
      return true;
   }

   public int size() {
      return solutions.size();
   }
}
