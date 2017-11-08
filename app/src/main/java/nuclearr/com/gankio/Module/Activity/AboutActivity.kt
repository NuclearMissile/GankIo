package nuclearr.com.gankio.Module.Activity

import android.widget.ImageView
import android.widget.TextView
import me.drakeet.multitype.Items
import me.drakeet.support.about.AbsAboutActivity
import me.drakeet.support.about.Category
import me.drakeet.support.about.License
import nuclearr.com.gankio.BuildConfig
import nuclearr.com.gankio.R

class AboutActivity : AbsAboutActivity() {
    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.drawable.default_img)
        slogan.text = "One more GankIO app."
        version.text = "v" + BuildConfig.VERSION_NAME
    }

    override fun onItemsCreated(items: Items) {
        items.add(Category("Open Source Licenses"))
        items.add(License("RxJava", "ReactiveX", License.APACHE_2, "https://github.com/ReactiveX/RxJava"))
        items.add(License("RxAndroid", "ReactiveX", License.APACHE_2, "https://github.com/ReactiveX/RxAndroid"))
        items.add(License("about-page", "drakeet", License.APACHE_2, "https://github.com/android-links/about-page"))
        items.add(License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"))
        items.add(License("BottomNavigation", "Ashok-Varma", License.APACHE_2, "https://github.com/Ashok-Varma/BottomNavigation"))
        items.add(License("CircleImageView", "hdodenhof", License.APACHE_2, "https://github.com/hdodenhof/CircleImageView"))
        items.add(License("PhotoView", "Chris Banes", License.APACHE_2, "https://github.com/chrisbanes/PhotoView"))
        items.add(License("RxLifecycle", "Trello", License.APACHE_2, "https://github.com/trello/RxLifecycle"))
        items.add(License("google-gson", "google", License.APACHE_2, "https://github.com/google/gson"))
        items.add(License("OkHttp", "square", License.APACHE_2, "https://github.com/square/okhttp"))
        items.add(License("Retrofit", "square", License.APACHE_2, "https://github.com/square/retrofit"))
        items.add(License("Glide", "Sam Judd", License.APACHE_2, "https://github.com/bumptech/glide"))
        items.add(License("jsoup", "jhy", License.MIT, "https://github.com/jhy/jsoup"))
    }
}

