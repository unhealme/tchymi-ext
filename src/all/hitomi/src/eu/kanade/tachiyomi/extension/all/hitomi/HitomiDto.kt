package eu.kanade.tachiyomi.extension.all.hitomi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class Gallery(
    val galleryurl: String,
    val title: String,
    val language: String,
    val date: String,
    val type: String,
    val id: String,
    val japanese_title: String?,
    val tags: List<Tag>?,
    val artists: List<Artist>?,
    val groups: List<Group>?,
    val characters: List<Character>?,
    val parodys: List<Parody>?,
    val files: List<ImageFile>,
) {
    val galleryinfo: List<String>
        get() = (
            listOf(
                title,
                "url:$galleryurl",
                "type:$type",
                "id:$id",
            ) +
                (tags?.map { it.formatted } ?: emptyList()) +
                (artists?.map { "artist:${it.artist}" } ?: emptyList()) +
                (groups?.map { "group:${it.group}" } ?: emptyList()) +
                (characters?.map { "character:${it.character}" } ?: emptyList()) +
                (parodys?.map { "series:${it.parody}" } ?: emptyList())
            )
}

@Serializable
data class ImageFile(
    val hash: String,
)

@Serializable
data class Tag(
    val female: JsonPrimitive?,
    val male: JsonPrimitive?,
    val tag: String,
) {
    val formatted
        get() = if (female?.content == "1") {
            "Female:${tag.toCamelCase()}"
        } else if (male?.content == "1") {
            "Male:${tag.toCamelCase()}"
        } else {
            "Tag:${tag.toCamelCase()}"
        }
}

@Serializable
data class Artist(
    val artist: String,
) {
    val formatted get() = artist.toCamelCase()
}

@Serializable
data class Group(
    val group: String,
) {
    val formatted get() = group.toCamelCase()
}

@Serializable
data class Character(
    val character: String,
) {
    val formatted get() = character.toCamelCase()
}

@Serializable
data class Parody(
    val parody: String,
) {
    val formatted get() = parody.toCamelCase()
}

private fun String.toCamelCase(): String {
    val result = StringBuilder(length)
    var capitalize = true
    for (char in this) {
        result.append(
            if (capitalize) {
                char.uppercase()
            } else {
                char.lowercase()
            },
        )
        capitalize = char.isWhitespace()
    }
    return result.toString()
}
