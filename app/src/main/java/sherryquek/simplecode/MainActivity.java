package sherryquek.simplecode;

import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.os.AsyncTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabhost = (TabHost)findViewById(R.id.tabHost);
        tabhost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabhost.newTabSpec("Tab One");
        spec.setContent(R.id.Code);
        spec.setIndicator("Code");
        tabhost.addTab(spec);

        //Tab 2
        spec = tabhost.newTabSpec("Tab Two");
        spec.setContent(R.id.Decode);
        spec.setIndicator("Decode");
        tabhost.addTab(spec);

        ImageView droid = (ImageView) findViewById(R.id.imageView);
        droid.setImageResource(R.drawable.droid);

    }

    EditText input1, input2;
    String inputStr, inputNum;
    TextView result;
    AsyncTaskRunner runner;

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonCode:
                runner = new AsyncTaskRunner();
                input1 = (EditText) findViewById(R.id.textUncoded);
                input2 = (EditText) findViewById(R.id.numCode);
                inputStr = input1.getText().toString().trim();
                inputNum = input2.getText().toString();
                result = (TextView) findViewById(R.id.coded);
                runner.execute(inputStr, inputNum, "1");
                break;
            case R.id.buttonUncode:
                runner = new AsyncTaskRunner();
                input1 = (EditText) findViewById(R.id.textCoded);
                input2 = (EditText) findViewById(R.id.numUncode);
                inputStr = input1.getText().toString().trim();
                inputNum = input2.getText().toString();
                result = (TextView) findViewById(R.id.uncoded);
                runner.execute(inputStr, inputNum, "2");
                break;
        }

    }
    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        private String str, num, operation;

        @Override
        protected String doInBackground(String... params) {
            str = params[0];
            num = params[1];
            operation = params[2];
            if (operation.equals("1")){
                return codeStr(str, num);
            }
            else if (operation.equals("2")){
                return uncodeStr(str, num);
            }
            else {
                return "Invalid input";
            }
        }

        @Override
        protected void onPostExecute(String res) {
            result.setText(res);
        }

        @Override
        protected void onPreExecute(){
            result.setText("Processing...");
        }

        public String codeStr (String str, String num) {
            String result = "";
            for (int i = 0; i < str.length(); i++) {
                int curr = str.codePointAt(i);
                int adder = Character.getNumericValue(num.charAt(i%num.length()));
                if (curr >= 97 && curr <= 122) {
                    curr = curr + adder;
                    if (curr > 122) {curr -= 26;}
                }
                else if (curr >= 65 && curr <= 90) {
                    curr = curr + adder;
                    if(curr > 90) {curr -= 26;}
                }
                result = result + (Character.toString((char)curr));
            }
            return result;
        }

        public String uncodeStr (String str, String num){
            String result = "";
            for (int i = 0; i < str.length(); i++) {
                int curr = str.codePointAt(i);
                int adder = Character.getNumericValue(num.charAt(i%num.length()));
                if (curr >= 97 && curr <= 122) {
                    curr = curr - adder;
                    if (curr < 97) {curr += 26;}
                }
                else if (curr >= 65 && curr <= 90) {
                    curr = curr + adder;
                    if(curr < 65) {curr += 26;}
                }
                result = result + (Character.toString((char)curr));
            }
            return result;
        }

    }
}


