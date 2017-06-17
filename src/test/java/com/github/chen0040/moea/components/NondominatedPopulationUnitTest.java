package com.github.chen0040.moea.components;


import com.github.chen0040.moea.utils.ArrayListUtils;
import com.github.chen0040.moea.utils.CostFunction;
import com.github.chen0040.moea.utils.SortUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.testng.Assert.*;


/**
 * Created by xschen on 17/6/2017.
 */
public class NondominatedPopulationUnitTest {

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
   public void test_sortDescAndTruncate(){
      NondominatedPopulation population = new NondominatedPopulation();
      population.setMediator(mediator);
      population.initialize();

      for(Solution s : population) {
         s.evaluate(mediator);
      }
      population.sortDescAndTruncate(99);

      assertThat(population.size()).isEqualTo(99);
      assertTrue(SortUtils.isSortedDesc(population.solutions, NondominatedPopulation::compare));

      for(int i=0; i < 10; ++i){
         Solution s1 = population.get(i);
         System.out.println("c1: " + s1.getCost(0) + ",\tc2: " + s1.getCost(1) + ",\tc3: " + s1.getCost(2));
      }
   }

   @Test
   public void test_add(){
      NondominatedPopulation population = new NondominatedPopulation();
      population.setMediator(mediator);

      for(int i=0; i < 100; ++i) {
         Solution s = new Solution();
         s.evaluate(mediator);
         population.add(s);
      }

      assertTrue(SortUtils.allEqual(population.solutions, NondominatedPopulation::compare));

      System.out.println("population size: " + population.size());

      NondominatedPopulation population2 = new NondominatedPopulation();
      population2.setMediator(mediator);
      population2.initialize();

      for(Solution s : population2) {
         s.evaluate(mediator);
      }

      population.add(population2);

      System.out.println("population size: " + population.size());

      assertTrue(SortUtils.allEqual(population.solutions, NondominatedPopulation::compare));

   }

   @Test
   public void test_better(){
      Solution s1 = new Solution();
      s1.getCosts().add(0.1);
      Solution s2 = new Solution();
      s2.getCosts().add(0.2);
      assertTrue(NondominatedPopulation.better(s1, s2));
   }

}
