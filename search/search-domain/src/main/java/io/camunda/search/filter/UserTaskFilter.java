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
import static io.camunda.util.CollectionUtil.collectValuesAsList;

import io.camunda.util.FilterUtil;
import io.camunda.util.ObjectBuilder;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record UserTaskFilter(
    List<Long> userTaskKeys,
    List<String> elementIds,
    List<String> bpmnProcessIds,
    List<Operation<String>> assigneeOperations,
    List<Operation<Integer>> priorityOperations,
    List<String> states,
    List<Long> processInstanceKeys,
    List<Long> processDefinitionKeys,
    List<Operation<String>> candidateUserOperations,
    List<Operation<String>> candidateGroupOperations,
    List<String> tenantIds,
    List<VariableValueFilter> processInstanceVariableFilter,
    List<VariableValueFilter> localVariableFilters,
    List<Long> elementInstanceKeys,
    List<Operation<OffsetDateTime>> creationDateOperations,
    List<Operation<OffsetDateTime>> completionDateOperations,
    List<Operation<OffsetDateTime>> followUpDateOperations,
    List<Operation<OffsetDateTime>> dueDateOperations,
    String type)
    implements FilterBase {

  public static final class Builder implements ObjectBuilder<UserTaskFilter> {

    private List<Long> userTaskKeys;
    private List<String> elementIds;
    private List<String> bpmnProcessIds;
    private List<Operation<String>> assigneeOperations;
    private List<Operation<Integer>> priorityOperations;
    private List<String> states;
    private List<Long> processInstanceKeys;
    private List<Long> processDefinitionKeys;
    private List<Operation<String>> candidateUserOperations;
    private List<Operation<String>> candidateGroupOperations;
    private List<String> tenantIds;
    private List<VariableValueFilter> processInstanceVariableFilters;
    private List<VariableValueFilter> localVariableFilters;
    private List<Long> elementInstanceKeys;
    private List<Operation<OffsetDateTime>> creationDateOperations;
    private List<Operation<OffsetDateTime>> completionDateOperations;
    private List<Operation<OffsetDateTime>> followUpDateOperations;
    private List<Operation<OffsetDateTime>> dueDateOperations;
    private String type;

    public Builder userTaskKeys(final Long... values) {
      return userTaskKeys(collectValuesAsList(values));
    }

    public Builder userTaskKeys(final List<Long> values) {
      userTaskKeys = addOperationsToList(userTaskKeys, values);
      return this;
    }

    public Builder elementIds(final String... values) {
      return elementIds(collectValuesAsList(values));
    }

    public Builder elementIds(final List<String> values) {
      elementIds = addOperationsToList(elementIds, values);
      return this;
    }

    public Builder bpmnProcessIds(final String... values) {
      return bpmnProcessIds(collectValuesAsList(values));
    }

    public Builder bpmnProcessIds(final List<String> values) {
      bpmnProcessIds = addOperationsToList(bpmnProcessIds, values);
      return this;
    }

    public Builder assigneeOperations(final List<Operation<String>> operations) {
      assigneeOperations = addOperationsToList(assigneeOperations, operations);
      return this;
    }

    public Builder assignees(final String value, final String... values) {
      return assigneeOperations(FilterUtil.mapDefaultToOperation(value, values));
    }

    @SafeVarargs
    public final Builder assigneeOperations(
        final Operation<String> operation, final Operation<String>... operations) {
      return assigneeOperations(collectOperations(operation, operations));
    }

    public Builder priorityOperations(final List<Operation<Integer>> operations) {
      priorityOperations = addOperationsToList(priorityOperations, operations);
      return this;
    }

    public Builder priorities(final Integer value, final Integer... values) {
      return priorityOperations(FilterUtil.mapDefaultToOperation(value, values));
    }

    @SafeVarargs
    public final Builder priorityOperations(
        final Operation<Integer> operation, final Operation<Integer>... operations) {
      return priorityOperations(collectOperations(operation, operations));
    }

    public Builder states(final String... values) {
      return states(collectValuesAsList(values));
    }

    public Builder states(final List<String> values) {
      states = addOperationsToList(states, values);
      return this;
    }

    public Builder processInstanceKeys(final Long... values) {
      return processInstanceKeys(collectValuesAsList(values));
    }

    public Builder processInstanceKeys(final List<Long> values) {
      processInstanceKeys = addOperationsToList(processInstanceKeys, values);
      return this;
    }

    public Builder processDefinitionKeys(final Long... values) {
      return processDefinitionKeys(collectValuesAsList(values));
    }

    public Builder processDefinitionKeys(final List<Long> values) {
      processDefinitionKeys = addOperationsToList(processDefinitionKeys, values);
      return this;
    }

    public Builder candidateUserOperations(final List<Operation<String>> operations) {
      candidateUserOperations = addOperationsToList(candidateUserOperations, operations);
      return this;
    }

    public Builder candidateUsers(final String value, final String... values) {
      return candidateUserOperations(FilterUtil.mapDefaultToOperation(value, values));
    }

    @SafeVarargs
    public final Builder candidateUserOperations(
        final Operation<String> operation, final Operation<String>... operations) {
      return candidateUserOperations(collectOperations(operation, operations));
    }

    public Builder candidateGroupOperations(final List<Operation<String>> operations) {
      candidateGroupOperations = addOperationsToList(candidateGroupOperations, operations);
      return this;
    }

    public Builder candidateGroups(final String value, final String... values) {
      return candidateGroupOperations(FilterUtil.mapDefaultToOperation(value, values));
    }

    @SafeVarargs
    public final Builder candidateGroupOperations(
        final Operation<String> operation, final Operation<String>... operations) {
      return candidateGroupOperations(collectOperations(operation, operations));
    }

    public Builder tenantIds(final String... values) {
      return tenantIds(collectValuesAsList(values));
    }

    public Builder tenantIds(final List<String> values) {
      tenantIds = addOperationsToList(tenantIds, values);
      return this;
    }

    public Builder processInstanceVariables(final List<VariableValueFilter> values) {
      processInstanceVariableFilters = addOperationsToList(processInstanceVariableFilters, values);
      return this;
    }

    public Builder localVariables(final List<VariableValueFilter> values) {
      localVariableFilters = addOperationsToList(localVariableFilters, values);
      return this;
    }

    public Builder elementInstanceKeys(final Long... values) {
      return elementInstanceKeys(collectValuesAsList(values));
    }

    public Builder elementInstanceKeys(final List<Long> values) {
      elementInstanceKeys = addOperationsToList(elementInstanceKeys, values);
      return this;
    }

    public Builder creationDateOperations(final List<Operation<OffsetDateTime>> operations) {
      creationDateOperations = addOperationsToList(creationDateOperations, operations);
      return this;
    }

    @SafeVarargs
    public final Builder creationDateOperations(
        final Operation<OffsetDateTime> operation, final Operation<OffsetDateTime>... operations) {
      return creationDateOperations(collectOperations(operation, operations));
    }

    public Builder completionDateOperations(final List<Operation<OffsetDateTime>> operations) {
      completionDateOperations = addOperationsToList(completionDateOperations, operations);
      return this;
    }

    @SafeVarargs
    public final Builder completionDateOperations(
        final Operation<OffsetDateTime> operation, final Operation<OffsetDateTime>... operations) {
      return completionDateOperations(collectOperations(operation, operations));
    }

    public Builder followUpDateOperations(final List<Operation<OffsetDateTime>> operations) {
      followUpDateOperations = addOperationsToList(followUpDateOperations, operations);
      return this;
    }

    @SafeVarargs
    public final Builder followUpDateOperations(
        final Operation<OffsetDateTime> operation, final Operation<OffsetDateTime>... operations) {
      return followUpDateOperations(collectOperations(operation, operations));
    }

    public Builder dueDateOperations(final List<Operation<OffsetDateTime>> operations) {
      dueDateOperations = addOperationsToList(dueDateOperations, operations);
      return this;
    }

    @SafeVarargs
    public final Builder dueDateOperations(
        final Operation<OffsetDateTime> operation, final Operation<OffsetDateTime>... operations) {
      return dueDateOperations(collectOperations(operation, operations));
    }

    public Builder type(final String value) {
      type = value;
      return this;
    }

    @Override
    public UserTaskFilter build() {
      return new UserTaskFilter(
          Objects.requireNonNullElse(userTaskKeys, Collections.emptyList()),
          Objects.requireNonNullElse(elementIds, Collections.emptyList()),
          Objects.requireNonNullElse(bpmnProcessIds, Collections.emptyList()),
          Objects.requireNonNullElse(assigneeOperations, Collections.emptyList()),
          Objects.requireNonNullElse(priorityOperations, Collections.emptyList()),
          Objects.requireNonNullElse(states, Collections.emptyList()),
          Objects.requireNonNullElse(processInstanceKeys, Collections.emptyList()),
          Objects.requireNonNullElse(processDefinitionKeys, Collections.emptyList()),
          Objects.requireNonNullElse(candidateUserOperations, Collections.emptyList()),
          Objects.requireNonNullElse(candidateGroupOperations, Collections.emptyList()),
          Objects.requireNonNullElse(tenantIds, Collections.emptyList()),
          Objects.requireNonNullElse(processInstanceVariableFilters, Collections.emptyList()),
          Objects.requireNonNullElse(localVariableFilters, Collections.emptyList()),
          Objects.requireNonNullElse(elementInstanceKeys, Collections.emptyList()),
          Objects.requireNonNullElse(creationDateOperations, Collections.emptyList()),
          Objects.requireNonNullElse(completionDateOperations, Collections.emptyList()),
          Objects.requireNonNullElse(followUpDateOperations, Collections.emptyList()),
          Objects.requireNonNullElse(dueDateOperations, Collections.emptyList()),
          type);
    }
  }
}
