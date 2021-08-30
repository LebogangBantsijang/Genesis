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

package com.lebogang.gensysmusic.ui.local

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.gensysmusic.GensysApp
import com.lebogang.gensysmusic.R
import com.lebogang.gensysmusic.database.local.models.Music
import com.lebogang.gensysmusic.databinding.ActivityEditBinding
import com.lebogang.gensysmusic.ui.utils.DialogStyle
import com.lebogang.gensysmusic.ui.utils.ImageLoader
import com.lebogang.gensysmusic.ui.utils.ModelFactory
import com.lebogang.gensysmusic.ui.utils.Type
import com.lebogang.gensysmusic.ui.local.viewmodel.MusicViewModel
import com.lebogang.gensysmusic.utils.Keys

class EditActivity : AppCompatActivity() {
    private val bind :ActivityEditBinding by lazy{ActivityEditBinding.inflate(layoutInflater)}
    private lateinit var music: Music
    private val imageLoader: ImageLoader by lazy{ ImageLoader(this) }
    private val musicViewModel: MusicViewModel by lazy{ ModelFactory(application as GensysApp)
        .getMusicViewModel()}
    private lateinit var permissionContract: ActivityResultLauncher<IntentSenderRequest>
    private lateinit var permissionContract29:ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.edit)
        setContentView(bind.root)
        music = intent.extras?.getParcelable(Keys.MUSIC_KEY)!!
        initToolbar()
        permissionContract = registerForActivityResult(StartIntentSenderForResult()) {
            if (it.resultCode == Activity.RESULT_OK) updateItem()
        }
        permissionContract29 = registerForActivityResult(RequestPermission()){
            if(it) updateItem() else showDialog()
        }
        initViews()
    }

    private fun initToolbar(){
        setSupportActionBar(bind.toolbar)
        bind.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViews(){
        imageLoader.loadImage(music.artUri, Type.MUSIC,bind.imageView)
        bind.titleEditText.setText(music.title)
        bind.albumEditText.setText(music.album)
        bind.artistEditText.setText(music.artist)
        bind.updateButton.setOnClickListener {
            val title = bind.titleEditText.text?.toString()
            val album = bind.albumEditText.text?.toString()
            val artist = bind.artistEditText.text?.toString()
            if (!title.isNullOrBlank() or !album.isNullOrBlank() or !artist.isNullOrBlank()){
                music.title = title!!
                music.album = album!!
                music.artist = artist!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                    update()
                else{
                    if (ContextCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED){ updateItem()
                    }else permissionContract29.launch(WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private fun updateItem(){
        musicViewModel.update(this,music)
        Toast.makeText(this,getString(R.string.item_update),Toast.LENGTH_SHORT).show()
        onBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun update(){
        val intent = MediaStore.createWriteRequest(contentResolver,listOf(Uri.parse(music.contentUri)))
        val request = IntentSenderRequest.Builder(intent).build()
        permissionContract.launch(request)
    }

    private fun showDialog() = MaterialAlertDialogBuilder(this)
        .setBackground(DialogStyle.getDialogBackground(this))
        .setTitle(getString(R.string.permission_error))
        .setMessage(getString(R.string.permission_error_message))
        .setNegativeButton(getString(R.string.close),null)
        .setPositiveButton(getString(R.string.grant)){_,_->
            permissionContract29.launch(WRITE_EXTERNAL_STORAGE)
        }
        .create().show()

}
