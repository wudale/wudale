package spring.core;

import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.SystemPropertyUtils;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/2/6
 */
public class PropertyPlaceHolderUtilMain {

    public static void main(String[] args) {

        PropertyPlaceholderHelper nonStrictHelper =
            new PropertyPlaceholderHelper(SystemPropertyUtils.PLACEHOLDER_PREFIX,
					SystemPropertyUtils.PLACEHOLDER_SUFFIX, SystemPropertyUtils.VALUE_SEPARATOR, true);

        PropertyPlaceholderHelper strictHelper =
            new PropertyPlaceholderHelper(SystemPropertyUtils.PLACEHOLDER_PREFIX,
					SystemPropertyUtils.PLACEHOLDER_SUFFIX, SystemPropertyUtils.VALUE_SEPARATOR, false);
        //以上代码参考自：org.springframework.web.util.ServletContextPropertyUtils，org.springframework.web.util.Log4jWebConfigurer.initLogging()


        PropertyPlaceholderHelper.PlaceholderResolver resolver = (placeholderName) -> {
            if ("testName".equals(placeholderName)) {
                return "wudale";
            }else if("testName2".equals(placeholderName)){
                return "wudale2";
            }else {
                return null;
            }
        };

        System.out.println(nonStrictHelper.replacePlaceholders("${testName}", resolver));
        System.out.println(nonStrictHelper.replacePlaceholders("${testName2}", resolver));
        System.out.println(nonStrictHelper.replacePlaceholders("${testName3}", resolver));

        System.out.println(strictHelper.replacePlaceholders("${testName}", resolver));
        System.out.println(strictHelper.replacePlaceholders("${testName2}", resolver));
        System.out.println(strictHelper.replacePlaceholders("${testName3}", resolver)); //这里应该抛出异常
    }

}
