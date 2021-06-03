package com.serverconnection;

import android.app.Activity;
import android.os.AsyncTask;

import com.dungeonmaster.ApplicationVariables;
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
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class Server {

//  Адаптер беспроводной локальной сети Беспроводная сеть: IPv4-адрес
    public static final String url =
    "http://37.139.35.47:8080";
    // Флаг - зашёл ли пользователь
    private static boolean isUserLogged = false;
    // Флаг - доступен ли сервер
    private static boolean isAvailable = false;

    // последний использованный запрос
    private static String lastUrl = null;
    // захеширванный заголовок "логин:пароль" для организации доступа к бэку
    private static String loginHeader;

    // файл с данными пользователя
    private final static String loginFileName = "loginData.lgn";

    // объект который гонит объект в json представление, чтобы потом отправить на бэк
    // Если хотим отправить что-то на бэк, сначала сообщение нужно преобразовать в json формат через
    // методы этого объекта
    private static Gson gson;

    // Маппа всех запросов.
    // Логика такая, после совершения запроса, он уходит в отдельный поток, чтобы не грузить
    // поток с интерфейсом приложения. Получить результат запрос можно вызвав у Server функцию
    // getQueryResult. За раз можно совершить только один запрос на один адресс
    // (т.е если кинуть запрос на регистрацию с одними данными, а затем ещё раз кинуть с другими,
    // не проверив чо там пришло в пером - данные по первому запросу затрутся)
    private static HashMap<String, AsyncTask<Querry, Void, ResponseEntity<String>>> queries = new HashMap<>();;
/*
*   Соединение с бэком. Создаётся при старте приложения.
*   Сначала просто пытаемся подцепится к бэку,
*   Если он не активен - выбрасываеться ошибка NoConnection
*   если он активен - смотрим есть ли данные по логину в
*   приложении.
*   Если есть - пытаемся залогинится - при неудаче выбрасываем @UserUnauthorized,
*   Если нет, то выкидываем ошибку @NoLoginInfo
*
* */
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

        passRequest(HttpMethod.GET, URLs.LOGIN, null);
        response = getQueryResult(URLs.LOGIN);

        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            logout(activity);
            throw new UserUnauthorized();
        }

        String name = response.getBody();
        ((ApplicationVariables) activity.getApplicationContext()).setUsername(name);

        isUserLogged = true;
    }

    /**
     * Функция читает файл с данными пользователя
     * @param activity - экран в котором была вызвана функция
     * @return - возвращает объект  - данные пользователя
     * @IOExcepton - если не был найден файл/если не смогли записать новый пустой файл
     */
    public static UserData readUserData(Activity activity) throws IOException {

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
     * Приводит @body к json формату и передаёт запрос на сервер
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
        if (body == null) {
            body = "";
        }
        RestTemplate restTemplate = new RestTemplateWithTimeOut(5000);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (loginHeader != null) {
            headers.add("Authorization", "Basic " + loginHeader);
        }
        System.out.println("My project encoding is : "+ Charset.defaultCharset());
        headers.add("Charset", "UTF-8");
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

        String loginCredentials = user.getUsername() + ":" + user.getPassword();
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

    public static void saveUserData(UserData userDataIn, Activity activity) {
        UserData userData;
        try {
            userData = readUserData(activity);
        } catch (IOException e) {
            userData = new UserData();
        }
        userData.encodedAuth = userDataIn.encodedAuth;
        userData.savedGameProgressIds = userDataIn.savedGameProgressIds;

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
    }

    public static void login(User user, Activity activity) {
        String loginCredentials = user.getUsername() + ":" + user.getPassword();
        loginHeader = Base64.getEncoder().encodeToString(loginCredentials.getBytes());
        UserData userData;
        try {
            userData = readUserData(activity);
        } catch (IOException e) {
            userData = new UserData();
        }
        userData.encodedAuth = loginHeader;
        userData.username = user.getUsername();
        saveUserData(userData, activity);

        passRequest(HttpMethod.POST, URLs.LOGIN, user);
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

    /*
    * возвращает последний url по которому был совершён запрос
    * */
    public static String getLastUrl() {
        return lastUrl;
    }
/*Возвращает true/false "залогинен ли пользователь?"*/
    public static boolean isUserLogged() {
        return isUserLogged;
    }
/*Проверка доступа к серверу/бэку*/
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