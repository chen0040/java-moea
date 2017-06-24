package com.github.chen0040.moea.problems;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.moea.components.Solution;

import java.util.Arrays;
import java.util.List;


/**
 * Created by xschen on 18/6/2017.
 */
public class SYMPART implements ProblemInstance {

   private final static double a = 1;
   private final static double b = 10;
   private final static double c = 8;
   private static final long serialVersionUID = -1412323130109052886L;
   private double c1;
   private double c2;
   private double b1;

   public SYMPART(){
      c1 = (a+c/2);
      c2 = (c+2*a);
      b1 = (b/2);
   }

   @Override public double getCost(Solution x, int objective_index) {
      double f=0;
      switch(objective_index)
      {
         case 0:
            f = SYMPART_f1(x, 6, 3);
            break;
         case 1:
            f = SYMPART_f2(x, 6, 3);
            break;
      }
      return f;
   }


   public double SYMPART_f1(Solution x, int nx,int n_obj)
   {
      double omega =  Math.PI / 4.0;
      double si = Math.sin(omega);
      double co = Math.cos(omega);
      // copy array to preserve original values
      double[] localX = new double[nx];
      for(int index=0; index < nx; ++index)
      {
         localX[index]=x.get(index);
      }

      int dim;
      double h1;
      // hide original x
      // x = localX;
      //rotate( nx, x );
      for( dim=0; dim+1 < nx; dim+=2 )
      {
         h1 = localX[dim];
         localX[dim] = co * h1 - si * localX[dim+1];
         localX[dim+1] = si * h1 + co * localX[dim+1];
      }
      double x1 = localX[0], x2 = localX[1];
      int xnum;
      // find tile
      TupleTwo<Integer, Integer> tt = findTile(x1, x2);
      int i = tt._1();
      int j = tt._2();
      // restrict to 9 tiles
      if (i > 1) i = 1; else if (i < -1) i = -1;
      if (j > 1) j = 1; else if (j < -1) j = -1;
      // Get values
      double f1 = 0;
      for( xnum=0; xnum<nx; xnum++ )
      {
         x1 = localX[xnum];
         if( (xnum % 2) == 0 )
         {
            f1 += Math.pow(x1+a-i*c2,2);
         }
         else
         {
            f1 += Math.pow(x1 - j * b, 2);
         }
      }
      f1 /= nx;
      
      return f1;
   }

   public double SYMPART_f2(Solution x, int nx,int n_obj)
   {
      double omega =  Math.PI / 4.0;
      double si = Math.sin(omega), co = Math.cos(omega);
      // copy array to preserve original values
      double[] localX = new double[nx];
      for(int index=0; index < nx; ++index)
      {
         localX[index]=x.get(index);
      }
      int dim;
      double h1;
      // hide original x
      // x = localX;
      //rotate( nx, x );
      for( dim=0; dim+1 < nx; dim+=2 )
      {
         h1 = localX[dim];
         localX[dim] = co * h1 - si * localX[dim+1];
         localX[dim+1] = si * h1 + co * localX[dim+1];
      }
      double x1 = localX[0], x2 = localX[1];
      int xnum;
      // find tile
      TupleTwo<Integer, Integer> tt = findTile(x1, x2);
      int i = tt._1();
      int j = tt._2();
      // restrict to 9 tiles
      if (i > 1) i = 1; else if (i < -1) i = -1;
      if (j > 1) j = 1; else if (j < -1) j = -1;
      // Get values

      double f2 = 0;
      for( xnum=0; xnum<nx; xnum++ )
      {
         x1 = localX[xnum];
         if( (xnum % 2) == 0 )
         {

            f2 += Math.pow(x1-a-i*c2,2);
         }
         else
         {

            f2 += Math.pow(x1-j*b,2);
         }
      }

      f2 /= nx;
      
      return f2;
   }

   /* these variables are needed to speed up rotation */
   public TupleTwo<Integer, Integer> findTile(double x1, double x2)
   {
      int t1 = 0;
      int t2 = 0;
      double xx1 = (x1 < 0) ? -x1 : x1;
      double xx2 = (x2 < 0) ? -x2 : x2;
      t1 = (xx1 < c1) ? 0 : ((int)Math.ceil((xx1-c1)/c2));
      t2 = (xx2 < b1) ? 0 : ((int)Math.ceil((xx2-b1)/b));
      if (x1 < 0) t1 = -(t1);
      if (x2 < 0) t2 = -(t2);
      
      return new TupleTwo<>(t1, t2);
   }

   /* returns tile number between 0 and 8
   returns - 1 if out of any tile, function does
   not depend on objFct! */
   public int findTileSYMPART(double x1, double x2)
   {
      int dim;
      double[] x = new double[2];
      double h1;
      double omega =  Math.PI / 4.0;
      double si = Math.sin(omega);
      double co = Math.cos(omega);
      x[0] = x1;
      x[1] = x2;
      //rotate( 2, x );
      for( dim=0; dim+1 < 2; dim+=2 )
      {
         h1 = x[dim];
         x[dim] = co * h1 - si * x[dim+1];
         x[dim+1] = si * h1 + co * x[dim+1];
      }
      TupleTwo<Integer, Integer> tt = findTile(x[0], x[1]);
      int i = tt._1();
      int j = tt._2();
      // restrict to 9 tiles
      if (Math.abs(i) > 1 || Math.abs(j) > 1) return -1;
      return (i + 1) * 3 + (j + 1);
   }


   @Override public int getObjectiveCount() {
      return 2;
   }


   @Override public int getDimension() {
      return 6;
   }


   @Override public List<Double> getLowerBounds() {
      return Arrays.asList(-20.0, -20.0, -20.0, -20.0, -20.0, -20.0);
   }


   @Override public List<Double> getUpperBounds() {
      return Arrays.asList(20.0, 20.0, 20.0, 20.0, 20.0, 20.0);
   }
}
