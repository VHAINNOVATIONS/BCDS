insert into knee_claim_source (VET_ID,CLAIM_ID)
select * from
(select distinct rcc.ptcpnt_vet_id, 
	rcc.bnft_claim_id
	from ah4929_rating_corp_claim rcc 
  left join ah4929_person p on p.ptcpnt_vet_id = rcc.ptcpnt_vet_id 
  inner join ( 
    select rcc.ptcpnt_vet_id 
    from ah4929_rating_corp_claim rcc 
    where cntntn_clsfcn_id in (230,270,3690,3700,3710,8919,3720,3730,3780,3790,3800) and prfil_dt >= date_of_claim 
    group by rcc.ptcpnt_vet_id 
  ) tmp on tmp.ptcpnt_vet_id = rcc.ptcpnt_vet_id 
	where prfil_dt >= date_of_claim 
	order by rcc.ptcpnt_vet_id desc,bnft_claim_id)
  where rownum <= 3800000