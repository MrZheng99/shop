package com.zyl.shop.conf;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AliPayConfig {

    public static String appId="2021000116675778";
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchantPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDNIX4hC5lyiCVt\n" +
            "7hT/H9GJzNItQPcE2JIetAR6qkbYVgjUmMoRL5IK2Bn9DvVkaNwTIGXiHW2Gc9vM\n" +
            "WiwvHXxnsjTC2VNecx0NNckA4g0m1ssNC7uJ5dE66IZkVES6pZxRahcsTEJMHZ2G\n" +
            "IuwRJ0IqTckTIbl/BluoAzxwo3/AkUKVeq6zG6vty1VJQZYdzRD/Mes/vLMM+j7u\n" +
            "pjR8CmbCve1lCtmHs8IEIHNS41tZaRe+0rSKVUF7vFGqTT67vNy+/eep+tgmlIoY\n" +
            "eg+8cKQ43FqmNK6PDX1wwE+YnOH48hA//d186mHhdmEYSoIbkCT0TCS8Df8WcayE\n" +
            "CcaPMqJ3AgMBAAECggEAMvZxOZCCLPw6IFeYC9JB4rRqOyf6ts9/bfVXb4pCU5j8\n" +
            "VKUo/qAM7njF7CL9QFb05iOUbFrXTSIXp2t3srTmpXSWuxn3Jm3+97s3FusSTWXr\n" +
            "yG/zxcy88J+4U9DDyoM+lNOAsla32eU5r4uZjqFsRMpSfFhSl4Zi2xkCuRFFufdq\n" +
            "mBqBFpXUhaZJnSZ+JLkoJcFn7X1cDGxHU5H+MFjk/N3W4LsAemtpk0fkQ5WVCRze\n" +
            "ePWdc954V0jTyalHruxysM8bn4lQt2l0KNjzYzPlStwJJppwkNB0Qhg+8R+Z3PrN\n" +
            "MrVOcBenOPgswINQcLAlSGCHB9PO4K3XqRhLNFTmoQKBgQD3onUnP86qek/1ktEy\n" +
            "0N+cNYnG8P7vz0y0iCfl+e1CHawazQlrikEx1eGSy/QVfJsRb9LDVfbg7G+u3xuF\n" +
            "vjWybY8M2amA7rvRKwtOOX/YLColG78RTbh2eGQSFjzUJ6k6F9nEhOzKN/pJAcBx\n" +
            "rhcBqdzXr/I3tjydFH7vvSyB7wKBgQDUD3Z2rDQFM4o6VRzY+56rvZaxxJx4BqyR\n" +
            "2vqtsHKnlrej40nkpN3TJjQxUcsx4i76fNio80byYK2pI1nzulOJlDodB0UqzieY\n" +
            "WEyiMGggP950A3gcjzUPvCI86/YpGnLITANACKfJpqoCYTDGT1bEoXrJuWSYm7kI\n" +
            "aJfyg6jP+QKBgEDa32vkHjAv0Hk0dm2knqu+qdLckygjVqeBcVGd3zGNq+FpCaqM\n" +
            "jv6KUYWsvMRT2hj6/GwEO6A0zBs0e3TPmCie4PQwnWATJkVlzJQ0IvNuvaryVVwm\n" +
            "ysTcSvkhsdSufnekf3gkiA9+EaLFPq2HNqdM4XXrsrg8litdjtEi7mEpAoGBAMKW\n" +
            "YcTppV50ec2nFi6BTYsYEDSVTmLQ9P9t6gMBW2Qu59oKEV8ubaJ4dwIepQ7kFumh\n" +
            "MApwGdUFI7WFi+AbRNaE3oKOjkiGHU0tETzuoE2fbdgU6t2CB1Wl8+DlQN9ctqDX\n" +
            "J1BV4Okn9u7yEhiW6u7JomuCpC1pD3QIzg0qBPi5AoGBANEUCk/5eKTpoWVy7HmB\n" +
            "0nr2Y7fSBCADPwWQiXqzr7nAPu0ZDFz/3mfocCdLHYgnhGwwF5xzmxq3h0rQ38Gx\n" +
            "UdZypboyBLcIXEF9uyKZagqSoOff4fYO2/auD/t1TC0eDvuo2PXFfVqwKT2xgQPC\n" +
            "V3OYjhXkMR1aZXJNjCMq2pp8";
    // 对应APPID下的支付宝公钥。
    public static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzSF+IQuZcoglbe4U/x/R\n"  +
            "iczSLUD3BNiSHrQEeqpG2FYI1JjKES+SCtgZ/Q71ZGjcEyBl4h1thnPbzFosLx18\n"  +
            "Z7I0wtlTXnMdDTXJAOINJtbLDQu7ieXROuiGZFREuqWcUWoXLExCTB2dhiLsESdC\n" +
            "Kk3JEyG5fwZbqAM8cKN/wJFClXqusxur7ctVSUGWHc0Q/zHrP7yzDPo+7qY0fApm\n"  +
            "wr3tZQrZh7PCBCBzUuNbWWkXvtK0ilVBe7xRqk0+u7zcvv3nqfrYJpSKGHoPvHCk\n" +
            "ONxapjSujw19cMBPmJzh+PIQP/3dfOph4XZhGEqCG5Ak9EwkvA3/FnGshAnGjzKi\n" +
            "dwIDAQAB";
    // 签名方式
    public static String signType = "RSA2";
    public  static  String gatewayHost = "openapi.alipay.com";
    public static String returnUrl="http://120.26.184.107:8080/aliPay/return";
    public static String notifyUrl="http://120.26.184.107:8080/aliPay/notify";

    // 字符编码格式
    public static String charset = "utf-8";
    public static String protocol = "https";
    // 支付宝网关
    public static String gatewayUrl="https://openapi.alipaydev.com/gateway.do";
}
