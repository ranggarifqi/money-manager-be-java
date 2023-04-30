package com.ranggarifqi.moneymanager.common.datetime;

import java.sql.Timestamp;
import java.util.Date;

public class DateTimeServiceService implements IDateTimeService {
    @Override
    public Timestamp getCurrentTimestamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }
}
