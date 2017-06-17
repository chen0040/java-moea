package com.github.chen0040.moea.components;


import com.github.chen0040.moea.utils.QuickSort;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


/**
 * Created by xschen on 15/6/2017.
 */
@Getter
@Setter
public class Population implements Serializable,Iterable<Solution> {

   private static final long serialVersionUID = -7818958192871058630L;

   @Setter(AccessLevel.NONE)
   protected final List<Solution> solutions = new ArrayList<>();
   protected Mediator mediator = new Mediator();
   protected int generation = 0;


   public void initialize(){

      int populationSize = mediator.getPopulationSize();
      solutions.clear();
      for(int i=0; i < populationSize; ++i){
         Solution s = new Solution();
         s.initialize(mediator);
         solutions.add(s);
      }
   }

   public void add(Population population){
      for(Solution s : population){
         add(s);
      }
   }

   public void sort(Comparator<Solution> comparator) {
      QuickSort.sort(solutions, comparator);
   }

   public void sortAndTruncate(int size, Comparator<Solution> comparator) {

      int current_size = solutions.size();
      QuickSort.sort(solutions, comparator);

      if(current_size > size) {
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


   @Override public Iterator<Solution> iterator() {
      return solutions.iterator();
   }


   public Solution get(int i) {
      return solutions.get(i);
   }

   public Population makeCopy() {
      Population clone = new Population();
      clone.copy(this);
      return clone;
   }

   public void copy(Population rhs) {
      generation = rhs.generation;
      solutions.clear();
      for(Solution s : rhs.solutions) {
         solutions.add(s.makeCopy());
      }
      mediator = rhs.mediator;
   }

   public Solution any(){
      return solutions.get(mediator.nextInt(solutions.size()));
   }



}
