package com.mstr.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;


public class Utils {

	private static final Logger logger = Logger.getLogger(Utils.class);
	
    /**
     * Null ?�� 공백문자�? �??�� ?��?�� 반환
     *
     * @param arg0
     */
    public static String Null2Blank(String arg0) {

        if(arg0 == null) arg0 = "";
        else if(arg0.equals("")) arg0 = "";

        arg0 = arg0.trim();

        return arg0;
    }

    /**
     * arg0 ?�� Null ?�� 경우 arg1 �? �??�� ?��?�� 반환
     *
     * @param arg0
     * @param arg1
     * @return
     */
    public static String Null2Blank(String arg0 , String arg1) {

        if(arg0 == null) arg0 = arg1;
        else if(arg0.equals("")) arg0 = arg1;

        arg0 = arg0.trim();

        return arg0;
    }

    /*
     * ?�� ?��?��?���? Int ?��?���? 반환
     */
    /**
     * arg0 ?�� Null ?��경우 int ???��?��  0?�� 반환
     */
    public static int Null2Zero(String arg0) {
        int rtnInt = 0;

        if(arg0 == null) rtnInt = 0;
        else {
            arg0 = arg0.trim();
            try {
                rtnInt = Integer.parseInt(arg0);
            }catch(Exception e) {
                rtnInt = 0;
            }
        }
        return rtnInt;
    }

    /**
     * arg0 ?�� Null ?�� 경우  double ???��?�� 0 ?�� 반환
     * @param arg0
     * @return
     */
    public static double Null2Zero(double arg0) {
        double rtn = 0;
        try {
            rtn = arg0;
        }catch(Exception e) {
            rtn = 0;
        }
        return rtn;
    }

    public static int Null2Zero(int arg0) {
        int rtn = 0;
        try {
            rtn = arg0;
        }catch(Exception e) {
            rtn = 0;
        }
        return rtn;
    }


    public static int Null2ZeroInteger(Integer arg0) {
        int rtn = 0;
        try {
            rtn = arg0.intValue();
        }catch(Exception e) {
            rtn = 0;
        }
        return rtn;
    }    
    
    /**
     * ?��?��?�� ?��짜�?? 반환 ?��?��.
     */
    public static String toDayDate() {

        Calendar cal = Calendar.getInstance() ;

        int my = cal.get(Calendar.YEAR) ;
        int mm = cal.get(Calendar.MONTH) + 1 ;
        int md = cal.get(Calendar.DAY_OF_MONTH) ;

        String rtn = "";

        rtn = String.valueOf(my);
        if(String.valueOf(mm).length() == 1) {
            rtn = rtn + "0" + mm;
        } else {
            rtn = rtn + mm;
        }
        if(String.valueOf(md).length() == 1) {
            rtn = rtn + "0" + md;
        } else {
            rtn = rtn + md;
        }
        return rtn;
    }

    public static String toDayDate(String format) {
        String today = toDayDate();

        String my = today.substring(0,4);
        String mm = today.substring(4,6);
        String md = today.substring(6);

        return my + format + mm + format + md;
    }

      /**
       * YYYYMMDD, YYYYMMDD24hhmiss
       *        ?��?�� ?��?��링을 YYYY-MM-DD HH:MI:SS ?��?��?���? 반환
       *
       * @param s date format?���? ?��?��?�� ?��?���?
       * @return
       *        data format?���? ?��?���??��?�� s ?�� YYYY/MM/DD HH:MI:SS ?��멧으�? 리턴?���?
       *        ?��?�� 경우 ""?�� 리턴.
       */
      public static String toDateTimeFormat(String s) {
          if (s == null) {
              return "";
          }

          String s_ = replace(s, "-", "");
          if(s.length() == 14) {
              return s_.substring(0, 4) + "-" +
                s_.substring(4, 6) + "-" +
                s_.substring(6, 8) + " " +
                s_.substring(8, 10) + ":" +
                s_.substring(10, 12) + ":" +
                s_.substring(12, 14);
          }else {
              return "";
          }
      }

      /**
       * YYYYMMDD, YYYYMMDD24hhmiss ?��?�� ?��?��링을 pattern ?��?��?���? 반환
       *
       * @param ?���? ?��?��?��
       * @return ?��맷의 ?��?��?���? ?��?��?���? String?���? 리턴
       */
      public static String toDateFormat(String s , String pattern) {

          if (s == null) return "-";
          s = trimNumber(s);

          if(pattern.equals("")) pattern = "-";

          String r_s = replace(s , pattern , "");

          if(s.length() >= 8) {
              return r_s.substring(0, 4) + pattern +
                            r_s.substring(4, 6) + pattern +
                            r_s.substring(6, 8);
          }else if(s.length() == 7){
              return r_s.substring(0,4) + pattern +
                            "0" +r_s.substring(4,5) + pattern +
                            r_s.substring(5,7);
          }else if(s.length() == 6){
              return r_s.substring(0,4) + pattern + r_s.substring(4,6);
          }else return s;
      }

      /*
       * replace
       */
      public static final String replace(Object src_, Object fnd, Object rep) {
          if (src_ == null) {
              return null;
          }

          String line = String.valueOf(src_).trim();
          String oldString = String.valueOf(fnd);
          String newString = String.valueOf(rep);
          int i = 0;

          if ((i = line.indexOf(oldString, i)) >= 0) {
              char[] line2 = line.toCharArray();
              char[] newString2 = newString.toCharArray();
              int oLength = oldString.length();

              StringBuffer buf = new StringBuffer(line2.length);
                buf.append(line2, 0, i).append(newString2);

              i += oLength;
              int j = i;
              while((i = line.indexOf(oldString, i)) > 0) {
                  buf.append(line2, j, i - j).append(newString2);
                  i += oLength;
                  j = i;
              }
              buf.append(line2, j, line2.length - j);
              return buf.toString();
          }
          return line;
      }

      /**
       * ?���? : ?��?�� ?��?��?��?�� ?��맷을 �??��?��?��.
       *
       * ?��?���? :
       *            (111111 , XXX-XXX)
       *            결과 : 111-111
       *            (111111 , XXX/XXX)
       *            결과 : 111/111
       *
       * 주의 :
       *            길이�? 맞�? ?��?��경우 ?��?�� 발생
       *
       * @param arg0
       * @param format
       * @return
       */
      public static String stringFormatter(String arg0 , String format) {

          if(arg0 == null || "".equals(arg0)) return "";

          arg0 = trimNumber(arg0);

          if("".equals(arg0)) return "";

          String rtnStr = "";
          int cnt = 0;

          for(int x=0; x<format.length(); x++) {
              if (format.charAt(x) == 'X') {
                  cnt++;
              }
          }

          if(arg0.length() < cnt) return "";
          cnt = 0;

          try {
              for(int x=0; x<format.length(); x++) {
                  if (format.charAt(x) == 'X') {
                      rtnStr += arg0.charAt(cnt);
                      cnt++;
                  }else {
                      rtnStr += format.charAt(x);
                  }
              }
          }catch(Exception e) {
              rtnStr = arg0;
          }

          return rtnStr;
      }

      /**
       * ?���? : ?��?�� 번호 ?��맷으�? 리턴 ?��?��.
       *
       * 02303984 ->  02-203-1231
       *
       * ?��별한 ?��?�� 처리 ?���? ?��?��.
       *
       * @param arg0
       * @return
       */
      public static String telFormatter(String arg0) {
          if(arg0 == null || arg0.equals("")) return "";

          arg0 = replace(arg0 , " " , "");
          arg0 = trimNumber(arg0);

          if(arg0 == null || arg0.equals("")) return "";
          if(arg0.length() < 9) return arg0;

          String telno1 = "";
          String telno2 = "";
          String telno3 = "";

          boolean isSeoul = true;

          String[] exchangNumbers = new String[] { "031", "032", "041",
                    "042", "043", "051", "052", "053", "054", "055", "061",
                    "062", "063", "064", "011", "010", "012", "015", "016",
                    "017", "018", "019" };

          telno1 = arg0.substring(0, 3);

          for (int x = 0; x < exchangNumbers.length; x++) {
              if (exchangNumbers[x].equals(telno1)) {
                  isSeoul = false;
              }
          }

          if (isSeoul) {
            telno1 = arg0.substring(0, 2);
            telno3 = arg0.substring((arg0.length() - 4));
            telno2 = arg0.substring(2, (arg0.length() - telno3.length()));
          } else {
              telno1 = arg0.substring(0, 3);
              telno3 = arg0.substring((arg0.length() - 4));
              telno2 = arg0.substring(3, (arg0.length() - telno3.length()));

          }

          return telno1 + "-" + telno2 + "-" + telno3;
      }

      /**
       * ?���? : ?��?�� 번호 ?��맷으�? 리턴 ?��?��.
       *
       * 02 303984 ->  (02)203-1231
       *
       * ?��별한 ?��?�� 처리 ?���? ?��?��.
       *
       * @param arg0
       * @return
       */
      public static String phoneFormatter(String arg0) {
          if(arg0 == null || arg0.equals("")) return "";

          StringBuffer rtnStr = new StringBuffer();

          arg0 = telFormatter(arg0);

          String [] tmp = arg0.split("-");

          if(tmp.length == 3) {
              rtnStr.append("(" + tmp[0] + ")");
              rtnStr.append(" ");
              rtnStr.append(tmp[1]);
              rtnStr.append(" - ");
              rtnStr.append(tmp[2]);
          }
          else rtnStr.append(arg0);

          return rtnStr.toString();
      }

      /*
       * Ssn Format?���? �?�?
       *
       * 1234567890
       *        return :760505-1******
       */
      public static String ssnFormatter(String arg0) {

          if(arg0 == null || arg0.equals("")) return "";
          if(arg0.length() < 13) return arg0;

          arg0 = replace(arg0 , " " , "");

          arg0 = arg0.trim();

          String rtnStr = "";

          for(int x=0; x<arg0.length(); x++) {
              if(x > 5)
                  rtnStr += "*";
              else rtnStr += arg0.charAt(x);
          }

          rtnStr = rtnStr.substring(0,6) + " - " + rtnStr.substring(6);

          return rtnStr;
      }

      /**
       * ?��?�� 번호, ?���? ?��?��?��?�� ?��?��?�� ?��?��?��만을 추출 ?��?��.
       *            input : 2006-10-01
       *            output : 20061001
       *
       * @param arg0
       * @return
       */
      public static String trimNumber(String arg0) {
          if(arg0 == null) return "";

          StringBuffer sb = new StringBuffer("");
          char numbers [] = new char[]{'0','1','2','3','4','5','6','7','8','9'};

          for(int x=0; x<arg0.length(); x++) {
              for(int y=0; y<numbers.length; y++) {
                  if(arg0.charAt(x) == numbers[y]) {
                      sb.append(arg0.charAt(x));
                  }
              }
          }

          //System.out.println(sb.toString());
          return sb.toString();
      }

       /**
         * 금액 ?��맷을 �??��?��?��.
         */
        private static DecimalFormat moneyForm = new DecimalFormat("###,###,###,###,###");

        /**?���? :
         * 금액 ?���? 처리�? ?��?��?��?��. 3?��리마?�� ','�? 찍어�??��.
         * @param str   금액 ?��맷처리용 ?��?��?��
         * @return  금액?���? ?��?��?�� 문자?��
         */
        public static String strToMoney(String str) {
            if(str == null || "".equals(str)) {
                return str;
            }
            else {
                long longStr = Long.parseLong(str);
                return moneyForm.format(longStr);
            }
        }

        /**?���? :
         * 금액 ?��???��?�� 문자?��(2,500)?�� ?��?��?��???��(2500)�? �??��
         * @param str   �??��?��고자 ?��?�� 문자?��
         * @return  ?��?��?��???�� 반환
         */
        public static String moneyToStr(String str) {
            if(str == null || "".equals(str)) {
                return str;
            }
            else {
                String[] splStr = str.split(",");
                StringBuffer sb = new StringBuffer();
                for(int i=0; i<splStr.length; i++) {
                    sb.append(splStr[i]);
                }

                return sb.toString();
            }
        }


        /**
         * ?��?�� ?��?��?��?��분초�? "yyyyMMddHHmmss"?�� time format?���? 구함
         * @return ?��?�� ?��?��?��?��분초
         */
        public static String getTime() {
            return getTime("yyyyMMddHHmmss");
        }

        public static String getTimeFomat(String format) {
            return getTime(format);
        }



        public static String getTime(String format) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }

        /**
         * ?���? : �??��, �?, 고유번호�? 분리?��?�� ?��?�� 번호�? KCB ?��?��?�� ?��맷으�? �?�? ?�� 리턴...
         * @param loc : �??��
         * @param off : �?
         * @param ori : 고유
         * @return
         */
        public static String getPhoneFormat(String loc, String off, String ori) {
            StringBuffer phone = new StringBuffer();

            int max = 4;

            if(loc == null || off == null || ori == null) {
                return "";
            }

            int locSize = loc.trim().length();
            int offSize = off.trim().length();
            int oriSize = ori.trim().length();

            if(locSize < 2) {
                return "";
            } else {
                
                phone.append(loc);
                for(int i = 0;i < max-locSize;i++) {
                    phone.append(" ");
                }
            }

            if(offSize < 3) {
                return "";
            } else {
                
                phone.append(off);
                for(int i = 0;i < max-offSize;i++) {
                    phone.append(" ");
                }
            }

            if(oriSize < 4) {
                return "";
            } else {
                
                phone.append(ori);
                for(int i = 0;i < max-oriSize;i++) {
                    phone.append(" ");
                }
            }

            return phone.toString();
        }

        /**
         * ?���? : 12?��리로 ?��?�� ?��?�� ?��?��번호(?��???��)?�� 3개로 ?��?��?�� 리턴...
         * @param phone
         * @return
         */
        public static String [] getPhoneNumbers(String phone) {

            String [] phoneNumbers = null;

            if(phone == null || phone.length() != 12) {
                return phoneNumbers;
            } else {
                phoneNumbers = new String[3];
                phoneNumbers[0] = phone.substring(0, 4).trim();
                phoneNumbers[1] = phone.substring(4, 8).trim();
                phoneNumbers[2] = phone.substring(8).trim();

                return phoneNumbers;
            }
        }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String filePathBlackList(String value) {
        String returnValue = value;
        if (returnValue == null || returnValue.trim().equals("")) {
            return "";
        }

        returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
        returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

        return returnValue;
    }

    public static boolean isIPAddress(String str) {
        Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

        return ipPattern.matcher(str).matches();
    }

    public static String getTimeStamp() {

        String rtnStr = null;

        // 문자?���? �??��?���? ?��?�� ?��?�� ?��?��(?��?��-?��-?�� ?��:�?:�?:�?(?��?��?��?�� �?))
        String pattern = "yyyyMMddhhmmssSSS";

        SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        rtnStr = sdfCurrent.format(ts.getTime());

        return rtnStr;
    }
}




