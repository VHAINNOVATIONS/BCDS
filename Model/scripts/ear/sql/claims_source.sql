insert into ear_claim_source (VET_ID,CLAIM_ID)
select * from
(select distinct rcc.ptcpnt_vet_id, 
	rcc.bnft_claim_id
	from ah4929_rating_corp_claim rcc 
  left join ah4929_person p on p.ptcpnt_vet_id = rcc.ptcpnt_vet_id 
  inner join ( 
    select rcc.ptcpnt_vet_id 
    from ah4929_rating_corp_claim rcc 
    where cntntn_clsfcn_id in (2200,2210,2220,3140,3150,4130,4210,4700,4920,6850) and prfil_dt >= date_of_claim 
    group by rcc.ptcpnt_vet_id 
  ) tmp on tmp.ptcpnt_vet_id = rcc.ptcpnt_vet_id 
	where prfil_dt >= date_of_claim 
	order by rcc.ptcpnt_vet_id desc,bnft_claim_id)
  where rownum <= 3800000