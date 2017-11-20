package g.y.v.vyg.Activities;

import com.android.volley.toolbox.StringRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import g.y.v.vyg.Constants;
import g.y.v.vyg.R;
import g.y.v.vyg.Utils.MySingleTon;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnSignup;
    EditText etUsername, etPass,etRepass, etMail, etName,etSurname;
    TextView tvlogin,tvsignup;
    LoginButton loginButton;
    CallbackManager callbackManager;
    String firstname="",lastname="",email="",id="";
    String sDate;
    String username,name,surname,maail,pas,repas;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        btnLogin=(Button)findViewById(R.id.buttonLogin);
        btnSignup=(Button)findViewById(R.id.buttonSignUp);
        etMail=(EditText)findViewById(R.id.editTextMail);
        etName=(EditText)findViewById(R.id.editTextName);
        etSurname=(EditText)findViewById(R.id.editTextSurname);
        etPass=(EditText)findViewById(R.id.editTextPass);
        etRepass=(EditText)findViewById(R.id.editTextRepass);
        etUsername=(EditText)findViewById(R.id.editUsername);
        tvlogin=(TextView)findViewById(R.id.textViewLogin);
        tvsignup=(TextView)findViewById(R.id.textViewSignUp);

        Calendar c = Calendar.getInstance();
        sDate = c.get(Calendar.YEAR) + "-"
                + (c.get(Calendar.MONTH)+1)
                + "-" + c.get(Calendar.DAY_OF_MONTH);

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setVisibility(View.VISIBLE);
                etSurname.setVisibility(View.VISIBLE);
                etMail.setVisibility(View.VISIBLE);
                etRepass.setVisibility(View.VISIBLE);
                btnSignup.setVisibility(View.VISIBLE);
                tvlogin.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
                tvsignup.setVisibility(View.GONE);
                loginButton.setVisibility(View.GONE);
            }
        });
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setVisibility(View.GONE);
                etSurname.setVisibility(View.GONE);
                etMail.setVisibility(View.GONE);
                etRepass.setVisibility(View.GONE);
                btnSignup.setVisibility(View.GONE);
                tvlogin.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                tvsignup.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email","public_profile");

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains("username")) {
            if(!(sharedpreferences.getString("username", "").equals("")  )){
                Intent intent=new Intent(LoginActivity.this,FilteringActivity.class);
                startActivity(intent);

            }
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String user_id=loginResult.getAccessToken().getUserId();

                GraphRequest graphRequest=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            firstname=object.getString("first_name");
                            lastname=object.getString("last_name");
                            email=object.getString("email");
                            id=object.getString("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        insert(firstname,lastname,email,sDate,id,firstname+lastname);
                    }
                });

                Bundle parameters=new Bundle();
                parameters.putString("fields","first_name, last_name, email, id");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pas=etPass.getText().toString();
                username=etUsername.getText().toString();
                if ( pas.equals(" ") || username.equals("")){

                    Toast.makeText(LoginActivity.this, "Please Fill All Blanks", Toast.LENGTH_LONG).show();

                }
                else {
                    login(username,pas);
                }
               // Intent intent=new Intent(LoginActivity.this,FilteringActivity.class);
               // startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=etName.getText().toString();
                surname=etSurname.getText().toString();
                maail=etMail.getText().toString();
                pas=etPass.getText().toString();
                repas=etRepass.getText().toString();
                username=etUsername.getText().toString();

                if (name.equals("") || surname.equals("") || maail.equals(" ") || pas.equals(" ") || repas.equals(" ") || username.equals("")){

                    Toast.makeText(LoginActivity.this, "Please Fill All Blanks", Toast.LENGTH_LONG).show();

                }
                else if(!pas.equals(repas)){

                    Toast.makeText(LoginActivity.this, "Passwords doesn't match", Toast.LENGTH_LONG).show();

                }
                else {
                    insert(name,surname,maail,sDate,pas,username);
                }
            }
        });
    }

    public void login(final String un, final String ps){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.loginUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equals("not")){
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("username",un);
                            editor.commit();
                            Intent intent=new Intent(LoginActivity.this,FilteringActivity.class);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No such user",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("salus", "a"+error.getMessage());
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("password",ps);
                params.put("username",un);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

     public void insert(final String firstn, final String lastn, final String mail, final String date, final String pass, final String usern){

         StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.insertUrl,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {

                         if (response.equals("ok")){

                             SharedPreferences.Editor editor = sharedpreferences.edit();
                             editor.putString("username",username);
                             editor.commit();
                             Intent intent=new Intent(LoginActivity.this,FilteringActivity.class);
                             startActivity(intent);
                         }
                         else{
                             Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                         }
                     }
                 },
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                         Log.d("salus", "a"+error.getMessage());
                     }
                 }
         ){

             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> params = new HashMap<>();
                 params.put("name",firstn);
                 params.put("surname",lastn);
                 params.put("email",mail);
                 params.put("date",date);
                 params.put("password",pass);
                 params.put("username",usern);

                 return params;
             }
         };
         stringRequest.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
         MySingleTon.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

     }

}
