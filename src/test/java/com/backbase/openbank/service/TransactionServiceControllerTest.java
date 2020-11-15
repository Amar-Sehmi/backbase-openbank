package com.backbase.openbank.service;

import com.backbase.openbank.service.controller.TransactionServiceController;
import com.backbase.openbank.service.domain.backbase.BackbaseGetTransactionsResponse;
import com.backbase.openbank.service.domain.openbank.OpenbankGetTransactionResponse;
import com.backbase.openbank.service.domain.openbank.Transaction;
import com.backbase.openbank.service.transaction.GetTransactionsApi;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RunWith(JMockit.class)
public class TransactionServiceControllerTest {

    @Tested
    TransactionServiceController controller;

    @Injectable
    GetTransactionsApi getTransactionsApi;

    private OpenbankGetTransactionResponse openbankGetTransactionResponse;

    @Before
    public void setUp() {
        openbankGetTransactionResponse = new OpenbankGetTransactionResponse();

        Transaction transaction1 = new Transaction();
        transaction1.setId("transaction_id_1");
        transaction1.getDetails().setType("transaction_type_1");
        transaction1.getDetails().getValue().setAmount(20.25f);

        Transaction transaction2 = new Transaction();
        transaction2.setId("transaction_id_2");
        transaction2.getDetails().setType("transaction_type_2");
        transaction2.getDetails().getValue().setAmount(10.1f);

        openbankGetTransactionResponse.getTransactions().add(transaction1);
        openbankGetTransactionResponse.getTransactions().add(transaction2);
    }

    @Test
    public void testGetTransactions_All() throws Exception {

        new Expectations() {{
            getTransactionsApi.getTransactions();
            result = openbankGetTransactionResponse;
        }};

        ResponseEntity<BackbaseGetTransactionsResponse> responseEntity = null;

        try {
            responseEntity = controller.getTransactions(null);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("should not have thrown exception");
        }

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        BackbaseGetTransactionsResponse response = responseEntity.getBody();
        Assert.assertEquals(2, response.getCount());
        Assert.assertEquals("transaction_id_1", response.getTransactions().get(0).getId());
        Assert.assertEquals("transaction_id_2", response.getTransactions().get(1).getId());
    }

    @Test
    public void testGetTransactions_Type() throws Exception {

        new Expectations() {{
            getTransactionsApi.getTransactions();
            result = openbankGetTransactionResponse;
        }};

        ResponseEntity<BackbaseGetTransactionsResponse> responseEntity = null;

        try {
            responseEntity = controller.getTransactions("transaction_type_2");
        } catch (Exception e) {
            Assert.fail("should not have thrown exception");
        }

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        BackbaseGetTransactionsResponse response = responseEntity.getBody();
        Assert.assertEquals(1, response.getCount());
        Assert.assertEquals("transaction_id_2", response.getTransactions().get(0).getId());
    }

    @Test
    public void testGetTransactions_None() throws Exception {

        new Expectations() {{
            getTransactionsApi.getTransactions();
            result = openbankGetTransactionResponse;
        }};

        ResponseEntity<BackbaseGetTransactionsResponse> responseEntity = null;

        try {
            responseEntity = controller.getTransactions("blah");
        } catch (Exception e) {
            Assert.fail("should not have thrown exception");
        }

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        BackbaseGetTransactionsResponse response = responseEntity.getBody();
        Assert.assertEquals(0, response.getCount());
        Assert.assertEquals(0, response.getTransactions().size());
    }

    @Test
    public void testGetAmount_All() throws Exception {

        new Expectations() {{
            getTransactionsApi.getTransactions();
            result = openbankGetTransactionResponse;
        }};

        ResponseEntity<Map<String, Float>> responseEntity = null;

        try {
            responseEntity = controller.getTransactionAmount(null);
        } catch (Exception e) {
            Assert.fail("should not have thrown exception");
        }

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Map<String, Float> response = responseEntity.getBody();
        Assert.assertEquals(1, response.size());
        Assert.assertEquals(30.35f, response.get("transactionAmount"), 0);
    }


    @Test
    public void testGetAmount_Type() throws Exception {

        new Expectations() {{
            getTransactionsApi.getTransactions();
            result = openbankGetTransactionResponse;
        }};

        ResponseEntity<Map<String, Float>> responseEntity = null;

        try {
            responseEntity = controller.getTransactionAmount("transaction_type_2");
        } catch (Exception e) {
            Assert.fail("should not have thrown exception");
        }

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Map<String, Float> response = responseEntity.getBody();
        Assert.assertEquals(1, response.size());
        Assert.assertEquals(10.1f, response.get("transactionAmount"), 0);
    }

    @Test
    public void testGetAmount_None() throws Exception {

        new Expectations() {{
            getTransactionsApi.getTransactions();
            result = openbankGetTransactionResponse;
        }};

        ResponseEntity<Map<String, Float>> responseEntity = null;

        try {
            responseEntity = controller.getTransactionAmount("blah");
        } catch (Exception e) {
            Assert.fail("should not have thrown exception");
        }

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Map<String, Float> response = responseEntity.getBody();
        Assert.assertEquals(1, response.size());
        Assert.assertEquals(0f, response.get("transactionAmount"), 0);
    }

}
