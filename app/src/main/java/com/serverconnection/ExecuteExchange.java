package com.serverconnection;

import android.os.AsyncTask;

import com.serverconnection.model.Querry;

import org.springframework.http.ResponseEntity;

public class ExecuteExchange extends AsyncTask<Querry, Void, ResponseEntity<String>> {

    @Override
    protected ResponseEntity<String> doInBackground(Querry... querries) {
        return querries[0].execute();
    }
}
