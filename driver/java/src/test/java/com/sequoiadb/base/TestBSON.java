package com.sequoiadb.base;

import org.bson.*;
import org.bson.io.Bits;
import org.bson.types.*;
import org.bson.util.JSON;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class TestBSON {
    private static BSONObject object;
    private static byte[] objBytes;

    @BeforeClass
    public static void setUpTestCase() {
        BSONObject embeddedObj = new BasicBSONObject();
        embeddedObj.put("int", 123);
        embeddedObj.put("long", 12345L);
        embeddedObj.put("double", 123.456);
        embeddedObj.put("string", "hello");
        embeddedObj.put("null", null);
        embeddedObj.put("maxKey", new MaxKey());
        embeddedObj.put("minKey", new MinKey());
        embeddedObj.put("oid", new ObjectId());
        embeddedObj.put("true", true);
        embeddedObj.put("false", false);
        embeddedObj.put("date", new Date());
        embeddedObj.put("timestamp", new BSONTimestamp((int) (System.currentTimeMillis() / 1000), 1234));
        embeddedObj.put("decimal", new BSONDecimal("12345678901234567890.09876543210987654321"));

        BasicBSONList embeddedArray = new BasicBSONList();
        embeddedArray.put("0", 123);
        embeddedArray.put("1", 12345L);
        embeddedArray.put("2", 123.456);
        embeddedArray.put("3", "hello");
        embeddedArray.put("4", null);
        embeddedArray.put("5", new MaxKey());
        embeddedArray.put("6", new MinKey());
        embeddedArray.put("7", new ObjectId());
        embeddedArray.put("8", true);
        embeddedArray.put("9", false);
        embeddedArray.put("10", new Date());
        embeddedArray.put("11", new BSONTimestamp((int) (System.currentTimeMillis() / 1000), 1234));
        embeddedArray.put("12", new BSONDecimal("12345678901234567890.09876543210987654321"));

        Binary binary1 = new Binary(BSON.B_GENERAL, "Hello, world!".getBytes());
        Binary binary2 = new Binary(BSON.B_FUNC, "Hello, world!".getBytes());
        Binary binary3 = new Binary(BSON.B_BINARY, "Hello, world!".getBytes());
        UUID binary4 = UUID.randomUUID();

        BSONObject obj = new BasicBSONObject();
        obj.put("int", 123);
        obj.put("long", 12345L);
        obj.put("double", 123.456);
        obj.put("string", "hello");
        obj.put("null", null);
        obj.put("maxKey", new MaxKey());
        obj.put("minKey", new MinKey());
        obj.put("oid", new ObjectId());
        obj.put("true", true);
        obj.put("false", false);
        obj.put("date", new Date());
        obj.put("timestamp", new BSONTimestamp((int) (System.currentTimeMillis() / 1000), 1234));
        obj.put("decimal", new BSONDecimal("12345678901234567890.09876543210987654321"));
        obj.put("binary1", binary1);
        obj.put("binary2", binary2);
        obj.put("binary3", binary3);
        obj.put("binary4", binary4);
        obj.put("object", embeddedObj);
        obj.put("array", embeddedArray);
        obj.put("code", new Code("你好！"));
        obj.put("codewscope", new CodeWScope("hello", new BasicBSONObject()));
        obj.put("regex", Pattern.compile("^a", BSON.regexFlags("i")));
        obj.put("symbol", new Symbol("你好！"));

        object = obj;
        objBytes = BSON.encode(obj);
    }

   /* @Test
    public void testNewBSONDecoder() {
        byte[] bytes = objBytes;

        BSONDecoder decoder = new NewBSONDecoder();
        BSONObject decodedObj = decoder.readObject(bytes);

        assertEquals(object, decodedObj);

        Binary binary1 = (Binary) object.get("binary1");
        Binary bin1 = (Binary) decodedObj.get("binary1");
        assertEquals(binary1, bin1);

        Binary binary2 = (Binary) object.get("binary2");
        Binary bin2 = (Binary) decodedObj.get("binary2");
        assertEquals(binary2, bin2);

        Binary binary3 = (Binary) object.get("binary3");
        Binary bin3 = (Binary) decodedObj.get("binary3");
        assertEquals(binary3, bin3);

        UUID binary4 = (UUID) object.get("binary4");
        UUID bin4 = (UUID) decodedObj.get("binary4");
        assertEquals(binary4, bin4);
    }*/

    /*@Test
    public void testNewBSONDecoder2() {
        byte[] bytes = objBytes;
        byte[] bytes2 = new byte[bytes.length + 10];
        System.arraycopy(bytes, 0, bytes2, 10, bytes.length);

        BSONDecoder decoder = new NewBSONDecoder();
        BasicBSONCallback callback = new BasicBSONCallback();

        int length = decoder.decode(bytes2, 10, callback);
        assertEquals(Bits.readInt(bytes), length);
    }

    @Test
    public void testNewBSONDecoder3() {
        byte[] bytes = objBytes;
        byte[] bytes2 = new byte[bytes.length + 10];
        System.arraycopy(bytes, 0, bytes2, 10, bytes.length);

        BSONDecoder decoder = new NewBSONDecoder();
        BasicBSONCallback callback = new BasicBSONCallback();

        int length = 0;
        try {
            length = decoder.decode(new ByteArrayInputStream(bytes2, 10, bytes.length), callback);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(Bits.readInt(bytes), length);
    }*/

}
