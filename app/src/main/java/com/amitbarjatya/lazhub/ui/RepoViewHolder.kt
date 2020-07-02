package com.amitbarjatya.lazhub.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amitbarjatya.lazhub.R
import com.amitbarjatya.lazhub.model.Repo
import com.bumptech.glide.Glide

/*
Copyright (c) 2012-2020 Grab Taxi Holdings PTE LTD (GRAB), All Rights Reserved. NOTICE: All information contained herein is, and
remains the property of GRAB. The intellectual and technical concepts contained herein are confidential, proprietary and controlled by
GRAB and may be covered by patents, patents in process, and are protected by trade secret or copyright law.
You are strictly forbidden to copy, download, store (in any medium), transmit, disseminate, adapt or change this material in any way
unless prior written permission is obtained from GRAB. Access to the source code contained herein is hereby forbidden to anyone except
current GRAB employees or contractors with binding Confidentiality and Non-disclosure agreements explicitly covering such access.
The copyright notice above does not evidence any actual or intended publication or disclosure of this source code, which includes
information that is confidential and/or proprietary, and is a trade secret, of GRAB.
ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC PERFORMANCE, OR PUBLIC DISPLAY OF OR THROUGH USE OF THIS SOURCE CODE WITHOUT THE
EXPRESS WRITTEN CONSENT OF GRAB IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE LAWS AND INTERNATIONAL TREATIES. THE RECEIPT OR
POSSESSION OF THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE
ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.
*/

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)
    private val stars: TextView = view.findViewById(R.id.repo_stars)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val forks: TextView = view.findViewById(R.id.repo_forks)
    private val avatar: ImageView = view.findViewById(R.id.avatar)

    private var repo: Repo? = null

    init {
        view.setOnClickListener {
            repo?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(repo: Repo?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            language.visibility = View.GONE
            stars.text = resources.getString(R.string.unknown)
            forks.text = resources.getString(R.string.unknown)
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: Repo) {
        this.repo = repo
        name.text = repo.fullName

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.description != null) {
            description.text = repo.description
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

        stars.text = repo.stars.toString()
        forks.text = repo.forks.toString()

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!repo.language.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            language.text = resources.getString(R.string.language, repo.language)
            languageVisibility = View.VISIBLE
        }
        language.visibility = languageVisibility

        Glide.with(itemView.context)
            .load(repo.owner.avatarUrl)
            .circleCrop()
            .into(avatar)
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)
            return RepoViewHolder(view)
        }
    }
}