package Songer.RPCObjects;

import lombok.Data;

import java.util.Map;
@Data
public class KodiJsonResponse {
    int id;
    String jsonrpc;
    Map<Object,Object> result;
}
