package com.stevanmatovic.elastic.scientificcentral.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Random;

public class WeatherDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    Random random = new Random();
    delegateExecution.setVariable("weatherOK",random.nextBoolean());
  }
}
