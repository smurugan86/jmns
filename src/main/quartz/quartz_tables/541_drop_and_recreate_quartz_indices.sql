declare
  --Declare Exception Variables
  object_already_exists exception;
  pragma exception_init(object_already_exists,-955);
  index_not_exists exception;
  pragma exception_init(index_not_exists,-1418);
BEGIN
	
    dbms_output.put_line('vvvvv Removing existing RIN quartz indices vvvvv');

	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_j_req_recovery';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_j_req_recovery. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_j_grp';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_j_grp. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_j';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_j. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_jg';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_jg. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_c';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_c. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_g';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_g. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_state';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_state. Index Does Not Exist - Expected Condition.');
	END;

	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_n_state';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_n_state. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_n_g_state';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_n_g_state. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_next_fire_time';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_next_fire_time. Index Does Not Exist - Expected Condition.');
	END;

	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_nft_st';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_nft_st. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_nft_misfire';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_nft_misfire. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_t_nft_st_misfire';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_t_nft_st_misfire. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_rin_t_nft_st_misfire_grp';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_rin_t_nft_st_misfire_grp. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_ft_trig_inst_name';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_ft_trig_inst_name. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_rin_ft_inst_job_req_rcvry';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_rin_ft_inst_job_req_rcvry. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_ft_j_g';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_ft_j_g. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_ft_jg';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_ft_jg. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_ft_t_g';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_ft_t_g. Index Does Not Exist - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'drop index idx_qrtz_rin_ft_tg';
		EXCEPTION
		  WHEN index_not_exists THEN
		    dbms_output.put_line('Index Not Dropped: idx_qrtz_rin_ft_tg. Index Does Not Exist - Expected Condition.');
	END;
    
	dbms_output.put_line('^^^^^ Finished removing existing RIN quartz indices ^^^^^');
	
	---------------------------------------------------------------------------
	---------------------------------------------------------------------------
	
    dbms_output.put_line('vvvvv Creating new quartz RIN indices vvvvv');
    
    BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_sn_jn_jg on qrtz_rin_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_sn_jn_jg. Index Already Exists - Expected Condition.');
	END;

	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_next_fire_time on qrtz_rin_triggers(NEXT_FIRE_TIME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_next_fire_time. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_state on qrtz_rin_triggers(TRIGGER_STATE) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_state. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_nf_st on qrtz_rin_triggers(TRIGGER_STATE,NEXT_FIRE_TIME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_nf_st. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_ft_mf on qrtz_rin_triggers(MISFIRE_INSTR,NEXT_FIRE_TIME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_ft_mf. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_nf_st_misfire on qrtz_rin_triggers(MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_nf_st_misfire. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_t_nf_st_misfire_g on qrtz_rin_triggers(MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_t_nf_st_misfire_g. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_trig_name on qrtz_rin_fired_triggers(TRIGGER_NAME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_trig_name. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_trig_group on qrtz_rin_fired_triggers(TRIGGER_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_trig_group. Index Already Exists - Expected Condition.');
	END;

		
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_trig_n_g on qrtz_rin_fired_triggers(TRIGGER_NAME,TRIGGER_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_trig_n_g. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_trig_inst_name on qrtz_rin_fired_triggers(INSTANCE_NAME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_trig_inst_name. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_trig_job_name on qrtz_rin_fired_triggers(JOB_NAME) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_trig_job_name. Index Already Exists - Expected Condition.');
	END;
	
	BEGIN
		EXECUTE IMMEDIATE 'create index idx_qrtz_rin_ft_trig_job_group on qrtz_rin_fired_triggers(JOB_GROUP) TABLESPACE &&cc_com_owner_indx_tblspace';
		EXCEPTION
		  WHEN object_already_exists THEN
		    dbms_output.put_line('Index Not Created: idx_qrtz_rin_ft_trig_job_group. Index Already Exists - Expected Condition.');
	END;
	
    dbms_output.put_line('^^^^^ Finished creating new quartz RIN indices ^^^^^');

end;
/