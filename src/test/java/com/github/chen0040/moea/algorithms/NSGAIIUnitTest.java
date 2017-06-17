package com.github.chen0040.moea.algorithms;


import com.github.chen0040.moea.components.NondominatedPopulation;
import com.github.chen0040.moea.enums.CrossoverType;
import com.github.chen0040.moea.enums.ReplacementType;
import com.github.chen0040.moea.tutorials.NDND;
import com.github.chen0040.moea.tutorials.OKA2;
import com.github.chen0040.moea.tutorials.TNK;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * Created by xschen on 17/6/2017.
 */
public class NSGAIIUnitTest {

   @Test
   public void test_tnk(){
      NSGAII algorithm = new NSGAII();
      algorithm.getMediator().read(new TNK());
      algorithm.getMediator().setPopulationSize(1000);
      algorithm.getMediator().setMaxGenerations(100);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }

   @Test
   public void test_ndnd_crossover_uniform(){
      NSGAII algorithm = new NSGAII();
      algorithm.getMediator().read(new TNK());
      algorithm.getMediator().setCrossoverType(CrossoverType.Uniform);
      algorithm.getMediator().setPopulationSize(1000);
      algorithm.getMediator().setMaxGenerations(100);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();

   }

   @Test
   public void test_ndnd_replacement_tournament(){
      NSGAII algorithm = new NSGAII();
      algorithm.getMediator().read(new TNK());
      algorithm.getMediator().setReplacementType(ReplacementType.Tournament);
      algorithm.getMediator().setPopulationSize(1000);
      algorithm.getMediator().setMaxGenerations(100);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }

   @Test
   public void test_ndnd(){

      NSGAII algorithm = new NSGAII();
      algorithm.getMediator().read(new NDND());
      algorithm.getMediator().setPopulationSize(1000);
      algorithm.getMediator().setMaxGenerations(100);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }

   @Test
   public void test_oka2(){
      NSGAII algorithm = new NSGAII();
      algorithm.getMediator().read(new OKA2());
      algorithm.getMediator().setPopulationSize(1000);
      algorithm.getMediator().setMaxGenerations(100);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }

}
