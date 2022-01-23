import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class ObjectToJsonString {
    public String makeJsonString(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        try {
            mapper.writeValue(stringWriter,obj);
        } catch (IOException e) {
            System.out.println("Can't map object");
            e.printStackTrace();
        }
        String strJson = stringWriter.toString();

        strJson = strJson.replace("\"","\\\"");
        strJson="\""+strJson+"\"";
        System.out.println(strJson);

        return strJson;
    }
}
