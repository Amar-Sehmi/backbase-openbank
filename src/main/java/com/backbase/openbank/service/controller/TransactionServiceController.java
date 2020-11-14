package com.backbase.openbank.service.controller;

import com.backbase.openbank.service.domain.backbase.BackbaseGetTransactionsResponse;
import com.backbase.openbank.service.exception.BackbaseException;
import com.backbase.openbank.service.transaction.GetTransactionsApi;
import com.backbase.openbank.service.util.ObjectMapperUtil;
import com.backbase.openbank.service.util.ResponseTransformerUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
@SuppressWarnings("unused")
public class TransactionServiceController {

    private static final Logger LOG = LogManager.getLogger(TransactionServiceController.class);

    @Autowired
    private GetTransactionsApi getTransactionsApi;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get Transactions",
            notes = "Gets all Transactions by default. If transaction type is provided, gets all Transactions for that type.")
    public ResponseEntity<BackbaseGetTransactionsResponse> getTransactions(
            @ApiParam(value = "Transaction Type", example = "SEPA")
            @RequestParam(value = "type", required = false)
                    String type) throws BackbaseException {
        String method = "getTransactions(" + type + "): ";
        LOG.info(method + "start...");

        BackbaseGetTransactionsResponse transactionResponse =
                ResponseTransformerUtil.getTransactionResponse(getTransactionsApi.getTransactions(), type);

        ResponseEntity<BackbaseGetTransactionsResponse> response = new ResponseEntity<>(transactionResponse, HttpStatus.OK);
        LOG.info(method + ObjectMapperUtil.convertResponseToJson(response));
        return response;
    }

    @GetMapping(value = "/amount", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get Transaction Amount",
            notes = "Gets Amount of all Transactions by default. If transaction type is provided, gets Amount of all Transactions for that type.")
    public ResponseEntity<Map<String, Float>> getTransactionAmount(
            @ApiParam(value = "Transaction Type", example = "SEPA")
            @RequestParam(value = "type", required = false)
                    String type) throws BackbaseException {
        String method = "getTransactions(" + type + "): ";
        LOG.info(method + "start...");

        Float amount = ResponseTransformerUtil.getTransactionAmount(getTransactionsApi.getTransactions(), type);

        Map<String, Float> amountResponse = new HashMap<>();
        amountResponse.put("transactionAmount", amount);
        ResponseEntity<Map<String, Float>> response = new ResponseEntity<>(amountResponse, HttpStatus.OK);
        LOG.info(method + ObjectMapperUtil.convertResponseToJson(response));
        return response;
    }

}
