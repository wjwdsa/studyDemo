-- 银行账号列表，保存需要从核心查询明细的账号列表
-- ReportingAccount
CREATE TABLE `gbm_account` (
                                      `acc_no` VARCHAR(40) NOT NULL,
                                      `doc_ref_no` VARCHAR(80) NULL,
                                      `status` INT NULL,
                                      `add_date` INT NULL,
                                      `del_date` INT NULL,
                                      PRIMARY KEY (`acc_no`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- 银行账号交易明细
-- ReportingDetail
CREATE TABLE `gbm_transaction` (
  `inf_bankId` varchar(40) NOT NULL,
  `ctl_date` int(11) NOT NULL,
  `ctl_stage` int(11) NOT NULL,
  `ctl_status` int(11) NOT NULL,
  `ctl_last_bn` int(11) DEFAULT NULL,
  `inf_balance` varchar(30)  DEFAULT NULL,
  `inf_counterBankId` varchar(40) DEFAULT NULL,
  `inf_counterBankName` varchar(200) DEFAULT NULL,
  `inf_currency` varchar(20) DEFAULT NULL,
  `inf_handFee` varchar(30) DEFAULT NULL,
  `inf_loanSign` varchar(1) DEFAULT NULL,
  `inf_mark` varchar(1) DEFAULT NULL,
  `inf_remarks` varchar(80) DEFAULT NULL,
  `inf_transAmount` varchar(30) DEFAULT NULL,
  `inf_total` varchar(30) DEFAULT NULL,
  `inf_transHour` varchar(20) DEFAULT NULL,
  `inf_transType` varchar(40) DEFAULT NULL,
  `inf_voucherNumber` varchar(40) DEFAULT NULL,
  `inf_pzNumber` varchar(40) DEFAULT NULL,
  `inf_payeeName` varchar(200) DEFAULT NULL,
  `inf_useFunds` varchar(40) DEFAULT NULL,
  `inf_virtualBankId` varchar(40) DEFAULT NULL,
  `inf_code` varchar(6) DEFAULT NULL,
  `inf_prompt` varchar(256) DEFAULT NULL,
  UNIQUE KEY `gbm_transaction_idx_1` (`ctl_date`,`ctl_last_bn`,`inf_bankId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

-- 每日自动推送任务状态表
-- ReportingStatus
-- _date 工作日期，定时任务执行日的前一日
-- stage 参见ReportingStatus
-- status 参见 ReportingStatus
-- batch_no 当前批次号（如果以后需要对自动推送返回的部分失败明细做进一步处理时，可能需要用到此字段，目前默认取值1）
CREATE TABLE `gbm_status` (
                                     `_date` INT NOT NULL,
                                     `stage` INT NULL,
                                     `status` INT NULL,
                                     `batch_no` INT NULL,
                                     PRIMARY KEY (`_date`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- 批次信息
-- ReportingBatch
CREATE TABLE `test`.`gbm_batch` (
                                    `_date` INT NOT NULL,
                                    `no` INT NOT NULL,
                                    `status` INT NULL,
                                    `begin_date` TIMESTAMP NULL,
                                    `end_date` TIMESTAMP NULL,
                                    PRIMARY KEY (`_date`, `no`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


