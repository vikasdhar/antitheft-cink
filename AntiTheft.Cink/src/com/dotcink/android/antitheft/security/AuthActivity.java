package com.dotcink.android.antitheft.security;

import com.dotcink.android.antitheft.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity about authentication.
 * @author dot.cink
 *
 */
public class AuthActivity extends Activity {

	/**
	 * request code used by startActivityForResult()
	 * @means verify password
	 */
	public static final int REQUEST_VERIFY_PASSWORD = 7001;
	/**
	 * request code used by startActivityForResult()
	 * @means set password
	 */
	public static final int REQUEST_SET_PASSWORD = 7002;
	/**
	 * request code used by startActivityForResult()
	 * @means reset password
	 */
	public static final int REQUEST_RESET_PASSWORD = 7003;

	/**
	 * intent action: User authentication.
	 */
	public static final String ACTION_VERIFY_PASSWORD = "dotcink.intent.ACTION_VERIFY_PASSWORD";
	/**
	 * intent action: Set app password.
	 */
	public static final String ACTION_SET_PASSWORD = "dotcink.intent.ACTION_SET_PASSWORD";
	/**
	 * intent action: Reset app password.
	 */
	public static final String ACTION_RESET_PASSWORD = "dotcink.intent.ACTION_RESET_PASSWORD";
	/**
	 * result: successful.
	 * @action: ACTION_VERIFY_PASSWORD, ACTION_SET_PASSWORD, ACTION_RESET_PASSWORD
	 */
	public static final int RESULT_SUCCESS = RESULT_OK;
	/**
	 * result: failed.
	 * @action: ACTION_VERIFY_PASSWORD, ACTION_SET_PASSWORD, ACTION_RESET_PASSWORD
	 */
	public static final int RESULT_FAILED = 7101;
	/**
	 * result: password has not been set.
	 * @action: ACTION_VERIFY_PASSWORD, ACTION_RESET_PASSWORD
	 */
	public static final int RESULT_PASSWORD_NOT_SET = 7102;
	/**
	 * result: password has already been set.
	 * @action: ACTION_SET_PASSWORD
	 */
	public static final int RESULT_PASSWORD_HAVE_SET = 7103;


	// views
	Button okButton;
	Button cancelButton;
	EditText oldPasswordArea;
	EditText newPasswordArea;
	TextView appPasswordInfo;
	TextView appPasswordInfoPadding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String action = getIntent().getAction();
		if(action.equals(ACTION_VERIFY_PASSWORD)){
			setContentView(R.layout.auth_verify);
			prepareAllViews();

			okButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(checkOldPassword()){
						setResult(RESULT_SUCCESS);
						finish();
					}
				}
			});

			if(!Security.hasPasswordSet(this)){
				toSetPasswordActivity();
			}
		}else if (action.equals(ACTION_SET_PASSWORD)){
			setContentView(R.layout.auth_set);
			prepareAllViews();

			okButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Security.setPassword(AuthActivity.this, 
							((EditText)findViewById(R.id.new_app_password)).getText().toString());
					setResult(RESULT_SUCCESS);
					finish();
				}
			});

			if(Security.hasPasswordSet(this)){
				toResetPasswordActivity();
			}
		}else if (action.equals(ACTION_RESET_PASSWORD)){
			setContentView(R.layout.auth_reset);
			prepareAllViews();

			okButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(checkOldPassword()){
						Security.setPassword(AuthActivity.this, 
								((EditText)findViewById(R.id.new_app_password)).getText().toString());
						setResult(RESULT_SUCCESS);
						finish();
					}
				}
			});

			if(!Security.hasPasswordSet(this)){
				toSetPasswordActivity();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == AuthActivity.REQUEST_SET_PASSWORD
				&& ACTION_VERIFY_PASSWORD.equals(getIntent().getAction())){
			if(resultCode != RESULT_SUCCESS){
				setResult(RESULT_FAILED);
				finish();
			}
		}else{
			setResult(resultCode);
			finish();
		}
	}

	/**
	 * Prepare all views of concern in this activity for future use.
	 */
	private void prepareAllViews(){
		okButton = (Button)findViewById(R.id.ok_button);
		cancelButton = (Button)findViewById(R.id.cancel_button);
		oldPasswordArea = (EditText)findViewById(R.id.old_app_password);
		newPasswordArea = (EditText)findViewById(R.id.new_app_password);
		appPasswordInfo = (TextView)findViewById(R.id.app_password_info);
		appPasswordInfoPadding = (TextView)findViewById(R.id.app_password_info_padding);

		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}

	/**
	 * check whether password in $oldPasswordArea is right or wrong.
	 * @return true if right or false otherwise.
	 */
	private boolean checkOldPassword(){
		if(Security.checkPassword(this, oldPasswordArea.getText().toString())){
			appPasswordInfo.setVisibility(View.INVISIBLE);
			appPasswordInfoPadding.setVisibility(View.INVISIBLE);
			return true;
		}else{
			appPasswordInfo.setVisibility(View.VISIBLE);
			appPasswordInfoPadding.setVisibility(View.VISIBLE);
			return false;
		}
	}

	private void toSetPasswordActivity(){
		Intent intent = new Intent(this, AuthActivity.class);
		intent.setAction(AuthActivity.ACTION_SET_PASSWORD);
		startActivityForResult(intent, AuthActivity.REQUEST_SET_PASSWORD);
	}

	private void toResetPasswordActivity(){
		Intent intent = new Intent(this, AuthActivity.class);
		intent.setAction(AuthActivity.ACTION_RESET_PASSWORD);
		startActivityForResult(intent, AuthActivity.REQUEST_RESET_PASSWORD);
	}
}