import os
import re
import cx_Oracle
import collections
import datetime

kneeDiagnosisCode = [6100,6200,6201,6202,6204,6205,6207,6209,6210,6211,6260]

#Primary query, Look for all decisions where a claim has been processed already. Much of the filtering is based on the engineering notebook, 
# the only add on is the prev_evaltn_ind which is a poor flag mechanism in Ratings.
#Because of historical copies being represented across all rating profiles grouping is required to clean up the data.
#Organize them based first by participant id, then profile date, disability id, begin date, then code and percent.
SQL="select rd.ptcpnt_vet_id, rd.prfil_dt, rd.begin_dt, rd.end_dt, rd.prmlgn_dt, rd.dgnstc_txt, rd.dsblty_id, rd.diagnosis_code, rd.hypntd_dgnstc_type_cd, rd.prcnt_nbr \
	from AH4929_RATING_DECISION rd \
	inner join KNEE_AGGREGATE_CONTENTION ac on ac.vet_id = rd.ptcpnt_vet_id \
	where rd.begin_dt IS NOT NULL and rd.begin_dt < rd.prmlgn_dt and (rd.end_dt is NULL or rd.end_dt >= rd.prmlgn_dt) and rd.system_type_cd = 'C' \
	and rd.dsblty_decn_type_cd = 'SVCCONNCTED' and (rd.prev_evaltn_ind IS NULL OR rd.prev_evaltn_ind = 'N') \
	group by rd.begin_dt, rd.end_dt, rd.prmlgn_dt, rd.dgnstc_txt, rd.dsblty_id, rd.diagnosis_code, rd.hypntd_dgnstc_type_cd, rd.prcnt_nbr, rd.ptcpnt_vet_id, rd.prfil_dt \
	order by ptcpnt_vet_id,prfil_dt,dsblty_id,begin_dt,diagnosis_code,prcnt_nbr"

class DecisionPercentage:
	def __init__(self,code,percentage):
		self.code = code
		self.percentage = percentage
		
class AggregateDecision:
	def __init__(self):
		self.VET_ID = None
		self.PROFILE_DATE = None
		self.PROMULGATION_DATE = None
		self.RECENT_KNEE_DATE = None
		self.CDD = 0
		self.KNEE_CDD = 0
		self.A5164 = 0
		self.A5165 = 0
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
		self.TXT_BILATERAL = 0		
		self.TXT_LEFT = 0
		self.TXT_RIGHT = 0
		self.TXT_KNEE = 0
		self.TXT_IMPAIRMENT = 0
		self.TXT_LIMITATION = 0
		self.TXT_AMPUTATION = 0
		self.TXT_ANKYLOSES = 0
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))
		
		
class Decision:
	def __init__(self, ptcpnt_vet_id, prfil_dt, begin_dt, end_dt, prmlgn_dt, dgnstc_txt, dsblty_id, diagnosis_code, hypntd_dgnstc_type_cd, prcnt_nbr):
		self.ptcpnt_vet_id = ptcpnt_vet_id
		self.prfil_dt = prfil_dt
		self.begin_dt = begin_dt
		self.end_dt = end_dt
		self.prmlgn_dt = prmlgn_dt
		if dgnstc_txt is None:
			self.dgnstc_txt = ''
		else:
			self.dgnstc_txt = dgnstc_txt
		self.dsblty_id = dsblty_id
		if diagnosis_code == 'Unknown':
			self.diagnosis_code = -99
		else:		
			self.diagnosis_code = int(diagnosis_code)
		self.hypntd_dgnstc_type_cd = hypntd_dgnstc_type_cd
		
		if prcnt_nbr is None:
			print(ptcpnt_vet_id)
			self.prcnt_nbr = 0
		else:
			self.prcnt_nbr = int(prcnt_nbr)
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))		

print(str(datetime.datetime.now()))
connection = cx_Oracle.connect('developer/<pass>@127.0.0.1:1521/DEV.BCDSS')
cursor = connection.cursor()
cursor.execute(SQL)

writeCursor = connection.cursor()
writeCursor.prepare('INSERT INTO DEVELOPER.KNEE_AGGREGATE_DECISION (VET_ID, PROFILE_DATE, PROMULGATION_DATE, RECENT_KNEE_DATE, CDD, KNEE_CDD, A5164, A5165, A5163, A5162, A5161, A5256, A5258, A5257, A5313, A5314, A5315, A5055, A5261, A5260, A5259, TXT_BILATERAL,TXT_LEFT,TXT_RIGHT,TXT_KNEE,TXT_IMPAIRMENT,TXT_LIMITATION,TXT_AMPUTATION,TXT_ANKYLOSES) \
VALUES (:VET_ID, :PROFILE_DATE, :PROMULGATION_DATE, :RECENT_KNEE_DATE, :CDD, :KNEE_CDD, \
:A5164, :A5165, :A5163, :A5162, :A5161, :A5256, :A5258, :A5257, :A5313,	:A5314, :A5315, :A5055, :A5261,	:A5260, :A5259, \
:TXT_BILATERAL,:TXT_LEFT,:TXT_RIGHT,:TXT_KNEE,:TXT_IMPAIRMENT,:TXT_LIMITATION,:TXT_AMPUTATION,:TXT_ANKYLOSES)')

aggregateDecision = None
currRatingProfile = -1
counter = 0;
hasMultipleDisabilityCodes = collections.Counter()
recentKneeBeginDate = collections.Counter()
totalCDD = 1;
totalKneeCDD = 1;


for row in cursor:
	if counter == 1000: #Commit every 1000 records. Improvement would be to look into aggregate inserts
		connection.commit()
		counter=0
		
	decision = Decision(row[0],row[1],row[2],row[3],row[4],row[5],row[6],row[7],row[8],row[9]) #Map loose fields into a Contention object. (Contention is a convenience object)
	
	if currRatingProfile != decision.prfil_dt: #Process insert statement and reset aggregation variables when profile date changes
		if currRatingProfile != -1:	#Skip if first time through

			#Calculate the CDD values
			for disabilityPercentage in multipleDisabilityCodes.values():				
				totalCDD *= (1 - (disabilityPercentage.percentage / 100))
				if disabilityPercentage.code in kneeDiagnosisCode:
					totalKneeCDD *= (1 - (disabilityPercentage.percentage / 100))	
					
			aggregateDecision.CDD = 100 * (1 - totalCDD)
			aggregateDecision.KNEE_CDD = 100 * (1 - totalKneeCDD)
			
			if recentKneeBeginDate[currRatingProfile] == 0: #Oracle will use the number 0 to indicate it has not been set, empty values list does not appear to work
				aggregateDecision.RECENT_KNEE_DATE = None
			else:
				aggregateDecision.RECENT_KNEE_DATE = recentKneeBeginDate[currRatingProfile]

			writeCursor.execute(None, {'VET_ID' :aggregateDecision.VET_ID, 'PROFILE_DATE' :aggregateDecision.PROFILE_DATE, 'PROMULGATION_DATE' :aggregateDecision.PROMULGATION_DATE, 'RECENT_KNEE_DATE' :aggregateDecision.RECENT_KNEE_DATE, 'CDD' :aggregateDecision.CDD, 'KNEE_CDD' :aggregateDecision.KNEE_CDD, 
			'A5164' :aggregateDecision.A5164,  'A5165' :aggregateDecision.A5165,  'A5163' :aggregateDecision.A5163,  'A5162' :aggregateDecision.A5162,  'A5161' :aggregateDecision.A5161,  'A5256' :aggregateDecision.A5256,  'A5258' :aggregateDecision.A5258,  'A5257' :aggregateDecision.A5257,  'A5313' :aggregateDecision.A5313, 	'A5314' :aggregateDecision.A5314,  'A5315' :aggregateDecision.A5315,  'A5055' :aggregateDecision.A5055,  'A5261' :aggregateDecision.A5261, 	'A5260' :aggregateDecision.A5260,  'A5259' :aggregateDecision.A5259, 
			'TXT_BILATERAL' :aggregateDecision.TXT_BILATERAL, 'TXT_LEFT' :aggregateDecision.TXT_LEFT, 'TXT_RIGHT' :aggregateDecision.TXT_RIGHT, 'TXT_KNEE' :aggregateDecision.TXT_KNEE, 'TXT_IMPAIRMENT' :aggregateDecision.TXT_IMPAIRMENT, 'TXT_LIMITATION' :aggregateDecision.TXT_LIMITATION, 'TXT_AMPUTATION' :aggregateDecision.TXT_AMPUTATION, 'TXT_ANKYLOSES' :aggregateDecision.TXT_ANKYLOSES})
			
			counter += 1
			
		#Reset the counters
		totalCDD = 1
		totalKneeCDD = 1
		currRatingProfile = decision.prfil_dt
		multipleDisabilityCodes = collections.Counter()
		recentKneeBeginDate = collections.Counter()
		
		#Capture all rating profile level items that do not change per contention
		aggregateDecision = AggregateDecision()
		aggregateDecision.VET_ID = decision.ptcpnt_vet_id
		aggregateDecision.PROFILE_DATE = currRatingProfile
		aggregateDecision.PROMULGATION_DATE = decision.prmlgn_dt
		
	#Since we are ordering by disability id then begin_dt we choose the most recent percent number
	multipleDisabilityCodes[decision.dsblty_id] = DecisionPercentage(decision.diagnosis_code, decision.prcnt_nbr)
	
	if decision.diagnosis_code in kneeDiagnosisCode: #Is the diagnosis an ear?
		if recentKneeBeginDate[currRatingProfile] == 0 or decision.begin_dt > recentKneeBeginDate[currRatingProfile]: #Is the date container empty, or is the knee decision date more recent?
			recentKneeBeginDate[currRatingProfile] = decision.begin_dt #Set it
		
			
	#Use regex to look for a hit and then if it hits make it true. No need to track how many times, just true or false
	try:
		if re.search("BilaterAL",decision.dgnstc_txt,re.IGNORECASE):
			aggregateDecision.TXT_BILATER = 1	
		if re.search("Left",decision.dgnstc_txt,re.IGNORECASE):
			aggregateDecision.TXT_LEFT = 1
		if re.search("Right",decision.dgnstc_txt,re.IGNORECASE):
			aggregateDecision.TXT_RIGHT = 1
		if re.search("Knee",decision.dgnstc_txt,re.IGNORECASE):
			aggregateDecision.TXT_KNEE = 1
		if re.search("Impairment",decision.dgnstc_txt,re.IGNORECASE):
			aggregateDecision.TXT_IMPAIRMENT = 1
		if re.search("Limitation",decision.dgnstc_txt,re.IGNORECASE):
			aggregateDecision.TXT_LIMITATION = 1
		if re.search("Amputation",decision.dgnstc_txt,re.IGNORECASE):
			aggregateDecision.TXT_AMPUTATION = 1
		if re.search("Ankyloses",decision.dgnstc_txt,re.IGNORECASE):
			aggregateDecision.TXT_ANKYLOSES = 1
			
	except TypeError:
		print(decision)

	#Simply test the codes and again true or false
	if decision.diagnosis_code == 5164:
		aggregateDecision.A5164 = 1
	if decision.diagnosis_code == 5165:
		aggregateDecision.A5165 = 1
	if decision.diagnosis_code == 5163:
		aggregateDecision.A5163 = 1
	if decision.diagnosis_code == 5162:
		aggregateDecision.A5162 = 1
	if decision.diagnosis_code == 5161:
		aggregateDecision.A5161 = 1
	if decision.diagnosis_code == 5256:
		aggregateDecision.A5256 = 1
	if decision.diagnosis_code == 5258:
		aggregateDecision.A5258 = 1
	if decision.diagnosis_code == 5257:
		aggregateDecision.A5257 = 1
	if decision.diagnosis_code == 5313:
		aggregateDecision.A5313 = 1
	if decision.diagnosis_code == 5314:
		aggregateDecision.A5314 = 1
	if decision.diagnosis_code == 5315:
		aggregateDecision.A5315 = 1
	if decision.diagnosis_code == 5055:
		aggregateDecision.A5055 = 1
	if decision.diagnosis_code == 5261:
		aggregateDecision.A5261 = 1
	if decision.diagnosis_code == 5260:
		aggregateDecision.A5260 = 1
	if decision.diagnosis_code == 5259:
		aggregateDecision.A5259 = 1
		
#A bit strange looking but due to Python's identation approach this occurs after the for loop in order to capture the last claim.
for disabilityPercentage in multipleDisabilityCodes.values():
	#Calculate the CDD values
	totalCDD *= (1 - (disabilityPercentage.percentage / 100))
	if disabilityPercentage.code in kneeDiagnosisCode:
		totalKneeCDD *= (1 - (disabilityPercentage.percentage / 100))	

aggregateDecision.CDD = 100 * (1 - totalCDD)
aggregateDecision.KNEE_CDD = 100 * (1 - totalKneeCDD)
if recentKneeBeginDate[currRatingProfile] == 0:
	aggregateDecision.RECENT_KNEE_DATE = None
else:
	aggregateDecision.RECENT_KNEE_DATE = recentKneeBeginDate[currRatingProfile]

writeCursor.execute(None, {'VET_ID' :aggregateDecision.VET_ID, 'PROFILE_DATE' :aggregateDecision.PROFILE_DATE, 'PROMULGATION_DATE' :aggregateDecision.PROMULGATION_DATE, 'RECENT_KNEE_DATE' :aggregateDecision.RECENT_KNEE_DATE, 'CDD' :aggregateDecision.CDD, 'KNEE_CDD' :aggregateDecision.KNEE_CDD, 
'A5164' :aggregateDecision.A5164,  'A5165' :aggregateDecision.A5165,  'A5163' :aggregateDecision.A5163,  'A5162' :aggregateDecision.A5162,  'A5161' :aggregateDecision.A5161,  'A5256' :aggregateDecision.A5256,  'A5258' :aggregateDecision.A5258,  'A5257' :aggregateDecision.A5257,  'A5313' :aggregateDecision.A5313, 	'A5314' :aggregateDecision.A5314,  'A5315' :aggregateDecision.A5315,  'A5055' :aggregateDecision.A5055,  'A5261' :aggregateDecision.A5261, 	'A5260' :aggregateDecision.A5260,  'A5259' :aggregateDecision.A5259, 
'TXT_BILATERAL' :aggregateDecision.TXT_BILATERAL, 'TXT_LEFT' :aggregateDecision.TXT_LEFT, 'TXT_RIGHT' :aggregateDecision.TXT_RIGHT, 'TXT_KNEE' :aggregateDecision.TXT_KNEE, 'TXT_IMPAIRMENT' :aggregateDecision.TXT_IMPAIRMENT, 'TXT_LIMITATION' :aggregateDecision.TXT_LIMITATION, 'TXT_AMPUTATION' :aggregateDecision.TXT_AMPUTATION, 'TXT_ANKYLOSES' :aggregateDecision.TXT_ANKYLOSES})

connection.commit()
print(str(datetime.datetime.now()))

writeCursor.close()
cursor.close()
connection.close()
