package MainActivity;



import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prathamesh.Authentication.R;
import com.example.prathamesh.Authentication.signin;

import java.util.ArrayList;

import Bank.Bank;
import Cash.Cash;
import Cheque.Cheque;
import Expenses.Expense;
import Expenses.Expense_List;
import Extra.AllParties;
import Extra.RealmConfig;
import ProductMaster.ItemList;
import Purchase.purchase;
import RealmClasses.Company_details;
import Returns.ReturnsPurchase;
import Returns.ReturnsSale;
import Sale.Sale;
import Tax.Taxlist;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textView = (TextView) findViewById(R.id.textView16);

        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        TextView TextView;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.allparties:
                        startActivity(new Intent(MainActivity.this, AllParties.class));
                        finish();
                        break;
                    case R.id.items:
                        startActivity(new Intent(MainActivity.this, ItemList.class));
                        finish();
                        break;
                    case R.id.expenses:
                        startActivity(new Intent(MainActivity.this, Expense.class));
                        finish();
                        break;
                    case R.id.accounts:
                        Intent intent_1 = new Intent(MainActivity.this, signin.class);
                        intent_1.putExtra("key", 2);
                        Toast.makeText(MainActivity.this, "Sign-In", Toast.LENGTH_SHORT).show();

                        try {
                            SyncUser syncUser = SyncUser.current();
                            if (syncUser != null) {
                                syncUser.logOut();
                            }
                        } catch (Exception e) {
                        }

                        startActivity(intent_1);
                        finish();
                        break;
                    case R.id.taxlist:
                        startActivity(new Intent(MainActivity.this, Taxlist.class));
                        finish();
                        break;
                    case R.id.cash:
                        startActivity(new Intent(MainActivity.this, Cash.class));
                        finish();
                        break;
                    case R.id.cheque:
                        startActivity(new Intent(MainActivity.this, Cheque.class));
                        finish();
                        break;
                    case R.id.calculator:
                        try {
                            Intent intent = new Intent();
                            intent.setClassName("com.android.calculator2", "com.android.calculator2.Calculator");
                            startActivity(intent);
                        } catch (Exception e) {
                            Log.d("mouseerror", e.getMessage());
                        }
                        finish();
                        break;
                    case R.id.bank:
                        startActivity(new Intent(MainActivity.this, Bank.class));
                        finish();
                        break;
                }
                return true;

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // Toolbar values for title
        EditText title = (EditText) findViewById(R.id.title);
        Button save = (Button) findViewById(R.id.save);


            RealmConfig realmConfig = new RealmConfig();
            SyncConfiguration config = realmConfig.getConfig();

            Realm.getInstanceAsync(config, new Realm.Callback() {
                @Override
                public void onSuccess(Realm realm) {
                    try {
                        ArrayList<Company_details> fetch = new ArrayList<>(realm.where(Company_details.class).findAll());
                        title.setText(fetch.get(0).getName());
                        title.setFocusableInTouchMode(false);
                        save.setVisibility(View.GONE);
                        title.setVisibility(View.GONE);
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onError(Throwable exception) {
                    // Handle error
                }

            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RealmConfig realmConfig = new RealmConfig();
                    SyncConfiguration config = realmConfig.getConfig();

                    Realm.getInstanceAsync(config, new Realm.Callback() {
                        @Override
                        public void onSuccess(Realm realm) {

                            realm.beginTransaction();
                            Company_details company_details = realm.createObject(Company_details.class);
                            company_details.setName(title.getText().toString());
                            realm.commitTransaction();

                            ArrayList<Company_details> fetch = new ArrayList<>(realm.where(Company_details.class).findAll());
                            title.setText(fetch.get(0).getName());
                            title.setFocusableInTouchMode(false);
                            save.setVisibility(View.GONE);
                            title.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Throwable exception) {
                            // Handle error
                        }

                    });


                }
            });


// Remove default title text

// Get access to the custom title view

            Button button7 = (Button) findViewById(R.id.button7);
            button7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, purchase.class);
                    startActivity(intent);
                }
            });

            Button button11 = (Button) findViewById(R.id.button11);
            button11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Sale.class);
                    startActivity(intent);
                }
            });

            Button button9 = (Button) findViewById(R.id.button9);
            button9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AllParties.class);
                    startActivity(intent);
                }
            });

            Button button10 = (Button) findViewById(R.id.button10);
            button10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ItemList.class);
                    startActivity(intent);
                }
            });

            FloatingActionButton fab = findViewById(R.id.fab);
            boolean[] phase = new boolean[1];

            phase[0] = false;
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Inflating the dialog box

                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    final LayoutInflater inflater = MainActivity.this.getLayoutInflater();

                    ViewGroup nullParent = null;
                    View custom_dialog = inflater.inflate(R.layout.dialog_mainactivity, nullParent);
                    builder.setView(custom_dialog);
                    AlertDialog doneBuild = builder.create();
                    doneBuild.show();
                    doneBuild.setCanceledOnTouchOutside(false);


                    Button salesreturn = (Button) custom_dialog.findViewById(R.id.textView16);
                    salesreturn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, ReturnsSale.class));
                        }
                    });

                    Button purchasereturn = (Button) custom_dialog.findViewById(R.id.textView12);
                    purchasereturn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, ReturnsPurchase.class));
                        }
                    });

                    Button Expense = (Button) custom_dialog.findViewById(R.id.textView11);
                    Expense.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, Expense.class));
                        }
                    });

                    Button Expense_list = (Button) custom_dialog.findViewById(R.id.textView13);
                    Expense_list.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, Expense_List.class));
                        }
                    });


                    if (phase[0] == false) {

                        final OvershootInterpolator interpolator = new OvershootInterpolator();
                        ViewCompat.animate(fab).
                                rotation(135f).
                                withLayer().
                                setDuration(200).
                                setInterpolator(interpolator).
                                start();
                        phase[0] = true;


                        doneBuild.show();

                        //Delay
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();

                        ViewCompat.animate(fab).
                                rotation(000f).
                                withLayer().
                                setDuration(200).
                                setInterpolator(interpolator).
                                start();
                        phase[0] = false;

                    }


                }


            });


        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    }





