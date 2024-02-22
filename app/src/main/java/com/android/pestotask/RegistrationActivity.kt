package com.android.pestotask

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.pestotask.databinding.ActivityRegistrationBinding
import com.android.pestotask.ext.hide
import com.android.pestotask.utils.Constants
import com.android.pestotask.utils.PreferenceUtils
import com.google.firebase.auth.FirebaseAuth


class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(PreferenceUtils.getString(this, Constants.KEY_USERID)?.isNotEmpty() == true) {
            val intent = Intent(
                this@RegistrationActivity,
                HomeActivity::class.java
            )
            startActivity(intent)
            finish()
        }
        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance()


        // Set on Click Listener on Registration button
        binding.btnregister.setOnClickListener(View.OnClickListener { registerNewUser() })
        binding.userLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@RegistrationActivity,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish()
        })
    }

    private fun registerNewUser() {

        // show the visibility of progress bar to show loading
        binding.progressbar.visibility = View.VISIBLE

        // Take the value of two edit texts in Strings
        val email: String
        val password: String
        email = binding.email.text.toString()
        password = binding.passwd.getText().toString()

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(
                applicationContext,
                "Please enter email!!",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(
                applicationContext,
                "Please enter password!!",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }

        // create new user or register new user
        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Registration successful!",
                        Toast.LENGTH_LONG
                    )
                        .show()


                    // hide the progress bar
                    binding.progressbar.hide()

                    // if the user created intent to login activity
                    val intent = Intent(
                        this@RegistrationActivity,
                        LoginActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                } else {

                    // Registration failed
                    Toast.makeText(
                        applicationContext, "Registration failed!!"
                                + " Please try again later",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    // hide the progress bar
                    binding.progressbar.hide()
                }
            }
    }
}