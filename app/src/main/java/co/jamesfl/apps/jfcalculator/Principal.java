package co.jamesfl.apps.jfcalculator;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Principal extends AppCompatActivity {
    private TextView tvRes;
    private EditText etNum1, etNum2;
    private Spinner spOperac;
    private Resources src;
    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tvRes = (TextView) findViewById(R.id.tvResultado);
        etNum1 = (EditText) findViewById(R.id.etNumero1);
        etNum2 = (EditText) findViewById(R.id.etNumero2);
        spOperac = (Spinner) findViewById(R.id.spOperacion);
        src = getResources();
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, src.getStringArray(R.array.operaciones));
        spOperac.setAdapter(adp);
        df = new DecimalFormat("#.##");
    }

    public void calcular(View v) {
        double resultado = 0;
        int opcion = spOperac.getSelectedItemPosition();
        double num1 = Double.parseDouble(etNum1.getText().toString());
        double num2 = Double.parseDouble(etNum2.getText().toString());
        if (esValido()) {
            switch (opcion) {
                case 0: //Suma
                    resultado = num1 + num2;
                    break;
                case 1: //Resta
                    resultado = num1 - num2;
                    break;
                case 2: //Mult
                    resultado = num1 * num2;
                    break;
                case 3: //Div
                    if (num2 > 0) {
                        resultado = num1 / num2;
                    }
            }
            tvRes.setText(df.format(resultado));
        }
    }

    private boolean esValido() {
        if (etNum1.getText().toString().isEmpty()) {
            etNum1.setError(src.getString(R.string.errNum1));
            etNum1.requestFocus();
            return false;
        }
        if (etNum2.getText().toString().isEmpty()) {
            etNum2.setError(src.getString(R.string.errNum2));
            etNum2.requestFocus();
            return false;
        }
        if (spOperac.getSelectedItemPosition() == 3 && Double.parseDouble(etNum2.getText().toString()) == 0) {
            etNum2.setError(src.getString(R.string.errNum2Div0));
            etNum2.requestFocus();
            return false;
        }
        etNum1.setError(null);
        etNum2.setError(null);
        tvRes.setText("");
        return true;
    }

    public void borrar(View v) {
        etNum1.setText("");
        etNum2.setText("");
        tvRes.setText("");
        spOperac.setSelection(0, true);
        etNum1.requestFocus();
    }
}
