package com.sports2020.demo.api;

data class NetworkState constructor(
    val status: Status,
    val msg: String? = null,
    val isOver: Boolean? = false
) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.LOADING)
        fun error(msg: String?) = NetworkState(Status.ERROR, msg)
        val OVER = NetworkState(Status.SUCCESS, null, true);
    }
}