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
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.lebogang.gensysmusic.ApiKeys
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.ui.local.HomeActivity
import com.lebogang.gensysmusic.ui.stream.StreamActivity

abstract class GoogleHelper :AppCompatActivity(){
    internal lateinit var contract: ActivityResultLauncher<Intent>
    internal val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    internal val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(ApiKeys.GOOGLE_REQUEST_TOKEN)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.let { data->
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    signIn(account.idToken)
                }catch (e: ApiException){
                    Log.e("Error",e.toString())
                    Toast.makeText(applicationContext
                        ,getString(R.string.failed),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    abstract fun signIn()

    private fun signIn(tokenId:String?){
        val credential = GoogleAuthProvider.getCredential(tokenId,null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful)
                startActivity(Intent(applicationContext, HomeActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                })
            else{
                Log.e("Login", it.exception?.toString()?:it.toString())
                Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
