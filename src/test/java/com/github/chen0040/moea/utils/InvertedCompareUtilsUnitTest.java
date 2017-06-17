package com.github.chen0040.moea.utils;


import com.github.chen0040.moea.components.NondominatedSortingPopulation;
import com.github.chen0040.moea.components.Solution;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 17/6/2017.
 */
public class InvertedCompareUtilsUnitTest {
   @Test
   public void test_paretoObjectiveCompare(){
      Solution s1 = new Solution();
      s1.getCosts().add(0.1);
      Solution s2 = new Solution();
      s2.getCosts().add(0.2);
      assertThat(InvertedCompareUtils.ParetoObjectiveCompare(s1, s2)).isEqualTo(-1);

      s1 = new Solution();
      s1.getCosts().add(0.1);
      s1.getCosts().add(0.1);
      s2 = new Solution();
      s2.getCosts().add(0.2);
      s2.getCosts().add(0.2);
      assertThat(InvertedCompareUtils.ParetoObjectiveCompare(s1, s2)).isEqualTo(-1);

      s1 = new Solution();
      s1.getCosts().add(0.2);
      s1.getCosts().add(0.1);
      s2 = new Solution();
      s2.getCosts().add(0.1);
      s2.getCosts().add(0.1);
      assertThat(InvertedCompareUtils.ParetoObjectiveCompare(s1, s2)).isEqualTo(1);

      s1 = new Solution();
      s1.getCosts().add(0.1);
      s1.getCosts().add(0.2);
      s2 = new Solution();
      s2.getCosts().add(0.1);
      s2.getCosts().add(0.2);
      assertThat(InvertedCompareUtils.ParetoObjectiveCompare(s1, s2)).isEqualTo(0);
   }

   @Test
   public void test_paretoConstraintCompare(){
      Solution s1 = new Solution(); // s1 is better than s2
      s1.getConstraints().add(0.1);
      Solution s2 = new Solution();
      s2.getConstraints().add(0.2);
      assertThat(InvertedCompareUtils.ParetoConstraintCompare(s1, s2)).isEqualTo(-1);

      s1 = new Solution();
      s1.getConstraints().add(0.1);  // s1 is better than s2
      s1.getConstraints().add(0.1);
      s2 = new Solution();
      s2.getConstraints().add(0.2);
      s2.getConstraints().add(0.2);
      assertThat(InvertedCompareUtils.ParetoConstraintCompare(s1, s2)).isEqualTo(-1);

      s1 = new Solution();
      s1.getConstraints().add(0.2);  // s2 is better than s1
      s1.getConstraints().add(0.1);
      s2 = new Solution();
      s2.getConstraints().add(0.1);
      s2.getConstraints().add(0.1);
      assertThat(InvertedCompareUtils.ParetoConstraintCompare(s1, s2)).isEqualTo(1);

      s1 = new Solution();
      s1.getConstraints().add(0.1);
      s1.getConstraints().add(0.2);
      s2 = new Solution();
      s2.getConstraints().add(0.1);
      s2.getConstraints().add(0.2);
      assertThat(InvertedCompareUtils.ParetoConstraintCompare(s1, s2)).isEqualTo(0);
   }

   @Test
   public void test_constraintCompare(){
      Solution s1 = new Solution(); // s1 is better than s2
      s1.getConstraints().add(0.1);
      Solution s2 = new Solution();
      s2.getConstraints().add(0.2);
      assertThat(InvertedCompareUtils.ConstraintCompare(s1, s2)).isEqualTo(-1);

      s1 = new Solution();
      s1.getConstraints().add(0.1); // s1 is better than s2
      s1.getConstraints().add(0.1);
      s2 = new Solution();
      s2.getConstraints().add(0.2);
      s2.getConstraints().add(0.2);
      assertThat(InvertedCompareUtils.ConstraintCompare(s1, s2)).isEqualTo(-1);

      s1 = new Solution();
      s1.getConstraints().add(0.2); //s2 is better than s1
      s1.getConstraints().add(0.1);
      s2 = new Solution();
      s2.getConstraints().add(0.1);
      s2.getConstraints().add(0.1);
      assertThat(InvertedCompareUtils.ConstraintCompare(s1, s2)).isEqualTo(1);

      s1 = new Solution();
      s1.getConstraints().add(0.1);
      s1.getConstraints().add(0.2);
      s2 = new Solution();
      s2.getConstraints().add(0.1);
      s2.getConstraints().add(0.2);
      assertThat(InvertedCompareUtils.ConstraintCompare(s1, s2)).isEqualTo(0);
   }

   @Test
   public void test_crowdingDistanceCompare(){
      Solution s1 = new Solution();
      Solution s2 = new Solution();

      s1.setCrowdingDistance(10); // s1 is better than s2
      s2.setCrowdingDistance(1);
      assertThat(InvertedCompareUtils.CrowdingDistanceCompare(s1, s2)).isEqualTo(-1);
   }

   @Test
   public void test_rankCompare(){
      Solution s1 = new Solution();
      Solution s2 = new Solution();

      s1.setRank(1); // s1 is better than s2
      s2.setRank(4);
      assertThat(InvertedCompareUtils.RankCompare(s1, s2)).isEqualTo(-1);
   }
}
