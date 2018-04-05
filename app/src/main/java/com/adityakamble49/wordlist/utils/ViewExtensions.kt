package com.adityakamble49.wordlist.utils

import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast

/**
 * @author Aditya Kamble
 * @since 4/4/2018
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View? = LayoutInflater.from(context).inflate(
        layoutRes,
        this, false)

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.gone() {
    this.visibility = GONE
}

fun View.invisible() {
    this.visibility = INVISIBLE
}

fun View.selected() {
    this.isSelected = true
}

fun View.deselect() {
    this.isSelected = false
}

fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment, fragment.tag) }
}

fun AppCompatActivity.showToast(toastString: String) {
    Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(toastString: String) {
    Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show()
}