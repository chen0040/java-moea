package com.github.chen0040.moea.utils;


import com.github.chen0040.moea.components.Solution;

import java.util.Comparator;
import java.util.List;


/**
 * Created by xschen on 15/6/2017.
 */
public class SortUtils {

   public static <S> boolean less(S a1, S a2, Comparator<S> comparator) {
      return comparator.compare(a1, a2) < 0;
   }

   public static <S> void exchange(List<S> a, int i, int j){
      S temp = a.get(i);
      a.set(i, a.get(j));
      a.set(j, temp);
   }

   public static <S> boolean isSortedAsc(List<S> a, Comparator<S> comparator) {
      for(int i=1; i < a.size(); ++i) {
         int j = i-1;
         if(comparator.compare(a.get(j), a.get(i)) > 0) {
            return false;
         }
      }
      return true;
   }


   public static void print(List<Integer> a) {
      System.out.print("[");
      for(int i=0; i < a.size(); ++i) {
         if(i != 0) {
            System.out.print(", ");
         }
         System.out.print(a.get(i));
      }
      System.out.println("]");
   }


   public static <S> boolean isSortedDesc(List<S> a, Comparator<S> comparator) {
      for(int i=1; i < a.size(); ++i) {
         int j = i-1;
         if(comparator.compare(a.get(j), a.get(i)) < 0) {
            return false;
         }
      }
      return true;
   }

}
