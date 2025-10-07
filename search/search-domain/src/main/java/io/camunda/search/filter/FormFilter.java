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

public record FormFilter(List<Long> formKeys, List<String> formIds) implements FilterBase {

  public static final class Builder implements ObjectBuilder<FormFilter> {

    private List<Long> formKeys;
    private List<String> formIds;

    public Builder formKeys(final Long value, final Long... values) {
      return formKeys(collectOperations(value, values));
    }

    public Builder formKeys(final List<Long> values) {
      formKeys = addOperationsToList(formKeys, values);
      return this;
    }

    public Builder formIds(final String value, final String... values) {
      return formIds(collectOperations(value, values));
    }

    public Builder formIds(final List<String> values) {
      formIds = addOperationsToList(formIds, values);
      return this;
    }

    @Override
    public FormFilter build() {
      return new FormFilter(
          Objects.requireNonNullElse(formKeys, Collections.emptyList()),
          Objects.requireNonNullElse(formIds, Collections.emptyList()));
    }
  }
}
