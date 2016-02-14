package quixada.ufc.br.kisan.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.adapter.TabFragmentsAdapter;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ListView mDrawerList;
    private TabFragmentsAdapter mainTabFragmentsAdapter;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String mActivityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerList = (ListView)findViewById(R.id.navList);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();


        viewPager = (ViewPager) findViewById(R.id.viewPagerTabs);
        viewPager.setOffscreenPageLimit(2);

        mainTabFragmentsAdapter = new TabFragmentsAdapter(getSupportFragmentManager(), this.getBaseContext());
        viewPager.setAdapter(mainTabFragmentsAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        int corUnselected = getResources().getColor(R.color.colorPrimaryDark);
        int corSelected = getResources().getColor(R.color.white);
        tabLayout.setTabTextColors(corUnselected, corSelected);
        //mudar cor de tab selecionada
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.setupWithViewPager(viewPager);

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }


    private void addDrawerItems() {

        String[] osArray = {"Aqui foto usuario","Meu Perfil", "Meus Anúncios" ,"Conversas", "Logout"," Mapa"};
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, osArray);

        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = null;

                switch (position){
                    case 1:
                        intent = new Intent(getApplicationContext(), VisualizarPerfilActivity.class);
                           startActivity(intent);
                        //    intent = new Intent(getApplicationContext(), VisualizarPerfilActivity.class);
                    //    startActivity(intent);
                        Toast.makeText(MainActivity.this, "Perfil!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), VisualizarMeusAnunciosActivity.class);
                        startActivity(intent);
                     //   Toast.makeText(MainActivity.this, "Meus Anuncios!", Toast.LENGTH_SHORT).show();

                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), ConversasActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Conversas!", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:

                        LoginManager.getInstance().logOut();
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(MainActivity.this, "Logout!", Toast.LENGTH_SHORT).show();

                        break;
                    case 5:

                        intent = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Maps!", Toast.LENGTH_SHORT).show();
                    default:
                }


                 }
        });

    }
    private void setupDrawer() {

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml

        // Activate the navigation drawer toggle
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
