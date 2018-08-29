declare
  --Declare Exception Variables
  object_already_exists exception;
  pragma exception_init(object_already_exists,-955);
BEGIN
	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_job_details
			  (
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    JOB_NAME  VARCHAR2(200) NOT NULL,
			    JOB_GROUP VARCHAR2(200) NOT NULL,
			    DESCRIPTION VARCHAR2(250) NULL,
			    JOB_CLASS_NAME   VARCHAR2(250) NOT NULL, 
			    IS_DURABLE VARCHAR2(1) NOT NULL,
			    IS_NONCONCURRENT VARCHAR2(1) NOT NULL,
			    IS_UPDATE_DATA VARCHAR2(1) NOT NULL,
			    REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
			    JOB_DATA BLOB NULL,
			    CONSTRAINT QRTZ_RIN_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
			)  TABLESPACE &&cc_com_owner_data_tblspace';
			
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_job_details TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_job_details TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_job_details TO &&CC_COM_OWNER_READONLY';

	    EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_job_details already exists. SQL Code: '||sqlcode||' '||sqlerrm);
	END;

	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_triggers
			  (
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    TRIGGER_NAME VARCHAR2(200) NOT NULL,
			    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
			    JOB_NAME  VARCHAR2(200) NOT NULL, 
			    JOB_GROUP VARCHAR2(200) NOT NULL,
			    DESCRIPTION VARCHAR2(250) NULL,
			    NEXT_FIRE_TIME NUMBER(13) NULL,
			    PREV_FIRE_TIME NUMBER(13) NULL,
			    PRIORITY NUMBER(13) NULL,
			    TRIGGER_STATE VARCHAR2(16) NOT NULL,
			    TRIGGER_TYPE VARCHAR2(8) NOT NULL,
			    START_TIME NUMBER(13) NOT NULL,
			    END_TIME NUMBER(13) NULL,
			    CALENDAR_NAME VARCHAR2(200) NULL,
			    MISFIRE_INSTR NUMBER(2) NULL,
			    JOB_DATA BLOB NULL,
			    CONSTRAINT QRTZ_RIN_TRIGGERS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
			    CONSTRAINT QRTZ_RIN_TRIGGER_TO_JOBS_FK FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP) 
			      REFERENCES QRTZ_RIN_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP) 
			) TABLESPACE &&cc_com_owner_data_tblspace';

		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_triggers TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_triggers TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_triggers TO &&CC_COM_OWNER_READONLY';
		
	    EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_triggers already exists. SQL Code: '||sqlcode||' '||sqlerrm);

	END;
	
	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_simple_triggers
			  (
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    TRIGGER_NAME VARCHAR2(200) NOT NULL,
			    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
			    REPEAT_COUNT NUMBER(7) NOT NULL,
			    REPEAT_INTERVAL NUMBER(12) NOT NULL,
			    TIMES_TRIGGERED NUMBER(10) NOT NULL,
			    CONSTRAINT QRTZ_RIN_SMP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
			    CONSTRAINT QRTZ_RIN_SMP_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
				REFERENCES QRTZ_RIN_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
			) TABLESPACE &&cc_com_owner_data_tblspace';

		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_simple_triggers TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_simple_triggers TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_simple_triggers TO &&CC_COM_OWNER_READONLY';
		
	    EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_simple_triggers already exists. SQL Code: '||sqlcode||' '||sqlerrm);

	END;

	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_cron_triggers
			  (
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    TRIGGER_NAME VARCHAR2(200) NOT NULL,
			    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
			    CRON_EXPRESSION VARCHAR2(120) NOT NULL,
			    TIME_ZONE_ID VARCHAR2(80),
			    CONSTRAINT QRTZ_RIN_CRON_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
			    CONSTRAINT QRTZ_RIN_CRON_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
			      REFERENCES QRTZ_RIN_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
			) TABLESPACE &&cc_com_owner_data_tblspace';

		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_cron_triggers TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_cron_triggers TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_cron_triggers TO &&CC_COM_OWNER_READONLY';
		
	    EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_cron_triggers already exists. SQL Code: '||sqlcode||' '||sqlerrm);

	END;

	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_simprop_triggers
			  (          
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    TRIGGER_NAME VARCHAR2(200) NOT NULL,
			    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
			    STR_PROP_1 VARCHAR2(512) NULL,
			    STR_PROP_2 VARCHAR2(512) NULL,
			    STR_PROP_3 VARCHAR2(512) NULL,
			    INT_PROP_1 NUMBER(10) NULL,
			    INT_PROP_2 NUMBER(10) NULL,
			    LONG_PROP_1 NUMBER(13) NULL,
			    LONG_PROP_2 NUMBER(13) NULL,
			    DEC_PROP_1 NUMERIC(13,4) NULL,
			    DEC_PROP_2 NUMERIC(13,4) NULL,
			    BOOL_PROP_1 VARCHAR2(1) NULL,
			    BOOL_PROP_2 VARCHAR2(1) NULL,
			    CONSTRAINT QRTZ_RIN_SIMPROP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
			    CONSTRAINT QRTZ_RIN_SMPR_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
			      REFERENCES QRTZ_RIN_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
			) TABLESPACE &&cc_com_owner_data_tblspace';

		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_simprop_triggers TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_simprop_triggers TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_simprop_triggers TO &&CC_COM_OWNER_READONLY';
		
	    EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_simprop_triggers already exists. SQL Code: '||sqlcode||' '||sqlerrm);

	END;

	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_blob_triggers
			  (
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    TRIGGER_NAME VARCHAR2(200) NOT NULL,
			    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
			    BLOB_DATA BLOB NULL,
			    CONSTRAINT QRTZ_RIN_BLOB_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
			    CONSTRAINT QRTZ_RIN_BLOB_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
			        REFERENCES QRTZ_RIN_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
			) TABLESPACE &&cc_com_owner_data_tblspace';

		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_blob_triggers TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_blob_triggers TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_blob_triggers TO &&CC_COM_OWNER_READONLY';
		
	    EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_blob_triggers already exists. SQL Code: '||sqlcode||' '||sqlerrm);

	END;

	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_calendars
			  (
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    CALENDAR_NAME  VARCHAR2(200) NOT NULL, 
			    CALENDAR BLOB NOT NULL,
			    CONSTRAINT QRTZ_RIN_CALENDARS_PK PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
			) TABLESPACE &&cc_com_owner_data_tblspace';

		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_calendars TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_calendars TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_calendars TO &&CC_COM_OWNER_READONLY';
		
	    EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_calendars already exists. SQL Code: '||sqlcode||' '||sqlerrm);

	END;

	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_paused_trigger_grps
			  (
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    TRIGGER_GROUP  VARCHAR2(200) NOT NULL, 
			    CONSTRAINT QRTZ_RIN_PAUSED_TRIG_GRPS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
			) TABLESPACE &&cc_com_owner_data_tblspace';

		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_paused_trigger_grps TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_paused_trigger_grps TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_paused_trigger_grps TO &&CC_COM_OWNER_READONLY';
		
	    EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_paused_trigger_grps already exists. SQL Code: '||sqlcode||' '||sqlerrm);

	END;

	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_fired_triggers 
			  (
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    ENTRY_ID VARCHAR2(95) NOT NULL,
			    TRIGGER_NAME VARCHAR2(200) NOT NULL,
			    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
			    INSTANCE_NAME VARCHAR2(200) NOT NULL,
			    FIRED_TIME NUMBER(13) NOT NULL,
			    SCHED_TIME NUMBER(13) NOT NULL,
			    PRIORITY NUMBER(13) NOT NULL,
			    STATE VARCHAR2(16) NOT NULL,
			    JOB_NAME VARCHAR2(200) NULL,
			    JOB_GROUP VARCHAR2(200) NULL,
			    IS_NONCONCURRENT VARCHAR2(1) NULL,
			    REQUESTS_RECOVERY VARCHAR2(1) NULL,
			    CONSTRAINT QRTZ_RIN_FIRED_TRIGGER_PK PRIMARY KEY (SCHED_NAME,ENTRY_ID)
			) TABLESPACE &&cc_com_owner_data_tblspace';

		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_fired_triggers TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_fired_triggers TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_fired_triggers TO &&CC_COM_OWNER_READONLY';
		
	    EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_fired_triggers already exists. SQL Code: '||sqlcode||' '||sqlerrm);

	END;

	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_scheduler_state 
			  (
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    INSTANCE_NAME VARCHAR2(200) NOT NULL,
			    LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
			    CHECKIN_INTERVAL NUMBER(13) NOT NULL,
			    CONSTRAINT QRTZ_RIN_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
			) TABLESPACE &&cc_com_owner_data_tblspace';

		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_scheduler_state TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_scheduler_state TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_scheduler_state TO &&CC_COM_OWNER_READONLY';
		
	    EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_scheduler_state already exists. SQL Code: '||sqlcode||' '||sqlerrm);

	END;

	BEGIN
		EXECUTE IMMEDIATE '
			CREATE TABLE qrtz_rin_locks
			  (
			    SCHED_NAME VARCHAR2(120) NOT NULL,
			    LOCK_NAME  VARCHAR2(40) NOT NULL, 
			    CONSTRAINT QRTZ_RIN_LOCKS_PK PRIMARY KEY (SCHED_NAME,LOCK_NAME)
			) TABLESPACE &&cc_com_owner_data_tblspace';

		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_locks TO &&CC_COM_APP';
		EXECUTE IMMEDIATE 'GRANT DELETE, INSERT, SELECT, UPDATE ON qrtz_rin_locks TO &&CC_COM_OWNER_READWRITE';
		EXECUTE IMMEDIATE 'GRANT SELECT ON qrtz_rin_locks TO &&CC_COM_OWNER_READONLY';

		EXCEPTION WHEN object_already_exists THEN
			dbms_output.put_line('Warning: table qrtz_rin_locks already exists. SQL Code: '||sqlcode||' '||sqlerrm);

	END;

	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_j_req_recovery on qrtz_rin_job_details(SCHED_NAME,REQUESTS_RECOVERY) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_j_req_recovery. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_j_grp on qrtz_rin_job_details(SCHED_NAME,JOB_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_j_grp. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_j on qrtz_rin_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_j. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_jg on qrtz_rin_triggers(SCHED_NAME,JOB_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_jg. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_c on qrtz_rin_triggers(SCHED_NAME,CALENDAR_NAME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_c. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_g on qrtz_rin_triggers(SCHED_NAME,TRIGGER_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_g. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_state on qrtz_rin_triggers(SCHED_NAME,TRIGGER_STATE) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_state. Index Already Exists - Expected Condition.');
	END;

	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_n_state on qrtz_rin_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_n_state. Index Already Exists - Expected Condition.');
	END;

	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_n_g_state on qrtz_rin_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_n_g_state. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_next_fire_time on qrtz_rin_triggers(SCHED_NAME,NEXT_FIRE_TIME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_next_fire_time. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_nft_st on qrtz_rin_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_nft_st. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_nft_misfire on qrtz_rin_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_nft_misfire. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_nft_st_misfire on qrtz_rin_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_nft_st_misfire. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_rin_t_nft_st_misfire_grp on qrtz_rin_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
	    dbms_output.put_line('Index Not Created: idx_rin_t_nft_st_misfire_grp. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_trig_inst_name on qrtz_rin_fired_triggers(SCHED_NAME,INSTANCE_NAME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_trig_inst_name. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_rin_ft_inst_job_req_rcvry on qrtz_rin_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_rin_ft_inst_job_req_rcvry. Index Already Exists - Expected Condition.');
	END;

	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_j_g on qrtz_rin_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_j_g. Index Already Exists - Expected Condition.');
	END;

	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_jg on qrtz_rin_fired_triggers(SCHED_NAME,JOB_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_jg. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_t_g on qrtz_rin_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_t_g. Index Already Exists - Expected Condition.');
	END;

	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_tg on qrtz_rin_fired_triggers(SCHED_NAME,TRIGGER_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_tg. Index Already Exists - Expected Condition.');
	END;

end;
/