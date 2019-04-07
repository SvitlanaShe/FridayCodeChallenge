import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public enum PatternsLibrary {
    P0 {
        @Override
        public List<Pattern> getPatternsCollection() {
            Pattern p = java.util.regex.Pattern.compile("^([A-Za-zÀ-ž]+\\s?)*(\\d*)\\s*([A-Za-zÀ-ž]+(\\s?))*\\d*$");
            Pattern street = java.util.regex.Pattern.compile("^([A-Za-zÀ-ž]+\\s?)*(\\d*)\\s");
            Pattern houseNumber = java.util.regex.Pattern.compile("^\\s?([A-Za-zÀ-ž0-9]+(\\s?))*\\d*$");

            return  Arrays.asList(new Pattern[]{p, street, houseNumber});
        }
    },
    P1 {
        @Override
        public List<Pattern> getPatternsCollection() {
            Pattern p = java.util.regex.Pattern.compile("^\\d*,?\\s?([A-Za-zÀ-ž]+\\s?)*$");
            Pattern houseNumber = java.util.regex.Pattern.compile("^\\d+,?");
            Pattern street = java.util.regex.Pattern.compile("\\s?([A-Za-zÀ-ž]+\\s?)*$");

            return  Arrays.asList(new Pattern[]{p, street, houseNumber});
        }
    },
    P2 {
        @Override
        public List<Pattern> getPatternsCollection() {
            Pattern p = java.util.regex.Pattern.compile("^([A-Za-zÀ-ž]+\\s?)*(,?)(\\s*)\\d*\\s?([A-Za-z]?)$");
            Pattern street = java.util.regex.Pattern.compile("^([A-Za-zÀ-ž]+\\s?)*(,?)");
            Pattern houseNumber = java.util.regex.Pattern.compile("^\\s?\\d*\\s?([A-Za-z]?)$");

            return  Arrays.asList(new Pattern[]{p, street, houseNumber});
        }
    };

    public abstract List<Pattern> getPatternsCollection();

}
