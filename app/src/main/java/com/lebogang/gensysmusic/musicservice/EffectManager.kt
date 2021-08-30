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

package com.lebogang.gensysmusic.musicservice

import android.media.audiofx.DynamicsProcessing
import android.os.Build
import androidx.annotation.RequiresApi

class EffectManager(audioSessionId:Int) {
    @RequiresApi(Build.VERSION_CODES.P)
    private val dynamicProcessingConfig = DynamicsProcessing.Config.Builder(0,1,
        true, 1, true, 1, true,
        1, true)
        .build()
    @RequiresApi(Build.VERSION_CODES.P)
    private val  dynamicsProcessing = DynamicsProcessing(100,audioSessionId,
        dynamicProcessingConfig)

    @RequiresApi(Build.VERSION_CODES.P)
    fun isDynamicProcessingEnabled() = dynamicsProcessing.enabled

    @RequiresApi(Build.VERSION_CODES.P)
    fun setEnabledDynamicProcessing(state:Boolean) = dynamicsProcessing.setEnabled(state)

    @RequiresApi(Build.VERSION_CODES.P)
    fun setPreEq(gain: Float){
        val preEq = dynamicsProcessing.getPreEqByChannelIndex(0).apply {
            getBand(0).gain = gain
        }
        dynamicsProcessing.setPreEqAllChannelsTo(preEq)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun setMultiBandCompressor(gain: Float){
        val compressor = dynamicsProcessing.getMbcByChannelIndex(0).apply {
            getBand(0).preGain = gain
            getBand(0).postGain = gain
        }
        dynamicsProcessing.setMbcAllChannelsTo(compressor)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun setPostEq(gain: Float){
        val postEq = dynamicsProcessing.getPostEqByChannelIndex(0).apply {
            getBand(0).gain = gain
        }
        dynamicsProcessing.setPostEqAllChannelsTo(postEq)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun setLimiter(gain: Float){
        val limiter = dynamicsProcessing.getLimiterByChannelIndex(0).apply {
            postGain = gain
        }
        dynamicsProcessing.setLimiterAllChannelsTo(limiter)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun setInputGain(gain: Float){
        dynamicsProcessing.setInputGainAllChannelsTo(gain)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun setInput(gain: Float, inputType: InputType){
        when(inputType){
            InputType.PRE_EQ -> setPreEq(gain)
            InputType.COMPRESSOR -> setMultiBandCompressor(gain)
            InputType.POST_EQ -> setPostEq(gain)
            InputType.LIMITER -> setLimiter(gain)
            InputType.INPUT_GAIN -> setInputGain(gain)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getGain(type: InputType) = when(type){
        InputType.PRE_EQ -> dynamicsProcessing.getPreEqBandByChannelIndex(0,0).gain
        InputType.COMPRESSOR -> dynamicsProcessing.getMbcBandByChannelIndex(0,0).postGain
        InputType.POST_EQ -> dynamicsProcessing.getPostEqBandByChannelIndex(0,0).gain
        InputType.LIMITER -> dynamicsProcessing.getLimiterByChannelIndex(0).postGain
        InputType.INPUT_GAIN -> dynamicsProcessing.getInputGainByChannelIndex(0)
    }

    enum class InputType{
        PRE_EQ,COMPRESSOR,POST_EQ,LIMITER,INPUT_GAIN
    }
}
