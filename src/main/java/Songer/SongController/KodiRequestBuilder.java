package Songer.SongController;

public class KodiRequestBuilder {
    KodiRequest kodi = new KodiRequest();


    public KodiRequestBuilder method(String method){
        kodi.setMethod(method);
        return this;

    }
     public KodiRequestBuilder params(Object key,Object value){
        kodi.setParams(key, value);
        return this;
     }
     public KodiRequest build(){
        return kodi;
     }
}
