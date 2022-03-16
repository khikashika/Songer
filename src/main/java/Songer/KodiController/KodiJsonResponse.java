package Songer.KodiController;

import java.util.Map;
public class KodiJsonResponse {
    int id;
    String jsonrpc;
    Map<Object,Object> result;

    public KodiJsonResponse() {
    }

    public int getId() {
        return this.id;
    }

    public String getJsonrpc() {
        return this.jsonrpc;
    }

    public Map<Object, Object> getResult() {
        return this.result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public void setResult(Map<Object, Object> result) {
        this.result = result;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof KodiJsonResponse)) return false;
        final KodiJsonResponse other = (KodiJsonResponse) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$jsonrpc = this.getJsonrpc();
        final Object other$jsonrpc = other.getJsonrpc();
        if (this$jsonrpc == null ? other$jsonrpc != null : !this$jsonrpc.equals(other$jsonrpc)) return false;
        final Object this$result = this.getResult();
        final Object other$result = other.getResult();
        if (this$result == null ? other$result != null : !this$result.equals(other$result)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof KodiJsonResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        final Object $jsonrpc = this.getJsonrpc();
        result = result * PRIME + ($jsonrpc == null ? 43 : $jsonrpc.hashCode());
        final Object $result = this.getResult();
        result = result * PRIME + ($result == null ? 43 : $result.hashCode());
        return result;
    }

    public String toString() {
        return "KodiJsonResponse(id=" + this.getId() + ", jsonrpc=" + this.getJsonrpc() + ", result=" + this.getResult() + ")";
    }
}
