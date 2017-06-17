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
      mediator.setPopulationSize(100);

      CostFunction costFunction = (CostFunction) (s, objective_index, lowerBounds, upperBounds) -> random.nextDouble();
      mediator.setCostFunction(costFunction);
   }

   @Test
   public void test_sort(){
      NondominatedSortingPopulation population = new NondominatedSortingPopulation();
      population.setMediator(mediator);
      population.initialize();

      for(Solution s : population) {
         s.evaluate(mediator);
      }
      population.sort();

      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));

      for(int i=0; i < 10; ++i){
         Solution s1 = population.get(i);
         System.out.println("rank: " + s1.getRank());
      }
   }

   @Test
   public void test_add(){
      NondominatedSortingPopulation population = new NondominatedSortingPopulation();
      population.setMediator(mediator);

      for(int i = 0; i < 100; ++i) {
         Solution s = new Solution();
         s.initialize(mediator);
         s.evaluate(mediator);
         population.add(s);
      }

      population.sort();

      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));

      for(int i=0; i < 100; ++i){
         Solution s1 = population.get(i);
         System.out.println("rank: " + s1.getRank());
      }
   }

   @Test
   public void test_truncate(){
      NondominatedSortingPopulation population = new NondominatedSortingPopulation();
      population.setMediator(mediator);
      population.initialize();

      for(Solution s : population) {
         s.evaluate(mediator);
      }
      population.truncate(50);

      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));

      for(int i=0; i < 10; ++i){
         Solution s1 = population.get(i);
         System.out.println("rank: " + s1.getRank());
      }
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
      population.setMediator(mediator);
      population.initialize();

      for(Solution s : population) {
         s.evaluate(mediator);
      }

      population.sort();

      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));

      NondominatedSortingPopulation population2 = (NondominatedSortingPopulation)population.makeCopy();
      for(int i=0; i < 10; ++i) {
         Solution s =new Solution();
         s.initialize(mediator);
         s.evaluate(mediator);
         population2.replace(population2.any(), s);
      }

      population2.sort();

      assertTrue(SortUtils.isSortedDesc(population2.solutions, NondominatedSortingPopulation::compare));

      assertThat(population2.solutions).isNotEqualTo(population.solutions);

   }

   @Test
   public void test_remove(){
      NondominatedSortingPopulation population = new NondominatedSortingPopulation();
      population.setMediator(mediator);
      population.initialize();

      for(Solution s : population) {
         s.evaluate(mediator);
      }

      population.sort();

      assertThat(population.size()).isEqualTo(100);

      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedSortingPopulation::compare));

      NondominatedSortingPopulation population2 = (NondominatedSortingPopulation)population.makeCopy();

      assertThat(population2.size()).isEqualTo(100);

      for(int i=0; i < 10; ++i) {
         population2.remove(population2.any());
      }

      assertThat(population2.size()).isEqualTo(90);

      for(int i=0; i < 10; ++i) {
         population2.remove(i);
      }

      assertThat(population2.size()).isEqualTo(80);

      population2.sort();

      assertTrue(SortUtils.isSortedDesc(population2.solutions, NondominatedSortingPopulation::compare));

      assertThat(population2.solutions).isNotEqualTo(population.solutions);

   }

}
