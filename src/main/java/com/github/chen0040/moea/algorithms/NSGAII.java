package com.github.chen0040.moea.algorithms;


import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.moea.components.*;
import com.github.chen0040.moea.enums.ReplacementType;
import com.github.chen0040.moea.utils.InvertedCompareUtils;
import com.github.chen0040.moea.utils.TournamentSelection;
import com.github.chen0040.moea.utils.TournamentSelectionResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by xschen on 17/6/2017.
 * NSGA-II
 */
@Getter
@Setter
public class NSGAII {
   private Mediator mediator = new Mediator();

   @Setter(AccessLevel.NONE)
   private NondominatedPopulation archive = new NondominatedPopulation();

   @Setter(AccessLevel.NONE)
   private int currentGeneration = 0;

   @Setter(AccessLevel.NONE)
   private NondominatedSortingPopulation population = new NondominatedSortingPopulation();

   public NondominatedPopulation solve(){
      initialize();
      int maxGenerations = mediator.getMaxGenerations();
      for(int generation = 0; generation < maxGenerations; ++generation) {
         evolve();
      }

      return archive;
   }

   public void initialize(){
      population.setMediator(mediator);
      population.initialize();
      evaluate(population);
      population.sort();
      currentGeneration = 0;
   }

   public void evolve()
   {
      Population offspring = new Population();
      offspring.setMediator(mediator);

      int populationSize = mediator.getPopulationSize();

      while (offspring.size() < populationSize)
      {
         TournamentSelectionResult<Solution> tournament = TournamentSelection.select(population.getSolutions(), mediator.getRandomGenerator(), (s1, s2) ->
         {
            int flag = 0;
            if ((flag = InvertedCompareUtils.ConstraintCompare(s1, s2))==0)
            {
               if ((flag = InvertedCompareUtils.ParetoObjectiveCompare(s1, s2)) == 0)
               {
                  flag = InvertedCompareUtils.CrowdingDistanceCompare(s1, s2);
               }
            }

            return flag < 0; // should return better
         });

         TupleTwo<Solution, Solution> tournament_winners = tournament.getWinners();

         TupleTwo<Solution, Solution> children = Crossover.apply(mediator, tournament_winners._1(), tournament_winners._2());

         Mutation.apply(mediator, children._1());
         Mutation.apply(mediator, children._2());
         offspring.add(children._1());
         offspring.add(children._2());
      }

      evaluate(offspring);

      ReplacementType replacementType = mediator.getReplacementType();
      if(replacementType == ReplacementType.Generational) {
         merge1(offspring);
      } else if(replacementType == ReplacementType.Tournament) {
         merge2(offspring);
      }

      currentGeneration++;
   }

   private void evaluate(Population population) {
      for (int i = 0; i < population.size(); ++i)
      {
         Solution s = population.getSolutions().get(i);
         s.evaluate(mediator);

         boolean is_archivable = archive.add(s);
         if (archive.size() > mediator.getMaxArchive())
         {
            archive.truncate(mediator.getMaxArchive());
         }
      }
   }

   protected void merge2(Population children)
   {
      int populationSize = mediator.getPopulationSize();

      Population offspring = new Population();

      for (int i = 0; i < populationSize; i++)
      {
         Solution s1 = children.get(i);
         Solution s2 = population.get(i);
         int flag = 0;
         if ((flag = InvertedCompareUtils.ConstraintCompare(s1, s2)) == 0)
         {
            if ((flag = InvertedCompareUtils.ParetoObjectiveCompare(s1, s2)) == 0)
            {
               flag = InvertedCompareUtils.CrowdingDistanceCompare(s1, s2);
            }
         }

         if (flag < 0)
         {
            offspring.add(children.get(i));
         }
         else if (flag > 0)
         {
            offspring.add(children.get(i));
         }
         else
         {
            offspring.add(children.get(i));
            offspring.add(population.get(i));
         }
      }

      population.clear();

      population.add(offspring);

      population.prune(populationSize);
   }

   protected void merge1(Population children)
   {
      int populationSize = mediator.getPopulationSize();

      population.add(children);

      population.truncate(populationSize);
   }
}
