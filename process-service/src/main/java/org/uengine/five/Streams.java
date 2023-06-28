package org.uengine.five;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Streams {
    String INPUT = "bpm-topic";

    // Inbound - Outbound 같으면 오류 발생 하는 것으로 보임..
    // Error : Dispatcher has no subscribers for channel 'bpm-topic' 
    // 
    @Input(INPUT)
    SubscribableChannel inboundChannel();

    // @Output(INPUT)
    // MessageChannel outboundChannel();

    @Output("python")
    MessageChannel outboundPython();

    // @Input("java")
    // MessageChannel inboundPython();

}
