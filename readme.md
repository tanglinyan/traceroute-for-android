# traceroute-for-java-android
Easy way to use traceroute on Android.

## Description

Traceroute tracks the route packets taken from an IP network on their way to a given host. It utilizes the IP protocol's time to live (TTL) field and attempts to elicit an ICMP TIME_EXCEEDED response from each gateway along the path to the host.

## Rendering Image

![images/1.png](images/1.png)

## Adding to project

```groovy
dependencies {
    implementation 'com.wandroid:traceroute:<latest-version>'
}
```

## Simple usage

synchronous way

```java
TraceRouteResult traceRouteResult = TraceRoute.getInstance().traceRoute("www.google.com");
```

asynchronous way

```java
TraceRoute traceRoute = TraceRoute.getInstance();
traceRoute.setCallback(new TraceRouteCallback() {
    @Override
    public void onSuccess(TraceRouteResult traceRouteResult) {
        Log.d("TraceRoute", "\ntraceroute finish");

    @Override
    public void onUpdate(String text) {
        Log.d("TraceRoute", text);

    @Override
    public void onFailed(int code, String reason) {
        Log.d("TraceRoute", "\ntraceroute failed.code:" + code + ", reason:" + reason);
    }
});
traceRoute.traceRoute("www.google.com", true);
```

## Proguard

```proguard
-keepclassmembers com.wandroid.traceroute.TraceRoute {
    void clearResult();
    void appendResult(***);
    int execute(***);
}
```

get executable file from [traceroute-android-executable](https://github.com/wangjing53406/traceroute-android-executable)
