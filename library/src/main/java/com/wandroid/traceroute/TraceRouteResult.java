package com.wandroid.traceroute;

/**
 * TracerouteResult data class
 */
public class TraceRouteResult {

    private int code;
    private String message;

    private TraceRouteResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static TraceRouteResult instance() {
        return new TraceRouteResult(-1, "");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
