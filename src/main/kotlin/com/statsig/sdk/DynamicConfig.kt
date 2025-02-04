package com.statsig.sdk

import com.google.gson.Gson

/**
 * A helper class for interfacing with Dynamic Configs defined in the Statsig console
 */
class DynamicConfig(
    val name: String,
    val value: Map<String, Any>,
    val ruleID: String? = null,
    val secondaryExposures: ArrayList<Map<String, String>> = arrayListOf()) {

    init { }

    /**
     * Gets a value from the config, falling back to the provided default value
     * @param key the index within the DynamicConfig to fetch a value from
     * @param default the default value to return if the expected key does not exist in the config
     * @return the value at the given key, or the default value if not found
     */
    fun getString(key: String, default: String?): String? {
        if (!value.containsKey(key)) {
            return default
        }
        return when (this.value[key]) {
            null -> default
            is String -> this.value[key] as String
            else -> default
        }
    }

    /**
     * Gets a value from the config, falling back to the provided default value
     * @param key the index within the DynamicConfig to fetch a value from
     * @param default the default value to return if the expected key does not exist in the config
     * @return the value at the given key, or the default value if not found
     */
    fun getBoolean(key: String, default: Boolean): Boolean {
        if (!value.containsKey(key)) {
            return default
        }
        return when (this.value[key]) {
            null -> default
            is Boolean -> this.value[key] as Boolean
            else -> default
        }
    }

    /**
     * Gets a value from the config, falling back to the provided default value
     * @param key the index within the DynamicConfig to fetch a value from
     * @param default the default value to return if the expected key does not exist in the config
     * @return the value at the given key, or the default value if not found
     */
    fun getDouble(key: String, default: Double): Double {
        if (!value.containsKey(key)) {
            return default
        }
        return when (this.value[key]) {
            null -> default
            is Double -> this.value[key] as Double
            is Int -> this.value[key] as Double
            else -> default
        }
    }

    /**
     * Gets a value from the config, falling back to the provided default value
     * @param key the index within the DynamicConfig to fetch a value from
     * @param default the default value to return if the expected key does not exist in the config
     * @return the value at the given key, or the default value if not found
     */
    fun getInt(key: String, default: Int): Int {
        if (!value.containsKey(key)) {
            return default
        }
        if (value[key] == null) {
            return default
        }
        if (value[key] is Int) {
            return value[key] as Int
        }
        if (value[key] is Double) {
            return (value[key] as Double).toInt()
        }
        return default
    }

    /**
     * Gets a value from the config, falling back to the provided default value
     * @param key the index within the DynamicConfig to fetch a value from
     * @param default the default value to return if the expected key does not exist in the config
     * @return the value at the given key, or the default value if not found
     */
    fun getArray(key: String, default: Array<*>?): Array<*>? {
        if (!value.containsKey(key)) {
            return default
        }
        return when (value[key]) {
            null -> default
            is Array<*> -> this.value[key] as Array<*>
            is IntArray -> (this.value[key] as IntArray).toTypedArray()
            is DoubleArray -> (this.value[key] as DoubleArray).toTypedArray()
            is BooleanArray -> (this.value[key] as BooleanArray).toTypedArray()
            else -> default
        }
    }

    /**
     * Gets a dictionary from the config, falling back to the provided default value
     * @param key the index within the DynamicConfig to fetch a dictionary from
     * @param default the default value to return if the expected key does not exist in the config
     * @return the value at the given key, or the default value if not found
     */
    fun getDictionary(key: String, default: Map<String, Any>?): Map<String, Any>? {
        if (!value.containsKey(key)) {
            return default
        }
        return when (this.value[key]) {
            null -> default
            is Map<*, *> -> this.value[key] as Map<String, Any>
            else -> default
        }
    }

    /**
     * Gets a value from the config as a new DynamicConfig, or null if not found
     * @param key the index within the DynamicConfig to fetch a value from
     * @return the value at the given key as a DynamicConfig, or null
     */
    fun getConfig(key: String): DynamicConfig? {
        if (!value.containsKey(key)) {
            return null
        }
        return when (this.value[key]) {
            null -> null
            is Map<*, *> -> DynamicConfig(
                    key,
                    this.value[key] as Map<String, Any>,
                    this.ruleID
            )
            else -> null
        }
    }

    fun getExposureMetadata(): String {
        return Gson().toJson(
            mapOf(
                "config" to this.name,
                "ruleID" to this.ruleID,
                "secondaryExposures" to this.secondaryExposures
            )
        )
    }
}
