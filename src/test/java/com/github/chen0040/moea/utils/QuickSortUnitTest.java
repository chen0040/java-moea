package com.github.chen0040.moea.utils;


import com.github.chen0040.moea.components.RandomGeneratorImpl;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;


/**
 * Created by xschen on 15/6/2017.
 */
public class QuickSortUnitTest {

   @Test
   public void test_sort(){
      List<Integer> a = Arrays.asList(10, 3, 5, 2, 5, 6,10, 11);

      QuickSort.sort(a, Integer::compareTo);

      assertTrue(SortUtils.isSortedAsc(a, Integer::compareTo));

      SortUtils.print(a);
   }

   @Test
   public void test_sort_large(){
      List<Integer> a = ArrayListUtils.range(100);
      KnuthShuffle.shuffle(a, new RandomGeneratorImpl());

      assertFalse(SortUtils.isSortedAsc(a, Integer::compareTo));

      QuickSort.sort(a, Integer::compareTo);

      assertTrue(SortUtils.isSortedAsc(a, Integer::compareTo));

      SortUtils.print(a);
   }
}
