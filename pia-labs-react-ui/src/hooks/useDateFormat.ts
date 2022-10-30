import { useEffect, useState } from 'react';
import { LOCALE } from '../App';

export default function useDateFormat(options: Intl.DateTimeFormatOptions | undefined) {
  const [ dateFormat, setDateFormat ] = useState(new Intl.DateTimeFormat(LOCALE, options));

  /**
   * Reset date time formatter on options changes.
   */
  useEffect(() => {
    setDateFormat(new Intl.DateTimeFormat(LOCALE, options));

  }, [ options ]);

  return dateFormat;
}