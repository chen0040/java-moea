package com.github.chen0040.moea.exceptions;


import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 17/6/2017.
 */
@Getter
@Setter
public class OutOfRangeException extends RuntimeException {
   private static final long serialVersionUID = -9128963842995898067L;
   private final int actualSize;
   private final int minSize;
   private final int maxSize;

   public OutOfRangeException(int actual_size, int min_size, int max_size) {
      this.actualSize = actual_size;
      this.minSize = min_size;
      this.maxSize = max_size;
   }
}
