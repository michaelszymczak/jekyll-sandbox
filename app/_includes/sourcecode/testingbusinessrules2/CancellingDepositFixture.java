package com.michaelszymczak.blog.testingbusinessrules2;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

@RunWith(ConcordionRunner.class)
public class CancellingDepositFixture {

    public Result cancel(int lengthInYears, int initialAmount, int interestRate, int cancelledAfterMonths, int cancelledAfterDays) {
        Result result = new Result();
        result.amountTransferred = "TO BE IMPLEMENTED";
        return result;
    }

    class Result {
        public String amountTransferred;
    }
}