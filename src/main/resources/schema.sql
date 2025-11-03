set mode MySQL;

-- 幣別資料
DROP TABLE IF EXISTS coin_type;
CREATE TABLE coin_type (
	code char(4) PRIMARY KEY,
	symbol char(15) NOT NULL,
	rate_float decimal(10,4) NOT NULL,
	description varchar(100),
	description_zh varchar(100),
	create_time datetime default now(),
	update_time datetime
);

