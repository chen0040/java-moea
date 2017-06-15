package com.github.chen0040.moea.utils;


import java.util.Comparator;
import java.util.List;


/**
 * Created by xschen on 15/6/2017.
 */
public class InsertionSort {
   public static <S> void sort(List<S> a, int lo, int hi, Comparator<S> comparator) {
      for(int i=lo; i <= hi; ++i){
         for(int j=i; j > lo; --j){
            if(SortUtils.less(a.get(j), a.get(j-1), comparator)){
               SortUtils.exchange(a, j, j-1);
            } else {
               break;
            }
         }
      }
   }

}
