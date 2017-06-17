package com.github.chen0040.moea.components;


import com.github.chen0040.moea.utils.ArrayListUtils;
import com.github.chen0040.moea.utils.CostFunction;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.*;


/**
 * Created by xschen on 15/6/2017.
 */
public class PopulationUnitTest {

   private static final int dimension = 1000;

   private Mediator mediator;
   @BeforeMethod
   public void setUp(){
      mediator = new Mediator();
      mediator.setDimension(dimension);
      mediator.setLowerBounds(ArrayListUtils.zeros(dimension));
      mediator.setUpperBounds(ArrayListUtils.asList(1.0, dimension));
      mediator.setObjectiveCount(3);
      mediator.setMutationRate(0.2);

      CostFunction costFunction = Mockito.mock(CostFunction.class);
      Mockito.when(costFunction.evaluate(any(Solution.class), anyInt(), anyListOf(Double.class), anyListOf(Double.class))).thenReturn(10.0);
      mediator.setCostFunction(costFunction);
   }

   @Test
   public void test_truncate(){
      Population p = new Population();
      p.setMediator(mediator);
      p.initialize();

      for(int i=0; i < p.size(); ++i){
         Solution s = p.getSolutions().get(i);
         s.getCosts().add(mediator.nextDouble());
         s.getCosts().add(mediator.nextDouble());
         s.getCosts().add(mediator.nextDouble());
      }

      assertThat(p.size()).isEqualTo(1000);

      p.sortAndTruncate(500, Comparator.comparingDouble(s -> s.getCosts().get(0)));

      assertThat(p.size()).isEqualTo(500);
   }
}
