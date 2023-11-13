import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

class MultipartStreamParserTest {

    @Test
    public void Test1() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("--MIN_MEGA_FEDE_BOUNDARYhejhesdsdhfsjhdkfjs\r\nkdfksdhfkhsdkjhfksjhdfkjhsdkjfhskjdhffksdfj\r\n--MIN_MEGA_FEDE_BOUNDARY\r\ncontentshålllo\r\n\r\nMessageMessageMessageMessageMessageMessageMessage--MIN_MEGA_FEDE_BOUNDARY--".getBytes(StandardCharsets.UTF_8));
        OutputStream outputStream = new ByteArrayOutputStream();
        byte[] boundary = "MIN_MEGA_FEDE_BOUNDARY".getBytes(StandardCharsets.UTF_8);
        MultipartStreamParser multipartStreamParser = new MultipartStreamParser(inputStream, boundary, outputStream);
        multipartStreamParser.extractMessage();
        System.out.println(outputStream);


    }

    @Test
    public void test2() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("--MIN_MEGA_FEDE_BOUNDARYhejhesdsdhfsjhdkfjs\r\nkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfj\r\n--MIN_MEGA_FEDE_BOUNDARYcontentshålllo\r\n\r\nMessageMessageMessageMessageMessageMessageMessage--MIN_MEGA_FEDE_BOUNDARY--".getBytes(StandardCharsets.UTF_8));
        OutputStream outputStream = new ByteArrayOutputStream();
        byte[] boundary = "MIN_MEGA_FEDE_BOUNDARY".getBytes(StandardCharsets.UTF_8);
        MultipartStreamParser multipartStreamParser = new MultipartStreamParser(inputStream, boundary, outputStream);
        multipartStreamParser.extractMessage();
        System.out.println(outputStream);
    }

    @Test
    public void test3() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("--MIN_MEGA_FEDE_BOUNDARYhejhesdsdhfsjhdkfjs\r\nkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfjkdfkshsdkjfhskjdhffksdfj\r\n--MIN_MEGA_FEDE_BOUNDARYcontentshålllo\r\n\r\nMessageMessageMessageMessageMessageMessageMessage--MIN_MEGA_FEDE_BOUNDARY--".getBytes(StandardCharsets.UTF_8));
        OutputStream outputStream = new ByteArrayOutputStream();
        byte[] boundary = "MIN_MEGA_FEDE_BOUNDARY".getBytes(StandardCharsets.UTF_8);
        MultipartStreamParser multipartStreamParser = new MultipartStreamParser(inputStream, boundary, outputStream);
        multipartStreamParser.extractMessage();
        System.out.println(outputStream);
    }

}