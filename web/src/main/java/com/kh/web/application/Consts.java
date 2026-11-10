package com.kh.web.application;

public class Consts {

    public static String user_service_name ="userService";
    public static String user_service_name ="userService";

    public static  enum authority  {ROLE_ADMIN, ROLE_GUEST, CREATE, READ, DELETE, PRINT};
    public static String authority_claim_name ="authority";

    public static  String security_keys_path="src/main/resources/sec_keys/";
    public static  String security_privateKey_name="private.pem";
    public static  String security_publicKey_name="public.pem";

    public static String begin_public_key = "-----BEGIN PUBLIC KEY-----";
    public static String end_public_key = "-----END PUBLIC KEY-----";
    public static String begin_private_key = "-----BEGIN PRIVATE KEY-----";
    public static String end_private_key = "-----END PRIVATE KEY-----";

}
