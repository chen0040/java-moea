# java-moea
An optimization framework for multi-objective evolutionary algorithms

[![Build Status](https://travis-ci.org/chen0040/java-moea.svg?branch=master)](https://travis-ci.org/chen0040/java-moea) [![Coverage Status](https://coveralls.io/repos/github/chen0040/java-moea/badge.svg?branch=master)](https://coveralls.io/github/chen0040/java-moea?branch=master) 

# Install

Add the follow dependency to your POM file:

```xml
<dependency>
  <groupId>com.github.chen0040</groupId>
  <artifactId>java-moea</artifactId>
  <version>1.0.1</version>
</dependency>
```

# Usage

The following sample code shows how to use NSGA-II to solve the NDND 2-objective optimization problem:

```java
NSGAII algorithm = new NSGAII();
algorithm.getMediator().read(new TNK());
algorithm.getMediator().setPopulationSize(1000);
algorithm.getMediator().setMaxGenerations(100);
algorithm.setDisplayEvery(10);

NondominatedPopulation pareto_front = algorithm.solve();
```
