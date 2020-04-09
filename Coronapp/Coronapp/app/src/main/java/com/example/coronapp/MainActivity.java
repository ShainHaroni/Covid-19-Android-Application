package com.example.coronapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, RatingBar.OnRatingBarChangeListener, AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    private int displayImage = 0;
    private TextView blinking;
    private TextView shippingTxt;
    private HorizontalScrollView orderButtons;
    private LinearLayout orderButtonsLayout;
    private EditText name;
    private EditText phone;
    private EditText address;
    private EditText shippingInput;
    private AlertDialog.Builder builder;
    private final int availableMaskTypes = 8;
    private HashMap<ImageButton, Boolean> masks;
    private Button finish;
    private int totalPrice = 0;
    private RelativeLayout mainLayout;
    private RatingBar ratingBar;
    private RadioGroup shippingRadio;
    private RadioButton yesRadio;
    private RadioButton noRadio;
    private Spinner spinner;
    private CheckBox mode;
    private TextView headline;
    private TextView ratingText;
    private SeekBar masksAmount;
    private TextView amount;
    private TextView selectTxt;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientation(this);
        setContentView(R.layout.activity_main);

        headline = findViewById(R.id.Headline);
        headline.requestFocus();
        submit = findViewById(R.id.submitBtn);
        spinner = findViewById(R.id.phoneSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phone_prefixes, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0,false);
        spinner.setOnItemSelectedListener(this);
        finish = findViewById(R.id.finishBtn);
        orderButtons = findViewById(R.id.orderButtons);
        name = findViewById(R.id.nameInput);
        phone = findViewById(R.id.phoneInput);
        address = findViewById(R.id.addressInput);
        mode = findViewById(R.id.modeSelector);
        mode.setOnCheckedChangeListener(this);
        blinking = findViewById(R.id.blinkingText);
        orderButtonsLayout = new LinearLayout(MainActivity.this);
        orderButtonsLayout.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        orderButtons.removeAllViews();
        submit.setOnClickListener(this);
        finish.setOnClickListener(this);
        builder = new AlertDialog.Builder(this);
        masks = new HashMap<ImageButton, Boolean>(availableMaskTypes);
        mainLayout = findViewById(R.id.mainLayout);
        ratingBar = findViewById(R.id.Rating);
        ratingBar.setOnRatingBarChangeListener(this);
        shippingRadio = findViewById(R.id.shipingAddress);
        yesRadio = findViewById(R.id.yesRadio);
        noRadio = findViewById(R.id.noRadio);
        yesRadio.setOnClickListener(this);
        noRadio.setOnClickListener(this);
        shippingInput = findViewById(R.id.shippingAddressInput);
        ratingText = findViewById(R.id.RatingTxt);
        shippingTxt = findViewById(R.id.shippingTxt);
        masksAmount = findViewById(R.id.masksAmount);
        masksAmount.setOnSeekBarChangeListener(this);
        amount = findViewById(R.id.amountTxt);
        selectTxt = findViewById(R.id.selectTxt);
    }

    private void startBlinking() {
        ObjectAnimator anim = ObjectAnimator.ofInt(blinking, "backgroundColor", Color.RED, Color.YELLOW, Color.WHITE);
        anim.setDuration(1500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }

    private boolean checkName() {
        boolean result = false;
        if (name.getText().toString().isEmpty()) {
            result = true;
        }

        return result;
    }

    private boolean checkPhone() {
        boolean result = false;
        if (phone.getText().toString().isEmpty() || phone.getText().toString().length()<7 || !(phone.getText().toString().matches("[0-9]+"))) {
            result = true;
        }
        return result;
    }

    private boolean checkAddress() {
        boolean result = false;
        if (address.getText().toString().isEmpty()) {
            result = true;
        }
        return result;
    }

    private boolean checkShippingAddress() {
        boolean result = false;
        if (shippingInput.getText().toString().isEmpty()) {
            result = true;
        }
        return result;
    }

    private int setImage() {
        switch (displayImage) {
            case 0:
                return R.drawable.select;
            case 1:
                return R.drawable.m1;
            case 2:
                return R.drawable.m2;
            case 3:
                return R.drawable.m3;
            case 4:
                return R.drawable.m4;
            case 5:
                return R.drawable.m5;
            case 6:
                return R.drawable.m6;
            case 7:
                return R.drawable.m7;
            case 8:
                return R.drawable.m8;
        }
        return R.drawable.select;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ScrollView main = findViewById(R.id.main_scroll);
        if (isChecked){
            main.setBackgroundResource(R.drawable.dark_mode);
            setFontColor(true);
        }
        else{
            main.setBackgroundResource(R.drawable.light_mode);
            setFontColor(false);
        }
    }

    private void setFontColor(boolean b) {
        if(b)
        {
            name.setTextColor(Color.WHITE);
            phone.setTextColor(Color.WHITE);
            address.setTextColor(Color.WHITE);
            shippingInput.setTextColor(Color.WHITE);
            name.setHintTextColor(Color.WHITE);
            phone.setHintTextColor(Color.WHITE);
            address.setHintTextColor(Color.WHITE);
            shippingInput.setHintTextColor(Color.WHITE);
            mode.setTextColor(Color.WHITE);
            yesRadio.setTextColor(Color.WHITE);
            noRadio.setTextColor(Color.WHITE);
            blinking.setTextColor(Color.WHITE);
            ratingText.setTextColor(Color.WHITE);
            shippingTxt.setTextColor(Color.WHITE);
            amount.setTextColor(Color.WHITE);
            selectTxt.setTextColor(Color.WHITE);
        }
        else{
            name.setTextColor(Color.BLACK);
            phone.setTextColor(Color.BLACK);
            address.setTextColor(Color.BLACK);
            shippingInput.setTextColor(Color.BLACK);
            name.setHintTextColor(Color.DKGRAY);
            phone.setHintTextColor(Color.DKGRAY);
            address.setHintTextColor(Color.DKGRAY);
            shippingInput.setHintTextColor(Color.DKGRAY);
            mode.setTextColor(Color.BLACK);
            yesRadio.setTextColor(Color.BLACK);
            noRadio.setTextColor(Color.BLACK);
            blinking.setTextColor(Color.BLACK);
            ratingText.setTextColor(Color.BLACK);
            shippingTxt.setTextColor(Color.BLACK);
            amount.setTextColor(Color.BLACK);
            selectTxt.setTextColor(Color.BLACK);
        }
    }

    public static void setOrientation(Activity context) {
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.O)
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        else
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitBtn)
            submit();
        if (v.getId() == R.id.finishBtn)
            finishOrder();
        if (v.getId() == R.id.yesRadio)
            hideShippingAddress();
        if (v.getId() == R.id.noRadio)
            showShippingAddress();
    }

    private void hideShippingAddress() {
        shippingRadio.clearCheck();
        shippingRadio.check(R.id.yesRadio);
        shippingInput.setVisibility(View.GONE);
    }

    private void showShippingAddress() {
        shippingRadio.clearCheck();
        shippingRadio.check(R.id.noRadio);
        shippingInput.setVisibility(View.VISIBLE);
    }


    private void finishOrder() {
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.yesBtn, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                setContentView(R.layout.activity_main);
                recreate();
                Toast.makeText(getApplicationContext(), R.string.toastNewOrder, Toast.LENGTH_SHORT).show();
            }
        })
                .setNegativeButton(R.string.noBtn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Toast.makeText(getApplicationContext(), R.string.toastClosed, Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

    private void submit() {
        orderButtons.removeAllViews();
        orderButtonsLayout.removeAllViews();
        if (!checkName() && !checkPhone() && !checkAddress() && checkSpinner()) {
            if(noRadio.isChecked() && checkShippingAddress())
                return;
            final int numOfOrders = masksAmount.getProgress();
            for (int i = 0; i < numOfOrders; i++) {
                final ImageButton button = new ImageButton(MainActivity.this);
                button.setBackgroundResource(R.drawable.select);
                button.setScaleType(ImageView.ScaleType.CENTER_CROP);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (displayImage < availableMaskTypes) {
                            displayImage++;
                            masks.put(button, true);
                        } else {
                            displayImage = 0;
                            masks.put(button, false);
                        }
                        ((ImageButton) view).setBackgroundResource(setImage());
                        if (checkAllSelected()) {
                            finish.setEnabled(true);
                            totalPrice = numOfOrders * 5;
                            Snackbar.make(mainLayout, getString(R.string.snackBarTotalPrice) + " " + totalPrice + "$",
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
                orderButtonsLayout.addView(button);
                masks.put(button, false);
            }
            orderButtons.addView(orderButtonsLayout);
            startBlinking();
            blinking.setVisibility(View.VISIBLE);
            selectTxt.setVisibility(View.VISIBLE);
            finish.setEnabled(false);
            checkAllSelected();
        }
        checkFields();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
    }

    private void checkFields() {
        boolean show = false;
        if (!checkName())
            name.setBackgroundResource(R.drawable.edittext_shape);
        else {
            name.setBackgroundResource(R.drawable.edittext_wrong);
            show = true;
        }
        if (!checkPhone())
            phone.setBackgroundResource(R.drawable.edittext_shape);
        else{
            phone.setBackgroundResource(R.drawable.edittext_wrong);
            show = true;
        }
        if (!checkAddress())
            address.setBackgroundResource(R.drawable.edittext_shape);
        else{
            address.setBackgroundResource(R.drawable.edittext_wrong);
            show = true;
        }
        if(noRadio.isChecked()){
            if (!checkShippingAddress())
                shippingInput.setBackgroundResource(R.drawable.edittext_shape);
            else {
                shippingInput.setBackgroundResource(R.drawable.edittext_wrong);
                show = true;
            }
        }
        if(show)
            Toast.makeText(this, R.string.toastFields, Toast.LENGTH_SHORT).show();
    }

    private boolean checkSpinner() {
        boolean result = true;
        if(spinner.getSelectedItemId() == 0) {
            result = false;
            Toast.makeText(this, R.string.toastPhonePrefix, Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private boolean checkAllSelected() {
        boolean result = false;
        Iterator<Map.Entry<ImageButton, Boolean>> i = masks.entrySet().iterator();
        Map.Entry mapElement;
        if (!masks.isEmpty()) {
            while (i.hasNext()) {
                mapElement = (Map.Entry) i.next();
                if (!(Boolean) mapElement.getValue()) {
                    result = false;
                    break;
                } else
                    result = true;
            }
        }

        return result;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        switch ((int) ratingBar.getRating()) {
            case 1:
                Toast.makeText(this, R.string.toastVeryBad, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, R.string.toastNeedImprovement, Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, R.string.toastGood, Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, R.string.toastGreat, Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, R.string.toastAwesome, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(id == 0)
            Toast.makeText(this, R.string.toastPhonePrefix, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        String amountxt = Integer.toString(seekBar.getProgress());
        amount.setText(getString(R.string.masksAmountTxt) + " " + amountxt);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        seekBar.requestFocus();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
