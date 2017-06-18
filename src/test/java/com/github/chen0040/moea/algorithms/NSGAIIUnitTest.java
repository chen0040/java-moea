package com.github.chen0040.moea.algorithms;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.moea.components.NondominatedPopulation;
import com.github.chen0040.moea.enums.CrossoverType;
import com.github.chen0040.moea.enums.ReplacementType;
import com.github.chen0040.moea.problems.*;
import com.github.chen0040.moea.utils.CostFunction;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


/**
 * Created by xschen on 17/6/2017.
 */
public class NSGAIIUnitTest {

   @Test
   public void test_tnk(){
      NSGAII algorithm = new NSGAII();
      algorithm.read(new TNK());
      algorithm.setPopulationSize(100);
      algorithm.setMaxGenerations(50);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }

   @Test
   public void test_ndnd_crossover_uniform(){
      NSGAII algorithm = new NSGAII();
      algorithm.read(new TNK());
      algorithm.setCrossoverType(CrossoverType.Uniform);
      algorithm.setPopulationSize(100);
      algorithm.setMaxGenerations(50);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();

   }

   @Test
   public void test_ndnd_replacement_tournament(){
      NSGAII algorithm = new NSGAII();
      algorithm.read(new TNK());
      algorithm.setReplacementType(ReplacementType.Tournament);
      algorithm.setPopulationSize(100);
      algorithm.setMaxGenerations(50);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }

   @Test
   public void test_ndnd(){

      NSGAII algorithm = new NSGAII();
      algorithm.read(new NDND());
      algorithm.setPopulationSize(100);
      algorithm.setMaxGenerations(50);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }

   @Test
   public void test_oka2(){
      NSGAII algorithm = new NSGAII();
      algorithm.read(new OKA2());
      algorithm.setPopulationSize(100);
      algorithm.setMaxGenerations(50);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }

   @Test
   public void test_ngpd(){
      NSGAII algorithm = new NSGAII();
      algorithm.read(new NGPD());
      algorithm.setPopulationSize(100);
      algorithm.setMaxGenerations(50);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }

   @Test
   public void test_sympart(){
      NSGAII algorithm = new NSGAII();
      algorithm.read(new SYMPART());
      algorithm.setPopulationSize(100);
      algorithm.setMaxGenerations(50);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }
   
   @Test
   public void test_ndnd_full(){
      NSGAII algorithm = new NSGAII();
      algorithm.setCostFunction((CostFunction) (x, objective_index, lowerBounds, upperBounds) -> {
         double f1 = 1 - Math.exp((-4) * x.get(0)) * Math.pow(Math.sin(5 * Math.PI * x.get(0)), 4);
         if (objective_index == 0)
         {
            // objective 0
            return f1;
         }
         else
         {
            // objective 1
            double f2, g, h;
            if (x.get(1) > 0 && x.get(1) < 0.4)
               g = 4 - 3 * Math.exp(-2500 * (x.get(1) - 0.2) * (x.get(1) - 0.2));
            else
               g = 4 - 3 * Math.exp(-25 * (x.get(1) - 0.7) * (x.get(1) - 0.7));
            double a = 4;
            if (f1 < g)
               h = 1 - Math.pow(f1 / g, a);
            else
               h = 0;
            f2 = g * h;
            return f2;
         }
      });
      algorithm.setDimension(2);
      algorithm.setObjectiveCount(2);
      algorithm.setLowerBounds(Arrays.asList(0.0, 0.0));
      algorithm.setUpperBounds(Arrays.asList(1.0, 1.0));

      algorithm.setPopulationSize(100);
      algorithm.setMaxGenerations(50);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
      List<TupleTwo<Double, Double>> front2d = pareto_front.front2D();
   }

}
