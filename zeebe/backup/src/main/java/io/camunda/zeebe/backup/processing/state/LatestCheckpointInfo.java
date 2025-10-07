/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Camunda License 1.0. You may not use this file
 * except in compliance with the Camunda License 1.0.
 */
package io.camunda.zeebe.backup.processing.state;

import io.camunda.zeebe.db.DbValue;
import io.camunda.zeebe.msgpack.UnpackedObject;
import io.camunda.zeebe.msgpack.property.LongProperty;

/** Checkpoint info stored in db in msgpack format. */
public final class LatestCheckpointInfo extends UnpackedObject implements DbValue {
  private final LongProperty idProperty = new LongProperty("id");
  private final LongProperty positionProperty = new LongProperty("position");

  public LatestCheckpointInfo() {
    super(2);
    declareProperty(idProperty).declareProperty(positionProperty);
  }

  public long getId() {
    return idProperty.getValue();
  }

  public LatestCheckpointInfo setId(final long id) {
    idProperty.setValue(id);
    return this;
  }

  public long getPosition() {
    return positionProperty.getValue();
  }

  public LatestCheckpointInfo setPosition(final long position) {
    positionProperty.setValue(position);
    return this;
  }
}
