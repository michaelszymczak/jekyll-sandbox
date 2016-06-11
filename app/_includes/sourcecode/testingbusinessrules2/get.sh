#!/bin/bash
#echo ${BASH_SOURCE}
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

revision="https://raw.githubusercontent.com/michaelszymczak/blog-testing-business-rules-example/bfee066df1a21e3343ff6e0e8441a2cde7f10688"

curl -o ${DIR}/CancellingDepositFixture.java ${revision}/src/test/java/com/michaelszymczak/blog/testingbusinessrules2/CancellingDepositFixture.java
curl -o ${DIR}/CancellingDeposit.md ${revision}/src/test/resources/com/michaelszymczak/blog/testingbusinessrules2/CancellingDeposit.md
curl -o ${DIR}/CancellingDepositReport-instrumented.html ${revision}//docs/CancellingDepositReport-instrumented.html

