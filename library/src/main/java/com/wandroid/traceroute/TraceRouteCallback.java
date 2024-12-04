package com.wandroid.traceroute;

/**
 * Traceroute callback interface.
 */
public interface TraceRouteCallback {

    void onSuccess(TraceRouteResult traceRouteResult);

    void onUpdate(String text);

    void onFailed(int code, String reason);
}