/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Camunda License 1.0. You may not use this file
 * except in compliance with the Camunda License 1.0.
 */
package io.camunda.search.filter;

import static io.camunda.util.CollectionUtil.addOperationsToList;
import static io.camunda.util.CollectionUtil.collectOperations;

import io.camunda.util.ObjectBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record BatchOperationFilter(
    List<String> batchOperationIds, List<String> operationTypes, List<String> state)
    implements FilterBase {

  public static final class Builder implements ObjectBuilder<BatchOperationFilter> {

    private List<String> batchOperationIds;
    private List<String> operationTypes;
    private List<String> state;

    public Builder batchOperationIds(final String value, final String... operations) {
      return batchOperationIds(collectOperations(value, operations));
    }

    public Builder batchOperationIds(final List<String> operations) {
      batchOperationIds = addOperationsToList(batchOperationIds, operations);
      return this;
    }

    public Builder operationTypes(final String value, final String... operations) {
      return operationTypes(collectOperations(value, operations));
    }

    public Builder operationTypes(final List<String> operations) {
      operationTypes = addOperationsToList(operationTypes, operations);
      return this;
    }

    public Builder state(final String value, final String... operations) {
      return state(collectOperations(value, operations));
    }

    public Builder state(final List<String> operations) {
      state = addOperationsToList(state, operations);
      return this;
    }

    @Override
    public BatchOperationFilter build() {
      return new BatchOperationFilter(
          Objects.requireNonNullElse(batchOperationIds, Collections.emptyList()),
          Objects.requireNonNullElse(operationTypes, Collections.emptyList()),
          Objects.requireNonNullElse(state, Collections.emptyList()));
    }
  }
}
