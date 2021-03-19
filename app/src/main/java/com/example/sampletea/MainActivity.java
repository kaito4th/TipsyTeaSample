package com.example.sampletea;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sampletea.Model.Login;
import com.example.sampletea.Model.User;
import com.example.sampletea.Service.Service;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button buts,buts2;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://tipsytea3-dev-ed.my.salesforce.com")
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    Service service = retrofit.create(Service.class);
    private static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buts = findViewById(R.id.button);
        buts2 = findViewById(R.id.button2);

        buts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login login = new Login("admin@tipsytea1.com", "Tipsytea2021cgqi72muaI3Coeb0RySqgyN1C");
                Call<User> call = service.login(login);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getBaseContext(),response.body().getToken(), Toast.LENGTH_SHORT).show();
                            token = response.body().getToken();
                            Log.e("View", String.valueOf(call) + "login: " + login + "response: " + response);
                        }else{
                            Toast.makeText(getBaseContext(),"Login not Correct", Toast.LENGTH_SHORT).show();
                            Log.e("View", String.valueOf(call) + "login: " + login + "response: " + response);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getBaseContext(),"This is an Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buts2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> call = service.getSecret(token);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try {
                                Toast.makeText(getBaseContext(),response.body().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(getBaseContext(),"Token is not Correct", Toast.LENGTH_SHORT).show();
                            Log.e("View", token + "login: " + "response: " + response);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getBaseContext(),"This is an Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}