-- tenant id --

ALTER TABLE ACT_RE_DEPLOYMENT 
  ADD TENANT_ID_ varchar(255);
  
create index ACT_IDX_DEPLOYMENT_TENANT_ID on ACT_RE_DEPLOYMENT(TENANT_ID_);