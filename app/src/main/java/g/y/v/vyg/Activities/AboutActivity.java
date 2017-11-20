package g.y.v.vyg.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import g.y.v.vyg.R;
import g.y.v.vyg.Utils.BaseActivity;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolBarInitialize(R.id.toolbar);
        setTitle("Voyage");
    }
}
