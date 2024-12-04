package com.wandroid.traceroute;

import android.os.Handler;
import android.os.Looper;

/**
 * Traceroute on Android with JNI (Singleton version).
 */
public class TraceRoute {

    // Singleton instance
    private static TraceRoute instance;

    // Record traceroute messages
    private StringBuilder result;

    // Traceroute callback to user
    private TraceRouteCallback callback;

    // Handler to switch thread to Android main thread
    private Handler handler;


    // Static block to load the native library
    static {
        // Load JNI library when the class is loaded
        System.loadLibrary("traceroute");
    }

    // Private constructor to prevent instantiation
    private TraceRoute() {
        // Initialize resources here
        result = new StringBuilder();
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * Get the singleton instance of TraceRoute.
     * The JNI library is loaded here to ensure it happens only once.
     *
     * @return the single instance of TraceRoute
     */
    public static TraceRoute getInstance() {
        if (instance == null) {
            synchronized (TraceRoute.class) {
                if (instance == null) {
                    instance = new TraceRoute();
                }
            }
        }
        return instance;
    }

    /**
     * Set the callback that will run.
     *
     * @param callback The callback to be used
     */
    public void setCallback(TraceRouteCallback callback) {
        this.callback = callback;
    }

    /**
     * Simplified method for Kotlin-style callback.
     *
     * @param traceRouteCallback The callback to run
     */
    public void setCallback(SimpleTraceRouteCallback traceRouteCallback) {
        traceRouteCallback.apply();
        setCallback(traceRouteCallback);
    }

    /**
     * Clear the result.
     */
    public void clearResult() {
        result.setLength(0); // Clear the result by resetting the StringBuilder
    }

    /**
     * Append result from JNI.
     *
     * @param text Current traceroute message
     */
    public void appendResult(String text) {
        result.append(text);
        if (callback != null) {
            handler.post(() -> callback.onUpdate(text));
        }
    }

    /**
     * Perform traceroute with hostname.
     *
     * @param hostname The hostname for traceroute
     * @param async    Whether to perform asynchronously
     * @return TraceRouteResult
     */
    public synchronized TraceRouteResult traceRoute(String hostname, boolean async) {
        String[] args = {"traceroute", hostname};
        if (async) {
            new Thread(() -> traceRoute(args), "trace_route_thread").start();
        } else {
            return traceRoute(args);
        }
        return null;
    }

    /**
     * Perform traceroute with command arguments.
     *
     * @param args The command arguments
     * @return TraceRouteResult
     */
    public synchronized TraceRouteResult traceRoute(String[] args) {
        TraceRouteResult traceRouteResult = TraceRouteResult.instance();
        traceRouteResult.setCode(execute(args));
        if (traceRouteResult.getCode() == 0) {
            traceRouteResult.setMessage(result.toString());
            handler.post(() -> callback.onSuccess(traceRouteResult));
        } else {
            traceRouteResult.setMessage("execute traceroute failed.");
            handler.post(() -> callback.onFailed(traceRouteResult.getCode(), traceRouteResult.getMessage()));
        }
        return traceRouteResult;
    }

    /**
     * JNI interface to execute traceroute.
     *
     * @param args The traceroute command arguments
     * @return Execution result code
     */
    public native int execute(String[] args);
}
