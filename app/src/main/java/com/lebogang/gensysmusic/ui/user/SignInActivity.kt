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

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.UserProfileChangeRequest
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.ActivitySignInBinding
import com.lebogang.gensysmusic.ui.local.HomeActivity

class SignInActivity : GoogleHelper() {
    private val bind:ActivitySignInBinding by lazy{ActivitySignInBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = null
        if (auth.currentUser != null)
            startActivity(Intent(applicationContext,HomeActivity::class.java))
        setContentView(bind.root)
        setSupportActionBar(bind.toolbar)
        initViewPager()
        initViews()
    }

    private fun initViewPager(){
        bind.viewpager.adapter = ViewpagerAdapter(this)
        TabLayoutMediator(bind.tabLayout,bind.viewpager){tab,position->
            when(position){
                0 -> tab.text = getString(R.string.login_in)
                1 -> tab.text = getString(R.string.create_new)
            }
        }.attach()
    }

    private fun initViews(){
        bind.googleButton.setOnClickListener { signIn() }
        bind.phoneButton.setOnClickListener { startActivity(Intent(this,PhoneActivity::class.java)) }
    }

    override fun signIn() {
        val client = GoogleSignIn.getClient(this,options)
        contract.launch(client.signInIntent)
    }

    internal fun signInEmail(email:String, password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            if (it.isSuccessful)
                startActivity(Intent(applicationContext,HomeActivity::class.java))
            else
                Toast.makeText(this,getString(R.string.check_your_details),Toast.LENGTH_SHORT).show()
        }
    }

    internal fun createUser(email:String,password:String,username:String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful)
                addUserName(username)
            else {
                Toast.makeText(this, getString(R.string.check_your_details), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun addUserName(username: String){
        val user = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()
        auth.currentUser?.updateProfile(user)?.addOnCompleteListener {
            if (it.isSuccessful){
                startActivity(Intent(applicationContext,HomeActivity::class.java))
            }
        }
    }

}
