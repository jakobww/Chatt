package com.example.jakobwilbrandt.chatt.serverFactory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.jakobwilbrandt.chatt.BaseActivity;
import com.example.jakobwilbrandt.chatt.MainActivity;
import com.example.jakobwilbrandt.chatt.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
/**
 * Created by Jakob Wilbrandt.
 * This class is part of the serverfactory, which decouples the implementation of the realtime database from the rest of the code.
 * By doing so, it's possible to implement another realtime database more easily.
 */
public class FirebaseLoginAcitivity extends BaseActivity {

    private FirebaseAuth auth;
    private SignInButton googleLoginBtn;
    private GoogleSignInClient googleSignInClient;
    private int RC_SIGN_IN = 0;
    private String TAG = "login_activity_firebase";

    CallbackManager callbackManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        //Checking if the user is logged in. If so, close the login activity and redirect to MainActivity.
        if(user != null) {
            Intent alreadyLoggedInIntent = new Intent(FirebaseLoginAcitivity.this,MainActivity.class);
            startActivity(alreadyLoggedInIntent);
            finish();
        }

        //If user is not logged in, set the views, and carry on logging in.
        setContentView(R.layout.activity_firebase_login);
        googleLoginBtn = findViewById(R.id.google_login_button);
        googleLoginBtn.setSize(SignInButton.SIZE_STANDARD);



        //Setting onclickListener to sign in using google.
        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.google_login_button:
                        signIn();
                        break;
                    default:
                        break;
                }
            }
        });

        //Configuring the sign in options.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);


        //Listen for callback, when the result of the login comes in, and define what happens.
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //Facebook sign in sucess, try and autheticate with firebase
                        firebaseAuthWithFacebook(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        //If sign in failed - show the user a dialog
                        failDialog();

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        //If sign in failed - show the user a dialog
                        Log.d(TAG,"Facebook login failed.");
                        failDialog();
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //At the login screen the normal menu item for logging out should not be visible since you're never here if logged in.
        MenuItem item = menu.findItem(R.id.action_loginout);
        item.setVisible(false);
        return true;

    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    //When successfully signed in using either google or facebook, try signing into firebase using the given account.
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //Sign in success, try and authenticate with firebase.
            firebaseAuthWithGoogle(account);
            Toast.makeText(FirebaseLoginAcitivity.this,getString(R.string.sign_in_sucess),Toast.LENGTH_LONG).show();
        } catch (ApiException e) {

            failDialog();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth = FirebaseAuth.getInstance();
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "signInWithCredential:success");
                            Intent forwardIntent = new Intent(FirebaseLoginAcitivity.this, MainActivity.class);
                            startActivity(forwardIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(FirebaseLoginAcitivity.this, getString(R.string.sign_in_fail),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void firebaseAuthWithFacebook(AccessToken token) {
        Log.d(TAG, "firebaseAuthWithFacebook:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success.
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(FirebaseLoginAcitivity.this, getString(R.string.sign_in_sucess),Toast.LENGTH_SHORT).show();
                            Intent forwardIntent = new Intent(FirebaseLoginAcitivity.this, MainActivity.class);
                            startActivity(forwardIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(FirebaseLoginAcitivity.this, getString(R.string.sign_in_fail),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void failDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(com.example.jakobwilbrandt.chatt.R.string.sing_in_fail_msg);
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);

        // set dialog message
        alertDialogBuilder
                .setMessage(com.example.jakobwilbrandt.chatt.R.string.fail_message)
                .setCancelable(false)
                .setPositiveButton(com.example.jakobwilbrandt.chatt.R.string.confirm,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                    }
                }).setNegativeButton("",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                }
                }).setNegativeButton("",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();



        // show it
        alertDialog.show();

    }


}
