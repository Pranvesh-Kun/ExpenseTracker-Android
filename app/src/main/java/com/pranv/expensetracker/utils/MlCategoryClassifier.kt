package com.pranv.expensetracker.utils

import ai.onnxruntime.OnnxMap
import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.content.Context

class MlCategoryClassifier(
    context: Context
) {
    private val env = OrtEnvironment.getEnvironment()
    private lateinit var session: OrtSession
    init {
        val modelBytes = context.assets.open("expense_classifier.onnx").readBytes()
        session = env.createSession(modelBytes)
    }
    fun predict(merchant: String): Pair<String, Float> {
        val inputTensor = OnnxTensor.createTensor(
            env,
            arrayOf(arrayOf(merchant))
        )
        return inputTensor.use { tensor ->
            val result = session.run(
                mapOf(
                    "merchant" to tensor
                )
            )
            result.use {
                val label = it[0].value as Array<String>
                @Suppress("UNCHECKED_CAST")
                val probabilitiesList = it[1].value as List<OnnxMap>
                @Suppress("UNCHECKED_CAST")
                val probabilities = probabilitiesList[0].value as Map<String, Float>

                // Close OnnxMap objects returned in the list as they are not closed by result.close()
                probabilitiesList.forEach { map -> map.close() }

                val confidence =
                    probabilities.values.maxOrNull() ?: 0f
                Pair(
                    label[0],
                    confidence
                )
            }
        }
    }
}