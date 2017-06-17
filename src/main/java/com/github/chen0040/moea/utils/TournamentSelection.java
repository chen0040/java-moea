package com.github.chen0040.moea.utils;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.moea.components.RandomGenerator;
import com.github.chen0040.moea.components.Solution;
import com.github.chen0040.moea.exceptions.OutOfRangeException;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;


/**
 * Created by xschen on 4/5/2017.
 */
public class TournamentSelection {
   public static TournamentSelectionResult<Solution> select(List<Solution> population, RandomGenerator randEngine, BiFunction<Solution, Solution, Boolean> better) {
      if(population.size() < 4) {
         throw new OutOfRangeException(population.size(), 4, 10000000);
      }
      KnuthShuffle.shuffle(population, randEngine);

      Solution good1, good2;
      Solution bad1, bad2;
      if(better.apply(population.get(0), population.get(1))){
         good1 = population.get(0);
         bad1 = population.get(1);
      } else {
         good1 = population.get(1);
         bad1 = population.get(0);
      }

      if(better.apply(population.get(2), population.get(3))){
         good2 = population.get(2);
         bad2 = population.get(3);
      } else {
         good2 = population.get(3);
         bad2 = population.get(2);
      }


      TournamentSelectionResult<Solution> result = new TournamentSelectionResult<>();

      result.setWinners(new TupleTwo<>(good1, good2));
      result.setLosers(new TupleTwo<>(bad1, bad2));


      return result;

   }
}
