package com.kh.web.helper;

import com.kh.web.application.Consts;
import com.kh.web.dto.UserPageableDto;
import io.netty.handler.codec.base64.Base64Encoder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Helper {

    private static KeyPair keyPair;
    private static KeyFactory keyFactory;
    public synchronized static void init(){
    if(keyPair != null) return;
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);
    keyPair = keyPairGenerator.generateKeyPair();
    keyFactory = KeyFactory.getInstance("RSA");
    }

    public static Pageable getPageAbleFromDto(UserPageableDto dto){
       boolean isSorted = dto.getSortBy() != null && ! dto.getSortBy().isBlank();
       if(isSorted) {
           Sort sort = Sort.by(dto.getAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, dto.getSortBy());
           return PageRequest.of(dto.getNumber(), dto.getSize(), sort);
       }
        return PageRequest.of(dto.getNumber(),dto.getSize());
    }

    private static RSAPublicKey generatePublicKey(File keyFile){
        //init();
        RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();


        String body = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());

        String total = Consts.begin_public_key + "\n" + body + "\n" + Consts.end_public_key;

       String parentDir = getFileParentDir(keyFile);
       Files.createDirectories(Paths.get(parentDir));
       Files.createFile(Paths.get(keyFile.toString()));
        Files.write((keyFile.toPath() , total.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
        return rsaPublicKey;
    }
    private static RSAPrivateKey generatePrivateKey(File keyFile){
        //init();
        RSAPrivateKey rSAPrivateKey = (RSAPrivateKey)keyPair.getPublic();


        String body = Base64.getEncoder().encodeToString(rSAPrivateKey.getEncoded());

        String total = Consts.begin_private_key + "\n" + body + "\n" + Consts.end_private_key;

        String parentDir = getFileParentDir(keyFile);
        Files.createDirectories(Paths.get(parentDir));
        Files.createFile(Paths.get(keyFile.toString()));
        Files.write((keyFile.toPath() , total.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
        return rSAPrivateKey;
    }
    public static RSAPublicKey loadPublicKey(){
        init();
        File keyFile = Paths.get(Consts.security_keys_path, Consts.security_publicKey_name).toFile();
        if( ! keyFile.exists()) return generatePublicKey(keyFile);
        String content = Files.readString(keyFile.toPath(), StandardCharsets.UTF_8)
                .replace(Consts.begin_public_key, "")
                .replace(Consts.end_public_key, "")
                .replaceAll("\\s", "");
              content = Base64.getDecoder().decode(content);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(content.getBytes(StandardCharsets.UTF_8));
         return  (RSAPublicKey) keyFactory.generatePublic(x509);
    }
    public static RSAPrivateKey loadPrivateKey(){
        init();
        File keyFile = Paths.get(Consts.security_keys_path, Consts.security_privateKey_name).toFile();
        if( ! keyFile.exists()) return generatePrivateKey(keyFile);
        String content = Files.readString(keyFile.toPath(), StandardCharsets.UTF_8)
                .replace(Consts.begin_private_key, "")
                .replace(Consts.end_private_key, "")
                .replaceAll("\\s", "");
        content = Base64.getDecoder().decode(content);
        PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(content.getBytes(StandardCharsets.UTF_8));
        return  (RSAPrivateKey) keyFactory.generatePrivate(pkcs);
    }

    public static String getFileParentDir(File file){
        int fileNameLength = file.getName().length();
        int parentLength = file.toString().length() - fileNameLength;
        return file.toString().substring(0, parentLength);
    }
}
