package com.github.chen0040.moea.algorithms;


import com.github.chen0040.moea.components.NondominatedPopulation;
import com.github.chen0040.moea.problems.NDND;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * Created by xschen on 19/6/2017.
 */
public class GDE3UnitTest {

   @Test
   public void test_ndnd(){

      GDE3 algorithm = new GDE3();
      algorithm.read(new NDND());
      algorithm.setPopulationSize(100);
      algorithm.setMaxGenerations(50);
      algorithm.setDisplayEvery(10);

      NondominatedPopulation pareto_front = algorithm.solve();
   }
}
