package com.example.soonsul.user.oauth.client;

import com.example.soonsul.user.oauth.OAuthProvider;
import com.example.soonsul.user.oauth.param.OAuthLoginParams;
import com.example.soonsul.user.oauth.response.AppleInfoResponse;
import com.example.soonsul.user.oauth.response.OAuthInfoResponse;
import com.example.soonsul.user.oauth.token.AppleTokens;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.*;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.nimbusds.jose.crypto.ECDSASigner;

import java.io.*;
import java.net.URL;
import java.security.interfaces.ECPrivateKey;
import java.util.Date;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.StringReader;
import java.security.PrivateKey;
import java.security.Security;

@Component
@RequiredArgsConstructor
public class AppleApiClient implements OAuthApiClient {
    private static final String GRANT_TYPE = "authorization_code";
    private static final String APPLE_AUTH_URL = "https://appleid.apple.com";

    @Value("${oauth.apple.team-id}")
    private String teamId;

    @Value("${oauth.apple.login-key}")
    private String loginKey;

    @Value("${oauth.apple.client-id}")
    private String clientId;

    @Value("${oauth.apple.redirect-url}")
    private String redirectUri;

    @Value("${oauth.apple.key-path}")
    private String keyPath;

    private final RestTemplate restTemplate;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.APPLE;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", createClientSecret());
        body.add("redirect_uri", redirectUri);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        AppleTokens response = restTemplate.postForObject(APPLE_AUTH_URL + "/auth/token", request, AppleTokens.class);
        assert response != null;

        SignedJWT signedJWT = SignedJWT.parse(response.getIdToken());
        ReadOnlyJWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();

        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject payload = objectMapper.readValue(getPayload.toJSONObject().toJSONString(), JSONObject.class);

        return String.valueOf(payload.get("sub"));
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        return AppleInfoResponse.builder()
                .id(accessToken)
                .build();
    }

    private String createClientSecret() throws Exception {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(loginKey).build();
        JWTClaimsSet claimsSet = new JWTClaimsSet();

        Date now = new Date();
        claimsSet.setIssuer(teamId);
        claimsSet.setIssueTime(now);
        claimsSet.setExpirationTime(new Date(now.getTime() + 3600000));
        claimsSet.setAudience(APPLE_AUTH_URL);
        claimsSet.setSubject(clientId);

        SignedJWT jwt = new SignedJWT(header, claimsSet);

        try {
            ECPrivateKey ecPrivateKey = convertToECPrivateKey(getPrivateKey());
            JWSSigner jwsSigner = new ECDSASigner(ecPrivateKey.getS());
            jwt.sign(jwsSigner);
        } catch (Exception e) {
            throw new Exception("Failed create client secret");
        }

        return jwt.serialize();
    }

    private ECPrivateKey convertToECPrivateKey(byte[] keyBytes) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        String privateKeyPEM = new String(keyBytes);
        PEMParser pemParser = new PEMParser(new StringReader(privateKeyPEM));
        Object object = pemParser.readObject();

        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        PrivateKey privateKey = converter.getPrivateKey(((PEMKeyPair) object).getPrivateKeyInfo());

        return (ECPrivateKey) privateKey;
    }

    private byte[] getPrivateKey() throws Exception {
        byte[] content = null;
        File file = null;

        URL res = getClass().getResource(keyPath);

        if ("jar".equals(res.getProtocol())) {
            try {
                InputStream input = getClass().getResourceAsStream(keyPath);
                file = File.createTempFile("tempfile", ".tmp");
                OutputStream out = new FileOutputStream(file);

                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                out.close();
                file.deleteOnExit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else file = new File(res.getFile());

        if (file.exists()) {
            try (FileReader keyReader = new FileReader(file);
                 PemReader pemReader = new PemReader(keyReader))
            {
                PemObject pemObject = pemReader.readPemObject();
                content = pemObject.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else throw new Exception("File " + file + " not found");

        return content;
    }
}
