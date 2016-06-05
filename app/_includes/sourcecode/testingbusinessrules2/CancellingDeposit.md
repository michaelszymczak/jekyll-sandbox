[cancel]: - "#result = cancel(#lengthInYears, #initialAmount, #interestRate, #months, #days)"
[length]: - "#lengthInYears"
[initial]: - "#initialAmount"
[interestrate]: - "#interestRate"
[months]: - "#months"
[days]: - "#days"
[transferred]:  - "?=#result.amountTransferred"

## Feature: A user cancels the deposit

A customer is able to cancel the deposit earlier. In such case
they will receive the full amount, but the interest may be
not as high as it would be if they did not cancel their deposit
prematurely.

## Scenario: Calculate the amount to be transferred after the cancellation

Given an X year(x) long time deposit

And the initial amount of Y pounds

And the interest rate of Z% a year

When I cancel the deposit after MONTHS months and DAYS days

Then I should have TRANSFERRED pounds transferred to my account

## Business rules:

### [One receives the same amount as deposited if the deposit cancelled during the first half](- "first half c:status=ExpectedToFail")

For [1][length] year long deposit with [100][initial] initial amount and [2][interestrate]% interest rate:

| [cancel][][MONTHS][months] | [DAYS][days] | [TRANSFERRED][transferred] ||
| :------------------------: | :----------: | :-------------------------: |
| 0                          | 7            | 100                         |
| 6                          | 0            | 100                         |
 
### [One receives the deposited amount plus half of the interest if the deposit cancelled after 6 months and within the first year](- "second half c:status=ExpectedToFail")

For [1][length] year long deposit with [100][initial] initial amount and [2][interestrate]% interest rate:

| [cancel][][MONTHS][months] | [DAYS][days] | [TRANSFERRED][transferred] ||
| :------------------------: | :----------: | :-------------------------: |
| 6                          | 1            | 101                         |
| 12                         | 0            | 101                         |

### [The deposit is automatically renewed if not cancelled](- "renewal c:status=ExpectedToFail")

For [1][length] year long deposit with [100][initial] initial amount and [2][interestrate]% interest rate:

| [cancel][][MONTHS][months] | [DAYS][days] | [TRANSFERRED][transferred] ||
| :------------------------: | :----------: | :-------------------------: |
| 12                         | 1            | 102                         |
| 18                         | 0            | 102                         |
| 18                         | 1            | 103                         |
| 24                         | 1            | 104                         |

### [One cannot cancel the deposit before 7th day](- "cannot cancel c:status=ExpectedToFail")

For [1][length] year long deposit with [100][initial] initial amount and [2][interestrate]% interest rate:

| [cancel][][MONTHS][months] | [DAYS][days] | [TRANSFERRED][transferred] ||
| :------------------------: | :----------: | :-------------------------: |
| 0                          | 0            | X                           |
| 0                          | 6            | X                           |

