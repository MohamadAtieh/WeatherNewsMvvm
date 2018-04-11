package com.example.multilivedata.ui.news

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatDialogFragment
import android.support.v7.widget.AppCompatSpinner
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.example.multilivedata.R
import com.example.multilivedata.network.nytimes.common.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsFilter(
        @Category var category: String = CategoryDef.MOST_VIEWED,
        @Section var section: String = SectionDef.ALL,
        @TimePeriod var timePeriod: String = TimePeriodDef.DAY
) : Parcelable

class NewsFilterDialogFragment : AppCompatDialogFragment() {

    interface NewsFilterDialogListener {
        fun onFilterSubmit(filter: NewsFilter)
    }

    companion object {
        private val LOG_TAG = NewsFilterDialogFragment::class.java.simpleName

        const val NEWS_FILTER_KEY = "news_filter_key"
    }

    // properties that require context
    private lateinit var listener: NewsFilterDialogListener
    private lateinit var categoryList: Array<String>
    private lateinit var sectionList: Array<String>
    private lateinit var timeList: Array<String>
    private lateinit var newsFilter: NewsFilter

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(activity).apply {
            val view = activity?.layoutInflater?.inflate(R.layout.dialog_news_filter, null) ?: return@apply
            setupViews(view)
            setView(view)

            setTitle(R.string.dialog_news_title)
            setPositiveButton(R.string.dialog_positive_text, { _, _ -> onPositiveClick()})
            setNegativeButton(R.string.dialog_negative_text, { _, _ -> dialog.cancel() })
        }

        return dialogBuilder.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            listener = activity as NewsFilterDialogListener
            categoryList = resources.getStringArray(R.array.category_list)
            sectionList = resources.getStringArray(R.array.section_list)
            timeList = resources.getStringArray(R.array.time_list)
        } catch (e: ClassCastException) {
            Log.d(LOG_TAG, "$activity must implement $LOG_TAG")
        }
    }

    private fun setupViews(view: View) {
        @LayoutRes val defaultSimpleResource = android.R.layout.simple_list_item_1
        newsFilter = getNewsFilter()

        with (view.findViewById<AppCompatSpinner>(R.id.category_picker)) {
            adapter = ArrayAdapter(activity, defaultSimpleResource, categoryList)
            setSelection(getSelectedCategoryPosition(newsFilter.category))
        }

        with (view.findViewById<AppCompatSpinner>(R.id.section_picker)) {
            adapter = ArrayAdapter(activity, defaultSimpleResource, sectionList)
            setSelection(getSelectedSectionPosition(newsFilter.section))
        }

        with (view.findViewById<AppCompatSpinner>(R.id.time_picker)) {
            adapter = ArrayAdapter(activity, defaultSimpleResource, timeList)
            setSelection(getSelectedTimePosition(newsFilter.timePeriod))
        }
    }

    private fun getSelectedCategoryPosition(category: String): Int
        = when (category) {
            CategoryDef.MOST_VIEWED -> 0
            CategoryDef.MOST_EMAILED -> 1
            CategoryDef.MOST_SHARED -> 2
            else -> 0
        }

    private fun getSelectedSectionPosition(section: String): Int
        = when (section) {
            SectionDef.ALL -> 0
            SectionDef.BOOKS -> 1
            SectionDef.FOOD -> 2
            SectionDef.MOVIES -> 3
            SectionDef.SPORTS -> 4
            else -> 0
        }

    private fun getSelectedTimePosition(time: String): Int
        = when (time) {
            TimePeriodDef.DAY -> 0
            TimePeriodDef.WEEK -> 1
            TimePeriodDef.MONTH -> 2
            else -> 0
        }

    private fun onPositiveClick() {
        with (dialog.findViewById<AppCompatSpinner>(R.id.category_picker)) {
            val selectedItem = selectedItem as String
            newsFilter.category = when (selectedItem) {
                categoryList[0] -> CategoryDef.MOST_VIEWED
                categoryList[1] -> CategoryDef.MOST_EMAILED
                categoryList[2] -> CategoryDef.MOST_SHARED
                else -> return@with
            }
        }

        with (dialog.findViewById<AppCompatSpinner>(R.id.section_picker)) {
            val selectedItem = selectedItem as String
            newsFilter.section = when (selectedItem) {
                sectionList[0] -> SectionDef.ALL
                sectionList[1] -> SectionDef.BOOKS
                sectionList[2] -> SectionDef.FOOD
                sectionList[3] -> SectionDef.MOVIES
                sectionList[4] -> SectionDef.SPORTS
                else -> return@with
            }
        }

        with (dialog.findViewById<AppCompatSpinner>(R.id.time_picker)) {
            val selectedItem = selectedItem as String
            newsFilter.timePeriod = when (selectedItem) {
                timeList[0] -> TimePeriodDef.DAY
                timeList[1] -> TimePeriodDef.WEEK
                timeList[2] -> TimePeriodDef.MONTH
                else -> return@with
            }
        }

        listener.onFilterSubmit(newsFilter)
        dialog.dismiss()
    }

    private fun getNewsFilter() = arguments?.getParcelable(NEWS_FILTER_KEY) as? NewsFilter ?: NewsFilter()
}