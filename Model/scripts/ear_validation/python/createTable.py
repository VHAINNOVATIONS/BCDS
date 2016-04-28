import os
import re
import cx_Oracle
import collections
import datetime
import math

earDiagnosisCode = [6100,6200,6201,6202,6204,6205,6207,6209,6210,6211,6260]

#Primary query, Grab all processed contentions and order by particpant id and then claim date descending
SQL="select * \
	from V_EAR_AGGREGATE_CONTENTION \
	order by VET_ID,CLAIM_DATE"

	
class EarFeatureVector:
	def __init__(self):
		self.VET_ID = None
		self.CLAIM_ID = None
		self.DOB = 0
		self.CLAIMANT_AGE = None
		self.END_PRODUCT_CODE = None
		self.RO_NUMBER = 0
		self.CLAIM_DATE = None
		self.PROFILE_DATE = None
		self.PROMULGATION_DATE = None
		self.RECENT_EAR_DATE = None
		self.CONTENTION_COUNT = 0
		self.EAR_CONTENTION_COUNT = 0
		self.PRIOR_EAR_CDD = 0
		self.QUANT_PRIOR_EAR_CDD = 0
		self.CURR_EAR_CDD = 0
		self.QUANT_EAR_CDD = 0
		self.CLAIM_AGE = 0
		self.EAR_CDD_AGE = 0
		self.EAR_CLAIM_COUNT = 0
		self.C2200 = 0
		self.C2210 = 0
		self.C2220 = 0
		self.C3140 = 0
		self.C3150 = 0
		self.C4130 = 0
		self.C4210 = 0
		self.C4700 = 0
		self.C4920 = 0
		self.C5000 = 0
		self.C5010 = 0
		self.C5710 = 0
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
		self.CONTENTION_LOSS = 0
		self.CONTENTION_TINITU = 0
		self.DECISION_LOSS = 0
		self.DECISION_TINITU = 0
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))
		
		
class AggregateDecision:
	def __init__(self,VET_ID,PROFILE_DATE,PROMULGATION_DATE,RECENT_EAR_DATE,CDD,EAR_CDD,A6100,A6200,A6201,A6202,A6204,A6205,A6207,A6209,A6210,A6211,A6260,TXT_LOSS,TXT_TINITU):
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
		self.TXT_LOSS = TXT_LOSS
		self.TXT_TINITU = TXT_TINITU
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))
		
class AggregateContention:
	def __init__(self,VET_ID,CLAIM_ID,DOB,END_PRODUCT_CODE,RO_NUMBER,CLAIM_DATE,MAX_PROFILE_DATE,CONTENTION_COUNT,EAR_CONTENTION_COUNT,C2200,C2210,C2220,C3140,C3150,C4130,C4210,C4700,C4920,C5000,C5010,C5710,C6850,TXT_LOSS,TXT_TINITU):
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
		self.C5000 = C5000
		self.C5010 = C5010
		self.C5710 = C5710
		self.C6850 = C6850
		self.TXT_LOSS = TXT_LOSS
		self.TXT_TINITU = TXT_TINITU
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))

print(str(datetime.datetime.now()))
connection = cx_Oracle.connect('developer/D3vVV0Rd@127.0.0.1:1521/DEV.BCDSS')
writeCursor = connection.cursor()
writeCursor.prepare('INSERT INTO DEVELOPER.V_EAR_FEATURE_VECTOR (VET_ID, CLAIM_ID, CLAIMANT_AGE, DOB, END_PRODUCT_CODE, RO_NUMBER, CLAIM_DATE, PROFILE_DATE, PROMULGATION_DATE, RECENT_EAR_DATE, CONTENTION_COUNT, EAR_CONTENTION_COUNT, PRIOR_EAR_CDD, QUANT_PRIOR_EAR_CDD, CURR_EAR_CDD, QUANT_EAR_CDD, CLAIM_AGE, EAR_CDD_AGE, EAR_CLAIM_COUNT, \
A6100, A6200,A6201,A6202,A6204,A6205,A6207,A6209,A6210,A6211,A6260,C2200,C2210, C2220,C3140,C3150,C4130,C4210,C4700,C4920,C5000, C5010, C5710, C6850, \
CONTENTION_LOSS, CONTENTION_TINITU, DECISION_LOSS, DECISION_TINITU) \
VALUES (:VET_ID, :CLAIM_ID, :CLAIMANT_AGE, :DOB, :END_PRODUCT_CODE, :RO_NUMBER, :CLAIM_DATE, :PROFILE_DATE, :PROMULGATION_DATE, :RECENT_EAR_DATE, :CONTENTION_COUNT, :EAR_CONTENTION_COUNT, :PRIOR_EAR_CDD, :QUANT_PRIOR_EAR_CDD, :CURR_EAR_CDD, :QUANT_EAR_CDD, :CLAIM_AGE, :EAR_CDD_AGE, :EAR_CLAIM_COUNT, \
:A6100, :A6200, :A6201, :A6202, :A6204, :A6205, :A6207, :A6209, :A6210, :A6211, :A6260, :C2200, :C2210, :C2220, :C3140, :C3150, :C4130 , :C4210, :C4700, :C4920, :C5000, :C5010, :C5710, :C6850, \
:CONTENTION_LOSS, :CONTENTION_TINITU, :DECISION_LOSS, :DECISION_TINITU)')

#Query used to pull decisions prior to claim.
#We use a rating profiles promulgation date before the claim date and for the given participant.
priorDecisionCursor = connection.cursor()
priorDecisionCursor.prepare('SELECT * FROM \
(SELECT * \
FROM V_EAR_AGGREGATE_DECISION \
WHERE to_date(PROMULGATION_DATE) < :CLAIM_DATE and VET_ID = :VET_ID \
ORDER BY PROMULGATION_DATE DESC) WHERE ROWNUM =1')

#Query used to pull decisions for the claim.
#We use a rating profiles profile date equal to the claims most recent rating profile date and for the given participant.
currDecisionCursor = connection.cursor()
currDecisionCursor.prepare('SELECT * \
FROM V_EAR_AGGREGATE_DECISION \
WHERE to_date(PROFILE_DATE) = :MAX_PROFILE_DATE and VET_ID = :VET_ID and ROWNUM = 1')

cursor = connection.cursor()
cursor.execute(SQL)

earFeatureVector = None
aggregateDecision = None
earCDDChangeDate = None
counter = 0;
currParticipant = -1
earClaimCount = 0


for row in cursor:
	if counter == 1000:
		connection.commit()
		counter=0
		
	aggregateContention = AggregateContention(row[0],row[1],row[2],row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10],row[11],row[12],row[13],row[14],row[15],row[16],row[17],row[18],row[19],row[20],row[21],row[22],row[23])
	
	if currParticipant != aggregateContention.VET_ID : 
		currParticipant = aggregateContention.VET_ID #Reset participant id
		earClaimCount = 0

		
	priorDecisionCursor.execute(None, {'VET_ID' :aggregateContention.VET_ID, 'CLAIM_DATE' :aggregateContention.CLAIM_DATE.strftime('%d-%b-%y')}) #rowcount always shows 0!!!!!!!!!!!!!!
	aggregateDecision = None
	
	prevEarCDD = -1;
	earCDDChangeDate = None	
	for decisionRow in priorDecisionCursor:
		aggregateDecision = AggregateDecision(decisionRow[0],decisionRow[1],decisionRow[2],decisionRow[3],decisionRow[4],decisionRow[5],decisionRow[6],decisionRow[7],decisionRow[8],decisionRow[9],decisionRow[10],decisionRow[11],decisionRow[12],decisionRow[13],decisionRow[14],decisionRow[15],decisionRow[16],decisionRow[17],decisionRow[18])
		if prevEarCDD != aggregateDecision.EAR_CDD:
			prevEarCDD = aggregateDecision.EAR_CDD
			earCDDChangeDate = aggregateDecision.PROMULGATION_DATE
	
	if aggregateDecision is None:
		aggregateDecision = AggregateDecision(None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None)
				
				
	currDecisionRow = None
	if not aggregateContention.MAX_PROFILE_DATE is None:
		currDecisionCursor.execute(None, {'VET_ID' :aggregateContention.VET_ID, 'MAX_PROFILE_DATE' :aggregateContention.MAX_PROFILE_DATE.strftime('%d-%b-%y')})
		currDecisionRow = currDecisionCursor.fetchone()	
	
	if not currDecisionRow is None:
		currAggregateDecision = AggregateDecision(currDecisionRow[0],currDecisionRow[1],currDecisionRow[2],currDecisionRow[3],currDecisionRow[4],currDecisionRow[5],currDecisionRow[6],currDecisionRow[7],currDecisionRow[8],currDecisionRow[9],currDecisionRow[10],currDecisionRow[11],currDecisionRow[12],currDecisionRow[13],currDecisionRow[14],currDecisionRow[15],currDecisionRow[16],currDecisionRow[17],currDecisionRow[18])
	else :
		currAggregateDecision = AggregateDecision(None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None)
		
	
	
	earFeatureVector = EarFeatureVector()
	earFeatureVector.VET_ID = aggregateContention.VET_ID
	earFeatureVector.CLAIM_ID = aggregateContention.CLAIM_ID
	if aggregateContention.DOB is None:
		earFeatureVector.CLAIMANT_AGE = None
	else:
		earFeatureVector.CLAIMANT_AGE = int((((aggregateContention.CLAIM_DATE.year - aggregateContention.DOB) + 9)/10)) * 10  #Below the nearest 10, If you are 30 then show 30, if 31-39 show 40.	
	earFeatureVector.DOB = aggregateContention.DOB
	earFeatureVector.END_PRODUCT_CODE = aggregateContention.END_PRODUCT_CODE
	earFeatureVector.RO_NUMBER = aggregateContention.RO_NUMBER
	earFeatureVector.CLAIM_DATE = aggregateContention.CLAIM_DATE	
	earFeatureVector.PROFILE_DATE = aggregateDecision.PROFILE_DATE
	earFeatureVector.PROMULGATION_DATE = aggregateDecision.PROMULGATION_DATE
	earFeatureVector.CONTENTION_COUNT = aggregateContention.CONTENTION_COUNT
	earFeatureVector.EAR_CONTENTION_COUNT = aggregateContention.EAR_CONTENTION_COUNT
	earFeatureVector.PRIOR_EAR_CDD = aggregateDecision.EAR_CDD
	earFeatureVector.CURR_EAR_CDD = currAggregateDecision.EAR_CDD
	earFeatureVector.CLAIM_AGE = int((datetime.datetime.now() - aggregateContention.CLAIM_DATE).days / 365.25)  #Today - Claim Date: This is a relative and changing number so should probably be dropped
	earFeatureVector.RECENT_EAR_DATE = currAggregateDecision.RECENT_EAR_DATE #Most recent ear begin date, from previous profile. We use begin date as promulgation keeps changing and not accurate to when diagnosed

	if aggregateDecision.EAR_CDD is None:
		earFeatureVector.QUANT_PRIOR_EAR_CDD = None
	else:
		earFeatureVector.QUANT_PRIOR_EAR_CDD = int(math.ceil(aggregateDecision.EAR_CDD / 10.0)) * 10 #Quantize Prior CDD to the nearest 10

	if currAggregateDecision.EAR_CDD is None:
		earFeatureVector.QUANT_EAR_CDD = None
	else:
		earFeatureVector.QUANT_EAR_CDD = int(math.ceil(currAggregateDecision.EAR_CDD / 10.0)) * 10 #Quantize CDD to the nearest 10


	if earCDDChangeDate is None:
		earFeatureVector.EAR_CDD_AGE	= None
	else:
		earFeatureVector.EAR_CDD_AGE = int((aggregateContention.CLAIM_DATE - earCDDChangeDate).days / 365.25) #Claim Date (newer) - Promulgation date of previous (or later) profile where ear cdd changed


	
	earFeatureVector.C2200 = aggregateContention.C2200
	earFeatureVector.C2210 = aggregateContention.C2210
	earFeatureVector.C2220 = aggregateContention.C2220
	earFeatureVector.C3140 = aggregateContention.C3140
	earFeatureVector.C3150 = aggregateContention.C3150
	earFeatureVector.C4130 = aggregateContention.C4130
	earFeatureVector.C4210 = aggregateContention.C4210
	earFeatureVector.C4700 = aggregateContention.C4700
	earFeatureVector.C4920 = aggregateContention.C4920
	earFeatureVector.C5000 = aggregateContention.C5000
	earFeatureVector.C5010 = aggregateContention.C5010
	earFeatureVector.C5710 = aggregateContention.C5710
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
	earFeatureVector.CONTENTION_LOSS = aggregateContention.TXT_LOSS
	earFeatureVector.CONTENTION_TINITU = aggregateContention.TXT_TINITU
	earFeatureVector.DECISION_LOSS = aggregateDecision.TXT_LOSS
	earFeatureVector.DECISION_TINITU = aggregateDecision.TXT_TINITU

	if earFeatureVector.PRIOR_EAR_CDD != earFeatureVector.CURR_EAR_CDD:
		earClaimCount += 1
		
	earFeatureVector.EAR_CLAIM_COUNT = earClaimCount	
		
	writeCursor.execute(None, {'VET_ID' :earFeatureVector.VET_ID, 'CLAIM_ID' :earFeatureVector.CLAIM_ID, 'CLAIMANT_AGE' :earFeatureVector.CLAIMANT_AGE, 'DOB' :earFeatureVector.DOB, 'END_PRODUCT_CODE' :earFeatureVector.END_PRODUCT_CODE, 'RO_NUMBER' :earFeatureVector.RO_NUMBER, 'CLAIM_DATE' :earFeatureVector.CLAIM_DATE, 'PROFILE_DATE' :earFeatureVector.PROFILE_DATE, 'PROMULGATION_DATE' :earFeatureVector.PROMULGATION_DATE, 'CONTENTION_COUNT' :earFeatureVector.CONTENTION_COUNT, 'EAR_CONTENTION_COUNT' :earFeatureVector.EAR_CONTENTION_COUNT,
	'PRIOR_EAR_CDD' :earFeatureVector.PRIOR_EAR_CDD, 'QUANT_PRIOR_EAR_CDD' :earFeatureVector.QUANT_PRIOR_EAR_CDD, 'CURR_EAR_CDD' :earFeatureVector.CURR_EAR_CDD, 'QUANT_EAR_CDD' :earFeatureVector.QUANT_EAR_CDD, 'CLAIM_AGE' :earFeatureVector.CLAIM_AGE, 'EAR_CDD_AGE' :earFeatureVector.EAR_CDD_AGE, 'RECENT_EAR_DATE' :earFeatureVector.RECENT_EAR_DATE, 'EAR_CLAIM_COUNT' :earFeatureVector.EAR_CLAIM_COUNT,
	'C2200' :earFeatureVector.C2200, 'C2210' :earFeatureVector.C2210, 'C2220' :earFeatureVector.C2220, 'C3140' :earFeatureVector.C3140, 'C3150' :earFeatureVector.C3150, 'C4130' :earFeatureVector.C4130, 'C4210' :earFeatureVector.C4210, 'C4700' :earFeatureVector.C4700, 'C4920' :earFeatureVector.C4920, 'C5000' :earFeatureVector.C5000, 'C5010' :earFeatureVector.C5010, 'C5710' :earFeatureVector.C5710, 'C6850' :earFeatureVector.C6850,
	'A6100' :earFeatureVector.A6100, 'A6200' :earFeatureVector.A6200, 'A6201' :earFeatureVector.A6201, 'A6202' :earFeatureVector.A6202, 'A6204' :earFeatureVector.A6204, 'A6205' :earFeatureVector.A6205, 'A6207' :earFeatureVector.A6207, 'A6209' :earFeatureVector.A6209, 'A6210' :earFeatureVector.A6210, 'A6211' :earFeatureVector.A6211, 'A6260' :earFeatureVector.A6260,
	'CONTENTION_LOSS' :earFeatureVector.CONTENTION_LOSS, 'CONTENTION_TINITU' :earFeatureVector.CONTENTION_TINITU, 'DECISION_LOSS' :earFeatureVector.DECISION_LOSS, 'DECISION_TINITU' :earFeatureVector.DECISION_TINITU})
	
	
	counter += 1
			
			

connection.commit()
print(str(datetime.datetime.now()))

writeCursor.close()
priorDecisionCursor.close()
currDecisionCursor.close()
cursor.close()
connection.close()
