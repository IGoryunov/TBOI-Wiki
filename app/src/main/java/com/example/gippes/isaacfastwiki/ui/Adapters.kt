package com.example.gippes.isaacfastwiki.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.gippes.isaacfastwiki.R
import com.example.gippes.isaacfastwiki.db.Element
import com.example.gippes.isaacfastwiki.ui.SuggestionsDetailAdapter.SuggestionHolder
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter

/**
 * Created by Igor Goryunov on 01.03.18.
 */

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val mFragments = arrayListOf<PageFragment>()
    private val mTitles = arrayListOf<String>()

    override fun getItem(position: Int): Fragment = mFragments[position]

    override fun getPageTitle(position: Int): CharSequence = mTitles[position]

    override fun getCount(): Int = mFragments.size

    override fun getItemPosition(`object`: Any): Int {
        super.getItemPosition(`object`)
        val fragment = `object` as PageFragment
        val pos = mTitles.indexOf(fragment.title)
        return if (pos == -1) PagerAdapter.POSITION_NONE else pos
    }

    fun addFragment(fragment: PageFragment) {
        mFragments.add(fragment)
        mTitles.add(fragment.title)
    }

    fun clear() {
        mFragments.clear()
        mTitles.clear()
    }
}

class ElementsAdapter(private val context: Context,
                      val data: ArrayList<Element>,
                      private var mClickListener: View.OnClickListener? = null)
    : RecyclerView.Adapter<ElementsAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        Log.d(LOG_TAG, "create view holder")
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.view_grid_element, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Log.d(LOG_TAG, "bind view holder - $position")
        holder.apply {
            image.setImageDrawable(data[position].image)
            itemView.tag = data[position].id
            itemView.setOnClickListener(mClickListener)
        }
    }

    class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView!!.findViewById(R.id.image)!!
    }
}

class SuggestionsDetailAdapter(private val mInflater: LayoutInflater, private val mOnClickListener: View.OnClickListener) : SuggestionsAdapter<Element, SuggestionHolder>(mInflater) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionHolder =
            SuggestionHolder(mInflater.inflate(R.layout.view_list_element, parent, false))

    override fun getSingleViewHeight(): Int = 70

    override fun onBindSuggestionHolder(element: Element?, holder: SuggestionHolder?, position: Int) {
        holder?.apply {
            if (element != null) {
                image.setImageDrawable(element.image)
                title.text = element.title
                message.text = element.message
                itemView.tag = element.id
                itemView.setOnClickListener(mOnClickListener)
            }
        }
    }

    inner class SuggestionHolder(view: View?) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view!!.findViewById(R.id.image)
        val title: TextView = view!!.findViewById(R.id.title)
        val message: TextView = view!!.findViewById(R.id.message)
    }
}
