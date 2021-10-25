package com.thiendz.wipe.wipeserve.dto.response;

import com.thiendz.wipe.wipeserve.utils.constant.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response<T> {
    boolean status;
    String message;
    T data;

    public Response(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(T data) {
        this.status = true;
        this.message = Message.SUCCESS;
        this.data = data;
    }
}
