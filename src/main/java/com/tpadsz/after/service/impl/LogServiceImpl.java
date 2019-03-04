package com.tpadsz.after.service.impl;

import com.tpadsz.after.dao.LogDao;
import com.tpadsz.after.entity.LogModal;
import com.tpadsz.after.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by losho on 2018-09-11.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public void insertLog(LogModal log) {
        logDao.insertLog(log);
    }
}
