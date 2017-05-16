package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsingUtils {
    public final static String FINAM_SEPARATORS = "[,.;\t ]";

    public static String prepareDoubleValue(String doubleValue) {

        // 60 487.0000000 => 60487.0000000
        String result = doubleValue.replace(" ", "");

        // 60.487.0000000 => 60487.0000000
        if (result.matches("\\d+\\.\\d+\\.\\d+")) {
            result = result.replaceFirst("\\.", "");
        }

        // 60,487 => 60487
        if (result.matches("\\d+,\\d+.\\d+")) {
            result = result.replaceFirst(",", "");
        }

        // 60'487 => 60487
        if (result.matches("\\d+'\\d+.\\d+")) {
            result = result.replaceFirst("'", "");
        }

        // 60487.0000000 => 60487
        result = result.replace(".0000000", "");

        return result;
    }
    public static String detectSeparator(String header) {
        // <TICKER>;<PER>;<DATE>;<TIME>;<OPEN>;<HIGH>;<LOW>;<CLOSE>;<VOL>
        // , . ; \t ' '
        Pattern p = Pattern.compile(">" + FINAM_SEPARATORS + "<");
        Matcher m = p.matcher(header);
        if (m.find()) {
            String separator = m.group();
            separator = separator.replace(">", "").replace("<", "");
            return separator;
        }
        throw new IllegalArgumentException("The value of argument is not a file header string");
    }
}
