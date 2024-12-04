package com.wandroid.traceroute;

/**
 * Wrapper class for simple use with callbacks.
 */
public class SimpleTraceRouteCallback implements TraceRouteCallback {

    private OnSuccessListener onSuccessListener;
    private OnUpdateListener onUpdateListener;
    private OnFailedListener onFailedListener;

    public interface OnSuccessListener {
        void onSuccess(TraceRouteResult traceRouteResult);
    }

    public interface OnUpdateListener {
        void onUpdate(String text);
    }

    public interface OnFailedListener {
        void onFailed(int code, String reason);
    }

    public void success(OnSuccessListener listener) {
        this.onSuccessListener = listener;
    }

    public void update(OnUpdateListener listener) {
        this.onUpdateListener = listener;
    }

    public void failed(OnFailedListener listener) {
        this.onFailedListener = listener;
    }

    @Override
    public void onSuccess(TraceRouteResult traceRouteResult) {
        if (onSuccessListener != null) {
            onSuccessListener.onSuccess(traceRouteResult);
        }
    }

    @Override
    public void onUpdate(String text) {
        if (onUpdateListener != null) {
            onUpdateListener.onUpdate(text);
        }
    }

    @Override
    public void onFailed(int code, String reason) {
        if (onFailedListener != null) {
            onFailedListener.onFailed(code, reason);
        }
    }

    public void apply() {
        // Placeholder method for Kotlin-style function usage
    }
}
