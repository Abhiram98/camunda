/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Camunda License 1.0. You may not use this file
 * except in compliance with the Camunda License 1.0.
 */
package io.camunda.search.query;

import io.camunda.search.page.QueryPage;
import io.camunda.search.page.QueryPageBuilders;
import io.camunda.util.ObjectBuilder;
import java.util.Objects;
import java.util.function.Function;

public interface SearchQueryBase {

  QueryPage page();

  public abstract static class AbstractQueryBuilder<T extends AbstractQueryBuilder<T>> {

    private static final QueryPage DEFAULT_PAGE = QueryPage.of((b) -> b);

    private QueryPage page;

    protected abstract T self();

    protected QueryPage page() {
      return Objects.requireNonNullElse(page, DEFAULT_PAGE);
    }

    public T page(final QueryPage value) {
      page = value;
      return self();
    }

    public T page(final Function<QueryPage.Builder, ObjectBuilder<QueryPage>> fn) {
      return page(QueryPageBuilders.page(fn));
    }
  }
}
