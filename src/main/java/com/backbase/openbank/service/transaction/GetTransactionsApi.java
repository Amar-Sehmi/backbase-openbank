package com.backbase.openbank.service.transaction;

import com.backbase.openbank.service.domain.openbank.OpenbankGetTransactionResponse;
import com.backbase.openbank.service.exception.BackbaseException;
import com.backbase.openbank.service.util.ObjectMapperUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GetTransactionsApi {

    private static final Logger LOG = LogManager.getLogger(GetTransactionsApi.class);

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private HttpGet httpGet;

    public OpenbankGetTransactionResponse getTransactions() throws BackbaseException {
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpGet);
            if (isSuccess(httpResponse)) {
                return ObjectMapperUtil.getInstance().readValue(httpResponse.getEntity().getContent(), OpenbankGetTransactionResponse.class);
            }
        } catch (Exception e) {
            LOG.error("Failed to retrieve transactions from OpenBank API", e);
            throw new BackbaseException("Failure to invoke OpenBank Transaction API: " + e.getMessage());
        }

        throw getException(httpResponse);
    }

    private static boolean isSuccess(HttpResponse httpResponse) {
        return httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.OK.value();
    }

    private static BackbaseException getException(HttpResponse httpResponse) {
        if (httpResponse == null) {
            return new BackbaseException("Failed to retrieve OpenBank Transactions - Reason: UNKNOWN");
        }

        return new BackbaseException(
                HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode()),
                httpResponse.getStatusLine().getReasonPhrase());
    }
}
