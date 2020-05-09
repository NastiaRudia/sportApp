package com.example.myapplication.retrofit;

import okhttp3.*;
import okhttp3.Response;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class HTTPLogInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(HTTPLogInterceptor.class);

    private boolean isEnabled = true;

    private boolean requestBody = true;

    private boolean disableRequestBodyOnce = false;

    private boolean respBody = true;

    private boolean disableResponseBodyOnce = false;

    public void enableReqBody() {
        requestBody = true;
    }

    public void disableReqBody() {
        requestBody = false;
    }

    public void enableRespBody() {
        respBody = true;
    }

    public void disableRespBody() {
        respBody = false;
    }

    public void disableReqBodyOnce() {
        disableRequestBodyOnce = true;
    }

    public void disableRespBodyOnce() {
        disableResponseBodyOnce = true;
    }

    public void disableBodiesOnce() {
        disableRespBodyOnce();
        disableReqBodyOnce();
    }

    public void disableBodies() {
        disableReqBody();
        disableRespBody();
    }

    public void isEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setEnabled() {
        isEnabled = true;
    }

    public void setDisabled() {
        isEnabled = false;
    }

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private Level level = Level.BODY;

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        if (!isEnabled) return chain.proceed(chain.request());
        String threadName = Thread.currentThread().getName();
        StringBuffer sb = new StringBuffer();
        String attachmentName = "REQUEST: ";

        Request request = chain.request();
        Headers headers = request.headers();
        RequestBody body = request.body();

        //START
        sb.append("REQUEST LOG");

        // ADD REQUEST URL
        sb.append("\n[" + threadName + "]---> " + request.method() + " " + request.url());
        attachmentName += request.method() + " " + request.url().encodedPath();

        //ADD REQUEST HEADERS
        if (body != null) {
            if (body.contentType() != null) sb.append("\n[" + threadName + "]---> Content-Type: " + body.contentType());
            if (body.contentLength() != -1)
                sb.append("\n[" + threadName + "]---> Content-Length: " + body.contentLength());
        }

        for (int i = 0; i < headers.size(); i++) {
            sb.append("\n[" + threadName + "]---> " + headers.name(i) + ": " + headers.value(i));
        }

        //ADD REQUEST BODY
        if (disableRequestBodyOnce) {
            try {
                logger.info("ADD REQUEST BODY PART DISABLED FOR SINGLE REQUEST.");
            } finally {
                disableRequestBodyOnce = false;
            }
        } else if (!requestBody) {
            logger.info("ADD REQUEST BODY PART DISABLED FOR ALL REQUESTS.");
        } else {
            if (body != null) {
                Charset charset = UTF8;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                if (isPlaintext(buffer)) {
                    sb.append("\n[" + threadName + "]---> BODY:\n" + buffer.readString(charset));
                    sb.append("\n[" + threadName + "]---> END " + request.method() + " (" + body.contentLength() + "-byte body)");
                } else {
                    sb.append("\n[" + threadName + "]---> END " + request.method() + " (binary " + body.contentLength() + "-byte body omitted)");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;

        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            sb.append("\n[" + threadName + "]<--- HTTP FAILED: " + e);
            e.printStackTrace();
            writeLog(sb, e, attachmentName);
            throw e;
        }
        try {
            String request_id = response.header("x-request-id");
            sb.append("\n[" + threadName + "]<--- x-request-id: " + request_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        sb.append("\n[" + threadName + "]<--- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + (", " + bodySize + " body") + ')');
        String str = responseBody.string();
        if (disableResponseBodyOnce) {
            try {
                logger.info("ADD RESPONSE BODY PART DISABLED FOR SINGLE REQUEST.");
            } finally {
                disableResponseBodyOnce = false;
            }
        } else if (!respBody) {
            logger.info("ADD RESPONSE BODY PART DISABLED FOR ALL REQUESTS.");
        } else {
            sb.append("\n[" + threadName + "]<--- RESPONSE BODY:\n" + str);
        }
        sb.append("\n[" + threadName + "]<--- END HTTP\n");


        writeLog(sb, attachmentName);
        return response;
    }

    public void writeLog(StringBuffer sb, String attachmentName) {
        String data = sb.toString();
        logger.info(data);
    }

    public void writeLog(StringBuffer sb, Exception e, String attachmentName) {
        logger.error(sb.toString());
    }


    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                if (Character.isISOControl(prefix.readUtf8CodePoint())) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         *
         * <photo>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         *
         * <photo>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         * <photo>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END GET
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

}
