drop index IF EXISTS config_idx CASCADE;
CREATE  INDEX config_idx ON CONFIG (code, type);


