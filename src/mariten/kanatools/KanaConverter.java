package mariten.kanatools;

public class KanaConverter
{
    // Conversion Operations Types
    public static final char OP_HANKAKU_ALPHANUMERIC_TO_ZENKAKU_ALPHANUMERIC = 'A';
    public static final char OP_ZENKAKU_ALPHANUMERIC_TO_HANKAKU_ALPHANUMERIC = 'a';
    public static final char OP_ZENKAKU_HIRAGANA_TO_ZENKAKU_KATAKANA         = 'C';
    public static final char OP_ZENKAKU_KATAKANA_TO_ZENKAKU_HIRAGANA         = 'c';
    public static final char OP_HANKAKU_KATAKANA_TO_ZENKAKU_HIRAGANA         = 'H';
    public static final char OP_ZENKAKU_HIRAGANA_TO_HANKAKU_KATAKANA         = 'h';
    public static final char OP_HANKAKU_KATAKANA_TO_ZENKAKU_KATAKANA         = 'K';
    public static final char OP_ZENKAKU_KATAKANA_TO_HANKAKU_KATAKANA         = 'k';
    public static final char OP_HANKAKU_NUMBER_TO_ZENKAKU_NUMBER             = 'N';
    public static final char OP_ZENKAKU_NUMBER_TO_HANKAKU_NUMBER             = 'n';
    public static final char OP_HANKAKU_ALPHABET_TO_ZENKAKU_ALPHABET         = 'R';
    public static final char OP_ZENKAKU_ALPHABET_TO_HANKAKU_ALPHABET         = 'r';
    public static final char OP_HANKAKU_SPACE_TO_ZENKAKU_SPACE               = 'S';
    public static final char OP_ZENKAKU_SPACE_TO_HANKAKU_SPACE               = 's';
    public static final char OP_COLLAPSE_HANKAKU_VOICE_MARKS                 = 'V';


    //{{{ mbConvertKana()
    /**
      * Port of PHP's "mb_convert_kana" function for Java.
      * @details http://www.php.net/manual/en/function.mb-convert-kana.php
      *
      * @param  original_string  Input string to perform conversion on
      * @param  conversion_ops   One or more characters indicating which type of conversion to perform
      * @param  encoding         Character encoding of input string
      * @return                  Content of "original_string" with specified conversions performed
      */
    public static String mbConvertKana(String original_string, String conversion_ops, String encoding)
    {
        // Ensure function received strings, not nulls
        if(original_string == null
        || conversion_ops == null
        || encoding == null) {
            return null;
        }

        int flag_count = conversion_ops.length();
        if(flag_count < 1) {
            // Return original if no conversion requested
            return original_string;
        }

        int char_count = original_string.length();
        StringBuffer new_string = new StringBuffer();
        int i = 0;
        while(i < char_count) {
            // Init char holders for this round
            char this_char = original_string.charAt(i);
            char next_char = 0;
            if(i < (char_count - 1)) {
                next_char = original_string.charAt(i);
            }

            char current_char = this_char;
            for(int j = 0; j < flag_count; j++) {
                char current_flag = conversion_ops.charAt(j);

                switch(current_flag) {
                case OP_HANKAKU_ALPHANUMERIC_TO_ZENKAKU_ALPHANUMERIC:
                    break;
                case OP_ZENKAKU_ALPHANUMERIC_TO_HANKAKU_ALPHANUMERIC:
                    break;
                case OP_ZENKAKU_HIRAGANA_TO_ZENKAKU_KATAKANA:
                    break;
                case OP_ZENKAKU_KATAKANA_TO_ZENKAKU_HIRAGANA:
                    break;
                case OP_HANKAKU_KATAKANA_TO_ZENKAKU_HIRAGANA:
                    break;
                case OP_ZENKAKU_HIRAGANA_TO_HANKAKU_KATAKANA:
                    break;
                case OP_HANKAKU_KATAKANA_TO_ZENKAKU_KATAKANA:
                    break;
                case OP_ZENKAKU_KATAKANA_TO_HANKAKU_KATAKANA:
                    break;
                case OP_HANKAKU_NUMBER_TO_ZENKAKU_NUMBER:
                    current_char = convertHankakuNumberToZenkakuNumber(current_char);
                    break;
                case OP_ZENKAKU_NUMBER_TO_HANKAKU_NUMBER:
                    current_char = convertZenkakuNumberToHankakuNumber(current_char);
                    break;
                case OP_HANKAKU_ALPHABET_TO_ZENKAKU_ALPHABET:
                    break;
                case OP_ZENKAKU_ALPHABET_TO_HANKAKU_ALPHABET:
                    break;
                case OP_HANKAKU_SPACE_TO_ZENKAKU_SPACE:
                    current_char = convertHankakuSpaceToZenkakuSpace(current_char);
                    break;
                case OP_ZENKAKU_SPACE_TO_HANKAKU_SPACE:
                    current_char = convertZenkakuSpaceToHankakuSpace(current_char);
                    break;
                case OP_COLLAPSE_HANKAKU_VOICE_MARKS:
                    // Collapse voiced characters (hankaku) to voiced-kana-chars (zenkaku).  Use with 'K' or 'H'
                    break;
                }
            }

            new_string.append(current_char);
            i++;
        }

        return new_string.toString();
    }

    /**
      * Same as "mbConvertKana()" above, but with second param as char
      *
      * @param  original_string  Input string to perform conversion on
      * @param  conversion_op    One character indicating which type of conversion to perform
      * @param  encoding         Character encoding of input string
      * @return                  original_string with specified conversion performed
      */
    public static String mbConvertKana(String original_string, char conversion_op, String encoding)
    {
        return mbConvertKana(original_string, String.valueOf(conversion_op), encoding);
    }
    //}}}


    // Numeric constants
    protected static final char HANKAKU_NUMBER_FIRST = '0';
    protected static final char HANKAKU_NUMBER_LAST  = '9';
    protected static final char ZENKAKU_NUMBER_FIRST = '０';
    protected static final char ZENKAKU_NUMBER_LAST  = '９';

    // Other constants
    protected static final char HANKAKU_SPACE = ' ';
    protected static final char ZENKAKU_SPACE = '　';


    //{{{ 'N': convertHankakuNumberToZenkakuNumber()
    protected static char convertHankakuNumberToZenkakuNumber(char target)
    {
        if(target >= HANKAKU_NUMBER_FIRST && target <= HANKAKU_NUMBER_LAST) {
            // Offset by difference in char-code position
            return (char)(target + (ZENKAKU_NUMBER_FIRST - HANKAKU_NUMBER_FIRST));
        } else {
            return target;
        }
    }
    //}}}


    //{{{ 'n': convertZenkakuNumberToHankakuNumber()
    protected static char convertZenkakuNumberToHankakuNumber(char target)
    {
        if(target >= ZENKAKU_NUMBER_FIRST && target <= ZENKAKU_NUMBER_LAST) {
            // Offset by difference in char-code position
            return (char)(target - (ZENKAKU_NUMBER_FIRST - HANKAKU_NUMBER_FIRST));
        } else {
            return target;
        }
    }
    //}}}


    //{{{ 'S': convertHankakuSpaceToZenkakuSpace()
    protected static char convertHankakuSpaceToZenkakuSpace(char target)
    {
        if(target == HANKAKU_SPACE) {
            return ZENKAKU_SPACE;
        } else {
            return target;
        }
    }
    //}}}


    //{{{ 's': convertZenkakuSpaceToHankakuSpace()
    protected static char convertZenkakuSpaceToHankakuSpace(char target)
    {
        if(target == ZENKAKU_SPACE) {
            return HANKAKU_SPACE;
        } else {
            return target;
        }
    }
    //}}}
}
