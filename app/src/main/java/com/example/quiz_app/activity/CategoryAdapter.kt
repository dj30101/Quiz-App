package com.example.quiz_app.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.quiz_app.R
import com.example.quiz_app.model.CategoryModel

class CategoryAdapter(val context: Context, private val categoryList: List<CategoryModel>) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return if (categoryList.isEmpty()) 0 else categoryList.size
    }

    override fun getItem(p0: Int): Any {
        return categoryList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

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