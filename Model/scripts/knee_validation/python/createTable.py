import os
import re
import cx_Oracle
import collections
import datetime
import math

#Primary query, Grab all processed contentions and order by particpant id and then claim date descending
SQL="select * \
	from V_KNEE_AGGREGATE_CONTENTION \
	order by VET_ID,CLAIM_DATE"

	
class KneeFeatureVector:
	def __init__(self):
		self.VET_ID = None
		self.CLAIM_ID = None
		self.CLAIMANT_AGE = None
		self.DOB = 0		
		self.END_PRODUCT_CODE = None
		self.RO_NUMBER = 0
		self.CLAIM_DATE = None
		self.PROFILE_DATE = None
		self.PROMULGATION_DATE = None
		self.RECENT_KNEE_DATE = None
		self.CONTENTION_COUNT = 0
		self.KNEE_CONTENTION_COUNT = 0
		self.PRIOR_KNEE_CDD = 0
		self.QUANT_PRIOR_KNEE_CDD = 0
		self.CURR_KNEE_CDD = 0
		self.QUANT_KNEE_CDD = 0
		self.CLAIM_AGE = 0
		self.KNEE_CDD_AGE = 0
		self.KNEE_CLAIM_COUNT = 0
		self.C230 = 0 
		self.C270 = 0 
		self.C3690 = 0 
		self.C3700 = 0 
		self.C3710 = 0 
		self.C8919 = 0 
		self.C3720 = 0 
		self.C3730 = 0 
		self.C3780 = 0 
		self.C3790 = 0 
		self.C3800 = 0 	
		self.A5164 = 0 
		self.A5164 = 0 
		self.A5163 = 0 
		self.A5162 = 0 
		self.A5161 = 0 
		self.A5256 = 0 
		self.A5258 = 0 
		self.A5257 = 0 
		self.A5313 = 0 
		self.A5314 = 0 
		self.A5315 = 0 
		self.A5055 = 0 
		self.A5261 = 0 
		self.A5260 = 0 
		self.A5259 = 0 
		self.A5262 = 0
		self.A5263 = 0
		self.A5264 = 0
		
		self.CONTENTION_KNEE = 0 
		self.CONTENTION_LEFT = 0 
		self.CONTENTION_RIGHT = 0 
		self.CONTENTION_BILATERAL = 0 
		self.CONTENTION_LEG = 0 
		self.CONTENTION_AMPUTATION = 0 
		
		self.DECISION_KNEE = 0 
		self.DECISION_LEFT = 0 
		self.DECISION_RIGHT = 0 
		self.DECISION_BILATERAL = 0 
		self.DECISION_LIMITATION = 0 
		self.DECISION_IMPAIRMENT = 0 
		self.DECISION_ANKYLOSES = 0 
		self.DECISION_AMPUTATION = 0		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))
		
		
class AggregateDecision:
	def __init__(self,VET_ID, PROFILE_DATE,PROMULGATION_DATE,RECENT_KNEE_DATE,CDD,KNEE_CDD,A5164,A5165,A5163,A5162,A5161,A5256,A5258,A5257,A5313,A5314,A5315,A5055,A5261,A5260,A5259,A5262,A5263,A5264,TXT_BILATERAL,TXT_LEFT,TXT_RIGHT,TXT_KNEE,TXT_IMPAIRMENT,TXT_LIMITATION,TXT_AMPUTATION,TXT_ANKYLOSES):
		self.VET_ID = VET_ID
		self.PROFILE_DATE = PROFILE_DATE
		self.PROMULGATION_DATE = PROMULGATION_DATE
		self.RECENT_KNEE_DATE = RECENT_KNEE_DATE
		self.CDD = CDD
		self.KNEE_CDD = KNEE_CDD
		self.A5164 = A5164
		self.A5165 = A5165
		self.A5163 = A5163
		self.A5162 = A5162
		self.A5161 = A5161		
		self.A5256 = A5256
		self.A5258 = A5258
		self.A5257 = A5257
		self.A5313 = A5313
		self.A5314 = A5314
		self.A5315 = A5315
		self.A5055 = A5055
		self.A5261 = A5261
		self.A5260 = A5260
		self.A5259 = A5259
		self.A5262 = A5262
		self.A5263 = A5263
		self.A5264 = A5264
		self.TXT_BILATERAL = TXT_BILATERAL		
		self.TXT_LEFT = TXT_LEFT
		self.TXT_RIGHT = TXT_RIGHT
		self.TXT_KNEE = TXT_KNEE
		self.TXT_IMPAIRMENT = TXT_IMPAIRMENT
		self.TXT_LIMITATION = TXT_LIMITATION
		self.TXT_AMPUTATION = TXT_AMPUTATION
		self.TXT_ANKYLOSES = TXT_ANKYLOSES
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))
		
class AggregateContention:
	def __init__(self,VET_ID,CLAIM_ID,DOB,END_PRODUCT_CODE,RO_NUMBER,CLAIM_DATE,MAX_PROFILE_DATE,CONTENTION_COUNT,KNEE_CONTENTION_COUNT,C230, C270, C3690, C3700, C3710, C8919, C3720, C3730, C3780, C3790, C3800,TXT_BILATERAL,TXT_LEFT,TXT_RIGHT,TXT_KNEE,TXT_LEG,TXT_AMPUTATION):
		self.VET_ID = VET_ID
		self.CLAIM_ID = CLAIM_ID
		self.DOB = DOB
		self.END_PRODUCT_CODE = END_PRODUCT_CODE
		self.RO_NUMBER = RO_NUMBER
		self.CLAIM_DATE = CLAIM_DATE
		self.MAX_PROFILE_DATE = MAX_PROFILE_DATE
		self.CONTENTION_COUNT = CONTENTION_COUNT
		self.KNEE_CONTENTION_COUNT = KNEE_CONTENTION_COUNT
		self.C230 = C230
		self.C270 = C270
		self.C3690 = C3690
		self.C3700 = C3700
		self.C3710 = C3710
		self.C8919 = C8919
		self.C3720 = C3720
		self.C3730 = C3730
		self.C3780 = C3780
		self.C3790 = C3790
		self.C3800 = C3800
		self.TXT_BILATERAL = TXT_BILATERAL
		self.TXT_LEFT = TXT_LEFT
		self.TXT_RIGHT = TXT_RIGHT
		self.TXT_KNEE = TXT_KNEE
		self.TXT_LEG = TXT_LEG
		self.TXT_AMPUTATION = TXT_AMPUTATION
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))

print(str(datetime.datetime.now()))
connection = cx_Oracle.connect('developer/D3vVV0Rd@127.0.0.1:1521/DEV.BCDSS')
writeCursor = connection.cursor()
writeCursor.prepare('INSERT INTO DEVELOPER.V_KNEE_FEATURE_VECTOR (VET_ID, CLAIM_ID, CLAIMANT_AGE, DOB, END_PRODUCT_CODE, RO_NUMBER, CLAIM_DATE, PROFILE_DATE, PROMULGATION_DATE, RECENT_KNEE_DATE, CONTENTION_COUNT, KNEE_CONTENTION_COUNT, PRIOR_KNEE_CDD, QUANT_PRIOR_KNEE_CDD, CURR_KNEE_CDD, QUANT_KNEE_CDD, CLAIM_AGE, KNEE_CDD_AGE, KNEE_CLAIM_COUNT, \
A5164, A5165, A5163, A5162, A5161, A5256, A5258, A5257, A5313, A5314, A5315, A5055, A5261, A5260, A5259, A5262, A5263, A5264, C230, C270, C3690, C3700, C3710, C8919, C3720, C3730, C3780, C3790, C3800, \
CONTENTION_BILATERAL,CONTENTION_LEFT,CONTENTION_RIGHT,CONTENTION_LEG,CONTENTION_KNEE,CONTENTION_AMPUTATION, \
DECISION_BILATERAL,DECISION_LEFT,DECISION_RIGHT,DECISION_KNEE,DECISION_IMPAIRMENT,DECISION_LIMITATION,DECISION_AMPUTATION,DECISION_ANKYLOSES) \
VALUES (:VET_ID, :CLAIM_ID, :CLAIMANT_AGE, :DOB, :END_PRODUCT_CODE, :RO_NUMBER, :CLAIM_DATE, :PROFILE_DATE, :PROMULGATION_DATE, :RECENT_KNEE_DATE, :CONTENTION_COUNT, :KNEE_CONTENTION_COUNT, :PRIOR_KNEE_CDD, :QUANT_PRIOR_KNEE_CDD, :CURR_KNEE_CDD, :QUANT_KNEE_CDD, :CLAIM_AGE, :KNEE_CDD_AGE, :KNEE_CLAIM_COUNT, \
:A5164, :A5165, :A5163, :A5162, :A5161, :A5256, :A5258, :A5257, :A5313, :A5314, :A5315, :A5055, :A5261, :A5260, :A5259, :A5262, :A5263, :A5264, \
:C230, :C270, :C3690, :C3700, :C3710, :C8919, :C3720, :C3730, :C3780, :C3790, :C3800, \
:CONTENTION_BILATERAL, :CONTENTION_LEFT, :CONTENTION_RIGHT, :CONTENTION_LEG, :CONTENTION_KNEE, :CONTENTION_AMPUTATION, \
:DECISION_BILATERAL,:DECISION_LEFT,:DECISION_RIGHT,:DECISION_KNEE,:DECISION_IMPAIRMENT,:DECISION_LIMITATION,:DECISION_AMPUTATION,:DECISION_ANKYLOSES)')

#Query used to pull decisions prior to claim.
#We use a rating profiles promulgation date before the claim date and for the given participant.
priorDecisionCursor = connection.cursor()
priorDecisionCursor.prepare('SELECT * FROM \
(SELECT * \
FROM V_KNEE_AGGREGATE_DECISION \
WHERE to_date(PROMULGATION_DATE) < :CLAIM_DATE and VET_ID = :VET_ID \
ORDER BY PROMULGATION_DATE DESC) WHERE ROWNUM =1')

#Query used to pull decisions for the claim.
#We use a rating profiles profile date equal to the claims most recent rating profile date and for the given participant.
currDecisionCursor = connection.cursor()
currDecisionCursor.prepare('SELECT * \
FROM V_KNEE_AGGREGATE_DECISION \
WHERE to_date(PROFILE_DATE) = :MAX_PROFILE_DATE and VET_ID = :VET_ID and ROWNUM = 1')

cursor = connection.cursor()
cursor.execute(SQL)

kneeFeatureVector = None
aggregateDecision = None
kneeCDDChangeDate = None
counter = 0;
currParticipant = -1
kneeClaimCount = 0

for row in cursor:
	if counter == 1000:
		connection.commit()
		counter=0

		
	aggregateContention = AggregateContention(row[0],row[1],row[2],row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10],row[11],row[12],row[13],row[14],row[15],row[16],row[17],row[18],row[19],row[20],row[21],row[22],row[23],row[24],row[25])
	
	if currParticipant != aggregateContention.VET_ID : 
		currParticipant = aggregateContention.VET_ID #Reset participant id
		kneeClaimCount = 0
	
	
	priorDecisionCursor.execute(None, {'VET_ID' :aggregateContention.VET_ID, 'CLAIM_DATE' :aggregateContention.CLAIM_DATE.strftime('%d-%b-%y')}) #rowcount always shows 0!!!!!!!!!!!!!!
	aggregateDecision = None
	
	prevKneeCDD = -1;
	kneeCDDChangeDate = None	
	for decisionRow in priorDecisionCursor:
		aggregateDecision = AggregateDecision(decisionRow[0],decisionRow[1],decisionRow[2],decisionRow[3],decisionRow[4],decisionRow[5],decisionRow[6],decisionRow[7],decisionRow[8],decisionRow[9],decisionRow[10],decisionRow[11],decisionRow[12],decisionRow[13],decisionRow[14],decisionRow[15],decisionRow[16],decisionRow[17],decisionRow[18],decisionRow[19],decisionRow[20],decisionRow[21],decisionRow[22],decisionRow[23],decisionRow[24],decisionRow[25],decisionRow[26],decisionRow[27],decisionRow[28],decisionRow[29],decisionRow[30],decisionRow[31])
		if prevKneeCDD != aggregateDecision.KNEE_CDD:
			prevKneeCDD = aggregateDecision.KNEE_CDD
			kneeCDDChangeDate = aggregateDecision.PROMULGATION_DATE
	
	if aggregateDecision is None:
		aggregateDecision = AggregateDecision(None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None)	
				
				
	currDecisionRow = None
	if not aggregateContention.MAX_PROFILE_DATE is None:
		currDecisionCursor.execute(None, {'VET_ID' :aggregateContention.VET_ID, 'MAX_PROFILE_DATE' :aggregateContention.MAX_PROFILE_DATE.strftime('%d-%b-%y')})
		currDecisionRow = currDecisionCursor.fetchone()	
	
	if not currDecisionRow is None:
		currAggregateDecision = AggregateDecision(currDecisionRow[0],currDecisionRow[1],currDecisionRow[2],currDecisionRow[3],currDecisionRow[4],currDecisionRow[5],currDecisionRow[6],currDecisionRow[7],currDecisionRow[8],currDecisionRow[9],currDecisionRow[10],currDecisionRow[11],currDecisionRow[12],currDecisionRow[13],currDecisionRow[14],currDecisionRow[15],currDecisionRow[16],currDecisionRow[17],currDecisionRow[18],currDecisionRow[19],currDecisionRow[20],currDecisionRow[21],currDecisionRow[22],currDecisionRow[23],currDecisionRow[24],currDecisionRow[25],currDecisionRow[26],currDecisionRow[27],currDecisionRow[28],currDecisionRow[29],currDecisionRow[30],currDecisionRow[31])
	else :
		currAggregateDecision = AggregateDecision(None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None,None)
		
	
	
	kneeFeatureVector = KneeFeatureVector()
	kneeFeatureVector.VET_ID = aggregateContention.VET_ID
	kneeFeatureVector.CLAIM_ID = aggregateContention.CLAIM_ID
	if aggregateContention.DOB is None:
		kneeFeatureVector.CLAIMANT_AGE = None
	else:
		kneeFeatureVector.CLAIMANT_AGE = int((((aggregateContention.CLAIM_DATE.year - aggregateContention.DOB) + 9)/10)) * 10  #Below the nearest 10, If you are 30 then show 30, if 31-39 show 40.		
	kneeFeatureVector.DOB = aggregateContention.DOB
	kneeFeatureVector.END_PRODUCT_CODE = aggregateContention.END_PRODUCT_CODE
	kneeFeatureVector.RO_NUMBER = aggregateContention.RO_NUMBER
	kneeFeatureVector.CLAIM_DATE = aggregateContention.CLAIM_DATE	
	kneeFeatureVector.PROFILE_DATE = aggregateDecision.PROFILE_DATE
	kneeFeatureVector.PROMULGATION_DATE = aggregateDecision.PROMULGATION_DATE
	kneeFeatureVector.CONTENTION_COUNT = aggregateContention.CONTENTION_COUNT
	kneeFeatureVector.KNEE_CONTENTION_COUNT = aggregateContention.KNEE_CONTENTION_COUNT
	kneeFeatureVector.PRIOR_KNEE_CDD = aggregateDecision.KNEE_CDD
	kneeFeatureVector.CURR_KNEE_CDD = currAggregateDecision.KNEE_CDD
	kneeFeatureVector.CLAIM_AGE = int((datetime.datetime.now() - aggregateContention.CLAIM_DATE).days / 365.25)  #Today - Claim Date: This is a relative and changing number so should probably be dropped	
	kneeFeatureVector.RECENT_KNEE_DATE = currAggregateDecision.RECENT_KNEE_DATE #Most recent knee begin date, from previous profile. We use begin date as promulgation keeps changing and not accurate to when diagnosed

	if aggregateDecision.KNEE_CDD is None:
		kneeFeatureVector.QUANT_PRIOR_KNEE_CDD = None
	else:
		kneeFeatureVector.QUANT_PRIOR_KNEE_CDD = int(math.ceil(aggregateDecision.KNEE_CDD / 10.0)) * 10 #Quantize Prior CDD to the nearest 10

	if currAggregateDecision.KNEE_CDD is None:
		kneeFeatureVector.QUANT_KNEE_CDD = None
	else:
		kneeFeatureVector.QUANT_KNEE_CDD = int(math.ceil(currAggregateDecision.KNEE_CDD / 10.0)) * 10 #Quantize CDD to the nearest 10

		
	if kneeCDDChangeDate is None:
		kneeFeatureVector.KNEE_CDD_AGE	= None
	else:
		kneeFeatureVector.KNEE_CDD_AGE = int((aggregateContention.CLAIM_DATE - kneeCDDChangeDate).days / 365.25) #Claim Date (newer) - Promulgation date of previous (or later) profile where knee cdd changed


	
	kneeFeatureVector.C230 = aggregateContention.C230
	kneeFeatureVector.C270 = aggregateContention.C270
	kneeFeatureVector.C3690 = aggregateContention.C3690
	kneeFeatureVector.C3700 = aggregateContention.C3700
	kneeFeatureVector.C3710 = aggregateContention.C3710
	kneeFeatureVector.C8919 = aggregateContention.C8919
	kneeFeatureVector.C3720 = aggregateContention.C3720
	kneeFeatureVector.C3730 = aggregateContention.C3730
	kneeFeatureVector.C3780 = aggregateContention.C3780
	kneeFeatureVector.C3790 = aggregateContention.C3790
	kneeFeatureVector.C3800 = aggregateContention.C3800
	kneeFeatureVector.A5164 = aggregateDecision.A5164
	kneeFeatureVector.A5165 = aggregateDecision.A5165
	kneeFeatureVector.A5163 = aggregateDecision.A5163
	kneeFeatureVector.A5162 = aggregateDecision.A5162
	kneeFeatureVector.A5161 = aggregateDecision.A5161
	kneeFeatureVector.A5256 = aggregateDecision.A5256
	kneeFeatureVector.A5258 = aggregateDecision.A5258
	kneeFeatureVector.A5257 = aggregateDecision.A5257
	kneeFeatureVector.A5313 = aggregateDecision.A5313
	kneeFeatureVector.A5314 = aggregateDecision.A5314
	kneeFeatureVector.A5315 = aggregateDecision.A5315
	kneeFeatureVector.A5055 = aggregateDecision.A5055
	kneeFeatureVector.A5261 = aggregateDecision.A5261
	kneeFeatureVector.A5260 = aggregateDecision.A5260
	kneeFeatureVector.A5259 = aggregateDecision.A5259
	kneeFeatureVector.A5262 = aggregateDecision.A5262
	kneeFeatureVector.A5263 = aggregateDecision.A5263
	kneeFeatureVector.A5264 = aggregateDecision.A5264
		
	kneeFeatureVector.CONTENTION_BILATERAL = aggregateContention.TXT_BILATERAL
	kneeFeatureVector.CONTENTION_LEFT = aggregateContention.TXT_LEFT
	kneeFeatureVector.CONTENTION_KNEE = aggregateContention.TXT_KNEE
	kneeFeatureVector.CONTENTION_LEG = aggregateContention.TXT_LEG
	kneeFeatureVector.CONTENTION_RIGHT = aggregateContention.TXT_RIGHT
	kneeFeatureVector.CONTENTION_AMPUTATION = aggregateContention.TXT_AMPUTATION
	
	kneeFeatureVector.DECISION_BILATERAL = aggregateDecision.TXT_BILATERAL
	kneeFeatureVector.DECISION_LEFT = aggregateDecision.TXT_LEFT
	kneeFeatureVector.DECISION_RIGHT = aggregateDecision.TXT_RIGHT
	kneeFeatureVector.DECISION_KNEE = aggregateDecision.TXT_KNEE
	kneeFeatureVector.DECISION_IMPAIRMENT = aggregateDecision.TXT_IMPAIRMENT
	kneeFeatureVector.DECISION_LIMITATION = aggregateDecision.TXT_LIMITATION
	kneeFeatureVector.DECISION_AMPUTATION = aggregateDecision.TXT_AMPUTATION
	kneeFeatureVector.DECISION_ANKYLOSES = aggregateDecision.TXT_ANKYLOSES	
	
	if kneeFeatureVector.PRIOR_KNEE_CDD != kneeFeatureVector.CURR_KNEE_CDD:
		kneeClaimCount += 1
		
	kneeFeatureVector.KNEE_CLAIM_COUNT = kneeClaimCount
	
		
	writeCursor.execute(None, {'VET_ID' :kneeFeatureVector.VET_ID, 'CLAIM_ID' :kneeFeatureVector.CLAIM_ID, 'CLAIMANT_AGE' :kneeFeatureVector.CLAIMANT_AGE, 'DOB' :kneeFeatureVector.DOB, 'END_PRODUCT_CODE' :kneeFeatureVector.END_PRODUCT_CODE, 'RO_NUMBER' :kneeFeatureVector.RO_NUMBER, 'CLAIM_DATE' :kneeFeatureVector.CLAIM_DATE, 'PROFILE_DATE' :kneeFeatureVector.PROFILE_DATE, 'PROMULGATION_DATE' :kneeFeatureVector.PROMULGATION_DATE, 'CONTENTION_COUNT' :kneeFeatureVector.CONTENTION_COUNT, 'KNEE_CONTENTION_COUNT' :kneeFeatureVector.KNEE_CONTENTION_COUNT,
	'PRIOR_KNEE_CDD' :kneeFeatureVector.PRIOR_KNEE_CDD, 'QUANT_PRIOR_KNEE_CDD' :kneeFeatureVector.QUANT_PRIOR_KNEE_CDD, 'CURR_KNEE_CDD' :kneeFeatureVector.CURR_KNEE_CDD, 'QUANT_KNEE_CDD' :kneeFeatureVector.QUANT_KNEE_CDD, 'CLAIM_AGE' :kneeFeatureVector.CLAIM_AGE, 'KNEE_CDD_AGE' :kneeFeatureVector.KNEE_CDD_AGE, 'RECENT_KNEE_DATE' :kneeFeatureVector.RECENT_KNEE_DATE, 'KNEE_CLAIM_COUNT' :kneeFeatureVector.KNEE_CLAIM_COUNT,
	'C230' :kneeFeatureVector.C230, 'C270' :kneeFeatureVector.C270, 'C3690' :kneeFeatureVector.C3690, 'C3700' :kneeFeatureVector.C3700, 'C3710' :kneeFeatureVector.C3710, 'C8919' :kneeFeatureVector.C8919, 'C3720' :kneeFeatureVector.C3720, 'C3730' :kneeFeatureVector.C3730, 'C3780' :kneeFeatureVector.C3780, 'C3790' :kneeFeatureVector.C3790, 'C3800' :kneeFeatureVector.C3800, 
	'A5164' :kneeFeatureVector.A5164,  'A5165' :kneeFeatureVector.A5165,  'A5163' :kneeFeatureVector.A5163,  'A5162' :kneeFeatureVector.A5162,  'A5161' :kneeFeatureVector.A5161,  'A5256' :kneeFeatureVector.A5256,  'A5258' :kneeFeatureVector.A5258,  'A5257' :kneeFeatureVector.A5257,  'A5313' :kneeFeatureVector.A5313, 	'A5314' :kneeFeatureVector.A5314,  'A5315' :kneeFeatureVector.A5315,  'A5055' :kneeFeatureVector.A5055,  'A5261' :kneeFeatureVector.A5261, 	'A5260' :kneeFeatureVector.A5260,  'A5259' :kneeFeatureVector.A5259, 'A5262' :kneeFeatureVector.A5262, 'A5263' :kneeFeatureVector.A5263, 'A5264' :kneeFeatureVector.A5264,
	'CONTENTION_BILATERAL' :kneeFeatureVector.CONTENTION_BILATERAL, 'CONTENTION_LEFT' :kneeFeatureVector.CONTENTION_LEFT, 'CONTENTION_LEG' :kneeFeatureVector.CONTENTION_LEG,	'CONTENTION_KNEE' :kneeFeatureVector.CONTENTION_KNEE, 'CONTENTION_RIGHT' :kneeFeatureVector.CONTENTION_RIGHT, 'CONTENTION_AMPUTATION' :kneeFeatureVector.CONTENTION_AMPUTATION,
	'DECISION_BILATERAL' :kneeFeatureVector.DECISION_BILATERAL, 'DECISION_LEFT' :kneeFeatureVector.DECISION_LEFT, 'DECISION_RIGHT' :kneeFeatureVector.DECISION_RIGHT, 'DECISION_KNEE' :kneeFeatureVector.DECISION_KNEE, 'DECISION_IMPAIRMENT' :kneeFeatureVector.DECISION_IMPAIRMENT, 'DECISION_LIMITATION' :kneeFeatureVector.DECISION_LIMITATION, 'DECISION_AMPUTATION' :kneeFeatureVector.DECISION_AMPUTATION, 'DECISION_ANKYLOSES' :kneeFeatureVector.DECISION_ANKYLOSES})
	
	
	counter += 1
			
			

connection.commit()
print(str(datetime.datetime.now()))

writeCursor.close()
priorDecisionCursor.close()
currDecisionCursor.close()
cursor.close()
connection.close()
