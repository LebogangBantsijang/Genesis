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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.lebogang.gensysmusic.databinding.FragmentLogInBinding

class LoginFragment : Fragment(){
    private lateinit var bind:FragmentLogInBinding

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        bind = FragmentLogInBinding.inflate(inflater,parent, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        bind.emailEditText.doAfterTextChanged {
            bind.signButton.isEnabled = !it.isNullOrEmpty() && !bind.passwordEditText.text.isNullOrEmpty()
        }
        bind.passwordEditText.doAfterTextChanged {
            bind.signButton.isEnabled = !it.isNullOrEmpty() && !bind.passwordEditText.text.isNullOrEmpty()
        }
        bind.resetPasswordButton.setOnClickListener {
            startActivity(Intent(requireContext(),ForgotPasswordActivity::class.java))
        }
        bind.signButton.setOnClickListener {
            (requireActivity() as SignInActivity).signInEmail(bind.emailEditText.text!!.toString(),
                bind.passwordEditText.text!!.toString())
        }
    }
}
