package kycklingstuds.kycklingstuds;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.WebDialog;

import java.util.Arrays;
import java.util.List;

public class FBFragment extends Fragment {

    private LoginButton loginBtn;
    private Button postImageBtn;
    private Button updateStatusBtn;
    private Button shareAppBtn;
    private Button inviteFriendBtn;

    private TextView userName;

    private UiLifecycleHelper uiHelper;

    private static final List<String> PERMISSIONS = Arrays.asList("basic_info, publish_actions");

    private static String message = "Sheep Leap";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu, container, false);

        userName = (TextView) view.findViewById(R.id.user_name);

        loginBtn = (LoginButton) view.findViewById(R.id.fb_login_button);
        loginBtn.setFragment(this);
        loginBtn.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    userName.setText("User: " + user.getName());
                } else {
                    userName.setText("Not logged in.");
                }
            }
        });

        updateStatusBtn = (Button) view.findViewById(R.id.updateStatusButton);
        updateStatusBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postStatusMessage();
            }
        });


        shareAppBtn = (Button) view.findViewById(R.id.shareStatusButton);
        shareAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FacebookDialog.canPresentShareDialog(v.getContext(),
                        FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
                    // Publish the post using the Share Dialog
                    FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
                            .setLink("https://developers.facebook.com/android") //Link to our app
                            .setDescription("My highscore is " + Integer.toString(Resources.HIGHSCORE.getHighscore().getPoints()) + " points. Can you beat me?")
                            .setName("Sheep Leap")
                            .build();
                    uiHelper.trackPendingDialogCall(shareDialog.present());

                } else {

                }
            }
        });

        inviteFriendBtn = (Button) view.findViewById(R.id.inviteFriendButton);
        inviteFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteFriend();
            }
        });


        buttonsEnabled(false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(getActivity(), statusCallback);
        uiHelper.onCreate(savedInstanceState);


    }

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {
                buttonsEnabled(true);
                Log.d("FacebookSampleActivity", "Facebook session opened");
            } else if (state.isClosed()) {
                buttonsEnabled(false);
                Log.d("FacebookSampleActivity", "Facebook session closed");
            }
        }
    };

    public void buttonsEnabled(boolean isEnabled) {
        updateStatusBtn.setEnabled(isEnabled);
    }

    //Future uses?
    public void postImage() {
        if (checkPermissions()) {
            Bitmap img = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            Request uploadRequest = Request.newUploadPhotoRequest(
                    Session.getActiveSession(), img, new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            Toast.makeText(getActivity(),
                                    "Photo uploaded successfully",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            );
            uploadRequest.executeAsync();
        } else {
            requestPermissions();
        }
    }

    public void postStatusMessage() {
        if (checkPermissions()) {
            Request request = Request.newStatusUpdateRequest(
                    Session.getActiveSession(), message,
                    new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            if (response.getError() == null)
                                Toast.makeText(getActivity(),
                                        "Status updated successfully",
                                        Toast.LENGTH_LONG).show();
                        }
                    }
            );
            request.executeAsync();
        } else {
            requestPermissions();
        }
    }

    private void inviteFriend() {
        Bundle params = new Bundle();
        params.putString("message", "My highscore is " + Integer.toString(Resources.HIGHSCORE.getHighscore().getPoints()) + " points. Can you beat me?");

        WebDialog requestsDialog = (
                new WebDialog.RequestsDialogBuilder(getActivity(),
                        Session.getActiveSession(),
                        params))
                .setOnCompleteListener(new WebDialog.OnCompleteListener() {

                    @Override
                    public void onComplete(Bundle values,
                                           FacebookException error) {
                        if (error != null) {
                            if (error instanceof FacebookOperationCanceledException) {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Request cancelled",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Network Error",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            final String requestId = values.getString("request");
                            if (requestId != null) {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Request sent",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Request cancelled",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                })
                .build();
        requestsDialog.show();
    }

  /*  private void sendRequestDialog() {
        Bundle params = new Bundle();
        params.putString("title", "Invite Friend");
        params.putString("message", " has invited you to try out Sheep Leap: The most awesome gaming experience of all time. " +
                " Download now on your Android device!");
        // comma seperated list of facebook IDs to preset the recipients. If left out, it will show a Friend Picker.
        params.putString("to", "");  // your friend id

        WebDialog requestsDialog = ( new WebDialog.RequestsDialogBuilder(getActivity(),
                Session.getActiveSession(), params)).setOnCompleteListener(new WebDialog.OnCompleteListener() {
            @Override
            public void onComplete(Bundle values, FacebookException error) {
                //   Auto-generated method stub
                if (error != null) {
                    if (error instanceof FacebookOperationCanceledException) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Request cancelled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Network Error",  Toast.LENGTH_SHORT).show();
                    }
                } else {
                    final String requestId = values.getString("request");
                    if (requestId != null) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Request sent",  Toast.LENGTH_SHORT).show();
                        Log.i("TAG", " Sent facebook invite ");
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Request cancelled", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).build();
        requestsDialog.show();
    }*/

    public boolean checkPermissions() {
        Session s = Session.getActiveSession();
        if (s != null) {
            return s.getPermissions().contains("publish_actions");
        } else
            return false;
    }

    public void requestPermissions() {
        Session s = Session.getActiveSession();
        if (s != null)
            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
                    this, PERMISSIONS));
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
        buttonsEnabled(Session.getActiveSession().isOpened());
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }

}


