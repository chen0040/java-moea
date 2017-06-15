package com.github.chen0040.moea.components;


import java.util.Random;


/**
 * Created by xschen on 15/6/2017.
 */
public class RandomGeneratorImpl implements RandomGenerator {

   private static final long serialVersionUID = 685834105363005445L;
   private Random random = new Random();

   @Override public double nextDouble() {
      return random.nextDouble();
   }


   @Override public int nextInt(int upper) {
      return random.nextInt(upper);
   }
}
