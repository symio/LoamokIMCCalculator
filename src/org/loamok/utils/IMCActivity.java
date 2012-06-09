package org.loamok.utils;

import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.text.Editable;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioGroup;

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
    EditText poidsE = null;
    String tailleHint = null;
    EditText tailleE = null;
    Button widget44 = null; // bouton calculer
    Button widget45 = null; // bouton RAZ
    
    // utilitaires
    Toast toast = null;
    
    // listeners
    // Modification d'un edit
    private View.OnKeyListener modificationListener = new View.OnKeyListener() {
        @Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
	    // On remet le texte à sa valeur par défaut pour ne pas avoir de résultat incohérent
	    widget47.setText(noResText);
	    return false;
	}
    };
    
    // RAZ
    private View.OnClickListener razClickListener = new View.OnClickListener() {
        
        Button b = null;
        
        public void onClick(View e) {
            b = (Button) e;
            raz();
        }
    };
    
    private View.OnClickListener calculClickListener = new View.OnClickListener() {
        
        Button b = null;
        String resText = null;
        
        public void onClick(View v) {
            b = (Button) v;
            int rc = widget40.getCheckedRadioButtonId();
            resText = getResources().getString(R.string.resText);
            float res;
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
        
        // radio cochée par défaut sur "centimètres"
        widget40 = (RadioGroup) findViewById(R.id.widget40);
        widget40.check(R.id.widget42);
        
        // allez on vas récupérer le message d'erreur et on l'affecte au toast
        erreur = getResources().getString(R.string.Erreur);
        toast = Toast.makeText(this, erreur, Toast.LENGTH_SHORT);
        
        // même chose pour le message 'noResText'
        noResText = getResources().getString(R.string.noResText);
        widget47 = (TextView) findViewById(R.id.widget47);
        widget47.setText(noResText);
        
        // et les hints des edit text (avec les edit texts)
        poidsHint = getResources().getString(R.string.poidsHint);
        tailleHint = getResources().getString(R.string.tailleHint);
        poidsE = (EditText) findViewById(R.id.widget37);
        poidsE.setOnKeyListener(modificationListener);
        tailleE = (EditText) findViewById(R.id.widget39);
        tailleE.setOnKeyListener(modificationListener);
        // récupération de divers widgets
        widget44 = (Button) findViewById(R.id.widget44);
        widget44.setOnClickListener(calculClickListener);
        widget45 = (Button) findViewById(R.id.widget45);
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
