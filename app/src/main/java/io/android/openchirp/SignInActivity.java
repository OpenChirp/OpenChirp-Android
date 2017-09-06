package io.android.openchirp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.ExecutionException;

import io.android.openchirp.R;


/**
 * Activity to demonstrate basic retrieval of the Google user's ID, email address, and basic
 * profile.
 */
public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_GET_TOKEN = 9002;
    private static String ID_TOKEN = null;

    String default_web_client_ID = "8799600805-r708mvl77fe1f5fu71r6sbo366m6e7r5.apps.googleusercontent.com";

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;

    OpenChirpHelper openChirpHelper = new OpenChirpHelper();

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String getSignOutIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        preferences = getSharedPreferences("openchirp", Context.MODE_PRIVATE);
        editor = preferences.edit();

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        validateServerClientID();

        Button dashboard_button = (Button)findViewById(R.id.dashboard_button);
        dashboard_button.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                if (findViewById(R.id.sign_in_button).getVisibility() != View.VISIBLE) {
                    Intent UserActivityIntent = new Intent(getApplicationContext(), NavigationDrawer.class);
                    startActivity(UserActivityIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Sign In", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient();
        mGoogleApiClient.connect();

        // [START customize_button]
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        // [END customize_button]

    }

    private void getIdToken() {
        // Show an account picker to let the user choose a Google account from the device.
        // If the GoogleSignInOptions only asks for IDToken and/or profile and/or email then no
        // consent screen will be shown here.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GET_TOKEN);
    }



    @Override
    public void onStart() {
        super.onStart();

//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
//            // and the GoogleSignInResult will be available instantly.
//            Log.d(TAG, "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            try {
//                handleSignInResult(result);
//            } catch (ExecutionException | InterruptedException | JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        else {
//            // If the user has not previously signed in on this device or the sign-in has expired,
//            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
//            // single sign-on will occur in this branch.
//            showProgressDialog();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
//                    try {
//                        handleSignInResult(googleSignInResult);
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideProgressDialog();

        getSignOutIntent = getIntent().getStringExtra("signout");
        getIntent().removeExtra("signout");
        if (getSignOutIntent !=null && getSignOutIntent.equals("true")){
            Log.d("dummy", getSignOutIntent);
            signOut();
        }
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            try {
                handleSignInResult(result);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) throws ExecutionException, InterruptedException, JSONException {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            String personName = acct.getDisplayName();
            Log.d(TAG, "Display Name:" + personName);

            String personGivenName = acct.getGivenName();
            Log.d(TAG, "Given Name:" + personGivenName);

            String personFamilyName = acct.getFamilyName();
            Log.d(TAG, "Family Name:" + personFamilyName);

            String personEmail = acct.getEmail();
            Log.d(TAG, "Email:" + personEmail);

            String personId = acct.getId();
            Log.d(TAG, "ID:" + personId);

            Uri personPhoto = acct.getPhotoUrl();
            Log.d(TAG, "Photo:" + personPhoto);

            final String id_token = acct.getIdToken();
            ID_TOKEN = acct.getIdToken();

            editor.clear();
            editor.apply();

            editor.putString("id_token", id_token);
            editor.putString("user_name", personName);
            editor.putString("user_email", personEmail);
            editor.apply();

            Log.d(TAG, "IDToken:" + id_token);

            Log.d(TAG, "Started HttpUrlConnection");
//            String url = "http://iot.andrew.cmu.edu:10010/auth/google/token";
            String url = "http://openchirp.andrew.cmu.edu:7000/auth/google/token";

            JSONObject postMessage = new JSONObject();

            try {
                postMessage.put("idToken", id_token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject resp =  openChirpHelper.PostOpenChirp(url, postMessage, getApplicationContext());
            Log.d(TAG, resp.toString());
            Log.d(TAG, "Ended HttpUrlConnection");

            CookieManager cmrCookieMan = new CookieManager(new MyCookieStore(getApplicationContext()), CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cmrCookieMan);

            Log.d(TAG, "Started Display");
            mStatusTextView.setText(acct.getDisplayName());
            Log.d(TAG, "Ended Display");

            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.googlesigninopti

        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    private void validateServerClientID() {
        //String serverClientId = getString(R.string.server_client_id);
        String serverClientId = default_web_client_ID;
        String suffix = ".apps.googleusercontent.com";
        String id_validate = "Server client ID validated";
        Log.w(TAG, id_validate);
        Toast.makeText(this, id_validate, Toast.LENGTH_LONG).show();

        if (!serverClientId.trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;
            Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }

    @Override
    public void onBackPressed(){
    }

}


