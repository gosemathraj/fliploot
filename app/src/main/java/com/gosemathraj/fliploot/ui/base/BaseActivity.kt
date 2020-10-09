package com.gosemathraj.fliploot.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.gosemathraj.fliploot.R
import com.gosemathraj.fliploot.utils.safeExecute

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    @get:LayoutRes
    abstract val layoutResourceId : Int

    abstract val currentFragment : Fragment?

    abstract val fragmentContainerId : Int

    protected lateinit var dataBinding : T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
    }

    /*
    *   Fragment Transactions
    */
    fun addFragment(fragment : Fragment) {
        addFragment(fragment, addToBackStack = false)
    }

    fun addFragment(fragment : Fragment, addToBackStack : Boolean) {
        safeExecute {
            if (fragment == null || fragment.isAdded || currentFragment?.javaClass?.name == fragment.javaClass.name) {
                return@safeExecute
            }
            val transaction = supportFragmentManager.beginTransaction()
            if (!isFinishing) {
                if (addToBackStack) {
                    transaction.addToBackStack(fragment.javaClass.simpleName)
                }
                transaction.add(fragmentContainerId, fragment, fragment.javaClass.simpleName)
                transaction.commit()
            }
        }
    }

    fun replaceFragment(fragment : Fragment, addToBackStack : Boolean) {
        safeExecute {
            if (fragment == null || fragment.isAdded || currentFragment?.javaClass?.name == fragment.javaClass.name) {
                return@safeExecute
            }
            val transaction = supportFragmentManager.beginTransaction()
            if (!isFinishing) {
                if (addToBackStack) {
                    transaction.addToBackStack(fragment.javaClass.simpleName)
                }
                transaction.replace(fragmentContainerId, fragment, fragment.javaClass.simpleName)
                transaction.commit()
            }
        }
    }

    /**
     *  Progressbar Dialog
     */
    private val progressBar: AlertDialog? by lazy {
        this.let {
            AlertDialog.Builder(it, R.style.ProgressDialog).setView(R.layout.layout_loading)
                .setCancelable(false).create()
        }
    }

    fun initLoader() {
        progressBar?.let { myProgressBar ->
            if (!myProgressBar.isShowing) {
                myProgressBar.show()
            } else {
                if (this.isFinishing) {
                    myProgressBar.show()
                }
            }
        }
    }

    fun finishLoader() {
        try {
            progressBar?.let {
                if (it.isShowing) {
                    it.dismiss()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *  Show Toast
     */
    fun showToast(toastMessage : String) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
    }

    fun showToast(toastMessage: String, length : Int) {
        Toast.makeText(this, toastMessage, length).show()
    }

    /**
     *  Show Snackbar
     */
    fun showSnack(snackMessage : String) {
        Snackbar.make(findViewById(android.R.id.content), snackMessage, Snackbar.LENGTH_SHORT).show()
    }

    /**
     *  Activity Navigator
     */
    fun openActivity(c: Class<*>) {
        val intent = Intent(this, c)
        startActivity(intent)
    }

    fun openActivity(c: Class<*>, b : Bundle) {
        val intent = Intent(this, c)
        intent.putExtras(b)
        startActivity(intent)
    }

    fun openActivity(intent : Intent) {
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        finishLoader()
    }

    /**
     *  Hide KeyBoard
     */
    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}