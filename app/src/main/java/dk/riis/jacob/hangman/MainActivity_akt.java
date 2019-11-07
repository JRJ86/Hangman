package dk.riis.jacob.hangman;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import dk.riis.jacob.hangman.fragment_controllers.About_frag;
import dk.riis.jacob.hangman.fragment_controllers.HelpPage_frag;
import dk.riis.jacob.hangman.fragment_controllers.Highscore_frag;
import dk.riis.jacob.hangman.fragment_controllers.WelcomePage_frag;

/**
 * The main activity which holds the actionbar, the navigation drawer and the fragment container
 * that switches content based on which fragment is displayed.
 */
public class MainActivity_akt extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle barDrawerToggle;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Started.");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        barDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close);

        drawerLayout.addDrawerListener(barDrawerToggle);
        barDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            Fragment fragment = new WelcomePage_frag();
            getSupportFragmentManager().beginTransaction().add(R.id.fragContainer,fragment).commit();
        }

        configureNavigationDrawer();
    }

    /**
     * This method gives functionality to all the items in the actionbar.
     *
     * @param item The Actionbar item.
     * @return     true, for the item selected.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (barDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return false;
    }

    /**
     * This method adds functionality to menu navigation drawer elements
     */
    private void configureNavigationDrawer() {
        NavigationView navigationView;
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.leftMenu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                WelcomePage_frag welcomePage_frag = new WelcomePage_frag();
                HelpPage_frag helpPage_frag = new HelpPage_frag();
                Highscore_frag highscore_frag = new Highscore_frag();
                About_frag about_frag = new About_frag();

                int itemid = menuItem.getItemId();

                if (itemid == R.id.nav_home) {
                    changeFragFromMenu(welcomePage_frag);

                }else if (itemid == R.id.nav_help){
                    changeFragFromMenuBackstack(helpPage_frag);

                } else if (itemid == R.id.nav_highscore) {
                    changeFragFromMenuBackstack(highscore_frag);

                } else if (itemid == R.id.nav_mail) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                    intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
                    intent.setData(Uri.parse("mailto:HangmanDeveloper@DTU.DK")); // or just "mailto:" for blank
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    startActivity(intent);

                } else if (itemid == R.id.nav_about) {
                    changeFragFromMenuBackstack(about_frag);

                } else {
                    Toast.makeText(MainActivity_akt.this, "Du klikkede på noget ikke funktionelt. prøv igen",
                            Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }

    /**
     * Changes fragment from the navigation drawer, with addToBackstack
     *
     * @param fragment The fragment you want to change to
     * @return         True if the function goes through
     */
    private boolean changeFragFromMenuBackstack(Fragment fragment){
        fragmentManager = MainActivity_akt.this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        drawerLayout.closeDrawers();
        return true;
    }

    /**
     * Changes fragment from the navigation drawer
     *
     * @param fragment The fragment you want to change to
     * @return         True if the function goes through
     */
    private boolean changeFragFromMenu(Fragment fragment){
        fragmentManager = MainActivity_akt.this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, fragment);
        fragmentTransaction.commit();
        drawerLayout.closeDrawers();
        return true;
    }

}
