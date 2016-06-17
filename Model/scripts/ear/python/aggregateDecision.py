import os
import re
import cx_Oracle
import collections
import datetime

earDiagnosisCode = [6100,6200,6201,6202,6204,6205,6207,6209,6210,6211,6260]

#Primary query, Look for all decisions where a claim has been processed already. Much of the filtering is based on the engineering notebook, the only add on is the prev_evaltn_ind which is a poor flag mechanism in Ratings.
#Because of historical copies being represented across all rating profiles grouping is required to clean up the data.
#Organize them based first by participant id, then profile date, disability id, begin date, then code and percent.
SQL="select rd.ptcpnt_vet_id, rd.prfil_dt, rd.begin_dt, rd.end_dt, rd.prmlgn_dt, rd.dgnstc_txt, rd.dsblty_id, rd.diagnosis_code, rd.hypntd_dgnstc_type_cd, rd.prcnt_nbr \
	from AH2626_RATING_DECISION rd \
	inner join EAR_AGGREGATE_CONTENTION ac on ac.vet_id = rd.ptcpnt_vet_id \
	where rd.begin_dt IS NOT NULL and rd.begin_dt < rd.prmlgn_dt and (rd.end_dt is NULL or rd.end_dt >= rd.prmlgn_dt) and rd.system_type_cd = 'C' and rd.dsblty_decn_type_cd in ('SVCCONNCTED','1151GRANTED')  \
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
		self.RECENT_EAR_DATE = None
		self.CDD = 0
		self.EAR_CDD = None
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
		self.TXT_LOSS = 0
		self.TXT_TINITU = 0
		
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
connection = cx_Oracle.connect('developer/D3vVV0Rd@127.0.0.1:1521/DEV.BCDSS')
cursor = connection.cursor()
cursor.execute(SQL)

writeCursor = connection.cursor()
writeCursor.prepare('INSERT INTO DEVELOPER.EAR_AGGREGATE_DECISION (VET_ID, PROFILE_DATE, PROMULGATION_DATE, RECENT_EAR_DATE, CDD, EAR_CDD, A6100, A6200,A6201,A6202,A6204,A6205,A6207,A6209,A6210,A6211,A6260,TXT_LOSS,TXT_TINITU) \
VALUES (:VET_ID, :PROFILE_DATE, :PROMULGATION_DATE, :RECENT_EAR_DATE, :CDD, :EAR_CDD, \
:A6100, :A6200, :A6201, :A6202, :A6204, :A6205, :A6207, :A6209, :A6210, :A6211, :A6260, \
:TXT_LOSS, :TXT_TINITU)')

aggregateDecision = None
currRatingProfile = -1
currParticipant = -1
counter = 0
hasMultipleDisabilityCodes = collections.Counter()
recentEarBeginDate = collections.Counter()
totalCDD = 1
totalEarCDD = 1
hasEarCDD = False


for row in cursor:
	if counter == 1000: #Commit every 1000 records. Improvement would be to look into aggregate inserts
		connection.commit()
		counter=0
		
	decision = Decision(row[0],row[1],row[2],row[3],row[4],row[5],row[6],row[7],row[8],row[9]) #Map loose fields into a Contention object. (Contention is a convenience object)
	
	if currParticipant != decision.ptcpnt_vet_id or currRatingProfile != decision.prfil_dt: #Process insert statement and reset aggregation variables when profile date changes
		if currRatingProfile != -1:	#Skip if first time through

			#Calculate the CDD values
			for disabilityPercentage in multipleDisabilityCodes.values():				
				totalCDD *= (1 - (disabilityPercentage.percentage / 100))
				if disabilityPercentage.code in earDiagnosisCode:
					hasEarCDD = True
					totalEarCDD *= (1 - (disabilityPercentage.percentage / 100))	
					
			aggregateDecision.CDD = 100 * (1 - totalCDD)			
			
			if hasEarCDD:
				aggregateDecision.EAR_CDD = 100 * (1 - totalEarCDD)
			else:
				aggregateDecision.EAR_CDD = None
			
			if recentEarBeginDate[currRatingProfile] == 0: #Oracle will use the number 0 to indicate it has not been set, empty values list does not appear to work
				aggregateDecision.RECENT_EAR_DATE = None
			else:
				aggregateDecision.RECENT_EAR_DATE = recentEarBeginDate[currRatingProfile]

			writeCursor.execute(None, {'VET_ID' :aggregateDecision.VET_ID, 'PROFILE_DATE' :aggregateDecision.PROFILE_DATE, 'PROMULGATION_DATE' :aggregateDecision.PROMULGATION_DATE, 'RECENT_EAR_DATE' :aggregateDecision.RECENT_EAR_DATE, 'CDD' :aggregateDecision.CDD, 'EAR_CDD' :aggregateDecision.EAR_CDD, 
			'A6100' :aggregateDecision.A6100, 'A6200' :aggregateDecision.A6200, 'A6201' :aggregateDecision.A6201, 'A6202' :aggregateDecision.A6202, 'A6204' :aggregateDecision.A6204, 'A6205' :aggregateDecision.A6205, 'A6207' :aggregateDecision.A6207, 'A6209' :aggregateDecision.A6209, 'A6210' :aggregateDecision.A6210, 'A6211' :aggregateDecision.A6211, 'A6260' :aggregateDecision.A6260,
			'TXT_LOSS' :aggregateDecision.TXT_LOSS, 'TXT_TINITU' :aggregateDecision.TXT_TINITU})
			
			counter += 1
			
		#Reset the counters
		totalCDD = 1
		totalEarCDD = 1
		hasEarCDD = False
		currRatingProfile = decision.prfil_dt
		currParticipant = decision.ptcpnt_vet_id
		multipleDisabilityCodes = collections.Counter()
		recentEarBeginDate = collections.Counter()
		
		#Capture all rating profile level items that do not change per contention
		aggregateDecision = AggregateDecision()
		aggregateDecision.VET_ID = decision.ptcpnt_vet_id
		aggregateDecision.PROFILE_DATE = currRatingProfile
		aggregateDecision.PROMULGATION_DATE = decision.prmlgn_dt
		
	#Since we are ordering by disability id then begin_dt we choose the most recent percent number
	multipleDisabilityCodes[decision.dsblty_id] = DecisionPercentage(decision.diagnosis_code, decision.prcnt_nbr)
	
	if decision.diagnosis_code in earDiagnosisCode: #Is the diagnosis an ear?
		if recentEarBeginDate[currRatingProfile] == 0 or decision.begin_dt > recentEarBeginDate[currRatingProfile]: #Is the date container empty, or is the ear decision date more recent?
			recentEarBeginDate[currRatingProfile] = decision.begin_dt #Set it
		
			
	#Use regex to look for a hit and then if it hits make it true. No need to track how many times, just true or false
	if re.search("Loss",decision.dgnstc_txt,re.IGNORECASE):
		aggregateDecision.TXT_LOSS += 1
	if re.search("Tinnitus",decision.dgnstc_txt,re.IGNORECASE):
		aggregateDecision.TXT_TINITU += 1
			
	#Simply test the codes and again true or false
	if decision.diagnosis_code == 6100:
		aggregateDecision.A6100 += 1
	if decision.diagnosis_code == 6200:
		aggregateDecision.A6200 += 1
	if decision.diagnosis_code == 6201:
		aggregateDecision.A6201 += 1
	if decision.diagnosis_code == 6202:
		aggregateDecision.A6202 += 1
	if decision.diagnosis_code == 6204:
		aggregateDecision.A6204 += 1
	if decision.diagnosis_code == 6205:
		aggregateDecision.A6205 += 1
	if decision.diagnosis_code == 6207:
		aggregateDecision.A6207 += 1
	if decision.diagnosis_code == 6209:
		aggregateDecision.A6209 += 1
	if decision.diagnosis_code == 6210:
		aggregateDecision.A6210 += 1
	if decision.diagnosis_code == 6211:
		aggregateDecision.A6211 += 1
	if decision.diagnosis_code == 6260:
		aggregateDecision.A6260 += 1
		
#A bit strange looking but due to Python's identation approach this occurs after the for loop in order to capture the last claim.
for disabilityPercentage in multipleDisabilityCodes.values():
	#Calculate the CDD values
	totalCDD *= (1 - (disabilityPercentage.percentage / 100))
	if disabilityPercentage.code in earDiagnosisCode:
		hasEarCDD = True
		totalEarCDD *= (1 - (disabilityPercentage.percentage / 100))	

aggregateDecision.CDD = 100 * (1 - totalCDD)
if hasEarCDD:
	aggregateDecision.EAR_CDD = 100 * (1 - totalEarCDD)
else:
	aggregateDecision.EAR_CDD = None
				
if recentEarBeginDate[currRatingProfile] == 0:
	aggregateDecision.RECENT_EAR_DATE = None
else:
	aggregateDecision.RECENT_EAR_DATE = recentEarBeginDate[currRatingProfile]

writeCursor.execute(None, {'VET_ID' :aggregateDecision.VET_ID, 'PROFILE_DATE' :aggregateDecision.PROFILE_DATE, 'PROMULGATION_DATE' :aggregateDecision.PROMULGATION_DATE, 'RECENT_EAR_DATE' :aggregateDecision.RECENT_EAR_DATE, 'CDD' :aggregateDecision.CDD, 'EAR_CDD' :aggregateDecision.EAR_CDD, 
			'A6100' :aggregateDecision.A6100, 'A6200' :aggregateDecision.A6200, 'A6201' :aggregateDecision.A6201, 'A6202' :aggregateDecision.A6202, 'A6204' :aggregateDecision.A6204, 'A6205' :aggregateDecision.A6205, 'A6207' :aggregateDecision.A6207, 'A6209' :aggregateDecision.A6209, 'A6210' :aggregateDecision.A6210, 'A6211' :aggregateDecision.A6211, 'A6260' :aggregateDecision.A6260,
			'TXT_LOSS' :aggregateDecision.TXT_LOSS, 'TXT_TINITU' :aggregateDecision.TXT_TINITU})
connection.commit()
print(str(datetime.datetime.now()))

writeCursor.close()
cursor.close()
connection.close()
