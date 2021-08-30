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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.databinding.FragmentCreateNewBinding
import com.lebogang.gensysmusic.ui.utils.DialogStyle

class CreateNewFragment : Fragment(){
    private lateinit var bind:FragmentCreateNewBinding

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        bind = FragmentCreateNewBinding.inflate(inflater,parent, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        bind.emailEditText.doAfterTextChanged {
            bind.signButton.isEnabled = !it.isNullOrEmpty() &&
                    !bind.passwordEditText.text.isNullOrEmpty() &&
                    !bind.usernameEditText.text.isNullOrEmpty()
        }
        bind.passwordEditText.doAfterTextChanged {
            bind.signButton.isEnabled = !it.isNullOrEmpty() &&
                    !bind.passwordEditText.text.isNullOrEmpty() &&
                    !bind.usernameEditText.text.isNullOrEmpty()
        }
        bind.usernameEditText.doAfterTextChanged {
            bind.signButton.isEnabled = !it.isNullOrEmpty() &&
                    !bind.passwordEditText.text.isNullOrEmpty() &&
                    !bind.usernameEditText.text.isNullOrEmpty()
        }
        bind.signButton.setOnClickListener {
            val email = bind.emailEditText.text?.toString()
            val password = bind.passwordEditText.text?.toString()
            val username = bind.usernameEditText.text?.toString()
            val passLength = bind.passwordEditText.text?.length ?: 0
            if (passLength < 6)
                showPasswordDialog()
            else if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !username.isNullOrEmpty())
                    (requireActivity() as SignInActivity).createUser(email, password,username)
        }
    }

    private fun showPasswordDialog() = MaterialAlertDialogBuilder(requireContext())
        .setBackground(DialogStyle.getDialogBackground(requireContext()))
        .setTitle(getString(R.string.invalid_input))
        .setMessage(getString(R.string.password_constraint))
        .setPositiveButton(getString(R.string.okay),null)
        .create().show()
}
