package com.sos.schedule;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.sos.common.ApplicationConstant.VoucherStatus;
import com.sos.repository.AccountRepository;
import com.sos.repository.VoucherRepository;

@Configuration
public class ScheduleConfig {

	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	@Scheduled(cron = "0 0 0 * * ?")
	public void checkExpiredVoucher() {
		voucherRepository.checkExpiredVoucher(VoucherStatus.INACTIVE, new Date());
	}

	@Transactional
	@Scheduled(cron = "0 0 0 1 1 ?")
	public void resetAccountPoint() {
		accountRepository.resetAccountPoint(0);
	}
	
}
