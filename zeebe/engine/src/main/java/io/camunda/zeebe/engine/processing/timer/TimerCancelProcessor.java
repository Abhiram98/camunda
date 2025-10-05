/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Camunda License 1.0. You may not use this file
 * except in compliance with the Camunda License 1.0.
 */
package io.camunda.zeebe.engine.processing.timer;

import io.camunda.zeebe.engine.processing.ExcludeAuthorizationCheck;
import io.camunda.zeebe.engine.processing.streamprocessor.TypedRecordProcessor;
import io.camunda.zeebe.engine.processing.streamprocessor.writers.StateWriter;
import io.camunda.zeebe.engine.processing.streamprocessor.writers.TypedRejectionWriter;
import io.camunda.zeebe.engine.state.immutable.TimerInstanceState;
import io.camunda.zeebe.engine.state.instance.TimerInstance;
import io.camunda.zeebe.protocol.impl.record.value.timer.TimerRecord;
import io.camunda.zeebe.protocol.record.RejectionType;
import io.camunda.zeebe.protocol.record.intent.TimerIntent;
import io.camunda.zeebe.stream.api.records.TypedRecord;

@ExcludeAuthorizationCheck
public final class TimerCancelProcessor implements TypedRecordProcessor<TimerRecord> {
  public static final String NO_TIMER_FOUND_MESSAGE =
      "Expected to cancel timer with key '%d', but no such timer was found";

  private final TimerInstanceState timerInstanceState;
  private final StateWriter stateWriter;
  private final TypedRejectionWriter rejectionWriter;

  public TimerCancelProcessor(
      final TimerInstanceState timerInstanceState,
      final StateWriter stateWriter,
      final TypedRejectionWriter rejectionWriter) {
    this.timerInstanceState = timerInstanceState;
    this.stateWriter = stateWriter;
    this.rejectionWriter = rejectionWriter;
  }

  @Override
  public void processRecord(final TypedRecord<TimerRecord> usageMetricRecord) {
    final TimerRecord timer = usageMetricRecord.getValue();
    final TimerInstance timerInstance =
        timerInstanceState.get(timer.getElementInstanceKey(), usageMetricRecord.getKey());

    if (timerInstance == null) {
      rejectionWriter.appendRejection(
          usageMetricRecord, RejectionType.NOT_FOUND, String.format(NO_TIMER_FOUND_MESSAGE, usageMetricRecord.getKey()));
    } else {
      stateWriter.appendFollowUpEvent(usageMetricRecord.getKey(), TimerIntent.CANCELED, timer);
    }
  }
}
