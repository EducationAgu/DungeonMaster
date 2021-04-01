package com.serverconnection;

import android.app.Activity;

import com.error.NoConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.serverconnection.model.Note;
import com.serverconnection.model.User;
import com.serverconnection.model.UserData;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Base64;

import static android.content.Context.MODE_PRIVATE;

public class Server {

    // Адаптер беспроводной локальной сети Беспроводная сеть: IPv4-адрес.
    // public static final String url = "http://192.168.0.103:8080";
    public static final String url = "http://10.255.9.19:8080";
    private static String loginHeader = null;

    private final static String loginFileName = "loginData.lgn";

    private static Gson gson;

    public Server(Activity activity) throws NoConnection, IOException {
        //clearUsedData(activity);
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        UserData userData = readUserData(activity);

        loginHeader = userData.encodedAuth;
        if (loginHeader == null) {
            String response = passRequest(HttpMethod.POST, "/hello", null);
            if (!response.equals("true")){
                throw new NoConnection(response);
            }
            throw new IOException();
        }
        Note note = new Note("note name", "note content");
        String response = passRequest(HttpMethod.POST, "/note", note);
    }

    private static UserData readUserData(Activity activity) throws IOException {

        FileInputStream fis = null;
        try {
            fis = activity.openFileInput(loginFileName);
        } catch (FileNotFoundException e) {
            try {
                // Если файл вообще не был создан, создаём его и выкидываем ошибку "файл не был найден"
                FileOutputStream outputStream = activity.openFileOutput(loginFileName, MODE_PRIVATE);
                String JSONed = gson.toJson(new UserData());
                outputStream.write(JSONed.getBytes());
            } catch (IOException innerEx) {
                throw innerEx;
            }
            throw e;
        }
        InputStreamReader isr = new InputStreamReader(fis);

        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String text;
        try {
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
        } catch (IOException e) {
            throw e;
        }

        UserData userData = gson.fromJson(sb.toString(), UserData.class);
        if (userData == null) {
            throw new IOException("Пустой файл данных пользователя");
        }
        return userData;
    }

    public static String passRequest(HttpMethod method, String urlEnd, Object body) {
        String JSONbody = gson.toJson(body);
        return passRequest(method, urlEnd, JSONbody);
    }

    public static String passRequest(HttpMethod method, String urlEnd, String body)  {
        RestTemplate restTemplate = new RestTemplateWithTimeOut(1000);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (loginHeader != null) {
            headers.add("Authorization", "Basic " + loginHeader);
        }
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url + urlEnd, method, entity, String.class);
        } catch (ResourceAccessException e) {
            return e.getMessage();
        }

        return response.getBody();
    }

    public static void register(User user, Activity activity) throws IOException {
        String body = gson.toJson(user);
        passRequest(HttpMethod.POST, "/User/registration", body);

        String loginCredentials = user.getLogin() + ":" + user.getPassword();
        String loginHeader = Base64.getEncoder().encodeToString(loginCredentials.getBytes());
        UserData userData;
        try {
            userData = readUserData(activity);
        } catch (IOException e) {
            userData = new UserData();
        }
        userData.encodedAuth = loginHeader;
        String JSONed = gson.toJson(userData);

        FileOutputStream outputStream = activity.openFileOutput(loginFileName, MODE_PRIVATE);
        outputStream.write(JSONed.getBytes());
    }

    public static void clearUsedData(Activity activity) throws IOException {
        FileOutputStream outputStream = activity.openFileOutput(loginFileName, MODE_PRIVATE);
        outputStream.write("".getBytes());
    }

}