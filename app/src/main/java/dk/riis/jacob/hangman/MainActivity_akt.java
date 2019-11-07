package dk.riis.jacob.hangman;

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

public class MainActivity_akt extends AppCompatActivity {

    private static  final String TAG = "MainActivity";

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

    private void configureNavigationDrawer() {
        NavigationView navigationView;
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.leftMenu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemid = menuItem.getItemId();

                //Tilføj funktionalitet til menu items

                if (true) {

                } else {
                    Toast.makeText(MainActivity_akt.this, "Du klikkede på noget ikke funktionelt. prøv igen",
                            Toast.LENGTH_LONG).show();

                }
                return false;
            }
        });
    }
}
