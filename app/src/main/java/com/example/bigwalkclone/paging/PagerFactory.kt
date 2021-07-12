package com.example.bigwalkclone.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource

object PagerFactory {

    fun<Key: Any, Value: Any> createPager(pageSize: Int, dataSource: PagingSource<Key, Value>): Pager<Key, Value> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
            ),
            pagingSourceFactory = {
                dataSource
            },
        )
    }

}