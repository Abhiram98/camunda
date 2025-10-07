/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Camunda License 1.0. You may not use this file
 * except in compliance with the Camunda License 1.0.
 */
package io.camunda.zeebe.backup.processing.state;

import io.camunda.zeebe.db.ColumnFamily;
import io.camunda.zeebe.db.TransactionContext;
import io.camunda.zeebe.db.ZeebeDb;
import io.camunda.zeebe.db.impl.DbString;
import io.camunda.zeebe.protocol.ZbColumnFamilies;

public final class DbLatestCheckpointState implements LatestCheckpointState {

  private static final String LATEST_CHECKPOINT_KEY = "checkpoint";

  private final LatestCheckpointInfo latestCheckpointInfo = new LatestCheckpointInfo();
  private final ColumnFamily<DbString, LatestCheckpointInfo> latestCheckpointColumnFamily;
  private final DbString latestCheckpointInfoKey;

  public DbLatestCheckpointState(
      final ZeebeDb<ZbColumnFamilies> zeebeDb, final TransactionContext transactionContext) {
    latestCheckpointInfoKey = new DbString();
    latestCheckpointInfoKey.wrapString(LATEST_CHECKPOINT_KEY);
    latestCheckpointColumnFamily =
        zeebeDb.createColumnFamily(
            ZbColumnFamilies.DEFAULT, transactionContext, latestCheckpointInfoKey, latestCheckpointInfo);
  }

  @Override
  public long getLatestCheckpointId() {
    final LatestCheckpointInfo info = latestCheckpointColumnFamily.get(latestCheckpointInfoKey);
    return info != null ? info.getId() : NO_CHECKPOINT;
  }

  @Override
  public long getLatestCheckpointPosition() {
    final LatestCheckpointInfo info = latestCheckpointColumnFamily.get(latestCheckpointInfoKey);
    return info != null ? info.getPosition() : NO_CHECKPOINT;
  }

  @Override
  public void setLatestCheckpointInfo(final long latestCheckpointId, final long latestCheckpointPosition) {
    latestCheckpointInfo.setId(latestCheckpointId).setPosition(latestCheckpointPosition);
    latestCheckpointColumnFamily.upsert(latestCheckpointInfoKey, latestCheckpointInfo);
  }
}
