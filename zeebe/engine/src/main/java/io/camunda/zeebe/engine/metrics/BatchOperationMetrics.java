/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Camunda License 1.0. You may not use this file
 * except in compliance with the Camunda License 1.0.
 */
package io.camunda.zeebe.engine.metrics;

import io.camunda.zeebe.engine.metrics.BatchOperationMetricsDoc.BatchOperationAction;
import io.camunda.zeebe.engine.metrics.BatchOperationMetricsDoc.BatchOperationDuration;
import io.camunda.zeebe.engine.metrics.BatchOperationMetricsDoc.BatchOperationKeyNames;
import io.camunda.zeebe.protocol.record.value.BatchOperationType;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class BatchOperationMetrics {

  private static final String ORGANIZATION_ID =
      System.getenv().getOrDefault("CAMUNDA_CLOUD_ORGANIZATION_ID", "null");

  final MeterRegistry registry;

  private final Map<BatchOperationAction, Counter> executedActions = new EnumMap<>(BatchOperationAction.class);
  private final Map<Long, Timer> durations = new HashMap<>();

  public BatchOperationMetrics(final MeterRegistry registry) {
    this.registry = Objects.requireNonNull(registry, "must specify a registry");
  }

  public void batchOperationCreated(final BatchOperationType batchOperationType) {
    batchOperationEvent(BatchOperationAction.CREATED, batchOperationType);
  }

  public void batchOperationQueryAgainstSecondaryDatabase() {
    batchOperationEvent(BatchOperationAction.QUERY, null);
  }

  public void batchOperationStarted(final BatchOperationType batchOperationType) {
    batchOperationEvent(BatchOperationAction.STARTED, batchOperationType);
  }

  public void batchOperationFailed(final BatchOperationType batchOperationType) {
    batchOperationEvent(BatchOperationAction.FAILED, batchOperationType);
  }

  public void batchOperationChunkCreated() {
    batchOperationEvent(BatchOperationAction.CHUNK_CREATED, null);
  }

  public void batchOperationExecute(final BatchOperationType batchOperationType) {
    batchOperationEvent(BatchOperationAction.EXECUTE, batchOperationType);
  }

  public void batchOperationCancelled(final BatchOperationType batchOperationType) {
    batchOperationEvent(BatchOperationAction.CANCELLED, batchOperationType);
  }

  public void batchOperationSuspended(final BatchOperationType batchOperationType) {
    batchOperationEvent(BatchOperationAction.SUSPENDED, batchOperationType);
  }

  public void batchOperationResumed(final BatchOperationType batchOperationType) {
    batchOperationEvent(BatchOperationAction.RESUMED, batchOperationType);
  }

  public void batchOperationCompleted(final BatchOperationType batchOperationType) {
    batchOperationEvent(BatchOperationAction.COMPLETED, batchOperationType);
  }

  public void recordBatchOperationDuration(
      final long batchOperationKey,
      final BatchOperationType batchOperationType,
      final BatchOperationDuration durationType,
      final long duration) {
    final var meterDoc = BatchOperationMetricsDoc.DURATION;
    durations
        .computeIfAbsent(
            batchOperationKey,
            (key) -> registerBatchOperationDurationTimer(key, batchOperationType, durationType))
        .record(duration);
  }

  public void recordItemsPerPartition(final int itemsAmount, final int partitionId, final long batchOperationKey, final BatchOperationType batchOperationType) {
    final var meterDoc = BatchOperationMetricsDoc.ITEMS_PER_PARTITION;
    DistributionSummary.builder(meterDoc.getName())
        .description(meterDoc.getDescription())
        .tag(BatchOperationKeyNames.BATCH_OPERATION_KEY.asString(), String.valueOf(batchOperationKey))
        .tag(BatchOperationKeyNames.PARTITION_ID.asString(), String.valueOf(partitionId))
        .tag(BatchOperationKeyNames.BATCH_OPERATION_TYPE.asString(), batchOperationType.toString())
        .tag(BatchOperationKeyNames.ORGANIZATION_ID.asString(), ORGANIZATION_ID)
        .serviceLevelObjectives(
            1, 2, 5, 10, 20, 50, 100, 200, 500, 1_000, 2_000, 5_000, 10_000, 20_000, 50_000,
            100_000, 500_000, 1_000_000, 2_000_000, 5_000_000, 10_000_000)
        .register(registry)
        .record(itemsAmount);
  }

  private void batchOperationEvent(
      final BatchOperationAction action, final BatchOperationType batchOperationType) {
    executedActions
        .computeIfAbsent(
            action,
            (key) -> registerBatchOperationEventCounter(key, batchOperationType))
        .increment();
  }

  private Counter registerBatchOperationEventCounter(
      final BatchOperationAction batchOperationAction,
      final BatchOperationType batchOperationType) {
    final var meterDoc = BatchOperationMetricsDoc.EXECUTED_EVENTS;
    return Counter.builder(meterDoc.getName())
        .description(meterDoc.getDescription())
        .tag(BatchOperationKeyNames.ACTION.asString(), batchOperationAction.toString())
        .tag(BatchOperationKeyNames.BATCH_OPERATION_TYPE.asString(), batchOperationType.toString())
        .tag(BatchOperationKeyNames.ORGANIZATION_ID.asString(), ORGANIZATION_ID)
        .register(registry);
  }

}
