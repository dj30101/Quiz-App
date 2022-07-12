package com.example.quiz_app.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.quiz_app.R
import com.example.quiz_app.model.CategoryModel

/**
 * CategoryAdapter -> Adapter for Spinner Category
 */
class CategoryAdapter(
    /**
     * Context of Respective Activity
     */
    val context: Context, private val categoryList: List<CategoryModel>) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /**
     * Return Size of list
     */
    override fun getCount(): Int {
        return if (categoryList.isEmpty()) 0 else categoryList.size
    }

    /**
     * Return Category from CategoryList at given position p0
     */
    override fun getItem(p0: Int): Any {
        return categoryList[p0]
    }

    /**
     * Return Item Id
     */
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    /**
     * Show Spinner drop down View
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_spinner, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.txtItem.text = categoryList[position].name
        return view
    }

    private class ItemHolder(row: View?) {
        val txtItem: TextView = row?.findViewById(R.id.txt_item) as TextView

    }
}