package com.wzf.com.sample.volley;

/**
 * Created by soonlen on 2017/3/28 11:00.
 * email wangzheng.fang@zte.com.cn
 */

public class Response<T> {

    public interface Listener<T> {
        public void response(T response);
    }

    public interface ErrorListener<T> {
        public void onErrorResponse(T error);
    }

    /**
     * Parsed response, or null in the case of error.
     */
    public final T result;

    /**
     * Returns a successful response containing the parsed result.
     */
    public static <T> Response<T> success(T result) {
        return new Response<T>(result);
    }

    /**
     * Returns a failed response containing the given error code and an optional
     * localized message displayed to the user.
     */
    public static <T> Response<T> error(VolleyError error) {
        return new Response<T>((T) error);
    }

    private Response(T result) {
        this.result = result;
    }
}
