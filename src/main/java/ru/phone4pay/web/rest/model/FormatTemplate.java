package ru.phone4pay.web.rest.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 *
 * Created by Олег on 14.10.2017.
 */

@Component
public class FormatTemplate {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public String getAsPhone(String number){

        String code = findPattern(number);

        switch(code) {
            case "RU":
                number = number.replaceAll("^(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})$", "+$1 ($2) $3-$4-$5");
                break;

            case "UA":
                number = number.replaceAll("^(\\d{3})(\\d{2})(\\d{3})(\\d{2})(\\d{2})$", "+$1 ($2) $3-$4-$5");
                break;

            case "BY":
                number = number.replaceAll("^(\\d{3})(\\d{2})(\\d{2})(\\d{3})(\\d{2})$", "+$1 ($2) $3-$4-$5");

                break;

            case "KZ":
                number = number.replaceAll("^(\\d{1})(\\d{3})(\\d{3})(\\d{4})$", "+$1 $2 $3-$4");
                break;

            case "LT":
                number = number.replaceAll("^(\\d{3})(\\d{3})(\\d{5})$", "+$1 $2 $3");
                break;

            default:
                break;
        }
        return number;
    }

    private String findPattern(String input){

        HashMap<String, String> regs = new HashMap<String, String>();
        regs.put("RU", "^79\\d{9}$");
        regs.put("UA", "^380\\d{9}$");
        regs.put("BY", "^375\\d{9}$");
        regs.put("KZ", "^77\\d{9}$");
        regs.put("LT", "^3706\\d{7}$");
        for (HashMap.Entry<String, String> entry : regs.entrySet()) {
            Pattern p = Pattern.compile(entry.getValue());
            if (p.matcher(input).matches())
                return entry.getKey();
        }
        return "RU";
    }
}
