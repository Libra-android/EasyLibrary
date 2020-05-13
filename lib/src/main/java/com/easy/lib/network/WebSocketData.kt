package com.easy.lib.network

import ua.naiksoftware.stomp.dto.StompMessage

/**
 * @author too young
 * @date  2020/4/23 21:23
 */
data class WebSocketData(val stompMessage: StompMessage?, val throwable: Throwable?)
