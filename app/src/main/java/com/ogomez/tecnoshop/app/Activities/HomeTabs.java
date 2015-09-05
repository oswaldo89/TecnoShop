package com.ogomez.tecnoshop.app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.ogomez.tecnoshop.app.Adapters.ViewPagerAdapter;
import com.ogomez.tecnoshop.R;
import com.quinny898.library.persistentsearch.SearchBox;
import java.util.ArrayList;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;


public class HomeTabs extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private SearchBox search;
    Toolbar toolbar;
    Boolean doubleBackToExitPressedOnce = false;
    private View theMenu;
    private View menu1;
    private View menu2;
    private View overlay;
    private boolean menuOpen = false;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tabs);




        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        //menu tipo whatsap
        theMenu = findViewById(R.id.the_menu);
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        overlay = findViewById(R.id.overlay);
        //Termina menu tipo whatsap

        search = (SearchBox) findViewById(R.id.searchbox);
        search.enableVoiceRecognition(this);
        search.setLogoText("TecnoShop");

        toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);
        this.setSupportActionBar(toolbar);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setTabsIcons(tabLayout);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        //Snackbar.make(findViewById(android.R.id.content), "1", Snackbar.LENGTH_LONG).show();
                        break;
                    case 1:
                        //Snackbar.make(findViewById(android.R.id.content), "2", Snackbar.LENGTH_LONG).show();
                        break;
                    case 2:
                        //Snackbar.make(findViewById(android.R.id.content), "3", Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void openSearch() {
        toolbar.setTitle("");
        search.revealFromMenuItem(R.id.search, this);

        search.setSearchListener(new SearchBox.SearchListener() {
            @Override
            public void onSearchOpened() {
            }

            @Override
            public void onSearchClosed() {
                closeSearch();
            }

            @Override
            public void onSearchTermChanged() {
            }

            @Override
            public void onSearch(String searchTerm) {
                Toast.makeText(HomeTabs.this, searchTerm + " Busqueda", Toast.LENGTH_LONG).show();
                toolbar.setTitle(searchTerm);
            }

            @Override
            public void onSearchCleared() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GOOGLE_SIGIN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }

        if (requestCode == 1234 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search.populateEditText(matches);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    protected void closeSearch() {
        search.hideCircularly(this);
        if(search.getSearchText().isEmpty())toolbar.setTitle("TecnoShop");
    }


    private void setupViewPager(ViewPager viewPager) {

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    public void setTabsIcons(TabLayout tabsIcons) {

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoBold.ttf");
        TextView tab1 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab1.setText("ULTIMOS");
        tab1.setTypeface(face);
        tab1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.time, 0, 0);
        tabsIcons.getTabAt(0).setCustomView(tab1);

        TextView tab2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab2.setText("CATEGORIAS");
        tab2.setTypeface(face);
        tab2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cate, 0, 0);
        tabsIcons.getTabAt(1).setCustomView(tab2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Busqueda").setIcon(R.drawable.search).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add("Opciones").setIcon(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle().equals("Busqueda")) {
            openSearch();
        }
        if (item.getTitle().equals("Opciones")) {
            if (!menuOpen) {
                revealMenu();
            } else {
                hideMenu();
            }
        }

        return super.onOptionsItemSelected(item);
    }


    public void revealMenu() {
        menuOpen = true;
        Log.v("LOG","Entra");
        theMenu.setVisibility(View.INVISIBLE);
        int cx = theMenu.getRight();
        int cy = theMenu.getTop();
        int finalRadius = Math.max(theMenu.getWidth(), theMenu.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(theMenu, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.start();
        theMenu.setVisibility(View.VISIBLE);
        overlay.setVisibility(View.VISIBLE);


        // Animate The Icons One after the other, I really would like to know if there is any
        // simpler way to do it
        Animation popeup1 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popeup2 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        popeup1.setStartOffset(50);
        popeup2.setStartOffset(100);
        menu1.startAnimation(popeup1);
        menu2.startAnimation(popeup2);

    }

    public void hideMenu() {
        menuOpen = false;
        int cx = theMenu.getRight();
        int cy = theMenu.getTop();
        int initialRadius = theMenu.getWidth();


        SupportAnimator anim = ViewAnimationUtils.createCircularReveal(theMenu, cx, cy, initialRadius, 0);
        anim.addListener(new SupportAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {
                theMenu.setVisibility(View.INVISIBLE);
                theMenu.setVisibility(View.GONE);
                overlay.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel() {

            }

            @Override
            public void onAnimationRepeat() {

            }
        });

        anim.start();
    }

    public void overlayClick(View v) {
        Log.v("TAG","Click overlay");
        hideMenu();
    }

    public void menuClick(View view) {
        Log.v("TAG","Click overlay");
        hideMenu();
        if(view.getTag().toString().equals("Salir"))
        {
            SharedPreferences prefs = HomeTabs.this.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", "0");
            editor.putString("id_user", "0");
            editor.commit();

            callFacebookLogout(HomeTabs.this);
        }

        if(view.getTag().toString().equals("Chat"))
        {
            Snackbar.make(findViewById(android.R.id.content), "Chat no se encuentra disponible aun", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {

        if (menuOpen) {
            hideMenu();
        } else {
            //finishAfterTransition();
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(findViewById(android.R.id.content), "Presione otra vez para salir", Snackbar.LENGTH_LONG).show();
        //closeSearch();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    /**
     * Logout From Facebook
     */

    public void callFacebookLogout(Context context) {
        Log.v("LOGIN", "mGoogleApiClient.isConnected()" + mGoogleApiClient.isConnected());
        if (mGoogleApiClient.isConnected()) {
            Log.v("LOGIN", "Se desloguea de G+");
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
        LoginManager.getInstance().logOut();
        Intent i = new Intent(HomeTabs.this,Login.class);
        startActivity(i);
        finish();
    }


    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        //Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
        getProfileInformation();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
            return;
        }

        if (!mIntentInProgress) {
            mConnectionResult = connectionResult;

            if (mSignInClicked) {
                resolveSignInError();
            }
        }
    }
    private static final int GOOGLE_SIGIN = 100;
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, GOOGLE_SIGIN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {

                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                Log.v("LOGIN",email);



            } else {
               // Toast.makeText(getApplicationContext(), "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




