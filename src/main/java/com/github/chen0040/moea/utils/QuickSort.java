package com.github.chen0040.moea.utils;


import java.util.Comparator;
import java.util.List;


/**
 * Created by xschen on 15/6/2017.
 */
public class QuickSort {
   public static <S> void sort(List<S> a, Comparator<S> comparator) {
      sort(a, 0, a.size()-1, comparator);
   }

   private static <S> void sort(List<S> a, int lo, int hi, Comparator<S> comparator) {
      if(lo >= hi) return;

      if(hi - lo < 7) {
         InsertionSort.sort(a, lo, hi, comparator);
         return;
      }

      int j = partition(a, lo, hi, comparator);
      sort(a, lo, j-1, comparator);
      sort(a, j+1, hi, comparator);
   }

   private static <S> int partition(List<S> a, int lo, int hi, Comparator<S> comparator) {
      int i=lo, j = hi+1;
      S v = a.get(lo);
      while(true) {
         while(SortUtils.less(a.get(++i), v, comparator)){
            if(i >= hi) break;
         }
         while(SortUtils.less(v, a.get(--j), comparator)) {
            if( j <= lo) break;
         }

         if(i >= j) break;

         SortUtils.exchange(a, i, j);
      }
      SortUtils.exchange(a, lo, j);
      return j;
   }


}
