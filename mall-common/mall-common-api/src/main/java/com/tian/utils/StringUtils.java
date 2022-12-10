package com.tian.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public final class StringUtils {
    private static final Random RANDOM = new Random();

    public static String toString(Object target) {
        return target == null ? null : target.toString();
    }


    public static Boolean equals(Object first, Object second) {
        if (first == null && second == null) {
            return Boolean.TRUE;
        } else {
            return first != null && second != null ? first.toString().equals(second.toString()) : Boolean.FALSE;
        }
    }

    public static Boolean equalsIgnoreCase(Object first, Object second) {
        if (first == null && second == null) {
            return Boolean.TRUE;
        } else {
            return first != null && second != null ? first.toString().equalsIgnoreCase(second.toString()) : Boolean.FALSE;
        }
    }


    public static String repeat(Object target, int times) {
        if (target == null) {
            return null;
        } else {
            String str = target.toString();
            StringBuilder strBuilder = new StringBuilder(str.length() * times + 10);

            for (int i = 0; i < times; ++i) {
                strBuilder.append(str);
            }

            return strBuilder.toString();
        }
    }

    public static String concat(Object... values) {
        return concatReplaceNulls("", values);
    }

    public static String concatReplaceNulls(String nullValue, Object... values) {
        if (values == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            Object[] var3 = values;
            int var4 = values.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Object value = var3[var5];
                if (value == null) {
                    sb.append(nullValue);
                } else {
                    sb.append(value.toString());
                }
            }

            return sb.toString();
        }
    }


    public static boolean isEmpty(String target) {
        return target == null || target.length() == 0;
    }

    public static boolean isEmptyOrWhitespace(String target) {
        if (target == null) {
            return true;
        } else {
            int targetLen = target.length();
            if (targetLen == 0) {
                return true;
            } else {
                char c0 = target.charAt(0);
                if ((c0 < 'a' || c0 > 'z') && (c0 < 'A' || c0 > 'Z')) {
                    for (int i = 0; i < targetLen; ++i) {
                        char c = target.charAt(i);
                        if (c != ' ' && !Character.isWhitespace(c)) {
                            return false;
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        }
    }


    public static String join(Iterable<?> target, char separator) {
        if (target == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            Iterator<?> it = target.iterator();
            if (it.hasNext()) {
                sb.append(it.next());

                while (it.hasNext()) {
                    sb.append(separator);
                    sb.append(it.next());
                }
            }

            return sb.toString();
        }
    }


    public static String trim(Object target) {
        return target == null ? null : target.toString().trim();
    }

    public static String pack(String target) {
        if (target == null) {
            return null;
        } else {
            int targetLen = target.length();
            StringBuilder strBuilder = null;

            for (int i = 0; i < targetLen; ++i) {
                char c = target.charAt(i);
                if (!Character.isWhitespace(c) && c > ' ') {
                    if (strBuilder != null) {
                        strBuilder.append(c);
                    }
                } else if (strBuilder == null) {
                    strBuilder = new StringBuilder();
                    strBuilder.append(target, 0, i);
                }
            }

            return strBuilder == null ? target.toLowerCase() : strBuilder.toString().toLowerCase();
        }
    }

    public static String capitalize(Object target) {
        if (target == null) {
            return null;
        } else {
            StringBuilder result = new StringBuilder(target.toString());
            if (result.length() > 0) {
                result.setCharAt(0, Character.toTitleCase(result.charAt(0)));
            }

            return result.toString();
        }
    }

    public static String unCapitalize(Object target) {
        if (target == null) {
            return null;
        } else {
            StringBuilder result = new StringBuilder(target.toString());
            if (result.length() > 0) {
                result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
            }

            return result.toString();
        }
    }

    private static int findNextWord(char[] buffer, int idx, char[] delimiterChars) {
        int len = buffer.length;
        if (idx >= 0 && idx < len) {
            boolean foundDelimiters = idx == 0;

            for (int i = idx; i < len; ++i) {
                char ch = buffer[i];
                boolean isDelimiter = delimiterChars == null ? Character.isWhitespace(ch) : Arrays.binarySearch(delimiterChars, ch) >= 0;
                if (isDelimiter) {
                    foundDelimiters = true;
                } else if (foundDelimiters) {
                    return i;
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static String randomAlphanumeric(int count) {
        StringBuilder strBuilder = new StringBuilder(count);
        int anLen = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".length();
        synchronized (RANDOM) {
            for (int i = 0; i < count; ++i) {
                strBuilder.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(RANDOM.nextInt(anLen)));
            }

            return strBuilder.toString();
        }
    }

    private StringUtils() {
    }
}

