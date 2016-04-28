spool %TEMP%\clearDecision.log
set echo off 

DROP TABLE "DEVELOPER"."V_EAR_CLAIM_SOURCE" CASCADE CONSTRAINTS;

CREATE TABLE "DEVELOPER"."V_EAR_CLAIM_SOURCE" 
(	"VET_ID" NUMBER(*,0), 
"CLAIM_ID" NUMBER(*,0)
) SEGMENT CREATION IMMEDIATE 
PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 COMPRESS FOR OLTP NOLOGGING
STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
TABLESPACE "VBMS_TS" ;
 


insert into v_ear_claim_source (VET_ID,CLAIM_ID)
select * from
(select distinct rcc.ptcpnt_vet_id, 
	rcc.bnft_claim_id
	from ah2626_rating_corp_claim rcc 
  left join ah2626_person p on p.ptcpnt_vet_id = rcc.ptcpnt_vet_id 
  inner join ( 
    select rcc.ptcpnt_vet_id 
    from ah2626_rating_corp_claim rcc 
    where cntntn_clsfcn_id in (2200,2210,2220,3140,3150,4130,4210,4700,4920,5000,5010,5710,6850) and prfil_dt >= date_of_claim 
    group by rcc.ptcpnt_vet_id 
  ) tmp on tmp.ptcpnt_vet_id = rcc.ptcpnt_vet_id 
	where prfil_dt >= date_of_claim 
	order by rcc.ptcpnt_vet_id desc,bnft_claim_id)
  where rownum <= 3800000;
  
insert into v_ear_claim_source (VET_ID,CLAIM_ID)
select * from
(select distinct rcc.ptcpnt_vet_id, 
	rcc.bnft_claim_id
	from ah4929_rating_corp_claim rcc 
  left join ah2626_person p on p.ptcpnt_vet_id = rcc.ptcpnt_vet_id 
  inner join ( 
    select rcc.ptcpnt_vet_id 
    from ah2626_rating_corp_claim rcc 
    where cntntn_clsfcn_id in (2200,2210,2220,3140,3150,4130,4210,4700,4920,5000,5010,5710,6850) and prfil_dt >= date_of_claim 
    group by rcc.ptcpnt_vet_id 
  ) tmp on tmp.ptcpnt_vet_id = rcc.ptcpnt_vet_id 
	where prfil_dt >= date_of_claim 
	order by rcc.ptcpnt_vet_id desc,bnft_claim_id)
where rownum <= 6000000;


/
exit