sqlplus developer/D3vVV0Rd@127.0.0.1:1521/DEV.BCDSS @..\sql\clearContention.sql
py aggregateContention.py
sqlplus developer/D3vVV0Rd@127.0.0.1:1521/DEV.BCDSS @..\sql\clearDecision.sql
py aggregateDecision.py
sqlplus developer/D3vVV0Rd@127.0.0.1:1521/DEV.BCDSS @..\sql\clearFeatureVector.sql
py createTable.py