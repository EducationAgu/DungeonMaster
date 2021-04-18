package com.serverconnection;

import android.app.Activity;
import android.os.AsyncTask;

import com.error.NoConnection;
import com.error.NoLoginInfo;
import com.error.UserUnauthorized;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.serverconnection.model.Querry;
import com.serverconnection.model.User;
import com.serverconnection.model.UserData;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class Server {

//  Адаптер беспроводной локальной сети Беспроводная сеть: IPv4-адрес
  public static final String url =
  "http://192.168.0.103:8080"
//  "http://10.255.9.19:8080"
//  "http://192.168.95.109:8080"
    ;
    private static boolean isUserLogged = false;
    private static boolean isAvailable = false;

    private static String lastUrl = null;
    private static String loginHeader;

    private final static String loginFileName = "loginData.lgn";

    private static Gson gson;

    private static HashMap<String, AsyncTask<Querry, Void, ResponseEntity<String>>> queries = new HashMap<>();;

    public Server(Activity activity) throws NoConnection, IOException, UserUnauthorized, NoLoginInfo {
        isAvailable = false;
        isUserLogged = false;

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        UserData userData = readUserData(activity);

        loginHeader = userData.encodedAuth;

        ResponseEntity<String> response;

        passRequest(HttpMethod.POST, URLs.HELLO, null);
        response = getQueryResult(URLs.HELLO);

        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.SERVICE_UNAVAILABLE){
            throw new NoConnection(response.getStatusCode() + "::" + response.getBody());
        }
        isAvailable = true;

        if (loginHeader == null) {
            throw new NoLoginInfo();
        }

        passRequest(HttpMethod.POST, URLs.LOGIN, null);
        response = getQueryResult(URLs.LOGIN);

        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new UserUnauthorized();
        }

        isUserLogged = true;
    }

    /**
     * Функция читает файл с данными пользователя
     * @param activity - экран в котором была вызвана функция
     * @return - возвращает объект  - данные пользователя
     * @IOExcepton - если не был найден файл/если не смогли записать новый пустой файл
     */
    private static UserData readUserData(Activity activity) throws IOException {

        FileInputStream fis;
        try {
            fis = activity.openFileInput(loginFileName);
        } catch (FileNotFoundException e) {
            UserData user = new UserData();
            try {
                FileOutputStream outputStream = activity.openFileOutput(loginFileName, MODE_PRIVATE);
                String JSONed = gson.toJson(user);
                outputStream.write(JSONed.getBytes());
            } catch (IOException innerEx) {
                throw new IOException("Ошибка при попытке создать пустой конфиг пользователя");
            }
            return user;
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
             throw  new IOException("Ошибка при попытке считать файл " +
                    "с конфигом пользователя.\n "+ e.getMessage());
        }
        UserData userData = gson.fromJson(sb.toString(), UserData.class);
        return userData == null ? new UserData() : userData;
    }

    /**
     * Передаёт запрос на сервер
     * @param method тип запроса Post|Get|Delete|Patch
     * @param urlEnd адрес на который отправляется запрос. e.g. "/home", "/User/register"
     * @param body объект который необходимо передать на сервер. Может быть пустым. e.g UserData для регистрации
     */
    public static void passRequest(HttpMethod method, String urlEnd, Object body) {
        String JSONBody = gson.toJson(body);
        passRequest(method, urlEnd, JSONBody);
    }


    /**
     * Передаёт запрос на сервер
     * @param method тип запроса Post|Get|Delete|Patch
     * @param urlEnd адрес на который отправляется запрос. e.g. "/home", "/User/register"
     * @param body объект-строка который необходимо передать на сервер.
     */
    public static void passRequest(HttpMethod method, String urlEnd, String body) throws NoConnection  {
        RestTemplate restTemplate = new RestTemplateWithTimeOut(5000);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (loginHeader != null) {
            headers.add("Authorization", "Basic " + loginHeader);
        }
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);

        AsyncTask<Querry, Void, ResponseEntity<String>> response;
        Querry querry = new Querry(url + urlEnd, method, entity, restTemplate);
        ExecuteExchange exchange = new ExecuteExchange();
        try {
            response = exchange.execute(querry);
        } catch (ResourceAccessException e) {
            throw new NoConnection(e.getMessage());
        }
        lastUrl = urlEnd;
        queries.put(urlEnd, response);
    }

    /**
     * Функция для регистрации.
     * @param user - Данные о пользователе
     * @param activity - экран в котором была вызвана
     * @throws IOException если не смогли записать в файл
     */
    public static void register(User user, Activity activity) throws IOException {
        String body = gson.toJson(user);
        loginHeader = null;
        passRequest(HttpMethod.POST, URLs.REGISTRATION, body);

        String loginCredentials = user.getLogin() + ":" + user.getPassword();
        loginHeader = Base64.getEncoder().encodeToString(loginCredentials.getBytes());
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
        isUserLogged = true;
    }

    /**
     * Функция очищает все данные о пользователе
     *  @param activity активность в которой была вызвана функция
     */
    public static void logout(Activity activity) {
        try{
            FileOutputStream outputStream = activity.openFileOutput(loginFileName, MODE_PRIVATE);
            outputStream.write("".getBytes());
        } catch (IOException e) {
        }
        isUserLogged = false;
    }

    public static void login(User user, Activity activity) {
        String loginCredentials = user.getLogin() + ":" + user.getPassword();
        loginHeader = Base64.getEncoder().encodeToString(loginCredentials.getBytes());
        UserData userData;
        try {
            userData = readUserData(activity);
        } catch (IOException e) {
            userData = new UserData();
        }
        userData.encodedAuth = loginHeader;

        String JSONed = gson.toJson(userData);
        FileOutputStream outputStream;
        try {
            outputStream = activity.openFileOutput(loginFileName, MODE_PRIVATE);
            outputStream.write(JSONed.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        passRequest(HttpMethod.POST, URLs.LOGIN, null);

        new Thread(() -> {
            try {
                ResponseEntity<String> result = queries.get(URLs.LOGIN).get();
                switch (result.getStatusCode()) {
                    case SERVICE_UNAVAILABLE:
                        isAvailable = false;
                        isUserLogged = false;
                        break;
                    case UNAUTHORIZED:
                        isUserLogged = false;
                        break;
                    case OK:
                        isAvailable = true;
                        isUserLogged = true;
                        break;
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

//        ResponseEntity<String> result = getQueryResult(URLs.LOGIN);
//        if (result.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
//            isAvailable = false;
//        }
//
//        isUserLogged = true;
    }

    /**
     * Получаем результат последнего вызова по определённому  url
     * допустим вызывалась функция passRequest c urlEnd = "/home"
     * Чтобы получить результат нужно вызвать эту функцию с таким же
     * @param urlEnd - концовка адреса, по которому вызывалась функция
     */
    public static ResponseEntity<String> getQueryResult(String urlEnd) {
        AsyncTask<Querry, Void, ResponseEntity<String>> result = queries.get(urlEnd);
        queries.remove(urlEnd);
        try {
            return result.get();
        } catch (ExecutionException | InterruptedException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String getLastUrl() {
        return lastUrl;
    }

    public static boolean isUserLogged() {
        return isUserLogged;
    }

    public static boolean isAvailable() {
        return isAvailable;
    }

    static class ExecuteExchange extends AsyncTask<Querry, Void, ResponseEntity<String>> {

        @Override
        protected ResponseEntity<String> doInBackground(Querry... querries) {
            ResponseEntity<String> response = querries[0].execute();
            return response;
        }

    }

}