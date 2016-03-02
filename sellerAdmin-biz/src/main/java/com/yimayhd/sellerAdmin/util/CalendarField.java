//$Id: CalendarField.java 14213 2006-08-31 23:57:47Z dberkland $
package com.yimayhd.sellerAdmin.util;

import java.util.Calendar;

/**
 * Date field for date math functions of {@link DateUtil}.<br>
 * Copyright (c) 2006 Datavantage Corporation
 *
 * @author dberkland
 * @version $Revision: 14213 $
 * @created Aug 25, 2006
 */
public enum CalendarField {
  /** the Year field for date math functions of {@link DateUtil} */
  YEAR(Calendar.YEAR),
  /** the Month field for date math functions of {@link DateUtil} */
  MONTH(Calendar.MONTH),
  /** the Day field for date math functions of {@link DateUtil} */
  WEEK(Calendar.WEEK_OF_YEAR),
  /** the Day field for date math functions of {@link DateUtil} */
  DAY(Calendar.DATE),
  /** the hour field for date math functions of {@link DateUtil} */
  HOUR(Calendar.HOUR),
  /** the Minute field for date math functions of {@link DateUtil} */
  MINUTE(Calendar.MINUTE),
  /** the Second field for date math functions of {@link DateUtil} */
  SECOND(Calendar.SECOND),
  /** the Millisecond field for date math functions of {@link DateUtil} */
  MILLISECOND(Calendar.MILLISECOND);

  private CalendarField(int argField) {

    id = argField;
  }

  final int id;

}

/*
 * $Log$
 * Revision 1.1  2006/08/31 23:57:47  dberkland
 * created
 *
 */
