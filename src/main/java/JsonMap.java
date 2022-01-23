import java.util.HashMap;
import java.util.Map;

public class JsonMap <K,V> extends HashMap<K,V> {
        private static final long serialVersionUID = 1L;
        private static String SEPARATOR = "";
        private static final char QUOTE = '"';
        private static final char DOTS = ':';

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<K, V> entry : this.entrySet()) {
                sb.append(SEPARATOR);

                // same as sb.append("" + QUOTE + entry.getKey() + QUOTE + ":"  + QUOTE + entry.getValue() + QUOTE);
                sb.append(QUOTE);
                sb.append(entry.getKey());
                sb.append(QUOTE);
                sb.append(DOTS);
                sb.append(QUOTE);
                sb.append(entry.getValue());
                sb.append(QUOTE);
                SEPARATOR = ", ";
            }
            return sb.toString();
        }

    }

