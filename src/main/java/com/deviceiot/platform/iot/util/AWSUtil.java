package com.deviceiot.platform.iot.util;

import java.io.*;
import java.math.*;
import java.net.*;
import java.nio.file.*;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;

import com.amazonaws.services.iot.client.*;

/**
 * Created by admin on 8/8/17.
 */
public class AWSUtil {

    public static AWSIotMqttClient initClientWithCert(String clientEndpoint, String clientId, String certificateFile, String privateKeyFile, String algorithm) {
        if(clientEndpoint==null || clientId==null || certificateFile==null || privateKeyFile==null){
            throw new IllegalArgumentException("Failed to construct client due to missing certificate.");
        }
        KeyStorePasswordPair pair = AWSUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile, algorithm);
        final AWSIotMqttClient awsIotMqttClient = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
        if (awsIotMqttClient == null) {
            throw new IllegalArgumentException("Failed to construct client due to missing certificate.");
        }
        return awsIotMqttClient;
    }
    public static AWSIotMqttClient initClientWithToken(String clientEndpoint, String clientId, String awsAccessKeyId, String awsSecretAccessKey, String sessionToken){
        if(clientEndpoint==null || clientId==null || awsAccessKeyId==null || awsSecretAccessKey==null){
            throw new IllegalArgumentException("Failed to construct client due to missing credentials.");
        }
        final AWSIotMqttClient awsIotMqttClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey,
                sessionToken);
        if (awsIotMqttClient == null) {
            throw new IllegalArgumentException("Failed to construct client due to missing credentials.");
        }
        return awsIotMqttClient;
    }

    public static class KeyStorePasswordPair {
        public KeyStore keyStore;
        public String keyPassword;

        public KeyStorePasswordPair(KeyStore keyStore, String keyPassword) {
            this.keyStore = keyStore;
            this.keyPassword = keyPassword;
        }
    }

    public static KeyStorePasswordPair getKeyStorePasswordPair(String certificateFile, String privateKeyFile) {
        return getKeyStorePasswordPair(certificateFile, privateKeyFile, null);
    }

    public static KeyStorePasswordPair getKeyStorePasswordPair(String certificateFile, String privateKeyFile, String keyAlgorithm) {
        if (certificateFile == null || privateKeyFile == null) {
            System.out.println("Certificate or private key file missing");
            return null;
        }

        Certificate certificate = loadCertificateFromFile(certificateFile);
        PrivateKey privateKey = loadPrivateKeyFromFile(privateKeyFile, keyAlgorithm);
        if (certificate == null || privateKey == null) {
            return null;
        }

        return getKeyStorePasswordPair(certificate, privateKey);
    }

    public static KeyStorePasswordPair getKeyStorePasswordPair(Certificate certificate, PrivateKey privateKey) {
        KeyStore keyStore = null;
        String keyPassword = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            keyStore.setCertificateEntry("alias", certificate);

            // randomly generated key password for the key in the KeyStore
            keyPassword = new BigInteger(128, new SecureRandom()).toString(32);
            keyStore.setKeyEntry("alias", privateKey, keyPassword.toCharArray(), new Certificate[] { certificate });
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            System.out.println("Failed to create key store");
            return null;
        }

        return new KeyStorePasswordPair(keyStore, keyPassword);
    }

    private static Certificate loadCertificateFromFile(String filename) {
        Certificate certificate = null;

        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Certificate file not found: " + filename);
            return null;
        }
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file))) {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            certificate = certFactory.generateCertificate(stream);
        } catch (IOException | CertificateException e) {
            System.out.println("Failed to load certificate file " + filename);
        }

        return certificate;
    }

    private static PrivateKey loadPrivateKeyFromFile(String filename, String algorithm) {
        PrivateKey privateKey = null;

        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Private key file not found: " + filename);
            return null;
        }
        try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
            privateKey = PrivateKeyReader.getPrivateKey(stream, algorithm);
        } catch (IOException | GeneralSecurityException e) {
            System.out.println("Failed to load private key from file " + filename);
        }

        return privateKey;
    }
    private static String readJsonPayload(String fileWithoutSuffix) {
        String content = null;

        URL url = AWSUtil.class.getResource("/json/" + fileWithoutSuffix + ".json");
        try {
            System.out.println("Resources Path : " + url.getFile());
            content = new String(Files.readAllBytes(Paths.get(url.getFile())));
        } catch (IOException e) {

        }

        return content;
    }


}
