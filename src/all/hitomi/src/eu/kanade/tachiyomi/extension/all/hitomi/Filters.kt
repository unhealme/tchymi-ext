package eu.kanade.tachiyomi.extension.all.hitomi

import eu.kanade.tachiyomi.source.model.Filter
import eu.kanade.tachiyomi.source.model.FilterList

fun getFilters(): FilterList {
    return FilterList(
        SortFilter("Sort by", getSortsList),
        QueryModeFilter(),
        TypeFilter("Types"),
        Filter.Separator(),
        Filter.Header("Default to comma (,) if empty"),
        TextFilter("Tags Separator", "sep"),
        Filter.Header("Prepend with dash (-) to exclude"),
        TextFilter("Groups", "group"),
        TextFilter("Artists", "artist"),
        TextFilter("Series", "series"),
        TextFilter("Characters", "character"),
        TextFilter("Male Tags", "male"),
        TextFilter("Female Tags", "female"),
        Filter.Header("Please don't put Female/Male tags here, they won't work!"),
        TextFilter("Tags", "tag"),
    )
}

internal open class TextFilter(name: String, val type: String) : Filter.Text(name)
internal open class SortFilter(
    name: String,
    val vals: List<Triple<String, String?, String>>,
    state: Selection = Selection(0, false),
) :
    Filter.Sort(name, vals.map { it.first }.toTypedArray(), state) {
    fun getArea() = vals[state!!.index].second
    fun getValue() = vals[state!!.index].third
}

internal class TypeFilter(name: String) :
    Filter.Group<CheckBoxFilter>(
        name,
        galleryType.map { CheckBoxFilter(it.value, it.key, true) },
    )

internal enum class QueryMode { AND, OR }
internal class QueryModeFilter :
    Filter.Select<QueryMode>("Query Mode", QueryMode.values(), 0)

internal open class CheckBoxFilter(name: String, val value: String, state: Boolean) :
    Filter.CheckBox(name, state)

private val getSortsList: List<Triple<String, String?, String>> = listOf(
    Triple("Date Added", null, "index"),
    Triple("Date Published", "date", "published"),
    Triple("Popular: Today", "popular", "today"),
    Triple("Popular: Week", "popular", "week"),
    Triple("Popular: Month", "popular", "month"),
    Triple("Popular: Year", "popular", "year"),
    Triple("Random", null, "index"),
)

val galleryType: Map<String, String> = mapOf(
    "anime" to "Anime",
    "artistcg" to "Artist CG",
    "doujinshi" to "Doujinshi",
    "gamecg" to "Game CG",
    "imageset" to "Image Set",
    "manga" to "Manga",
)
