1. 开日志 可以很好查看程序运行流程
2. 应该还有一种方式 可以帮助我们知道调用流程。

auth url:Http:localhost:8080/auth/realms/LcpRealm/protocol/openid-connect/auth

token url: http://127.0.0.1:8080/auth/realms/LcpRealm/protocol/openid-connect/token

Logout: /auth/realms/LcpRealm/protocol/openid-connect/logout

Account: http://127.0.0.1:8080/auth/realms/LcpRealm/account

register node: http://127.0.0.1:8080/auth/realms/LcpRealm/clients-managements/register-node

JwksUrl(getPublicKey): http://127.0.0.1:8080/auth/realms/LcpRealm/protocol/openid-connect/certs

```java
Public Key:

{

​            "kid": "qnY224RitGFuogvtE0Gd2_bj0egYJlQMpWYKnJWDrHM",

​            "kty": "RSA",

​            "alg": "RS256",

​            "use": "sig",

​            "n": "nAfZ4E-3rwaJ3BJdzNFJKTv54mO4x3yE9sewq4UC0QsK-n31MuP0pby0tmPerfYlKzcJKwHp6-Mo1wYCCM82e1FpZ-F1daOWCWMTxBIIRyvhVf5iBvWf4BG5gOVXO4GdCr7nFQ1tH3zDUwj9GBqtfpmEN7rnWkEQHJVrUpy3InUa8RdIDrL7nsBpyDrhbrIm90UkOFYVzCD4TUMLflOzUlYwvq5cYFhJNkqflwkWN2o5d5yXuIW7j5xfmpcYKSgjjtar5BD1p4yIiYFJxJph78G4nrM6gy_aoecqn5KI9T7FxnouA9TYVRxXGPDrQe1rImm9qcHu5jepk6WslVdWTQ",

​            "e": "AQAB",

​            "x5c": [

​                "MIICnzCCAYcCBgF9g2f24DANBgkqhkiG9w0BAQsFADATMREwDwYDVQQDDAhMY3BSZWFsbTAeFw0yMTEyMDQwMzA0MjhaFw0zMTEyMDQwMzA2MDhaMBMxETAPBgNVBAMMCExjcFJlYWxtMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnAfZ4E+3rwaJ3BJdzNFJKTv54mO4x3yE9sewq4UC0QsK+n31MuP0pby0tmPerfYlKzcJKwHp6+Mo1wYCCM82e1FpZ+F1daOWCWMTxBIIRyvhVf5iBvWf4BG5gOVXO4GdCr7nFQ1tH3zDUwj9GBqtfpmEN7rnWkEQHJVrUpy3InUa8RdIDrL7nsBpyDrhbrIm90UkOFYVzCD4TUMLflOzUlYwvq5cYFhJNkqflwkWN2o5d5yXuIW7j5xfmpcYKSgjjtar5BD1p4yIiYFJxJph78G4nrM6gy/aoecqn5KI9T7FxnouA9TYVRxXGPDrQe1rImm9qcHu5jepk6WslVdWTQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQAWbc00OSROsQneIlgemOdPePn0wS1mHuC+6jkY5V7ARWIIxtqzOE3nyVbojvqMxWoMonHHg1SsblmLazEUY3kQL1bGZPbToMDr9jGTYPOz8RZHY52F6ME4kuWOwfwSY7NUsWt1jnOygwGSVEVa/7ORvb7rmytnpTcSZLjqm7IRz2+Ou36q7MyuCIgPdjhDF16Ad/R4GrHON8+vfaYexbftEXtjakRxwfGKvfULCdyz9z7DssgBotz7SeXmYfAgKOcS4c1rBThXLJ1Y+8aOdBV2kgRo/jaP//EsWSFQESMFCH0m1Z1MFHT8Ke05viHSUblJkKPox8NIwHqllFxwM3H+"

​            ],

​            "x5t": "qtvv-RStuvS5848TPpLwKog8FCA",

​            "x5t#S256": "GPeTjV3KAqEF1d8TkFGF4h3Vwvm_uY9Frae-i7n9p3g"

​        }
```



1.BearerTokenRequestAuthenticator  

2.JWKPublicKeyLocator 拿realeam公钥 校验了。

3.RequestAuthenticator 拿到了User 'd348abfa-fc96-45e0-9c47-73433417193d' invoking

RequestAuthenticator.  Bearer AUTHENTICATED