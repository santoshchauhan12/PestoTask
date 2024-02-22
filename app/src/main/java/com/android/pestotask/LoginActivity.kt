package com.android.pestotask

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.pestotask.databinding.ActivityLoginBinding
import com.android.pestotask.ext.hide
import com.android.pestotask.ext.show
import com.android.pestotask.utils.Constants
import com.android.pestotask.utils.PreferenceUtils
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance()


        // Set on Click Listener on Registration button
        binding.login.setOnClickListener(View.OnClickListener { loginUserAccount() })
    }

    private fun loginUserAccount() {

        // show the visibility of progress bar to show loading
        binding.progressbar.show()

        // Take the value of two edit texts in Strings
        val email: String = binding.email.text.toString()
        val password: String = binding.password.text.toString()

        // validations for input email and password
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

        // signin existing user
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Login successful!!",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    // hide the progress bar
                    binding.progressbar.hide()
                    val userid  = FirebaseAuth.getInstance().currentUser?.uid
                    PreferenceUtils.putString(this, Constants.KEY_USERID, userid.orEmpty())
                    // if sign-in is successful
                    // intent to home activity
                    val intent = Intent(
                        this@LoginActivity,
                        HomeActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                } else {
                    // sign-in failed
                    Toast.makeText(
                        applicationContext,
                        "Login failed!!",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    // hide the progress bar
                    binding.progressbar.hide()
                }
            }
    }
}