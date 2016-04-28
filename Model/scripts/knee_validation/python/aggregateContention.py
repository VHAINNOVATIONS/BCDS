import os
import re
import cx_Oracle
import collections
import datetime

kneeContentionCode = [230,270,3690,3700,3710,8919,3720,3730,3780,3790,3800]

#Primary query, Look for all claims/contentions where the participant has at least one contention with an knee-related contention code.
#Organize them based first by participant id, then claim id and finally by profile date descending.
SQL="select rcc.ptcpnt_vet_id, \
	bnft_claim_id, \
	date_of_claim, \
	prfil_dt, \
	claim_ro_number, \
	cntntn_id, \
	cntntn_clsfcn_id, \
	cntntn_clmant_txt, \
	p.dob, \
	end_prdct_type_cd \
	from combo_knee_corp_claim rcc \
  left join ah2626_person p on p.ptcpnt_vet_id = rcc.ptcpnt_vet_id \
  inner join v_knee_claim_source cs on cs.vet_id = rcc.ptcpnt_vet_id and cs.claim_id = rcc.bnft_claim_id \
	where prfil_dt >= date_of_claim \
	order by rcc.ptcpnt_vet_id desc,bnft_claim_id,prfil_dt"

class AggregateContention:
	def __init__(self):
		self.VET_ID = None
		self.CLAIM_ID = None
		self.DOB = 0
		self.END_PRODUCT_CODE = None
		self.RO_NUMBER = 0
		self.CLAIM_DATE = None
		self.MAX_PROFILE_DATE = None
		self.CONTENTION_COUNT = 0
		self.KNEE_CONTENTION_COUNT = 0
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
		self.TXT_BILATERAL = 0		
		self.TXT_LEFT = 0
		self.TXT_RIGHT = 0
		self.TXT_KNEE = 0
		self.TXT_LEG = 0
		self.TXT_AMPUTATION = 0
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))
		
		
class Contention:
	def __init__(self, ptcpnt_vet_id, bnft_claim_id, claim_date, prfil_dt, claim_ro_number, cntntn_id, cntntn_clsfcn_id, cntntn_clmant_txt, dob, end_prdct_type_cd):
		self.ptcpnt_vet_id = ptcpnt_vet_id
		self.bnft_claim_id = bnft_claim_id
		self.claim_date = claim_date
		self.prfil_dt = prfil_dt
		self.claim_ro_number = claim_ro_number
		self.cntntn_id = cntntn_id
		self.cntntn_clsfcn_id = cntntn_clsfcn_id
		self.cntntn_clmant_txt = cntntn_clmant_txt
		if not dob is None:
			self.dob = int(dob)
		else:
			self.dob = None
		self.end_prdct_type_cd = end_prdct_type_cd
		
		
	def __str__(self):
		from pprint import pprint
		return str(vars(self))		

connection = cx_Oracle.connect('developer/D3vVV0Rd@127.0.0.1:1521/DEV.BCDSS')
writeCursor = connection.cursor()
writeCursor.prepare('INSERT INTO DEVELOPER.V_KNEE_AGGREGATE_CONTENTION (VET_ID, CLAIM_ID, END_PRODUCT_CODE, CLAIM_DATE, CONTENTION_COUNT, KNEE_CONTENTION_COUNT, C230, C270, C3690, C3700, C3710, C8919, C3720, C3730, C3780, C3790, C3800, TXT_BILATERAL,TXT_LEFT,TXT_RIGHT,TXT_LEG,TXT_KNEE,TXT_AMPUTATION, DOB, RO_NUMBER, MAX_PROFILE_DATE) \
VALUES (:VET_ID, :CLAIM_ID, :END_PRODUCT_CODE, :CLAIM_DATE, :CONTENTION_COUNT, :KNEE_CONTENTION_COUNT, \
 :C230, :C270, :C3690, :C3700, :C3710, :C8919, :C3720, :C3730, :C3780, :C3790, :C3800, \
 :TXT_BILATERAL, :TXT_LEFT, :TXT_RIGHT, :TXT_LEG, :TXT_KNEE, :TXT_AMPUTATION, \
 :DOB, :RO_NUMBER, :MAX_PROFILE_DATE)')


print(str(datetime.datetime.now()))
cursor = connection.cursor()
cursor.execute(SQL)

aggregateContention = None
counterAggregateContention = None
totalContentions = None
totalKneeContentions = None
maxProfileDate = None

currBenefitClaim = -1
currParticipant = -1
counter = 0;

for row in cursor:
	if counter == 1000: #Commit every 1000 records. Improvement would be to look into aggregate inserts
		connection.commit()
		counter=0
		
	contention = Contention(row[0],row[1],row[2],row[3],row[4],row[5],row[6], row[7], row[8], row[9]) #Map loose fields into a Contention object. (Contention is a convenience object)
	

	if currBenefitClaim != contention.bnft_claim_id: #Process insert statement and reset aggregation variables when claim id changes

		if currBenefitClaim != -1: #Skip if first time through
			#Perform all aggregation calculations before inserting and resetting
			aggregateContention.CONTENTION_COUNT = sum(totalContentions.values()) 
			aggregateContention.KNEE_CONTENTION_COUNT = sum(totalKneeContentions.values())
			aggregateContention.MAX_PROFILE_DATE = maxProfileDate[currBenefitClaim]
			
			writeCursor.execute(None, {'VET_ID' :aggregateContention.VET_ID, 'CLAIM_ID' :aggregateContention.CLAIM_ID, 'END_PRODUCT_CODE' :aggregateContention.END_PRODUCT_CODE, 'CLAIM_DATE' :aggregateContention.CLAIM_DATE, 'CONTENTION_COUNT' :aggregateContention.CONTENTION_COUNT, 'KNEE_CONTENTION_COUNT' :aggregateContention.KNEE_CONTENTION_COUNT, 
			'C230' :counterAggregateContention.C230, 'C270' :counterAggregateContention.C270, 'C3690' :counterAggregateContention.C3690, 'C3700' :counterAggregateContention.C3700, 'C3710' :counterAggregateContention.C3710, 'C8919' :counterAggregateContention.C8919, 'C3720' :counterAggregateContention.C3720, 'C3730' :counterAggregateContention.C3730, 'C3780' :counterAggregateContention.C3780, 'C3790' :counterAggregateContention.C3790, 'C3800' :counterAggregateContention.C3800, 
			'TXT_BILATERAL' :counterAggregateContention.TXT_BILATERAL, 'TXT_LEFT' :counterAggregateContention.TXT_LEFT, 'TXT_LEG' :counterAggregateContention.TXT_LEG,	'TXT_KNEE' :counterAggregateContention.TXT_KNEE, 'TXT_RIGHT' :counterAggregateContention.TXT_RIGHT, 'TXT_AMPUTATION' :counterAggregateContention.TXT_AMPUTATION,
			'DOB' :aggregateContention.DOB, 'RO_NUMBER' :aggregateContention.RO_NUMBER, 'MAX_PROFILE_DATE' :aggregateContention.MAX_PROFILE_DATE})

			counter += 1
		
		currBenefitClaim = contention.bnft_claim_id #Reset claim id
		
		if currParticipant != contention.ptcpnt_vet_id : 
			currParticipant = contention.ptcpnt_vet_id #Reset participant id
			counterAggregateContention = AggregateContention()		
		
		#Capture all claim/person level items that do not change per contention
		aggregateContention = AggregateContention()
		aggregateContention.VET_ID = contention.ptcpnt_vet_id
		aggregateContention.CLAIM_ID = currBenefitClaim
		aggregateContention.RO_NUMBER = contention.claim_ro_number
		aggregateContention.DOB = contention.dob
		aggregateContention.CLAIM_DATE = contention.claim_date
		aggregateContention.END_PRODUCT_CODE = contention.end_prdct_type_cd
		
		#Reset the counters
		totalContentions = collections.Counter();
		totalKneeContentions = collections.Counter();
		maxProfileDate = collections.Counter();
		
	maxProfileDate[currBenefitClaim] = contention.prfil_dt #If a claim has multiple profile dates, because of the sorting, we always end up with the most recent profile date
	totalContentions[currBenefitClaim] += 1 #For every contention add one
	if contention.cntntn_clsfcn_id in kneeContentionCode:
		totalKneeContentions[currBenefitClaim] +=1 #For any contention that is knee-related, add one
			
	#Use regex to look for a hit and then if it hits make it true. No need to track how many times, just true or false
	if re.search("Bilateral",contention.cntntn_clmant_txt,re.IGNORECASE):
		counterAggregateContention.TXT_BILATERAL += 1
	if re.search("Left",contention.cntntn_clmant_txt,re.IGNORECASE):
		counterAggregateContention.TXT_LEFT += 1
	if re.search("Leg",contention.cntntn_clmant_txt,re.IGNORECASE):
		counterAggregateContention.TXT_LEG += 1
	if re.search("Knee",contention.cntntn_clmant_txt,re.IGNORECASE):
		counterAggregateContention.TXT_KNEE += 1
	if re.search("Right",contention.cntntn_clmant_txt,re.IGNORECASE):
		counterAggregateContention.TXT_RIGHT += 1
	if re.search("Amputation",contention.cntntn_clmant_txt,re.IGNORECASE):
		counterAggregateContention.TXT_AMPUTATION += 1

	#Simply test the codes and again true or false
	if contention.cntntn_clsfcn_id == 230:
		counterAggregateContention.C230 += 1
	if contention.cntntn_clsfcn_id == 270:
		counterAggregateContention.C270 += 1
	if contention.cntntn_clsfcn_id == 3690:
		counterAggregateContention.C3690 += 1
	if contention.cntntn_clsfcn_id == 3700:
		counterAggregateContention.C3700 += 1
	if contention.cntntn_clsfcn_id == 3710:
		counterAggregateContention.C3710 += 1
	if contention.cntntn_clsfcn_id == 8919:
		counterAggregateContention.C8919 += 1
	if contention.cntntn_clsfcn_id == 3720:
		counterAggregateContention.C3720 += 1
	if contention.cntntn_clsfcn_id == 3730:
		counterAggregateContention.C3730 += 1
	if contention.cntntn_clsfcn_id == 3780:
		counterAggregateContention.C3780 += 1
	if contention.cntntn_clsfcn_id == 3790:
		counterAggregateContention.C3790 += 1
	if contention.cntntn_clsfcn_id == 3800:
		counterAggregateContention.C3800 += 1
		
#A bit strange looking but due to Python's identation approach this occurs after the for loop in order to capture the last claim.
aggregateContention.CONTENTION_COUNT = sum(totalContentions.values())
aggregateContention.KNEE_CONTENTION_COUNT = sum(totalKneeContentions.values())
aggregateContention.MAX_PROFILE_DATE = maxProfileDate[currBenefitClaim]
			
writeCursor.execute(None, {'VET_ID' :aggregateContention.VET_ID, 'CLAIM_ID' :aggregateContention.CLAIM_ID, 'END_PRODUCT_CODE' :aggregateContention.END_PRODUCT_CODE, 'CLAIM_DATE' :aggregateContention.CLAIM_DATE, 'CONTENTION_COUNT' :aggregateContention.CONTENTION_COUNT, 'KNEE_CONTENTION_COUNT' :aggregateContention.KNEE_CONTENTION_COUNT, 
'C230' :counterAggregateContention.C230, 'C270' :counterAggregateContention.C270, 'C3690' :counterAggregateContention.C3690, 'C3700' :counterAggregateContention.C3700, 'C3710' :counterAggregateContention.C3710, 'C8919' :counterAggregateContention.C8919, 'C3720' :counterAggregateContention.C3720, 'C3730' :counterAggregateContention.C3730, 'C3780' :counterAggregateContention.C3780, 'C3790' :counterAggregateContention.C3790, 'C3800' :counterAggregateContention.C3800, 
'TXT_BILATERAL' :counterAggregateContention.TXT_BILATERAL, 'TXT_LEFT' :counterAggregateContention.TXT_LEFT, 'TXT_LEG' :counterAggregateContention.TXT_LEG,	'TXT_KNEE' :counterAggregateContention.TXT_KNEE, 'TXT_RIGHT' :counterAggregateContention.TXT_RIGHT, 'TXT_AMPUTATION' :counterAggregateContention.TXT_AMPUTATION,
'DOB' :aggregateContention.DOB, 'RO_NUMBER' :aggregateContention.RO_NUMBER, 'MAX_PROFILE_DATE' :aggregateContention.MAX_PROFILE_DATE})

connection.commit()
print(str(datetime.datetime.now()))

writeCursor.close()
cursor.close()
connection.close()
