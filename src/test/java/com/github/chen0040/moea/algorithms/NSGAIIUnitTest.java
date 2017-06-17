package com.github.chen0040.moea.algorithms;


import com.github.chen0040.moea.components.NondominatedPopulation;
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

      NondominatedPopulation pareto_front = algorithm.solve();



   }

}
