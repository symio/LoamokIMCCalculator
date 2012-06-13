package org.loamok.utils;

import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.content.res.Resources;
import android.text.method.ScrollingMovementMethod;

public class IMCActivity extends Activity
{
    // Variables métier
    float poids = 0;
    float taille = 0;
    
    // ressources
    RadioGroup widget40 = null; // groupe radios
    String erreur = null;
    String noResText = null;
    TextView widget47 = null; // resText
    String poidsHint = null;
    EditText poidsE = null; // EditText Poids
    String tailleHint = null;
    EditText tailleE = null; // EditText Taille
    Button widget44 = null; // bouton calculer
    Button widget45 = null; // bouton RAZ
    
    // utilitaires
    Toast toast = null;
    Resources Res = null;
    
    // listeners
    // Modification d'un edit
    private View.OnKeyListener modificationListener = new View.OnKeyListener() {
        @Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
	    // On remet le texte à sa valeur par défaut pour ne pas avoir de résultat incohérent
	    widget47.setText(noResText);
            if(poidsE.getText().toString().length() != 0 || tailleE.getText().toString().length() != 0) {
                widget45.setEnabled(true);
            } else {
                widget45.setEnabled(false);
            }
	    return false;
	}
    };
    
    // RAZ
    private View.OnClickListener razClickListener = new View.OnClickListener() {
        
        Button b = null;
        
        public void onClick(View e) {
            b = (Button) e;
            raz();
            widget45.setEnabled(false);
        }
    };
    
    private RadioGroup.OnCheckedChangeListener rgChangeListener = new RadioGroup.OnCheckedChangeListener() {

        public void onCheckedChanged(RadioGroup arg0, int arg1) {
            if(poidsE.getText().toString().length() != 0 || tailleE.getText().toString().length() != 0) {
                widget45.setEnabled(true);
            } else {
                widget45.setEnabled(false);
            }
        }
    };
    
    private View.OnClickListener calculClickListener = new View.OnClickListener() {
        
        Button b = null;
        String resText = null;
        
        public void onClick(View v) {
            b = (Button) v;
            int rc = widget40.getCheckedRadioButtonId();
            resText = Res.getString(R.string.resText);
            float res;
            
            if(poidsE.getText().toString() != "" || tailleE.getText().toString() != "") {
                widget45.setEnabled(true);
            } else {
                widget45.setEnabled(false);
            }
            
            try {
                if(checkVars()) {
                    if(rc == R.id.widget42) {
                        taille = taille / 100;
                    }

                    res = poids / (taille * taille);
                    widget47.setText(resText.replace(":", ": " + String.valueOf(res)));
                }
            } catch (Exception e) {
                toast.show();
            }
        }
    };
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Res = getResources();
        
        // radio cochée par défaut sur "centimètres"
        widget40 = (RadioGroup) findViewById(R.id.widget40);
        widget40.check(R.id.widget42);
        widget40.setOnCheckedChangeListener(rgChangeListener);
        
        // allez on vas récupérer le message d'erreur et on l'affecte au toast
        erreur = Res.getString(R.string.Erreur);
        toast = Toast.makeText(this, erreur, Toast.LENGTH_SHORT);
        
        // même chose pour le message 'noResText'
        noResText = Res.getString(R.string.noResText);
        
        widget47 = (TextView) findViewById(R.id.widget47);
        widget47.setText(noResText);
        
        // et les hints des edit text (avec les edit texts)
        poidsHint = Res.getString(R.string.poidsHint);
        tailleHint = Res.getString(R.string.tailleHint);
        poidsE = (EditText) findViewById(R.id.widget37);
        poidsE.setOnKeyListener(modificationListener);
        tailleE = (EditText) findViewById(R.id.widget39);
        tailleE.setOnKeyListener(modificationListener);
        // récupération de divers widgets
        widget44 = (Button) findViewById(R.id.widget44);
        widget44.setOnClickListener(calculClickListener);
        widget45 = (Button) findViewById(R.id.widget45);
        widget45.setEnabled(false);
        widget45.setOnClickListener(razClickListener);
        
    try {
        
    } catch (Exception e) {
        TextView tv = (TextView) findViewById(R.id.widget47);
        tv.setText(e.toString());
    }
    
    }
    
    private boolean checkVars() {
        boolean res = false;
        
        poids = Float.valueOf(poidsE.getText().toString());
        taille = Float.valueOf(tailleE.getText().toString());
        
        if(poids <= 0 || taille <= 0) {
            res = false;
        } else if(poids >= 0 && taille >= 0) {
            res = true;
        }
        
        if(res == false) {
            toast.show();
        }
        return res;
    }
    
    private void raz() {
        poidsE.getText().clear();
        tailleE.getText().clear();
        widget47.setText(noResText);
    }
}
