package com.gosemathraj.fliploot.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    @get:LayoutRes
    abstract val layoutResourceId : Int

    protected lateinit var dataBinding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return dataBinding.root
    }

    /*
    *   Fragment Transactions
    */
    fun replaceFragment(fragment: Fragment, addToBackStack : Boolean){
        ((activity) as BaseActivity<*>).replaceFragment(fragment, addToBackStack)
    }

    /**
     *  Show Toast
     */
    fun showToast(toastMessage : String) {
        Toast.makeText(activity, toastMessage, Toast.LENGTH_LONG).show()
    }

    fun showToast(toastMessage: String, length : Int) {
        Toast.makeText(activity, toastMessage, length).show()
    }

    /**
     *  Show Snackbar
     */
    fun showSnack(snackMessage : String) {
        (activity as BaseActivity<*>).showSnack(snackMessage)
    }

    /**
     *  Activity Navigator
     */
    fun openActivity(c: Class<*>) {
        (activity as BaseActivity<*>).openActivity(c)
    }

    fun openActivity(c: Class<*>, b : Bundle) {
        (activity as BaseActivity<*>).openActivity(c, b)
    }

    fun openActivity(intent : Intent) {
        (activity as BaseActivity<*>).openActivity(intent)
    }
}