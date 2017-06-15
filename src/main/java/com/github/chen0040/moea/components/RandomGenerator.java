package com.github.chen0040.moea.components;


import java.io.Serializable;


/**
 * Created by xschen on 15/6/2017.
 */
public interface RandomGenerator extends Serializable {
   double nextDouble();
   int nextInt(int upper);
}
