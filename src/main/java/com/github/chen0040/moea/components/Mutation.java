package com.github.chen0040.moea.components;


import com.github.chen0040.moea.enums.MutationType;


/**
 * Created by xschen on 17/6/2017.
 */
public class Mutation {
   public static void apply(Mediator mediator, Solution solution) {
      MutationType mutationType = mediator.getMutationType();
      if(mutationType == MutationType.Uniform) {
         solution.mutateUniformly(mediator);
      }
   }

}
