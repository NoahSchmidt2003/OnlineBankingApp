package de.noah.onlinebankingapp;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;


public class Conn implements Serializable {
    Socket socket = null;
    String line = null;
    String cryptMsg = "";
    PrintStream output_stream;
    BufferedReader input_stream;
    String privateKeyUser;
    String publicKeyUser;
    String publicKeyServer;
    String signature;
    String message;
    Map<String, Object> KeyMap;
    String sessionKey = "";
    AES256 aes256;
    DataInputStream dIn;




    public void connect(){
        try {
            socket = new Socket("192.168.178.62", 60000);
            output_stream = new PrintStream(socket.getOutputStream());
            input_stream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            KeyMap = RSA.initKey();
            publicKeyUser = RSA.getPublicKey(KeyMap);
            privateKeyUser = RSA.getPrivateKey(KeyMap);

            publicKeyServer = input_stream.readLine();
            output_stream.println(publicKeyUser);
            output_stream.flush();

            cryptMsg = input_stream.readLine();
            byte[] dec = RSA.decryptByPrivateKey(RSA.decryptBASE64(cryptMsg), privateKeyUser);
            System.out.println(sessionKey);
            System.out.println("Input is: D6B9F87651E6423628D977556E2CB");
            System.out.println("Encrypted is:" + cryptMsg);
            sessionKey = RSA.encryptBASE64(dec);
            System.out.println("Decrypted" + sessionKey);
            //sessionKey = Arrays.toString(dec);
            sessionKey = new String(dec);
            signature = input_stream.readLine();
            //sessionKey = "D6B9F87651E6423628D977556E2CA";
            boolean isCorrect = RSA.verify(dec, publicKeyServer, signature);
            System.out.println(isCorrect);
            System.out.println(sessionKey);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handlerOUT(String message){
        try {
            System.out.println( "the session key  " + sessionKey );
            aes256 = new AES256(message, sessionKey);
            cryptMsg = aes256.encrypt();
            output_stream.println(cryptMsg);
            output_stream.flush();
            System.out.println("sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String handlerIN(){
        String msgCrypt;
        String msg = "";
        try {
            msgCrypt = input_stream.readLine();
            aes256 = new AES256(msgCrypt, sessionKey);
            msg = aes256.decrypt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean login(String email, String password){
        boolean check_login = false;
        handlerOUT("login");
        handlerOUT("email");
        handlerOUT(email);
        handlerOUT(password);

        message = handlerIN();
        if(message.equals("true")){
            check_login = true;
            System.out.println("Login success");
        }
        return check_login;
    }

    public double update(){
        double balance;
        handlerOUT("update");
        String balance_str = handlerIN();
        balance = Double.parseDouble(balance_str);

        return balance;
    }

    public String SignUp(String email, String password) {
        String signUPSuccess = "";
        try {
            handlerOUT("signup");
            handlerOUT("email");
            handlerOUT(email);
            handlerOUT(password);
            message = handlerIN();
            if(message.equals("0")){
                signUPSuccess = "user";
            }else if(message.equals("1")){
                signUPSuccess = "success";
            }
    }catch (Exception e){
            signUPSuccess = "conn";
        }
        System.out.println(signUPSuccess);
        return signUPSuccess;
    }

}


