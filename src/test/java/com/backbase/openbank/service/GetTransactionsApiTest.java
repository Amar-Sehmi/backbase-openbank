package com.backbase.openbank.service;

import com.backbase.openbank.service.domain.openbank.OpenbankGetTransactionResponse;
import com.backbase.openbank.service.exception.BackbaseException;
import com.backbase.openbank.service.transaction.GetTransactionsApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;

import java.io.InputStream;

@RunWith(JMockit.class)
public class GetTransactionsApiTest {

    @Tested
    GetTransactionsApi getTransactionsApi;

    @Injectable
    HttpClient httpClient;

    @Injectable
    HttpGet httpGet;

    @Injectable
    HttpResponse httpResponse;

    @Mocked
    ObjectMapper objectMapper;

    @Test
    public void testGetTransactions() throws Exception {
        new Expectations() {{
            httpClient.execute(httpGet);
            result = httpResponse;

            httpResponse.getStatusLine().getStatusCode();
            result = HttpStatus.OK.value();

            objectMapper.readValue((InputStream) any, OpenbankGetTransactionResponse.class);
            result = null;
        }};

        try {
            getTransactionsApi.getTransactions();
        } catch (BackbaseException be) {
            Assert.fail("should not have thrown exception");
        }
    }

    @Test
    public void testGetTransactions_Failure_Response() throws Exception {
        new Expectations() {{
            httpClient.execute(httpGet);
            result = httpResponse;

            httpResponse.getStatusLine().getStatusCode();
            result = HttpStatus.BAD_REQUEST.value();

            httpResponse.getStatusLine().getReasonPhrase();
            result = "some error";
        }};

        try {
            getTransactionsApi.getTransactions();
            Assert.fail("should have thrown exception");
        } catch (BackbaseException be) {
            Assert.assertEquals("some error", be.getMessage());
        }
    }

    @Test
    public void testGetTransactions_Null_Response() throws Exception {
        new Expectations() {{
            httpClient.execute(httpGet);
            result = null;
        }};

        try {
            getTransactionsApi.getTransactions();
            Assert.fail("should have thrown exception");
        } catch (BackbaseException be) {
            Assert.assertEquals("Failed to retrieve OpenBank Transactions - Reason: UNKNOWN", be.getMessage());
        }
    }

    @Test
    public void testGetTransactions_Exception() throws Exception {
        new Expectations() {{
            httpClient.execute(httpGet);
            result = new Exception("blah");
        }};

        try {
            getTransactionsApi.getTransactions();
            Assert.fail("should have thrown exception");
        } catch (BackbaseException be) {
            Assert.assertEquals("Failure to invoke OpenBank Transaction API: blah", be.getMessage());
        }
    }

}
