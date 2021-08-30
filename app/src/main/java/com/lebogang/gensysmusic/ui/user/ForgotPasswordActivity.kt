/*
 * Copyright (c) 2021. - Lebogang Bantsijang
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.lebogang.gensysmusic.ui.user

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.ActivityForgotPasswordBinding
import com.lebogang.gensysmusic.utils.Keys

class ForgotPasswordActivity : AppCompatActivity() {
    private val bind:ActivityForgotPasswordBinding by lazy{ActivityForgotPasswordBinding.inflate(layoutInflater) }
    private var email:String? = null
    private val auth:FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        email = intent.getStringExtra(Keys.EMAIL_KEY)
        initToolbar()
        initViews()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViews(){
        bind.emailEditText.doAfterTextChanged {
            bind.requestButton.isEnabled = !it.isNullOrEmpty()
        }
        bind.emailEditText.setText(email)
        bind.requestButton.setOnClickListener {
            bind.emailEditText.text?.let {
                bind.progressBar.visibility = View.VISIBLE
                sendLink(it.toString())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun sendLink(email:String){
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful)
                bind.feedbackTextView.text = getString(R.string.email_sent_to) + email
            else
                bind.feedbackTextView.text = getString(R.string.failed_to_send_email)
            bind.progressBar.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(Keys.EMAIL_KEY,email)
        super.onSaveInstanceState(outState)
    }
}
