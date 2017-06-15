package com.github.chen0040.moea.components;


import com.github.chen0040.moea.utils.ArrayListUtils;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;


/**
 * Created by xschen on 15/6/2017.
 */
public class SolutionUnitTest {
   private static final int dimension = 1000;

   private Mediator mediator;
   @BeforeMethod
   public void setUp(){
      mediator = new Mediator();
      mediator.setDimension(dimension);
      mediator.setLowerBounds(ArrayListUtils.zeros(dimension));
      mediator.setUpperBounds(ArrayListUtils.asArray(1.0, dimension));
      mediator.setObjectiveCount(3);
      mediator.setMutationRate(0.2);

      mediator = Mockito.spy(mediator);
      Mockito.when(mediator.evaluate(any(), eq(0))).thenReturn(10.0);
      Mockito.when(mediator.evaluate(any(), eq(1))).thenReturn(20.0);
      Mockito.when(mediator.evaluate(any(), eq(2))).thenReturn(30.0);
   }

   @Test
   public void test_createAndCloneSolution(){
      Solution s = new Solution();
      s.initialize(mediator);
      assertThat(s.size()).isEqualTo(dimension);

      Solution clone = s.makeCopy();
      assertThat(clone).isEqualTo(s);

   }

   @Test
   public void test_crossoverAndMutate(){
      Solution s = new Solution();
      s.initialize(mediator);
      Solution mutated = s.makeCopy();
      mutated.mutateUniformly(mediator);
      assertThat(mutated).isNotEqualTo(s);

      Solution s2 = new Solution();
      s2.initialize(mediator);

      Solution child1 = s.makeCopy();
      Solution child2 = s2.makeCopy();

      child1.onePointCrossover(mediator, child2);

      assertThat(child1).isNotEqualTo(s);
      assertThat(child2).isNotEqualTo(s2);

      child1 = s.makeCopy();
      Solution child3 = s2.makeCopy();

      child1.uniformCrossover(mediator, child3);

      assertThat(child1).isNotEqualTo(s);
      assertThat(child3).isNotEqualTo(s2);
   }

   @Test
   public void test_evaluate(){
      Solution s = new Solution();
      s.initialize(mediator);

      s.evaluate(mediator);
      assertThat(s.getCosts().get(0)).isEqualTo(10.0);
      assertThat(s.getCosts().get(1)).isEqualTo(20.0);
      assertThat(s.getCosts().get(2)).isEqualTo(30.0);
   }

   @Test
   public void test_evaluate_by_index(){
      Solution s = new Solution();
      s.initialize(mediator);

      s.evaluate(mediator, 1);
      assertThat(s.getCosts().get(1)).isEqualTo(20.0);
   }
}
