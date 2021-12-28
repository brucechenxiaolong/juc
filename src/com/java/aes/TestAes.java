package com.java.aes;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class TestAes {
    private static final String enType_AES = "AES";
    private static final String pathStr = "D://aes.key";
    private static final String testStr = "AES tips you shoutld try again";
    private static final String jsonStr = "";


    public static void main(String[] args) {

        SecretKey genSecretKey = testGenerateKey();
        String string = genSecretKey.toString();

		byte[] testEncryptBytes = testEncryptBytes(jsonStr, genSecretKey);
        System.out.println(testEncryptBytes);
//		String testDecode = testDecrptBytes(testEncryptBytes, genSecretKey);
//		System.out.println(testDecode);  //直接操作数组,加密解密正常


//        String testEncrypt = testEncrypt(testStr, genSecretKey);
//        String testDecode2 = testDecrpt(testEncrypt, genSecretKey);
//        System.out.println(testDecode2);


        String jsonStr = "bEhViXSnbub7hEJ/IsvzfmkQCUTxUct0aCJX1fivIRmNRAOVNMeIIdrNLOspHJXsCWPKxzEebYf2" +
                "LiBixYa/CqsRslV8TDD83YmzwbDELi/TIqHtPsmqgoe5iM9kL51T9FQ7b6NcCGLyy4a/8r/VFkzh" +
                "gMYxUx0akMRzZODT+WCJIvLeQVJgjlg++5UFMPQBg+jud1YEBj1ql+Ttf4RoWbPycFrKOAHvnefN" +
                "fk1+yZp6fzAqS+y+EFVjSsngTVOvAFxmsLhrW2H9ajCyqAvlphcCX821Oe0lRWdjU60m+i14OTQE" +
                "lJTExw9LiobpG5itstPEP0kUmtTakb/Qgkomck4dk/AJu7+vr9jublcte7G6u9AgMdyM5It0AqK/" +
                "rDmSN3EEaITiFGEouJaOHG9JRenA0osOxnG9A620Teh/HoyIVBd0t6BbDA/huYGOg+5mTRC1ptEd" +
                "ssPLTGKo2IcRJOkhtOz+oDwOIz9tCeL9cV01vEYzfhLWeG9NSLOFQTXzvYu0uLrIQlFDv5RptsJI" +
                "EptzlK0WFioA/jBNHDOXzLn47WzKQKvTbFThE38rm/WhDuzeURynOROL6e2WHGn+hAqn4GIFtraL" +
                "SpvWDBy26pFzWuEPciQMJhKRbKGLPZ1xdjobRO2+8rcUaxNhUA7Vh5D5gv66i2R8YgpYMskWLNMp" +
                "qheyqCD0nH7IUGrLu3JFi33HPc8c1dUz+3yp6JVsZ8voLXgvsW8natLoTx3YwuJnZvfb0wZamt56" +
                "wJZM+sLFFtsk8NCIqa7nGjj3SPR5qd2gopuJdhgCQX6Xsyjj6h41LJ4DrPc7SKpkYOf2t4gaf1re" +
                "KaGa3ofniD5jM7pSSiU91QJZfVqEgN+ueFqNngcT4t+rVmBaW+DdI8JiyWgtog2sGjmYC/l7JAns" +
                "/Jv30bGv1woTkUMlRjXiRBj8CQgnC6wpFwpRQORvhZ8eJ/6wHTo8ElkUcJ2gZpZbsmRI82EuOTI1" +
                "eG/KxxylEB1oLRc8NmZ28/+ysYM3hdliqKAa9YqFWOuaSqJc0mdoyGneKQN26AgQf6BjtiFArTDt" +
                "PMQxarOUJuSq/+Igzl4y94gZB1xJ1gpklnmeDstZAjlWrypoKnGCEUpj1+QSRJqBWk4ay0LJQiw1" +
                "YMzJFLjTJQWErekqx7aESbM9WThZJ6pO0lLvpTHA9HTkXTzlaKgtu6n5TKSVRFbEl21per4HI/ON" +
                "1X72e9IBMCE2C4n4eyYC7NoScNXWD3u9eqmwZnU6I32pfXqjuWufEL5ejWHV909mY0SaBH7FV0zT" +
                "va6TpV5+GQpty82LvAbXnFFCg0lXZoGYJeTXhvoNytieg8+10YI0GApYSt59HNBY+c/dBFl0BlwC" +
                "i7Rh2eIlJmDjDQ/1JmQBSbPyKShXPJYgaPRUMgeeeey8Pz8ImsBTUcPlIGq8m7sc9MM+uz2MsF5j" +
                "g+IbJ647bx8X8fleg2UZt9paEVIt+NMr8QoA6drCP76VRO3/VgC6kmZRQnnCRLUXb2ppe8ypNZ/i" +
                "Q4Dz0e5ZjYuS11ElFLICdGqbMWWy2Fa14AZkiiSrkd0CDLTXWCBV8SBEVmybpv/mAH5IHLsutTIi" +
                "WE+be4RPOeYkMEslWbB8pH0+gSF77BBPhXO+vUcJnydxMX3VSklKvnlY1V2gANEj6NRiT86ZHSlC" +
                "LQyh6EtC9+yb8Ob0Yo811zVq3MV+SdY9QYvT8VHtltvkxcFY8jtU6rHXwB/x0VYXyzlBmGAmoFGp" +
                "Flzy9AKCiyjD22OhLKMNXgxvwCihYZX/00qM3bRLsphWnXzGmpjX0x8upi/yxy9VckF2mB4kpVXk" +
                "3M33FcxeRZNbg0NIWmXpM/2opLoZid95Qz0NX7jf/0GOKOzmeIzf2p6rQgPICGIst/HvP1nYd1z2" +
                "IoRIh6q5Udyo2BAUjTZgcOqT8WJiJTPKgid//cs+JD9uf8Mj3ndOJnofeZaW+5fnIQ3AUm4lbhLg" +
                "ulQBPvXsSWvc++HflYdsqtCC9KgepntFa3r7jyivmGyll0oGHNi0+esKblUN6GmidHgC5TVvJ1GR" +
                "zcUNH9V7VkU6+gapdIIRlXDVo8S7ZSqDFUT+gfpXOvEM+uwnFpoGjkew/i+fL7l60aOUpKffv1bc" +
                "acZ8epPVi9resbI42amcEHh0PNI1GkNsBEXqiajkYyaXm4hoj/qUDgn7z1y8hh0lKXOvAphOyD2m" +
                "/883HkUM+ITxuQOWjbNJGMONKW+YuYNPSr+gdjIJ7tryqyt9dIWf/4eG69YNxpQ4RSERddLob99R" +
                "JxVrfFoM8N30o+8e5aPSDHX1umtS0mgU8kvnJ4cgqwV2NZ4bg6SYKAPxPSOBGLYOAA7QurLH5s+E" +
                "B0Xa7t69BQK0ujaGH2iruZhR4IB3Q31kHCCdxmcIWYqaSjZYdBuRwSBQ6thL3Gy9ZqT+dt/AcvO1" +
                "3zN9tBuUp3D0kNXkjBt0gUWjftg9efiPzKsS0BJxG2TxfRcyrowinjmDsSL73lQypvQi2dTPMu/i" +
                "SSppoVVpbFOJ5nCDdxHY+hCOJ1woL98q4oZZRt5dZ23MFSkQo6VtFu2fUHrufmRK4wiMnFw+SlKR" +
                "zTrcyZrrx6f9o/njWv+gjcsK6uLbqHhkxim1YRb1fhNeqS7kRpVh1xprWkgMUGjH9t6vWYKdyAt3" +
                "85BWQmhGNCf8feENvgza1UE3RmVwrtdaGcR2C+N7ur3kx5Rf0PjAhVzlxVieTL6Nzbx5PQSVyZMH" +
                "KQvck6dGsyuEe9/gGNoPJyZ2/oiHe3KXbhqsmOCxmRFOmEdIKp85JC2vzwQ4qUYBXOzvxqwCYug/" +
                "OVRPIQVSPwCUuEQR+w6UPpN5eeYdHd2rKbDAY7JNsq0TwlYXSCRykRXLhlxk1mwAPyGWv0HdafCO" +
                "Tkw2c9Ue9uZ+0Lj0hml2rrjm+wJAml2n6dffCTA9aCDi/bz2iEQm8ZLbLVeOsrMmzS1+A5GpaH5C" +
                "9RWnQM8Xw+Bd8/gsZn4Kpo5Ol9pnSPPdcmdf2X9qHnbH1aprHaFHj8lBcWSOUXwzqVz4NLzrv+9O" +
                "e8xj1Y7o1bgX7LpYzHgY1bWioBehqNGu4Rs/T0zLSRw707c0HvOINKY7mJL0Rf3sNwQvaokxZl6T" +
                "66Uy2E4DuJu1+X46h6Kgsr5AhsRr1DZ13vgugebwzdRgVvcuR9JkIMrpE40Q5EBX0vg/U5eDSM0m" +
                "zbJwvrmAwEQp5Hv+WCrCnbeCmE/6o6sEWxSVoERTP2s1PJvSE7DDfdo15F3skmuTLNvAjMAdOAvF" +
                "0KNC3OImioEMAo6jXn1ve91u/JnD9/NwLNR13eHQYBDq8atM3uruXtFX2+LcadkaFVBgO83wIpxZ" +
                "2/COajzqz1Xudgfy4IL1NLqD8gnAbZiQX36rmkcsUt0ko/Ihj5qco96ci2OA76NJHGenZJg7OlPj" +
                "RiCOHAznWR3AmUmknf0SGo5oTjXN5hcFaI188sZmNpvrHzLqHIUomDf3crC4a9FKU0UI0NbN7NQn" +
                "dgvOViqlxGiN9Z/aP6o9wv4doQrDCAa9vqfB13IlPsMBXffl34atjATiifr+6QOZaIGgar4jjYrt" +
                "YTpql0GcmY/WPFtTIzNv0z8fbJVt2BY+QWnaENa5TMM9xysot+D5iv+wHX/spPNlpTsLLZr6Qk7n" +
                "cwoLZKsuV+fP2WRG3XVrSZhRhKEeOXAfZrsWZQcnw4Nh3JJyxtAQMXOAgjP5c8DctqcI7gv+dXhx" +
                "ia8UZ+6e1UBbBfAywH4zwwYhVNmnuBm3MaG0L8khgqmw1PNJiZX0u+C/6YcDal8tGQ44OhKQxPFI" +
                "qbAM5+BbH2It3cskH1tl6KGsWuNarzsnnN2zwDMbHthpeZIy+ymxzp1MKg5BxvL4XM2YGfzYjmgf" +
                "GevyO0ET2wuiIMV0rgMmGZMjmKzApm2L5fwLQMAIMYAUpnJACsfEGA9QAnv0/aOVBglTVNcIGpkm" +
                "dd18iAOIzNU047fM0bhR75M1PZ2GzkZPN4yjJ+ACdsmT1lSrjUgEpO+tDzxTsRjba16x58NB04E+" +
                "QFm5Ewv6b0AU3Me8DHMu0eDpuCSFNb4NCbt/tOSP2e8wpykHVHAb+UDjQ14XvRgxEnXCfanI+GkV" +
                "auK76OgXZwFTi+4XBKrCMewgsOB1iqjOgmB8p+pMm3ZBJTU607X08076uKiqPsnD5lZLqn3vV4dB" +
                "5JBmNySNxRn5vdnkYhgGRT6d5STJI9iLewn7QoaQcG4iPn1tAoJI5Rvh5TvUsybI0O8JT5nKVICc" +
                "jVYIrzsyiA4bSRRIki8vsH216hZ1n2aKXkWKWuj3YM5desatTH8oMIswjGAidAT615KlVMFV02Jg" +
                "7z5DUniPjbBvPqG8FR/mdpEGHKDhuSVm+E+Xka6ku+0pGld01ZO/bGurHiZKiCRJYR3GHBQ0dLA4" +
                "ZrckA75+M+C+5GOetIN1jVff4wY18a4vtIQG/N8HPlWBrzbN9vSO6Y333tDoeVYUm0fMqlfWfr1N" +
                "8ra0t8vdl38Bu/7QFbmM9kSrkPAvMrpUhciT/U0jjLEIeKfNwejNfS1V2gwfuN7JGwmTrpBLhow0" +
                "AB0Me0nQqhBMvw5sIuSmtQGrI/5SwYDhrSxpBR80KwBR9rl3BQnhiX7mvtKWg9DPbXpxRVc+DGTV" +
                "1F04uLGsRulV+huuXQL0Fi3tqdgYHjoxzV9RzELXHb5GecAIjfOwZDoGLfVAG3QAmcjXlusAmbGP" +
                "DbIz/ifhV7xqN8bU/SS3oXQ/WrqlANMxzHV2kvGi/BgLs5K8Ix3PggFMGaA+/x+cB2ZQGmCBE8dG" +
                "YgTFjwrL0WlVRO5fJ4Ok3+N0VVbPXA2AGteI1mI2dsruXs89/UL8lS5Ot/+nQmtkwFUpaO0uTZjb" +
                "uQQHvurNQKCCOpmOb3juANff7g1KyRl92DaFc1hHD8kToJkAUWSZEVXRLsHaGkD6t9WdbjAOQfo1" +
                "fCN8QfjUS5E3XeW8t+TJntXCcHlm7RLJm7GjR/3EamgNdBN0ojV2OvUT4VE3VJW1hIkTs59zxphu" +
                "yaq8H94h4QQ0W6nFBbtCt52EdhoB1OKXm1Sx4mjgnZkcPCVP0FofknAFQdzTJf2BpPMAwm0WlPIW" +
                "7tqFztFstPTRoV34jrR3WgOypdJeq4sDk9Mk5q3iPz1sQamfDEH6MkBXRv91OCTcjg5dEAuAiHPE" +
                "PzC7MHNDvQnklPIhVMSXblv+FKaaWfE67Jt9Ehjpqc96y91YJk3Mub0cev2jorYGOMbT+smKXkGh" +
                "PUrj5ItC+Aabax8BslN/BaMyll6OW6tkuV8x8TJmeIfDfEJ0RS84qlLS8lEQAm9KpUawbToPa6XK" +
                "OQywM5rMv/5ggd3WumhqVE9fXuCXLaevQrR1RBMhT4cAQ4/TnI2dNBsHODBdJAYYqe/XFsu2Gdho" +
                "DTj9lm8QTiUB6wUYQsIfnUrEILDdJJaUylsHWz/u3A6dPrgrnRSM/m0PKXJn3ix51CamEyUyTu6C" +
                "2YR47kyebpYIR5K12TJ5GnxXT8/ennIvpBAEzdc0f01eUHsQzUKLa460SefALhJKZyK/wDzrSQLa" +
                "aP+afrY+MVNc+H1gY+k4rrWR0NJSldL1xuIU4N5Q8wsMoUqH/o/9JK1EoOXsubfOsKbd9/USyXna" +
                "KD6CwH3s8IzHVaVRWGJ7f4uTA5Z5poBQ2Mpla9NFvSc+8BLOyWfYy6AzYd0JDfzlAtV+QFU5kx3v" +
                "LXzhFW2tiPYovMJbHGnmjhTj99/AftLJU2iDj6DAgUS3QntX0Ka9OcvdCLXezRW7qhoCVAkOePkp" +
                "PrG5wiTOc8IfSg9ZQHEtAsV40x9NIpZvYO2avJtbjByhguSBlvmFIEluIbw6CZ8v79inRcOC88Ai" +
                "yp3IZ5SbzhFy99KeF+9g/g00ji+is0lYbJO4L7YKHJ3L8EypOyHactvHueEaD87prIEFa9FR69vB" +
                "gxv8nJ/kcxNcjT1B/oKrU7DCZDMYkcoH30Z1a70UZYrCgGk63gQRK9ZuXK6rWWrl6m3EHOxjttCu" +
                "YhDojWvmDuIYrt1CsJFOIFSsUwv/+s3BnCVtlH/BTue2wV2zQnHja2ABdmo91DHiuCPw9mIkFxzE" +
                "+n0+FQ2lLvjmTdkE4zKxEBpwe0ooYbTAenAZTRK6+ZXqDPBqIGkV5lyfRGY/ZNvKslvOIO/dRtXG" +
                "yUGlSgXDEFmV47opif9yPLSQQyoWP5TZRktDVnq26x893yJA9RJicF1Lr+vd+qA2cw/ClaQEEZsF" +
                "+F+Y5ES+cOOPud3SRGgTE8/uaT8j0uIRwcxh9T9b4lK/7apMBltdfsG4oMjXF7/z7bfjFoQ79KPz" +
                "4+FcNQ23/2JPKObCk3eM18a05mpg6t9m3LD7UVCJSFTz+Chq15Btyvu71teD0s1llOj8yCBb3bSY" +
                "do9JyIhepB41hzEmiKxaIjeIC4wJqvXHP5mOSIsRqmvMdNDDrwtOb50czN+Y1M/ZGI8bjff3jtbW" +
                "aKNxwABEVo5v4I+D5oZViEdRS04y3KWU990rddzosB0emmLGU0aK1GMVFjlcp0uoB8yKckFAv3lG" +
                "OHGNeYylLDfFj1ookgSjwj9648RIz4XY7lLIW5uEkCosvaKXMJLFhky/b2kv52ioEgJuh6tWZhY1" +
                "gtaSfFIaMB5JH3zQQn5vw6wb+PTtGQ6SF+5lX1ROBNWrVY4tMBnDpfjsYOZHaOW7y7+8HgxDU5lt" +
                "EdtHfxXncVMlokLEWXt72g3d+kJD6eVnXKnBIKxLcN+sjCGl8zq4c1Ul66pJkvW3qgbxT5BdGwO5" +
                "sVdeU2r0iK3gkH/OwDOX+qjHYMVdUJ8/mzVBtE2qhokfSN5aBTWr5egQT2NfaqVam02HGfiSf714" +
                "j2ExU7aVftk9mWKPKUaficVejzBBaNYvJBuv/uAGJBn7r3wML/H81Ksg0hwQteaEPPKEJxiMWlE9" +
                "nzoZGCKeHIr3jrhZqxKH2REFtdbmchNgzmPaHkNpYoa1LDApsZAn9QqFHNm//opJjYgqsKqztAB7" +
                "xcyp/202oCZYaTxyS+FqKBD6wJfJBcykmYU+XoNotyFLcFYoT8bUIhEhJoYUBTtltyYT9nSK75ji" +
                "fEfkw0HZQjH7h8zcHCxpDV0mAtXO3jHYQOFvqQwItEY5HelreWByx7v133YrQ+b38VOcsE1FzmC9" +
                "eYaeJmH5IyNOm16Y5htOLvVcDGCk7ZjBuuWl6v41J7usgg53BWRGqyt/BRdlaKdq9wPVBeMUYkIR" +
                "Gs2/CSPKgxqwfLlGdyC8xqIhxaB2uqIYpsTbp0K0HrnZzVenrJRcQ4ZRvd0Y8iJfmA+uOnSL0+2z" +
                "Jd/FI/yWi0zcBk24fQ92vuajQbJrG8AUyNyWDCXiHh88vGxP+uDjk2pWk2P2xur5wxd4/1lJkPOA" +
                "0kpGtBXp87hUs7N87PHIC0+VSzv2zp6sRqDrO/9WsKLgpKZ+XDerwmPwgJ0tV8fcf4XvI+EF0qoc" +
                "+DMu4mu+l8gF60YB3zbfkxnhVZ2xFtHSs7zPRZQLUcUQOxXqgMOJ+byZyCjLMVWEP1IJS0YbvUnO" +
                "MfK5nX6dgfojxLC/ejav2w+OoxE1bc9KRiKSdnWKRNy/9fJKGHEsWk3kV9bGrPHJ3uTs0FjlernA" +
                "1Ur4Gso+cUp9oslt/FLMbRrYhSZ9boWic6WEbteXy4r4qQ8KciLgss0zr/sNSIO3VHFP3YhZz6cH" +
                "bNZP8fi+6X/x6ikp39p+5PcAnzucDKQIRwA4X4Qlu67HCdnvTaHGev4MeGdtgR9iCAjSla/+HuxE" +
                "apdK9mRrnPwCUg57b/WiSUtbjO+cdk4PACFfnVt8R14DwoDt4UUekbOpRDMqVesrRn7uPLvV0kqI" +
                "SOIvZ+KtA+X5N0hrd1jEFZh2CftnLVbgEwicLEH5obu9ioLJc23+7vlA9oPfMw7mOsjVwccOFGpn" +
                "MhjAqosaG3joarfwUFtlR8eWPGB2si2odqZ4SwZuSYXveKWOfdkVsvKY8JHcU6VAbsQQ/CeV6wx+" +
                "+SItofj2tJZr0svk75xgt+10ZQ0arpfqYGnPp73f+LWU1M787oRgdHn4TZagP5tYx+hTa3A5NjsS" +
                "c9G+GVUxtVSIcDZKMmnEA1vB2XSLx6TgZmJClSURpVcPFYXqyvSay3tlpfKGIqR/XRyYqaMzzC8L" +
                "cqznA25PwzjOCbHKXKqS6/77bIfzI/DAo90t2D5A8OWfWxJ9wFjybHe6JRNxYdwz4hpsFwOL9OU4" +
                "tIYvuAPuWnR+YpKn+mx89y1e/C0o1ZNyUiukXE6IEfnB5JeEEFuuieDi3bOeueloeIXfTBR8JGok" +
                "+geg3wzyq2i1eQGCAfUK4HSztlLpNowZTMCKC708etIoTQ7aadHe/aN6JUHGxiqZvWVt70CgwrBE" +
                "3GqAIu2fIUhglEGzcBuDffWwVSspFGJMFTkz5YIJDHjDdffFrgZLTcu4Zmffq7Y13LU0tfCQnT6E" +
                "mZIJOnDjDiqiT11NSs4y9o/jEyI54KMFs6Tj0oqstQ4pmsmfJAIusrTaRuowJIqZGwMImVVvpZJp" +
                "vhP+GuCSvcXXGvHPqj3t9fgsublRSARXR6OWfdaL9m63mNdWB/L0z0iH8SfGr35Q6CnBwSQ8c4dh" +
                "GtIFafBfUjDww4+69MwoCE75kVrkMrZKDKhP/fe4aD8osOqtQsvZ/5h2R/9AQSty/TYGqnTiLppR" +
                "1hHfW3q+Ba9ByFSm2754XfQGm49wsnOflcFcAvI44k/nbFgGyWeCacvYhsK+A2v7mcebFQQ2iORS" +
                "LyZTZF2Zgn/rVI8j6jSMGM6QXzlqU8AIRqRqih4g3ZLClQHLeVzbz77eX8AifjPXPj5dWl31eJy3" +
                "e58yDWfV1IxOE/y2CQH2N0eF95SpZUlSQRSjNj9+TDBiRPH3KBod26wpWqrw+gFvBH3A6VWP0jCi" +
                "73NaUgMUpAh/53H+5NwjX6+WM0dzrsncLrgP4m5f3hyomsbixJafDmE3nSNaj/E0WhtE7jEKEcY4" +
                "cFnr/xONdr/KFM0y61xqqwnvtoA+tjgWaOANLCSD2w8Gs1P8Srj9iEtMPZAjW5w2UG7i65q339xz" +
                "z+61wPj+z5LIL6oo9uwijm0YgDybp+qjqlTW5gLRWbrVBQ8gd8YR4h/IYVQysg7IEPm4p6CyBbbK" +
                "2C3NdSrHviHFJxyjJsw9XJ8H3ww+gh0hSlTzNmE33qXPE3CEnyM7JU4vYp02ghTMP7xCZwKbcHRk" +
                "tz59HlgLZkEAlH5Tty2gGz5A/T8S3TlNFKZNqvAN/hyX6ZCPDW+s9D5vuS2uoJ65NU22p1wu4SIj" +
                "Se1dSqpvquO3b7sXIcmpwqYiAV6TQs0dOTC8l9SmiARZobyRpOwDFVPgZhGGCatNWGBZ4Ck/UkhY" +
                "ZcQssVCN+1z/tjgrCTQgNh0i9DchavSp1wmY83zEbbHNKdPptsgwRPLHTnusBZJg1c1Qb7PlEQbF" +
                "BACiX4k/5a10oUNxcCbtikdh+HyLgTiEi7Lhs77I2aE/dnwuDbIp2WKcD32ZIsMixfAnpuayEv5M" +
                "haAfgc9LMNFypUcIg1MpOV0CZ7G5e1dkM5c0TmL0jb6rX1iH0cO9QjH7NeTeU3Juy6W/z+VFPIZH" +
                "mQGdPl11TdV/puWJHq5fndEenaEnsnk3GTW8FLQAuSdeh0nPmq5UIeMWPF8bywyC4oWg86tZ8RqH" +
                "gwS1w+wNqE/XMxRSxENtUv2jC9equEkif/otFxJ8R5Ah/P42FZ3sZOx9zc1Vof6KUwHTCj0Pv0jO" +
                "fYehIysi88KX1nW54o2eumGIBtOcVsRW9doLAx1GZVs+o7L5Y3lq488O2wfhTgfnojS0jhRkKtJ2" +
                "lhO5eseDzjxV36gjzBWe8uJSHJZm3+hZORqMiGqNB83hp2JD6fqg1/mVN2pNqHbhd3SJml2ZBEIa" +
                "+E0LUSJTpDXUiueE/ppe0Tgb6j9uUZOneS5TWYFjQK/KTEVtq9la0189e8a1Zq0wROsW6031pHmL" +
                "8JQT9mcmSygHkYqaXnqHy/uVj6ZEtoE5oNRjqfQ8bPUeLOaofhgyzZEU3KZ47iHIz+GJy4rbzXRO" +
                "hWE4ta0CoIE28jBNISFXStnHgt5mRVgL0YNYp1Gkr++gFtQGdLFul+ZVKAmtz+ml3qFHQwrDSXBz" +
                "75rBOz9O59bMLC1KmO98TwsglHgNmJjMmZsVS/7ppGRFzeKkC6Br1A4jMpihbLFkl0ixuBe4SA+M" +
                "bfSqye2pAfg1t72veNx7+pX15t3XkJKKAzdHrx4yWtTxVLAQ24kWdpEUq1mfCzUrs60kbDYo+0QA" +
                "o3pWcuS9cm/4PaCuSqvt+KYsRQa7HqGSSfGT5VcJNyEDn7R0Kq01ZNWDyk+zl1ucuSIp3KUezLfM" +
                "tgvNWYqsYuLnvkOOYjBgxVaj5X71vtFlcuHmmH/EaN2/mZyrUwIfeo7sWMe5+p/B2qp+PRiUwSlE" +
                "hR+Y+3rWhT5CnTf2y1ZTfHEdx5+Oh1M1ctPlv4lYpgyKJbwo+OrhoC/iVqt2gqNVULmcpkWME7rI" +
                "2fmvnwsLcfFHhP99VyT6UHrmOnwpjMUbb2EP31fNKOsSFZK2GqOQX+Whnci9ZQ+7STs/e9hNJPsO" +
                "3d61I9HO9boCvFmJPVg5PbJycM8cujQe1ZFp0TTsOuT3g6weZO/L3bTMpx8+nbqNiE33N5WpFK0H" +
                "VCdVtfpzQ7n8iv5glqOCpSQb5b9+sebUTKA07LjinwFbbigqtwBlW58HwT5dbZg6grbas+tgygnW" +
                "WSHrrKQJDopGz34szqh9gemgXfkLtT3MTNqCk/mRzmCSe9jJNqiUKSvlMldGKSkGdHMh5SIyDUpT" +
                "urPqujlVkv30AbuMFTAxeIcDoylMYEULYyVwqo/wgN0Q+1VnCii5k/kOZD/Jjl83lxbpzjPnF2ju" +
                "euIiMjEmUYpxsALagRPOuUhAVrL3dyxbrp2c4AyTIlGY3FJcoNO06tECTxFTSCeijxEVyyg3esaw" +
                "25ALkRuI68msFMS3nNC+zkTzuukYZ7S0B1nvWbaWnSw4f1zC77V9RK6z7Di8sZPYp3ccVHKeNklN" +
                "GFwVxNc6ffwCPcapWbatrx3k+zb6XTaBQg0r7h/GRJs+0ghP7bB1ed+n6SshBxAUZdYwIJGKOdZp" +
                "ieCVwvu9QS+GRibnUzupSHQWZ5K5Cu/7S4OFVy/O0xBviCxaiTf25td2pDxcPp4YP7BdDM4cBWtc" +
                "oCzROKKC03VxPaAjsPSrVHwY0aXlsmCJOHAgdUI6FI9FD/8TT8cMdnJ1NnW1wfTiyF3mD9Rn0E8S" +
                "VWqruutBXZbz5fm7f9K338P0ZoUqrf4G5QCIWLGa4/r9KxUZhp7eQJlbcID4BPfAZjnJhyfCb++d" +
                "AiLD7Z2H7kYQRPOcJWEM+taCE3FrIvyesULjsTlEfm7NQJlNYJGwAZgf7JJyEcKRpBkgAUhitXPJ" +
                "y7uCXwEATBd5k3MM3Y4uHKrqNUM0JHd0X01cHWbMuIOmz74wGtd1ETyKMlnMSjeY4zgO8EpYlbk7" +
                "XnyoCNq7FrGu0KHpVga54y4JTSauuo1g1eKeRevT+5li7mmzeqY8sYPNBg+2hNU8oPseRmSNJw2K" +
                "H0aX5GJ/72rP+5nMM5VPh4VtVTQeLAH0iiy9XLpfka4BqIdcUKyKb0h/aPYSk7EfpfJKq5+/ZaTo" +
                "wAToCmlAXZ9TVxupyyUF4qcvlKflphKH/qBUritkSmiAHCzwnM50kKGLDM8X7KpYc8Bnt20SRslA" +
                "wkfm6FqEKz9yb/vUhSJTmOUpFnhza3FqUAqquZ13COiTXgoOKJTrOkxr0JWJzFKtXJ5/TrTwUTk6" +
                "XuIhwL07RvAWNCUluiVyXUU3RhgaLqQW4fZtFIKSic+qsdZp7aL3wfzDmfiI6y/h81KBWzpGS7k8" +
                "G+QXg1fY9aYb5YP205XH20LkhJHg6brwMbfeYLeliKfZV4SCYL3kyS6XDkE6JnOGBaRK/p43z4sE" +
                "1AMZlC3ySpe7TWhrTN1cOkQmCGr1MeAYF9ybmwxvkxyV2FfZ3cGXnACJhKWNMKFpXXHD2m7ZbX5q" +
                "Jf2KmZmvsjlqAjm5iaAygTa44/i+3aYnZoySA102c5cf0vons/gBQbmz2cKnGFejRt+UxTzYx7YW" +
                "qzG2HnNbr2lJkC/GSh0lC2AP4L1NCb0mnlAW3/zQ09N9cPeFDJ+Ug0yzka78gaAHTl1wCxWBGAsp" +
                "LCIv8G8r4DSNjM+zUk4pkMNBGfWwTc7k5JHyd1nOhv0RLIR2bpH+P1GC5ne1LXi19E/rTg0RvAFD" +
                "Kdwv7QyszR/3bCDt1nJ71kEA6TbWlWBToehaooqJ8tEdVIWSaJoSajEfweCg7zgL00Uv0k9x8EUI" +
                "yUO/rKiXJfp34WMniBgg13diXBXqzfdkGI4yRhsr9yXOUvmw//uQ4yZlv5uLpeaWziyspEqad6tH" +
                "cQRilVfzbcbKuAIlsVRwbC2Ci+GiutRRHRKF6YDMByypnDKBuM0douNiec3CCHjecUvQ4w/lKip8" +
                "7kpQxx1EyR0t5grYEAWRhuCvILJmkzdTDI1KXKVVkA96DqjM1CZ9Wj/pD9iwZxoOqCqj2ZESdY3G" +
                "JEtp48bNuoXqQr5pqHBmfd83zH+595uUUsojC66ji8EVwrls1JICs7gVyIroqGzwn5MfLZlntvfl" +
                "eijoGthzyse/VV6M6/nnlXbrr6gPe2pHlD6V/41Loa6py/i+Wrf09RqUafR5WTVArFsnZPpR0hA6" +
                "cQxrguZd2WlGByRh61Mds9Rx9xfoo6cycmeN8Sqs8odSaUsjYE+A7DOERLK8TM3WH5TI+3rDwV8N" +
                "mfDUBeImP4C0jPjNvcBsZIxB24dx6PnJeCqC3HQ48Iv/jGuQxO3zqBu4GurWSPqpjFqLLeSi9jTM" +
                "EX0UzzZ2ZOlhX3T/7tAz7qKQOSthruAZfkpIwI92JcHEXnfORWTo2dKcKE16tXUF1M7WldcfDamV" +
                "QUHFBaX1jZPunFUPhBTsxf4Tj60UagGWCAe2XDAnyzU1j1wscNo0XG2g7BdigNa9LXEI0j0rxGR8" +
                "MGniQyZw83Yt2dngPBh/BaXaD2r+XzK5ZUkFCzQUY3wAjGEcWC2iKHIuFa1SIOo3phEAktmtQsLc" +
                "9qRTlY0JPR55w87XzrnRMTGvic3IYOF2ibxnMEcQNNrODfZTX+brva5jF8ER0gvhMVj+RC2JRt4x" +
                "R36PYt/X1ShayyLSugl916ZdJXOzlzYRb0nztL5nEBzo8fhB+LM7BiTwXg==";

        try {

            //解密：方式一
//            Aesutils aesutils = new Aesutils();
//            String xx = aesutils.decrypt(jsonStr, "LUCULENT19991101", "LUCULENT19991101");
//            System.out.println(xx);

            //解密：方式二
            String xx2 = Aesutils.decrypt(jsonStr);
            System.out.println(xx2);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 生成密钥
     * 通过传递SecureRandom对象进行初始化
     * 不指定种子或密钥
     * @return
     */
    public static SecretKey testGenerateKey(){
        SecretKey genSecretKey = null;
        try {
            KeyGenerator kGenerator = KeyGenerator.getInstance(enType_AES);
            SecureRandom sRandom = new SecureRandom();
            kGenerator.init(sRandom);//不使用种子,每次生成的都不同
            genSecretKey = kGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("genSecretKey=" + genSecretKey);
        return genSecretKey;
    }


    /**
     * 加密
     * @param str
     * @param genSecretKey
     * @return
     */
    public static byte[] testEncryptBytes(String str,SecretKey genSecretKey){
        byte encrypt [] = null;
        try {
            Cipher cipher = Cipher.getInstance(enType_AES);
            cipher.init(Cipher.ENCRYPT_MODE, genSecretKey);//加密模式,密钥
            //cipher.update(str.getBytes());
            encrypt = cipher.doFinal(str.getBytes());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return encrypt;
    }

    public static String testEncrypt(String str,SecretKey genSecretKey){
        byte[] testEncryptBytes = testEncryptBytes(str, genSecretKey);
        System.out.println(Arrays.toString(testEncryptBytes));
        System.out.println("加密后的数组长度"+testEncryptBytes.length);
        return (new String(testEncryptBytes));
    }




    /**
     * 解密
     * @param bytes
     * @param genSecretKey
     * @return
     */
    public static String testDecrptBytes(byte[] bytes, SecretKey genSecretKey){
        String decoderStr = null;
        try {
            Cipher cipher = Cipher.getInstance(enType_AES);
            cipher.init(Cipher.DECRYPT_MODE, genSecretKey);//解密模式
            //cipher.update(str.getBytes());
            byte[] doFinal = cipher.doFinal(bytes);
            decoderStr = new String(doFinal);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return decoderStr;
    }

    public static String testDecrpt(String str,SecretKey genSecretKey){
        System.out.println(Arrays.toString(str.getBytes()));
        System.out.println("数组的大小"+str.getBytes().length);
        return testDecrptBytes(str.getBytes(), genSecretKey);
    }

}
