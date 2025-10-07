/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Camunda License 1.0. You may not use this file
 * except in compliance with the Camunda License 1.0.
 */
package io.camunda.zeebe.gateway.rest.controller.usermanagement;

import static io.camunda.zeebe.gateway.rest.RestErrorMapper.mapErrorToResponse;

import io.camunda.search.query.MappingQuery;
import io.camunda.security.auth.CamundaAuthenticationProvider;
import io.camunda.service.MappingServices;
import io.camunda.service.MappingServices.MappingDTO;
import io.camunda.zeebe.gateway.protocol.rest.MappingResult;
import io.camunda.zeebe.gateway.protocol.rest.MappingRuleCreateRequest;
import io.camunda.zeebe.gateway.protocol.rest.MappingRuleUpdateRequest;
import io.camunda.zeebe.gateway.protocol.rest.MappingSearchQueryRequest;
import io.camunda.zeebe.gateway.protocol.rest.MappingSearchQueryResult;
import io.camunda.zeebe.gateway.rest.RequestMapper;
import io.camunda.zeebe.gateway.rest.ResponseMapper;
import io.camunda.zeebe.gateway.rest.RestErrorMapper;
import io.camunda.zeebe.gateway.rest.SearchQueryRequestMapper;
import io.camunda.zeebe.gateway.rest.SearchQueryResponseMapper;
import io.camunda.zeebe.gateway.rest.annotation.CamundaDeleteMapping;
import io.camunda.zeebe.gateway.rest.annotation.CamundaGetMapping;
import io.camunda.zeebe.gateway.rest.annotation.CamundaPostMapping;
import io.camunda.zeebe.gateway.rest.annotation.CamundaPutMapping;
import io.camunda.zeebe.gateway.rest.controller.CamundaRestController;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CamundaRestController
@RequestMapping("/v2/mapping-rules")
public class MappingRuleController {

  private final MappingServices mappingRuleServices;
  private final CamundaAuthenticationProvider authenticationProvider;

  public MappingRuleController(
      final MappingServices mappingRuleServices,
      final CamundaAuthenticationProvider authenticationProvider) {
    this.mappingRuleServices = mappingRuleServices;
    this.authenticationProvider = authenticationProvider;
  }

  @CamundaPostMapping
  public CompletableFuture<ResponseEntity<Object>> create(
      @RequestBody final MappingRuleCreateRequest mappingRuleRequest) {
    return RequestMapper.toMappingDTO(mappingRuleRequest)
        .fold(RestErrorMapper::mapProblemToCompletedResponse, this::createMappingRule);
  }

  @CamundaPutMapping(path = "/{mappingId}")
  public CompletableFuture<ResponseEntity<Object>> update(
      @PathVariable final String mappingRuleId,
      @RequestBody final MappingRuleUpdateRequest mappingRuleRequest) {
    return RequestMapper.toMappingDTO(mappingRuleId, mappingRuleRequest)
        .fold(RestErrorMapper::mapProblemToCompletedResponse, this::updateMappingRule);
  }

  @CamundaDeleteMapping(path = "/{mappingId}")
  public CompletableFuture<ResponseEntity<Object>> deleteMappingRule(
      @PathVariable final String mappingRuleId) {
    return RequestMapper.executeServiceMethodWithNoContentResult(
        () ->
            mappingRuleServices
                .withAuthentication(authenticationProvider.getCamundaAuthentication())
                .deleteMappingRule(mappingRuleId));
  }

  @CamundaGetMapping(path = "/{mappingId}")
  public ResponseEntity<MappingResult> getMappingRule(@PathVariable final String mappingRuleId) {
    try {
      return ResponseEntity.ok()
          .body(
              SearchQueryResponseMapper.toMapping(
                  mappingRuleServices
                      .withAuthentication(authenticationProvider.getCamundaAuthentication())
                      .getMappingRule(mappingRuleId)));
    } catch (final Exception exception) {
      return RestErrorMapper.mapErrorToResponse(exception);
    }
  }

  @CamundaPostMapping(path = "/search")
  public ResponseEntity<MappingSearchQueryResult> searchMappingRules(
      @RequestBody(required = false) final MappingSearchQueryRequest query) {
    return SearchQueryRequestMapper.toMappingQuery(query)
        .fold(RestErrorMapper::mapProblemToResponse, this::search);
  }

  private CompletableFuture<ResponseEntity<Object>> createMappingRule(final MappingDTO request) {
    return RequestMapper.executeServiceMethod(
        () ->
            mappingRuleServices
                .withAuthentication(authenticationProvider.getCamundaAuthentication())
                .createMappingRule(request),
        ResponseMapper::toMappingCreateResponse);
  }

  private CompletableFuture<ResponseEntity<Object>> updateMappingRule(final MappingDTO request) {
    return RequestMapper.executeServiceMethod(
        () ->
            mappingRuleServices
                .withAuthentication(authenticationProvider.getCamundaAuthentication())
                .updateMappingRule(request),
        ResponseMapper::toMappingUpdateResponse);
  }

  private ResponseEntity<MappingSearchQueryResult> search(final MappingQuery query) {
    try {
      final var result =
          mappingRuleServices
              .withAuthentication(authenticationProvider.getCamundaAuthentication())
              .search(query);
      return ResponseEntity.ok(SearchQueryResponseMapper.toMappingSearchQueryResponse(result));
    } catch (final Exception e) {
      return mapErrorToResponse(e);
    }
  }
}
