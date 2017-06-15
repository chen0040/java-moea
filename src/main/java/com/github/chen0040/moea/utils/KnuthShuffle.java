package com.github.chen0040.moea.utils;


import com.github.chen0040.moea.components.RandomGenerator;

import java.util.List;


/**
 * Created by xschen on 15/6/2017.
 */
public class KnuthShuffle {
   public static <S> void shuffle(List<S> a, RandomGenerator randomGenerator) {
      if(a.size() < 2) return;
      for(int j=1; j < a.size(); ++j) {
         int i = randomGenerator.nextInt(j+1);
         SortUtils.exchange(a, i, j);
      }
   }
}
