package Networking.rpcprotocol;

import java.io.Serializable;

/**
 * Created by Utilizator on 03-Apr-17.
 */
public class Request implements Serializable {

    private RequestType type;

    private Object data;

    private Request(){}

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static class Builder{
        private Request request = new Request();

        public Builder type(RequestType type){
            request.setType(type);
            return this;
        }

        public Builder data(Object data){
            request.setData(data);
            return this;
        }

        public Request build(){
            return request;
        }
    }
}
