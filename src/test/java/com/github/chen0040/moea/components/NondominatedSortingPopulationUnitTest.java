package com.github.chen0040.moea.components;


import com.github.chen0040.moea.utils.ArrayListUtils;
import com.github.chen0040.moea.utils.CostFunction;
import com.github.chen0040.moea.utils.SortUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.assertTrue;


/**
 * Created by xschen on 17/6/2017.
 */
public class NondominatedSortingPopulationUnitTest {

   private static final int dimension = 1000;
   private static final Random random = new Random();

   private Mediator mediator;
   @BeforeMethod
   public void setUp(){
      mediator = new Mediator();
      mediator.setDimension(dimension);
      mediator.setLowerBounds(ArrayListUtils.zeros(dimension));
      mediator.setUpperBounds(ArrayListUtils.asList(1.0, dimension));
      mediator.setObjectiveCount(3);
      mediator.setMutationRate(0.2);

      CostFunction costFunction = (CostFunction) (s, objective_index, lowerBounds, upperBounds) -> random.nextDouble();
      mediator.setCostFunction(costFunction);
   }

   @Test
   public void test_sortDescAndTruncate(){
      NondominatedSortingPopulation population = new NondominatedSortingPopulation();
      population.setPopulationSize(100);
      population.initialize();

      for(Solution s : population) {
         s.setRank(mediator.nextInt(100));
      }
      population.sortDescAndTruncate(99);

      assertThat(population.size()).isEqualTo(99);
      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));

      for(int i=0; i < 10; ++i){
         Solution s1 = population.get(i);
         System.out.println("c1: " + s1.getRank());
      }
   }

   @Test
   public void test_add(){
      NondominatedSortingPopulation population = new NondominatedSortingPopulation();
      population.setPopulationSize(100);
      population.initialize();

      for(Solution s : population) {
         s.setRank(mediator.nextInt(100));
      }

      population.sortDescAndTruncate(100);
      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));

      NondominatedSortingPopulation population2 = (NondominatedSortingPopulation) population.makeCopy();

      boolean added = false;
      for(int i=0; i < 100; ++i) {
         Solution s = new Solution();
         s.setRank(mediator.nextInt(100));
         if(population2.add(s)){
            added = true;
         }
      }

      assertTrue(SortUtils.isSortedDesc(population2.solutions, NondominatedSortingPopulation::compare));

      if(added) {
         assertThat(population.solutions).isNotEqualTo(population2.solutions);
      }

      population.add(population2);
      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));


   }

   @Test
   public void test_better(){
      Solution s1 = new Solution();
      s1.setRank(1);
      Solution s2 = new Solution();
      s2.setRank(3);
      assertTrue(NondominatedSortingPopulation.better(s1, s2));
   }

   @Test
   public void test_replace(){
      NondominatedSortingPopulation population = new NondominatedSortingPopulation();
      population.setPopulationSize(100);
      population.initialize();

      for(Solution s : population) {
         s.setRank(mediator.nextInt(100));
      }

      population.sortDescAndTruncate(100);
      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));

      NondominatedSortingPopulation population2 = (NondominatedSortingPopulation) population.makeCopy();

      boolean replaced = false;
      for(int i=0; i < 100; ++i) {
         Solution s = new Solution();
         s.setRank(mediator.nextInt(100));
         if(population2.replace(population2.any(), s)){
            replaced = true;
         }
      }

      assertTrue(SortUtils.isSortedDesc(population2.solutions, NondominatedSortingPopulation::compare));
      assertTrue(replaced);
      assertThat(population.solutions).isNotEqualTo(population2.solutions);

      population2.clear();
   }

   @Test
   public void test_remove(){
      NondominatedSortingPopulation population = new NondominatedSortingPopulation();
      population.setPopulationSize(100);
      population.initialize();

      for(Solution s : population) {
         s.setRank(mediator.nextInt(100));
      }

      population.sortDescAndTruncate(100);
      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));

      population.remove(population.any());
      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));

      population.remove(mediator.nextInt(population.size()));
      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));
   }
}
