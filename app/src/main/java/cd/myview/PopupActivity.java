package cd.myview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class PopupActivity extends AppCompatActivity {
    private EditText et_input;
    private ImageView iv_down_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        et_input = findViewById(R.id.et_input);
        iv_down_arrow = findViewById(R.id.iv_down_arrow);
    }
}
