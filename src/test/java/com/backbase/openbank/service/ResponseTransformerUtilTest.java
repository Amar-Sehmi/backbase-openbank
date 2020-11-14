package com.backbase.openbank.service;

import com.backbase.openbank.service.domain.backbase.BackbaseGetTransactionsResponse;
import com.backbase.openbank.service.domain.backbase.BackbaseTransaction;
import com.backbase.openbank.service.domain.openbank.OpenbankGetTransactionResponse;
import com.backbase.openbank.service.domain.openbank.Transaction;
import com.backbase.openbank.service.util.ResponseTransformerUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResponseTransformerUtilTest {

    private OpenbankGetTransactionResponse openbankGetTransactionResponse;

    @Before
    public void setUp() {
        openbankGetTransactionResponse = new OpenbankGetTransactionResponse();

        Transaction transaction1 = new Transaction();
        transaction1.setId("transaction_id_1");
        transaction1.getDetails().setType("transaction_type_1");
        transaction1.getDetails().setDescription("description_1");
        transaction1.getDetails().getValue().setAmount(20.25f);
        transaction1.getDetails().getValue().setCurrency("USD");
        transaction1.getThis_account().setId("this_account_id_1");
        transaction1.getOther_account().setNumber("other_account_number_1");
        transaction1.getOther_account().getHolder().setName("other_account_holder_1");
        transaction1.getOther_account().getMetadata().setImage_URL("image_url_1");

        Transaction transaction2 = new Transaction();
        transaction2.setId("transaction_id_2");
        transaction2.getDetails().setType("transaction_type_2");
        transaction2.getDetails().getValue().setAmount(10.1f);

        openbankGetTransactionResponse.getTransactions().add(transaction1);
        openbankGetTransactionResponse.getTransactions().add(transaction2);
    }

    @Test
    public void testGetTransactionResponse() {
        BackbaseGetTransactionsResponse response =
                ResponseTransformerUtil.getTransactionResponse(openbankGetTransactionResponse, null);

        Assert.assertEquals(2, response.getCount());

        BackbaseTransaction transaction1 = response.getTransactions().get(0);
        Assert.assertEquals("transaction_id_1", transaction1.getId());
        Assert.assertEquals("transaction_type_1", transaction1.getTransactionType());
        Assert.assertEquals("description_1", transaction1.getDescription());
        Assert.assertEquals(20.25f, transaction1.getInstructedAmount(), 0);
        Assert.assertEquals("USD", transaction1.getInstructedCurrency());
        Assert.assertEquals(20.25f, transaction1.getTransactionAmount(), 0);
        Assert.assertEquals("USD", transaction1.getTransactionCurrency());
        Assert.assertEquals("this_account_id_1", transaction1.getAccountId());
        Assert.assertEquals("other_account_number_1", transaction1.getCounterpartyAccount());
        Assert.assertEquals("other_account_holder_1", transaction1.getCounterpartyName());
        Assert.assertEquals("image_url_1", transaction1.getCounterPartyLogoPath());

        BackbaseTransaction transaction2 = response.getTransactions().get(1);
        Assert.assertEquals("transaction_id_2", transaction2.getId());
        Assert.assertEquals("transaction_type_2", transaction2.getTransactionType());
        Assert.assertNull(transaction2.getDescription());
        Assert.assertEquals(10.1f, transaction2.getInstructedAmount(), 0);
        Assert.assertNull(transaction2.getInstructedCurrency());
        Assert.assertEquals(10.1f, transaction2.getTransactionAmount(), 0);
        Assert.assertNull(transaction2.getTransactionCurrency());
        Assert.assertNull(transaction2.getAccountId());
        Assert.assertNull(transaction2.getCounterpartyAccount());
        Assert.assertNull(transaction2.getCounterpartyName());
        Assert.assertNull(transaction2.getCounterPartyLogoPath());
    }

    @Test
    public void testGetTransactionResponse_Type() {
        BackbaseGetTransactionsResponse response =
                ResponseTransformerUtil.getTransactionResponse(openbankGetTransactionResponse, "transaction_type_1");

        Assert.assertEquals(1, response.getCount());

        BackbaseTransaction transaction1 = response.getTransactions().get(0);
        Assert.assertEquals("transaction_id_1", transaction1.getId());
        Assert.assertEquals("transaction_type_1", transaction1.getTransactionType());
        Assert.assertEquals("description_1", transaction1.getDescription());
        Assert.assertEquals(20.25f, transaction1.getInstructedAmount(), 0);
        Assert.assertEquals("USD", transaction1.getInstructedCurrency());
        Assert.assertEquals(20.25f, transaction1.getTransactionAmount(), 0);
        Assert.assertEquals("USD", transaction1.getTransactionCurrency());
        Assert.assertEquals("this_account_id_1", transaction1.getAccountId());
        Assert.assertEquals("other_account_number_1", transaction1.getCounterpartyAccount());
        Assert.assertEquals("other_account_holder_1", transaction1.getCounterpartyName());
        Assert.assertEquals("image_url_1", transaction1.getCounterPartyLogoPath());

        response =
                ResponseTransformerUtil.getTransactionResponse(openbankGetTransactionResponse, "transaction_type_2");

        Assert.assertEquals(1, response.getCount());

        BackbaseTransaction transaction2 = response.getTransactions().get(0);
        Assert.assertEquals("transaction_id_2", transaction2.getId());
        Assert.assertEquals("transaction_type_2", transaction2.getTransactionType());
        Assert.assertNull(transaction2.getDescription());
        Assert.assertEquals(10.1f, transaction2.getInstructedAmount(), 0);
        Assert.assertNull(transaction2.getInstructedCurrency());
        Assert.assertEquals(10.1f, transaction2.getTransactionAmount(), 0);
        Assert.assertNull(transaction2.getTransactionCurrency());
        Assert.assertNull(transaction2.getAccountId());
        Assert.assertNull(transaction2.getCounterpartyAccount());
        Assert.assertNull(transaction2.getCounterpartyName());
        Assert.assertNull(transaction2.getCounterPartyLogoPath());

        response =
                ResponseTransformerUtil.getTransactionResponse(openbankGetTransactionResponse, "blah");

        Assert.assertEquals(0, response.getCount());

    }

    @Test
    public void testGetTransactionAmount() {
        Assert.assertEquals(30.35f,
                ResponseTransformerUtil.getTransactionAmount(openbankGetTransactionResponse, null), 0);
    }

    @Test
    public void testGetTransactionAmount_Type() {
        Assert.assertEquals(10.1f,
                ResponseTransformerUtil.getTransactionAmount(openbankGetTransactionResponse, "transaction_type_2"), 0);
    }
}
