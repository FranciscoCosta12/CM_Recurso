package ipvc.estg.cm_recurso.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.cm_recurso.R
import ipvc.estg.cm_recurso.api.Post

class PostAdapter(val posts: List<Post>): RecyclerView.Adapter<PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_post, parent, false)
        return PostsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        return holder.bind(posts[position])
    }
}

class PostsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

    private val rue: TextView = itemView.findViewById(R.id.rue)
    private val desc: TextView = itemView.findViewById(R.id.desc)

    fun bind(post: Post){
        rue.text = "Local: " + post.rue
        desc.text = "Descrição: " + post.desc
    }

}