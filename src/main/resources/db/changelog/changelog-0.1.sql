--liquibase formatted sql
--changeset alextsyg972:1
CREATE TABLE IF NOT EXISTS wallet
(
    wallet_id UUID NOT NULL,
    amount NUMERIC(12,2) NOT NULL DEFAULT 0,
    PRIMARY KEY (wallet_id)
);

--changeset alextsyg972:2
insert into wallet VALUES ('388b377f-58f5-45d2-951b-19da115c2afd',1000);
insert into wallet VALUES ('1790b855-478c-4ff8-b05e-cdbd53059478', 1000);
