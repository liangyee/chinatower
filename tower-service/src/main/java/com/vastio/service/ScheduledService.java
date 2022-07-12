package com.vastio.service;

import com.vastio.bean.model.OverTimeContract;
import com.vastio.bean.model.StandBook;
import com.vastio.mapper.ContractMapper;
import com.vastio.mapper.StandBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 11:30 2018/7/5
 */

@Service
public class ScheduledService {
    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private StandBookMapper standBookMapper;

    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void timer() {
        contractMapper.deleteAllToPayContract();
        contractMapper.deleteAllRenewContract();
        contractMapper.deleteAllOverTimeContract();
        if (!standBookMapper.findToPayStandBook().isEmpty())
            contractMapper.createToPayContract(standBookMapper.findToPayStandBook());
        if (!standBookMapper.findOverTimePayStandBook().isEmpty())
            contractMapper.createOverTimeContract(standBookMapper.findOverTimePayStandBook());
        if (!standBookMapper.findRenewContractStandBook().isEmpty())
            contractMapper.createRenewContract(standBookMapper.findRenewContractStandBook());
    }
}
