import os
import re
import cx_Oracle
import collections
import datetime

earContentionCode = [2200,2210,2220,3140,3150,4130,4210,4700,4920,6850]

#Primary query, Look for all claims/contentions where the participant has at least one contention with an ear-related contention code.
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
	from ah4929_rating_corp_claim rcc \
  left join ah4929_person p on p.ptcpnt_vet_id = rcc.ptcpnt_vet_id \
  inner join ear_claim_source cs on cs.vet_id = rcc.ptcpnt_vet_id and cs.claim_id = rcc.bnft_claim_id \
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
		self.EAR_CONTENTION_COUNT = 0
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
writeCursor.prepare('INSERT INTO DEVELOPER.EAR_AGGREGATE_CONTENTION (VET_ID, CLAIM_ID, END_PRODUCT_CODE, CLAIM_DATE, CONTENTION_COUNT, EAR_CONTENTION_COUNT, C2200,C2210, C2220,C3140,C3150,C4130,C4210,C4700,C4920,C6850, DOB, RO_NUMBER, MAX_PROFILE_DATE) \
VALUES (:VET_ID, :CLAIM_ID, :END_PRODUCT_CODE, :CLAIM_DATE, :CONTENTION_COUNT, :EAR_CONTENTION_COUNT, \
 :C2200, :C2210, :C2220, :C3140, :C3150, :C4130 , :C4210, :C4700, :C4920, :C6850, \
 :DOB, :RO_NUMBER, :MAX_PROFILE_DATE)')


print(str(datetime.datetime.now()))
cursor = connection.cursor()
cursor.execute(SQL)

aggregateContention = None
counterAggregateContention = None
totalContentions = None
totalEarContentions = None
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
			aggregateContention.EAR_CONTENTION_COUNT = sum(totalEarContentions.values())
			aggregateContention.MAX_PROFILE_DATE = maxProfileDate[currBenefitClaim]
			
			writeCursor.execute(None, {'VET_ID' :aggregateContention.VET_ID, 'CLAIM_ID' :aggregateContention.CLAIM_ID, 'END_PRODUCT_CODE' :aggregateContention.END_PRODUCT_CODE, 'CLAIM_DATE' :aggregateContention.CLAIM_DATE, 'CONTENTION_COUNT' :aggregateContention.CONTENTION_COUNT, 'EAR_CONTENTION_COUNT' :aggregateContention.EAR_CONTENTION_COUNT, 
			'C2200' :counterAggregateContention.C2200, 'C2210' :counterAggregateContention.C2210, 'C2220' :counterAggregateContention.C2220, 'C3140' :counterAggregateContention.C3140, 'C3150' :counterAggregateContention.C3150, 'C4130' :counterAggregateContention.C4130, 'C4210' :counterAggregateContention.C4210, 'C4700' :counterAggregateContention.C4700, 'C4920' :counterAggregateContention.C4920, 'C6850' :counterAggregateContention.C6850,
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
		totalEarContentions = collections.Counter();
		maxProfileDate = collections.Counter();
		
	maxProfileDate[currBenefitClaim] = contention.prfil_dt #If a claim has multiple profile dates, because of the sorting, we always end up with the most recent profile date
	totalContentions[currBenefitClaim] += 1 #For every contention add one
	if contention.cntntn_clsfcn_id in earContentionCode:
		totalEarContentions[currBenefitClaim] +=1 #For any contention that is ear-related, add one
			
	#Use regex to look for a hit and then if it hits make it true. No need to track how many times, just true or false

	#Simply test the codes and again true or false
	if contention.cntntn_clsfcn_id == 2200:
		counterAggregateContention.C2200 += 1
	if contention.cntntn_clsfcn_id == 2210:
		counterAggregateContention.C2210 += 1
	if contention.cntntn_clsfcn_id == 2220:
		counterAggregateContention.C2220 += 1
	if contention.cntntn_clsfcn_id == 3140:
		counterAggregateContention.C3140 += 1
	if contention.cntntn_clsfcn_id == 3150:
		counterAggregateContention.C3150 += 1
	if contention.cntntn_clsfcn_id == 4130:
		counterAggregateContention.C4130 += 1
	if contention.cntntn_clsfcn_id == 4210:
		counterAggregateContention.C4210 += 1
	if contention.cntntn_clsfcn_id == 4700:
		counterAggregateContention.C4700 += 1
	if contention.cntntn_clsfcn_id == 4920:
		counterAggregateContention.C4920 += 1
	if contention.cntntn_clsfcn_id == 6850:
		counterAggregateContention.C6850 += 1
		
#A bit strange looking but due to Python's identation approach this occurs after the for loop in order to capture the last claim.
aggregateContention.CONTENTION_COUNT = sum(totalContentions.values())
aggregateContention.EAR_CONTENTION_COUNT = sum(totalEarContentions.values())
aggregateContention.MAX_PROFILE_DATE = maxProfileDate[currBenefitClaim]
			
writeCursor.execute(None, {'VET_ID' :aggregateContention.VET_ID, 'CLAIM_ID' :aggregateContention.CLAIM_ID, 'END_PRODUCT_CODE' :aggregateContention.END_PRODUCT_CODE, 'CLAIM_DATE' :aggregateContention.CLAIM_DATE, 'CONTENTION_COUNT' :aggregateContention.CONTENTION_COUNT, 'EAR_CONTENTION_COUNT' :aggregateContention.EAR_CONTENTION_COUNT, 
'C2200' :counterAggregateContention.C2200, 'C2210' :counterAggregateContention.C2210, 'C2220' :counterAggregateContention.C2220, 'C3140' :counterAggregateContention.C3140, 'C3150' :counterAggregateContention.C3150, 'C4130' :counterAggregateContention.C4130, 'C4210' :counterAggregateContention.C4210, 'C4700' :counterAggregateContention.C4700, 'C4920' :counterAggregateContention.C4920, 'C6850' :counterAggregateContention.C6850,
'DOB' :aggregateContention.DOB, 'RO_NUMBER' :aggregateContention.RO_NUMBER, 'MAX_PROFILE_DATE' :aggregateContention.MAX_PROFILE_DATE})

connection.commit()
print(str(datetime.datetime.now()))

writeCursor.close()
cursor.close()
connection.close()
