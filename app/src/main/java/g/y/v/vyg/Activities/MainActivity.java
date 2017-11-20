package g.y.v.vyg.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import g.y.v.vyg.R;
import g.y.v.vyg.Utils.BaseActivity;

public class MainActivity extends BaseActivity{

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBarInitialize(R.id.toolbar);
        setTitle("Voyage");

    }
}
