CREATE DIRECTORY dmpdir as 'D:/DataTransfer';

CREATE USER developer identified by ****;
grant connect, create session to developer;
grant resource to developer;
grant read,write on directory dmpdir to developer;
grant IMP_FULL_DATABASE to developer;
grant create user to developer;

commit; 

impdp developer/<pass>@DEV directory=dmpdir dumpfile=expdp_ADHOC_DMT_RECURR.dmp full=y

select count(ptcpnt_vet_id) from AH4929_PERSON_FLASH
select * from user_tablespaces;
select * from dba_data_files;

ALTER USER developer default tablespace VBMS_TS;

ALTER DATABASE DATAFILE 'D:/Oracle/11.2.0/home/database/VBMS_01.DBF'
AUTOEXTEND ON NEXT 1M MAXSIZE 5120M;

commit;

select * from DEVELOPER.AH4929_RATING_DECISION where rownum < 10;

select * from dba_datapump_jobs

ALTER TABLESPACE VBMS_TS ADD DATAFILE 'vbms_02.dbf' SIZE 16M AUTOEXTEND ON NEXT 8M MAXSIZE UNLIMITED;
ALTER TABLESPACE VBMS_TS ADD DATAFILE 'vbms_03.dbf' SIZE 16M AUTOEXTEND ON NEXT 8M MAXSIZE UNLIMITED;
ALTER TABLESPACE VBMS_TS ADD DATAFILE 'vbms_04.dbf' SIZE 16M AUTOEXTEND ON NEXT 8M MAXSIZE UNLIMITED;
commit;


CREATE TABLESPACE VBMS_TS DATAFILE 'vbms_01.dbf' SIZE 16M AUTOEXTEND ON NEXT 8M MAXSIZE UNLIMITED
        LOGGING
        ONLINE
        EXTENT MANAGEMENT LOCAL AUTOALLOCATE
        BLOCKSIZE 8K
        SEGMENT SPACE MANAGEMENT AUTO
        FLASHBACK ON;