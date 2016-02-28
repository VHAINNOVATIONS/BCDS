import os
import re
import cx_Oracle
import collections
import datetime

earDiagnosisCode = [6100,6200,6201,6202,6204,6205,6207,6209,6210,6211,6260]

#Primary query, Grab all processed contentions and order by particpant id and then claim date descending
SQL="select * \
	from AGGREGATE_CONTENTION \
	where vet_id < 11088698 \
	order by VET_ID,CLAIM_DATE"

	
class EarFeatureVector:
	def __init__(self):
		self.VET_ID = None
		self.CLAIM_ID = None
		self.DOB = 0
		self.END_PRODUCT_CODE = None
		self.RO_NUMBER = 0
		self.CLAIM_DATE = None
		self.PROFILE_DATE = None
		self.PROMULGATION_DATE = None
		self.RECENT_EAR_DATE = None
		self.CONTENTION_COUNT = 0
		self.EAR_CONTENTION_COUNT = 0
		self.PRIOR_CDD = 0
		self.PRIOR_EAR_CDD = 0
		self.CURR_CDD = 0
		self.CURR_EAR_CDD = 0
		self.AGE_OF_CLAIM = 0
		self.AGE_OF_EAR_CDD = 0
		self.AGE_OF_CDD = 0
		self.C2200 = 0
		self.C2210 = 0
		self.C2220 = 0
		self.C3140 = 0
		self.C3150 = 0
		self.C4130 = 0
		self.C4210 = 0
		self.C4700 = 0
		self.C4920 = 0
		self.C6850 = 0
		self.A6100 = 0
		self.A6200 = 0
		self.A6201 = 0
		self.A6202 = 0
		self.A6204 = 0
		self.A6205 = 0
		self.A6207 = 0
		self.A6209 = 0
		self.A6210 = 0
		self.A6211 = 0
		self.A6260 = 0

		self.CONTENTION_BILATER = 0		
		self.CONTENTION_LEFT = 0
		self.CONTENTION_HEARING = 0
		self.CONTENTION_LOSS = 0
		self.CONTENTION_RIGHT = 0
		self.CONTENTION_TINITU = 0		
		
		self.DECISION_BILATER = 0		
		self.DECISION_LEFT = 0
		self.DECISION_RIGHT = 0
		self.DECISION_HEARING = 0
		self.DECISION_LOSS = 0
		self.DECISION_TINITU = 0
		self.DECISION_CONDITION = 0
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))
		
		
class AggregateDecision:
	def __init__(self,VET_ID,PROFILE_DATE,PROMULGATION_DATE,RECENT_EAR_DATE,CDD,EAR_CDD,A6100,A6200,A6201,A6202,A6204,A6205,A6207,A6209,A6210,A6211,A6260,TXT_BILATER,TXT_LEFT,TXT_RIGHT,TXT_HEARING,TXT_LOSS,TXT_TINITU,TXT_CONDITION):
		self.VET_ID = VET_ID
		self.PROFILE_DATE = PROFILE_DATE
		self.PROMULGATION_DATE = PROMULGATION_DATE
		self.RECENT_EAR_DATE = RECENT_EAR_DATE
		self.CDD = CDD
		self.EAR_CDD = EAR_CDD
		self.A6100 = A6100
		self.A6200 = A6200
		self.A6201 = A6201
		self.A6202 = A6202
		self.A6204 = A6204
		self.A6205 = A6205
		self.A6207 = A6207
		self.A6209 = A6209
		self.A6210 = A6210
		self.A6211 = A6211
		self.A6260 = A6260
		self.TXT_BILATER = TXT_BILATER		
		self.TXT_LEFT = TXT_LEFT
		self.TXT_RIGHT = TXT_RIGHT
		self.TXT_HEARING = TXT_HEARING
		self.TXT_LOSS = TXT_LOSS
		self.TXT_TINITU = TXT_TINITU
		self.TXT_CONDITION = TXT_CONDITION
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))
		
class AggregateContention:
	def __init__(self,VET_ID,CLAIM_ID,DOB,END_PRODUCT_CODE,RO_NUMBER,CLAIM_DATE,MAX_PROFILE_DATE,CONTENTION_COUNT,EAR_CONTENTION_COUNT,C2200,C2210,C2220,C3140,C3150,C4130,C4210,C4700,C4920,C6850,TXT_BILATER,TXT_RIGHT,TXT_LEFT,TXT_HEARING,TXT_LOSS,TXT_TINITU):
		self.VET_ID = VET_ID
		self.CLAIM_ID = CLAIM_ID
		self.DOB = DOB
		self.END_PRODUCT_CODE = END_PRODUCT_CODE
		self.RO_NUMBER = RO_NUMBER
		self.CLAIM_DATE = CLAIM_DATE
		self.MAX_PROFILE_DATE = MAX_PROFILE_DATE
		self.CONTENTION_COUNT = CONTENTION_COUNT
		self.EAR_CONTENTION_COUNT = EAR_CONTENTION_COUNT
		self.C2200 = C2200
		self.C2210 = C2210
		self.C2220 = C2220
		self.C3140 = C3140
		self.C3150 = C3150
		self.C4130 = C4130
		self.C4210 = C4210
		self.C4700 = C4700
		self.C4920 = C4920
		self.C6850 = C6850
		self.TXT_BILATER = TXT_BILATER
		self.TXT_LEFT = TXT_LEFT
		self.TXT_RIGHT = TXT_RIGHT
		self.TXT_HEARING = TXT_HEARING
		self.TXT_LOSS = TXT_LOSS
		self.TXT_TINITU = TXT_TINITU
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))

print(str(datetime.datetime.now()))
connection = cx_Oracle.connect('developer/<pass>@127.0.0.1:1521/DEV.BCDSS')
writeCursor = connection.cursor()
writeCursor.prepare('INSERT INTO DEVELOPER.EAR_FEATURE_VECTOR (VET_ID, CLAIM_ID, DOB, END_PRODUCT_CODE, RO_NUMBER, CLAIM_DATE, PROFILE_DATE, PROMULGATION_DATE, RECENT_EAR_DATE, CONTENTION_COUNT, EAR_CONTENTION_COUNT, PRIOR_CDD, PRIOR_EAR_CDD, CURR_CDD, CURR_EAR_CDD, AGE_OF_CLAIM, AGE_OF_CDD, AGE_OF_EAR_CDD, \
A6100, A6200,A6201,A6202,A6204,A6205,A6207,A6209,A6210,A6211,A6260,C2200,C2210, C2220,C3140,C3150,C4130,C4210,C4700,C4920,C6850, \
CONTENTION_BILATER, CONTENTION_LEFT, CONTENTION_RIGHT, CONTENTION_HEARING, CONTENTION_LOSS, CONTENTION_TINITU, \
DECISION_BILATER, DECISION_LEFT, DECISION_RIGHT, DECISION_HEARING, DECISION_LOSS, DECISION_TINITU, DECISION_CONDITION) \
VALUES (:VET_ID, :CLAIM_ID, :DOB, :END_PRODUCT_CODE, :RO_NUMBER, :CLAIM_DATE, :PROFILE_DATE, :PROMULGATION_DATE, :RECENT_EAR_DATE, :CONTENTION_COUNT, :EAR_CONTENTION_COUNT, :PRIOR_CDD, :PRIOR_EAR_CDD, :CURR_CDD, :CURR_EAR_CDD, :AGE_OF_CLAIM, :AGE_OF_CDD, :AGE_OF_EAR_CDD, \
:A6100, :A6200, :A6201, :A6202, :A6204, :A6205, :A6207, :A6209, :A6210, :A6211, :A6260, :C2200, :C2210, :C2220, :C3140, :C3150, :C4130 , :C4210, :C4700, :C4920, :C6850, \
:CONTENTION_BILATER, :CONTENTION_LEFT, :CONTENTION_RIGHT, :CONTENTION_HEARING, :CONTENTION_LOSS, :CONTENTION_TINITU, \
:DECISION_BILATER, :DECISION_LEFT, :DECISION_RIGHT, :DECISION_HEARING, :DECISION_LOSS, :DECISION_TINITU, :DECISION_CONDITION)')

#Query used to pull decisions prior to claim.
#We use a rating profiles promulgation date before the claim date and for the given participant.
priorDecisionCursor = connection.cursor()
priorDecisionCursor.prepare('SELECT * \
FROM AGGREGATE_DECISION \
WHERE to_date(PROMULGATION_DATE) < :CLAIM_DATE and VET_ID = :VET_ID \
ORDER BY PROMULGATION_DATE')

#Query used to pull decisions for the claim.
#We use a rating profiles profile date equal to the claims most recent rating profile date and for the given participant.
currDecisionCursor = connection.cursor()
currDecisionCursor.prepare('SELECT * \
FROM AGGREGATE_DECISION \
WHERE to_date(PROFILE_DATE) = :MAX_PROFILE_DATE and VET_ID = :VET_ID and ROWNUM = 1')

cursor = connection.cursor()
cursor.execute(SQL)

earFeatureVector = None
aggregateDecision = None
earCDDChangeDate = None
counter = 0;

for row in cursor:
	if counter == 1000:
		connection.commit()
		counter=0
		
	aggregateContention = AggregateContention(row[0],row[1],row[2],row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10],row[11],row[12],row[13],row[14],row[15],row[16],row[17],row[18],row[19],row[20],row[21],row[22],row[23],row[24])
	
	priorDecisionCursor.execute(None, {'VET_ID' :aggregateContention.VET_ID, 'CLAIM_DATE' :aggregateContention.CLAIM_DATE.strftime('%d-%b-%y')}) #rowcount always shows 0!!!!!!!!!!!!!!
	aggregateDecision = None
	
	prevEarCDD = -1;
	earCDDChangeDate = None	
	for decisionRow in priorDecisionCursor:
		aggregateDecision = AggregateDecision(decisionRow[0],decisionRow[1],decisionRow[2],decisionRow[3],decisionRow[4],decisionRow[5],decisionRow[6],decisionRow[7],decisionRow[8],decisionRow[9],decisionRow[10],decisionRow[11],decisionRow[12],decisionRow[13],decisionRow[14],decisionRow[15],decisionRow[16],decisionRow[17],decisionRow[18],decisionRow[19],decisionRow[20],decisionRow[21],decisionRow[22],decisionRow[23])
		if prevEarCDD != aggregateDecision.EAR_CDD:
			prevEarCDD = aggregateDecision.EAR_CDD
			earCDDChangeDate = aggregateDecision.PROMULGATION_DATE
	
	if aggregateDecision is None:
		aggregateDecision = AggregateDecision(None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None)	
				
				
	currDecisionRow = None
	if not aggregateContention.MAX_PROFILE_DATE is None:
		currDecisionCursor.execute(None, {'VET_ID' :aggregateContention.VET_ID, 'MAX_PROFILE_DATE' :aggregateContention.MAX_PROFILE_DATE.strftime('%d-%b-%y')})
		currDecisionRow = currDecisionCursor.fetchone()	
	
	if not currDecisionRow is None:
		currAggregateDecision = AggregateDecision(currDecisionRow[0],currDecisionRow[1],currDecisionRow[2],currDecisionRow[3],currDecisionRow[4],currDecisionRow[5],currDecisionRow[6],currDecisionRow[7],currDecisionRow[8],currDecisionRow[9],currDecisionRow[10],currDecisionRow[11],currDecisionRow[12],currDecisionRow[13],currDecisionRow[14],currDecisionRow[15],currDecisionRow[16],currDecisionRow[17],currDecisionRow[18],currDecisionRow[19],currDecisionRow[20],currDecisionRow[21],currDecisionRow[22],currDecisionRow[23])
	else :
		currAggregateDecision = AggregateDecision(None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None)
		
	
	
	earFeatureVector = EarFeatureVector()
	earFeatureVector.VET_ID = aggregateContention.VET_ID
	earFeatureVector.CLAIM_ID = aggregateContention.CLAIM_ID
	earFeatureVector.DOB = aggregateContention.DOB
	earFeatureVector.END_PRODUCT_CODE = aggregateContention.END_PRODUCT_CODE
	earFeatureVector.RO_NUMBER = aggregateContention.RO_NUMBER
	earFeatureVector.CLAIM_DATE = aggregateContention.CLAIM_DATE	
	earFeatureVector.PROFILE_DATE = aggregateDecision.PROFILE_DATE
	earFeatureVector.PROMULGATION_DATE = aggregateDecision.PROMULGATION_DATE
	earFeatureVector.CONTENTION_COUNT = aggregateContention.CONTENTION_COUNT
	earFeatureVector.EAR_CONTENTION_COUNT = aggregateContention.EAR_CONTENTION_COUNT
	earFeatureVector.PRIOR_CDD = aggregateDecision.CDD
	earFeatureVector.PRIOR_EAR_CDD = aggregateDecision.EAR_CDD
	earFeatureVector.CURR_CDD = currAggregateDecision.CDD
	earFeatureVector.CURR_EAR_CDD = currAggregateDecision.EAR_CDD
	earFeatureVector.AGE_OF_CLAIM = (datetime.datetime.now() - aggregateContention.CLAIM_DATE).days  #Today - Claim Date: This is a relative and changing number so should probably be dropped
	earFeatureVector.RECENT_EAR_DATE = currAggregateDecision.RECENT_EAR_DATE #Most recent ear begin date, from previous profile. We use begin date as promulgation keeps changing and not accurate to when diagnosed

	if earCDDChangeDate is None:
		earFeatureVector.AGE_OF_EAR_CDD	= None
	else:
		earFeatureVector.AGE_OF_EAR_CDD = (aggregateContention.CLAIM_DATE - earCDDChangeDate).days #Claim Date (newer) - Promulgation date of previous (or later) profile where ear cdd changed

	
	if aggregateDecision.PROMULGATION_DATE is None:
		earFeatureVector.AGE_OF_CDD = 0
	else:
		earFeatureVector.AGE_OF_CDD = (aggregateContention.CLAIM_DATE - aggregateDecision.PROMULGATION_DATE).days #Claim Date (newer) - Promulgation date of of previous profile

	
	earFeatureVector.C2200 = aggregateContention.C2200
	earFeatureVector.C2210 = aggregateContention.C2210
	earFeatureVector.C2220 = aggregateContention.C2220
	earFeatureVector.C3140 = aggregateContention.C3140
	earFeatureVector.C3150 = aggregateContention.C3150
	earFeatureVector.C4130 = aggregateContention.C4130
	earFeatureVector.C4210 = aggregateContention.C4210
	earFeatureVector.C4700 = aggregateContention.C4700
	earFeatureVector.C4920 = aggregateContention.C4920
	earFeatureVector.C6850 = aggregateContention.C6850
	earFeatureVector.A6100 = aggregateDecision.A6100
	earFeatureVector.A6200 = aggregateDecision.A6200
	earFeatureVector.A6201 = aggregateDecision.A6201
	earFeatureVector.A6202 = aggregateDecision.A6202
	earFeatureVector.A6204 = aggregateDecision.A6204
	earFeatureVector.A6205 = aggregateDecision.A6205
	earFeatureVector.A6207 = aggregateDecision.A6207
	earFeatureVector.A6209 = aggregateDecision.A6209
	earFeatureVector.A6210 = aggregateDecision.A6210
	earFeatureVector.A6211 = aggregateDecision.A6211
	earFeatureVector.A6260 = aggregateDecision.A6260

	earFeatureVector.CONTENTION_BILATER = aggregateContention.TXT_BILATER
	earFeatureVector.CONTENTION_LEFT = aggregateContention.TXT_LEFT
	earFeatureVector.CONTENTION_HEARING = aggregateContention.TXT_HEARING
	earFeatureVector.CONTENTION_LOSS = aggregateContention.TXT_LOSS
	earFeatureVector.CONTENTION_RIGHT = aggregateContention.TXT_RIGHT
	earFeatureVector.CONTENTION_TINITU = aggregateContention.TXT_TINITU
	
	earFeatureVector.DECISION_BILATER = aggregateDecision.TXT_BILATER
	earFeatureVector.DECISION_LEFT = aggregateDecision.TXT_LEFT
	earFeatureVector.DECISION_RIGHT = aggregateDecision.TXT_RIGHT
	earFeatureVector.DECISION_HEARING = aggregateDecision.TXT_HEARING
	earFeatureVector.DECISION_LOSS = aggregateDecision.TXT_LOSS
	earFeatureVector.DECISION_TINITU = aggregateDecision.TXT_TINITU
	earFeatureVector.DECISION_CONDITION = aggregateDecision.TXT_CONDITION
	
		
	writeCursor.execute(None, {'VET_ID' :earFeatureVector.VET_ID, 'CLAIM_ID' :earFeatureVector.CLAIM_ID, 'DOB' :earFeatureVector.DOB, 'END_PRODUCT_CODE' :earFeatureVector.END_PRODUCT_CODE, 'RO_NUMBER' :earFeatureVector.RO_NUMBER, 'CLAIM_DATE' :earFeatureVector.CLAIM_DATE, 'PROFILE_DATE' :earFeatureVector.PROFILE_DATE, 'PROMULGATION_DATE' :earFeatureVector.PROMULGATION_DATE, 'CONTENTION_COUNT' :earFeatureVector.CONTENTION_COUNT, 'EAR_CONTENTION_COUNT' :earFeatureVector.EAR_CONTENTION_COUNT,
	'PRIOR_CDD' :earFeatureVector.PRIOR_CDD, 'PRIOR_EAR_CDD' :earFeatureVector.PRIOR_EAR_CDD, 'CURR_CDD' :earFeatureVector.CURR_CDD, 'CURR_EAR_CDD' :earFeatureVector.CURR_EAR_CDD, 'AGE_OF_CLAIM' :earFeatureVector.AGE_OF_CLAIM, 'AGE_OF_EAR_CDD' :earFeatureVector.AGE_OF_EAR_CDD, 'RECENT_EAR_DATE' :earFeatureVector.RECENT_EAR_DATE, 'AGE_OF_CDD' :earFeatureVector.AGE_OF_CDD,
	'C2200' :earFeatureVector.C2200, 'C2210' :earFeatureVector.C2210, 'C2220' :earFeatureVector.C2220, 'C3140' :earFeatureVector.C3140, 'C3150' :earFeatureVector.C3150, 'C4130' :earFeatureVector.C4130, 'C4210' :earFeatureVector.C4210, 'C4700' :earFeatureVector.C4700, 'C4920' :earFeatureVector.C4920, 'C6850' :earFeatureVector.C6850,
	'A6100' :earFeatureVector.A6100, 'A6200' :earFeatureVector.A6200, 'A6201' :earFeatureVector.A6201, 'A6202' :earFeatureVector.A6202, 'A6204' :earFeatureVector.A6204, 'A6205' :earFeatureVector.A6205, 'A6207' :earFeatureVector.A6207, 'A6209' :earFeatureVector.A6209, 'A6210' :earFeatureVector.A6210, 'A6211' :earFeatureVector.A6211, 'A6260' :earFeatureVector.A6260,
	'CONTENTION_BILATER' :earFeatureVector.CONTENTION_BILATER, 'CONTENTION_LEFT' :earFeatureVector.CONTENTION_LEFT, 'CONTENTION_RIGHT' :earFeatureVector.CONTENTION_RIGHT, 'CONTENTION_HEARING' :earFeatureVector.CONTENTION_HEARING, 'CONTENTION_LOSS' :earFeatureVector.CONTENTION_LOSS, 'CONTENTION_TINITU' :earFeatureVector.CONTENTION_TINITU, 
	'DECISION_BILATER' :earFeatureVector.DECISION_BILATER, 'DECISION_LEFT' :earFeatureVector.DECISION_LEFT, 'DECISION_RIGHT' :earFeatureVector.DECISION_RIGHT, 'DECISION_HEARING' :earFeatureVector.DECISION_HEARING, 'DECISION_LOSS' :earFeatureVector.DECISION_LOSS, 'DECISION_TINITU' :earFeatureVector.DECISION_TINITU, 'DECISION_CONDITION' :earFeatureVector.DECISION_CONDITION})
	
	
	counter += 1
			
			

connection.commit()
print(str(datetime.datetime.now()))

writeCursor.close()
priorDecisionCursor.close()
currDecisionCursor.close()
cursor.close()
connection.close()
