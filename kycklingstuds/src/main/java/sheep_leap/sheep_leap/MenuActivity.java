package sheep_leap.sheep_leap;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MenuActivity extends FragmentActivity {

    private FBFragment fbFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            fbFragment = new FBFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fbFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            fbFragment = (FBFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "kycklingstuds.kycklingstuds",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                System.out.println("DEBUG: KeyHash: " + (Base64.encodeToString(md.digest(), Base64.DEFAULT)));
            }
        } catch (PackageManager.NameNotFoundException e) {
            System.out.println("EXCEPTION: " + e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("EXCEPTION: " + e);
        }




    }

    public void onPlayBtnClicked(View v) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }


    public void onOptionsBtnClicked(View v) {
        System.out.println("DEBUG: onOptionsBtnClicked");
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);



    }

    public void onLeaderboardBtnClicked(View v) {
        System.out.println("DEBUG: onLeaderboardBtnClicked");

        System.out.println("DEBUG: onOptionsBtnClicked");
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);

       /* Uri uri = Uri.parse("http://sheep-leap.raimat.webfactional.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);*/
    }


}
