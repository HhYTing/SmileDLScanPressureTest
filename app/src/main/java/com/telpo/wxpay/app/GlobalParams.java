package com.telpo.wxpay.app;

public class GlobalParams {

    public static String SP_NAME = "sp_syh";
    public static String APP_NAME = "WXPOSDemo";
    public static int DATABASE_VERSION = 1;
    public static String APPID = "wxa86153a91420728b";// 微信appid

    // 商户号
    public static String MCH_ID = "1259951301";// 商户号

    // API密钥，在商户平台设置
    public static String API_KEY = "telpo111111111111111111111111111";

//    ===========shengyihuo@telpo.net生意火收银机测试用=============
    // // 支付宝公钥
//    public static String ALIPAY_PUBLIC_KEY =
//        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
//    // // 商户私钥
//    public static String ALIPAY_PRIVATE_KEY =
//            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL0FvRiFpSDXt/KirD3+uWbQz2nqGalejwmONer3agITmXp+7rC+2Xs2uLT4A1vemWvYOMaGRhh+7T9y/GcME/nPgJcAWTEAsAyybRH9wK4Ona93nYDZrb1MgI5sDIx9fHl9HrR40qUmuifotOvaUfYqQJEnrihJb0d4q1W6JYPfAgMBAAECgYALeS+zL1mIls95kNo3oSCL1xzf5u/L0guInvMy4fWzTpFAWDgQzmTRD89CXNOHXgmd/C/mDPxwPDQWZ9pePuNvaaOOhQawNIzXiNf6FqZ0X/Uaw+EovyHlg2SmVlmBvL0JXAwwJMxFQonwRp7vQqPda/8v+4/5m3m0MsE1Q3T5wQJBAOjKOSzxCo/ttZdcRHvrpRD8qPLZkX5mkpPRlGOQFq1NJRBrzSO0gf60GOY97OQ+EwDFkMphumlElby0wRMG+K8CQQDP3mJr+gaR5fyk8Xd9a9CXFRLPraDTcyS3xaWJVTLejOTkz/q0NMlEIjF3o8s/f1q9lYQ95YQtb5Lxizyz2JPRAkBmCS2ii0zr5W+xFppWDuXdWkHboI3rb55T+IsoUOH/uE7EsdvLCLXqJJq9YOtS5/qAXhim/MsulQdlHX17j5S1AkB9pmB6An18Lbhk/+0JrGU/Hibr0Hl5t41HZLqmZBDGxAYMZceWrJ3IiJ68Qg82x8XS+ZEhVR5r1JjPVyoPUGgxAkEAveOoQWCY5pto7kFJA2SWdSSo8CWM/ShNNU4se/Z1Wlf7DGGS9BOMF3kOS5Hc348cQq0ALTKMWeNlFPbNl35vug==";
//    // 支付宝APPID
//    public static String ALIPAY_APP_ID = "2015083100245155";// 生意火收银机测试用
//    public static String RSAtype = "RSA";

    //==========telpopos@telpo.com账号用=2088611308670178
    public static String ALIPAY_APP_ID = "2019072565978534";// telpopos@telpo.com账号用
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArZZAhBYnm2g3CAL0hjD4Wl9b/I07Bef/YTAL4KqKsLmSEZnv9zX/0K8A/i5feuevDr8YlYkFgDFwjgIY7A2kzB+/QFSjos0RwjEi//H7w8QW2ga9KdPRkexICbconZybS1vDIWPMzquAmYVVXN+niO++mRyy8vzGO4hK8pW6MVzbOxCuBg8qYFDifa9u9IjBTNk3k0A68Prwj0Vj/xDk/GkUrTNsfRRdmKSSange2RNa8xxZCyiYIPZraahEe7cwYoBiK/jotChZUiY1xLPCOalw67cXvI8gO2a7R/IMiJDPBwxm5LwKZ1zfEuM5HFsVQ6f/XVDd1kL8hK0+plRUtQIDAQAB";
    public static String ALIPAY_PRIVATE_KEY = "MIIEogIBAAKCAQEArZZAhBYnm2g3CAL0hjD4Wl9b/I07Bef/YTAL4KqKsLmSEZnv9zX/0K8A/i5feuevDr8YlYkFgDFwjgIY7A2kzB+/QFSjos0RwjEi//H7w8QW2ga9KdPRkexICbconZybS1vDIWPMzquAmYVVXN+niO++mRyy8vzGO4hK8pW6MVzbOxCuBg8qYFDifa9u9IjBTNk3k0A68Prwj0Vj/xDk/GkUrTNsfRRdmKSSange2RNa8xxZCyiYIPZraahEe7cwYoBiK/jotChZUiY1xLPCOalw67cXvI8gO2a7R/IMiJDPBwxm5LwKZ1zfEuM5HFsVQ6f/XVDd1kL8hK0+plRUtQIDAQABAoIBAFa9OcfG+kuhq74JG/7iAXfHJsPwHoo4/1elFopRLbRZRjAdZ9dz2DyC9lANBBD6LHOF8dbm/SStj7ymN6Xcu3h0dLDEmnADYNhUjVSvqkqMdY7Ex0lbWuE73FNefqEjXEYvuDW7FOBAuqX8Bke+So0Wn3sK6DCbNsMQu8K0JrH7DYetrpQvIh3DOgmLEkE2cC9LRdqTXElvBNbMUj4K3OuX/NYT/5LLumVhZBi7KPlGAEPLf1KqKL0iw+gSJhUL+1EZ1nQw0eNIXtAbOZheSnriWTXFAWCl6Tra97/lIQ0f+zFsGGJProkPYGyO6bl8K/tZS4agNQVNTTyaSWe0UYECgYEA25OFXmGNsCk/CTiu0cjPns3qIQ2v+df5npddRejdLjm7RcPtELBiKJPiS7v+pDyWS49ujIvYEtQQtdJhluMQN0XJLfn69+UrNwicI8wEnyuzj/IliYlXnLgupiNKr8W2PIajj8mGwwyo73cD2eufDsqkFyMhawdc0t9RNfzptp0CgYEAymHB+6unK4D6tg31y+J0TPIaKvUPPzcfaiAMEZ+G9defQ8dnZail+Lke4jKHmWhu+hzDMZrJ2XN0udpFCQiMVDjJR+kD/ZjIJdEjLn30MCWn7JsmXyiPRXf97tjxxn7d4mIVhnnL7TMh7dO5bLIMcBp6lQVXuc75h5g673l/rvkCgYAiHE+6ssL1G+M8Bn85/c0rKWf2zI6K7J9NoMwPoTZDCRrrop1AW3ZUQW0FQOY1E2hxwo107gCaJDGYXV9ltk59l6zCJLR1MDe/a+n9saMCKyLQ+NgV/q0Knp44lwDyP2+pBW2BV9hEksHnuYByqSTjgl1tbrqxSsqDw8d08J5KDQKBgAoMbItIbJZ2YvxjGQ80gT3Iz4/OdyFWZVLpoG/HkfXm7Nt6rSGZDBSbbHMiuyTc0JXOk8qEuvz3BJatxbu7FuyfnS7a3P3cdiOajLqkOlmhpQLUeUVESEjUumD/mJGYfv/ciuEQbxWp8kdSueQxuvhd+DQp/b8WDcGzx0lYqQRRAoGAPwiIqW4slvOc6OqjVemGGffdy9WnPj604EKa9GyTiIs/h3u5KgTcCI9R0OEuq+JbFkakIpNJ+dw7d79WeMRPIUjrI4AEtKRwptv0RnUJTC+KmVVbCRGi9RlojSUyb6dVS+76iRFzvLluLQGyGu3wxxCp9W3zDmIBovHJFjuP4nM=";
    public static String RSAtype = "RSA2";

    //========生产对接专用==========
    public static String ALIPAY_product_APP_ID = "2018112162288088";// 生产对接专用
    //    public static String ALIPAY_product_PUBLIC_KEY = "";
    public static String ALIPAY_product_PRIVATE_KEY = "";
    public static String product_RSAtype = "RSA2";

    /**
     * 以下是赛威的支付资料
     */
//	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoLydlfPf96umD5/bwBGL+umRcdgp61wTCcdcXECQbM4boh7rbL55DzyY9eLHYt4OJ1TENVRUcZBUAAIf4xYYbxAOsxS6l+fu3T9BFn3f6KyNsXneJVKGCkiiedgNDVHRyPvkoJOO3L9/lp091a75Fqqk3OrmAjA9/mKXLKWF/ymMn+tOzseVxAfZ+C4yMRaPbl8iCbm689ZFNu/YHCkTrZotBrJ1wjGUkjDnWuRFPS92zaG8y8JDpZcoF8dVUZrchUQx+4/IBL+ioUZ9dkFIa/PzxlC9FnluqbE+leL6y8zXoHA3jEPQHt1CPGfI7bLcRgpdQU2ium2Bi50TY1Fh+QIDAQAB";
    // 商户私钥
//	public static String ALIPAY_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCo/r50TwHUgODhAPo4FGjODV5uK+Ewv3OKblM+TuR0t1DXCP2jAaR3yPBiCIt9yKQkDMuhFOrpZ/AbtJKaFXQ33hshgYRGMNXE9PXsSbvMJDneWyLBk4grfCpg0BgWh5kYWmkVMZdGHOEp0NP12HefG7DXgocKrqNSQBUrEQVxcvdfjy5crhu7fy4bwgRDCE9vrbT7KEKyMgDzgDZmlnohixTYGUWxrcbGQhJr/9r7RWT99p8LrGyrNeadOrkg5hFEpeFX8qlu7/hQOBX7JVcq11+J4wo6Y8PqRwlb4LMwYpzY/UgdqrkuCqBxeuufkOOLBkh4/HQwLXwBzFRYdCDTAgMBAAECggEAVwFYcq8ehRaZMJ59NJQlZThyzove02cuCC5Gw0SA0Cp0/1DMrLiywUz9natCjc7X2wuPZu5LB0IQ12K/O38iJ4U9OKHixPRtioqHdlB71F0uQfFv0nOvXpv9gXyBek/znRsnENdIR7uknQ+7oM4CwDLUeA/mkLRat4TwoQyRXdd5maK+1uJIOZa73S0JcO1tSIj9jJTrxi4S0KYclS9+k3gjvIjPLAcxtfN6RFFe69CV8k3VtDTyOFVxQR4Vu9Zk34S4ok0WzLUX4XI7oN9U7bI6bvOJsnlyG/CrcAguZPDlVeEDeMRlYsmAMfKCrfKwCsOFUymn2jZQLJKJOWeh6QKBgQDTtAKkDJqLJcytRvqpbQBQzX0TpybCKw6hc7QZMhp0P3eQ72Im503ZVeseuj6ZW0kKNEUsBmedu2MzezhevBYPleAUbncEPb+c8h49PrB9DG90v+Z0EPxn5udes/k9wjTSIycoBwXMR16j2vBmIpnzDCRi1RCz+lfUTONX3aeKXQKBgQDMWw29P6BeDB5lwryynNzUVczERzAAWUwjn4E78jWpOIbsOHAD4zRyeruSY9RWG+dex5IF5Ljpgz68tCLhbnhWrN8QjzZlZJH+Aq6MOdfMXtuqpylecuKAwz+P8Y5Z7vRRDwdsozDRMuLddkCOe6wH78p9ZPwzJG+U2nQs1VqE7wKBgCTCuUKrPZqz8ZqVHKmW19swE2v6Nmy49U1rvfYyEQ3mh2iPaMfAmnAGz1ECniQbCc/Py8MdSZjGFS+3Uc6BcoM6TzK9C8Wcw00Jbd7Mp/DO2/u15tp4qJuSKNL1BW6H7owetonozkTuj6x8NApIL9tYXGkc/k2CRwYCY+e6AYGxAoGAWJMm015eriTmkAjmLSzccpR12qVYYu5RmCcspsUHCT/dC0ozExxJwUz9PChVArW5hVVPL4rBs0UVzkjtzXRKaJcpYE4Sr8P1m3miGll/9a8v2zn1LmDxWApI5KU4pDZ0YyX2e9oH8MTznDYDKT7lg9Mlm1BIGAq06nn9XOllALUCgYEAyTN+e+mweCyxR3o9UScWHOHiYEgRLZkiaf+VjFGE41Sykk+nqCHv1MaShwAVMmEFhjfrKruBFweZVYnCVaNw0sPDfwY8gToqIhjlOR7foKfsGdsCouBJ3lORnnzJRYrnpkHeO1jtQpYgXIojiqlHnGepiUvcrkiLToEh6+bN/y0=";
    // 支付宝APPID
//	public static String ALIPAY_APP_ID = "2017090808616916";// 赛威的支付
    public static int PAY_WX = 1;
    public static int PAY_CASH = 2;
    public static int PAY_ALI = 3;

}
