package com.kh.web;

import com.kh.web.application.Consts;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.resource.NoResourceFoundException;

import java.io.File;
import java.nio.file.Paths;

public class Test {

    static void main() {
        File keyFile = Paths.get(Consts.security_keys_path, Consts.security_publicKey_name).toFile();
        String parent = keyFile.toString().substring(0, keyFile.toString().lastIndexOf("\\"));
        System.out.println(parent);
    }
}
