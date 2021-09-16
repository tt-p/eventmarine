package yte.intern.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import yte.intern.common.enums.Message;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageResponse<T> {

    final private Message message;
    private T t;

}
