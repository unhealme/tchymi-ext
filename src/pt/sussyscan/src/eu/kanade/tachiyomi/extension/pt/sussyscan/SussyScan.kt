package eu.kanade.tachiyomi.extension.pt.sussyscan

import eu.kanade.tachiyomi.multisrc.madara.Madara
import eu.kanade.tachiyomi.network.interceptor.rateLimit
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.Locale

class SussyScan : Madara(
    "Sussy Scan",
    "https://oldi.sussytoons.com",
    "pt-BR",
    SimpleDateFormat("MMMM dd, yyyy", Locale("pt", "BR")),
) {
    override val client = super.client.newBuilder()
        .rateLimit(2)
        .build()

    override val useLoadMoreRequest = LoadMoreStrategy.Never
    override val useNewChapterEndpoint = true

    override val mangaDetailsSelectorAuthor = "div.manga-authors > a"
    override val mangaDetailsSelectorDescription = ".manga-about.manga-info"
    override val mangaDetailsSelectorThumbnail = "head meta[property='og:image']"

    override fun imageFromElement(element: Element): String? {
        return super.imageFromElement(element)?.takeIf { it.isNotEmpty() }
            ?: element.attr("content") // Thumbnail from <head>
    }
}
