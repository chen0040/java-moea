package com.github.chen0040.moea.utils;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 15/6/2017.
 */
public class ArrayListUtils {
   public static List<Double> zeros(int size) {
      List<Double> result = new ArrayList<>();
      for(int i=0; i < size; ++i) {
         result.add(0.0);
      }
      return result;
   }


   public static List<Double> asArray(double value, int size) {
      List<Double> result = new ArrayList<>();
      for(int i=0; i < size; ++i) {
         result.add(value);
      }
      return result;
   }
}
