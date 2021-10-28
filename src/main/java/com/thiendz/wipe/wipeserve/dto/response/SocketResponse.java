package com.thiendz.wipe.wipeserve.dto.response;

import com.thiendz.wipe.wipeserve.utils.enums.SocketType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class SocketResponse<T> extends Response<T> {
    SocketType type;

    public SocketResponse(boolean status, String message, T data, SocketType type) {
        super(status, message, data);
        this.type = type;
    }

    public SocketResponse(SocketType type) {
        this.type = type;
    }

    public SocketResponse(boolean status, String message, SocketType type) {
        super(status, message);
        this.type = type;
    }

    public SocketResponse(String message, SocketType type) {
        super(message);
        this.type = type;
    }

    public SocketResponse(T data, SocketType type) {
        super(data);
        this.type = type;
    }
}
