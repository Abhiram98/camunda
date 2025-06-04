/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Camunda License 1.0. You may not use this file
 * except in compliance with the Camunda License 1.0.
 */
package io.camunda.zeebe.engine.processing.streamprocessor.writers;

import io.camunda.zeebe.protocol.record.RecordMetadataDecoder;
import io.camunda.zeebe.protocol.record.RecordValue;
import io.camunda.zeebe.protocol.record.intent.Intent;
import io.camunda.zeebe.stream.api.records.ExceededBatchRecordSizeException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/** This interface is supposed to replace TypedCommandWriter */
public interface TypedCommandWriter {

  /**
   * Append a new command to the result builder
   *
   * @param intent the intent of the command
   * @param value the record of the command
   * @throws ExceededBatchRecordSizeException if the appended command doesn't fit into the
   *     RecordBatch
   */
  void appendNewCommand(Intent intent, RecordValue value);

  /**
   * Append a follow up command to the result builder
   *
   * @param intent the intent of the command
   * @param value the record of the command
   * @throws ExceededBatchRecordSizeException if the appended command doesn't fit into the
   *     RecordBatch
   */
  void appendFollowUpCommand(long key, Intent intent, RecordValue value);

  /**
   * Append a follow up command to the result builder
   *
   * @param intent the intent of the command
   * @param value the record of the command
   * @param metadata the optional metadata for the command
   * @throws ExceededBatchRecordSizeException if the appended command doesn't fit into the
   *     RecordBatch
   */
  void appendFollowUpCommand(long key, Intent intent, RecordValue value, Metadata metadata);

  /**
   * @param commandLength the length of the command that will be written
   * @return true if a command of the given length can be written
   */
  boolean canWriteCommandOfLength(final int commandLength);

  record Metadata(long operationReference, long batchOperationKey, Map<String, Object> claims) {

    public static MetadataBuilder builder() {
      return new MetadataBuilder();
    }

    public static Metadata of(final Consumer<MetadataBuilder> consumer) {
      final MetadataBuilder builder = new MetadataBuilder();
      consumer.accept(builder);
      return builder.build();
    }

    public static class MetadataBuilder {
      private long operationReference = RecordMetadataDecoder.operationReferenceNullValue();
      private long batchOperationKey = RecordMetadataDecoder.batchOperationKeyNullValue();
      private Map<String, Object> claims = null;

      public MetadataBuilder operationReference(final long operationReference) {
        this.operationReference = operationReference;
        return this;
      }

      public MetadataBuilder batchOperationKey(final long batchOperationKey) {
        this.batchOperationKey = batchOperationKey;
        return this;
      }

      public MetadataBuilder claims(final Map<String, Object> claims) {
        this.claims = claims;
        return this;
      }

      public MetadataBuilder claim(final String key, final Object value) {
        if (claims == null) {
          claims = new HashMap<>();
        }

        claims.put(key, value);
        return this;
      }

      public Metadata build() {
        return new Metadata(operationReference, batchOperationKey, claims);
      }
    }
  }
}
